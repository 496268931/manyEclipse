package com.bxt.sptask.common.holder;

import java.io.Serializable;
import java.util.Map;

import org.springframework.context.ApplicationContext;

public class ApplicationContextHolder implements Serializable {
	private static final long serialVersionUID	= -3834011279732775327L;
	private static final ApplicationContextHolder CONTEXT_HOLDER = new ApplicationContextHolder();
	
	private ApplicationContext	context;
	private ApplicationContextHolder() {
	}
	
	public ApplicationContext getContext() {
		return context;
	}
	
	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	
	/**
	 * Description: 获取Bean的对象实例<br>
	 * 
	 * @Version1.0 2014-4-29 下午1:47:15 by ly（ly@chinazrbc.com）创建
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return ApplicationContextHolder.getInstance().getContext().getBean(beanName);
	}
	
	/**
	 * Description:获取单例对象 <br>
	 * 
	 * @Version1.0 2014-4-29 下午1:46:38 by ly（ly@chinazrbc.com）创建
	 * @return
	 */
	public static ApplicationContextHolder getInstance() {
		return CONTEXT_HOLDER;
	}
	
	/**
	 * Description: 按类型获取Bean的对象实例<br/>
	 * 
	 * @Version1.0 14-4-29 下午3:55 by ff(ff@chinazrbc.com)创建
	 * @return
	 */
	public static <T> T getType(Class<T> clazz) {
		Map<String, T> beansOfType = ApplicationContextHolder.getInstance().getContext().getBeansOfType(clazz);
		if (beansOfType == null) {
			throw new RuntimeException("获取对象失败, 没有找到该类型对象");
		}
		if (beansOfType.values().size() > 1) {
			throw new RuntimeException("获取对象失败, 存在多个实现");
		}
		return beansOfType.values().iterator().next();
	}
	
}
