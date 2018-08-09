package com.futu.opend.api.impl;

import java.util.List;
import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.protobuf.QotCommon.TimeShare;
import com.futu.opend.api.protobuf.QotGetRT;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetRTExec implements IUpdateExecutor<QotGetRT.Response>{
	
	private QotMarket market;
	private String symbol;
	
	private QotGetRT.Response response;
	
	private IUpdateCallBack<List<TimeShare>> callback;
	
	public final static int nProtoID = 3008;
	
	public final static int nUpdateProtoID = 3009;
	
	public QotGetRTExec(QotMarket market,String symbol,IUpdateCallBack<List<TimeShare>> callback){
		this.market = market;
		this.symbol = symbol;
		this.callback = callback;
	}

	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetRT.Request.Builder request = QotGetRT.Request.newBuilder();
		QotGetRT.C2S.Builder c2s = QotGetRT.C2S.newBuilder();
		c2s.setSecurity(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetRT.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetRT.Response getValue() {
		return response;
	}

	@Override
	public QotGetRT.Response parse(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		return QotGetRT.Response.parseFrom(pack.getBodys());
	}

	@Override
	public void handler(QotGetRT.Response res) {
		if (this.callback!=null){
			if (res.getS2C().getSecurity().getMarket()==this.market.getNumber()&&res.getS2C().getSecurity().getCode().equals(this.symbol))
				callback.callback(res.getS2C().getRtListList());
		}
		
	}

	@Override
	public int getnUpdateProtoID() {
		return nUpdateProtoID;
	}
}
