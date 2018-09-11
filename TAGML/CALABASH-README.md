## Using the Alexandria Step in Calabash

- Install [Calabash](http://xmlcalabash.com/download/). Download the jar file and run the installer by running `java -jar xmlcalabash-1.1.22-98.jar`. 
- Download the latest alexandria-calabash jar from (https://cdn.huygens.knaw.nl/alexandria/) into the xmlcalabash `lib/` directory. On Mac OS this is in /Applications/xmlcalabash-versionnumber/lib/.
- MacOS/Unix: Add calabash to your path by `ln /Applications/xmlcalabash-1.1.22-98/calabash calabash`
- in `calabash` and/or `calabash.bat`, add the `lib/` directory to the classpath
- Download [an example pipeline](https://raw.githubusercontent.com/HuygensING/alexandria-markup/develop/alexandria-calabash/src/test/resources/test-pipeline.xpl)
- Download [an example tagml file](https://raw.githubusercontent.com/HuygensING/alexandria-markup/develop/alexandria-calabash/src/test/resources/example.tagml)
- run calabash with the example pipeline:
  - windows:  
   `calabash.bat test-pipeline.xml`
  - other os:  
    `calabash test-pipeline.xml`
