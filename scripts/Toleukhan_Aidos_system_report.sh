#!/bin/bash
# System monitoring script by Toleukhan Aidos

STUDENT="Toleukhan Aidos"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
LOG_FILE="$SCRIPT_DIR/../logs/Toleukhan_Aidos_system_report.log"

{
echo "========================================"
echo "Student: $STUDENT"
echo "Date and time: $(date '+%Y-%m-%d %H:%M:%S')"
echo "Username: $(whoami)"
echo "OS: $(grep PRETTY_NAME /etc/os-release | cut -d= -f2 | tr -d '"')"
echo "Kernel: $(uname -r)"
echo "--- CPU ---"
lscpu | grep -E 'Model name|^CPU\(s\)'
echo "--- Memory ---"
free -h
echo "--- Disk usage ---"
df -h -x tmpfs -x squashfs
echo "--- Available disk space on / ---"
df -h --output=avail / | tail -1
echo "========================================"
} | tee -a "$LOG_FILE"
