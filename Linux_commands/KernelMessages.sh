#!/bin/bash
#vi /var/log/kern.log
dmesg --level err
echo press ENTER to show live updates:
read A
dmesg -w # with live update
