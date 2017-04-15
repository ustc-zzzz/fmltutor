#!/bin/bash

function do-apply
{
    cp ../LICENSE LICENSE
    
    git init
    git add LICENSE
    git commit -m "Initial commit" >/dev/null
    git tag -f start

    echo -n "Applying patches: "
    git am --whitespace=nowarn --keep-cr ../patch/*.patch | while read m
    do echo -n '.'; done
    echo " done"
}

function do-clean
{
    rm -rf build/
}

function do-build
{
    mkdir build/ && cd build && do-apply
}

cd $(dirname $0)

case $1 in
    build) do-build;;
    clean) do-clean;;
    *) do-clean; do-build;;
esac
