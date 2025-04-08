package com.sakurapuare.boatmanagement.utils;

import com.mybatisflex.core.constant.SqlConnector;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.SqlOperators;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class POJOUtils {
    public static <DTO, Entity> QueryWrapper getQueryWrapper(DTO dto, Class<Entity> entityClass) {
        try {
            Entity entity = entityClass.getDeclaredConstructor().newInstance();
            if (dto == null) {
                return QueryWrapper.create(entity);
            }
            BeanUtils.copyProperties(dto, entity);
            return QueryWrapper.create(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create query wrapper", e);
        }
    }

    public static <Other, Entity> Other asOther(Entity entity, Class<Other> otherClass) {
        try {
            if (entity == null) {
                return otherClass.getDeclaredConstructor().newInstance();
            }
            Other other = otherClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, other);
            return other;
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert entity to other", e);
        }
    }

    public static <Other, Entity> List<Other> asOtherList(List<Entity> entityList, Class<Other> otherClass) {
        if (entityList == null || entityList.isEmpty()) {
            return Collections.emptyList();
        }

        return entityList.stream()
                .filter(Objects::nonNull)
                .map(entity -> asOther(entity, otherClass))
                .collect(Collectors.toList());
    }

    public static <T> List<T> getListFromIds(List<Long> idList, Function<Long, T> getter) {
        if (idList == null || idList.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>();
        for (Long id : idList) {
            T item = getter.apply(id);
            if (item != null) {
                result.add(item);
            }
        }
        return result;
    }

    public static void sort(QueryWrapper queryWrapper, Map<String, String> sortMap) {
        sortMap.forEach((field, direction) -> queryWrapper.orderBy(field, direction.equals("asc")));
    }

    public static void search(QueryWrapper queryWrapper, QueryColumn[] searchFields, String search) {
        // 1. 快速返回检查 - 保留这个好习惯
        if (search == null || search.isEmpty()) {
            return;
        }

        // 2. 预先估计合适的初始容量可以减少哈希表扩容开销
        int initialCapacity = Math.min(16, searchFields.length);
        Map<String, Object> whereConditions = new HashMap<>(initialCapacity);
        SqlOperators operators = new SqlOperators();

        // 3. 提前准备搜索值，避免循环中重复处理
        String searchValue = search.trim();

        // 4. 只构建一次条件集
        for (QueryColumn field : searchFields) {
            whereConditions.put(field.getName(), searchValue);
            operators.set(field, SqlOperator.LIKE);
        }

        // 5. 一次性添加所有条件
        queryWrapper.and(whereConditions, operators, SqlConnector.OR);
    }

    public static void dateRange(QueryWrapper queryWrapper, String startDateTime, String endDateTime,
            QueryColumn... columns) {
        QueryColumn createdAt = new QueryColumn("created_at");
        QueryColumn updatedAt = new QueryColumn("updated_at");

        boolean hasStartDateTime = startDateTime != null && !startDateTime.isEmpty();
        boolean hasEndDateTime = endDateTime != null && !endDateTime.isEmpty();

        if (hasStartDateTime) {
            queryWrapper.where(createdAt.ge(startDateTime).or(updatedAt.ge(startDateTime)));
        }

        if (hasEndDateTime) {
            queryWrapper.where(createdAt.le(endDateTime).or(updatedAt.le(endDateTime)));
        }

        for (QueryColumn column : columns) {
            if (hasStartDateTime) {
                queryWrapper.where(column.ge(startDateTime));
            }
            if (hasEndDateTime) {
                queryWrapper.where(column.le(endDateTime));
            }
        }
    }
}
