## java-for-FutuOpenD
FutuQuant量化接口的Java版本

## 运行
- jdk1.8+
- 导入eclipse
- 运行TestCase


## 功能
- FutuOpenD客户端为1.0.8
- 完成基础定义与行情协议部分
- 完成交易协议

## 更新
- 2018-12-14 Api升级到FutuOpenD-1.08(1.08取消了历史数据全量下载功能,增加新的qotRequestHistoryKL直接调用,请升级到1.08)
- 2018-08-11 完成交易协议接口
- 2018-08-09 完成基础定义与行情协议接口
- 2018-08-06 完成基础功能，跑通流程 
	
## API举例
API行情接口详细调用例程参见 com.test.TestCase<br>
API交易接口详细调用例程参见 com.test.TraderTestCase<br>

行情api<br>
```
Session session = FutuOpenD.openSession("localhost", 11111);
//订阅股票
session.qotSub(QotMarket.QotMarket_HK_Security, new String[]{"00700","00005"},new SubType[]	{SubType.SubType_Basic,SubType.SubType_Ticker,SubType.SubType_KL_1Min,SubType.SubType_KL_Day,SubType.SubType_RT,SubType.SubType_OrderBook,SubType.SubType_Broker});
//订阅推送
session.qotRegQotPush(QotMarket.QotMarket_HK_Security, new String[]{"00700"}, new SubType[]{SubType.SubType_Basic,SubType.SubType_Ticker,SubType.SubType_KL_1Min,SubType.SubType_KL_Day,SubType.SubType_RT,SubType.SubType_OrderBook,SubType.SubType_Broker});
//分时(含回调)
session.qotGetRT(QotMarket.QotMarket_HK_Security, "00700", new IUpdateCallBack<List<TimeShare>>(){
	@Override
	public void callback(List<TimeShare> res) {
	}
});		
session.close();
```	

交易api<br>
```
Session session = FutuOpenD.openSession("localhost", 11111);
//解锁交易，自动打开交易推送功能
//TraderSession traderSession =  session.trdUnlockTradeForReal(this.futuUserID,pwdMD5);//实盘交易
//模拟交易
TraderSession traderSession =  session.trdUnlockTradeForSimulate(this.futuUserID,pwdMD5);
//下单
traderSession.trdPlaceOrder(TrdMarket.TrdMarket_HK,TrdSide.TrdSide_Buy,OrderType.OrderType_Normal,"00700",100,200,null,null,null);
session.close();
```	
## 参考
[FutuQuant富途量化投资平台官方python版](https://github.com/FutunnOpen/futuquant)<br>
[FutuQuant协议接口指南](https://futunnopen.github.io/futuquant/protocol/intro.html)
