package com.tony.constants;

/**
 * @author jiangwenjie 2019/10/30
 */
public enum EnumNettyProtoType {
    /**
     *
     */
    PROTOSTUFF("protostuff", "protostuff"),
    STUFF_PROTOBUF("stuff-protobuf", "stuff内置protobuf"),
    PURE_PROTOBUF("pure-protobuf", "纯protobuf");
    private String key;
    private String desc;

    EnumNettyProtoType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean contains(String key) {
        for (EnumNettyProtoType protoType : values()) {
            if (protoType.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }
}
