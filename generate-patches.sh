#!/bin/bash

function do-apply
{
    rm *.patch 2>/dev/null
    init="$(git rev-list --max-parents=0 HEAD)"
    ident="Yanbing\\ Zhao\\ \\<zzzz@mail.ustc.edu.cn\\>"
    patches="$(git -c format.from=Yanbing\ Zhao\ \<zzzz@mail.ustc.edu.cn\> \
        format-patch ${init}..master --minimal --keep-subject --zero-commit \
        --no-add-header --no-stat --no-signature)"
    if [ $? -eq 0 ]
    then
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
    fi
}

cd $(dirname $0)

cd build && do-apply
