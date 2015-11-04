package interceptors;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import entity.Users;
//������  ����Ƿ��Ѿ���½
public class CheckLogined extends AbstractInterceptor{

	public String intercept(ActionInvocation act) throws Exception {
		
		Users users = (Users) ActionContext.getContext().getSession().get("us");
		//���usersΪnull ��sessionû���û����� �û���Ҫ���µ�¼
		if(users==null){
			ActionContext.getContext().put("error", "�Բ�������û�е�½,���¼���ٲ���");
			return "login";
		}
		//act Ϊ��ǰ���е�action�����Ķ������һ������������ 
		//invoke����  ����ִ����һ�������������action��ִ������
		//invoke�����᷵��һ��string�������� Ϊ��һ�����ػ�action���󷵻ص����� ������Ϊstructs.xml�н����
		
		String res = act.invoke();
		
		return res;
	}

}
