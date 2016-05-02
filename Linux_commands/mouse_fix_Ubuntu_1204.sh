#!/bin/bash
sudo modprobe -r psmouse
sudo modprobe psmouse proto=imps

echo "options psmouse proto=imps" > /etc/modprobe.d/ubuntu_touchpad_fix.conf
