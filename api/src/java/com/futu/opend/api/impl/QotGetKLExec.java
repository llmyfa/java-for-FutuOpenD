package com.futu.opend.api.impl;

import java.util.List;
import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.protobuf.QotCommon.KLType;
import com.futu.opend.api.protobuf.QotCommon.KLine;
import com.futu.opend.api.protobuf.QotCommon.RehabType;
import com.futu.opend.api.protobuf.QotGetKL;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotUpdateKL;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetKLExec implements IUpdateExecutor<QotUpdateKL.Response>{

	private QotGetKL.Response response;
	
	private QotMarket market;
	private String symbol;
	private RehabType rehabType;
	private KLType klType;
	private int reqNum;
	
	private IUpdateCallBack<List<KLine>> callback;
	
	public final static int nProtoID = 3006;
	
	public final static int nUpdateProtoID = 3007;
	
	public QotGetKLExec(QotMarket market,String symbol,RehabType rehabType,KLType klType,int reqNum,IUpdateCallBack<List<KLine>> callback){
		this.market = market;
		this.symbol = symbol;
		this.rehabType = rehabType;
		this.klType = klType;
		this.reqNum = reqNum;
		if (this.reqNum>1000)
			this.reqNum = 1000;
		this.callback = callback;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetKL.Request.Builder request = QotGetKL.Request.newBuilder();
		QotGetKL.C2S.Builder c2s = QotGetKL.C2S.newBuilder();
		c2s.setKlType(klType.getNumber());
		c2s.setRehabType(rehabType.getNumber());
		c2s.setReqNum(reqNum);
		c2s.setSecurity(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetKL.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetKL.Response getValue() {
		return response;
	}

	@Override
	public QotUpdateKL.Response parse(ProtoBufPackage pack)
			throws InvalidProtocolBufferException {
		return QotUpdateKL.Response.parseFrom(pack.getBodys());
	}

	@Override
	public void handler(QotUpdateKL.Response res) {
		if (callback!=null){
			if (res.getS2C().getSecurity().getMarket()==this.market.getNumber()&&res.getS2C().getSecurity().getCode().equals(this.symbol)&&
				res.getS2C().getRehabType()==this.rehabType.getNumber()&&res.getS2C().getKlType()==this.klType.getNumber()){
					callback.callback(res.getS2C().getKlListList());
			}
		}
		
	}

	@Override
	public int getnUpdateProtoID() {
		return nUpdateProtoID;
	}

}
