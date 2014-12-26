/**
 * Copyright (c) 2005-20010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: PropertyFilter.java 1205 2010-09-09 15:12:17Z calvinxiu $
 */
package com.jst.common.DAO;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * 与具体ORM实现无关的属性过滤条件封装类, 主要记录页面中简单的搜索过滤条件.
 * 
 * @author calvin
 */
public class PropertyFilter {

	/** 多个属性间OR关系的分隔符. */
	public static final String OR_SEPARATOR = "_OR_";

	/** 属性比较类型. */
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE, NE, ILIKE, IN, BETWEEN;
	}

	/** 属性数据类型. */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

		private Class<?> clazz;

		private PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	// 属性
	private String fieldName = null;
	// 匹配类型
	private MatchType matchType = null;
	// 匹配值
	private Object matchValue = null;

	private Class<?> propertyClass = null;
	private String[] propertyNames = null;

	public PropertyFilter() {
	}

	/**
	 * values为具体类型值的构造函数
	 * 
	 * @param fieldName
	 *            属性名
	 * @param matchType
	 *            匹配类型 {@link MatchType}
	 * @param values
	 *            值数组，MatchType为BETWEEN类型时，长度必须是2，其他为1，值必须是具体类型的值，
	 *            如果是字符串需要转换类型，见另一个构造函数
	 *            {@link #PropertyFilter(String, MatchType, FieldType, Object)}
	 */
	public PropertyFilter(final String fieldName, MatchType matchType, Object values) {
		this.fieldName = fieldName;
		this.matchType = matchType;
		this.matchValue = values;
	}

	/**
	 * 
	 * values值需要转换类型的构造函数
	 * 
	 * @param fieldName
	 *            属性名
	 * @param matchType
	 *            匹配类型 {@link MatchType}
	 * @param fieldType
	 *            属性的类型，value将被转换到此类型
	 * @param values
	 *            值数组,BETWEEN类型时，长度必须是2，其他为1，值必须是具体类型的值， 如果是字符串需要转换类型，见另一个构造函数
	 *            {@link #FieldFilter(String, MatchType, FieldType, Object)}
	 */
	public PropertyFilter(final String fieldName, MatchType matchType, PropertyType fieldType, Object values) {
		this.fieldName = fieldName;
		this.matchType = matchType;
		Assert.notNull(values);
		this.matchValue = org.apache.commons.beanutils.ConvertUtils.convert(values, fieldType.getValue());
	}

	/**
	 * 获取比较值的类型.
	 */
	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	/**
	 * 获取比较方式.
	 */
	public MatchType getMatchType() {
		return matchType;
	}

	/**
	 * 获取比较值.
	 */
	public Object getMatchValue() {
		return matchValue;
	}

	/**
	 * 获取比较属性名称列表.
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}
	
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 获取唯一的比较属性名称.
	 */
	public String getPropertyName() {
		Assert.isTrue(propertyNames.length == 1, "There are not only one property in this filter.");
		return propertyNames[0];
	}

	/**
	 * 是否比较多个属性.
	 */
	public boolean hasMultiProperties() {
		return (propertyNames.length > 1);
	}
}
