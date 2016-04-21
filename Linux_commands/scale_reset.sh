#!/bin/bash
echo "Scaling Display 1.0x1.0"
xrandr --current
xrandr --output DP-1 --scale 1.0x1.0
