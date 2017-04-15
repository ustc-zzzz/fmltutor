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
    [ -d files/ ] && pushd files >/dev/null
    if [ $? -eq 0 ]
    then
        for file in $(git ls-files)
        do
            rm -f $file
        done
        rm -rf .git/
        find . -type d -empty -delete
        popd >/dev/null
    fi
}

function do-build
{
    mkdir -p files/ && pushd files >/dev/null
    if [ $? -eq 0 ]
    then
        do-apply
        popd >/dev/null
    fi
}

cd $(dirname $0)

case $1 in
    build) do-build;;
    clean) do-clean;;
    *) do-clean; do-build;;
esac
