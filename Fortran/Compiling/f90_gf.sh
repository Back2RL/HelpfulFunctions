#!/bin/bash
#
# Mi 11. Nov 14:56:50 CET 2015
# for use in combination with vim
# place in your home directory ~/
# ----------------------------------------
 set -x
#
 fn=$1
#
#xlf -C -u -qflttrap -qinitauto=ff -qsigtrap -g -o ${fn}.exe ${fn}.f
#VASTPATH=/home/wolf/fortran.d/VASTf90.d/
# IBM FORTRAN77 Compiler :
#CF=xlf
# GNU FORTRAN77 Compiler :
#CF=g77
# GNU FORTRAN90 Compiler :
CF=gfortran
# VAST FORTRAN90 Compiler :
#CF=f90                                                                     
# LINUX : g77 flags :
#FFLAGS="-keep -g -Wall"
# LINUX : gfortran flags :
# FFLAGS="-g -ffree-form -fbounds-check -fimplicit-none"
# FFLAGS="-g -DERF -ffree-form -fbounds-check -fimplicit-none"
# FFLAGS="-g -DTEST1 -ffree-form -fbounds-check -fimplicit-none"
# FFLAGS="-g -DTEST1 -DOpenMP -ffree-form -fbounds-check -fimplicit-none"
# FFLAGS="-g -DTEST1 -Dprint2 -DOpenMP -fopenmp -ffree-form -fbounds-check -fimplicit-none"
## LIBFLAGS=" "
#Intel--FORTRAN90-Compiler :
#CF=ifc
#CF=ifort
#CF=/opt/intel_fc_80/bin/ifc
#Intel--FORTRAN90-Compiler Flags :
# -u        # <==> implicit none
# -r8       # 64bit
# -O3       # Highest optimization
# -prefetch # Only with -O3
# -tpp7     # Optimization for Pentium 4
#FFLAGS="-free -g -C -132 -openmp"
#FFLAGS="-DTEST1 -Dprint2 -DOpenMP -openmp -O3 -fast -axAVX"
#FFLAGS="-DTEST1 -Dprint2 -O3 -fast -axAVX"
#FFLAGS="-DTEST1 -Dprint2 -DOpenMP -openmp -O3 -fast"
#FFLAGS="-DTEST1 -Dprint2 -O3 -fast"
#LIBFLAGS="-Xlinker -rpath -Xlinker /opt/intel/compiler70/ia32/lib -I/opt/intel/compiler70/ia32/include"
#
${CF} ${FFLAGS} ${LIBFLAGS} -o $fn.exe $fn.f90
#${CF} ${FFLAGS} ${LIBFLAGS} -o $fn.exe $fn.F90

