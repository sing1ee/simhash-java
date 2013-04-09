simhash-java
============

A simple implementation of simhash algorithm by java.

### Features:<br />  
1. compute the simhash of a string<br />  
2. compute the similarity between all the strins by build smart index, so We can deal with big data.<br />  

### How to use:
run Main with inputfile and outputfile.<br />  

The format of inputfile(see src/test_in): one doc eachline with the utf8 charset.<br />  

The format of outputfile(see src/test_out): <br />  
start //start flag<br />  
first line // doc<br />  
sencode lien // doc1\tdist the dist is the hamming distance between doc and doc1 <br />  
end //end flag<br />  

### Future:
1. Build the project to a runnable jar.<br />  
2. Improve the performace under big data.<br />  


