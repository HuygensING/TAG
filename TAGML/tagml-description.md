# TAGML description

## A syntax for the TAG model.

TAGML (Text-As-Graph Markup Language)

Textual phenomena and how to code them in TAGML

- markup
  ```
  [line>The rain in Spain falls mainly on the plain.<line]
  ```

- overlapping markup
  ```
  [s>[a>Cookie Monster [b>likes<a] cookies.<b]<s]
  ```
  Markup "a" overlaps with markup "b"

- self-overlapping markup
  ```
  [s>[a~1>Cookie Monster [a~2>likes<a~1] cookies.<a~2]<s]
  ```
  By adding identifiers to the markup tag (for both the opening and the closing tags).  
  The combination tag + identifier should be unique within the TAGML document.

- annotations on markup
  ```
  [poem type="limerick"
        author='John'
        year=1818
        rhymes=true
        keywords=["unfinished","censored"]>There once was a vicar from Slough...<poem]
  ```
  annotations can be added to the start tag of any markup  
  annotation values can be any of the following data types:
  - string: `"string"` or `'string'`
  - mixed content: `|mixed [b>content<b]|`
  - boolean: `true` or `false`
  - number: `3.14`
  - annotation: `{x=1 y=2}`
  
  
  


----------

[TexMECS] http://mlcd.blackmesatech.com/mlcd/2003/Papers/texmecs.html

[LMNL]    http://lmnl-markup.org/specs/archive/LMNL_syntax.xhtml

[FTANML]  https://www.balisage.net/Proceedings/vol10/html/Kay01/BalisageVol10-Kay01.html