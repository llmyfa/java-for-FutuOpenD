package com.futu.opend.api.impl;

import com.futu.opend.api.protobuf.QotGetHistoryKLPoints;
import com.futu.opend.api.protobuf.QotCommon.KLType;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.RehabType;
import com.futu.opend.api.protobuf.QotCommon.Security;
import com.futu.opend.api.protobuf.QotGetHistoryKLPoints.NoDataMode;
import com.google.protobuf.InvalidProtocolBufferException;

class QotGetHistoryKLPointsExec implements IExecutor{

	private QotMarket market;
	private String[] symbols;
	private RehabType rehabType;
	private KLType klType;
	private NoDataMode noDataMode;
	private String[] timeLists;
	private int maxReqSecurityNum;
	private long needKLFieldsFlag;
	
	private QotGetHistoryKLPoints.Response response;
	
	public final static int nProtoID = 3101;
	
	
	public QotGetHistoryKLPointsExec(QotMarket market,String[] symbols,RehabType rehabType,KLType klType,String[] timeLists,NoDataMode noDataMode,int maxReqSecurityNum,long needKLFieldsFlag){
		this.market = market;
		this.symbols = symbols;
		this.rehabType = rehabType;
		this.klType = klType;
		this.noDataMode = noDataMode;
		this.timeLists = timeLists;
		if (maxReqSecurityNum > 0)
			this.maxReqSecurityNum = maxReqSecurityNum;
		if (needKLFieldsFlag > 0)
			this.needKLFieldsFlag = needKLFieldsFlag;
	}
	
	@Override
	public int getnProtoID() {
		return nProtoID;
	}

	@Override
	public ProtoBufPackage buildPackage() {
		QotGetHistoryKLPoints.Request.Builder request = QotGetHistoryKLPoints.Request.newBuilder();
		QotGetHistoryKLPoints.C2S.Builder c2s = QotGetHistoryKLPoints.C2S.newBuilder();
		for(String symbol : symbols)
			c2s.addSecurityList(Security.newBuilder().setMarket(market.getNumber()).setCode(symbol));
		c2s.setRehabType(this.rehabType.getNumber());
		c2s.setKlType(this.klType.getNumber());
		c2s.setNoDataMode(noDataMode.getNumber());
		int count = 0;
		for(String timeList : timeLists){
			c2s.addTimeList(timeList);
			count++;
			if (count==5)
				break;
		}
		if (maxReqSecurityNum>0)
			c2s.setMaxReqSecurityNum(maxReqSecurityNum);
		if (needKLFieldsFlag>0)
			c2s.setNeedKLFieldsFlag(needKLFieldsFlag);
		request.setC2S(c2s);
		ProtoBufPackage pack = new ProtoBufPackage();
		pack.setnProtoID(nProtoID);
		pack.setBodys(request.build().toByteArray());
		return pack;
	}

	@Override
	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException {
		response = QotGetHistoryKLPoints.Response.parseFrom(pack.getBodys());
	}

	@Override
	public QotGetHistoryKLPoints.Response getValue() {
		return response;
	}

}
