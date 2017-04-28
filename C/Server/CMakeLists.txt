cmake_minimum_required(VERSION 3.7)
project(Server)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        Xserver.c)

add_executable(Server ${SOURCE_FILES})

target_link_libraries(Server libScanFunctions.a)