package org.eleme.qianggou.dal.dom;

import java.io.Serializable;

//���ﳵ�з���ʳƷ��
public class CartsFoodDo implements Serializable {

	private static final long serialVersionUID = 4L;

	private Integer id;

	private Integer price;

	private Integer count;

	// �޲����Ĺ�����
	public CartsFoodDo() {
	}

	// ��ʼ��ȫ����Ա�����Ĺ�����
	public CartsFoodDo(Integer id, Integer price, Integer count) {
		this.setId(id);
		this.price = price;
		this.count = count;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
