cmake_minimum_required(VERSION 3.7)
project(VectorIteratingSpeedTest)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        CPUtime.cpp
        CPUtime.h
        main.cpp
        Loops/ForEach.h
        Loops/ForEach.cpp
        Loops/Hand.h
        Loops/Hand.cpp
        Loops/HandOpt.h
        Loops/HandOpt.cpp
        Loops/HandOptRef.h
        Loops/HandOptRef.cpp
        Loops/Iterator.h
        Loops/Iterator.cpp
        Loops/IteratorOpt.h
        Loops/IteratorOpt.cpp)

add_executable(VectorIteratingSpeedTest ${SOURCE_FILES})