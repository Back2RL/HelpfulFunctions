#!/bin/bash
#changes until next reboot how aggressive the system tries to write data from RAM to disk
#default 60 (0-100)
sudo sysctl vm.swappiness=10
#persistant change:
#open /etc/sysctl.conf
#edit line vm.swappiness=10
