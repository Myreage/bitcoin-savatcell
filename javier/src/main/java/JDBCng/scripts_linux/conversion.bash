#!/bin/bash
find . -type f -name "*.java" | xargs ./scripts_linux/conversion_cp1252_utf-8.bash
find . -type f -name "*.txt" | grep -v README_Linux.txt | xargs ./scripts_linux/conversion_cp1252_utf-8.bash
find . -type f -name "*.csv" | xargs ./scripts_linux/conversion_cp1252_utf-8.bash
find . -type f -name "*.xml" | xargs ./scripts_linux/conversion_cp1252_utf-8.bash
find . -type f -name "*.rsc" | xargs ./scripts_linux/conversion_cp1252_utf-8.bash
find . -type f -name "*.html" | xargs ./scripts_linux/conversion_cp1252_utf-8.bash

find . -type f -name "*.new" | xargs ./scripts_linux/replace_old_files.bash
