package com.trm.trmclient.DTOs;

public class MemberDTO implements IDTO {
	public String memberID;
	public String userID;
	public String tourID;
	public String groupID;
	public String status;
	public String longitude;
	public String latitude;
	public String inviteFlag;
	
	@Override
	public String getJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseFromJSON(String data) {
		// TODO Auto-generated method stub
		
	}
}
