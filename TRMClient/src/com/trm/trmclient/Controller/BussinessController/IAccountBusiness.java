package com.trm.trmclient.Controller.BussinessController;

import com.trm.trmclient.DTOs.AccountDTO;
import com.trm.trmclient.View.BaseActivity;

/**
 * Created by hieu.t.vo on 3/13/14.
 */
public interface IAccountBusiness {
	boolean signIn(BaseActivity sender, String email, String password);

	boolean signUp(BaseActivity sender, AccountDTO accountDTO);

	boolean updateUserInfo(BaseActivity sender, AccountDTO accountDTO);

	boolean updateAvatar(BaseActivity sender, Object avatar);
}
