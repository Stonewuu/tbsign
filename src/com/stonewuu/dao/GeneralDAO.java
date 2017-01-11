package com.stonewuu.dao;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @Title: GeneralDAO.java 
 * @Package com.weige.dao 
 * @Description: 基类DAO
 * @author wqw
 * @date 2015年3月30日 下午2:08:04 
 * @version V1.0
 *
 */

public class GeneralDAO<T>{
	@PersistenceContext
	protected EntityManager em;
	private Class<T> entityClass;
	
	/**
	 * 通过子类获取子类确定的泛型类
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GeneralDAO(){
		Type type = getClass().getGenericSuperclass();
		Type[] types = ((ParameterizedType)type).getActualTypeArguments();
		entityClass = (Class)types[0];
	}

	public EntityManager getEm() {
		return em;
	}

	/**
	 * 持久化
	 * @param t 对象
	 * @return 
	 */
	@Transactional
	public T save(T t){
		return em.merge(t);
	}
	
	/**
	 * 删除
	 * @param t 对象
	 */
	public void delete(T t){
		em.remove(t);
	}
	
	/**
	 * 新增或修改
	 * @param t 对象
	 */
	public void update(T t){
		em.merge(t);
	}
	
	/**
	 * 游离
	 * @param t 对象
	 */
	public void detach(T t){
		em.detach(t);
	}
	
	/**
	 * 根据主键查找对象
	 * @param t 查询对象
	 * @param v 编号
	 * @return 获取对象
	 */
	public T find(Object v){
		return em.find(entityClass,v);
	}
	
	/**
	 * 查找所有数据
	 */
	public List<T> findAll(){
		List<T> li = null;
		String jpql = "from "+entityClass.getName();
		TypedQuery<T> query = em.createQuery(jpql,entityClass);
		li = query.getResultList();
		return li;
	}

	/**
	 * 单个条件查找
	 * @param propertyName
	 * @param val
	 * @return
	 */
	public List<T> findByProperty(String propertyName,Object val){
		String jpql = "from "+entityClass.getName()+" where "+propertyName+"=:v";
		TypedQuery<T> query = em.createQuery(jpql,entityClass);
		query.setParameter("v", val);
		return query.getResultList();
	}

	/**
	 * 组装条件查找
	 * @param sql
	 * @param vals
	 * @return
	 */
	public List<T> findByPropertys(String sql,Object[] vals){
		String jpql = "from "+entityClass.getName()+" where 1=1 and "+sql;
		TypedQuery<T> query = em.createQuery(jpql,entityClass);
		for(int i=0;i<vals.length;i++){
			query.setParameter(i+1, vals[i]);
		}
		return query.getResultList();
	}

	public List<T> findAllByPage(int size,int page,String reg) {
		String sql = "from "+entityClass.getName()+" where 1 = 1 "+reg;
		TypedQuery<T> query = em.createQuery(sql, entityClass);
		if(size>0){
			query.setFirstResult((page-1)*size).setMaxResults(size);
		}
		return query.getResultList();
	}
	
	public List<T> findAllByShowNum(int start,int length,String reg) {
		String sql = "from "+entityClass.getName()+" where 1 = 1 "+reg;
		TypedQuery<T> query = em.createQuery(sql, entityClass);
		query.setFirstResult(start).setMaxResults(length);
		return query.getResultList();
	}
	
	public int batchDelete(Integer[] obj){
		int count = 0;
		for(int i=0;i<obj.length;i++){
			em.remove(em.find(entityClass, obj[i]));
			if (i % 30 == 0) {
                em.flush();
                em.clear();
            }
			count++;
		}
		return count;
	}
	public int selectCount(String reg ,Map<String, Object> valueMap){
		String sql = "select count(*) from "+entityClass.getName()+" where 1 = 1 "+reg;
		TypedQuery<Long> query = em.createQuery(sql, Long.class);
		if(valueMap != null){
			Iterator<Entry<String, Object>> entries = valueMap.entrySet().iterator();
			while(entries.hasNext()){
				Entry<String, Object> entry = (Entry<String, Object>) entries.next();
				String key = (String) entry.getKey();
				Object value = (Object) entry.getValue();
				query.setParameter(key, value);
			}
		}
		List<Long> list = query.getResultList();
		if(list != null && list.get(0)!=null){
			return list.get(0).intValue();
		}
		return 0;
	}
}
