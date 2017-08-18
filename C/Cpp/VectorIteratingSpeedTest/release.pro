CONFIG = release
CONFIG += warn_on
QMAKE_CXXFLAGS += -std=c++1y
QMAKE_LIBDIR +=  -Lusr/local/lib  

HEADERS += CPUtime.h
HEADERS += Loops/ForEach.h
HEADERS += Loops/Hand.h
HEADERS += Loops/HandOpt.h
HEADERS += Loops/HandOptRef.h
HEADERS += Loops/Iterator.h
HEADERS += Loops/IteratorOpt.h
SOURCES += CPUtime.cpp
SOURCES += main.cpp
