package com.jst.common.service;

import java.io.Serializable;
import java.util.List;

import com.jst.common.DAO.PropertyFilter;
import com.jst.common.model.JstFetch;
import com.jst.common.model.Page;

/**
 * 
 * @author amado
 * @date 2014-7-11
 * @Description: 基础Service接口类
 */
public interface IBaseService<T> {
	public void add(T t);

	public void update(T t);

	public void delete(T t);

	public void delete(Serializable id);
	
	public void delete(Serializable[] id);
	
	public List<T> findByExample(T t);
	
	public T Query(Long id);

	public Page<T> getPage(Page<T> page, List<PropertyFilter> filters) throws Exception;
	
	public Page<T> getPage(Page<T> page, List<PropertyFilter> filters,JstFetch fetch) throws Exception;
	
	public Page<T> getPage(Page<T> page, List<PropertyFilter> filters,List<JstFetch> fetchList) throws Exception;

}
