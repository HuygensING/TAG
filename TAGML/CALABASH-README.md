## Using the Alexandria Step in Calabash

- Install Calabash [http://xmlcalabash.com/download/]
- Download the latest alexandria-calabash jar from [https://cdn.huygens.knaw.nl/alexandria/] into the xmlcalabash `lib/` directory.
- in `calabash` and/or `calabash.bat`, add the `lib/` directory to the classpath
- Download an example pipeline from [https://raw.githubusercontent.com/HuygensING/alexandria-markup/develop/alexandria-calabash/src/test/resources/test-pipeline.xpl]
- Download an example tagml file from [https://raw.githubusercontent.com/HuygensING/alexandria-markup/develop/alexandria-calabash/src/test/resources/example.tagml]
- run calabash with the example pipeline:
  - windows:  
   `calabash.bat test-pipeline.xml`
  - other os:  
    `calabash test-pipeline.xml`
