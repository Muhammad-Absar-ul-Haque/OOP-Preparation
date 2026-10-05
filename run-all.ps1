# Compiles and runs the demo in every topic folder.
$ErrorActionPreference = 'Stop'

Get-ChildItem -Path $PSScriptRoot -Directory | Where-Object { $_.Name -match '^\d\d-' } | ForEach-Object {
    $demo = Get-ChildItem -Path $_.FullName -Filter '*Demo.java' | Select-Object -First 1
    if (-not $demo) { return }
    Write-Host "===== $($_.Name) ====="
    $out = Join-Path $_.FullName 'out'
    $sources = (Get-ChildItem -Path $_.FullName -Filter '*.java').FullName
    javac -d $out $sources
    if ($LASTEXITCODE -ne 0) { throw "Compilation failed in $($_.Name)" }
    java -cp $out $demo.BaseName
    Write-Host ''
}
