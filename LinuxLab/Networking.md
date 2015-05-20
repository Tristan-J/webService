*Networking*

**Goals of this lab:**
-	To learn how to configure network connections in a UNIX system.
-	To gain experience with fundamental routing. 

**E1.**
-	1.
	-	1. ping, ping6 - send ICMP(internet control message protocal) ECHO_REQUEST to network hosts 
		-	ECHO_REQUEST includes IP head and ICMP head + a timestamp + pads
		-	ping6 is lpv6 version of ping, and does not allow source routing(path addressing)
	-	2. send ECHO_REQUEST to hosts and wait for the ECHO_RESPONSE
		-	the structure of ECHO_REQUEST could be found above
	-	3. ping 10.17.1.1
	-	4. 
		-	input ifconfig
		-	find the "inet addr:192.168.194.128  Bcast:192.168.194.255  Mask:255.255.255.0"
		-	ping -b 192.168.194.255
	-	5. It is the broadcast address of the zero network or 0.0.0.0, which in Internet Protocol standards stands for this network, i.e. the local network. Transmission to this address is limited by definition, in that it is never forwarded by the routers connecting the local network to other networks.
		-	and if we can send a broadcast message to 255.255.255.255 without limitation, all the hosts on the internet will receive the ECHO_REQUEST
	-	6. ping -n ADDRESS
-	2. 
	-	1. print the route packets trace to network host
	-	2. traceroute  tracks  the route packets taken from an IP network on their
       way to a given host. It utilizes the IP protocol's time to  live  (TTL)
       field  and  attempts to elicit an ICMP TIME_EXCEEDED response from each
       gateway along the path to the host.
    -	3. it prints numerical result by default
    -	4. traceroute 150.203.99.8 | sed -n '4,$'p
-	3.
	-	1. 
		-	ifconfig eth0 130.236.189.14 netmask 255.255.255.0 	broadcast 130.236.189.255
		-	ip addr add 130.236.189.14/24 dev eth0; ip addr add broadcast 130.236.189.255 dev eth0 
    	2. 
    	-	with "route": route
    	-	with "ip": ip route (ip r)
    	-	with "netstat": netstat -r
-	4.
	-	1. from wikipedia: In Linux, the sysctl interface mechanism is also exported as part of procfs(process file system) under the /proc/sys directory (not to be confused with the /sys directory). This difference means checking the value of some parameter requires opening a file in a virtual file system, reading its contents, parsing them and closing the file. The sysctl system call does exist on Linux, but does not have a wrapping function in glibc and is not recommended for use.
	-	2. /etc/sysctl.conf
	-	3. 
		# Uncomment the next line to enable packet forwarding for IPv4
		#net.ipv4.ip_forward=1

		# Uncomment the next line to enable packet forwarding for IPv6
		#  Enabling this option disables Stateless Address Autoconfiguration
		#  based on Router Advertisements for this host
		#net.ipv6.conf.all.forwarding=1
-	5. 
	-	inet addr:192.168.194.128  Bcast:192.168.194.255  Mask:255.255.255.0
	-	hostname: ubuntu

**E2.**
-	因为busybox-shellhostname显示为路径，所以这里展示修改mln配置,重新build后,vm中的hosts以及hostname文件内容:
``` bash
#router gw
root@/etc/hostname:/etc$ cat hosts
127.0.0.1 localhost gw
192.168.0.4 server
192.168.0.2 client-1
192.168.0.3 client-2
root@/etc/hostname:/etc$ cat hostname
gw

#host client-2
root@/etc/hostname:/etc$ cat hosts
127.0.0.1 localhost client-2
127.0.0.1 gw
192.168.0.4 server
192.168.0.2 client-1
root@/etc/hostname:/etc$ cat hostname
client-2

#host server 
root@/etc/hostname:/etc$ cat hosts
127.0.0.1 localhost server
127.0.0.1 gw
192.168.0.3 client-2
192.168.0.2 client-1
root@/etc/hostname:/etc$ cat hostname
server

#host client-1
root@/etc/hostname:/etc$ cat hosts
127.0.0.1 localhost client-1
127.0.0.1 gw
192.168.0.3 client-2
192.168.0.4 server
root@/etc/hostname:/etc$ cat hostname
client-1

```

**E3.(gateway) & E4.(client)**
-	ifconfig output:
``` bash
#router gw
root@/etc/hostname:~$ ifconfig
eth0      Link encap:Ethernet  HWaddr FE:FD:00:00:8A:DE  
          inet addr:192.168.0.1  Bcast:192.168.0.255  Mask:255.0.0.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
          Interrupt:5 

eth1      Link encap:Ethernet  HWaddr FE:FD:00:00:34:71  
          inet addr:192.168.194.2  Bcast:192.168.194.255  Mask:255.255.255.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
          Interrupt:5 

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:16436  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:0 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

#host client-2
root@/etc/hostname:/etc$ ifconfig
eth0      Link encap:Ethernet  HWaddr FE:FD:00:00:D4:D6  
          inet addr:192.168.0.3  Bcast:192.168.0.255  Mask:255.255.255.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
          Interrupt:5 

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:16436  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:0 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

#host server 
root@/etc/hostname:/etc$ ifconfig
eth0      Link encap:Ethernet  HWaddr FE:FD:00:00:EF:DB  
          inet addr:192.168.0.4  Bcast:192.168.0.255  Mask:255.255.255.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
          Interrupt:5 

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:16436  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:0 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

#host client-1
root@/etc/hostname:/etc$ ifconfig
eth0      Link encap:Ethernet  HWaddr FE:FD:00:00:BD:92  
          inet addr:192.168.0.2  Bcast:192.168.0.255  Mask:255.255.255.0
          UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:1000 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
          Interrupt:5 

lo        Link encap:Local Loopback  
          inet addr:127.0.0.1  Mask:255.0.0.0
          UP LOOPBACK RUNNING  MTU:16436  Metric:1
          RX packets:0 errors:0 dropped:0 overruns:0 frame:0
          TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
          collisions:0 txqueuelen:0 
          RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
```
