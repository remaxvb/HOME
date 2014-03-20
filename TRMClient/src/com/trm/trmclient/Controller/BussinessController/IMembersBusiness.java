package com.trm.trmclient.Controller.BussinessController;

import com.trm.trmclient.DTOs.MemberDTO;

public interface IMembersBusiness {
	public MemberDTO getMember(String memberID);
	public void inviteMember(MemberDTO memberDTO);
	public void deleteMember(MemberDTO memberDTO);
}
