CONFIG = release
CONFIG += warn_on
QMAKE_CXXFLAGS += -std=c++1y
QMAKE_LIBDIR +=  -Lusr/local/lib  

HEADERS += CPUtime.h
SOURCES += CPUtime.cpp
SOURCES += main.cpp
