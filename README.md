# KV Store

Group Members:  
 - [Jiuzhi Qi](mailto:qijiuzhi@gwu.edu)  
 - [Tong Yang](mailto:yangtong@gwu.edu)
 - [Lijun Liu](mailto:lijun@gwu.edu)
 - [Xianquan Liao](mailto:xianquanliao@gwu.edu)

Extra Features:
 - Feature 1: Support a fixed sized data store with an eviction policy (LRU).
 - Feature 2: Support for both TCP and UDP.
 - Feature 3: Support a MULTISET operation that allows several key/value pairs to be added in a single connection. 
 - Feature 4: Support a MULTIGET operation that allows several key/value pairs to be added in a single connection.
 - Feature 5: Support parallelism in the server using threads for both TCP and UDP.
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

 ## Performance Evaluation
