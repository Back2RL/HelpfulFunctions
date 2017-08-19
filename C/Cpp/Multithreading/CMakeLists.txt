cmake_minimum_required(VERSION 3.7)
project(Multithreading)

set(CMAKE_CXX_STANDARD 14)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pthread")

set(SOURCE_FILES main.cpp)
add_executable(Multithreading ${SOURCE_FILES})