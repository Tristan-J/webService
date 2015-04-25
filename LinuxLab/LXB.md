**E1.**
-	No report required. 

**E2.**
-	vmware + ubuntu
	-	1.命令行->图形切换:	
		-	(ctrl + alt + space) + (ctrl + alt + F7) 
	-	2.图形->命令行切换(F1可换为F2-6)(tty1-tty6):	
		-	(ctrl + alt + space) + (ctrl + alt + F1) 
	-	3.F1-F6都会显示login提示，F8-F12没有反应

**E3.**
-	VMware + Ubuntu Kylin(GUI: Unity)
	-	1.system settings
		-	or find "help" on the top title of the window
		-	or see the -help api offered by application
	-	2.system settings -> apparence
	-	3.work space
		-	in Ubuntu 3.04+ it should be "Ctrl+Alt + arrow keys" after turning workspace on
		-	and in vmware, it should be "Ctrl+Alt + space" + "Ctrl+Alt + arrow keys"
	-	4.maximaze the window of the application
	-	5.show some options like "alwasy on the top" and "always on visible workspace"
	-	6.alt + pressing left button
	-	7.e.g Terminal
		-	file:
			-	open new terminal 
			-	open tab
			-	create new profile
			-	close tab
			-	close window
		-	edit
			-	copy
			-	paste
			-	select all
			-	keyboard short cuts
			-	set profile preference 
		-	view
			-	show manuebar
			-	full screen
			-	zoom in 
			-	zoom out
			-	normal size
		-	search
			-	search for text in the console
		-	terminal
			-	change profile
			-	set title
			-	set character encoding
			-	reset 
			-	change size
		-	help
			-	content 
			-	about
	
**E4.**
-	No report required. 
	-	
	
**E5.**
-	1.man man: the description and usage of "man" command
	-	-a: show all of the available manual pages
	-	-k:	show all the commands that have the key word given and display the brief intro
	-	-f
	-	-w
	-	
-	2.
	-	show the files' information
	-	ls -l
	-	show all the files' information including the ones unber the folders
	-	

**E6.**
-	1.
	-	Section #1 : User Commands
	-	Section #2 : System Calls
	-	Section #3 : C Library Functions
	-	Section #4 : Devices and Special Files
	-	Section #5 : File Formats and Conventions
	-	Section #6 : Games et. Al.
	-	Section #7 : Miscellanea
	-	Section #8 : System Administration tools and Deamons
	-	
-	2.
	-	the location of the configuration file, manual page hierachy and db caches
	-	description
	-	see also
	-	the first line of the option's description
	-	search the manual page names and descriptions
	-	

**E7.**
-	1.
	-	at the top of the info tree
	-	x q H Up Down DEL SPC HOME END TAB RET l [ ]......
	-	
-	2.
	-	actual example
	-	details about version sort
	-	general output formatting
	-	
-	3.
	-	s or i and search for a next one with { or }
	-	

**E8.**<<
-	1.the doc in iproute redirect to the ../iproute2, and there's no kernel version message mentioned in the docs
-	2.it doesn't mention what are supported in iproute

**E9.**
-	1.
	-	/home/rijag112/ bin/mplayer
	-	/usr/bin/ssh
-	2.
	-	./ssh
	-	../bin/ssh
	-	../../../usr/bin/ssh

**E10.**
-	1. sudo chmod u+rw,go+r file
-	2. sudo chmod g+w file
-	3. sudo chmod u+x file

**E11.**
-	1. 666: rw-rw-rw-
	-	770: rwxrwx---
	-	640: rw-r-----
	-	444: r--r--r--
-	2.
	-	X	特殊执行权限	只有当文件为目录文件，或者其他类型的用户有可执行权限时，才将文件权限设置可执行
	-	s	setuid/gid	当文件被执行时，根据who参数指定的用户类型设置文件的setuid或者setgid权限
	-	t	粘贴位	设置粘贴位，只有超级用户可以设置该位，只有文件所有者u可以使用该位
-	3. -R
-	4. a

**E12.**
-	1.sudo chown -R tristan:users

**E13.**
-	1.上级
-	2.上级上级
-	3.显示根目录
-	4.显示隐藏文件、以及当前目录下文件的详细信息
-	5.
	-	目录 权限 连接计数为22 dave:staff 大小4096 更新日期 Jan 12 2001 名称dir/
	-	特殊字符文件 权限 连接计数为1 root:audio 更新日期 Jan 22 2001 名称 dsp 
-	6. 
	-	a覆盖 b
	-	mv -r a b
