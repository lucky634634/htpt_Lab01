#!/bin/bash

# Change directory to ./cwd
cd ./cwd || { echo "Directory ./cwd not found"; exit 1; }

# Loop from 0 to 11 (inclusive)
for i in {0..11}; do
    # Run Java with preview features and pass config file + iteration index
    java --enable-preview ../src/App.java config.cfg "$i" &
done

wait