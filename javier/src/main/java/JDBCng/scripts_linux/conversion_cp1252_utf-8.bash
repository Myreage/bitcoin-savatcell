#!/bin/bash
for i in $@
do
	iconv -f CP1252 -t UTF-8 "$i" > "$i.new"
done
