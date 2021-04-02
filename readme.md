# Huffman Encoding
This project is a working example of non-preemptive Huffman Encoding.
Frequencies are created on a letter by letter basis, not individual words or phrases.
# Compile

Go to root folder where ls = {src, pom.xml}
pom.xml is needed for maven
* Run in terminal
```
mvn -install
java -jar target/Huffman-1.jar {path.to.file.to.encode}
```
*make sure the files Tree.dat & encodedText.bin are in the same folder as src
