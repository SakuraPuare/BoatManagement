. ./scripts/init.ps1

Get-ChildItem -Path "$spring_path/service/base/impl" -Filter "*.java" | ForEach-Object {
    # Extract service name from Base<Service>Impl
    $service_name = $_.BaseName -replace "Base(.*)ServiceImpl", '$1'
    
    if ($service_name) {
        $template = @"
package com.sakurapuare.$project_name.service;

import com.sakurapuare.$project_name.service.base.impl.Base${service_name}ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ${service_name}Service extends Base${service_name}ServiceImpl {
}
"@
        $output_file = "$spring_path/service/${service_name}Service.java"
        
        if (-not (Test-Path $output_file)) {
            $template | Out-File -FilePath $output_file -Encoding utf8
            Write-Host "Created: ${service_name}Service.java"
        } else {
            Write-Host "Skipping: ${service_name}Service.java"
        }
    }
} 