package com.haven.Haven_TRMClient.Controller.BussinessController;

import com.haven.Haven_TRMClient.DTOs.AccountDTO;
import com.haven.Haven_TRMClient.View.BaseActivity;

/**
 * Created by hieu.t.vo on 3/13/14.
 */
public interface IAccountBusiness {
	boolean signIn(BaseActivity sender, String email, String password);

	boolean signUp(BaseActivity sender, AccountDTO accountDTO);

	boolean updateUserInfo(BaseActivity sender, AccountDTO accountDTO);

	boolean updateAvatar(BaseActivity sender, Object avatar);
}
