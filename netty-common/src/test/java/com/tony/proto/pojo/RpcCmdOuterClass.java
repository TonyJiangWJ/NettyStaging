package com.tony.proto.pojo;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RpcCmd.proto

public final class RpcCmdOuterClass {
    private RpcCmdOuterClass() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public interface RpcCmdOrBuilder extends
            // @@protoc_insertion_point(interface_extends:RpcCmd)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>.MessageDto message = 1;</code>
         *
         * @return Whether the message field is set.
         */
        boolean hasMessage();

        /**
         * <code>.MessageDto message = 1;</code>
         *
         * @return The message.
         */
        MessageDtoOuterClass.MessageDto getMessage();

        /**
         * <code>.MessageDto message = 1;</code>
         */
        MessageDtoOuterClass.MessageDtoOrBuilder getMessageOrBuilder();

        /**
         * <code>string randomKey = 2;</code>
         *
         * @return The randomKey.
         */
        String getRandomKey();

        /**
         * <code>string randomKey = 2;</code>
         *
         * @return The bytes for randomKey.
         */
        com.google.protobuf.ByteString
        getRandomKeyBytes();

        /**
         * <code>string remoteAddressKey = 3;</code>
         *
         * @return The remoteAddressKey.
         */
        String getRemoteAddressKey();

