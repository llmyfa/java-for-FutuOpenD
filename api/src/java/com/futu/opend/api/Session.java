package com.futu.opend.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.futu.opend.api.protobuf.QotCommon.BasicQot;
import com.futu.opend.api.protobuf.QotCommon.Ticker;
import com.futu.opend.api.protobuf.QotGetBasicQot;
import com.futu.opend.api.protobuf.QotGetTicker;
import com.futu.opend.api.protobuf.QotRegQotPush;
import com.futu.opend.api.protobuf.QotSub;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.SubType;

/**
 * FutuOpenD接口文件，包含所有api功能
 * @author jun
 *
 */
public interface Session {
	
	void openSession(String ip,int port) throws UnknownHostException, IOException;
	
	/**
	 * 
	 * @param ip
	 * @param port
	 * @param keepAlive ture,提供心跳协议，false不提供。 默认提供. 无心跳协议可以进行功能性测试，不用等待十秒钟关闭程序
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	void openSession(String ip,int port,boolean keepAlive) throws UnknownHostException, IOException;
	
	/**
	 * 获取股票基本行情
	 * @param market
	 * @param symbol
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	QotGetBasicQot.Response stockBasicInfo(QotMarket market,String[] symbol,IUpdateCallBack<List<BasicQot>> callback) throws IOException;
	
	/**
	 * 订阅
	 * @param market
	 * @param symbols
	 * @param subType
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	QotSub.Response qotSub(QotMarket market,String[] symbols,SubType[] subTypes) throws IOException;
	
	/**
	 * 反订阅
	 * @param market
	 * @param symbols
	 * @param subType
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	QotSub.Response qotUnSub(QotMarket market,String[] symbols,SubType[] subTypes) throws IOException;
	
	/**
	 * 逐笔 maxRetNum 100
	 * @param market
	 * @param symbol
	 * @param 
	 * @return
	 * @throws IOException
	 */
	QotGetTicker.Response qotGetTicker(QotMarket market,String symbol,IUpdateCallBack<List<Ticker>> callback) throws IOException;
	
	/**
	 * 逐笔
	 * @param market
	 * @param symbol
	 * @param maxRetNum
	 * @return
	 * @throws IOException
	 */
	QotGetTicker.Response qotGetTicker(QotMarket market,String symbol,int maxRetNum,IUpdateCallBack<List<Ticker>> callback) throws IOException;
	
	/**
	 * 注册行情推送
	 * @param market
	 * @param symbols
	 * @param subTypes
	 * @return
	 * @throws IOException
	 */
	QotRegQotPush.Response QotRegQotPushExec(QotMarket market,String[] symbols,SubType[] subTypes) throws IOException;
	
	/**
	 * 注册行情推送
	 * @param market
	 * @param symbols
	 * @param subTypes
	 * @param isFirstPush
	 * @param isRegOrUnReg
	 * @return
	 * @throws IOException
	 */
	QotRegQotPush.Response QotRegQotPushExec(QotMarket market,String[] symbols,SubType[] subTypes,boolean isFirstPush,boolean isRegOrUnReg) throws IOException;
	
	void close() throws IOException;
	
	
}
