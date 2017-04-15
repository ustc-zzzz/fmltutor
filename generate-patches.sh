#!/bin/bash

function do-apply
{
    init="$(git rev-list --max-parents=0 HEAD)"
    patches="$(git format-patch ${init}..master \
        --minimal --no-add-header --no-stat \
        --no-signature --keep-subject --zero-commit)"
    for name in $patches
    do
        commit=${name:5:-6}
        newname="$(ls ../*${commit}*.patch 2>/dev/null | head -n1)"
        if [ -z "$newname" ]
        then
            echo -n "Please define a description for $name: "
            read description
            newname="${name:0:5}$description.patch"
        else
            newname="${newname:3}"
        fi
        mv $name ${newname}
        echo ${newname}
    done
    rm ../*.patch
    mv *.patch ../
}

cd $(dirname $0)

cd build && do-apply
