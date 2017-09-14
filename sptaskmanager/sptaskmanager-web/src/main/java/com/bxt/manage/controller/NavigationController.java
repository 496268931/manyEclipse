package com.bxt.manage.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bxt.sptask.common.vo.AjaxSimpleResult;
import com.bxt.sptask.navgationtree.vo.NavigationVO;
import com.bxt.sptask.service.NavigationService;

/**
 * Description: NavigationController t_navigationCONTROLLER控制器 

 * All Rights Reserved.
 *
 * @version V1.0  2017-01-03 下午 04:15:14 星期二
 * @author wxd(wxd@inter3i.com)
 */
@Controller
@RequestMapping(value = "/NavigationInfo")
public class NavigationController{
    private static Logger LOGGER = LoggerFactory.getLogger(NavigationController.class);
    private static final String TYPE_TMP_VIEW = "taskmanager/seltypetmp";
	private static final String DETAIL_VIEW = "navigation/navigationDetail";
	private static final String LIST_VIEW = "navigation/navigationList";

    @Autowired
    private NavigationService navigationService;
    
    /**
     * Description: 查找t_navigation信息列表
     * @version V1.0  2017-01-03 下午 04:15:14 星期二
     * @author wxd(wxd@inter3i.com)
     * @param request HttpServletRequest
	 * @param model Model
	 * @param navigationVO
     * @return
     */
    @RequestMapping(value = "/getNavigation")
    public void getNavigationTree(NavigationVO navigationVO, HttpServletRequest request,HttpServletResponse response) {
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		String jsonobjNavgationVO = "";
    	try {
    		jsonobjNavgationVO = navigationService.getNavigationInfo("wxd");
    		try{
    			out = response.getWriter();
    			out.write(jsonobjNavgationVO.toString());
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
		} catch (Exception e) {
			LOGGER.error("t_navigation查询异常", e);
		}
    }
    
    /**
     * 显示分类管理和模板检索页面
     */
    @RequestMapping(value = "/showNavigationPage")
    public String showTaskPage(){
    	return TYPE_TMP_VIEW;
    }
    

    /**
     * Description: 删除t_navigationVO实体信息
     *
     * @version V1.0  2017-01-03 下午 04:15:14 星期二
     * @author wxd(wxd@inter3i.com)
     * @param navigationVO
     * @param request
     * @return
     */
	@RequestMapping(value = "/deleteNavigation")
	@ResponseBody
    public Object deleteNavigation(NavigationVO navigationVO, HttpServletRequest request) {
        AjaxSimpleResult result = new AjaxSimpleResult();
//        try {
//	        navigationVO.setModifyEmp(SessionUtils.getSessionUserId(request));
//            this.navigationService.deleteNavigation(navigationVO);
//            result.setMessage("删除成功！");
//        } catch (Exception e) {
//            String errMsg = "系统异常，删除失败！";
//            LOGGER.error(errMsg, e);
//            result.setStatus(AjaxSimpleResult.ERROR);
//            result.setMessage(errMsg);
//        }
        return result;
    }

    /**
     * Description: 新增或修改t_navigation信息
     *
     * @version V1.0  2017-01-03 下午 04:15:14 星期二
     * @author wxd(wxd@inter3i.com)
     * @param navigationVO
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateNavigation")
    @ResponseBody
    public Object saveOrUpdateNavigation(@ModelAttribute NavigationVO navigationVO, HttpServletRequest request) {
	    AjaxSimpleResult result = new AjaxSimpleResult();
//	    try {
//            Long userId = SessionUtils.getSessionUserId(request);
//	        navigationVO.setModifyEmp(userId);
//	        if (navigationVO.getNodeid() == null) {
//	            navigationVO.setCreateEmp(userId);
//	            navigationService.insertNavigation(navigationVO);
//	        } else {
//	            navigationService.updateNavigation(navigationVO);
//	        }
//		    result.setMessage("保存成功！");
//	    } catch (Exception e) {
//		    String errMsg = "系统异常，保存失败！";
//		    LOGGER.error(errMsg, e);
//		    result.setStatus(AjaxSimpleResult.ERROR);
//		    result.setMessage(errMsg);
//	    }
	    return result;
    }

}