cmake_minimum_required(VERSION 3.7)
project(Client)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        Xclient.c
        #Xclient_HA.c
        )

add_executable(Client ${SOURCE_FILES})

target_link_libraries(Client libScanFunctions.a)