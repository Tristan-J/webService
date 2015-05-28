*Networking*

**Goals of this lab:**
- To learn how to configure network connections in a UNIX system.
- To gain experience with fundamental routing. 

**E1.**
- 1.
  - 1. ping, ping6 - send ICMP(internet control message protocal) ECHO_REQUEST to network hosts 
    - ECHO_REQUEST includes IP head and ICMP head + a timestamp + pads
    - ping6 is lpv6 version of ping, and does not allow source routing(path addressing)
  - 2. send ECHO_REQUEST to hosts and wait for the ECHO_RESPONSE
    - the structure of ECHO_REQUEST could be found above
  - 3. ping 10.17.1.1
  - 4. 
    - input ifconfig
    - find the "inet addr:192.168.194.128  Bcast:192.168.194.255  Mask:255.255.255.0"
    - ping -b 192.168.194.255
  - 5. It is the broadcast address of the zero network or 0.0.0.0, which in Internet Protocol standards stands for this network, i.e. the local network. Transmission to this address is limited by definition, in that it is never forwarded by the routers connecting the local network to other networks.
    - and if we can send a broadcast message to 255.255.255.255 without limitation, all the hosts on the internet will receive the ECHO_REQUEST
  - 6. ping -n ADDRESS
- 2. 
  - 1. print the route packets trace to network host
  - 2. traceroute  tracks  the route packets taken from an IP network on their
     way to a given host. It utilizes the IP protocol's time to  live  (TTL)
     field  and  attempts to elicit an ICMP TIME_EXCEEDED response from each
     gateway along the path to the host.
  - 3. it prints numerical result by default
  - 4. traceroute 150.203.99.8 | sed -n '4,$'p
- 3.
  - 1. 
    - ifconfig eth0 130.236.189.14 netmask 255.255.255.0  broadcast 130.236.189.255
    - ip addr add 130.236.189.14/24 dev eth0; ip addr add broadcast 130.236.189.255 dev eth0 
    2. 
    - with "route": route
    - with "ip": ip route (ip r)
    - with "netstat": netstat -r
- 4.
  - 1. from wikipedia: In Linux, the sysctl interface mechanism is also exported as part of procfs(process file system) under the /proc/sys directory (not to be confused with the /sys directory). This difference means checking the value of some parameter requires opening a file in a virtual file system, reading its contents, parsing them and closing the file. The sysctl system call does exist on Linux, but does not have a wrapping function in glibc and is not recommended for use.
  - 2. /etc/sysctl.conf
  - 3. 
  ``` bash
    # Uncomment the next line to enable packet forwarding for IPv4
    #net.ipv4.ip_forward=1

    # Uncomment the next line to enable packet forwarding for IPv6
    #  Enabling this option disables Stateless Address Autoconfiguration
    #  based on Router Advertisements for this host
    #net.ipv6.conf.all.forwarding=1
  ```
- 5. 
  - inet addr:192.168.194.128  Bcast:192.168.194.255  Mask:255.255.255.0
  - hostname: ubuntu

**E2.**
- 因为busybox-shellhostname显示为路径，所以这里展示修改mln配置,重新build后,vm中的hosts以及hostname文件内容:
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
192.168.0.4 server
192.168.0.2 client-1
root@/etc/hostname:/etc$ cat hostname
client-2

#host server 
root@/etc/hostname:/etc$ cat hosts
127.0.0.1 localhost server
192.168.0.3 client-2
192.168.0.2 client-1
root@/etc/hostname:/etc$ cat hostname
server

#host client-1
root@/etc/hostname:/etc$ cat hosts
127.0.0.1 localhost client-1
192.168.0.3 client-2
192.168.0.4 server
root@/etc/hostname:/etc$ cat hostname
client-1

```

**E3.(gateway) & E4.(client)**
- ifconfig output:
``` bash
#router gw
root@/etc/hostname:~$ ifconfig
eth0    Link encap:Ethernet  HWaddr FE:FD:00:00:8A:DE  
      inet addr:192.168.0.1  Bcast:192.168.0.255  Mask:255.0.0.0
      UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
      RX packets:0 errors:0 dropped:0 overruns:0 frame:0
      TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
      collisions:0 txqueuelen:1000 
      RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
      Interrupt:5 

eth1    Link encap:Ethernet  HWaddr FE:FD:00:00:34:71  
      inet addr:192.168.194.2  Bcast:192.168.194.255  Mask:255.255.255.0
      UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
      RX packets:0 errors:0 dropped:0 overruns:0 frame:0
      TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
      collisions:0 txqueuelen:1000 
      RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
      Interrupt:5 

lo    Link encap:Local Loopback  
      inet addr:127.0.0.1  Mask:255.0.0.0
      UP LOOPBACK RUNNING  MTU:16436  Metric:1
      RX packets:0 errors:0 dropped:0 overruns:0 frame:0
      TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
      collisions:0 txqueuelen:0 
      RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

#host client-2
root@/etc/hostname:/etc$ ifconfig
eth0    Link encap:Ethernet  HWaddr FE:FD:00:00:D4:D6  
      inet addr:192.168.0.3  Bcast:192.168.0.255  Mask:255.255.255.0
      UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
      RX packets:0 errors:0 dropped:0 overruns:0 frame:0
      TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
      collisions:0 txqueuelen:1000 
      RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
      Interrupt:5 

