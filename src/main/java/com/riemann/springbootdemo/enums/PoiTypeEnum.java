package com.riemann.springbootdemo.enums;

/**
 * 定位商类型枚举类
 */
public enum PoiTypeEnum {

    LOCATION_MALL("商圈", 1),
    LOCATION_SHOP("店铺", 2);

    private final String name;
    private final int value;

    PoiTypeEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public static boolean valid(PoiTypeEnum type) {
        for (PoiTypeEnum typeEnum : PoiTypeEnum.values()) {
            if (typeEnum.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static int getValueByType(PoiTypeEnum type) {
        for (PoiTypeEnum typeEnum : PoiTypeEnum.values()) {
            if (typeEnum.equals(type)) {
                return typeEnum.value;
            }
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
