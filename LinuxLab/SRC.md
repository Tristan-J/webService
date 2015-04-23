**E1**
-	1. No report required.

**E2**
-	1. option2，流程：
        -       get the name of directory and files 
        -       get time stamp
        -       use tar to create a .tar archive with file names and stamp
        -       find if the number of .tar files, delete the most previous one (if the number of .tar begins from 0)
-	2. pseudocode:
``` bash
Names = get name list from Find(.backuprc)
TimeStamp = get time stamp
Tar $TimeStamp.tar Names
```

**E3**
-	1. 例：
``` bash
#!/bin/bash
#filename:e3.1.sh
echoP()
{
echo "$1"
}

echo "\$@:"
echoP "$@"
echo "\$*:"
echoP "$*"
```	

**E4**
-	1. 防止文件名中有空格的情况
-	2. 不可用，如果使用"$*", 则所有文件名会同时输入；如果使用 $* 不用引号，则会出现1.中所顾虑的问题

**E5**
-	1. 见LXB lab: 
``` bash
##handleBak.sh
fileBak=$1
fileName="${fileBak%.*}"
fileOrigin="$(basename $fileName)"
dirName="$(dirname $fileBak)"
fileOriginResult="$(find $dirName $fileOrigin)"
message=""
status="0"

if [ -z "$fileOriginResult" ]; then
        if [$fileOrigin -ot $fileBak]; then
                message="the original file is beyond of date."
                status="1"
        fi
else
        echo $fileBak
        echo $fileName
        message="there is no original file."
        status="1"
fi

if [ "$status" = "1" ]; then
        echo "$message"
        while true; do
                read -p "confirm to update your original file?[Y/N]" yn
                case $yn in
                        [Yy]* ) cp "$fileBak" "$fileName"; rm "$fileBak";break;;
                        [Nn]* ) echo "pass";break;;
                        * ) echo "please input Y or N";;
                esac
        done
fi

exit 0
```

**E6**
-	test coed：
``` bash
sh -x e6.1.sh
```

``` bash
#!/bin/bash -x
#filename: e6.1.sh
for p in "$@" ; do
        echo "$p"
done
exit 0
```

**E7**
-	option2:
``` bash
##/bin/bash -x
#filename: e7.1.sh
tempFile="$(mktemp)"
timeStamp=`date '+%Y%m%d-%H%M%S'`
find /home/ -name ".backuprc" | xargs cat >>"$tempFile"
cat "$tempFile" | xargs tar -cvf "/var/backups/userFile/$timeStamp.tar" -P
rm "$tempFile"
num="$(ls /var/backups/userFile/ | wc -l)"
if [ $num>10 ]; then
        find /var/backups/userFile/* | sort | head -1 | xargs rm
fi
exit 0

```