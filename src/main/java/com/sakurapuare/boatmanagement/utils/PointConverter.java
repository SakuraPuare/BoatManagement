package com.sakurapuare.boatmanagement.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class PointConverter {
    // WKB 转 List<Double>
    public static List<Double> wkbToPoint(String base64Wkb) {
        byte[] wkbBytes = Base64.getDecoder().decode(base64Wkb);
        ByteBuffer buffer = ByteBuffer.wrap(wkbBytes);

        // 读取字节序
        byte byteOrder = buffer.get();
        buffer.order(byteOrder == 0 ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);

        // 跳过几何类型(4字节)
        buffer.getInt();

        // 读取坐标
        double x = buffer.getDouble();
        double y = buffer.getDouble();

        return Arrays.asList(x, y);
    }

    // List<Double> 转 WKB
    public static byte[] pointToWkb(List<Double> point) {
        ByteBuffer buffer = ByteBuffer.allocate(21); // WKB POINT 固定21字节
        buffer.order(ByteOrder.BIG_ENDIAN);

        // 写入字节序(大端序 = 0)
        buffer.put((byte) 0);

        // 写入几何类型(POINT = 1)
        buffer.putInt(1);

        // 写入坐标
        buffer.putDouble(point.get(0)); // x
        buffer.putDouble(point.get(1)); // y

        return buffer.array();
    }
}