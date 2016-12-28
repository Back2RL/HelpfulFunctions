#!/bin/bash
#
# Stefan Wohlfeil
#
# WS 2016/2017
#
# Erzeuge viele Testdateien in einem Verzeichnis, damit die Studis
# dann die Dateikommandos üben können.
#
[ ! -d testverz ] && mkdir testverz
cd testverz
for pre in bild foto xy abc video musik prog
do
   for mid in klaus gabi susi dirk olaf
   do
      for post in heute morgen gestern alt neu speziell
      do
         for suf in jpeg avi txt java bak py
         do
            file=$pre-$mid-$post.$suf
            touch $file
         done
      done
      [ ! -d $mid ] && mkdir $mid
   done
done
