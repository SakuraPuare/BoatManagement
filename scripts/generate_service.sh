#!/bin/bash
source ./scripts/init.sh

fd -t f . $spring_path/service/base/impl | while read -r file; do
    # 提取Base<Service>Impl中的Service名称
    service_name=$(basename "$file" | sed -n 's/Base\(.*\)ServiceImpl\.java/\1/p')
    # 加上Service后缀
    template="package com.sakurapuare.$project_name.service;

import com.sakurapuare.$project_name.service.base.impl.Base${service_name}ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ${service_name}Service extends Base${service_name}ServiceImpl {
}"
    service_name=${service_name}Service.java

    # 如果提取成功且目标文件不存在，则创建文件
    if [ ! -z "$service_name" ] && [ ! -f "$spring_path/service/$service_name" ]; then
        touch "$spring_path/service/$service_name"
        echo "$template" > "$spring_path/service/$service_name"
        echo "Created: $service_name"
    else
        echo "Skipping: $service_name"
    fi
done

