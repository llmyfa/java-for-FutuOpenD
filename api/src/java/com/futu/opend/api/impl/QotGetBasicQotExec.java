package com.futu.opend.api.impl;


import java.util.ArrayList;
import java.util.List;

import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.protobuf.QotCommon.BasicQot;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotGetBasicQot;
import com.futu.opend.api.protobuf.QotGetBasicQot.Response;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetBasicQotExec implements IUpdateExecutor<QotGetBasicQot.Response>{

	private QotGetBasicQot.Response response;
	
	private QotMarket market;
	private String[] symbols;
	
	private IUpdateCallBack<List<BasicQot>> callback;
	
	public final static int nProtoID = 3004;
	
	public final static int nUpdateProtoID = 3005;
	
	public QotGetBasicQotExec(QotMarket market,String[] symbols,IUpdateCallBack<List<BasicQot>> callback){
		this.market = market;
		this.symbols = symbols;
		this.callback = callback;
	}
	
	public ProtoBufPackage buildPackage(){
		QotGetBasicQot.Request.Builder request = QotGetBasicQot.Request.newBuilder();
		QotGetBasicQot.C2S.Builder c2s = QotGetBasicQot.C2S.newBuilder();
		for(String symbol : symbols)
			c2s.addSecurityList(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}


	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetBasicQot.Response.parseFrom(pack.getBodys());
	}


	public QotGetBasicQot.Response getValue() {
		return response;
	}

	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public Response parse(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		return QotGetBasicQot.Response.parseFrom(pack.getBodys());
	}

	@Override
	public void handler(Response res) {
		if (callback!=null){
			List<BasicQot> list = new ArrayList<BasicQot>();
			for(BasicQot qot : res.getS2C().getBasicQotListList()){
				int market = qot.getSecurity().getMarket();
				String symbol = qot.getSecurity().getCode();
				if (this.market.getNumber() == market){
					for(String innerSymbol : symbols){
						if (symbol.equals(innerSymbol))
							list.add(qot);
					}
				}
			}
			callback.callback(list);
		}
	}

	@Override
	public int getnUpdateProtoID() {
		return nUpdateProtoID;
	}

	
}
