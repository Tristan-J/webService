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