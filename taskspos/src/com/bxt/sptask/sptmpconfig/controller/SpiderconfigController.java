package com.bxt.sptask.sptmpconfig.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bxt.sptask.sptmpconfig.service.SpiderconfigService;
import com.bxt.sptask.sptmpconfig.service.TaskcfgtempService;
import com.bxt.sptask.sptmpconfig.vo.SchSpiderconfigVO;

import net.sf.json.JSONObject;

/**
 * Description: SpiderconfigController spiderconfigCONTROLLER控制器 

 * All Rights Reserved.
 *
 * @version V1.0  2017-01-06 下午 05:09:36 星期五
 * @author wxd(wxd@inter3i.com)
 */
@Controller
@RequestMapping(value = "/SpiderconfigController")
public class SpiderconfigController {
    private static Logger LOGGER = LoggerFactory.getLogger(SpiderconfigController.class);

	private static final String DETAIL_VIEW = "model/submodel/spiderconfigDetail";
	private static final String LIST_VIEW = "model/submodel/spiderconfigList";

    @Autowired
    private SpiderconfigService spiderconfigService;
    
    @Autowired
    private TaskcfgtempService taskcfgtempService;


    /**
     * Description: 查找spiderconfig信息列表
     * @version V1.0  2017-01-06 下午 05:09:36 星期五
     * @author wxd(wxd@inter3i.com)
     * @return
     */
    @RequestMapping(value = "/schSpiderConfig")
    public void searchSpiderconfigList(SchSpiderconfigVO schSpiderconfigVO, HttpServletRequest request,HttpServletResponse response) {
    	response.setContentType("application/json;charset=utf-8");
    	int icount = 0;
		PrintWriter out = null;
		
		List<SchSpiderconfigVO> schSpc_list = null;
		Integer iSptmpCount = null;
    	Integer isptmpCurrentPage = null;
    	Integer isptmpTotalPage = null;
    	JSONObject jsonobjSptmpVo =null;
    	try {
			// 查询列表数据及其记录数
    		schSpc_list = spiderconfigService.searchSpiderconfig(schSpiderconfigVO);
    		if(schSpc_list != null && schSpc_list.size() > 0){
    			//命中数
	    		iSptmpCount = spiderconfigService.searchSpiderconfigCount(schSpiderconfigVO);
	        	
	        	schSpiderconfigVO.getPageHandle().setTotalResult(iSptmpCount);
	        	//当前页
	        	isptmpCurrentPage = schSpiderconfigVO.getPageHandle().getCurrentPage();
	        	//总页数
	        	isptmpTotalPage = schSpiderconfigVO.getPageHandle().getTotalPage();
	        	
	        	jsonobjSptmpVo = new JSONObject();
	        	jsonobjSptmpVo.put("totalResult", iSptmpCount);
	        	jsonobjSptmpVo.put("currentPage", isptmpCurrentPage);
	        	jsonobjSptmpVo.put("totalPage", isptmpTotalPage);
	        	jsonobjSptmpVo.put("sptmplist", schSpc_list);
    		}else{
    			jsonobjSptmpVo = new JSONObject();
    			jsonobjSptmpVo.put("error", "检索结果为空");
    		}
        	try{
    			out = response.getWriter();
    			out.write(jsonobjSptmpVo.toString());
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
		} catch (Exception e) {
			LOGGER.error("spiderconfig查询异常", e);
		}
    }
    
    @RequestMapping(value = "/getParamesTaskTree")
    public void getParamesTaskTree(HttpServletRequest request,HttpServletResponse response){
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		String jsonTaskcfgtempVO = "";
		try{
			jsonTaskcfgtempVO = taskcfgtempService.getTaskcfgtempInfo();
			out = response.getWriter();
			out.write(jsonTaskcfgtempVO.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    
}