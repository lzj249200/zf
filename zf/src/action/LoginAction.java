package action;

import biz.UserBiz;
import biz.impl.UserBizImpl;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.Users;

public class LoginAction extends ActionSupport{
	//����˽���������ڻ�ȡҳ������ ������������ҳ��������������ƶ�Ӧ
	//�����Ԫ���е�name���� �������ж�Ӧ�� get�� set����
	private String username;
	private String password;
	private UserBiz userBiz;//new �ӿڵ�ʵ����
	
	public UserBiz getUserBiz() {
		return userBiz;
	}
	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * ���ڴ����û�����ķ��� Structs2�ĺ��Ŀ���Ĭ�ϵ��ø÷���
	 * ����ֵ Ϊ�û���Ӧ������ ��Ҫ��struts.xml�����ļ��е�name���Զ�Ӧ
	 */
	@Override
	public String execute() throws Exception {
		//��ȡStruts2��������
		ActionContext ac = ActionContext.getContext();
		//���ݷ�װ
		Users users = new Users();
		users.setName(username);
		users.setPassword(password);
		
		//����Biz�ķ�����ɵ�¼����
		int res = userBiz.login(users);			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		//����biz�ķ��ؽ�������ж�  ����1��ʾ��½�ɹ�  2��ʾ�˺Ŵ���  3��ʾ�������
		if(res==2){
			//�˺Ŵ���
			ac.put("error",this.getText("zhcw"));
			return this.ERROR;
		}else if(res==3){
			//�������
			ac.put("error",this.getText("mmcw"));
			return this.ERROR;
		}
		//��½�ɹ�
		//return this.SUCCESS;
		//System.out.println(username);
		//System.out.println(password);
		ac.getSession().put("us", users);
		return this.SUCCESS;
	}

		
}
