package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.QotGetHistoryKL;
import com.futu.opend.api.protobuf.QotCommon.KLType;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.RehabType;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotRequestHistoryKL;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class QotRequestHistoryKLExec implements IExecutor{

	private QotMarket market;
	private String symbol;
	private RehabType rehabType;
	private KLType klType;
	private int maxAckKLNum;
	private String beginTime;
	private String endTime;
	private long needKLFieldsFlag;
	private String nextReqKey;
	
	private QotRequestHistoryKL.Response response;
	
	public final static int nProtoID = 3103;
	
	
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
	 * @param nextReqKey  分页请求的key。如果start和end之间的数据点多于max_count，那么后续请求时，要传入上次调用返回的page_req_key。初始请求时应该传None。
	 */
	public QotRequestHistoryKLExec(QotMarket market,String symbol,RehabType rehabType,KLType klType,String beginTime,String endTime,int maxAckKLNum,long needKLFieldsFlag,String nextReqKey){
		this.market = market;
		this.symbol = symbol;
		this.rehabType = rehabType;
		this.klType = klType;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.maxAckKLNum = maxAckKLNum;
		this.needKLFieldsFlag = needKLFieldsFlag;
		this.nextReqKey = nextReqKey;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotRequestHistoryKL.Request.Builder request = QotRequestHistoryKL.Request.newBuilder();
		QotRequestHistoryKL.C2S.Builder c2s = QotRequestHistoryKL.C2S.newBuilder();
		c2s.setSecurity(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		c2s.setRehabType(this.rehabType.getNumber());
		c2s.setKlType(this.klType.getNumber());
		c2s.setBeginTime(beginTime);
		c2s.setEndTime(endTime);
		if (this.maxAckKLNum >0)
			c2s.setMaxAckKLNum(maxAckKLNum);
		if (this.needKLFieldsFlag>0)
			c2s.setNeedKLFieldsFlag(needKLFieldsFlag);
		if (nextReqKey==null||nextReqKey.trim().equals(""))
			c2s.setNextReqKey(ByteString.copyFromUtf8("None"));
		else
			c2s.setNextReqKey(ByteString.copyFromUtf8(nextReqKey));
		
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotRequestHistoryKL.Response.parseFrom(pack.getBodys());
		
	}

	@Override
	public QotRequestHistoryKL.Response getValue() {
		return response;
	}

}
