# KV Store

Group Members:  
 - [Jiuzhi Qi](mailto:qijiuzhi@gwu.edu)  
 - [Tong Yang](mailto:yangtong@gwu.edu)
 - [Lijun Liu](mailto:lijun@gwu.edu)
 - [Xianquan Liao](mailto:xianquanliao@gwu.edu)

Extra Features:
 - Feature 1: Support a fixed sized data store with an eviction policy (LRU).  
 - Feature 2: Support for both TCP and UDP.  
 class `Server` news 2 objects, `TCPHandlerFactory` and `UDPHandlerFactory`. The object `TCPHandlerFactory` is responsible for handling TCP connections and `UDPHandlerFactory` for UDP connections.  
   
 - Feature 3: Support a MULTISET operation that allows several key/value pairs to be added in a single connection.  
 When users want to use MULTISET operation, you just need to pass `SET` operation followed by multiple key and value pairs.  
 
 - Feature 4: Support a MULTIGET operation that allows several key/value pairs to be added in a single connection.  
 When users want to use MULTIGET operation, you just need to pass `GET` operation followed by multiple keys.  
   
 - Feature 5: Support parallelism in the server using threads for both TCP and UDP.  
 Whenever `TCPHandlerFactory` gets a socket from a client, `TCPHandlerFactory` generate a new `TCPHandler` to serve the client. `UDPHandlerFactory` is similar to the `TCPHandlerFactory`.  
   
 ## Client usage  
 usage: java Client `<server>` `<protocol>` `<operation>` `<key>` `<value>`  

 - `<server>`  
 should be the IP address or the host name of the server  

 - `<protocol>`  
 should be "--TCP" or "-t" or "--UDP" or "-u"  (Ignore Case)

 - `<operation>`  
 should be "SET" or "GET" or "STATS"  (Ignore Case)

 - `<key>`  
 should be a continuous string without any space or carriage return. There can be multiple keys.  

 - `<value>`  
 should be a continuous string without any space or carriage return. There can be multiple vaules corresponding to keys, and the format should be `<key>` followed by `<value>`, followed by next `<key>`, followed by next `<value>`, and so forth.  

 ## Protocol Description
  Message for Requests (from Client to Server):  
    SET: operator + "\r\n" + count + "\r\n" + key + "\r\n" + value + "\r\n" + key + ....
    get: operator + "\r\n" + count + "\r\n" + key + "\r\n" + key +....
    stats: operator
    
 ## Performance Evaluation  
 ### Experimental Results
 - TCP maximum throughput for SET operation: 
    our server can handle 1768 SET requests per second.
 - The average latency for SET operation is 767192 ns. 
    
      
 - TCP maximum throughput for GET operation:  
    out server can handle 1863 GET requests per second.  
 - The average latency for GET operation is 831922 ns.  
    
## Usage for telnet
The TCP port number for our server is 5556.  
First of all, to connect our server by telnet, input "telnet localhost 5556", then input "<operation>". Input "<count of the key you want to get or set>" as a new line. Input "<key>" and "<value>" one by one. Each key and value should be a new line.  
For example:  
set
2  
key1  
value1  
key2  
value2  
   
