. ./scripts/init.ps1

Get-ChildItem -Path "$spring_path/service/base/" -File | Remove-Item
Get-ChildItem -Path "$spring_path/pojo/vo/base" -File | Remove-Item
Get-ChildItem -Path "$spring_path/pojo/dto/base" -File | Remove-Item
Get-ChildItem -Path "$spring_path/pojo/entity" -Filter "Base*.java" | Remove-Item 