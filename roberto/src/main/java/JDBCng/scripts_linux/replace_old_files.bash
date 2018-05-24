#!/bin/bash
for i in $@
do
	mv "$i" "$(echo "$i" | sed 's/\.new$//')"
done
