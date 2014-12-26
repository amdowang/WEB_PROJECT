package com.jst.common.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.jst.common.DAO.IBaseDAO;
import com.jst.common.DAO.PropertyFilter;
import com.jst.common.model.JstFetch;
import com.jst.common.model.Page;
import com.jst.common.service.IBaseService;

public class BaseServiceImpl<T> implements IBaseService<T> {

	protected IBaseDAO<T> dao;
	@Override
	public void add(T t) {
		dao.save(t);
	}

	@Override
	public void update(T t) {
		dao.saveOrUpdate(t);
	}

	@Override
	public void delete(T t) {
		dao.delete(t);
	}

	@Override
	public void delete(Serializable id) {
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		dao.delete(dao.getById(c, id));
	}
	
	@Override
	public void delete(Serializable[] ids) {
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		for(Serializable id:ids){
			dao.delete(dao.getById(c, id));
		}
	}

	@Override
	public T Query(Long id) {
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return dao.getById(c, id);
	}
	@Override
	public Page<T> getPage(Page<T> page, List<PropertyFilter> filters) throws Exception {
		return dao.getList(page, filters);
	}
	@Override
	public Page<T> getPage(Page<T> page, List<PropertyFilter> filters,JstFetch fetch) throws Exception{
		return dao.getList(page, filters,fetch);
	}
	@Override
	public Page<T> getPage(Page<T> page, List<PropertyFilter> filters,List<JstFetch> fetchList) throws Exception{
		return dao.getList(page, filters,fetchList);
	}
	@Override
	public List<T> findByExample(T t) {
		return dao.findByExample(t);
	}
}
