package com.poscoict.mvc.guestbook;

import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.mysite.mvc.main.MainAction;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;
import com.poscoict.web.util.MvcUtil;

public class GuestbookActionFactory extends ActionFactory{

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("insert".equals(actionName)) {
			action = new InsertAction();
		}
		else if("deleteform".equals(actionName)){
			action = new DeleteFormAction();
		}
		else if("delete".equals(actionName)){
			action = new DeleteAction();
		}
		else{
			action = new IndexAction();
		}
		return action;
	}

}
