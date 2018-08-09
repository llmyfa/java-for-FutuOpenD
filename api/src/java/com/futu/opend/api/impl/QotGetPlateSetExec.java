package com.futu.opend.api.impl;


import com.futu.opend.api.protobuf.QotCommon.PlateSetType;
import com.futu.opend.api.protobuf.QotGetPlateSet;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetPlateSetExec implements IExecutor{

	private QotMarket market;
	
	private PlateSetType plateSetType;
	
	private QotGetPlateSet.Response response;
	
	public final static int nProtoID = 3204;
	
	public QotGetPlateSetExec(QotMarket market,PlateSetType plateSetType){
		this.market = market;
		this.plateSetType = plateSetType;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetPlateSet.Request.Builder request = QotGetPlateSet.Request.newBuilder();
		QotGetPlateSet.C2S.Builder c2s = QotGetPlateSet.C2S.newBuilder();
		c2s.setMarket(market.getNumber());
		c2s.setPlateSetType(plateSetType.getNumber());
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetPlateSet.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetPlateSet.Response getValue() {
		return response;
	}

}
