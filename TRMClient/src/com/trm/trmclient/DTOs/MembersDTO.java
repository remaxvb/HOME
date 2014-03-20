package com.trm.trmclient.DTOs;

import java.util.ArrayList;

public class MembersDTO implements IDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<MemberDTO> memberList;
	public MembersDTO() {
		memberList = new ArrayList<MemberDTO>();
	}
	
	public void addMember(MemberDTO memberDTO) {
		memberList.add(memberDTO);
	}
	
	public void removeMember(MemberDTO memberDTO) {
		memberList.remove(memberDTO);
	}
	
	public void clearMemberList() {
		memberList.clear();
	}
	
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
