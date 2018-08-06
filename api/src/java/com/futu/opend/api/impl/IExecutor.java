package com.futu.opend.api.impl;

import com.google.protobuf.InvalidProtocolBufferException;

interface IExecutor {
	
	public int getnProtoID();
	
	public ProtoBufPackage buildPackage();

	public void execute(ProtoBufPackage pack) throws InvalidProtocolBufferException;
	
	public Object getValue();
}
