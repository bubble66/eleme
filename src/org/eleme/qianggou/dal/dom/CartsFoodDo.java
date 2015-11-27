package org.eleme.qianggou.dal.dom;

import java.io.Serializable;

//���ﳵ�з���ʳƷ��
public class CartsFoodDo implements Serializable {

	private static final long serialVersionUID = 4L;

	private int id;

	private int price;

	private int count;

	// �޲����Ĺ�����
	public CartsFoodDo() {
	}

	// ��ʼ��ȫ����Ա�����Ĺ�����
	public CartsFoodDo(int id, int price, int count) {
		this.setId(id);
		this.price = price;
		this.count = count;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean equals(CartsFoodDo item) {
		if(this.id == item.getId())
			return true;
		return false;	
	}
}
