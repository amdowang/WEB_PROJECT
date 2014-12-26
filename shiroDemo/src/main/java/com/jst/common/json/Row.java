package com.jst.common.json;

import net.sf.json.JSONObject;

/**
 * @author lee
 * @see 表单查询行记录对象
 * @param row 行记录存储对象
 */
public class Row{
	
	private JSONObject row;
	
	public Row(){
		row = new JSONObject();
	}
	
	public JSONObject getRow() {
		return row;
	}

	public void addCell(String key, Object value){
		row.accumulate(key, value);
	}
	
}