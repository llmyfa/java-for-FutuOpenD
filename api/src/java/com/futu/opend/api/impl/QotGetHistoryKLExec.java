package com.futu.opend.api.impl;


import com.futu.opend.api.protobuf.QotGetHistoryKL;
import com.futu.opend.api.protobuf.QotCommon.KLFields;
import com.futu.opend.api.protobuf.QotCommon.KLType;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.RehabType;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetHistoryKLExec implements IExecutor{

	private QotMarket market;
	private String symbol;
	private RehabType rehabType;
	private KLType klType;
	private int maxAckKLNum;
	private String beginTime;
	private String endTime;
	private long needKLFieldsFlag;
	
	private QotGetHistoryKL.Response response;
	
	public final static int nProtoID = 3100;
	
	
	/**
	 * 
	 * @param market
	 * @param symbol
	 * @param rehabType
	 * @param klType
	 * @param beginTime
	 * @param endTime
	 * @param maxAckKLNum
	 * @param needKLFieldsFlag
	 */
	public QotGetHistoryKLExec(QotMarket market,String symbol,RehabType rehabType,KLType klType,String beginTime,String endTime,int maxAckKLNum,long needKLFieldsFlag){
		this.market = market;
		this.symbol = symbol;
		this.rehabType = rehabType;
		this.klType = klType;
		this.beginTime = beginTime;
		this.endTime = endTime;
		if (maxAckKLNum > 0)
			this.maxAckKLNum = maxAckKLNum;
		if (needKLFieldsFlag>0)
			this.needKLFieldsFlag = needKLFieldsFlag;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetHistoryKL.Request.Builder request = QotGetHistoryKL.Request.newBuilder();
		QotGetHistoryKL.C2S.Builder c2s = QotGetHistoryKL.C2S.newBuilder();
		c2s.setSecurity(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		c2s.setRehabType(this.rehabType.getNumber());
		c2s.setKlType(this.klType.getNumber());
		c2s.setBeginTime(beginTime);
		c2s.setEndTime(endTime);
		if (this.maxAckKLNum >0)
			c2s.setMaxAckKLNum(maxAckKLNum);
		if (this.needKLFieldsFlag>0)
			c2s.setNeedKLFieldsFlag(needKLFieldsFlag);
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetHistoryKL.Response.parseFrom(pack.getBodys());
		
	}

	@Override
	public QotGetHistoryKL.Response getValue() {
		return response;
	}

}
