package com.sakurapuare.boatmanagement.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GoodsInfoTypeHandler extends BaseTypeHandler<Map<Integer, Double>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<Integer, Double> parameter, JdbcType jdbcType) 
            throws SQLException {
        // 将Map转换为字符串格式 "id:num,id:num"
        StringBuilder sb = new StringBuilder();
        parameter.forEach((id, num) -> {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(id).append(":").append(num);
        });
        ps.setString(i, sb.toString());
    }

    @Override
    public Map<Integer, Double> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseGoodsInfo(rs.getString(columnName));
    }

    @Override
    public Map<Integer, Double> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseGoodsInfo(rs.getString(columnIndex));
    }

    @Override
    public Map<Integer, Double> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseGoodsInfo(cs.getString(columnIndex));
    }

    private Map<Integer, Double> parseGoodsInfo(String value) {
        Map<Integer, Double> result = new HashMap<>();
        if (value != null && !value.isEmpty()) {
            String[] items = value.split(",");
            for (String item : items) {
                String[] parts = item.split(":");
                if (parts.length == 2) {
                    try {
                        Integer id = Integer.parseInt(parts[0].trim());
                        Double num = Double.parseDouble(parts[1].trim());
                        result.put(id, num);
                    } catch (NumberFormatException e) {
                        // 处理解析异常，可以选择记录日志或者抛出自定义异常
                        throw new RuntimeException("Invalid goods info format: " + value, e);
                    }
                }
            }
        }
        return result;
    }
}