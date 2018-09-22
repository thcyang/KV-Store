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
  protocol: 
  message String form(from Client to Server):
    set: operation+"\0"+count+"\0"+key+"\0"+value+"\0"+key+....
    get: operation+"\0"+count+"\0"+key+"\0"+key+....
    stats: operation+"\0"
    
  answer String form(from Server to Client):
    set: answer
    get: count+"\0"+answer+"\0"+answer+....
    stats: answer

 ## Performance Evaluation  
 ### Experimental Results
 - TCP maxThroughput for set operation: 1768  
    our server can handle 1768 set requests per second.  
 - The average latency for set is 767192 ns.  
 - TCP maxThroughput for get operation: 1863  
    out server can handle  1863 get requests per second.  
 - The average latency is 831922 ns.  
    
   
   
