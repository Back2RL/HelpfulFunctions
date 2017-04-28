cmake_minimum_required(VERSION 3.7)
project(sockets)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        SocketExample.c)

add_executable(sockets ${SOURCE_FILES})