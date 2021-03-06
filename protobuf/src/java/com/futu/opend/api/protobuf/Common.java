// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Common.proto

package com.futu.opend.api.protobuf;

public final class Common {
  private Common() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  /**
   * Protobuf enum {@code Common.RetType}
   *
   * <pre>
   *返回结果
   * </pre>
   */
  public enum RetType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>RetType_Succeed = 0;</code>
     *
     * <pre>
     *成功
     * </pre>
     */
    RetType_Succeed(0, 0),
    /**
     * <code>RetType_Failed = -1;</code>
     *
     * <pre>
     *失败
     * </pre>
     */
    RetType_Failed(1, -1),
    /**
     * <code>RetType_TimeOut = -100;</code>
     *
     * <pre>
     *超时
     * </pre>
     */
    RetType_TimeOut(2, -100),
    /**
     * <code>RetType_Unknown = -400;</code>
     *
     * <pre>
     *未知结果
     * </pre>
     */
    RetType_Unknown(3, -400),
    ;

    /**
     * <code>RetType_Succeed = 0;</code>
     *
     * <pre>
     *成功
     * </pre>
     */
    public static final int RetType_Succeed_VALUE = 0;
    /**
     * <code>RetType_Failed = -1;</code>
     *
     * <pre>
     *失败
     * </pre>
     */
    public static final int RetType_Failed_VALUE = -1;
    /**
     * <code>RetType_TimeOut = -100;</code>
     *
     * <pre>
     *超时
     * </pre>
     */
    public static final int RetType_TimeOut_VALUE = -100;
    /**
     * <code>RetType_Unknown = -400;</code>
     *
     * <pre>
     *未知结果
     * </pre>
     */
    public static final int RetType_Unknown_VALUE = -400;


    public final int getNumber() { return value; }

