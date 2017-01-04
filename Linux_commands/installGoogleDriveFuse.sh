#!/bin/bash
#http://xmodulo.com/mount-google-drive-linux.html
#install google drive software to mount gdrive like a hdd or usb stick
#mint or ubuntu:
sudo add-apt-repository ppa:alessandro-strada/ppa
sudo apt-get update
sudo apt-get install google-drive-ocamlfuse

curruser=$USER
echo "User is \"$curruser\""
sudo usermod -a -G fuse "$curruser"
exec su -l "$curruser"

google-drive-ocamlfuse
