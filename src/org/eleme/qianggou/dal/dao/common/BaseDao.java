package org.eleme.qianggou.dal.dao.common;

import java.util.List;
import java.io.Serializable;


/**
 * Description: ʵ�ֻ��������ݿ��ѯ�ӿ�
 * @author xuegang.xg xguestc@126.com
 * @version 1.0
 */
public interface BaseDao<T>
{

	public T get(Class<T> entityClazz , Serializable id);

	public Serializable save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public void delete(Class<T> entityClazz , Serializable id);

	public List<T> findAll(Class<T> entityClazz);

	public long findCount(Class<T> entityClazz);

}
