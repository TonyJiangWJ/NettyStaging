package com.tony.message.protobuff;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MessageDto.proto

public final class MessageDtoOuterClass {
    private MessageDtoOuterClass() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public interface MessageDtoOrBuilder extends
            // @@protoc_insertion_point(interface_extends:MessageDto)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>string action = 1;</code>
         *
         * @return The action.
         */
        String getAction();

        /**
         * <code>string action = 1;</code>
         *
         * @return The bytes for action.
         */
        com.google.protobuf.ByteString
        getActionBytes();

        /**
         * <code>int32 state = 2;</code>
         *
         * @return The state.
         */
        int getState();

        /**
         * <code>.google.protobuf.Any data = 3;</code>
         *
         * @return Whether the data field is set.
         */
        boolean hasData();

        /**
         * <code>.google.protobuf.Any data = 3;</code>
         *
         * @return The data.
         */
        com.google.protobuf.Any getData();

        /**
         * <code>.google.protobuf.Any data = 3;</code>
         */
        com.google.protobuf.AnyOrBuilder getDataOrBuilder();
    }

    /**
     * Protobuf type {@code MessageDto}
     */
    public static final class MessageDto extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:MessageDto)
            MessageDtoOrBuilder {
        private static final long serialVersionUID = 0L;

        // Use MessageDto.newBuilder() to construct.
        private MessageDto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private MessageDto() {
            action_ = "";
        }

        @Override
        @SuppressWarnings({"unused"})
        protected Object newInstance(
                UnusedPrivateParameter unused) {
            return new MessageDto();
        }

        @Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        private MessageDto(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
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
                        case 10: {
                            String s = input.readStringRequireUtf8();

                            action_ = s;
                            break;
                        }
                        case 16: {

                            state_ = input.readInt32();
                            break;
                        }
                        case 26: {
                            com.google.protobuf.Any.Builder subBuilder = null;
                            if (data_ != null) {
                                subBuilder = data_.toBuilder();
                            }
                            data_ = input.readMessage(com.google.protobuf.Any.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(data_);
                                data_ = subBuilder.buildPartial();
                            }

                            break;
                        }
                        default: {
                            if (!parseUnknownField(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return MessageDtoOuterClass.internal_static_MessageDto_descriptor;
        }

        @Override
        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return MessageDtoOuterClass.internal_static_MessageDto_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            MessageDto.class, Builder.class);
        }

        public static final int ACTION_FIELD_NUMBER = 1;
        private volatile Object action_;

        /**
         * <code>string action = 1;</code>
         *
         * @return The action.
         */
        @Override
        public String getAction() {
            Object ref = action_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                String s = bs.toStringUtf8();
                action_ = s;
                return s;
            }
        }

        /**
         * <code>string action = 1;</code>
         *
         * @return The bytes for action.
         */
        @Override
        public com.google.protobuf.ByteString
        getActionBytes() {
            Object ref = action_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (String) ref);
                action_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        public static final int STATE_FIELD_NUMBER = 2;
        private int state_;

        /**
         * <code>int32 state = 2;</code>
         *
         * @return The state.
         */
        @Override
        public int getState() {
            return state_;
        }

        public static final int DATA_FIELD_NUMBER = 3;
        private com.google.protobuf.Any data_;

        /**
         * <code>.google.protobuf.Any data = 3;</code>
         *
         * @return Whether the data field is set.
         */
        @Override
        public boolean hasData() {
            return data_ != null;
        }

        /**
         * <code>.google.protobuf.Any data = 3;</code>
         *
         * @return The data.
         */
        @Override
        public com.google.protobuf.Any getData() {
            return data_ == null ? com.google.protobuf.Any.getDefaultInstance() : data_;
        }

        /**
         * <code>.google.protobuf.Any data = 3;</code>
         */
        @Override
        public com.google.protobuf.AnyOrBuilder getDataOrBuilder() {
            return getData();
        }

        private byte memoizedIsInitialized = -1;

        @Override
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }

            memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            if (!getActionBytes().isEmpty()) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 1, action_);
            }
            if (state_ != 0) {
                output.writeInt32(2, state_);
            }
            if (data_ != null) {
                output.writeMessage(3, getData());
            }
            unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) {
                return size;
            }

            size = 0;
            if (!getActionBytes().isEmpty()) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, action_);
            }
            if (state_ != 0) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(2, state_);
            }
            if (data_ != null) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(3, getData());
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MessageDto)) {
                return super.equals(obj);
            }
            MessageDto other = (MessageDto) obj;

            if (!getAction()
                    .equals(other.getAction())) {
                return false;
            }
            if (getState()
                    != other.getState()) {
                return false;
            }
            if (hasData() != other.hasData()) {
                return false;
            }
            if (hasData()) {
                if (!getData()
                        .equals(other.getData())) {
                    return false;
                }
            }
            if (!unknownFields.equals(other.unknownFields)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            hash = (37 * hash) + ACTION_FIELD_NUMBER;
            hash = (53 * hash) + getAction().hashCode();
            hash = (37 * hash) + STATE_FIELD_NUMBER;
            hash = (53 * hash) + getState();
            if (hasData()) {
                hash = (37 * hash) + DATA_FIELD_NUMBER;
                hash = (53 * hash) + getData().hashCode();
            }
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static MessageDto parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MessageDto parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MessageDto parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MessageDto parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MessageDto parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static MessageDto parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static MessageDto parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static MessageDto parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static MessageDto parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static MessageDto parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static MessageDto parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static MessageDto parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(MessageDto prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(
                BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /**
         * Protobuf type {@code MessageDto}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:MessageDto)
                MessageDtoOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return MessageDtoOuterClass.internal_static_MessageDto_descriptor;
            }

            @Override
            protected FieldAccessorTable
            internalGetFieldAccessorTable() {
                return MessageDtoOuterClass.internal_static_MessageDto_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                MessageDto.class, Builder.class);
            }

            // Construct using MessageDtoOuterClass.MessageDto.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                action_ = "";

                state_ = 0;

                if (dataBuilder_ == null) {
                    data_ = null;
                } else {
                    data_ = null;
                    dataBuilder_ = null;
                }
                return this;
            }

            @Override
            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return MessageDtoOuterClass.internal_static_MessageDto_descriptor;
            }

            @Override
            public MessageDto getDefaultInstanceForType() {
                return MessageDto.getDefaultInstance();
            }

            @Override
            public MessageDto build() {
                MessageDto result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public MessageDto buildPartial() {
                MessageDto result = new MessageDto(this);
                result.action_ = action_;
                result.state_ = state_;
                if (dataBuilder_ == null) {
                    result.data_ = data_;
                } else {
                    result.data_ = dataBuilder_.build();
                }
                onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return super.clone();
            }

            @Override
            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.setField(field, value);
            }

            @Override
            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return super.clearField(field);
            }

            @Override
            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, Object value) {
                return super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof MessageDto) {
                    return mergeFrom((MessageDto) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(MessageDto other) {
                if (other == MessageDto.getDefaultInstance()) {
                    return this;
                }
                if (!other.getAction().isEmpty()) {
                    action_ = other.action_;
                    onChanged();
                }
                if (other.getState() != 0) {
                    setState(other.getState());
                }
                if (other.hasData()) {
                    mergeData(other.getData());
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                MessageDto parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (MessageDto) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private Object action_ = "";

            /**
             * <code>string action = 1;</code>
             *
             * @return The action.
             */
            @Override
            public String getAction() {
                Object ref = action_;
                if (!(ref instanceof String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    String s = bs.toStringUtf8();
                    action_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>string action = 1;</code>
             *
             * @return The bytes for action.
             */
            @Override
            public com.google.protobuf.ByteString
            getActionBytes() {
                Object ref = action_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (String) ref);
                    action_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <code>string action = 1;</code>
             *
             * @param value The action to set.
             * @return This builder for chaining.
             */
            public Builder setAction(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                action_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>string action = 1;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearAction() {

                action_ = getDefaultInstance().getAction();
                onChanged();
                return this;
            }

            /**
             * <code>string action = 1;</code>
             *
             * @param value The bytes for action to set.
             * @return This builder for chaining.
             */
            public Builder setActionBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                checkByteStringIsUtf8(value);

                action_ = value;
                onChanged();
                return this;
            }

            private int state_;

            /**
             * <code>int32 state = 2;</code>
             *
             * @return The state.
             */
            @Override
            public int getState() {
                return state_;
            }

            /**
             * <code>int32 state = 2;</code>
             *
             * @param value The state to set.
             * @return This builder for chaining.
             */
            public Builder setState(int value) {

                state_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>int32 state = 2;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearState() {

                state_ = 0;
                onChanged();
                return this;
            }

            private com.google.protobuf.Any data_;
            private com.google.protobuf.SingleFieldBuilderV3<
                    com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> dataBuilder_;

            /**
             * <code>.google.protobuf.Any data = 3;</code>
             *
             * @return Whether the data field is set.
             */
            @Override
            public boolean hasData() {
                return dataBuilder_ != null || data_ != null;
            }

            /**
             * <code>.google.protobuf.Any data = 3;</code>
             *
             * @return The data.
             */
            @Override
            public com.google.protobuf.Any getData() {
                if (dataBuilder_ == null) {
                    return data_ == null ? com.google.protobuf.Any.getDefaultInstance() : data_;
                } else {
                    return dataBuilder_.getMessage();
                }
            }

            /**
             * <code>.google.protobuf.Any data = 3;</code>
             */
            public Builder setData(com.google.protobuf.Any value) {
                if (dataBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    data_ = value;
                    onChanged();
                } else {
                    dataBuilder_.setMessage(value);
                }

                return this;
            }

            /**
             * <code>.google.protobuf.Any data = 3;</code>
             */
            public Builder setData(
                    com.google.protobuf.Any.Builder builderForValue) {
                if (dataBuilder_ == null) {
                    data_ = builderForValue.build();
                    onChanged();
                } else {
                    dataBuilder_.setMessage(builderForValue.build());
                }

                return this;
            }

            /**
             * <code>.google.protobuf.Any data = 3;</code>
             */
            public Builder mergeData(com.google.protobuf.Any value) {
                if (dataBuilder_ == null) {
                    if (data_ != null) {
                        data_ =
                                com.google.protobuf.Any.newBuilder(data_).mergeFrom(value).buildPartial();
                    } else {
                        data_ = value;
                    }
                    onChanged();
                } else {
                    dataBuilder_.mergeFrom(value);
                }

                return this;
            }

            /**
             * <code>.google.protobuf.Any data = 3;</code>
             */
            public Builder clearData() {
                if (dataBuilder_ == null) {
                    data_ = null;
                    onChanged();
                } else {
                    data_ = null;
                    dataBuilder_ = null;
                }

                return this;
            }

            /**
             * <code>.google.protobuf.Any data = 3;</code>
             */
            public com.google.protobuf.Any.Builder getDataBuilder() {

                onChanged();
                return getDataFieldBuilder().getBuilder();
            }

            /**
             * <code>.google.protobuf.Any data = 3;</code>
             */
            @Override
            public com.google.protobuf.AnyOrBuilder getDataOrBuilder() {
                if (dataBuilder_ != null) {
                    return dataBuilder_.getMessageOrBuilder();
                } else {
                    return data_ == null ?
                            com.google.protobuf.Any.getDefaultInstance() : data_;
                }
            }

            /**
             * <code>.google.protobuf.Any data = 3;</code>
             */
            private com.google.protobuf.SingleFieldBuilderV3<
                    com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>
            getDataFieldBuilder() {
                if (dataBuilder_ == null) {
                    dataBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
                            com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>(
                            getData(),
                            getParentForChildren(),
                            isClean());
                    data_ = null;
                }
                return dataBuilder_;
            }

            @Override
            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:MessageDto)
        }

        // @@protoc_insertion_point(class_scope:MessageDto)
        private static final MessageDto DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new MessageDto();
        }

        public static MessageDto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        private static final com.google.protobuf.Parser<MessageDto>
                PARSER = new com.google.protobuf.AbstractParser<MessageDto>() {
            @Override
            public MessageDto parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new MessageDto(input, extensionRegistry);
            }
        };

        public static com.google.protobuf.Parser<MessageDto> parser() {
            return PARSER;
        }

        @Override
        public com.google.protobuf.Parser<MessageDto> getParserForType() {
            return PARSER;
        }

        @Override
        public MessageDto getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_MessageDto_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_MessageDto_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        String[] descriptorData = {
                "\n\020MessageDto.proto\032\031google/protobuf/any." +
                        "proto\"O\n\nMessageDto\022\016\n\006action\030\001 \001(\t\022\r\n\005s" +
                        "tate\030\002 \001(\005\022\"\n\004data\030\003 \001(\0132\024.google.protob" +
                        "uf.Anyb\006proto3"
        };
        descriptor = com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                                com.google.protobuf.AnyProto.getDescriptor(),
                        });
        internal_static_MessageDto_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_MessageDto_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_MessageDto_descriptor,
                new String[]{"Action", "State", "Data",});
        com.google.protobuf.AnyProto.getDescriptor();
    }

    // @@protoc_insertion_point(outer_class_scope)
}
