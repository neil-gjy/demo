package com.tjport.common.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.tjport.common.query.Page;
import com.tjport.common.query.PropertyFilterParam;
import com.tjport.common.query.QueryFilter;
import com.tjport.common.query.Rule;
import com.tjport.common.utils.StringUtils;






@Repository
public class BaseDaoImpl <T, PK extends Serializable> implements IBaseDao<T, PK > {

	private Class<T> entityClass;  
	
	@Autowired
    private SessionFactory sessionFactory;  
    

	public BaseDaoImpl() {  
        this.entityClass = null;  
        Class<?> c = getClass();  
        Type type = c.getGenericSuperclass();  
        if (type instanceof ParameterizedType) {  
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();  
            this.entityClass = (Class<T>) parameterizedType[0];  
        }  
    }  
	
	private Session getSession() {  
        return sessionFactory.getCurrentSession();  
    }  
	
	@Transactional(readOnly=true)
	@Override
	public T load(PK id) {
		// TODO Auto-generated method stub
		
		return (T) getSession().load(entityClass, id);
		
		
	}
	
	@Transactional(readOnly=true)
	@Override
	public T get(PK id) {
		// TODO Auto-generated method stub
		
		return (T) getSession().get(entityClass, id);  
	}

	@Transactional(readOnly=true)
	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		String queryString = "from " + entityClass.getName(); 
		return (List<T>) this.getSession().createQuery(queryString).list();
	}

	@Transactional(readOnly=true)
	@Override
	public List<T> findAll(final String orderBy , final String order) {
		// TODO Auto-generated method stub
		String queryString = "from " + entityClass.getName(); 
		
		Query query = createQuery(orderBy, order, queryString);
		
		return query.list();
	}
	
	@Override 
	public void persist(T entity) {
		// TODO Auto-generated method stub
		this.getSession().persist(entity);
	}

	@Transactional
	@Override
	public PK save(T entity) {
		// TODO Auto-generated method stub
		return (PK) this.getSession().save(entity);
	}

	
	@Transactional
	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		this.getSession().update(entity);
	}
	
	@Transactional
	@Override
	public void saveOrUpdate(T entity) {
		// TODO Auto-generated method stub
		this.getSession().saveOrUpdate(entity);
	}

	@Transactional
	@Override
	public void delete(PK id) {
		// TODO Auto-generated method stub
		T entity = this.get(id);
		this.getSession().delete(entity);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		this.getSession().flush();
	}
	

	@Transactional
    public List<T> find(final String whereHql, final Object... values){
		
		StringBuilder stringBuilder = new StringBuilder();
		
		String hql = "from " + this.entityClass.getName() + " o " ;
		stringBuilder.append(hql);
		
        if(StringUtils.isNoneBlank(whereHql)){
        	stringBuilder.append("where " + whereHql);
        }
		
		List<T> list = createQuery(stringBuilder.toString(), values).list();
		
		return list;
	} 
 
	@Transactional
    public List<T> find(final String orderBy , final String order, final String whereHql, final Object... values){
		
		StringBuilder stringBuilder = new StringBuilder();
		
		String hql = "from " + this.entityClass.getName() + " o " ;
		stringBuilder.append(hql);
		
        if(StringUtils.isNoneBlank(whereHql)){
        	stringBuilder.append("where " + whereHql);
        }
		
		List<T> list = createQuery(orderBy, order, stringBuilder.toString(), values).list();
		
		return list;
	} 
    
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		
		
		return query;
	}
	
	
	
	public Query createQuery(final String orderBy , final String order, final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(queryString);
		
		if (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order)) {
			String[] orderByArray = StringUtils.split(orderBy, ',');
			String[] orderArray = StringUtils.split(order, ',');

			Assert.isTrue(orderByArray.length == orderArray.length,
					"分页多重排序参数中,排序字段与排序方向的个数不相等");

			stringBuilder.append(" order by ");
			for (int i = 0; i < orderByArray.length; i++) {
				stringBuilder.append(orderByArray[i] + " " + orderArray[i]);
				
				if (i != orderByArray.length - 1) {
					 stringBuilder.append(", ");
				}
			}
		}
		
		Query query = getSession().createQuery(stringBuilder.toString());
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	@Transactional(readOnly=true)
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) createQuery(hql, values).uniqueResult();
	}
	
	
	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	
	@Transactional(readOnly=true)
    public long countHqlResult(final String hql, final Object... values) {
		String countHql = prepareCountHql(hql);

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}
    
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public Page<T> findPage(final Page<T> page, final String whereHql, final Object... values) {
		Assert.notNull(page, "page不能为空");
		
		String hql = "from " + this.entityClass.getName() + " o";
		
		if (StringUtils.isNotBlank(whereHql)){ 
			hql += " where " + whereHql;
		}
		
		Query q;
		
		if (StringUtils.isNotBlank(page.getOrderBy()) && StringUtils.isNotBlank(page.getOrder())) {
			q= createQuery(page.getOrderBy(), page.getOrder(), hql, values);
		}
		else
		{
			q = createQuery(hql, values);
		}
		

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameterToQuery(q, page);

		List<T> result = q.list();
		page.setResult(result);
		return page;
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public Page<T> findPage(final Page<T> page, final PropertyFilterParam filterParam) {
		// TODO Auto-generated method stub
			
		return this.findPage(page, filterParam.getHql(), filterParam.getParam().toArray());
	}
	
	@Transactional(readOnly=true)
	public Page<T> findPage(final Page<T> page, final QueryFilter queryFilter) {
		Assert.notNull(page, "page不能为空");

		PropertyFilterParam propertyFilterParam = this.buildPropertyFilterParam(queryFilter);
		
		
		return this.findPage(page, propertyFilterParam);
	}
	
	@Transactional(readOnly=true)
	public Page<T> getAll(final Page<T> page) {
			
		return findPage(page, "");
	}
	
	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
    private Query setPageParameterToQuery(final Query q, final Page<T> page) {
		Assert.isTrue(page.getPageSize() > 0, "Page Size must larger than zero");

		// hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}
    
	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(o) " + fromHql;
		return countHql;
	}
	
	private PropertyFilterParam buildPropertyFilterParam(QueryFilter filter) {
		
		PropertyFilterParam filterParam = new PropertyFilterParam();
		StringBuilder stringBuilder = new StringBuilder();
		
		Rule[] rules = filter.getRules();
	      
		stringBuilder.append(BuildRule(rules[0]));		
        for (int i = 1; i < rules.length; i++)
        {
        	stringBuilder.append(filter.getGroupOp() + " " + BuildRule(rules[i]));
        }
        
        filterParam.setHql(stringBuilder.toString());
        filterParam.setParam(getParams(filter));
		
		return filterParam;
		
		 
	}
	
	 private String BuildRule(Rule rule)
     {
         StringBuilder ruleString = new StringBuilder();
         String[] splits;
         
         switch (rule.getOp())
         { 
             case "eq":
                 ruleString.append(rule.getField() + " = ? ");
                 return ruleString.toString();
             case "ne":
                 ruleString.append(rule.getField() + " <> ? ");
                 return ruleString.toString();
             case "lt":
                 ruleString.append(rule.getField() + " < ? ");
                 return ruleString.toString();
             case "le":
                 ruleString.append(rule.getField() + " <= ? ");
                 return ruleString.toString();
             case "gt":
                 ruleString.append(rule.getField() + " > ? ");
                 return ruleString.toString();
             case "ge":
                 ruleString.append(rule.getField() + " >= ? ");
                 return ruleString.toString();
             case "bw":
                 ruleString.append(rule.getField() + " like ? ");
                 return ruleString.toString();
             case "bn":
                 ruleString.append(rule.getField() + " not like ? ");
                 return ruleString.toString();
             case "in":
                 splits = StringUtils.split(rule.getData(), ',');   // in 查询操作可能包含多个参数，需分解处理
                 
                 ruleString.append(rule.getField() + " in ( ?");

                 for (int i = 1; i < splits.length; i++)
                 {
                     ruleString.append(", ?");
                 }

                 ruleString.append(")");
                 return ruleString.toString();
             case "ni":
                 splits = StringUtils.split(rule.getData(), ',');
                 
                 ruleString.append(rule.getField() + " not in ( ?");

                 for (int i = 1; i < splits.length; i++)
                 {
                     ruleString.append(", ?");
                 }
                 ruleString.append(")");
                 return ruleString.toString();
             case "ew":
                 ruleString.append(rule.getField() + " like ? ");
                 return ruleString.toString();
             case "en":
                 ruleString.append(rule.getField() + " not like ? ");
                 return ruleString.toString();
             case "cn":
                 ruleString.append(rule.getField() + " like ? ");
                 return ruleString.toString();
             case "nc":
                ruleString.append(rule.getField() + " not like ? ");
                 return ruleString.toString();
             case "tge":
                 ruleString.append(" to_char("+ rule.getField() + ",'yyyy-MM-dd') >= ? ");
                 return ruleString.toString();
             case "tle":
                 ruleString.append(" to_char(" + rule.getField() + ",'yyyy-MM-dd') <= ? ");
                 return ruleString.toString();
         }

         return "";
     }

	 private ArrayList<Object> getParams(QueryFilter filter)
     {
		 ArrayList<Object> paras = new ArrayList<Object>();
		 Rule[] rules = filter.getRules();
         for (int i = 0; i < rules.length; i++)
         {
             switch (rules[i].getOp())
             {
                 case "eq":
                     
                 case "ne":

                 case "lt":

                 case "le":

                 case "gt":

                 case "ge":
                     paras.add(rules[i].getData().trim());
                     break;
                 case "bw":
                   
                 case "bn":
                     paras.add(rules[i].getData().trim() + "%");
                     break;
                 case "in":
                                        
                 case "ni":
                     String[] splits = StringUtils.split(rules[i].getData(), ',');
                     for (String split : splits)
                     { 
                         paras.add(split.trim());
                     }
                     
                     break;
                 case "ew":

                 case "en":
                     paras.add("%" + rules[i].getData().trim());
                     break;
                 case "cn":
                     
                 case "nc":
                     paras.add("%" + rules[i].getData().trim() + "%");
                     break;
                 case "tle":
                 case "tge":
                     paras.add(rules[i].getData().trim());
                     break;
             }
         
         }
             
         return paras;
     }

	@Override
	public Page<T> findPage(Page<T> page, PropertyFilterParam filterParam, String whereHql, Object... values) {
		// TODO Auto-generated method stub
		
		String where = filterParam.getHql();
		
		ArrayList paramList = filterParam.getParam();
		
		if (StringUtils.isNoneBlank(where)) {
			
			if (StringUtils.isNoneBlank(whereHql)) {
				where += " and ( " + whereHql + " )" ;
				
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						
						paramList.add(values[i]);
					}
				}
			}
			
			
		}
		else {
			if (StringUtils.isNoneBlank(whereHql)) {
				where += whereHql;
				
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						
						paramList.add(values[i]);
					}
				}
			}
		}
		
		return this.findPage(page, where, paramList.toArray());
	
	}

	@Override
	public Page<T> findPage(Page<T> page, QueryFilter queryFilter, String whereHql, Object... values) {
		// TODO Auto-generated method stub
		PropertyFilterParam propertyFilterParam = this.buildPropertyFilterParam(queryFilter);
		
		
		return this.findPage(page, propertyFilterParam, whereHql, values);
	}

	
}
 