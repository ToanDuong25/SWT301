# PowerShell Script để tải ChromeDriver trên Windows
# Sử dụng: .\install-chromedriver.ps1

Write-Host "=== ChromeDriver Installation Script ===" -ForegroundColor Green

# 1. Kiểm tra Chrome version hiện tại
Write-Host "`n1. Kiểm tra Chrome version..." -ForegroundColor Yellow
try {
    $ChromePath = "C:\Program Files\Google\Chrome\Application\chrome.exe"
    if (-not (Test-Path $ChromePath)) {
        $ChromePath = "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe"
    }

    if (Test-Path $ChromePath) {
        $ChromeVersion = [System.Diagnostics.FileVersionInfo]::GetVersionInfo($ChromePath).FileVersion
        Write-Host "OK Chrome version: $ChromeVersion" -ForegroundColor Green
    } else {
        Write-Host "Chrome khong tim thay. Cai dat Chrome truoc." -ForegroundColor Red
        exit 1
    }
}
catch {
    Write-Host "Error: $_" -ForegroundColor Red
    exit 1
}

# 2. Hướng dẫn download
Write-Host "`n2. Huong dan tai ChromeDriver:" -ForegroundColor Yellow
Write-Host "   URL: https://googlechromelabs.github.io/chrome-for-testing/" -ForegroundColor Cyan
Write-Host "   1. Vao URL tren"
Write-Host "   2. Chon version (stable)"
Write-Host "   3. Download 'chromedriver' cho Windows"
Write-Host "   4. Giai nen file"
Write-Host "   5. Copy chromedriver.exe vao C:\Windows\System32\"

Write-Host "`n3. Sau khi tai xong, chay:" -ForegroundColor Yellow
Write-Host "   chromedriver --version" -ForegroundColor Cyan
Write-Host "   mvn clean test -Dbrowser=chrome" -ForegroundColor Cyan

Write-Host "`nOK Script hoan thanh!" -ForegroundColor Green

