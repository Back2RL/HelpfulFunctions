cmake_minimum_required(VERSION 3.7)
project(ScanFunctions)

set(CMAKE_C_STANDARD 90)

set(SOURCE_FILES readline.c readline.h)
add_library(ScanFunctions ${SOURCE_FILES})