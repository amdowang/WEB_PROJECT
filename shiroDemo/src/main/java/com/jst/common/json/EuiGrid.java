package com.jst.common.json;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author lee
 * @see 封装EasyUI所需JSON实例
 * @param rowCount 记录总数
 * @param rows 查询记录
 */
public class EuiGrid {
	
	private long rowCount;
	
	private JSONArray rows;
	
	
	//构造方法
	public EuiGrid(){
		//初始化对象
		rows = new JSONArray();
	}
	
	//设置查询记录总数
	public void setRowCount(long rowCount){
		this.rowCount = rowCount;
	}
	
	//创建行记录对象
	public Row createRowInstance(){
		return new Row();
	}
	
	//增加一条行记录
	public void addRow(Row row){
		rows.add(row.getRow());
	}
	
	//一次性添加所有查询记录，前提是该集合所有元素为查询实例的对象
	public void addRows(List<?> recordList){
		rows.addAll(recordList);
	}
	
	//重写toString方法，返回符合EasyUI所需的JSON格式字符串
	public String toString() {
		JSONObject json = new JSONObject();
		
		json.accumulate("total", rowCount);
		json.accumulate("rows", rows);
		
		return json.toString();
	}
	public static String toSuccessMsg() {
		JSONObject json = new JSONObject();
		json.accumulate("success", "true");
		return json.toString();
	}
	public static String toSuccessMsg(String message) {
		JSONObject json = new JSONObject();
		json.accumulate("success", "true");
		json.accumulate("message", message);
		return json.toString();
	}
	public static String toErrorMsg(String message) {
		JSONObject json = new JSONObject();
		json.accumulate("message", message);
		return json.toString();
	}
}
