#!/bin/bash

function do-apply
{
    prefix="../patch"
    rm *.patch 2>/dev/null
    ident="Yanbing\\ Zhao\\ \\<zzzz@mail.ustc.edu.cn\\>"
    patches="$(git -c format.from=Yanbing\ Zhao\ \<zzzz@mail.ustc.edu.cn\> \
        format-patch start..master --minimal --keep-subject --zero-commit \
        --no-add-header --no-stat --no-signature)"
    if [ $? -eq 0 ]
    then
        for name in $patches
        do
            pattern="*${name:5:-6}*.patch"
            newname="$(cd ${prefix} && ls ${pattern} 2>/dev/null | head -n1)"
            if [ -z "$newname" ]
            then
                echo -n "Please define a description for $name: "
                read description
                newname="${name:0:5}$description.patch"
            fi
            mv $name ${newname}
            echo ${newname}
        done
        rm ${prefix}/*.patch
        mv *.patch ${prefix}
    fi
}

cd $(dirname $0)

cd files && do-apply
