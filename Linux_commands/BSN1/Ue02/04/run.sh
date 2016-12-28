#!/bin/bash
echo "lösche testverz:"
rm -R testverz
echo "erzeuge Testdateien:"
./erzeugeTestdateien.sh

echo "sortiere:"
cd testverz
for name in dirk gabi klaus olaf susi; do mkdir "$name"; mv *"$name"* "$name"; done
cd ..

