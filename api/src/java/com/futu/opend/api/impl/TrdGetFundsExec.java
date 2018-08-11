package com.futu.opend.api.impl;


import com.futu.opend.api.protobuf.TrdCommon.TrdEnv;
import com.futu.opend.api.protobuf.TrdCommon.TrdHeader;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.futu.opend.api.protobuf.TrdGetFunds;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdGetFundsExec implements IExecutor{
	
	private TrdEnv trdenv;
	private long accID;
	private TrdMarket trdMarket;
	
	private TrdGetFunds.Response response;
	
	public final static int nProtoID = 2101;

	
	public TrdGetFundsExec(TrdEnv trdenv,long accID,TrdMarket trdMarket){
		this.trdenv = trdenv;
		this.accID = accID;
		this.trdMarket = trdMarket;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		TrdGetFunds.Request.Builder request = TrdGetFunds.Request.newBuilder();
		TrdGetFunds.C2S.Builder c2s = TrdGetFunds.C2S.newBuilder();
		TrdHeader.Builder headerBuilder = TrdHeader.newBuilder();
		headerBuilder.setTrdEnv(trdenv.getNumber());
		headerBuilder.setAccID(accID);
		headerBuilder.setTrdMarket(trdMarket.getNumber());
		c2s.setHeader(headerBuilder.build());
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = TrdGetFunds.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdGetFunds.Response getValue() {
		return response;
	}

}
