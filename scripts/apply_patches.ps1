Get-ChildItem -Path "./scripts/patches" -Filter *.patch | ForEach-Object {
    git apply $_.FullName
} 