package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.TrdSubAccPush;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdSubAccPushExec implements IExecutor{

	private TrdSubAccPush.Response response;
	
	private Long[] accIDList;
	
	public final static int nProtoID = 2008;
	
	public TrdSubAccPushExec(Long[] accIDList){
		this.accIDList = accIDList;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		TrdSubAccPush.Request.Builder request = TrdSubAccPush.Request.newBuilder();
		TrdSubAccPush.C2S.Builder c2s = TrdSubAccPush.C2S.newBuilder();
		for(long accID : accIDList)
			c2s.addAccIDList(accID);
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = TrdSubAccPush.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdSubAccPush.Response getValue() {
		return response;
	}

}
