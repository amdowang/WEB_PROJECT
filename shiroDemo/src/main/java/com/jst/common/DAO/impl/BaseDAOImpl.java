package com.jst.common.DAO.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.jst.common.DAO.IBaseDAO;
import com.jst.common.DAO.PropertyFilter;
import com.jst.common.DAO.PropertyFilter.MatchType;
import com.jst.common.model.JstFetch;
import com.jst.common.model.Page;
import com.jst.common.util.ReflectionUtils;

@Repository
public class BaseDAOImpl<T> implements IBaseDAO<T> {
	private final Log log = LogFactory.getLog(BaseDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 获得当前事物的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Serializable save(T o) {
		if (o != null) {
			return getCurrentSession().save(o);
		}
		return null;
	}

	@Override
	public T getById(Class<T> c, Serializable id) {
		return (T) getCurrentSession().get(c, id);
	}

	@Override
	public T getByHql(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public T getByHql(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public void delete(T o) {
		if (o != null) {
			getCurrentSession().delete(o);
		}
	}

	@Override
	public void update(T o) {
		if (o != null) {
			getCurrentSession().update(o);
		}
	}

	@Override
	public void saveOrUpdate(T o) {
		if (o != null) {
			getCurrentSession().saveOrUpdate(o);
		}
	}

	@Override
	public List<T> find(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> find(String hql, int page, int rows) {
		Query q = getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public Long count(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public int executeHql(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	public List<T> findByExample(T t) {
		log.debug("finding " + t + " instance by example");
		try {
			List<T> results = getCurrentSession().createCriteria(t.getClass()).add(Example.create(t)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List<List> findBySql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.setResultTransformer(Transformers.TO_LIST).list();

	}

	@Override
	public List<List> findBySql(String sql, int page, int rows) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.TO_LIST)
				.list();
	}

	@Override
	public List<List> findBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(Transformers.TO_LIST).list();
	}

	@Override
	public List<List> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.TO_LIST)
				.list();
	}

	@Override
	public int executeSql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public BigInteger countBySql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

	@Override
	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}

	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> getList(final Page<T> page, final List<PropertyFilter> filters, final JstFetch fetch)
			throws Exception {
		if(fetch==null){
			throw new IllegalArgumentException("fetch canot be null");
		}
		List<Criterion> criterions = new HibernateHelper().buildCriterionByPropertyFilter(filters);
		return new HibernateHelper().getPage(getClass(), page, criterions, Arrays.asList(fetch));
	}
	/**
	 * 按属性过滤条件列表分页查找对象.
	 */
	public Page<T> getList(final Page<T> page, final List<PropertyFilter> filters, final List<JstFetch> fetchList)
			throws Exception {
		List<Criterion> criterions = new HibernateHelper().buildCriterionByPropertyFilter(filters);
		return new HibernateHelper().getPage(getClass(), page, criterions, fetchList);
	}
	public Page<T> getList(final Page<T> page, final List<PropertyFilter> filters) throws Exception {
		List<Criterion> criterions = new HibernateHelper().buildCriterionByPropertyFilter(filters);
		return new HibernateHelper().getPage(getClass(), page, criterions, null);
	}

	class HibernateHelper {

		/**
		 * 按属性条件列表创建Criterion数组,辅助函数.
		 */
		private List<Criterion> buildCriterionByPropertyFilter(final List<PropertyFilter> filters) {
			List<Criterion> criterionList = new ArrayList<Criterion>();
			for (PropertyFilter filter : filters) {
				Criterion criterion = buildCriterion(filter.getFieldName(), filter.getMatchValue(),
						filter.getMatchType());
				criterionList.add(criterion);
			}
			return criterionList;
		}

		/**
		 * 按属性条件参数创建Criterion,辅助函数.
		 */
		private Criterion buildCriterion(final String propertyName, final Object propertyValue,
				final MatchType matchType) {
			Assert.hasText(propertyName, "propertyName不能为空");
			Criterion criterion = null;
			// 根据MatchType构造criterion
			switch (matchType) {
			case EQ:
				criterion = Restrictions.eq(propertyName, propertyValue);
				break;
			case LIKE:
				criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
				break;
			case LE:// 小于
				criterion = Restrictions.le(propertyName, propertyValue);
				break;
			case LT:// 小于等于
				criterion = Restrictions.lt(propertyName, propertyValue);
				break;
			case GE:// 大于
				criterion = Restrictions.ge(propertyName, propertyValue);
				break;
			case GT:// 大于等于
				criterion = Restrictions.gt(propertyName, propertyValue);
				break;
			case NE: // 不等于
				criterion = Restrictions.ne(propertyName, propertyValue);
				break;
			case ILIKE:// 模糊查询不分大小写
				criterion = Restrictions.ilike(propertyName, propertyValue);
				break;
			case IN:// IN
				criterion = Restrictions.in(propertyName, (Collection) propertyValue);
				break;
			case BETWEEN:// BETWEEN
				criterion = Restrictions.between(propertyName, ((List) propertyValue).get(0),
						((List) propertyValue).get(1));
				break;
			}
			return criterion;
		}

		/**
		 * 按Criteria分页查询.
		 * 
		 * @param page
		 *            分页参数.
		 * @param criterions
		 *            数量可变的Criterion.
		 * 
		 * @return 分页查询结果.附带结果列表及所有查询输入参数.
		 */
		@SuppressWarnings("unchecked")
		private Page<T> getPage(final Class zlass, final Page<T> page, final List<Criterion> criterions,
				final List<JstFetch> fetchList) throws Exception {
			Assert.notNull(page, "page不能为空");

			Criteria c = createCriteria(zlass, criterions);
			// 查询分页
			if (page.isAutoCount()) {
				long totalCount = countCriteriaResult(c);
				page.setTotalCount(totalCount);
			}
			// 抓取信息
			if (fetchList != null && !fetchList.isEmpty()) {
				for(JstFetch tmpFetch:fetchList){
					switch (tmpFetch.getJstFetchModel()) {
					case JOIN:
						c.setFetchMode(tmpFetch.getFetchObj(), FetchMode.JOIN);
						break;
					case SELECT:
						c.setFetchMode(tmpFetch.getFetchObj(), FetchMode.SELECT);
						break;
					case DEFAULT:
						c.setFetchMode(tmpFetch.getFetchObj(), FetchMode.DEFAULT);
						break;
					default:
						throw new IllegalArgumentException("argument not in (JOIN,SELECT,DEFAULT)");
					}
				}
			}
			setPageParameterToCriteria(c, page);
			List<T> result = c.list();
			page.setResult(result);
			return page;
		}

		private Criteria createCriteria(Class zlass, final List<Criterion> criterions) throws Exception {
			// ParameterizedType pt = (ParameterizedType)
			// this.getClass().getGenericSuperclass();
			// Class<T> modelClass = (Class) pt.getActualTypeArguments()[0];

			Criteria criteria = getCurrentSession().createCriteria(ReflectionUtils.getSuperClassGenricType(zlass));
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			return criteria;
		}

		/**
		 * 执行count查询获得本次Criteria查询所能获得的对象总数.
		 */
		@SuppressWarnings("unchecked")
		private long countCriteriaResult(final Criteria c) throws Exception {
			CriteriaImpl impl = (CriteriaImpl) c;

			// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
			Projection projection = impl.getProjection();
			ResultTransformer transformer = impl.getResultTransformer();

			List<CriteriaImpl.OrderEntry> orderEntries = null;
			try {
				orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
				ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
			} catch (Exception e) {
				log.error("countCriteriaResult error", e);
				throw e;
			}

			// 执行Count查询
			Long totalCountObject = new Long(c.setProjection(Projections.rowCount()).uniqueResult().toString());
			long totalCount = (totalCountObject != null) ? totalCountObject : 0;

			// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
			c.setProjection(projection);

			if (projection == null) {
				c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			}
			if (transformer != null) {
				c.setResultTransformer(transformer);
			}
			try {
				ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
			} catch (Exception e) {
				log.error("countCriteriaResult error", e);
				throw e;
			}

			return totalCount;
		}

		/**
		 * 设置分页参数到Criteria对象,辅助函数.
		 */
		private Criteria setPageParameterToCriteria(final Criteria c, final Page<T> page) {

			Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

			// hibernate的firstResult的序号从0开始

			c.setFirstResult((page.getPageNo() - 1) * page.getPageSize());
			c.setMaxResults(page.getPageSize());

			if (page.getOrderBy() != null && page.getOrderBy().trim().length() > 0) {
				String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
				String[] orderArray = StringUtils.split(page.getOrder(), ',');

				Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

				for (int i = 0; i < orderByArray.length; i++) {
					if (Page.ASC.equals(orderArray[i])) {
						c.addOrder(Order.asc(orderByArray[i]));
					} else {
						c.addOrder(Order.desc(orderByArray[i]));
					}
				}
			}
			return c;
		}
	}

}
