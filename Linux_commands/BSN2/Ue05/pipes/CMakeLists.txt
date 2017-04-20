cmake_minimum_required(VERSION 3.7)
project(pipes)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        main.c)

add_executable(pipes ${SOURCE_FILES})