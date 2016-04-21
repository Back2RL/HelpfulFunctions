#!/bin/bash
echo "Scaling Display 1.5"
xrandr --current
xrandr --output DP-1 --scale 1.5x1.5
