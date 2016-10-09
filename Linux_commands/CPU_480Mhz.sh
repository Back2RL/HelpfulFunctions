#!/bin/bash
sudo cpufreq-set -g userspace
sudo cpufreq-set -f 480Mhz
sudo cpufreq-set -u 480Mhz
sudo cpufreq-info 
sudo cpufreq-info -l 
sudo cpufreq-info -p
