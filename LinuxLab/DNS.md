*DNS*

**E1.**
-	1. no report(review the section on DNS from the basic network course)
-	2.  (some of them are not found in RFC but in wikipedia)
	 -	1. Authoritative name server is a name server that gives answers in response to questions asked about names in a zone. And it can also act as a chache server.
	-	2. Every domain name appears in a zone served by one or more authoritative name servers. Domain name servers store information about part of the domain name space called a zone. 
    A zone is simply a portion of a domain. For example, the Domain Microsoft.com may contain all of the data for Microsoft.com, Marketing.microsoft.com and Development.microsoft.com. However, the zone Microsoft.com contains only information for Microsoft.com and references to the authoritative name servers for the subdomains. 
	 -	3. divide an upper level zone to some smaller ones and part of DNS namespace will be delegated to another location or department in your organization
	 -	4. DNS resource records (RRs) describe the characteristics of a zone (or domain) and have a binary or wire-format, which is used in queries and responses
	 -	5. compare the head of data in message, if the timestamp is pretty previous, then we can say it comes from the cache
	 -	6. The answer section contains RRs that answer the question; the authority section contains RRs that point toward an authoritative name server; the additional records section contains RRswhich relate to the query, but are not strictly answers for the question.
	 -	7. in the header section of dns protocol, If RD(Recursion Desired) is set, it directs the name server to pursue the query recursively.Recursive query support is optional.
	 -	8. NS RR
-	3. 
	-	1. host DOMAIN-NAME NAME-SERVER
	-	2. dig @NAME-SERVER DOMAIN-NAME
	-	3. recursion is automaticaly disabled when the +nssearch or +trace is query option is used

**E2. **
-	1. 
	-	1. 130.236.177.26
	-	2. 130.236.177.26 www.ida.liu.se is an alias for informatix.ida.liu.se
	-	3. 130.236.5.66
-	2. 更改名字服务器之后，该domain name 不是ultradns收录的服务对象
-	3. host www.ida.liu.se dns.liu.se

**E3. **
-	1. 
``` bash
tristan@ubuntu:~$ host -t ns amazon.com
amazon.com name server ns1.p31.dynect.net.
amazon.com name server ns3.p31.dynect.net.
amazon.com name server ns2.p31.dynect.net.
amazon.com name server pdns1.ultradns.net.
amazon.com name server ns4.p31.dynect.net.
amazon.com name server pdns6.ultradns.co.uk.
```
-	2. 0<<
-	3. 10
``` bash
tristan@ubuntu:~$ host -a sysinst.ida.liu.se
Trying "sysinst.ida.liu.se"
;; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 43184
;; flags: qr rd ra; QUERY: 1, ANSWER: 4, AUTHORITY: 2, ADDITIONAL: 3

;; QUESTION SECTION:
;sysinst.ida.liu.se.		IN	ANY

;; ANSWER SECTION:
sysinst.ida.liu.se.	5	IN	MX	10 ida-gw.sysinst.ida.liu.se.
sysinst.ida.liu.se.	5	IN	SOA	sysinst-gw.ida.liu.se. davby.ida.liu.se. 2014120200 3600 1800 604800 3600
sysinst.ida.liu.se.	5	IN	NS	ns.ida.liu.se.
sysinst.ida.liu.se.	5	IN	NS	sysinst-gw.ida.liu.se.

;; AUTHORITY SECTION:
sysinst.ida.liu.se.	5	IN	NS	ns.ida.liu.se.
sysinst.ida.liu.se.	5	IN	NS	sysinst-gw.ida.liu.se.

;; ADDITIONAL SECTION:
ida-gw.sysinst.ida.liu.se. 5	IN	A	130.236.189.1
ns.ida.liu.se.		5	IN	A	130.236.177.25
ns.ida.liu.se.		5	IN	AAAA	2001:6b0:17:f020::53

Received 231 bytes from 127.0.1.1#53 in 719 ms

```
-	4. 
``` bash
tristan@ubuntu:~$ host -a  ida.liu.se
Trying "ida.liu.se"
;; ->>HEADER<<- opcode: QUERY, status: NOERROR, id: 19182
;; flags: qr rd ra; QUERY: 1, ANSWER: 4, AUTHORITY: 4, ADDITIONAL: 3

;; QUESTION SECTION:
;ida.liu.se.			IN	ANY

;; ANSWER SECTION:
ida.liu.se.		5	IN	NS	ns1.liu.se.
ida.liu.se.		5	IN	NS	ns2.liu.se.
ida.liu.se.		5	IN	NS	nsauth.isy.liu.se.
ida.liu.se.		5	IN	NS	ns.ida.liu.se.

;; AUTHORITY SECTION:
ida.liu.se.		5	IN	NS	ns1.liu.se.
ida.liu.se.		5	IN	NS	ns2.liu.se.
ida.liu.se.		5	IN	NS	nsauth.isy.liu.se.
ida.liu.se.		5	IN	NS	ns.ida.liu.se.

;; ADDITIONAL SECTION:
ns.ida.liu.se.		5	IN	A	130.236.177.25
ns.ida.liu.se.		5	IN	AAAA	2001:6b0:17:f020::53
nsauth.isy.liu.se.	5	IN	A	130.236.48.9

Received 222 bytes from 127.0.1.1#53 in 16 ms

```

**E4. **
-	1. AUTHORITY: 0, ADDITIONAL: 14
-	2. 
	-	1. 130.236.189.1
	-	2. 
``` bash
sysinst.ida.liu.se.	5	IN	NS	sysinst-gw.ida.liu.se.
sysinst.ida.liu.se.sys.gtei.net	5	IN	NS	ns.ida.liu.se.
```
	-	3. 6.5.4.3.in-addr.arpa
-	3. 
	-	1. 
	``` bash
	dig www.ida.liu.se +trace | more
	.					202.112.14.151
	se.					g.root-servers.net
	liu.se.				c.ns.se
	ida.liu.se.			ns3.liu.se
	www.ida.liu.se.		ns1.liu.se
	```
	-	2.
	``` bash
	dig update.microsoft.com +trace | more
	.						202.112.14.151
	com.					f.root-servers.net
	microsoft.com.			f.gtld-servers.net
	update.microsoft.com.	ns4.msft.net
	```

**E5.**
![dig www.example.com](http://img.blog.csdn.net/20150529050456133)

**E6.**
![zone of example.com.db](http://img.blog.csdn.net/20150529050446645)

![dig mta.example.com](http://img.blog.csdn.net/20150529050415529)

**E7.**
![zone of reverse](http://img.blog.csdn.net/20150529050510544)

![dig ns1.example.com](http://img.blog.csdn.net/20150529050616735)
