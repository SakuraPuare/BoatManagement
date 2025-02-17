package com.sakurapuare.boatmanagement.handler;

import com.sakurapuare.boatmanagement.utils.PointConverter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

public class PointTypeHandler extends BaseTypeHandler<List<Double>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Double> parameter, JdbcType jdbcType)
            throws SQLException {
        byte[] wkb = PointConverter.pointToWkb(parameter);
        ps.setBytes(i, wkb);
    }

    @Override
    public List<Double> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        byte[] bytes = rs.getBytes(columnName);
        if (bytes == null) return null;
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return PointConverter.wkbToPoint(base64);
    }

    @Override
    public List<Double> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        byte[] bytes = rs.getBytes(columnIndex);
        if (bytes == null) return null;
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return PointConverter.wkbToPoint(base64);
    }

    @Override
    public List<Double> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        byte[] bytes = cs.getBytes(columnIndex);
        if (bytes == null) return null;
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return PointConverter.wkbToPoint(base64);
    }
}
