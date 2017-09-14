package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bxt.sptask.utils.DefParamUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SpGrabDataHandle {
	/**
	 * @param resultDatas_json 爬虫提交数据
	 * @param taskPro_json  原始任务
	 * @param curRootParams_json root任务
	 * @param childParamId       子任务ID
	 * @param childTaskParam_json  子任务
	 * @param taskNumPerChild      子任务步长
	 * @param genIdx   任务索引位置
	 * @param remarks  提示信息
	 * @return
	 * @throws Exception 
	 */
	public static boolean chunkDeriveTask4Common(JSONObject resultDatas_json,JSONObject taskParams_json, JSONObject curRootParams_json, String childParamId, JSONObject childTaskParam_json, Integer taskNumPerChild, Integer genIdx, String remarks) throws Exception{
		if(taskNumPerChild > 0 && resultDatas_json != null && childParamId != null && childTaskParam_json != null){
			taskParams_json.remove("root");       //移除父任务
			taskParams_json.remove(childParamId); //移除当前子任务
			
			taskParams_json.put("root", childTaskParam_json); //把当前子任务设置为父任务
			//获取子任务生产规则
			//$genTaskCfg = $currentTaskParam["taskGenConf"][$genIdx];
			JSONArray genTaskCfg_arr = null;
			if(curRootParams_json.containsKey("taskGenConf")){
				genTaskCfg_arr = curRootParams_json.getJSONArray("taskGenConf");
				if(genTaskCfg_arr != null && genTaskCfg_arr.size()>0){
					JSONObject genTaskCfg_json = (JSONObject)genTaskCfg_arr.get(genIdx);
					if(genTaskCfg_json != null){
						if(genTaskCfg_json.containsKey("params") && genTaskCfg_json.get("params") != null){
							JSONArray params_json = genTaskCfg_json.getJSONArray("params");
							String fromPath = "",toParaPath = "";
							JSONObject targetValues = null,souceValues = null;
							for(int j = 0;j<params_json.size();j++){
								JSONObject paramSetCfg_json = (JSONObject)params_json.get(j);
								
								JSONObject currAllParaMap_json = curRootParams_json.getJSONObject("pathStructMap");
								
								fromPath = paramSetCfg_json.getString("paramPath");
								toParaPath = paramSetCfg_json.getString("toParamPath");
								if(fromPath == null || fromPath.trim().equals("")){
									continue;
								}
								if(toParaPath == null && toParaPath.trim().equals("")){
									continue;
								}
								fromPath = fromPath.substring(1, fromPath.trim().length()-1);
				                toParaPath = toParaPath.substring(1, toParaPath.trim().length()-1);
								
				                if(currAllParaMap_json.getJSONObject(fromPath).getString("paramSource").equals("5")){
				                	//在这里先不设置来自父任务抓取结果中的参数，到下面进行设置(因为首先需要根据配置将N条抓取结果最为一个小的数据集
				                    //来产生子任务(该数据集可以用于生成子任务URL时，替换Enum、obj、Num等规则定义)，参数N不同，则用于生成子任务的
				                    //数据就会不同，可能会直接影响Enum、Num等变量的取值个数，所以在这里不进行设置该类参数传递
				                    continue;
				                }
				                JSONObject fromPath_json = currAllParaMap_json.getJSONObject(fromPath); 
				                JSONObject toParaPath_json = currAllParaMap_json.getJSONObject(toParaPath);
				                
				                souceValues = DefParamUtil.getSourceObj(curRootParams_json, fromPath_json);
				                String paramValue = DefParamUtil.getValueFromObjWrap(souceValues, fromPath_json);
				                targetValues = DefParamUtil.getSourceObj(childTaskParam_json, toParaPath_json);
				                if(!targetValues.isNullObject()){
				                	targetValues = setValue4ObjWrap(targetValues,toParaPath_json,paramValue);
				                	currAllParaMap_json.put(toParaPath, toParaPath_json);
				                	curRootParams_json.put("pathStructMap", currAllParaMap_json);
				                }else{
				                	System.out.println("测试信息！");
				                	continue;
				                }
				                
				                /*$souceValues = &getSourceObj($currentTaskParam, $currAllParaMap[$fromPath]);
				                $paramValue = getValueFromObjWrap($souceValues, $currAllParaMap[$fromPath]);
				                $targetValues = &getSourceObj($childTaskParam, $currAllParaMap[$toParaPath]);
				                setValue4ObjWrap($targetValues, $currAllParaMap[$toParaPath], $paramValue);*/
				                
				            
								
							}
						}else{
							 //$logger->debug("no need to transmit parentTaskParams to child task! Param4Child:[" . var_export($genTaskCfg, true) . "].");
						}
					}else{
						//规则数组内容为空，
					}
				}else{
					//规则数组不存在
				}
			}else{
				//throw new Exception("Generator child task execption,param:[taskNumPerChild] must more than 0![" . $taskNumPerChild . "].");
			}
		}else{
			//throw new Exception("Generator child task execption,param:[taskNumPerChild] must more than 0![" . $taskNumPerChild . "].");
		}
		
		return true;
	}
	
	//"collectfloorKey":{"col_name":"g_collect","col_type":"24","col_name_ex":
	//{"type":1,"data":{"arr_data":{"arr_idx":-1,"min_comb_eles":null,"max_comb_eles":null}},
	//"name_ex":{"type":2,"data":{"chld_col_name":"floor"},"name_ex":null}},"paramSource":10}
	public static JSONObject setValue4ObjWrap(JSONObject targetValues,JSONObject toParaPath_json,String paramValue){
		if(toParaPath_json.containsKey("col_name_ex") && toParaPath_json.get("col_name_ex") != null){
			 //递归调用
			 //"paramsDef":{"run_url":"http://list.jiuxian.com/1-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0.htm?area=2","source_host":"电商","column":"白酒项目","column1":"酒仙网","floor":0},
			 //"path606":{"col_name":"column","col_type":"13","col_name_ex":null,"paramSource":2}
			 //"path799":{"col_name":"column","col_type":"13","col_name_ex":null,"paramSource":4}
			JSONArray targetValues_arr = (JSONArray) targetValues.get(toParaPath_json.getString("col_name"));
			targetValues_arr = SpGrabDataHandle.getValue4Obj(targetValues_arr, (JSONObject)toParaPath_json.get("col_name_ex"), paramValue);
			targetValues.put("col_name", targetValues_arr);
		}else{
			targetValues.put(toParaPath_json.getString("col_name"), paramValue);
		}
		return targetValues;
	}
	
	public static JSONArray getValue4Obj(JSONArray targetValues_arr,JSONObject toParaPath_json,String paramValue){
		if(toParaPath_json.containsKey("type") && toParaPath_json.get("type") != null){
			String stype = toParaPath_json.getString("type");
			if(stype.equals("1")){
				//处理数组
				String arrayIdx = toParaPath_json.getJSONObject("data").getJSONObject("arr_data").getString("arr_idx");
				if(arrayIdx != null && arrayIdx.equals("-1")){
					if(toParaPath_json.containsKey("name_ex") && toParaPath_json.get("name_ex") != null){
						
						for(int i =0;i<targetValues_arr.size();i++){
							JSONArray oneElement = (JSONArray)targetValues_arr.get(i);
							oneElement = getValue4Obj(oneElement,toParaPath_json.getJSONObject("name_ex"),paramValue);
							targetValues_arr.add(oneElement);
						}
					}else{
						targetValues_arr.add(paramValue);
					}
				}else{
					
					
					
				}
			}else if (stype.equals("2")) {
				//处理对象
				
				
				
			}
			
			
		}
		return targetValues_arr;
	}
	
	public static JSONObject formatPostdata(JSONObject postdata_json){
		JSONArray retdata_arr = new JSONArray();
		
		String imgpattern = "";
		if(postdata_json != null){
			if(postdata_json.containsKey("data") && postdata_json.get("data") != null){
				JSONArray data_jsonarr = postdata_json.getJSONArray("data");
				for (int i = 0; i < data_jsonarr.size(); i++) {
					JSONObject data_jsonobj = (JSONObject)data_jsonarr.get(i);
					if(data_jsonobj.containsKey("text") && data_jsonobj.get("text") != null){
						String item_text = data_jsonobj.getString("text");
						if(item_text == null){
							item_text = new String("");
						}
						
						imgpattern = "<[img|IMG].*?src=[\\'|\\\"](.*?(?:[\\.gif|\\.jpg|\\.jpeg|\\.bmp|\\.png|\\.pic]?))[\\'|\\\"].*?[\\/]?>";
						Matcher matches2 = Pattern.compile(imgpattern).matcher(item_text);  
						
						List<String> listImgUrl = new ArrayList<String>();  
				        while (matches2.find()) {  
				            listImgUrl.add(matches2.group());  
				        }  
					      
				        String pattern = "<br>|<br\\/>|<p>|<\\/p>|<BR>|<BR\\/>|<P>|<\\/P>";
				        String replacement = "\r\n";
				        item_text = item_text.replaceAll(pattern, replacement);
				        item_text = item_text.replaceAll("<script[^>]*?>.*?</script>", "");
				        item_text = item_text.replaceAll("<[\\/\\!]*?[^<>]*?>", "");
				        item_text = item_text.replaceAll("([\r\n])[\\s]+", "\r\n"); //php中说是去掉空白符，但是这样的替换就没有回车换行了 "'([\r\n])[\s]+'","\\1"
				        item_text = item_text.replaceAll("&(quot|#34);", "\"");
				        item_text = item_text.replaceAll("&(amp|#38);", "&");
				        item_text = item_text.replaceAll("&(lt|#60);", "<");
				        item_text = item_text.replaceAll("&(gt|#62);", ">");
				        item_text = item_text.replaceAll("&(nbsp|#160);"," ");
				        char c161 = 161;
				        char c162 = 162;
		                char c163 = 163;
		                char c169 = 169;
				        item_text = item_text.replaceAll("&(iexcl|#161);", String.valueOf(c161));
				        item_text = item_text.replaceAll("&(cent|#162);", String.valueOf(c162));
				        item_text = item_text.replaceAll("&(pound|#163);",String.valueOf(c163));
				        item_text = item_text.replaceAll("&(copy|#169);",String.valueOf(c169));
				        item_text = item_text.replaceAll("　","");
				        //item_text = item_text.replaceAll("&#(\\d+);","chr(\\1)");//根据情况再做决定
				        
				        //需要去除所有html标签吗？
				       /* Pattern p = Pattern.compile("<[^>]+>([^<]*)</[^>]+>");
					    Matcher m = p.matcher(item_text);
					    while(m.find()){
					    	item_text = item_text.replaceFirst("<[^>]+>([^<]*)</[^>]+>", m.group(1).toString());
					    }*/
				        
				        String[] paragraphs = item_text.split("[\r\n]+");
				        String pg_text = "";
				        JSONArray article = new JSONArray();
				        for(String pitem:paragraphs){
				        	pg_text = pitem.trim();
				        	if(pg_text != null && !pg_text.equals("")){
				        		article.add(pg_text); //整篇文章
				        	}
				        }
				        
				        if(article.size() > 0){
				        	data_jsonobj.put("pg_text", article);
				        }
				        data_jsonobj.put("text", item_text);
				        if(!data_jsonobj.containsKey("bmiddle_pic") || data_jsonobj.get("bmiddle_pic") == null){
				        	data_jsonobj.put("bmiddle_pic",listImgUrl);
				        	// Integer ecr = 0;
							// String strpattern =
							// ".*?\\.[gif|jpg|jpeg|bmp|png|pic]";
							// for(String imgarr:listImgUrl){
							//
							// }
				        }
				        	
				        	
				        System.out.println("测试信息:"+ data_jsonobj.getString("bmiddle_pic"));
					}
					
					data_jsonobj.put("paragraphid", 0);
					retdata_arr.add(data_jsonobj);
				}
				
				
			}
			postdata_json.put("data", retdata_arr);
		}
		return postdata_json;
	}
	
	public static String stringToJson(String str) {        
        StringBuffer sb = new StringBuffer();        
        for (int i=0; i<str.length(); i++) {  
            char c = str.charAt(i);    
             switch (c){
         /* case '\"':        
                 sb.append("\\\"");        
                 break;     */
             case '\\':        
                 sb.append("\\\\");        
                 break;      
             case '/':        
                 sb.append("\\/");        
                 break;        
             case '\b':        
                 sb.append("\\b");        
                 break;        
             case '\f':        
                 sb.append("\\f");        
                 break;        
             case '\n':        
                 sb.append("\\n");        
                 break;        
             case '\r':        
                 sb.append("\\r");        
                 break;        
             case '\t':        
                 sb.append("\\t");        
                 break;        
             default:        
                 sb.append(c);     
             }  
         }
        return sb.toString();     
    } 
	
	
	public static void main(String[] args) {
        //String stest = "{\"data\":[{\"compDesMatch\":\"4.8\",\"serviceScore\":\"4.7\",\"logisticsScore\":\"4.8\",\"post_title\":\"爱居兔2017夏季热卖女装可爱卡通图案中袖休闲衬衫\",\"created_at\":\"2017-03-19\",\"productType\":\"爱居兔2017夏季热卖女装可爱卡通图案中袖休闲衬衫\",\"pro_init_price\":0,\"proOriPrice\":169,\"allRepNum\":\"12\",\"productColor\":\"漂白花纹(10)\",\"stockNum\":null,\"product_no\":530663079505,\"isFavorite\":82,\"productFullName\":\"Eichitoo/H兔\",\"text\":\"材质成分: 棉100%货号: ENEBJ2G009A服装版型: 直筒风格: <p class=\\\"f_center\\\"><img alt=\\\"undefined\\\" src=\\\"http://cms-bucket.nosdn.127.net/f16151d031784416ac54f59afefa64e220161114102530.png?imageView&amp;thumbnail=550x0\\\"></p>其他衣长: 常规款袖长: 七分袖袖型: 其他领型: 其他图案: 其他品牌: Eichitoo/H兔适用年龄: 25-29周岁年份季节: 2016年夏季颜色分类: 漂白花纹(10)尺码: 155/80A 160/84A 165/88A 170/92A\",\"detailParam\":[\"材质成分: 棉100%\",\"货号: ENEBJ2G009A\",\"服装版型: 直筒\",\"风格: 其他\",\"衣长: 常规款\",\"袖长: 七分袖\",\"袖型: 其他\",\"领型: 其他\",\"图案: 其他\",\"品牌: Eichitoo/H兔\",\"适用年龄: 25-29周岁\",\"年份季节: 2016年夏季\",\"颜色分类: 漂白花纹(10)\",\"尺码: 155/80A 160/84A 165/88A 170/92A\"],\"productDesc\":[\"材质成分: 棉100%\",\"货号: ENEBJ2G009A\",\"服装版型: 直筒\",\"风格: 其他\",\"衣长: 常规款\",\"袖长: 七分袖\",\"袖型: 其他\",\"领型: 其他\",\"图案: 其他\",\"品牌: Eichitoo/H兔\",\"适用年龄: 25-29周岁\",\"年份季节: 2016年夏季\",\"颜色分类: 漂白花纹(10)\",\"尺码: 155/80A 160/84A 165/88A 170/92A\"],\"satisfaction\":\"4.6\",\"compName\":\"h兔官方旗舰店\",\"original_url\":\"https://detail.tmall.com/item.htm?id=530663079505&areaId=110100&is_b=1&cat_id=50025135&rn=3cfaa5f1a2be445247c4c65e4c10a3a8\",\"source_host\":\"电商\",\"column\":\"天猫h兔官方旗舰店女装大量\",\"column1\":\"天猫-旗舰店大量\",\"floor\":0}]}";
		String sss = "{data:[{"
+ "\"page_url\":\"https://detail.tmall.com/item.htm?id=530663079505&areaId=110100&is_b=1&cat_id=50025135&rn=3cfaa5f1a2be445247c4c65e4c10a3a8\"," +

"\"data\":[{\"compDesMatch\":\"4.8\",\"serviceScore\":\"4.7\",\"logisticsScore\":\"4.8\"," +
"\"post_title\":\"爱居兔2017夏季热卖女装可爱卡通图案中袖休闲衬衫\"," +
"\"created_at\":\"2017-03-19\"," +
"\"productType\":\"爱居兔2017夏季热卖女装可爱卡通图案中袖休闲衬衫\"," +
"\"pro_init_price\":0,\"proOriPrice\":169,\"allRepNum\":\"12\"," +
"\"productSize\":\"155/80A\\r\\n160/84A\\r\\n165/88A\\r\\n170/92A\"," +
"\"productColor\":\"漂白花纹(10)\",\"stockNum\":null,\"product_no\":530663079505,\"isFavorite\":82," +
"\"productFullName\":\"Eichitoo/H兔\"," +

"\"text\":\"材质成分: 棉100%货号: ENEBJ2G009A服装版型: 直筒风格: 其他衣长: 常规款袖长: 七分袖袖型: 其他领型: 其他图案: 其他品牌: Eichitoo/H兔适用年龄: 25-29周岁年份季节: 2016年夏季颜色分类: 漂白花纹(10)尺码: 155/80A 160/84A 165/88A 170/92A\"," +

"\"detailParam\":[\"材质成分: 棉100%\",\"货号: ENEBJ2G009A\",\"服装版型: 直筒\",\"风格: 其他\",\"衣长: 常规款\",\"袖长: 七分袖\",\"袖型: 其他\",\"领型: 其他\",\"图案: 其他\",\"品牌: Eichitoo/H兔\",\"适用年龄: 25-29周岁\",\"年份季节: 2016年夏季\",\"颜色分类: 漂白花纹(10)\",\"尺码: 155/80A 160/84A 165/88A 170/92A\"]," +

"\"productDesc\":[\"材质成分: 棉100%\",\"货号: ENEBJ2G009A\",\"服装版型: 直筒\",\"风格: 其他\",\"衣长: 常规款\",\"袖长: 七分袖\",\"袖型: 其他\",\"领型: 其他\",\"图案: 其他\",\"品牌: Eichitoo/H兔\",\"适用年龄: 25-29周岁\",\"年份季节: 2016年夏季\",\"颜色分类: 漂白花纹(10)\",\"尺码: 155/80A 160/84A 165/88A 170/92A\"]," +
"\"satisfaction\":\"4.6\"," +
"\"compName\":\"h兔官方旗舰店\"," +

"\"original_url\":\"https://detail.tmall.com/item.htm?id=530663079505&areaId=110100&is_b=1&cat_id=50025135&rn=3cfaa5f1a2be445247c4c65e4c10a3a8\"," +
"\"source_host\":\"电商\",\"column\":\"天猫h兔官方旗舰店女装大量\",\"column1\":\"天猫-旗舰店大量\",\"floor\":0}]," +

"\"param\":{\"runTimeParam\":{\"scene\":{\"isremote\":1,\"historystat\":null},\"run_url\":\"https://detail.tmall.com/item.htm?id=530663079505&areaId=110100&is_b=1&cat_id=50025135&rn=3cfaa5f1a2be445247c4c65e4c10a3a8\"}}" +

"}]}";
		
		sss = stringToJson(sss);
		JSONObject resultDatas_json = JSONObject.fromObject(sss);
		
		String str = "{\"1\":{\"taskPro\":{\"tasklevel\":\"3\",\"local\":0,\"remote\":1,\"iscommit\":\"1\",\"source\":11,\"content_type\":0,\"duration\":\"3600\",\"conflictdelay\":\"\",\"iscalctrend\":\"0\",\"dataSave\":\"0\",\"filPageTag\":\"1\",\"addUser\":\"1\",\"genCreatedAt\":\"1\",\"isGenChildTask\":\"1\",\"dictionaryPlan\":\"[[]]\",\"cronstart\":\"\",\"specifiedType\":\"\",\"specifiedMac\":\"\",\"specifiedTypeForChild\":true,\"specifiedMacForChild\":true,\"cronend\":\"\",\"crontime\":\"\",\"crontimedes\":\"\",\"remarks\":\"智机网\",\"userPathId\":\"\",\"template\":\"117\"},\"templMap\":{\"wfun-xinxi\":[1]},\"stepNumURLPatterns\":{\"1\":[\"wfun.com/\"]},\"paramsDef_def\":null,\"parentParam_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"parentParam\",\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{\"run_url\":{\"col_type\":\"13\",\"col_type_ex\":null},\"floorNum\":{\"col_type\":\"5\",\"col_type_ex\":null},\"source_host\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column1\":{\"col_type\":\"13\",\"col_type_ex\":null}}},\"name\":\"parentParam\",\"open\":true,\"level\":0},\"runTimeParam_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"runTimeParam\",\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{\"original_url\":{\"col_type\":\"13\",\"col_type_ex\":null}}},\"name\":\"runTimeParam\",\"open\":true},\"constants_def\":null,\"outData_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"outData\",\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{\"proClassify\":{\"col_type\":\"13\",\"col_type_ex\":null},\"post_title\":{\"col_type\":\"13\",\"col_type_ex\":null},\"read_count\":{\"col_type\":\"5\",\"col_type_ex\":null},\"comments_count\":{\"col_type\":\"5\",\"col_type_ex\":null}}},\"name\":\"outData\",\"open\":true,\"level\":0},\"g_global_def\":null,\"URLCache_def\":null,\"TaskCache_def\":null,\"AppCache_def\":null,\"g_collect_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"g_collect\",\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":{\"col_type\":23,\"col_type_ex\":{\"sc_map\":{\"original_url\":{\"col_type\":\"13\",\"col_type_ex\":null},\"floor\":{\"col_type\":\"5\",\"col_type_ex\":null},\"source_host\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column1\":{\"col_type\":\"13\",\"col_type_ex\":null}}}}},\"name\":\"g_collect\",\"open\":true},\"CurrPageCache_def\":null,\"g_current_def\":null,\"paramsDef\":null,\"parentParam\":null,\"runTimeParam\":null,\"constants\":null,\"loginAccounts\":{\"globalaccount\":\"0\",\"logoutfirst\":\"0\",\"isswitch\":\"0\",\"switchpage\":\"5\",\"switchtime\":\"0\",\"switchstrategy\":1,\"accounts\":[],\"globalaccounts\":\"\"},\"pathStructMap\":{\"keys\":[\"runTimeParmOriUrlKey\",\"collectDatasOrigUrlKey\",\"sourceHostParentParam\",\"collectDatasourceHostKey\",\"columnParebtParam\",\"collectDatacolumnKey\",\"column1Parentparam\",\"collectDatacolumn1Key\",\"floorParentparam\",\"collectDatafloorKey\"],\"runTimeParmOriUrlKey\":{\"col_name\":\"original_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":3},\"collectDatasOrigUrlKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"original_url\"},\"name_ex\":null}},\"paramSource\":10},\"sourceHostParentParam\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatasourceHostKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"source_host\"},\"name_ex\":null}},\"paramSource\":10},\"columnParebtParam\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatacolumnKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"column\"},\"name_ex\":null}},\"paramSource\":10},\"column1Parentparam\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatacolumn1Key\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"column1\"},\"name_ex\":null}},\"paramSource\":10},\"floorParentparam\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatafloorKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"floor\"},\"name_ex\":null}},\"paramSource\":10},\"path223\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path249\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path440\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path808\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path717\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path857\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path301\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path503\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path384\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path670\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path773\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path493\":{\"col_name\":\"proClassify\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path764\":{\"col_name\":\"proClassify\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path438\":{\"col_name\":\"post_title\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path937\":{\"col_name\":\"post_title\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path38\":{\"col_name\":\"read_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":5},\"path311\":{\"col_name\":\"read_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path391\":{\"col_name\":\"comments_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":5},\"path77\":{\"col_name\":\"comments_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path642\":{\"col_name\":\"URLS\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path110\":{\"col_name\":\"URLS\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path53\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path993\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":2},\"path2\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path203\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":2},\"path96\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path396\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":2},\"path199\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path519\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":2},\"path149\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4}},\"filters\":[],\"taskUrls\":null,\"outData\":[],\"taskGenConf\":[{\"dataPath\":\"\",\"splitStep\":\"1\",\"childTaskDefId\":2,\"childTaskUrl\":{\"type\":\"consts\",\"value\":{\"paramValue\":\"|path223|\",\"paramSource\":4}},\"params\":[{\"paramPath\":\"|path249|\",\"toParamPath\":\"|path440|\"},{\"paramPath\":\"|path808|\",\"toParamPath\":\"|path717|\"},{\"paramPath\":\"|path857|\",\"toParamPath\":\"|path301|\"},{\"paramPath\":\"|path503|\",\"toParamPath\":\"|path384|\"},{\"paramPath\":\"|path670|\",\"toParamPath\":\"|path773|\"},{\"paramPath\":\"|path493|\",\"targetType\":\"0\",\"toParamPath\":\"|path764|\"},{\"paramPath\":\"|path438|\",\"targetType\":\"0\",\"toParamPath\":\"|path937|\"},{\"paramPath\":\"|path38|\",\"targetType\":\"0\",\"toParamPath\":\"|path311|\"},{\"paramPath\":\"|path391|\",\"targetType\":\"0\",\"toParamPath\":\"|path77|\"}]}],\"parentTaskID\":\"root\"},\"2\":{\"taskPro\":{\"tasklevel\":\"3\",\"local\":0,\"remote\":1,\"iscommit\":\"1\",\"source\":11,\"content_type\":0,\"duration\":\"3600\",\"conflictdelay\":\"\",\"iscalctrend\":\"0\",\"dataSave\":\"1\",\"filPageTag\":\"1\",\"addUser\":\"0\",\"genCreatedAt\":\"1\",\"isGenChildTask\":\"1\",\"dictionaryPlan\":\"[[]]\",\"cronstart\":\"\",\"specifiedType\":\"\",\"specifiedMac\":\"\",\"specifiedTypeForChild\":true,\"specifiedMacForChild\":true,\"cronend\":\"\",\"crontime\":\"\",\"crontimedes\":\"\",\"remarks\":\"智机网\",\"userPathId\":\"\",\"template\":\"117\"},\"templMap\":{\"wfun-pinglun\":[1]},\"stepNumURLPatterns\":{\"1\":[\"wfun.com/\"]},\"paramsDef_def\":null,\"parentParam_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"parentParam\",\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{\"run_url\":{\"col_type\":\"13\",\"col_type_ex\":null},\"floorNum\":{\"col_type\":\"5\",\"col_type_ex\":null},\"source_host\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column1\":{\"col_type\":\"13\",\"col_type_ex\":null},\"proClassify\":{\"col_type\":\"13\",\"col_type_ex\":null},\"post_title\":{\"col_type\":\"13\",\"col_type_ex\":null},\"read_count\":{\"col_type\":\"5\",\"col_type_ex\":null},\"comments_count\":{\"col_type\":\"5\",\"col_type_ex\":null}}},\"name\":\"parentParam\",\"open\":true},\"runTimeParam_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"runTimeParam\",\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{\"original_url\":{\"col_type\":\"13\",\"col_type_ex\":null}}},\"name\":\"runTimeParam\",\"open\":true},\"constants_def\":null,\"outData_def\":null,\"g_global_def\":null,\"URLCache_def\":null,\"TaskCache_def\":null,\"AppCache_def\":null,\"g_collect_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"g_collect\",\"col_type\":\"24\",\"col_type_ex\":{\"ele_col\":{\"col_type\":23,\"col_type_ex\":{\"sc_map\":{\"original_url\":{\"col_type\":\"13\",\"col_type_ex\":null},\"floor\":{\"col_type\":\"5\",\"col_type_ex\":null},\"source_host\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column1\":{\"col_type\":\"13\",\"col_type_ex\":null},\"proClassify\":{\"col_type\":\"13\",\"col_type_ex\":null},\"post_title\":{\"col_type\":\"13\",\"col_type_ex\":null},\"read_count\":{\"col_type\":\"5\",\"col_type_ex\":null},\"comments_count\":{\"col_type\":\"5\",\"col_type_ex\":null}}}}},\"name\":\"g_collect\",\"open\":true},\"CurrPageCache_def\":null,\"g_current_def\":null,\"paramsDef\":null,\"parentParam\":null,\"runTimeParam\":null,\"constants\":null,\"loginAccounts\":{\"globalaccount\":\"0\",\"logoutfirst\":\"0\",\"isswitch\":\"0\",\"switchpage\":\"5\",\"switchtime\":\"0\",\"switchstrategy\":1,\"accounts\":[],\"globalaccounts\":\"\"},\"pathStructMap\":{\"keys\":[\"ParentParmOriUrlKey\",\"collectDatasOrigUrlKey\",\"sourceHostParentParam\",\"collectDatasourceHostKey\",\"columnParentParam\",\"collectDatacolumnKey\",\"column1Parentparam\",\"collectDatacolumn1Key\",\"proClassifyParentparam\",\"collectDataclassKey\",\"postTitleParentparam\",\"collectDatapostTitleKey\",\"read_countParentparam\",\"collectDatareadKey\",\"comments_countParentparam\",\"collectDatacommentsKey\"],\"ParentParmOriUrlKey\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatasOrigUrlKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"original_url\"},\"name_ex\":null}},\"paramSource\":10},\"sourceHostParentParam\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatasourceHostKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"source_host\"},\"name_ex\":null}},\"paramSource\":10},\"columnParentParam\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatacolumnKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"column\"},\"name_ex\":null}},\"paramSource\":10},\"column1Parentparam\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatacolumn1Key\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"column1\"},\"name_ex\":null}},\"paramSource\":10},\"proClassifyParentparam\":{\"col_name\":\"proClassify\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDataclassKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"proClassify\"},\"name_ex\":null}},\"paramSource\":10},\"postTitleParentparam\":{\"col_name\":\"post_title\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatapostTitleKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"post_title\"},\"name_ex\":null}},\"paramSource\":10},\"read_countParentparam\":{\"col_name\":\"read_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatareadKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"read_count\"},\"name_ex\":null}},\"paramSource\":10},\"comments_countParentparam\":{\"col_name\":\"comments_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"collectDatacommentsKey\":{\"col_name\":\"g_collect\",\"col_type\":\"24\",\"col_name_ex\":{\"type\":1,\"data\":{\"arr_data\":{\"arr_idx\":-1,\"min_comb_eles\":null,\"max_comb_eles\":null}},\"name_ex\":{\"type\":2,\"data\":{\"chld_col_name\":\"comments_count\"},\"name_ex\":null}},\"paramSource\":10},\"path223\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path249\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path440\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path808\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path717\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path857\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path301\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path503\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path384\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path670\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path773\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path493\":{\"col_name\":\"proClassify\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path764\":{\"col_name\":\"proClassify\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path438\":{\"col_name\":\"post_title\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path937\":{\"col_name\":\"post_title\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path38\":{\"col_name\":\"read_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":5},\"path311\":{\"col_name\":\"read_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path391\":{\"col_name\":\"comments_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":5},\"path77\":{\"col_name\":\"comments_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4}},\"filters\":[],\"taskUrls\":null,\"outData\":[],\"taskGenConf\":[],\"parentTaskID\":\"1\"},\"root\":{\"taskPro\":{\"tasklevel\":\"2\",\"local\":0,\"remote\":1,\"iscommit\":\"1\",\"source\":11,\"content_type\":0,\"duration\":\"3600\",\"conflictdelay\":\"\",\"iscalctrend\":\"0\",\"dataSave\":\"0\",\"filPageTag\":\"1\",\"addUser\":\"1\",\"genCreatedAt\":\"1\",\"isGenChildTask\":\"1\",\"dictionaryPlan\":\"[[]]\",\"cronstart\":\"\",\"specifiedType\":\"\",\"specifiedMac\":\"\",\"specifiedTypeForChild\":true,\"specifiedMacForChild\":true,\"cronend\":\"\",\"remarks\":\"智机网\",\"userPathId\":\"\",\"template\":\"117\",\"importurl\":\"http://://\"},\"templMap\":{\"wfun-URLS\":[1]},\"stepNumURLPatterns\":{\"1\":[\"wfun.com/\"]},\"paramsDef_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"paramsDef\",\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{\"run_url\":{\"col_type\":\"13\",\"col_type_ex\":null},\"floorNum\":{\"col_type\":\"5\",\"col_type_ex\":null},\"source_host\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column\":{\"col_type\":\"13\",\"col_type_ex\":null},\"column1\":{\"col_type\":\"13\",\"col_type_ex\":null}}},\"name\":\"paramsDef\",\"open\":true,\"level\":0},\"parentParam_def\":null,\"runTimeParam_def\":null,\"constants_def\":null,\"outData_def\":{\"type\":\"phyfield_add\",\"id\":1,\"label\":\"outData\",\"col_type\":\"23\",\"col_type_ex\":{\"sc_map\":{\"URLS\":{\"col_type\":\"13\",\"col_type_ex\":null}}},\"name\":\"outData\",\"open\":true,\"level\":0},\"g_global_def\":null,\"URLCache_def\":null,\"TaskCache_def\":null,\"AppCache_def\":null,\"g_collect_def\":null,\"CurrPageCache_def\":null,\"g_current_def\":null,\"paramsDef\":{\"run_url\":\"http://bbs.wfun.com/s?q=%E5%9B%A0%E7%89%B9%E5%B0%94&sub=1&query=default&sort=0&fid=0&time_order=0\",\"floorNum\":0,\"source_host\":\"BBS\",\"column\":\"因特尔\",\"column1\":\"智机网\"},\"parentParam\":null,\"runTimeParam\":{\"scene\":{\"dropcount\":12}},\"constants\":null,\"loginAccounts\":{\"globalaccount\":\"0\",\"logoutfirst\":\"0\",\"isswitch\":\"0\",\"switchpage\":\"5\",\"switchtime\":\"0\",\"switchstrategy\":1,\"accounts\":[],\"globalaccounts\":\"\"},\"pathStructMap\":{\"path333\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":2},\"keys\":[],\"path223\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path249\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path440\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path808\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path717\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path857\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path301\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path503\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path384\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path670\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path773\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path493\":{\"col_name\":\"proClassify\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path764\":{\"col_name\":\"proClassify\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path438\":{\"col_name\":\"post_title\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path937\":{\"col_name\":\"post_title\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path38\":{\"col_name\":\"read_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":5},\"path311\":{\"col_name\":\"read_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path391\":{\"col_name\":\"comments_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":5},\"path77\":{\"col_name\":\"comments_count\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path642\":{\"col_name\":\"URLS\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path110\":{\"col_name\":\"URLS\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":5},\"path53\":{\"col_name\":\"run_url\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path993\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":2},\"path2\":{\"col_name\":\"floorNum\",\"col_type\":\"5\",\"col_name_ex\":null,\"paramSource\":4},\"path203\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":2},\"path96\":{\"col_name\":\"source_host\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path396\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":2},\"path199\":{\"col_name\":\"column\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4},\"path519\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":2},\"path149\":{\"col_name\":\"column1\",\"col_type\":\"13\",\"col_name_ex\":null,\"paramSource\":4}},\"filters\":[],\"taskUrls\":{\"type\":\"consts\",\"urlValues\":[\"|path333|\"]},\"outData\":[],\"taskGenConf\":[{\"dataPath\":\"\",\"splitStep\":\"1\",\"childTaskDefId\":1,\"childTaskUrl\":{\"type\":\"consts\",\"value\":{\"paramValue\":\"|path642|\",\"paramSource\":5}},\"params\":[{\"paramPath\":\"|path110|\",\"targetType\":\"0\",\"toParamPath\":\"|path53|\"},{\"paramPath\":\"|path993|\",\"toParamPath\":\"|path2|\"},{\"paramPath\":\"|path203|\",\"toParamPath\":\"|path96|\"},{\"paramPath\":\"|path396|\",\"toParamPath\":\"|path199|\"},{\"paramPath\":\"|path519|\",\"toParamPath\":\"|path149|\"}]}]}}";
		//str = stringToJson(str);
		JSONObject taskParams_json = JSONObject.fromObject(str);
		JSONObject curRootParams_json = taskParams_json.getJSONObject("root");
		String childParamId = "1",remarks = "";
		Integer taskNumPerChild = 1;
		Integer genIdx = 0;
		JSONObject childTaskParam_json = taskParams_json.getJSONObject("1");
				
		try {
			SpGrabDataHandle.chunkDeriveTask4Common(resultDatas_json, taskParams_json, curRootParams_json, childParamId, childTaskParam_json, taskNumPerChild, genIdx, remarks);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		
		/*String sss = "{\"data\":[{\"compDesMatch\":\"4.8\",\"serviceScore\":\"4.7\",\"logisticsScore\":\"4.8\"," +
				"\"post_title\":\"爱居兔2017夏季热卖女装可爱卡通图案中袖休闲衬衫\"," +
				"\"created_at\":\"2017-03-19\"," +
				"\"productType\":\"爱居兔2017夏季热卖女装可爱卡通图案中袖休闲衬衫\"," +
				"\"pro_init_price\":0,\"proOriPrice\":169,\"allRepNum\":\"12\"," +
				"\"productSize\":\"155/80A\\r\\n160/84A\\r\\n165/88A\\r\\n170/92A\"," +
				"\"productColor\":\"漂白花纹(10)\",\"stockNum\":null,\"product_no\":530663079505,\"isFavorite\":82," +
				"\"productFullName\":\"Eichitoo/H兔\"," +

				"\"text\":\"材质成分: 棉100%货号: ENEBJ2G009A服装版型: 直筒风格: 其他衣长: 常规款袖长: 七分袖袖型: 其他领型: 其他图案: 其他品牌: Eichitoo/H兔适用年龄: 25-29周岁年份季节: 2016年夏季颜色分类:\\r\\n    \\r\\n漂白花纹(10)\\r\\n                  \\r\\n尺码: 155/80A 160/84A 165/88A 170/92A\"," +

				"\"detailParam\":[\"材质成分: 棉100%\",\"货号: ENEBJ2G009A\",\"服装版型: 直筒\",\"风格: 其他\",\"衣长: 常规款\",\"袖长: 七分袖\",\"袖型: 其他\",\"领型: 其他\",\"图案: 其他\",\"品牌: Eichitoo/H兔\",\"适用年龄: 25-29周岁\",\"年份季节: 2016年夏季\",\"颜色分类: 漂白花纹(10)\",\"尺码: 155/80A 160/84A 165/88A 170/92A\"]," +
				
				"\"productDesc\":[\"材质成分: \\r\\n棉100%\",\"货号: ENEBJ2G009A\",\"服装版型: 直筒\",\"风格: 其他\",\"衣长: 常规款\",\"袖长: 七分袖\",\"袖型: 其他\",\"领型: 其他\",\"图案: 其他\",\"品牌: Eichitoo/H兔\",\"适用年龄: 25-29周岁\",\"年份季节: 2016年夏季\",\"颜色分类: 漂白花纹(10)\",\"尺码: 155/80A 160/84A 165/88A 170/92A\"]," +
				"\"satisfaction\":\"4.6\"," +
				"\"compName\":\"h兔官方旗舰店\"," +

				"\"original_url\":\"https://detail.tmall.com/item.htm?id=530663079505&areaId=110100&is_b=1&cat_id=50025135&rn=3cfaa5f1a2be445247c4c65e4c10a3a8\"," +
				"\"source_host\":\"电商\",\"column\":\"天猫h兔官方旗舰店女装大量\",\"column1\":\"天猫-旗舰店大量\",\"floor\":0}]}";
		*/
        
        
        /*String stest = "{data:[{\"page_url\":\"https://detail.tmall.com/item.htm?id=530663079505&areaId=110100&is_b=1&cat_id=50025135&rn=3cfaa5f1a2be445247c4c65e4c10a3a8\",\"data\":[{\"compDesMatch\":\"4.8\",\"serviceScore\":\"4.7\",\"logisticsScore\":\"4.8\",\"post_title\":\"爱居兔2017夏季热卖女装可爱卡通图案中袖休闲衬衫\",\"created_at\":\"2017-03-19\",\"productType\":\"爱居兔2017夏季热卖女装可爱卡通图案中袖休闲衬衫\",\"pro_init_price\":0,\"proOriPrice\":169,\"allRepNum\":\"12\",\"productSize\":\"155/80A\r\n160/84A\r\n165/88A\r\n170/92A\",\"productColor\":\"漂白花纹(10)\",\"stockNum\":null,\"product_no\":530663079505,\"isFavorite\":82,\"productFullName\":\"Eichitoo/H兔\",\"text\":\"材质成分: 棉100%货号: ENEBJ2G009A服装版型: 直筒风格: 其他衣长: 常规款袖长: 七分袖袖型: 其他领型: 其他图案: 其他品牌: Eichitoo/H兔适用年龄: 25-29周岁年份季节: 2016年夏季颜色分类: 漂白花纹(10)尺码: 155/80A 160/84A 165/88A 170/92A\",\"detailParam\":[\"材质成分: 棉100%\",\"货号: ENEBJ2G009A\",\"服装版型: 直筒\",\"风格: 其他\",\"衣长: 常规款\",\"袖长: 七分袖\",\"袖型: 其他\",\"领型: 其他\",\"图案: 其他\",\"品牌: Eichitoo/H兔\",\"适用年龄: 25-29周岁\",\"年份季节: 2016年夏季\",\"颜色分类: 漂白花纹(10)\",\"尺码: 155/80A 160/84A 165/88A 170/92A\"],\"productDesc\":[\"材质成分: 棉100%\",\"货号: ENEBJ2G009A\",\"服装版型: 直筒\",\"风格: 其他\",\"衣长: 常规款\",\"袖长: 七分袖\",\"袖型: 其他\",\"领型: 其他\",\"图案: 其他\",\"品牌: Eichitoo/H兔\",\"适用年龄: 25-29周岁\",\"年份季节: 2016年夏季\",\"颜色分类: 漂白花纹(10)\",\"尺码: 155/80A 160/84A 165/88A 170/92A\"],\"satisfaction\":\"4.6\",\"compName\":\"h兔官方旗舰店\",\"original_url\":\"https://detail.tmall.com/item.htm?id=530663079505&areaId=110100&is_b=1&cat_id=50025135&rn=3cfaa5f1a2be445247c4c65e4c10a3a8\",\"source_host\":\"电商\",\"column\":\"天猫h兔官方旗舰店女装大量\",\"column1\":\"天猫-旗舰店大量\",\"floor\":0}],\"param\":{\"runTimeParam\":{\"scene\":{\"isremote\":1,\"historystat\":null},\"run_url\":\"https://detail.tmall.com/item.htm?id=530663079505&areaId=110100&is_b=1&cat_id=50025135&rn=3cfaa5f1a2be445247c4c65e4c10a3a8\"}}}]}";
        stest = stringToJson(stest);
        JSONObject stest_json = JSONObject.fromObject(stest);
		JSONObject json_obj = SpGrabDataHandle.formatPostdata(stest_json);
		System.out.println(json_obj.getString("data"));*/
		
		// String stemp =
		// "{\"productSize\":\"155/80A\\r\\n160/84A\\r\\n165/88A\\r\\n170/92A\"}";
		// JSONObject obj = JSONObject.fromObject(stemp);
		// System.out.println(obj.toString());
		/* char c161 = 161;
	     char c162 = 162;
         char c163 = 163;
         char c169 = 169;
         System.out.println( String.valueOf(c161) + "|" +
                 String.valueOf(c162)+ "|" +
                 String.valueOf(c163)+ "|" +
                 String.valueOf(c169));*/
        /* String ss = "我的数据是&#13233;ss";
         char cha1 = '\1';
         ss = ss.replaceAll("&#(\\d+);", "chr(\\1)");
         char chaa = ' ';
         
         System.out.println( String.valueOf(chaa));*/
		
		/*String matchScore = "<span class=\"dddd\">P</span>   0 - 0 <span class=\"eeee\">P</span>";

		  Pattern p = Pattern.compile("<[^>]+>([^<]*)</[^>]+>");
		  Matcher m = p.matcher(matchScore);
		  while(m.find()){
		    matchScore = matchScore.replaceFirst("<[^>]+>([^<]*)</[^>]+>", m.group(1).toString());
		  }
		  System.out.println(matchScore);*/
		
		
		
		
	}
	
	
}
