package com.tjport.common.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

import com.tjport.common.query.Page;
import com.tjport.common.query.PropertyFilterParam;
import com.tjport.common.query.QueryFilter;

public interface IBaseDao<T, PK extends Serializable> {

	T load(PK id);

	T get(PK id);

	List<T> findAll();

	void persist(T entity);

	PK save(T entity);

	void update(T entity);

	void saveOrUpdate(T entity);

	void delete(PK id);

	void flush();

	Query createQuery(final String queryString, final Object... values);

	Query createQuery(final String orderBy, final String order, final String queryString, final Object... values);

	<X> X findUnique(final String hql, final Object... values);

	long countHqlResult(final String hql, final Object... values);

	Page<T> findPage(final Page<T> page, final String whereHql, final Object... values);

	Page<T> findPage(final Page<T> page, final PropertyFilterParam filterParam);
	
	Page<T> findPage(final Page<T> page, final QueryFilter queryFilter);
	
	Page<T> findPage(final Page<T> page, final PropertyFilterParam filterParam, final String whereHql, final Object... values);
	
	Page<T> findPage(final Page<T> page, final QueryFilter queryFilter, final String whereHql, final Object... values);
	
	Page<T> getAll(final Page<T> page);

	List<T> findAll(String orderBy, String order);
	
    List<T> find(final String whereHql, final Object... values);
    
    List<T> find(final String orderBy, final String order, final String whereHql, final Object... values);
}
