package com.stonewuu.service;

public interface GeneralService<T> {
	/**
	 * @Title: create
	 * @Description: 通用新增
	 * @author stonewuu 2017年1月13日 下午2:34:52
	 *
	 * @param t
	 * @return
	 */
	public T create(T t);

	/**
	 * 
	 * @Title: delete
	 * @Description: 通用删除(通过ID)
	 * @author stonewuu 2017年1月13日 下午2:35:10
	 *
	 * @param t
	 */
	public void delete(Object id);

	/**
	 * 
	 * @Title: update
	 * @Description: 通用更新
	 * @author stonewuu 2017年1月13日 下午2:35:22
	 *
	 * @param t
	 */
	public void update(T t);
}
