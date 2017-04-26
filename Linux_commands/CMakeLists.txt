cmake_minimum_required(VERSION 3.7)
project(Linux_commands)

set(CMAKE_CXX_STANDARD 11)

set(SOURCE_FILES
        AutoCPUFreq.sh
        Bootrepair.sh
        change_swappiness_temp.sh
        extract_zipfiles.sh
        install_programs.sh
        installGoogleDriveFuse.sh
        mount_GoogleDrive.sh
        mouse_fix_Ubuntu_1204.sh
        scale_Display.sh
        scale_reset.sh
        setCPUFreqs.sh
        show_wlans.sh)

add_executable(Linux_commands ${SOURCE_FILES})