#!/bin/bash
du -xh / |grep '^\S*[0-9\.]\+G'|sort -rn
