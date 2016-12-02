cmake_minimum_required(VERSION 3.6)
project(05)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        #DVektor_test.c
        Vektor_test.c
        #DVektor.c
        #DVektor.h
        Vektor.c Vektor.h)

add_executable(05 ${SOURCE_FILES})