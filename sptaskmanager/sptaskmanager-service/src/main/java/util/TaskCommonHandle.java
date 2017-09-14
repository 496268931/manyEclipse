package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

import com.bxt.sptask.taskhandle.vo.TaskVo;
import com.bxt.sptask.utils.DateTimeUtil;
import com.bxt.sptask.utils.DefParamUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 处理任务
 */
public class TaskCommonHandle {
	
	//初始化运行参数
	public static JSONObject initRunTimeParams(TaskVo taskvo,JSONObject curRootParams_json,String hostname){
		Long  lunixTime = DateTimeUtil.getUnixTimeBySecond();
		Long duration = 0l;
		taskvo.setStarttime(lunixTime.intValue());
		taskvo.setDatastatus(0);
		taskvo.setMachine(hostname);
		JSONObject taskPro_json =  curRootParams_json.getJSONObject("taskPro");
		try{
			duration = lunixTime + Long.parseLong(taskPro_json.getString("duration"));
		}catch(Exception e){
			duration = lunixTime + 3600l;
		}
		taskvo.setTimeout(duration.intValue());//.put("timeout", duration);

		//初始化爬虫运行时参数
		if(!curRootParams_json.containsKey("runTimeParam")){
			curRootParams_json.put("runTimeParam", new JSONObject());
		}else if(curRootParams_json.getString("runTimeParam").equals("null")){
			curRootParams_json.put("runTimeParam", new JSONObject());
		}
		
		JSONObject runTimeParam = (JSONObject) curRootParams_json.get("runTimeParam");
		if(runTimeParam.containsKey("followpost")){
			runTimeParam.put("followpost", (new JSONArray()));
		}

		if(runTimeParam.containsKey("scene")){
			runTimeParam.remove("scene");
		}
		if(!runTimeParam.containsKey("scene")){
			runTimeParam.put("scene",(new JSONObject()));
		}
		
		JSONObject sceneJson = (JSONObject) runTimeParam.get("scene");
		if(!sceneJson.containsKey("isremote")){
			sceneJson.put("isremote",1);
		}else{
			sceneJson.put("isremote",1);
		}
		
		if(!sceneJson.containsKey("historystat")){
			sceneJson.put("historystat","null");
		}
		
		runTimeParam.put("scene", sceneJson);
		curRootParams_json.put("runTimeParam", runTimeParam);
		
		return curRootParams_json;
	}
	
	public static JSONObject unionTaskTemplate(TaskVo taskvo,JSONObject curRootParams_json,JSONObject tmplate_json,JSONObject task_json) throws Exception{
		if(tmplate_json != null && !tmplate_json.equals("")){
			String seol = "";
			if(tmplate_json.getString("content").indexOf("\r\n") > 0){
				seol = "\r\n";
			}else{
				seol = "\n";
			}
/**更新模板urlregex参数*/	
			String urlregex = tmplate_json.getString("urlregex");
			String[] arurlregex = null;
			if(urlregex != null && !urlregex.equals("")){
				 arurlregex = urlregex.split("[\\r\n,\\s]+");
				 task_json.put("urlregex", arurlregex);
			}
/**更新模板detailurlregex参数*/
			String sdetailurlregex = tmplate_json.getString("detailurlregex");
			String[] arDetailrulregex = null;
			if(sdetailurlregex != null && !sdetailurlregex.equals("")){
				arDetailrulregex = sdetailurlregex.split("[\\r\n,\\s]+");
				task_json.put("detailurlregex", arDetailrulregex);
			}
			
			String constantsString = "";
			if(curRootParams_json.containsKey("constants") && curRootParams_json.get("constants") != null && 
					!curRootParams_json.getString("constants").equals("null")){
				JSONObject constantsJson = (JSONObject)curRootParams_json.get("constants");
				String sConstants = DefParamUtil.defparamIntAndString(constantsJson);
				if(sConstants != null && !sConstants.equals("")){
					constantsString = "#CONST:{" + sConstants + "}" + seol;
					task_json.put("config", constantsString);
				}else{
					task_json.put("config", "");
				}
			}else{
				task_json.put("config", "");
			}
/**更新模板参数*/			
			if(task_json.containsKey("config") && task_json.getString("config") != null){
				task_json.put("config", task_json.getString("config") + tmplate_json.getString("content"));
			}else{
				task_json.put("config", tmplate_json.getString("content"));
			}
			
			/**考虑一个任务中配置了多个模版文件,设置任务中步骤和所使用的模版之间的映射关系*/
			JSONObject templMapJson = new JSONObject();
			if(curRootParams_json.containsKey("templMap") && (curRootParams_json.get("templMap") != null)){
				templMapJson = (JSONObject)curRootParams_json.get("templMap");
				task_json.put("templMap", templMapJson);
			}
			JSONObject stepNumUrlPatJson = new JSONObject();
			if(curRootParams_json.containsKey("stepNumURLPatterns") && (curRootParams_json.get("stepNumURLPatterns") != null)){
				stepNumUrlPatJson = (JSONObject)curRootParams_json.get("stepNumURLPatterns");
				task_json.put("stepNumURLPatterns",stepNumUrlPatJson);
			}
			
			String sUrl = DefParamUtil.getUrlFromTaskParam(curRootParams_json);
			task_json.put("url", sUrl);
			return task_json;
		}
		return task_json;
	} 
	
	/**
	 * 数据存盘配置
	 * @param curRootParams_json
	 * @return JSONObject
	 * @throws Exception
	 */
	public static JSONObject getSpToCacheUrl(JSONObject curRootParams_json,JSONObject task_json) throws Exception{
		//保存到对应数据库的url
		String sUrl = DefParamUtil.getUrlFromTaskParam(curRootParams_json);
		sUrl = sUrl.replaceAll("[/]", "\\\\/");
		//escapeUnicode(sUrl);
		//sUrl = sUrl.replaceAll("[/]", "\\\\/");
		// Matcher m =Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(sUrl);
		//sUrl = StringEscapeUtils.escapeXml11(sUrl);
		task_json.put("url", sUrl);
		return task_json;
	}
	
	public static JSONObject getDefTaskParams(TaskVo taskvo,JSONObject curRootParams_json,JSONObject task_json,String hostname){
		try{
			//爬虫下载完成数据后调用该接口提交数据
			String psSaveDateUrl = "http://" + hostname + "/sptaskmanager-web";
			task_json.put("importurl", psSaveDateUrl);//设置爬虫提交地址
			
			//设置传递的参数 -->运行时参数
			if(curRootParams_json.containsKey("runTimeParam") && curRootParams_json.get("runTimeParam") != null){
				task_json.put("runTimeParam", curRootParams_json.getJSONObject("runTimeParam"));
			}
			//设置传递的参数 -->数据定义
			if(curRootParams_json.containsKey("paramsDef") && curRootParams_json.get("paramsDef") != null){
				task_json.put("paramsDef", curRootParams_json.getJSONObject("paramsDef"));
			}
		    //设置传递的参数 -->来自父任务的参数
	        if(curRootParams_json.containsKey("parentParam") && curRootParams_json.get("parentParam") != null){
				task_json.put("parentParam", curRootParams_json.getJSONObject("parentParam"));
			}
	        //设置参数取值路径定义
	        if(curRootParams_json.containsKey("pathStructMap") && curRootParams_json.get("pathStructMap") != null){
				task_json.put("pathStructMap", curRootParams_json.getJSONObject("pathStructMap"));
			}
	        
	        task_json.put("id",taskvo.getId());
	        task_json.put("version","NEW-VERSION");
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return task_json;
	}
	
	
	public static void main(String[] args) {
		
	}

}
