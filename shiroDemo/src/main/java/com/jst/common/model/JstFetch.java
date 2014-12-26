package com.jst.common.model;

/**
 * hibername 查询时抓取策略类
 * 
 * @author amado
 * @date 2014-8-15
 * @Description: TODO(用一句话描述该文件做什么)
 */
public class JstFetch {
	// 抓取对象
	private String fetchObj;
	// 抓取方式
	private JstFetchModel jstFetchModel;

	public JstFetch(String fetchObj, JstFetchModel jstFetchModel) {
		this.fetchObj = fetchObj;
		this.jstFetchModel = jstFetchModel;
	}

	public String getFetchObj() {
		return fetchObj;
	}

	public void setFetchObj(String fetchObj) {
		this.fetchObj = fetchObj;
	}

	public JstFetchModel getJstFetchModel() {
		return jstFetchModel;
	}

	public void setJstFetchModel(JstFetchModel jstFetchModel) {
		this.jstFetchModel = jstFetchModel;
	}

	public enum JstFetchModel {
		DEFAULT, JOIN, SELECT;
	}
}
