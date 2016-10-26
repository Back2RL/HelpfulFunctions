#!/bin/bash
sudo apt-get update
sudo apt-get install g++
sudo apt-get install vim
sudo apt-get install preload
sudo apt-get install gthumb
#/etc/preload.conf (settings)
sudo apt-get install htop
sudo apt-get install cpufrequtils
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

sudo add-apt-repository ppa:jfi/ppa
sudo apt-get update
sudo apt-get install psensor
sudo sensors-detect
