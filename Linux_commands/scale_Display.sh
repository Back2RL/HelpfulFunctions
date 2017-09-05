#!/bin/bash
xrandr --current

echo 10 seconds until changes will be applied

ADAPTER=eDP1
MODE=1366x768
PANNING=1920x1080
SCALE=1.0

sleep 10
xrandr --output "$ADAPTER" --mode "$MODE" --panning "$PANNING" --scale "$SCALE"x"$SCALE"
