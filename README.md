FileSort
======
**FileSort** can sort the files by name or content. The usage is explained like this:

Copy both files from the **bin** folder on your computer and run

```
$ java -jar Files-sort.jar
```

Options run the script

```
-c config.properties      set config files
-f typefiles              set file extensions
-d dir                    set working dir
```
## Description file config.properties
* **Set working dir**  
DIR=/tmp/sort        #mac & linux  
DIR=c\:\\tmp\\sort   #Windows  

* **Set file extensions**  
TYPEFILES=* #all files in work dir  
TYPEFILES=txt #all files in work dir  
TYPEFILES=xml #all files in work dir  

* **Set rules example**  
*Search in the name of the files with the key 2016. The result is moved to a folder /tmp/sort/2016*  
```name\:2016=/tmp/sort/2016/ # mac & linux```  
```name\:2016=c\:\\sort\\2016\\ # windows```  
*Search within a file with the key 2017. The result of the is moved to a folder /tmp/sort/2017*  
``` body\:2017=/tmp/sort/2017/  #mac & linux```  
``` body\:2017=c\:\\sort\\2017\\ #windows```  

## Version 
* 0.1

## Contact
* tox: F4CDF19528FF9CA1A526AA295334558E051234419DC0305D1F64BEB189885D2DD5E33458B0B6
* mail: alex@ganin.su
