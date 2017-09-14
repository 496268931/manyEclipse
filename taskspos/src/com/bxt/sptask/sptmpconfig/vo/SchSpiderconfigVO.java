package com.bxt.sptask.sptmpconfig.vo;

import com.bxt.sptask.common.entity.PageHandle;

public class SchSpiderconfigVO {
	private Integer id; // 配置ID
    private String content; // 配置内容
    private String detailurlregex; // 详情url正则
    private String name; // 配置名称
    private Integer templatetype; // 内容类型1.文章列表2.文章详情3.用户详情4.文章列表＋文章详情＋用户详情
    private String urlconfigrule; // 生成url的配置json
    private String urlregex; // 分析抓取url正则表达式
    
	//分页字段
	private PageHandle pageHandle;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDetailurlregex() {
		return detailurlregex;
	}
	public void setDetailurlregex(String detailurlregex) {
		this.detailurlregex = detailurlregex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTemplatetype() {
		return templatetype;
	}
	public void setTemplatetype(Integer templatetype) {
		this.templatetype = templatetype;
	}
	public String getUrlconfigrule() {
		return urlconfigrule;
	}
	public void setUrlconfigrule(String urlconfigrule) {
		this.urlconfigrule = urlconfigrule;
	}
	public String getUrlregex() {
		return urlregex;
	}
	public void setUrlregex(String urlregex) {
		this.urlregex = urlregex;
	}
	public PageHandle getPageHandle() {
		return pageHandle;
	}
	public void setPageHandle(PageHandle pageHandle) {
		this.pageHandle = pageHandle;
	}
	
	
}
