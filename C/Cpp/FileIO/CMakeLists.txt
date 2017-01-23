cmake_minimum_required(VERSION 3.6)
project(FileIO)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.cpp src/FileManager.cpp src/FileManager.h)
add_executable(FileIO ${SOURCE_FILES})