-	7. cp -p /dir1/* /dir2/*
-	8. 
	-	sudo chown root:wheel
	-	sudo chmod 640 secret
-	9. sudo rm -r Dir
-	10.将/path/to/directory/及目录中的文件拥有者和用户组改为user:user
-	11. ls -l
-	12. file test.file
-	13. 正常创建但不可用

**E14.**
-	1. only bash
-	2. /etc/profile 启动时使用

**E15.**
-	1. touch + names of the files
-	2. 自动补全到cat READ
-	3. 显示符合的文件
-	4. 
	-	null
	-	显示MAKEDEV ModemManager
-	5. null
-	6. null
-	7. 补充到aut 然后显示aut之后继续补全的待选命令

**E16.**
-	1. env | grep PATH: path下为各bin路径
-	2. /home/tristan 用户的根目录
-	3. TEST = "this is a test"
-	4. echo $TEST
	-	this is a test
-	5. PATH=/home/TDDI05/bin:$PATH

**E17.**
-	1. TEST="noscript"
-	2. script started, file is ...
-	3-7 按照tutorial输入
-	content: 
  1 Script started on Wed 22 Apr 2015 07:54:39 PM CST
  2 ^[]0;tristan@ubuntu: ~/workspace/Temp^Gtristan@ubuntu:~/workspace/Temp$ ifconfig a^H^[[K-a^M
  3 eth0      Link encap:Ethernet  HWaddr 00:0c:29:d8:3c:b5  ^M
  4           inet addr:192.168.239.169  Bcast:192.168.239.255  Mask:255.255.255.0^M
  5           inet6 addr: fe80::20c:29ff:fed8:3cb5/64 Scope:Link^M
  6           UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1^M
  7           RX packets:16991 errors:0 dropped:0 overruns:0 frame:0^M
  8           TX packets:515 errors:0 dropped:0 overruns:0 carrier:0^M
  9           collisions:0 txqueuelen:1000 ^M
 10           RX bytes:1611007 (1.6 MB)  TX bytes:68132 (68.1 KB)^M
 11 ^M
 12 lo        Link encap:Local Loopback  ^M
 13           inet addr:127.0.0.1  Mask:255.0.0.0^M
 14           inet6 addr: ::1/128 Scope:Host^M
 15           UP LOOPBACK RUNNING  MTU:65536  Metric:1^M
 16           RX packets:332 errors:0 dropped:0 overruns:0 frame:0^M
 17           TX packets:332 errors:0 dropped:0 overruns:0 carrier:0^M
 18           collisions:0 txqueuelen:0 ^M
 19           RX bytes:37280 (37.2 KB)  TX bytes:37280 (37.2 KB)^M
 20 ^M
 21 ^[]0;tristan@ubuntu: ~/workspace/Temp^Gtristan@ubuntu:~/workspace/Temp$ ifconfig -a^H^H^H^H^H^H^H^H^H^H^Hsudo reboot^H^H^H^H^H^H^H^H^H^H^Hif    config -a^H^H^H^H^H^H^H^H^H^H^Hsudo reboot^H^H^H^H^H^H^H^H^H^H^Hifconfig -a^H^H^H^H^H^H^H^H^H^H^H^[[K^G^Gifconfig -a^H^H^H^H^H^H^H^H^H^H^Hsu    do reboot^H^H^H^H^H^H^H^H^H^H^H^[[5Preboot^H^H^H^H^H^Hsudo chown tristan:tristan .Xauthority^M^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[    [C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[Ccd tristan/^[[K^H^H^H^H^H^H^H^H^H^H^Hsudo chown trista    n:tristan .Xauthority^M^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^[[C^    [[C^[[C^[[C^[[Creboot^[[K^H^H^H^H^H^Hsudo reboot^H^H^H^H^H^H^H^H^H^H^Hifconfig -a^H^H^H^H^H^H^H^H^H^H^H^[[K^G^G^Gs^H^[[KTEST=""^Hs"^Hc"^Hr"^    Hi"^Hp"^Ht"^H^M
 22 ^[]0;tristan@ubuntu: ~/workspace/Temp^Gtristan@ubuntu:~/workspace/Temp$ exit^M
 23 exit^M
 24 
 25 Script done on Wed 22 Apr 2015 07:55:39 PM CST

-	因为所有的输入输出都在script创建的shell中，exit之后的shell和之前的并非同一个

**E18.**
-	1. stdout重定向为file1
-	2. stderr重定向为stdout的位置,stdout定向为file1
-	3. stderr和stdout融合并输出到file1

**E19.**
-	1. 
	-	显示当前目录下名字中有大写或小写“doc”的文件
	-	stderr定向为stdout的位置，并将command的stdout作为grep的输入，在屏幕上显示command stdout中含有“fail”的行
	-	stderr定向为stdout的位置，并将stdout定向为/dev/null，通道没有起作用
-	2.
	-	ls -ar >/tmp/HOMEFILES
	-	find /home/tristan/ -perm -u=r,-g=r,-o=r 2>/dev/null
	-	find /etc/* | grep -rE 10.17.1\|130.236.189 -l >/tmp/FILES 或 ... egrep -r ...
	-	ls -ar	~/ | tee -a /tmp/HOMEFILES
-	3.
	-	find /etc/* | grep -rE 10.17.1\|130.236.189 -l | head -1 | xargs cat -

**E20.**
-	1. 按照操作执行
-	2. 执行ping操作后 ps aux | grep ping ,之后kill
-	3. ps -auxf
-	4.
	-	kill -9: 发送无条件终止进程信号 -9
	-	kill -l:列出所有进程名称; kill 0: 终止所有由当前shell启动的进程
	-	kill -9 -1: kill all of the process that you can kill

**E21.**
-	1. PATH=/home/TDDI05/bin:$PATH
-	2. set nowrap

**E22.**
-	1. g or < or esc+<
-	2. G or > or esc+>
-	3. /baloney
-	4. n
-	5. F
-	6. zcat README.Debian.gz | less
-	7. 
	-	no
	-	install xauth on the server. In Debian this si a in the xbase-clients package

**E23.**
-	1. sed -e 's/\/bin\/sh/\/bin\/tcsh/g' <etc/passwd >~/Tempfile
-	2. 
	-	paste -d " " ~/TDDI05/lxb/passwd ~/TDDI05/lxb/shadow >~/Tempfile
	-	awk: awk 'NR==FNR{a[i]=$0;i++}NR>FNR{print a[j]" "$0;j++}' ~/TDDI05/lxb/passwd ~/TDDI05/lxb/shadow >~/Tempfile

**E24.**
-	1. 不断展示该日志的最新内容
-	2. grep cron /var/log/syslog | tail -10

**E25.**
-	1.
	-	stdout:file1 stderr:screen
	-	stdout:file1 stderr:file1


-	2. 
``` bash
last | head -n -2 | awk '{print $1}'| tac | awk '{ if (!seen[$1]++) { print $1; } }' | head -10 | while read Line; do  grep "$Line.*ssh" /var/log/auth.log | tail -1; done | awk '{print $1, $2, $3}'
```

-	3. 
	-	将标注输入的内容输入文件标识符23
	-	将文件描述符为24重定向到stdout中
	-	找到 / 下名字以.bak结尾的文件并打印
	-	输出重定向到 /tmp/RECORD
	-	读取每一行到变量f
		-	echo内容"Record" + 读入行的内容，删除自动添加的换行符，并输出到文件标识符24(此时即为标准输出)
		-	从标识符23(现在为标准输入)中读入数据，并赋值给ans
		-	如果ans的值为"y"，则echo出f的内容


-	4. socat <<
	-	(sleep 5; cat <file.key>; sleep 5; cat <file.command>; sleep 1) | socat - EXEC:'ssh -l <user> <ip>',pty,setsid,ctty
	-	对于所有15000端口的TCP访问，转发到 server.tristan.test:15000 上:
``` bash
socat -d -d -lf /var/log/socat.log TCP4-LISTEN:15000,reuseaddr,fork,su=nobody TCP4:server.tristan.test:15000
```


-	5. ps | grep "linux" | grep -Po "\d{5,10}" | xargs kill -9
	-	ps | grep "linux" 不会有grep的pid


-	6. 
``` bash
##e25.6.sh
find ./ -name "*.bak" -exec sh handleBak.sh {} \;
```
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


-	7.
``` bash
##e25.7.sh
#this is a test about replacement 10.17.1 with 130.236.189
find /etc | while read File
do
        if [ -f "${File}" ]; then
                grep -l "10.17.1" $File | xargs awk -v file=$File '$1 {print file}' | sort | uniq
                sed -i "s/10.17.1/130.236.189/g" $File
        fi
done
```

-	8.
``` bash
sh e25.8.sh
##e25.8.sh
awk '{ print $1 }' temp1 >temp1_temp;
awk '{ print $2 }' temp2 >temp2_temp;
paste temp1_temp temp2_temp >temp3;
exit 0;
```

-	9.
``` bash
find ./temp1 -depth -exec bash e25.9.sh {} \;

##e25.9.sh
filename=$1
oPut() {
        read foo
        if [ -z "$foo" ]
        then 
                echo "message: $filename"
        else
                #rename 'y/A-Z/a-z/' $foo
                rename 's/(.*)\/([^\/]*)/$1\/\L$2/' $foo
        fi
}
echo $1 | grep [A-Z] | oPut;
```