    public static RetType valueOf(int value) {
      switch (value) {
        case 0: return RetType_Succeed;
        case -1: return RetType_Failed;
        case -100: return RetType_TimeOut;
        case -400: return RetType_Unknown;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<RetType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<RetType>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<RetType>() {
            public RetType findValueByNumber(int number) {
              return RetType.valueOf(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.futu.opend.api.protobuf.Common.getDescriptor().getEnumTypes().get(0);
    }

    private static final RetType[] VALUES = values();

    public static RetType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;
    private final int value;

    private RetType(int index, int value) {
      this.index = index;
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:Common.RetType)
  }

  public interface PacketIDOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Common.PacketID)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required uint64 connID = 1;</code>
     *
     * <pre>
     *当前TCP连接的连接ID，一条连接的唯一标识，InitConnect协议会返回
     * </pre>
     */
    boolean hasConnID();
    /**
     * <code>required uint64 connID = 1;</code>
     *
     * <pre>
     *当前TCP连接的连接ID，一条连接的唯一标识，InitConnect协议会返回
     * </pre>
     */
    long getConnID();

    /**
     * <code>required uint32 serialNo = 2;</code>
     *
     * <pre>
     *包头中的包自增序列号
     * </pre>
     */
    boolean hasSerialNo();
    /**
     * <code>required uint32 serialNo = 2;</code>
     *
     * <pre>
     *包头中的包自增序列号
     * </pre>
     */
    int getSerialNo();
  }
  /**
   * Protobuf type {@code Common.PacketID}
   *
   * <pre>
   *包的唯一标识，用于回放攻击的识别和保护
   * </pre>
   */
  public static final class PacketID extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:Common.PacketID)
      PacketIDOrBuilder {
    // Use PacketID.newBuilder() to construct.
    private PacketID(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private PacketID(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final PacketID defaultInstance;
    public static PacketID getDefaultInstance() {
      return defaultInstance;
    }

    public PacketID getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private PacketID(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              connID_ = input.readUInt64();
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              serialNo_ = input.readUInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.futu.opend.api.protobuf.Common.internal_static_Common_PacketID_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.futu.opend.api.protobuf.Common.internal_static_Common_PacketID_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.futu.opend.api.protobuf.Common.PacketID.class, com.futu.opend.api.protobuf.Common.PacketID.Builder.class);
    }

    public static com.google.protobuf.Parser<PacketID> PARSER =
        new com.google.protobuf.AbstractParser<PacketID>() {
      public PacketID parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new PacketID(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<PacketID> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int CONNID_FIELD_NUMBER = 1;
    private long connID_;
    /**
     * <code>required uint64 connID = 1;</code>
     *
     * <pre>
     *当前TCP连接的连接ID，一条连接的唯一标识，InitConnect协议会返回
     * </pre>
     */
    public boolean hasConnID() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required uint64 connID = 1;</code>
     *
     * <pre>
     *当前TCP连接的连接ID，一条连接的唯一标识，InitConnect协议会返回
     * </pre>
     */
    public long getConnID() {
      return connID_;
    }

    public static final int SERIALNO_FIELD_NUMBER = 2;
    private int serialNo_;
    /**
     * <code>required uint32 serialNo = 2;</code>
     *
     * <pre>
     *包头中的包自增序列号
     * </pre>
     */
    public boolean hasSerialNo() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required uint32 serialNo = 2;</code>
     *
     * <pre>
     *包头中的包自增序列号
     * </pre>
     */
    public int getSerialNo() {
      return serialNo_;
    }

    private void initFields() {
      connID_ = 0L;
      serialNo_ = 0;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasConnID()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasSerialNo()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeUInt64(1, connID_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeUInt32(2, serialNo_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt64Size(1, connID_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(2, serialNo_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.futu.opend.api.protobuf.Common.PacketID parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.futu.opend.api.protobuf.Common.PacketID parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.futu.opend.api.protobuf.Common.PacketID parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.futu.opend.api.protobuf.Common.PacketID parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.futu.opend.api.protobuf.Common.PacketID parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.futu.opend.api.protobuf.Common.PacketID parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.futu.opend.api.protobuf.Common.PacketID parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.futu.opend.api.protobuf.Common.PacketID parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.futu.opend.api.protobuf.Common.PacketID parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.futu.opend.api.protobuf.Common.PacketID parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.futu.opend.api.protobuf.Common.PacketID prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Common.PacketID}
     *
     * <pre>
     *包的唯一标识，用于回放攻击的识别和保护
     * </pre>
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Common.PacketID)
        com.futu.opend.api.protobuf.Common.PacketIDOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.futu.opend.api.protobuf.Common.internal_static_Common_PacketID_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.futu.opend.api.protobuf.Common.internal_static_Common_PacketID_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.futu.opend.api.protobuf.Common.PacketID.class, com.futu.opend.api.protobuf.Common.PacketID.Builder.class);
      }

      // Construct using com.futu.opend.api.protobuf.Common.PacketID.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        connID_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000001);
        serialNo_ = 0;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.futu.opend.api.protobuf.Common.internal_static_Common_PacketID_descriptor;
      }

      public com.futu.opend.api.protobuf.Common.PacketID getDefaultInstanceForType() {
        return com.futu.opend.api.protobuf.Common.PacketID.getDefaultInstance();
      }

      public com.futu.opend.api.protobuf.Common.PacketID build() {
        com.futu.opend.api.protobuf.Common.PacketID result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.futu.opend.api.protobuf.Common.PacketID buildPartial() {
        com.futu.opend.api.protobuf.Common.PacketID result = new com.futu.opend.api.protobuf.Common.PacketID(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.connID_ = connID_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.serialNo_ = serialNo_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.futu.opend.api.protobuf.Common.PacketID) {
          return mergeFrom((com.futu.opend.api.protobuf.Common.PacketID)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.futu.opend.api.protobuf.Common.PacketID other) {
        if (other == com.futu.opend.api.protobuf.Common.PacketID.getDefaultInstance()) return this;
        if (other.hasConnID()) {
          setConnID(other.getConnID());
        }
        if (other.hasSerialNo()) {
          setSerialNo(other.getSerialNo());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasConnID()) {
          
          return false;
        }
        if (!hasSerialNo()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.futu.opend.api.protobuf.Common.PacketID parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.futu.opend.api.protobuf.Common.PacketID) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private long connID_ ;
      /**
       * <code>required uint64 connID = 1;</code>
       *
       * <pre>
       *当前TCP连接的连接ID，一条连接的唯一标识，InitConnect协议会返回
       * </pre>
       */
      public boolean hasConnID() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required uint64 connID = 1;</code>
       *
       * <pre>
       *当前TCP连接的连接ID，一条连接的唯一标识，InitConnect协议会返回
       * </pre>
       */
      public long getConnID() {
        return connID_;
      }
      /**
       * <code>required uint64 connID = 1;</code>
       *
       * <pre>
       *当前TCP连接的连接ID，一条连接的唯一标识，InitConnect协议会返回
       * </pre>
       */
      public Builder setConnID(long value) {
        bitField0_ |= 0x00000001;
        connID_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required uint64 connID = 1;</code>
       *
       * <pre>
       *当前TCP连接的连接ID，一条连接的唯一标识，InitConnect协议会返回
       * </pre>
       */
      public Builder clearConnID() {
        bitField0_ = (bitField0_ & ~0x00000001);
        connID_ = 0L;
        onChanged();
        return this;
      }

      private int serialNo_ ;
      /**
       * <code>required uint32 serialNo = 2;</code>
       *
       * <pre>
       *包头中的包自增序列号
       * </pre>
       */
      public boolean hasSerialNo() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required uint32 serialNo = 2;</code>
       *
       * <pre>
       *包头中的包自增序列号
       * </pre>
       */
      public int getSerialNo() {
        return serialNo_;
      }
      /**
       * <code>required uint32 serialNo = 2;</code>
       *
       * <pre>
       *包头中的包自增序列号
       * </pre>
       */
      public Builder setSerialNo(int value) {
        bitField0_ |= 0x00000002;
        serialNo_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required uint32 serialNo = 2;</code>
       *
       * <pre>
       *包头中的包自增序列号
       * </pre>
       */
      public Builder clearSerialNo() {
        bitField0_ = (bitField0_ & ~0x00000002);
        serialNo_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:Common.PacketID)
    }

    static {
      defaultInstance = new PacketID(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:Common.PacketID)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Common_PacketID_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_Common_PacketID_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014Common.proto\022\006Common\",\n\010PacketID\022\016\n\006co" +
      "nnID\030\001 \002(\004\022\020\n\010serialNo\030\002 \002(\r*w\n\007RetType\022" +
      "\023\n\017RetType_Succeed\020\000\022\033\n\016RetType_Failed\020\377" +
      "\377\377\377\377\377\377\377\377\001\022\034\n\017RetType_TimeOut\020\234\377\377\377\377\377\377\377\377\001\022" +
      "\034\n\017RetType_Unknown\020\360\374\377\377\377\377\377\377\377\001B\035\n\033com.fut" +
      "u.opend.api.protobuf"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Common_PacketID_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Common_PacketID_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_Common_PacketID_descriptor,
        new java.lang.String[] { "ConnID", "SerialNo", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
