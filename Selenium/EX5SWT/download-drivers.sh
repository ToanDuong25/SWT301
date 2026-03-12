#!/bin/bash
# Script để tải ChromeDriver tự động (dành cho Linux/Mac)

DRIVER_DIR="src/test/resources/drivers"
mkdir -p "$DRIVER_DIR"

# Detect OS
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" || "$OSTYPE" == "win32" ]]; then
    # Windows
    DRIVER_FILE="$DRIVER_DIR/chromedriver.exe"
    OS_NAME="win32"
else
    # Linux/Mac
    DRIVER_FILE="$DRIVER_DIR/chromedriver"
    OS_NAME="linux64"
fi

echo "Tải ChromeDriver..."
# Đây chỉ là gợi ý - cần tải version phù hợp với Chrome của bạn
# Vào https://googlechromelabs.github.io/chrome-for-testing/ để tìm version phù hợp

echo "✓ ChromeDriver sẽ được đặt tại: $DRIVER_FILE"
