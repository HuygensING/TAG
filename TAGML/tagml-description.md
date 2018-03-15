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
  [poem (type/limerick/type)><poem]
```
  annotations can be added to the start tag of any markup  
  the annotation is bracketed by (x/  and /x) elements
  






----
[TexMECS] http://mlcd.blackmesatech.com/mlcd/2003/Papers/texmecs.html

[LMNL]    http://lmnl-markup.org/specs/archive/LMNL_syntax.xhtml

[FTANML]  https://www.balisage.net/Proceedings/vol10/html/Kay01/BalisageVol10-Kay01.html