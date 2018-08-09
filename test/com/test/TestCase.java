package com.test;

import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.futu.opend.api.Brokers;
import com.futu.opend.api.IUpdateCallBack;
import com.futu.opend.api.OrderBooks;
import com.futu.opend.api.Session;
import com.futu.opend.api.impl.FutuOpenD;
import com.futu.opend.api.protobuf.QotCommon.KLFields;
import com.futu.opend.api.protobuf.QotCommon.KLType;
import com.futu.opend.api.protobuf.QotCommon.KLine;
import com.futu.opend.api.protobuf.QotCommon.PlateSetType;
import com.futu.opend.api.protobuf.QotCommon.RehabType;
import com.futu.opend.api.protobuf.QotCommon.SecurityStaticInfo;
import com.futu.opend.api.protobuf.QotCommon.SecurityType;
import com.futu.opend.api.protobuf.QotCommon.Ticker;
import com.futu.opend.api.protobuf.QotCommon.TimeShare;
import com.futu.opend.api.protobuf.QotGetBasicQot;
import com.futu.opend.api.protobuf.QotGetBroker;
import com.futu.opend.api.protobuf.QotGetHistoryKL;
import com.futu.opend.api.protobuf.QotGetHistoryKLPoints;
import com.futu.opend.api.protobuf.QotGetHistoryKLPoints.NoDataMode;
import com.futu.opend.api.protobuf.QotGetKL;
import com.futu.opend.api.protobuf.QotGetOrderBook;
import com.futu.opend.api.protobuf.QotGetPlateSecurity;
import com.futu.opend.api.protobuf.QotGetPlateSet;
import com.futu.opend.api.protobuf.QotGetRT;
import com.futu.opend.api.protobuf.QotGetReference;
import com.futu.opend.api.protobuf.QotGetReference.ReferenceType;
import com.futu.opend.api.protobuf.QotGetRehab;
import com.futu.opend.api.protobuf.QotGetSecuritySnapshot;
import com.futu.opend.api.protobuf.QotGetStaticInfo;
import com.futu.opend.api.protobuf.QotGetTicker;
import com.futu.opend.api.protobuf.QotGetTradeDate;
import com.futu.opend.api.protobuf.QotRegQotPush;
import com.futu.opend.api.protobuf.QotSub;
import com.futu.opend.api.protobuf.QotCommon.QotMarket;
import com.futu.opend.api.protobuf.QotCommon.SubType;

/**
 * 在tearUp和tearDown启用推送功能
 * 也可以直接运行main方法。
 * @author jun
 *
 */
public class TestCase{
	
	private static Session session = null;
	
