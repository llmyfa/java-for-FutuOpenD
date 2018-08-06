package com.futu.opend.api.impl;


import java.util.List;

import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotCommon.Ticker;
import com.futu.opend.api.protobuf.QotGetTicker;
import com.futu.opend.api.protobuf.QotGetTicker.Response;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetTickerExec implements IUpdateExecutor<QotGetTicker.Response>{

	private QotGetTicker.Response response;
	
	private QotMarket market;
	private String symbol;
	private int maxRetNum = 100;
	private IUpdateCallBack<List<Ticker>> callback;
	
	public final static int nProtoID = 3010;
	
	public final static int nUpdateProtoID = 3011;
	
	public QotGetTickerExec(QotMarket market,String symbol,int maxRetNum,IUpdateCallBack<List<Ticker>> callback){
		this.market = market;
		this.symbol = symbol;
		this.maxRetNum = maxRetNum;
		if (maxRetNum>1000)
			this.maxRetNum = 1000;
		this.callback = callback;
	}
	
	@Override
	public ProtoBufPackage buildPackage() {
		QotGetTicker.Request.Builder request = QotGetTicker.Request.newBuilder();
		QotGetTicker.C2S.Builder c2s = QotGetTicker.C2S.newBuilder();
		Security.Builder security = Security.newBuilder();
		security.setMarket(market.getNumber());
		security.setCode(symbol);
		c2s.setSecurity(security);
		c2s.setMaxRetNum(maxRetNum);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack)throws InvalidProtocolBufferException {
		response = QotGetTicker.Response.parseFrom(pack.getBodys());
		
	}

	@Override
	public QotGetTicker.Response getValue() {
		return response;
	}

	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public Response parse(ProtoBufPackage pack)
			throws InvalidProtocolBufferException {
		return QotGetTicker.Response.parseFrom(pack.getBodys());
	}

	@Override
	public void handler(Response res) {
		if (callback!=null){
			int market = res.getS2C().getSecurity().getMarket();
			String symbol = res.getS2C().getSecurity().getCode();
			if (this.market.getNumber()==market&&this.symbol.equals(symbol))
				callback.callback(res.getS2C().getTickerListList());
		}
	}

	@Override
	public int getnUpdateProtoID() {
		return nUpdateProtoID;
	}

}
