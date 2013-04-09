simhash-java
============

A simple implementation of simhash algorithm by java.

1. compute the simhash of a string
2. compute the similarity between all the strins by build smart index, so We can deal with big data.

How to use:

run Main with inputfile and outputfile.

The format of inputfile: one doc eachline with the utf8 charset.

The format of outputfile: 
start //start flag
first line // doc
sencode lien // doc1\tdist the dist is the hamming distance between doc and doc1 
end //end flat

