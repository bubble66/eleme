package org.eleme.qianggou.dal.dao.common.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.eleme.qianggou.dal.dao.common.BaseDao;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional("transactionManager")
public class BaseDaoHibernate4<T> implements BaseDao<T> {
	// DAO组件进行持久化操作底层依赖的SessionFactory组件
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	// 依赖注入SessionFactory所需的setter方法
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	// 根据ID加载实体
	@SuppressWarnings("unchecked")
	public T get(Class<T> entityClazz, Serializable id) {
		return (T) getSessionFactory().getCurrentSession().get(entityClazz, id);
	}

	// 保存实体
	public Serializable save(T entity) {
		return getSessionFactory().getCurrentSession().save(entity);
	}

	// 更新实体
	public void update(T entity) {
		getSessionFactory().getCurrentSession().saveOrUpdate(entity);
	}

	// 删除实体
	public void delete(T entity) {
		getSessionFactory().getCurrentSession().delete(entity);
	}

	// 根据ID删除实体
	public void delete(Class<T> entityClazz, Serializable id) {
		getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"delete " + entityClazz.getSimpleName()
								+ " en where en.id = ?0").setParameter("0", id)
				.executeUpdate();
	}

	// 获取所有实体
	public List<T> findAll(Class<T> entityClazz) {
		try {
			return find("select en from " + entityClazz.getSimpleName() + " en");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	// 获取实体总数

	public long findCount(Class<T> entityClazz) {
		List<?> l = find("select count(*) from " + entityClazz.getSimpleName());
		// 返回查询得到的实体总数
		if (l != null && l.size() == 1) {
			return (Long) l.get(0);
		}
		return 0;
	}

	// 根据HQL语句查询实体
	@SuppressWarnings("unchecked")
	protected List<T> find(String hql) {
		try {
			return (List<T>) getSessionFactory().getCurrentSession()
					.createQuery(hql).list();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	// 根据带占位符参数HQL语句查询实体
	@SuppressWarnings("unchecked")
	protected List<T> find(String hql, Object... params) {
		// 创建查询
		try {
			Query query = getSessionFactory().getCurrentSession().createQuery(hql);

			// 为包含占位符的HQL语句设置参数
			for (int i = 0, len = params.length; i < len; i++) {
				query.setParameter(i + "", params[i]);
			}
			return (List<T>) query.list();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}

	/**
	 * b 使用hql 语句进行分页查询操作
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param pageNo
	 *            查询第pageNo页的记录
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByPage(String hql, int pageNo, int pageSize) {
		// 创建查询
		return getSessionFactory().getCurrentSession().createQuery(hql)
				// 执行分页
				.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	/**
	 * 使用hql 语句进行分页查询操作
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param params
	 *            如果hql带占位符参数，params用于传入占位符参数
	 * @param pageNo
	 *            查询第pageNo页的记录
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByPage(String hql, int pageNo, int pageSize,
			Object... params) {
		// 创建查询
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		// 为包含占位符的HQL语句设置参数
		for (int i = 0, len = params.length; i < len; i++) {
			query.setParameter(i + "", params[i]);
		}
		// 执行分页，并返回查询结果
		return query.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}
}
