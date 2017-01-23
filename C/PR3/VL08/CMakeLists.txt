cmake_minimum_required(VERSION 3.6)
project(VL08)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.cpp Person.cpp Person.h)
add_executable(VL08 ${SOURCE_FILES})