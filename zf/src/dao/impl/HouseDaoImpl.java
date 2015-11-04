package dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import dao.HouseDao;
import entity.House;

public class HouseDaoImpl implements HouseDao {

	public int getZongJiLu() {

		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();

		Query query = session.createQuery("select count(id) from House");
		// ֱ�Ӳ�ѯ������Ψһ���
		int res = Integer.parseInt(query.uniqueResult().toString());
		session.close();
		sf.close();
		return res;
	}
	
	public List fanYe(int dangQianYe){
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session=sf.openSession();
		Query query = session.createQuery("from House h left join fetch h.street s left join fetch s.district d");
		//dangQianYe ��ʼ��Ϊ1
		query.setFirstResult((dangQianYe-1)*3);
		query.setMaxResults(3);
		List list=query.list();
		//session.close();
		//sf.close();
		return list;
	}

	@Override
	public void faBu(House house) {
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		//���������޸ģ�û�����򱣴�
		session.saveOrUpdate(house);
		t.commit();
		session.close();
		sf.close();
	}
	//!!!!!!!!!!!!111
	public void delete(House house){
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		//ɾ��
		session.delete(house);
		t.commit();
		session.close();
		sf.close();
		
	}

	@Override
	public House huoQuFangWu(long id) {
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Query query = session.createQuery("from House h left join fetch h.street s left join fetch s.district d where h.id=?");
		query.setLong(0, id);
		House h = (House) query.uniqueResult();
		session.close();
		sf.close();
		return h;
	}
	
	//��ȡ������ҳ��������б�����ʾdistrict�е���������
	public List huoQuQuYu(){
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Query query = session.createQuery("from District");
		List list = query.list();
		session.close();
		sf.close();
		return list;
	}
	
	public List huoQuJieDao(long quId){
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		Query query = session.createQuery("from Street s where s.district=?");
		query.setLong(0, quId);
		List list = query.list();
		session.close();
		sf.close();
		return list;
	}

	@Override
	public List fanYe(int dangQianYe, House house, int price, int floorage) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		//where 1=1 :��ȷ�������Ļ������(������������ֱ�Ӽ�&�������ɣ����迼�Ǻ�ʱ��where��ʱ��&(and))
		String hql=" from House h left join fetch h.street s left join fetch s.district d left join fetch h.type where 1=1";
		//��ϲ�ѯ���
		if(house != null&&house.getTitle()!=null&&!"".equals(house.getTitle())){
			hql+=" and h.title like :title";
		}
		if(price==1){
			hql +=" and h.price > 500 and h.price < 1500"; 
		}
		if(price==2){
			hql +=" and h.price>=1500 and h.price < 2500";
		}
		if(price==3){
			hql +=" and h.price>=2500";
		}
		if(floorage==1){
			hql +=" and h.floorage > 30 and h.floorage < 50";	
		}
		if(floorage==2){
			hql +=" and h.floorage >= 50 and h.floorage < 80";	
		}if(floorage==3){
			hql +=" and h.floorage >= 80";	
		}
		
		Query query = session.createQuery(hql);
		//����ÿҳ��ʾ�����������
		query.setFirstResult((dangQianYe-1)*3);
		query.setMaxResults(3);
		
		//Ϊ���Ĳ�����ֵ
		if(house!=null&&house.getTitle()!=null&&!"".equals(house.getTitle())){
			query.setString("title", "%"+house.getTitle()+"%");
		}
		
		List list = query.list();
		return list;
	}

	@Override
	//ͳ���ܼ�¼��
	public int getZongJiLu(House house, int price, int floorage) {
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		//where 1=1 :��ȷ�������Ļ������(������������ֱ�Ӽ�&�������ɣ����迼�Ǻ�ʱ��where��ʱ��&(and))
		String hql="select count(h.id) from House h  where 1=1";
		
		//��ϲ�ѯ���
		if(house != null&&house.getTitle()!=null&&"".equals(house.getTitle())){
			hql+=" and h.title like :title";
		}
		if(price==1){
			hql +=" and h.price > 500 and h.price < 1500"; 
		}
		if(price==2){
			hql +=" and h.price>=1500 and h.price < 2500";
		}
		if(price==3){
			hql +=" and h.price>=2500";
		}
		if(floorage==1){
			hql +=" and h.floorage > 30 and h.floorage < 50";	
		}
		if(floorage==2){
			hql +=" and h.floorage >= 50 and h.floorage < 80";	
		}if(floorage==3){
			hql +=" and h.floorage >= 80";	
		}
		
		Query query = session.createQuery(hql);
		
		//Ϊ���Ĳ�����ֵ
		if(house!=null&&house.getTitle()!=null&&"".equals(house.getTitle())){
			query.setString("title", "%"+house.getTitle()+"%");
		}
		
		int res=Integer.parseInt(query.uniqueResult().toString());
		
		return res;
	}

}
