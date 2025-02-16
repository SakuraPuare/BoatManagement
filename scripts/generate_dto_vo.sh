#!/bin/bash
source ./scripts/init.sh

#  获取@ApiModelProperty 和 他的下一行
function get_api_model_property_name() {
    local entity_name="$1"
    local input_file="$entity_path/$entity_name.java"

    # 使用awk处理文件
    awk '
        # 当找到@ApiModelProperty行时
        /@ApiModelProperty/ {
            # 保存当前行
            property_line=$0
            # 读取下一行
            getline next_line
            # 打印两行,用特殊分隔符分开
            print property_line "\n"  next_line "\n"
        }
    ' "$input_file"
    
    # return 
    echo "$property_line"
    echo "$next_line"
}

function generate_file() {
    local entity_name="$1"
    local api_model_property_name="$2"
    local api_model_name="$3"
    local sql_import_list="$4"
    local math_import_list="$5"
    local time_import_list="$6"

    for type in "dto" "vo"; do
        # 如果存在_n$type，则跳过生成
        if grep -q "_n$type" "$entity_path/$entity_name.java"; then
            # 跳过
            continue
        fi
        
        if [ "$type" == "dto" ]; then
            file_path="$dto_path"
        else
            file_path="$vo_path"
        fi
        file_name="Base$entity_name$(echo $type | tr '[:lower:]' '[:upper:]')"
        file_real_name="$file_name.java"

        if [ -f "$file_path/$file_real_name" ]; then
            rm "$file_path/$file_real_name"
        fi

        template_before="package com.sakurapuare.$project_name.pojo.$type.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;"

        if [ "$type" == "dto" ]; then
            template_before="package com.sakurapuare.$project_name.pojo.$type.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;"
            template_after="@Data
@ApiModel(\"${api_model_name}\")
public class ${file_name} {
${api_model_property_name}
}
"
        else
            template_before="package com.sakurapuare.$project_name.pojo.$type.base;

import com.sakurapuare.$project_name.pojo.vo.BaseEntityVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;"
            template_after="@Data
@ApiModel(\"${api_model_name}\")
@EqualsAndHashCode(callSuper = true)
public class ${file_name} extends BaseEntityVO {
${api_model_property_name}
}
"
        fi
        echo "$template_before" > "$file_path/base/$file_real_name"
        sql_import_list=$(echo "$sql_import_list" | sed '/^[[:space:]]*$/d')
        math_import_list=$(echo "$math_import_list" | sed '/^[[:space:]]*$/d')

        echo -e "$sql_import_list" >> "$file_path/base/$file_real_name"
        echo -e "$math_import_list" >> "$file_path/base/$file_real_name"
        echo -e "$time_import_list" >> "$file_path/base/$file_real_name"

        echo "$template_after" >> "$file_path/base/$file_real_name"
        echo "生成文件: $file_path/base/$file_real_name"
    done
}

function post_process() {
    local dto_path="$1"
    local vo_path="$2"

    for file in "$dto_path/base"/*.java; do
        echo "处理文件: $file"
        # 过滤一些字段
        sed -i '/@ApiModelProperty("")/d' "$file"
        sed -i '/private Long id;/d' "$file"
        # delete serverside
        sed -i '/@ApiModelProperty(".*_serverside")/,+1d' "$file"
        sed -i '/@ApiModelProperty(".*_bothside")/,+1d' "$file"
        echo "处理文件: $file 完成"
    done

    for file in "$vo_path/base"/*.java; do
        echo "处理文件: $file"
        # 过滤一些字段
        sed -i '/@ApiModelProperty("")/d' "$file"
        sed -i '/private Boolean isDeleted;/d' "$file"
        # delete client side
        sed -i '/@ApiModelProperty(".*_clientside")/,+1d' "$file"
        sed -i '/@ApiModelProperty(".*_bothside")/,+1d' "$file"
        echo "处理文件: $file 完成"
    done
}


# 函数 根据entity文件生成dto文件
function generate() {
    local entity_name="$1"

    # 获取@ApiModel
    api_model_name=$(sed -n 's/.*@ApiModel("\(.*\)").*/\1/p' "$entity_path/$entity_name.java")
    # 获取@ApiModelProperty 和 他的下一行
    api_model_property_name=$(get_api_model_property_name "$entity_name")
    # 获取所有import java*
    sql_import_list=$(sed -n 's/^import java\.sql\(.*\);/\1;/p' "$entity_path/$entity_name.java" | sed 's/^/import java.sql/')
    math_import_list=$(sed -n 's/^import java\.math\(.*\);/\1;/p' "$entity_path/$entity_name.java" | sed 's/^/import java.math/')
    time_import_list=$(sed -n 's/^import java\.time\(.*\);/\1;/p' "$entity_path/$entity_name.java" | sed 's/^/import java.time/')

    # 生成dto文件
    generate_file "$entity_name" "$api_model_property_name" "$api_model_name" "$sql_import_list" "$math_import_list" "$time_import_list"
}

entity_path="$spring_path/pojo/entity"
dto_path="$spring_path/pojo/dto"
vo_path="$spring_path/pojo/vo"


rm -rf "$dto_path/base"
rm -rf "$vo_path/base"

mkdir -p "$dto_path/base"
mkdir -p "$vo_path/base"

# 过滤BaseEntity
fd -t f . $entity_path -d 1 -E "Base*.java" | while read -r file; do 
    entity_name=$(basename "$file" | sed -n 's/\(.*\)\.java/\1/p')
    generate "$entity_name"
    # echo "$entity_name"
    post_process "$dto_path" "$vo_path"
done
