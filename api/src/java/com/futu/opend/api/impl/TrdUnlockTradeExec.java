package com.futu.opend.api.impl;


import com.futu.opend.api.protobuf.TrdUnlockTrade;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdUnlockTradeExec implements IExecutor{

	private boolean unlock;
	
	private String pwdMD5;
	
	private TrdUnlockTrade.Response response;
	
	public final static int nProtoID = 2005;
	
	public TrdUnlockTradeExec(boolean unlock,String pwdMD5){
		this.unlock = unlock;
		this.pwdMD5 = pwdMD5;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		TrdUnlockTrade.Request.Builder request = TrdUnlockTrade.Request.newBuilder();
		TrdUnlockTrade.C2S.Builder c2s = TrdUnlockTrade.C2S.newBuilder();
		c2s.setUnlock(unlock);
		if (unlock)
			c2s.setPwdMD5(pwdMD5);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = TrdUnlockTrade.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdUnlockTrade.Response getValue() {
		return response;
	}

}

