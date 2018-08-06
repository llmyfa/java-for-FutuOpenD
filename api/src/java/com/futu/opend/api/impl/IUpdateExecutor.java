package com.futu.opend.api.impl;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 推送处理接口，如果api含有数据推送功能要实现此接口
 * 如:
 *  3004获取股票基本行情 对应推送协议为 3005推送股票基本报价 
 *  3008获取分时 对应推送协议为 3009推送分时 
 * @author jun
 *
 * @param <T>
 */
interface IUpdateExecutor<T> extends IExecutor{
	
	/**
	 * 解析推送数据
	 * @param pack
	 * @return
	 * @throws InvalidProtocolBufferException
	 */
	public T parse(ProtoBufPackage pack) throws InvalidProtocolBufferException;
	
	/**
	 * 推送数据不确定，此方法的作用是过滤,寻找匹配的数据后再由用户处理业务
	 * @param t
	 */
	public void handler(T res);
	
	/**
	 * 推送数据协议ID
	 * 因为推送数据没有显示的api调用。只能对应api提供回调功能处理数据
	 *  如:
	 *  3004获取股票基本行情 的推送协议为  3005推送股票基本报价  
	 *  33008获取分时 的推送协议为 009推送分时
	 * @return
	 */
	public int getnUpdateProtoID();

}
