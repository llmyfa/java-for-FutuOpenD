package com.futu.opend.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.futu.opend.api.protobuf.GetGlobalState;
import com.futu.opend.api.protobuf.QotCommon.BasicQot;
import com.futu.opend.api.protobuf.QotCommon.KLType;
import com.futu.opend.api.protobuf.QotCommon.KLine;
import com.futu.opend.api.protobuf.QotCommon.PlateSetType;
import com.futu.opend.api.protobuf.QotCommon.RehabType;
import com.futu.opend.api.protobuf.QotCommon.SecurityType;
import com.futu.opend.api.protobuf.QotCommon.Ticker;
import com.futu.opend.api.protobuf.QotCommon.TimeShare;
import com.futu.opend.api.protobuf.QotGetBasicQot;
import com.futu.opend.api.protobuf.QotGetBroker;
import com.futu.opend.api.protobuf.QotGetHistoryKL;
import com.futu.opend.api.protobuf.QotGetHistoryKLPoints;
import com.futu.opend.api.protobuf.QotGetKL;
import com.futu.opend.api.protobuf.QotGetOrderBook;
import com.futu.opend.api.protobuf.QotGetPlateSecurity;
import com.futu.opend.api.protobuf.QotGetPlateSet;
import com.futu.opend.api.protobuf.QotGetRT;
import com.futu.opend.api.protobuf.QotGetReference;
import com.futu.opend.api.protobuf.QotGetRehab;
import com.futu.opend.api.protobuf.QotGetSecuritySnapshot;
import com.futu.opend.api.protobuf.QotGetStaticInfo;
import com.futu.opend.api.protobuf.QotGetSubInfo;
import com.futu.opend.api.protobuf.QotGetTicker;
import com.futu.opend.api.protobuf.QotGetTradeDate;
import com.futu.opend.api.protobuf.QotRegQotPush;
import com.futu.opend.api.protobuf.QotSub;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.SubType;
import com.futu.opend.api.protobuf.QotGetHistoryKLPoints.NoDataMode;
import com.futu.opend.api.protobuf.QotGetReference.ReferenceType;

/**
 * FutuOpenD接口文件，包含所有api功能
 * @author jun
 *
 */
public interface Session {
	
	void openSession(String ip,int port) throws UnknownHostException, IOException;
	
	/**
	 * 获取全局状态
	 * @param futuUserID
	 * @return
	 * @throws IOException
	 */
	GetGlobalState.Response getGlobalState(long futuUserID) throws IOException;
	
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
	 * 获取订阅信息
	 * @param isReqAllConn 是否返回所有连接的订阅状态,false只返回当前连接数据
	 * @return
	 * @throws IOException
	 */
	QotGetSubInfo.Response qotGetSubInfo(boolean isReqAllConn) throws IOException;
	
	/**
	 * 获取K线
	 * @param market
	 * @param symbol
	 * @param rehabType
	 * @param klType
	 * @param reqNum
	 * @return
	 * @throws IOException
	 */
	QotGetKL.Response qotGetKL(QotMarket market,String symbol,RehabType rehabType,KLType klType,int reqNum,IUpdateCallBack<List<KLine>> callback) throws IOException;
	
	/**
	 * 分时
	 * @param market
	 * @param symbol
	 * @param callback
	 * @return
	 * @throws IOException
	 */
	QotGetRT.Response qotGetRT(QotMarket market,String symbol,IUpdateCallBack<List<TimeShare>> callback)  throws IOException;
	
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
	 * 买卖盘
	 * @param market
	 * @param symbol
	 * @param num
	 * @param callback
	 * @return
	 * @throws IOException
	 */
	QotGetOrderBook.Response qotGetOrderBook(QotMarket market,String symbol,int num,IUpdateCallBack<OrderBooks> callback) throws IOException;
	
	/**
	 * 经纪队列
	 * @param market
	 * @param symbol
	 * @param callback
	 * @return
	 * @throws IOException
	 */
	QotGetBroker.Response qotGetBroker(QotMarket market,String symbol,IUpdateCallBack<Brokers> callback)  throws IOException;
	
