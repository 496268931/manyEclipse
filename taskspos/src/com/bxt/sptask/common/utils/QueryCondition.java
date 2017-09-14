package com.bxt.sptask.common.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Description: 控制分页和排序,vo辅助类 All Rights Reserved.
 * 
 * @version 1.0 14-4-22 下午4:26 by ff(ff@chinazrbc.com)创建
 */
public abstract class QueryCondition implements Serializable {
	private static final long	serialVersionUID	= -7400992390475109807L;
	/**
	 * 起始记录
	 */
	private Integer	            startIndex	     = 0;
	/**
	 * 结束记录
	 */
	private Integer	            endIndex;
	/**
	 * 每页记录数
	 */
	private Integer	            size	         = Integer.MAX_VALUE;
	/**
	 * 排序字段
	 */
	private String	            multiColumnSort;
	/**
	 * 数据库字段与属性映射
	 */
	private Map<String, Object>	mapping	         = new HashMap<String, Object>();
	
	private String	            defaultSort;
	
	public Integer getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	
	public Integer getEndIndex() {
		return endIndex;
	}
	
	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}
	
	public String getMultiColumnSort() {
		return multiColumnSort == null ? getDefaultSort() : multiColumnSort;
	}
	
	public void setMultiColumnSort(String multiColumnSort) {
		this.multiColumnSort = multiColumnSort;
	}
	
	public Map<String, Object> getMapping() {
		return mapping;
	}
	
	public void setMapping(Map<String, Object> mapping) {
		this.mapping = mapping;
	}
	
	public String getDefaultSort() {
		return defaultSort == null ? "" : " ORDER BY " + defaultSort;
	}
	
	public void setDefaultSort(String defaultSort) {
		this.defaultSort = defaultSort;
	}
	
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, false);
	}
}
