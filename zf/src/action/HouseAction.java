package action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz.HouseBiz;
import biz.impl.HouseBizImpl;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.District;
import entity.House;
import entity.Street;

public class HouseAction extends ActionSupport {

	private House house;
	private int dangQianYe = 1;
	private int zongYe;
	private int zongJiLu;
	private HouseBiz houseBiz;

	private List jsonList;
	private Long quId;
	
	private int price ;
	private int floorage;

	
	
	// private HouseBiz houseBiz = new HouseBizImpl();
	// private UserBiz userBiz = new UserBizImpl(); //new �ӿڵ�ʵ����

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getFloorage() {
		return floorage;
	}

	public void setFloorage(int floorage) {
		this.floorage = floorage;
	}

	public List getJsonList() {
		return jsonList;
	}

	public void setJsonList(List jsonList) {
		this.jsonList = jsonList;
	}

	public Long getQuId() {
		return quId;
	}

	public void setQuId(Long quId) {
		this.quId = quId;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public HouseBiz getHouseBiz() {
		return houseBiz;
	}

	public void setHouseBiz(HouseBiz houseBiz) {
		this.houseBiz = houseBiz;
	}

	public int getDangQianYe() {
		return dangQianYe;
	}

	public void setDangQianYe(int dangQianYe) {
		this.dangQianYe = dangQianYe;
	}

	public int getZongYe() {
		return zongYe;
	}

	public void setZongYe(int zongYe) {
		this.zongYe = zongYe;
	}

	public int getZongJiLu() {
		return zongJiLu;
	}

	public void setZongJiLu(int zongJiLu) {
		this.zongJiLu = zongJiLu;
	}

	public String fenYe() {

		// ��ȡ�ܼ�¼��!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
		this.zongJiLu = houseBiz.getZongJiLu();

		// ������ҳ��
		this.zongYe = this.zongJiLu / 3;
		if (this.zongJiLu % 3 > 0) {
			this.zongYe += 1;
		}

		// ��ȡָ��ҳ������
		List list = houseBiz.fanYe(this.dangQianYe);// ��ҳ

		// �����ݷ���ҳ��
		ActionContext ac = ActionContext.getContext();
		ac.put("list", list);

		return this.SUCCESS;
	}

	public String faBu() {
		// !!!!!!!!!!!!!!!!!!!!!!
		houseBiz.faBu(house);
		return this.SUCCESS;
	}

	/**
	 * faBu������������֤����
	 */
	public void validateFaBu() {

		if (house.getTitle() == null || "".equals(house.getTitle())) {
			this.addFieldError("title", "���ⲻ��Ϊ��");
		}
		if (house.getPrice() == null || house.getPrice() <= 0) {
			this.addFieldError("price", "�۸���С��0");
		}
	}

	public String huoQuFangWu() {

		house = houseBiz.huoQuFangWu(house.getId());

		return this.SUCCESS;
	}

	public String delete() {
		houseBiz.delete(house);
		return this.SUCCESS;
	}

	public String huoQuQuYu() {

		List list = houseBiz.huoQuQuYu();
		Map map = new HashMap();

		for (Object obj : list) {
			District d = (District) obj;
			map.put(d.getId(), d.getName());
		}
		ActionContext.getContext().put("map", map);
		return this.SUCCESS;
	}

	public String ld() {

		this.jsonList = houseBiz.huoQuJieDao(quId);
		for (int i = 0; i < jsonList.size(); i++) {
			((Street) jsonList.get(i)).setDistrict(null);
			((Street) jsonList.get(i)).setHouses(null);
		}
		return this.SUCCESS;
	}

	public String souSuo() {

		// ��ȡ�ܼ�¼�� (����ҳ������)
		this.zongJiLu = houseBiz.getZongJiLu(house,price,floorage);
		// ������ҳ��
		this.zongYe = this.zongJiLu / 3;
		if (this.zongJiLu % 3 > 0) {
			this.zongYe += 1;
		}
		// ��ȡָ��ҳ������ (����ҳ������)
		List list = houseBiz.fanYe(this.dangQianYe,house,price,floorage);// ��ҳ
		// �����ݷ���ҳ��
		ActionContext ac = ActionContext.getContext();
		ac.put("list", list);

		return this.SUCCESS;
	}

}