	/**
	 * 单只股票一段历史K线(必须先下载本地历史数据)
	 * @param market
	 * @param symbol
	 * @param rehabType
	 * @param klType
	 * @param beginTime
	 * @param endTime
	 * @param maxAckKLNum <0 所有数据
	 * @param needKLFieldsFlag <0 所有字段
	 * @return
	 * @throws IOException
	 */
	QotGetHistoryKL.Response qotGetHistoryKL(QotMarket market,String symbol,RehabType rehabType,KLType klType,String beginTime,String endTime,int maxAckKLNum,long needKLFieldsFlag) throws IOException;
	
	/**
	 * 获取多只股票多点历史K线(必须先下载本地历史数据)
	 * @param market
	 * @param symbols
	 * @param rehabType
	 * @param klType
	 * @param timeLists 最多5个时间点
	 * @param noDataMode
	 * @param maxReqSecurityNum <0 所有数据
	 * @param needKLFieldsFlag <0 所有字段
	 * @return
	 * @throws IOException
	 */
	QotGetHistoryKLPoints.Response qotGetHistoryKLPoints(QotMarket market,String[] symbols,RehabType rehabType,KLType klType,String[] timeLists,NoDataMode noDataMode,int maxReqSecurityNum,long needKLFieldsFlag)  throws IOException;
	/**
	 * 注册行情推送
	 * @param market
	 * @param symbols
	 * @param subTypes
	 * @return
	 * @throws IOException
	 */
	QotRegQotPush.Response qotRegQotPush(QotMarket market,String[] symbols,SubType[] subTypes) throws IOException;
	
	/**
	 * 获取复权信息
	 * @param market
	 * @param symbols
	 * @return
	 * @throws IOException
	 */
	QotGetRehab.Response qotGetRehab(QotMarket market,String[] symbols)  throws IOException;
	
	/**
	 * 获取某一市场的证券信息
	 * 如香港所有股票: QotMarket.QotMarket_HK_Security, SecurityType.SecurityType_Eqty,null
	 * @param market
	 * @param symbols
	 * @param secType
	 * @return
	 * @throws IOException
	 */
	QotGetStaticInfo.Response qotGetStaticInfo(QotMarket market,SecurityType secType,String[] symbols)   throws IOException;
	
	/**
	 * 获取股票快照
	 * @param market
	 * @param symbols
	 * @return
	 * @throws IOException
	 */
	QotGetSecuritySnapshot.Response qotGetSecuritySnapshot(QotMarket market,String[] symbols)   throws IOException;
	
	/**
	 * 获取板块集合下的板块
	 * @param market
	 * @param plateSetType
	 * @return
	 * @throws IOException
	 */
	QotGetPlateSet.Response qotGetPlateSet(QotMarket market,PlateSetType plateSetType)   throws IOException;
	
	/**
	 * 获取板块下的股票
	 * @param market
	 * @param symbol
	 * @return
	 * @throws IOException
	 */
	QotGetPlateSecurity.Response qotGetPlateSecurity(QotMarket market,String symbol) throws IOException;
	
	/**
	 * 获取正股相关股票
	 * @param market
	 * @param symbol
	 * @param referenceType
	 * @return
	 * @throws IOException
	 */
	QotGetReference.Response qotGetReference(QotMarket market,String symbol,ReferenceType referenceType) throws IOException;
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
	QotRegQotPush.Response qotRegQotPush(QotMarket market,String[] symbols,SubType[] subTypes,boolean isFirstPush,boolean isRegOrUnReg) throws IOException;
	
	/**
	 * 获取市场交易日
	 * @param market
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws IOException
	 */
	QotGetTradeDate.Response qotGetTradeDate(QotMarket market,String beginTime,String endTime) throws IOException;
	
	/**
	 * 解锁模拟盘交易
	 * @return
	 * @throws IOException
	 */
	TraderSession trdUnlockTradeForSimulate(long futuUserID,String pwdMD5) throws IOException;
	/**
	 * 解锁实盘盘交易
	 * @param futuUserID
	 * @param pwdMD5
	 * @return
	 * @throws IOException
	 */
	TraderSession trdUnlockTradeForReal(long futuUserID,String pwdMD5) throws IOException;
	
	void close() throws IOException;
	
	
}
