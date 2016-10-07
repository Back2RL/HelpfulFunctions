#!/bin/bash
sudo cpufreq-set -g userspace
sudo cpufreq-set -f 800Mhz
sudo cpufreq-set -u 800Mhz
sudo cpufreq-info 
sudo cpufreq-info -l 
sudo cpufreq-info -p
