package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.TrdCommon.TrdFilterConditions;
import com.futu.opend.api.protobuf.TrdCommon.TrdEnv;
import com.futu.opend.api.protobuf.TrdCommon.TrdHeader;
import com.futu.opend.api.protobuf.TrdCommon.TrdMarket;
import com.futu.opend.api.protobuf.TrdGetPositionList;
import com.google.protobuf.InvalidProtocolBufferException;

class TrdGetPositionListExec implements IExecutor{
	
	private TrdEnv trdenv;
	private long accID;
	private TrdMarket trdMarket;
	
	private TrdFilterConditions filterConditions;
	private Double filterPLRatioMin;
	private Double filterPLRatioMax;
	
	private TrdGetPositionList.Response response;
	
	public final static int nProtoID = 2102;

	
	public TrdGetPositionListExec(TrdEnv trdenv,long accID,TrdMarket trdMarket,TrdFilterConditions filterConditions,Double filterPLRatioMin,Double filterPLRatioMax){
		this.trdenv = trdenv;
		this.accID = accID;
		this.trdMarket = trdMarket;
		this.filterConditions = filterConditions;
		this.filterPLRatioMin = filterPLRatioMin;
		this.filterPLRatioMax = filterPLRatioMax;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		TrdGetPositionList.Request.Builder request = TrdGetPositionList.Request.newBuilder();
		TrdGetPositionList.C2S.Builder c2s = TrdGetPositionList.C2S.newBuilder();
		TrdHeader.Builder headerBuilder = TrdHeader.newBuilder();
		headerBuilder.setTrdEnv(trdenv.getNumber());
		headerBuilder.setAccID(accID);
		headerBuilder.setTrdMarket(trdMarket.getNumber());
		c2s.setHeader(headerBuilder.build());
		if (filterConditions!=null)
			c2s.setFilterConditions(filterConditions);
		if (filterPLRatioMin!=null)
			c2s.setFilterPLRatioMin(filterPLRatioMin);
		if (filterPLRatioMax!=null)
			c2s.setFilterPLRatioMax(filterPLRatioMax);
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = TrdGetPositionList.Response.parseFrom(pack.getBodys());
	}

	@Override
	public TrdGetPositionList.Response getValue() {
		return response;
	}

}