lo    Link encap:Local Loopback  
      inet addr:127.0.0.1  Mask:255.0.0.0
      UP LOOPBACK RUNNING  MTU:16436  Metric:1
      RX packets:0 errors:0 dropped:0 overruns:0 frame:0
      TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
      collisions:0 txqueuelen:0 
      RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

#host server 
root@/etc/hostname:/etc$ ifconfig
eth0    Link encap:Ethernet  HWaddr FE:FD:00:00:EF:DB  
      inet addr:192.168.0.4  Bcast:192.168.0.255  Mask:255.255.255.0
      UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
      RX packets:0 errors:0 dropped:0 overruns:0 frame:0
      TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
      collisions:0 txqueuelen:1000 
      RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
      Interrupt:5 

lo    Link encap:Local Loopback  
      inet addr:127.0.0.1  Mask:255.0.0.0
      UP LOOPBACK RUNNING  MTU:16436  Metric:1
      RX packets:0 errors:0 dropped:0 overruns:0 frame:0
      TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
      collisions:0 txqueuelen:0 
      RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)

#host client-1
root@/etc/hostname:/etc$ ifconfig
eth0    Link encap:Ethernet  HWaddr FE:FD:00:00:BD:92  
      inet addr:192.168.0.2  Bcast:192.168.0.255  Mask:255.255.255.0
      UP BROADCAST RUNNING MULTICAST  MTU:1500  Metric:1
      RX packets:0 errors:0 dropped:0 overruns:0 frame:0
      TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
      collisions:0 txqueuelen:1000 
      RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
      Interrupt:5 

lo    Link encap:Local Loopback  
      inet addr:127.0.0.1  Mask:255.0.0.0
      UP LOOPBACK RUNNING  MTU:16436  Metric:1
      RX packets:0 errors:0 dropped:0 overruns:0 frame:0
      TX packets:0 errors:0 dropped:0 overruns:0 carrier:0
      collisions:0 txqueuelen:0 
      RX bytes:0 (0.0 B)  TX bytes:0 (0.0 B)
```

**E5. & E6.**
- 修改完后路由表如下：
  - gw:
  [gw路由表](http://img.blog.csdn.net/20150527033002301)
  - one of internal hosts:
  ![internal host路由表](http://img.blog.csdn.net/20150527040422915)


**E7.**
- 1. 
  - file: /etc/default/nss
  - file: /etc/recolv.conf
  - keywords:
    - nameserver   #定义DNS服务器的IP地址
    - domain     #定义本地域名
    - search     #定义域名的搜索列表
    - sortlist   #对返回的域名进行排序
  - (1) local file  (2) DNS server
    - reason: 可以避免GW的DNS污染之类的
- 2-4. 
![gw-debian use dns server resolves BaiDu](http://img.blog.csdn.net/20150527032415425)


**E8.**
- 1. 
  - zebra - kernel interface, static routes, zserv server
  - ripd  ripngd  - RIPv1/RIPv2 for IPv4 and RIPng for IPv6
  - ospfd ospf6d  - OSPFv2 and OSPFv3
  - bgpd  - BGPv4+ (including address family support for multicast and IPv6)
  - isisd - IS-IS with support for IPv4 and IPv6
  - under development or unmaintained:
    - olsrd   - OLSR wireless mesh routing through a plugin for olsrd
    - ldpd  - MPLS Label Distribution Protocol
    - bfdd  - Bidirectional Forwarding Detection
- 2. /etc/quagga/debian.conf
- 3. 
  - (1) with an integrated user interface shell called vtysh.g
  - (2) Each daemon has it’s own configuration file and terminal interface. When you configure a static route, it must
be done in zebra configuration file. When you configure BGP network it must be done in bgpd configuration file.
  - (3) telnet localhost (service name)


**E9.**
- 路由信息协议(Routing Information Protocol，缩写：RIP)是一种使用最广泛的内部网关协议(IGP).(IGP)是在内部网络上使用的路由协议(在少数情形下,也可以用于连接到因特网的网络),它可以通过不断的交换信息让路由器动态的适应网络连接的变化, 这些信息包括每个路由器可以到达哪些网络, 这些网络有多远等, RIP 属于网络层。
- content: prefix of the network; to: 10.0.0.0/24

**E10.**
- ![之前的路由表](http://img.blog.csdn.net/20150527033002301)

![进行rip操作后](http://img.blog.csdn.net/20150527033434943)

![操作后的路由表](http://img.blog.csdn.net/20150527032806814)



**E11.**
- 外部网络中的gateway中没有试验所述网络，所以通过telnet连接gw的zebra服务后，打印该gw的show ip route: 
![这里写图片描述](http://img.blog.csdn.net/20150527035044784)


**E12.**
- ![从内网访问Baidu](http://img.blog.csdn.net/20150527033115200)


**E13.**
- 1.
  ``` bash 
  # if other groups' networks are like 10.17.2.0 or 10.17.254.0
  ip prefix-list allowList permit 10.17.0.0 ge 16 
  ip prefix-list denyList deny 10.17.1.0 ge 25
  ```
- 2. 
  ``` bash
  # didn't find the prefix-way and here's the bgp way
  bgp router-id 192.168.1.1
  ```
