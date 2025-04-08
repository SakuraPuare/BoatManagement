package com.sakurapuare.boatmanagement.utils;

import java.util.*;

public class ParamsUtils {

    public static List<Long> getListFromIds(String ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        String[] arr = ids.split(",");
        List<Long> result = new ArrayList<>(arr.length);

        for (String s : arr) {
            if (s.matches("\\d+")) {
                result.add(Long.parseLong(s));
            }
        }

        return result;
    }

    public static Map<String, String> getSortFromParams(String sort) {
        // 如果输入为null，直接返回空Map
        if (sort == null || sort.isEmpty()) {
            return Collections.emptyMap();
        }
        // id.asc,name.desc
        // 预估Map大小可以避免扩容
        String[] sortArray = sort.split(",");
        Map<String, String> sortMap = new HashMap<>(sortArray.length);

        for (String sortItem : sortArray) {
            int dotIndex = sortItem.indexOf('.');
            if (dotIndex > 0 && dotIndex < sortItem.length() - 1) {
                String field = sortItem.substring(0, dotIndex);
                String direction = sortItem.substring(dotIndex + 1);

                if ("asc".equals(direction) || "desc".equals(direction)) {
                    sortMap.put(field, direction);
                }
            }
        }

        return sortMap;
    }
}
