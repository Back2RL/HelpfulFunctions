cmake_minimum_required(VERSION 3.6)
project(VL09)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES main.cpp Pos.cpp Pos.h CPos.cpp CPos.h)
add_executable(VL09 ${SOURCE_FILES})