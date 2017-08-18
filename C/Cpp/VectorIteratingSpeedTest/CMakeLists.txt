cmake_minimum_required(VERSION 3.7)
project(VectorIteratingSpeedTest)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        CPUtime.cpp
        CPUtime.h
        main.cpp
        Loops/ForEach.h
        Loops/Hand.h
        Loops/HandOpt.h
        Loops/HandOptRef.h
        Loops/Iterator.h
        Loops/IteratorOpt.h)

add_executable(VectorIteratingSpeedTest ${SOURCE_FILES})