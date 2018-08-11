package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.TrdGetHistoryOrderFillList;
import com.futu.opend.api.protobuf.TrdCommon.TrdEnv;
import com.futu.opend.api.protobuf.TrdCommon.TrdFilterConditions;
import com.futu.opend.api.protobuf.TrdCommon.TrdHeader;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdGetHistoryOrderFillListExec  implements IExecutor{
	
	private TrdEnv trdenv;
	private long accID;
	private TrdMarket trdMarket;
	
	private TrdFilterConditions filterConditions;

	private TrdGetHistoryOrderFillList.Response response;
	
	public final static int nProtoID = 2222;

	
	public TrdGetHistoryOrderFillListExec(TrdEnv trdenv,long accID,TrdMarket trdMarket,TrdFilterConditions filterConditions){
		this.trdenv = trdenv;
		this.accID = accID;
		this.trdMarket = trdMarket;
		this.filterConditions = filterConditions;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		TrdGetHistoryOrderFillList.Request.Builder request = TrdGetHistoryOrderFillList.Request.newBuilder();
		TrdGetHistoryOrderFillList.C2S.Builder c2s = TrdGetHistoryOrderFillList.C2S.newBuilder();
		TrdHeader.Builder headerBuilder = TrdHeader.newBuilder();
		headerBuilder.setTrdEnv(trdenv.getNumber());
		headerBuilder.setAccID(accID);
		headerBuilder.setTrdMarket(trdMarket.getNumber());
		c2s.setHeader(headerBuilder.build());
		c2s.setFilterConditions(filterConditions);
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = TrdGetHistoryOrderFillList.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdGetHistoryOrderFillList.Response getValue() {
		return response;
	}

}