package com.frameworktool.ibatis;

import com.frameworktool.basetype.IEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumEnumTypeHandler<E extends IEnum<E>> extends BaseTypeHandler<E> {
    private final Class<E> type;
    private E[] enums;

    public NumEnumTypeHandler(Class<E> type) {
        this.type = type;
        if (type != null) {
            enums = (E[]) type.getEnumConstants();
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, ((IEnum) e).getIndex());
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int value = Integer.valueOf(resultSet.getString(s));
        for (E e : enums) if (Integer.valueOf(value).equals(e.getIndex())) return e;
        return null;
    }

    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int value = Integer.valueOf(resultSet.getString(i));
        for (E e : enums) if (Integer.valueOf(value).equals(e.getIndex())) return e;
        return null;
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        for (E e : enums) {
            if (Integer.valueOf(i).equals(e.getIndex())) return e;
        }
        return null;
    }
}
