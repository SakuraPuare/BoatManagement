. ./scripts/init.ps1

function Get-ApiModelPropertyName {
    param (
        [string]$entity_name
    )
    
    $input_file = "$entity_path/$entity_name.java"
    $content = Get-Content $input_file -Raw
    $matches = [regex]::Matches($content, '(?ms)@ApiModelProperty.*?\r?\n.*?\r?\n')
    
    $result = $matches | ForEach-Object { $_.Value }
    return $result -join ""
}

function Generate-File {
    param (
        [string]$entity_name,
        [string]$api_model_property_name,
        [string]$api_model_name,
        [string]$java_import_list,
        [string]$extends_class
    )

    foreach ($type in @("dto", "vo")) {
        # Check if _n$type exists in entity file
        $entity_content = Get-Content "$entity_path/$entity_name.java" -Raw
        if ($entity_content -match "_n$type") {
            continue
        }

        $file_path = if ($type -eq "dto") { $dto_path } else { $vo_path }
        $file_name = "Base$entity_name$($type.ToUpper())"
        $file_real_name = "$file_name.java"
        $full_path = "$file_path/base/$file_real_name"

        if (Test-Path $full_path) {
            Remove-Item $full_path
        }

        $template = if ($type -eq "dto") {
@"
package com.sakurapuare.$project_name.pojo.$type.base;

import com.sakurapuare.$project_name.pojo.dto.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
$java_import_list

@Data
@ApiModel("$api_model_name")
@EqualsAndHashCode(callSuper = true)
public class $file_name extends $($extends_class)DTO {
$api_model_property_name
}
"@
        } else {
@"
package com.sakurapuare.$project_name.pojo.$type.base;

import com.sakurapuare.$project_name.pojo.vo.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
$java_import_list

@Data
@ApiModel("$api_model_name")
@EqualsAndHashCode(callSuper = true)
public class $file_name extends $($extends_class)VO {
$api_model_property_name
}
"@
        }

        $template | Out-File -FilePath $full_path -Encoding utf8
        Write-Host "Generated file: $full_path"
    }
}

function Post-Process {
    param (
        [string]$dto_path,
        [string]$vo_path
    )

    Get-ChildItem "$dto_path/base" -Filter "*.java" | ForEach-Object {
        Write-Host "Processing file: $_"
        $content = Get-Content $_.FullName -Raw
        $content = $content -replace '@ApiModelProperty\(""\)\r?\n[^\r\n]*\r?\n', ''
        $content = $content -replace 'private Long id;\r?\n', ''
        $content = $content -replace 'import java.io.Serial;\r?\n', ''
        $content = $content -replace 'import java.io.Serializable;\r?\n', ''
        $content = $content -replace '@Column\([^)]+\)\r?\n', ''
        $content = $content -replace '@ApiModelProperty\("[^"]*_serverside"\)[^\r\n]*\r?\n[^\r\n]*\r?\n', ''
        $content = $content -replace '@ApiModelProperty\("[^"]*_bothside"\)[^\r\n]*\r?\n[^\r\n]*\r?\n', ''
        $content | Out-File $_.FullName -Encoding utf8
    }

    Get-ChildItem "$vo_path/base" -Filter "*.java" | ForEach-Object {
        Write-Host "Processing file: $_"
        $content = Get-Content $_.FullName -Raw
        $content = $content -replace '@ApiModelProperty\(""\)\r?\n[^\r\n]*\r?\n', ''
        $content = $content -replace 'private Boolean isDeleted;\r?\n', ''
        $content = $content -replace 'import java.io.Serial;\r?\n', ''
        $content = $content -replace 'import java.io.Serializable;\r?\n', ''
        $content = $content -replace '@Column\([^)]+\)\r?\n', ''
        $content = $content -replace '@ApiModelProperty\("[^"]*_clientside"\)[^\r\n]*\r?\n[^\r\n]*\r?\n', ''
        $content = $content -replace '@ApiModelProperty\("[^"]*_bothside"\)[^\r\n]*\r?\n[^\r\n]*\r?\n', ''
        $content | Out-File $_.FullName -Encoding utf8
    }
}

$entity_path = "$spring_path/pojo/entity"
$dto_path = "$spring_path/pojo/dto"
$vo_path = "$spring_path/pojo/vo"

# Clean directories
if (Test-Path "$dto_path/base") { Remove-Item "$dto_path/base" -Recurse }
if (Test-Path "$vo_path/base") { Remove-Item "$vo_path/base" -Recurse }

# Create directories
New-Item -ItemType Directory -Path "$dto_path/base" -Force
New-Item -ItemType Directory -Path "$vo_path/base" -Force

# Process each entity file
Get-ChildItem $entity_path -Filter "*.java" | Where-Object { $_.Name -notmatch "^Base" } | ForEach-Object {
    $entity_name = $_.BaseName
    $content = Get-Content $_.FullName -Raw
    
    $api_model_name = if ($content -match '@ApiModel\("([^"]+)"\)') { $matches[1] } else { "" }
    $api_model_property_name = Get-ApiModelPropertyName $entity_name
    $java_import_list = ($content | Select-String -Pattern '^import java.*?;' -AllMatches).Matches.Value -join "`n"
    $extends_class = if ($content -match 'class\s+\w+\s+extends\s+(\w+)') { $matches[1] } else { "BaseEntity" }

    Generate-File $entity_name $api_model_property_name $api_model_name $java_import_list $extends_class
}

Post-Process $dto_path $vo_path 