package com.trm.trmclient.Model;

import org.json.JSONObject;

import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Controller.BussinessController.IMembersBusiness;
import com.trm.trmclient.DTOs.MemberDTO;
import com.trm.trmclient.DTOs.MembersDTO;

public class MembersModel extends BaseModel implements IMembersBusiness, IServiceListener {
	private static MembersModel instance;

	public static synchronized MembersModel getInstance() {
		if (instance == null) {
			instance = new MembersModel();
		}
		return instance;
	}

	private MembersModel() {
		dto = new MembersDTO();
		dto = loadData("MembersDTO.data");
	}
	
	@Override
	public MemberDTO getMember(String memberID) {
		//TODO: Search in membersDTO
		return null;
	}

	@Override
	public void inviteMember(MemberDTO memberDTO) {
		// TODO Call service		
	}

	@Override
	public void deleteMember(MemberDTO memberDTO) {
		// TODO Call service, change member list, update view
		
	}

	@Override
	public void onServiceResponse(ServiceEventConstant serviceType,
			JSONObject data) {
		// TODO Check service result, change member list, update view
		
	}

	@Override
	public void onServiceError(ServiceEventConstant serviceType, int errorCode) {
		// TODO Inform view
		
	}
		
}
