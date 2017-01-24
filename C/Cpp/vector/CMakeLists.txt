cmake_minimum_required(VERSION 3.5)
project(vector)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11  -Ddebug")

set(SOURCE_FILES vektoren.cpp Vektor.cpp Headers/Vektor.h Headers/CPUtime.h CPUtime.cpp Headers/TestInterface.h Headers/Macros.h)
add_executable(vektoren ${SOURCE_FILES})