        /**
         * <code>string remoteAddressKey = 3;</code>
         *
         * @return The bytes for remoteAddressKey.
         */
        com.google.protobuf.ByteString
        getRemoteAddressKeyBytes();
    }

    /**
     * Protobuf type {@code RpcCmd}
     */
    public static final class RpcCmd extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:RpcCmd)
            RpcCmdOrBuilder {
        private static final long serialVersionUID = 0L;

        // Use RpcCmd.newBuilder() to construct.
        private RpcCmd(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private RpcCmd() {
            randomKey_ = "";
            remoteAddressKey_ = "";
        }

        @Override
        @SuppressWarnings({"unused"})
        protected Object newInstance(
                UnusedPrivateParameter unused) {
            return new RpcCmd();
        }

        @Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        private RpcCmd(
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
                            MessageDtoOuterClass.MessageDto.Builder subBuilder = null;
                            if (message_ != null) {
                                subBuilder = message_.toBuilder();
                            }
                            message_ = input.readMessage(MessageDtoOuterClass.MessageDto.parser(), extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(message_);
                                message_ = subBuilder.buildPartial();
                            }

                            break;
                        }
                        case 18: {
                            String s = input.readStringRequireUtf8();

                            randomKey_ = s;
                            break;
                        }
                        case 26: {
                            String s = input.readStringRequireUtf8();

                            remoteAddressKey_ = s;
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
            return RpcCmdOuterClass.internal_static_RpcCmd_descriptor;
        }

        @Override
        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return RpcCmdOuterClass.internal_static_RpcCmd_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            RpcCmd.class, Builder.class);
        }

        public static final int MESSAGE_FIELD_NUMBER = 1;
        private MessageDtoOuterClass.MessageDto message_;

        /**
         * <code>.MessageDto message = 1;</code>
         *
         * @return Whether the message field is set.
         */
        @Override
        public boolean hasMessage() {
            return message_ != null;
        }

        /**
         * <code>.MessageDto message = 1;</code>
         *
         * @return The message.
         */
        @Override
        public MessageDtoOuterClass.MessageDto getMessage() {
            return message_ == null ? MessageDtoOuterClass.MessageDto.getDefaultInstance() : message_;
        }

        /**
         * <code>.MessageDto message = 1;</code>
         */
        @Override
        public MessageDtoOuterClass.MessageDtoOrBuilder getMessageOrBuilder() {
            return getMessage();
        }

        public static final int RANDOMKEY_FIELD_NUMBER = 2;
        private volatile Object randomKey_;

        /**
         * <code>string randomKey = 2;</code>
         *
         * @return The randomKey.
         */
        @Override
        public String getRandomKey() {
            Object ref = randomKey_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                String s = bs.toStringUtf8();
                randomKey_ = s;
                return s;
            }
        }

        /**
         * <code>string randomKey = 2;</code>
         *
         * @return The bytes for randomKey.
         */
        @Override
        public com.google.protobuf.ByteString
        getRandomKeyBytes() {
            Object ref = randomKey_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (String) ref);
                randomKey_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        public static final int REMOTEADDRESSKEY_FIELD_NUMBER = 3;
        private volatile Object remoteAddressKey_;

        /**
         * <code>string remoteAddressKey = 3;</code>
         *
         * @return The remoteAddressKey.
         */
        @Override
        public String getRemoteAddressKey() {
            Object ref = remoteAddressKey_;
            if (ref instanceof String) {
                return (String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                String s = bs.toStringUtf8();
                remoteAddressKey_ = s;
                return s;
            }
        }

        /**
         * <code>string remoteAddressKey = 3;</code>
         *
         * @return The bytes for remoteAddressKey.
         */
        @Override
        public com.google.protobuf.ByteString
        getRemoteAddressKeyBytes() {
            Object ref = remoteAddressKey_;
            if (ref instanceof String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (String) ref);
                remoteAddressKey_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
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
            if (message_ != null) {
                output.writeMessage(1, getMessage());
            }
            if (!getRandomKeyBytes().isEmpty()) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 2, randomKey_);
            }
            if (!getRemoteAddressKeyBytes().isEmpty()) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 3, remoteAddressKey_);
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
            if (message_ != null) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(1, getMessage());
            }
            if (!getRandomKeyBytes().isEmpty()) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, randomKey_);
            }
            if (!getRemoteAddressKeyBytes().isEmpty()) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, remoteAddressKey_);
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
            if (!(obj instanceof RpcCmd)) {
                return super.equals(obj);
            }
            RpcCmd other = (RpcCmd) obj;

            if (hasMessage() != other.hasMessage()) {
                return false;
            }
            if (hasMessage()) {
                if (!getMessage()
                        .equals(other.getMessage())) {
                    return false;
                }
            }
            if (!getRandomKey()
                    .equals(other.getRandomKey())) {
                return false;
            }
            if (!getRemoteAddressKey()
                    .equals(other.getRemoteAddressKey())) {
                return false;
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
            if (hasMessage()) {
                hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
                hash = (53 * hash) + getMessage().hashCode();
            }
            hash = (37 * hash) + RANDOMKEY_FIELD_NUMBER;
            hash = (53 * hash) + getRandomKey().hashCode();
            hash = (37 * hash) + REMOTEADDRESSKEY_FIELD_NUMBER;
            hash = (53 * hash) + getRemoteAddressKey().hashCode();
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static RpcCmd parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RpcCmd parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RpcCmd parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RpcCmd parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RpcCmd parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RpcCmd parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RpcCmd parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static RpcCmd parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RpcCmd parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static RpcCmd parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RpcCmd parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static RpcCmd parseFrom(
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

        public static Builder newBuilder(RpcCmd prototype) {
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
         * Protobuf type {@code RpcCmd}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:RpcCmd)
                RpcCmdOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return RpcCmdOuterClass.internal_static_RpcCmd_descriptor;
            }

            @Override
            protected FieldAccessorTable
            internalGetFieldAccessorTable() {
                return RpcCmdOuterClass.internal_static_RpcCmd_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                RpcCmd.class, Builder.class);
            }

            // Construct using RpcCmdOuterClass.RpcCmd.newBuilder()
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
                if (messageBuilder_ == null) {
                    message_ = null;
                } else {
                    message_ = null;
                    messageBuilder_ = null;
                }
                randomKey_ = "";

                remoteAddressKey_ = "";

                return this;
            }

            @Override
            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return RpcCmdOuterClass.internal_static_RpcCmd_descriptor;
            }

            @Override
            public RpcCmd getDefaultInstanceForType() {
                return RpcCmd.getDefaultInstance();
            }

            @Override
            public RpcCmd build() {
                RpcCmd result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public RpcCmd buildPartial() {
                RpcCmd result = new RpcCmd(this);
                if (messageBuilder_ == null) {
                    result.message_ = message_;
                } else {
                    result.message_ = messageBuilder_.build();
                }
                result.randomKey_ = randomKey_;
                result.remoteAddressKey_ = remoteAddressKey_;
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
                if (other instanceof RpcCmd) {
                    return mergeFrom((RpcCmd) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(RpcCmd other) {
                if (other == RpcCmd.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMessage()) {
                    mergeMessage(other.getMessage());
                }
                if (!other.getRandomKey().isEmpty()) {
                    randomKey_ = other.randomKey_;
                    onChanged();
                }
                if (!other.getRemoteAddressKey().isEmpty()) {
                    remoteAddressKey_ = other.remoteAddressKey_;
                    onChanged();
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
                RpcCmd parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (RpcCmd) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private MessageDtoOuterClass.MessageDto message_;
            private com.google.protobuf.SingleFieldBuilderV3<
                    MessageDtoOuterClass.MessageDto, MessageDtoOuterClass.MessageDto.Builder, MessageDtoOuterClass.MessageDtoOrBuilder> messageBuilder_;

            /**
             * <code>.MessageDto message = 1;</code>
             *
             * @return Whether the message field is set.
             */
            @Override
            public boolean hasMessage() {
                return messageBuilder_ != null || message_ != null;
            }

            /**
             * <code>.MessageDto message = 1;</code>
             *
             * @return The message.
             */
            @Override
            public MessageDtoOuterClass.MessageDto getMessage() {
                if (messageBuilder_ == null) {
                    return message_ == null ? MessageDtoOuterClass.MessageDto.getDefaultInstance() : message_;
                } else {
                    return messageBuilder_.getMessage();
                }
            }

            /**
             * <code>.MessageDto message = 1;</code>
             */
            public Builder setMessage(MessageDtoOuterClass.MessageDto value) {
                if (messageBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    message_ = value;
                    onChanged();
                } else {
                    messageBuilder_.setMessage(value);
                }

                return this;
            }

            /**
             * <code>.MessageDto message = 1;</code>
             */
            public Builder setMessage(
                    MessageDtoOuterClass.MessageDto.Builder builderForValue) {
                if (messageBuilder_ == null) {
                    message_ = builderForValue.build();
                    onChanged();
                } else {
                    messageBuilder_.setMessage(builderForValue.build());
                }

                return this;
            }

            /**
             * <code>.MessageDto message = 1;</code>
             */
            public Builder mergeMessage(MessageDtoOuterClass.MessageDto value) {
                if (messageBuilder_ == null) {
                    if (message_ != null) {
                        message_ =
                                MessageDtoOuterClass.MessageDto.newBuilder(message_).mergeFrom(value).buildPartial();
                    } else {
                        message_ = value;
                    }
                    onChanged();
                } else {
                    messageBuilder_.mergeFrom(value);
                }

                return this;
            }

            /**
             * <code>.MessageDto message = 1;</code>
             */
            public Builder clearMessage() {
                if (messageBuilder_ == null) {
                    message_ = null;
                    onChanged();
                } else {
                    message_ = null;
                    messageBuilder_ = null;
                }

                return this;
            }

            /**
             * <code>.MessageDto message = 1;</code>
             */
            public MessageDtoOuterClass.MessageDto.Builder getMessageBuilder() {

                onChanged();
                return getMessageFieldBuilder().getBuilder();
            }

            /**
             * <code>.MessageDto message = 1;</code>
             */
            @Override
            public MessageDtoOuterClass.MessageDtoOrBuilder getMessageOrBuilder() {
                if (messageBuilder_ != null) {
                    return messageBuilder_.getMessageOrBuilder();
                } else {
                    return message_ == null ?
                            MessageDtoOuterClass.MessageDto.getDefaultInstance() : message_;
                }
            }

            /**
             * <code>.MessageDto message = 1;</code>
             */
            private com.google.protobuf.SingleFieldBuilderV3<
                    MessageDtoOuterClass.MessageDto, MessageDtoOuterClass.MessageDto.Builder, MessageDtoOuterClass.MessageDtoOrBuilder>
            getMessageFieldBuilder() {
                if (messageBuilder_ == null) {
                    messageBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
                            MessageDtoOuterClass.MessageDto, MessageDtoOuterClass.MessageDto.Builder, MessageDtoOuterClass.MessageDtoOrBuilder>(
                            getMessage(),
                            getParentForChildren(),
                            isClean());
                    message_ = null;
                }
                return messageBuilder_;
            }

            private Object randomKey_ = "";

            /**
             * <code>string randomKey = 2;</code>
             *
             * @return The randomKey.
             */
            @Override
            public String getRandomKey() {
                Object ref = randomKey_;
                if (!(ref instanceof String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    String s = bs.toStringUtf8();
                    randomKey_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>string randomKey = 2;</code>
             *
             * @return The bytes for randomKey.
             */
            @Override
            public com.google.protobuf.ByteString
            getRandomKeyBytes() {
                Object ref = randomKey_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (String) ref);
                    randomKey_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <code>string randomKey = 2;</code>
             *
             * @param value The randomKey to set.
             * @return This builder for chaining.
             */
            public Builder setRandomKey(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                randomKey_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>string randomKey = 2;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearRandomKey() {

                randomKey_ = getDefaultInstance().getRandomKey();
                onChanged();
                return this;
            }

            /**
             * <code>string randomKey = 2;</code>
             *
             * @param value The bytes for randomKey to set.
             * @return This builder for chaining.
             */
            public Builder setRandomKeyBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                checkByteStringIsUtf8(value);

                randomKey_ = value;
                onChanged();
                return this;
            }

            private Object remoteAddressKey_ = "";

            /**
             * <code>string remoteAddressKey = 3;</code>
             *
             * @return The remoteAddressKey.
             */
            @Override
            public String getRemoteAddressKey() {
                Object ref = remoteAddressKey_;
                if (!(ref instanceof String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    String s = bs.toStringUtf8();
                    remoteAddressKey_ = s;
                    return s;
                } else {
                    return (String) ref;
                }
            }

            /**
             * <code>string remoteAddressKey = 3;</code>
             *
             * @return The bytes for remoteAddressKey.
             */
            @Override
            public com.google.protobuf.ByteString
            getRemoteAddressKeyBytes() {
                Object ref = remoteAddressKey_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (String) ref);
                    remoteAddressKey_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            /**
             * <code>string remoteAddressKey = 3;</code>
             *
             * @param value The remoteAddressKey to set.
             * @return This builder for chaining.
             */
            public Builder setRemoteAddressKey(
                    String value) {
                if (value == null) {
                    throw new NullPointerException();
                }

                remoteAddressKey_ = value;
                onChanged();
                return this;
            }

            /**
             * <code>string remoteAddressKey = 3;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearRemoteAddressKey() {

                remoteAddressKey_ = getDefaultInstance().getRemoteAddressKey();
                onChanged();
                return this;
            }

            /**
             * <code>string remoteAddressKey = 3;</code>
             *
             * @param value The bytes for remoteAddressKey to set.
             * @return This builder for chaining.
             */
            public Builder setRemoteAddressKeyBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                checkByteStringIsUtf8(value);

                remoteAddressKey_ = value;
                onChanged();
                return this;
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


            // @@protoc_insertion_point(builder_scope:RpcCmd)
        }

        // @@protoc_insertion_point(class_scope:RpcCmd)
        private static final RpcCmd DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new RpcCmd();
        }

        public static RpcCmd getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        private static final com.google.protobuf.Parser<RpcCmd>
                PARSER = new com.google.protobuf.AbstractParser<RpcCmd>() {
            @Override
            public RpcCmd parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new RpcCmd(input, extensionRegistry);
            }
        };

        public static com.google.protobuf.Parser<RpcCmd> parser() {
            return PARSER;
        }

        @Override
        public com.google.protobuf.Parser<RpcCmd> getParserForType() {
            return PARSER;
        }

        @Override
        public RpcCmd getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_RpcCmd_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_RpcCmd_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        String[] descriptorData = {
                "\n\014RpcCmd.proto\032\020MessageDto.proto\"S\n\006RpcC" +
                        "md\022\034\n\007message\030\001 \001(\0132\013.MessageDto\022\021\n\trand" +
                        "omKey\030\002 \001(\t\022\030\n\020remoteAddressKey\030\003 \001(\tb\006p" +
                        "roto3"
        };
        descriptor = com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                                MessageDtoOuterClass.getDescriptor(),
                        });
        internal_static_RpcCmd_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_RpcCmd_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_RpcCmd_descriptor,
                new String[]{"Message", "RandomKey", "RemoteAddressKey",});
        MessageDtoOuterClass.getDescriptor();
    }

    // @@protoc_insertion_point(outer_class_scope)
}