	@BeforeClass
	public static void tearUp() throws IOException{
		session = FutuOpenD.openSession("localhost", 11111,false);//true,启用推送功能
		
		//订阅股票
		QotSub.Response value = session.qotSub(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"},new SubType[]{SubType.SubType_Basic,SubType.SubType_Ticker,SubType.SubType_KL_1Min,SubType.SubType_KL_Day,SubType.SubType_RT,SubType.SubType_OrderBook,SubType.SubType_Broker});
		System.out.println("订阅股票");
		System.out.println(value);
		
		//订阅推送(基本报价与逐笔)
		QotRegQotPush.Response value4 = session.qotRegQotPush(QotMarket.QotMarket_HK_Security, new String[]{"00700"}, new SubType[]{SubType.SubType_Basic,SubType.SubType_Ticker,SubType.SubType_KL_1Min,SubType.SubType_KL_Day,SubType.SubType_RT,SubType.SubType_OrderBook,SubType.SubType_Broker});
		System.out.println("订阅推送");
		System.out.println(value4);
	}
	
	@AfterClass
	public static void tearDown() throws InterruptedException, IOException{
		//Thread.sleep(30*1000);   //取消注释启用推送功能
		session.close();
	}
		
	//全局状态
	@Test
	public void testGlobalState() throws IOException{
		System.out.println(session.getGlobalState(297743));
	}
	
	//订阅详情
	@Test
	public void testQotGetSubInfo() throws IOException{
		System.out.println(session.qotGetSubInfo(false));
	}
	
	//基本报价，不处理推送
	@Test
	public void testStockBasicInfo() throws IOException{
		QotGetBasicQot.Response value2 = session.stockBasicInfo(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"},null);
		System.out.println(value2);
	}
	
	//逐笔，并处理推送
	@Test
	public void testQotGetTickero() throws IOException{
		QotGetTicker.Response value3 = session.qotGetTicker(QotMarket.QotMarket_HK_Security, "00700", 2,new IUpdateCallBack<List<Ticker>>() {
			@Override
			public void callback(List<Ticker> res) {
				System.out.println("逐笔推送:00700");
				for(Ticker qot : res)
					System.out.println(qot.toString());
			}
		});
		System.out.println(value3);
	}
	
	//逐笔，无处理推送
	@Test
	public void testQotGetTicker() throws IOException{
		QotGetTicker.Response value3 = session.qotGetTicker(QotMarket.QotMarket_HK_Security, "00005", 2,null);
		System.out.println(value3);
	}
	
	//K线，并处理推送
	@Test
	public void testQotGetKL() throws IOException{
		QotGetKL.Response value5 = session.qotGetKL(QotMarket.QotMarket_HK_Security, "00700", RehabType.RehabType_Forward, KLType.KLType_1Min, 2, new IUpdateCallBack<List<KLine>>(){
			@Override
			public void callback(List<KLine> res) {
				System.out.println("K线推送:00700");
				System.out.println(res);
			}
		});
		System.out.println(value5);
	}
	
	//分时，并处理推送
	@Test
	public void testQotGetRT() throws IOException{
	QotGetRT.Response value6 = session.qotGetRT(QotMarket.QotMarket_HK_Security, "00700", new IUpdateCallBack<List<TimeShare>>(){
		@Override
		public void callback(List<TimeShare> res) {
			System.out.println("分时推送:00700");
			System.out.println(res);
		}
	});
		System.out.println(value6);
	}
	
	//买卖盘，并处理推送
	@Test
	public void testQotGetOrderBook() throws IOException{
		QotGetOrderBook.Response value7 = session.qotGetOrderBook(QotMarket.QotMarket_HK_Security, "00700",2, new IUpdateCallBack<OrderBooks>(){
			@Override
			public void callback(OrderBooks res) {
				System.out.println("买盘推送:00700");
				System.out.println(res.getOrderBookBidList());
				System.out.println("卖盘推送:00700");
				System.out.println(res.getOrderBookAskList());
			}
		});
		System.out.println(value7);
	}
	
	//经纪队列，并处理推送
	@Test
	public void testQotGetBroker() throws IOException{
		QotGetBroker.Response value8 = session.qotGetBroker(QotMarket.QotMarket_HK_Security, "00700", new IUpdateCallBack<Brokers>(){
			@Override
			public void callback(Brokers res) {
				System.out.println("经纪队列买盘推送:00700");
				System.out.println(res.getBrokerBidList());
				System.out.println("经纪队列卖盘推送:00700");
				System.out.println(res.getBrokerAskList());
			}
		});
		System.out.println(value8.getS2C().getBrokerAskListList().get(0));
		System.out.println(value8.getS2C().getBrokerBidListList().get(0));
	}
	
	//单只股票一段历史K线(请选下载历史数据)
	@Test
	public void testQotGetHistoryKLAll() throws IOException{
		QotGetHistoryKL.Response value9 = session.qotGetHistoryKL(QotMarket.QotMarket_HK_Security, "00700",RehabType.RehabType_Forward, KLType.KLType_Day,"2018-07-01","2018-07-30", -1,-1);
		System.out.println(value9);
	}
	
	//单只股票一段历史K线,部分字段(请选下载历史数据)
	@Test
	public void testQotGetHistoryKL() throws IOException{
		QotGetHistoryKL.Response value10 = session.qotGetHistoryKL(QotMarket.QotMarket_HK_Security, "00700",RehabType.RehabType_Forward, KLType.KLType_Day,"2018-07-01","2018-07-30", -1,KLFields.KLFields_Open_VALUE|KLFields.KLFields_Close_VALUE|KLFields.KLFields_Low_VALUE|KLFields.KLFields_High_VALUE);
		System.out.println(value10);
	}
	
	//获取多只股票多点历史K线(请选下载历史数据)
	@Test
	public void testQotGetHistoryKLPoints() throws IOException{
		QotGetHistoryKLPoints.Response value11 = session.qotGetHistoryKLPoints(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"},RehabType.RehabType_Forward, KLType.KLType_Day,new String[]{"2018-07-01","2018-07-30"},NoDataMode.NoDataMode_Forward, -1,-1);
		System.out.println(value11);
	}
	
	//获取复权信息
	@Test
	public void testQotGetRehab() throws IOException{
		QotGetRehab.Response value12 = session.qotGetRehab(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"});
		System.out.println(value12);
	}
	
	//获取市场交易日
	@Test
	public void testQotGetTradeDate() throws IOException{
		QotGetTradeDate.Response value13 = session.qotGetTradeDate(QotMarket.QotMarket_HK_Security, "2018-08-10","2018-08-20");
		System.out.println(value13);
	}
	
	//获取股票静态信息
	@Test
	public void testQotGetStaticInfo() throws IOException{
		QotGetStaticInfo.Response value14 = session.qotGetStaticInfo(QotMarket.QotMarket_HK_Security, SecurityType.SecurityType_Eqty,new String[]{"00700","00005"});
		System.out.println(value14);
	}
	
	//获取股票快照
	@Test
	public void testQotGetSecuritySnapshot() throws IOException{
		QotGetSecuritySnapshot.Response value15 = session.qotGetSecuritySnapshot(QotMarket.QotMarket_HK_Security,new String[]{"00700","00005"});
		System.out.println(value15);
	}
	
	//板块
	@Test
	public void testqotGetPlateSet() throws IOException{
		//获取板块集合下的板块
		QotGetPlateSet.Response value16 = session.qotGetPlateSet(QotMarket.QotMarket_HK_Security,PlateSetType.PlateSetType_All);
		System.out.println(value16);
		//获取板块下的股票
		QotGetPlateSecurity.Response value17 = session.qotGetPlateSecurity(QotMarket.QotMarket_HK_Security, value16.getS2C().getPlateInfoList(0).getPlate().getCode());
		System.out.println(value17);
		
		System.out.println("输出中文名");
		for(SecurityStaticInfo info : value17.getS2C().getStaticInfoListList()){
			System.out.println(new String(info.getBasic().getName().getBytes(),"UTF-8"));
		}
	}
	
	//获取正股相关股票
	@Test
	public void testQotGetReference() throws IOException{
		QotGetReference.Response value18 = session.qotGetReference(QotMarket.QotMarket_HK_Security, "00700",ReferenceType.ReferenceType_Warrant);
		System.out.println(value18);
	}
	

	public static void main(String[] avgs) {
		Session session = FutuOpenD.openSession("localhost", 11111);
		try {
			long futuUserID = 297743;//你的富途牛牛号
			//全局状态
			System.out.println("全局状态");
			System.out.println(session.getGlobalState(futuUserID));
			//订阅详情
			System.out.println("订阅详情");
			System.out.println(session.qotGetSubInfo(false));
			
			//订阅股票
			QotSub.Response value = session.qotSub(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"},new SubType[]{SubType.SubType_Basic,SubType.SubType_Ticker,SubType.SubType_KL_1Min,SubType.SubType_KL_Day,SubType.SubType_RT,SubType.SubType_OrderBook,SubType.SubType_Broker});
			System.out.println("订阅股票");
			System.out.println(value);
			
			//基本报价，不处理推送
			QotGetBasicQot.Response value2 = session.stockBasicInfo(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"},null);
			System.out.println("基本报价");
			System.out.println(value2);
			
			//逐笔，并处理推送
			QotGetTicker.Response value3 = session.qotGetTicker(QotMarket.QotMarket_HK_Security, "00700", 2,new IUpdateCallBack<List<Ticker>>() {
				@Override
				public void callback(List<Ticker> res) {
					System.out.println("逐笔推送:00700");
					for(Ticker qot : res)
						System.out.println(qot.toString());
					
				}
			});
			System.out.println("逐笔");
			System.out.println(value3);
			
			//逐笔，无处理推送
			value3 = session.qotGetTicker(QotMarket.QotMarket_HK_Security, "00005", 2,null);
			System.out.println("逐笔");
			System.out.println(value3);
			
			//订阅推送(基本报价与逐笔)
			QotRegQotPush.Response value4 = session.qotRegQotPush(QotMarket.QotMarket_HK_Security, new String[]{"00700"}, new SubType[]{SubType.SubType_Basic,SubType.SubType_Ticker,SubType.SubType_KL_1Min,SubType.SubType_KL_Day,SubType.SubType_RT,SubType.SubType_OrderBook,SubType.SubType_Broker});
			System.out.println("订阅推送");
			System.out.println(value4);
			
			//K线，并处理推送
			QotGetKL.Response value5 = session.qotGetKL(QotMarket.QotMarket_HK_Security, "00700", RehabType.RehabType_Forward, KLType.KLType_1Min, 2, new IUpdateCallBack<List<KLine>>(){
				@Override
				public void callback(List<KLine> res) {
					System.out.println("K线推送:00700");
					System.out.println(res);
				}
				
			});
			System.out.println("K线");
			System.out.println(value5);
			
			//分时，并处理推送
			QotGetRT.Response value6 = session.qotGetRT(QotMarket.QotMarket_HK_Security, "00700", new IUpdateCallBack<List<TimeShare>>(){
				@Override
				public void callback(List<TimeShare> res) {
					System.out.println("分时推送:00700");
					System.out.println(res);
				}
			});
			System.out.println("分时");
			System.out.println(value6.getS2C().getRtListList().get(0));
			
			//买卖盘，并处理推送
			QotGetOrderBook.Response value7 = session.qotGetOrderBook(QotMarket.QotMarket_HK_Security, "00700",2, new IUpdateCallBack<OrderBooks>(){
				@Override
				public void callback(OrderBooks res) {
					System.out.println("买盘推送:00700");
					System.out.println(res.getOrderBookBidList());
					System.out.println("卖盘推送:00700");
					System.out.println(res.getOrderBookAskList());
				}
			});
			System.out.println("买卖盘");
			System.out.println(value7);
			
			//经纪队列，并处理推送
			QotGetBroker.Response value8 = session.qotGetBroker(QotMarket.QotMarket_HK_Security, "00700", new IUpdateCallBack<Brokers>(){
				@Override
				public void callback(Brokers res) {
					System.out.println("经纪队列买盘推送:00700");
					System.out.println(res.getBrokerBidList());
					System.out.println("经纪队列卖盘推送:00700");
					System.out.println(res.getBrokerAskList());
				}
			});
			System.out.println("经纪队列");
			System.out.println(value8.getS2C().getBrokerAskListList().get(0));
			System.out.println(value8.getS2C().getBrokerBidListList().get(0));
			
			//单只股票一段历史K线(请选下载历史数据)
			QotGetHistoryKL.Response value9 = session.qotGetHistoryKL(QotMarket.QotMarket_HK_Security, "00700",RehabType.RehabType_Forward, KLType.KLType_Day,"2018-07-01","2018-07-30", -1,-1);
			System.out.println("单只股票一段历史K线");
			System.out.println(value9);
			
			//单只股票一段历史K线,部分字段(请选下载历史数据)
			QotGetHistoryKL.Response value10 = session.qotGetHistoryKL(QotMarket.QotMarket_HK_Security, "00700",RehabType.RehabType_Forward, KLType.KLType_Day,"2018-07-01","2018-07-30", -1,KLFields.KLFields_Open_VALUE|KLFields.KLFields_Close_VALUE|KLFields.KLFields_Low_VALUE|KLFields.KLFields_High_VALUE);
			System.out.println("单只股票一段历史K线,部分字段");
			System.out.println(value10);
			
			//获取多只股票多点历史K线(请选下载历史数据)
			QotGetHistoryKLPoints.Response value11 = session.qotGetHistoryKLPoints(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"},RehabType.RehabType_Forward, KLType.KLType_Day,new String[]{"2018-07-01","2018-07-30"},NoDataMode.NoDataMode_Forward, -1,-1);
			System.out.println("获取多只股票多点历史K线");
			System.out.println(value11);
			
			//获取复权信息
			QotGetRehab.Response value12 = session.qotGetRehab(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"});
			System.out.println("获取复权信息");
			System.out.println(value12);
			
			//获取市场交易日
			QotGetTradeDate.Response value13 = session.qotGetTradeDate(QotMarket.QotMarket_HK_Security, "2018-08-10","2018-08-20");
			System.out.println("获取市场交易日");
			System.out.println(value13);
			
			//获取股票静态信息
			QotGetStaticInfo.Response value14 = session.qotGetStaticInfo(QotMarket.QotMarket_HK_Security, SecurityType.SecurityType_Eqty,new String[]{"00700","00005"});
			System.out.println("获取股票静态信息");
			System.out.println(value14);
			
			//获取股票快照
			QotGetSecuritySnapshot.Response value15 = session.qotGetSecuritySnapshot(QotMarket.QotMarket_HK_Security,new String[]{"00700","00005"});
			System.out.println("获取股票快照");
			System.out.println(value15);
			
			
			System.out.println("获取板块集合下的板块");
			//获取板块集合下的板块
			QotGetPlateSet.Response value16 = session.qotGetPlateSet(QotMarket.QotMarket_HK_Security,PlateSetType.PlateSetType_All);
			System.out.println(value16.getS2C().getPlateInfoList(0));
			//获取板块下的股票
			QotGetPlateSecurity.Response value17 = session.qotGetPlateSecurity(QotMarket.QotMarket_HK_Security, value16.getS2C().getPlateInfoList(0).getPlate().getCode());
			//System.out.println(value17);
			System.out.println("获取板块下的股票");
			//转中文
			for(SecurityStaticInfo info : value17.getS2C().getStaticInfoListList()){
				System.out.println(new String(info.getBasic().getName().getBytes(),"UTF-8"));
			}
			
			//获取正股相关股票
			QotGetReference.Response value18 = session.qotGetReference(QotMarket.QotMarket_HK_Security, "00700",ReferenceType.ReferenceType_Warrant);
			System.out.println("获取正股相关股票");
			System.out.println(value18.getS2C().getStaticInfoListList().get(0));
			
			Thread.sleep(60*1000);
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
