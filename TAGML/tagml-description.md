# TAGML description

## A syntax for the TAG model.

TAGML (Text-As-Graph Markup Language)

TAG (CAPS) stands for the model, **T**ext **a**s **G**raph

A tag (lowercase) is the entity used to indicate the markup boundaries.

A TAGML document consist of Unicode characters and adheres to the syntax defined in this description. 

### Textual phenomena and how to code them in TAGML

#### Markup tags
  ```
  [line>The rain in Spain falls mainly on the plain.<line]
  ```
  
  For every open tag `[markup>]` there should be a corresponding close tag `<markup]`  
  markup has a *name*, an optional *identifier*, and (for the open tag) one or more *annotations*.
  ```
  [markup~1 annotation_1='string value' annotation_2=2.718>text<markup~1]
  ```
  `markup` is the name,  
  `1` is the identifier,  
  `annotation_1` and `annotation_2` are the names of the annotations

#### Overlapping markup  
  Unlike in XML, markup can *overlap* in TAGML:    
  ```
  [s>[a>Cookie Monster [b>likes<a] cookies.<b]<s]
  ```
  Markup "a" overlaps with markup "b", the "b" markup starts before the "a" markup ends, but that doesn't mean it also has to end before "a". 

#### Self-overlapping markup
  ```
  [s>[a~1>Cookie Monster [a~2>likes<a~1] cookies.<a~2]<s]
  ```
  Markup of the same name can overlap by adding identifiers to the markup name (for both the opening and the closing tags).  
  The combination name + identifier should be unique within the TAGML document.

#### Markup annotations
  ```
  [poem type="limerick"
        author='John'
        year=1818
        rhymes=true
        keywords=["unfinished","censored"]>There once was a vicar from Slough...<poem]
  ```
  Annotations can be added to the start tag of any markup.  
  Annotation values can be any of the following data types:
  - string: `"string"` or `'string'`
  - mixed content: `|mixed [b>content<b]|`
  - boolean: `true` or `false`
  - number: `3.14`
  - (nested) annotation: `{x=1 y=2}`
  - list of these data types: `['Hughie', 'Louis', 'Dewey']`
  
  By using an annotation data type as value for an annotation, you can have hierarchical annotations:
  ```
  [origin
    location={
      position={x=1, y=2}
      countrycode='nl'
    }>Amsterdam<origin]
  ```

#### Whitespace significance  
  Whitespace is only significant within string or mixed content annotation values, or inside markup that's been defined in the schema as containing mixed content.  
  In all other places, whitespace is insignificant and can be used for formatting the TAGML.

#### Dominance / Containment  
  > When you’re talking about overlapping structures, it’s useful to make the distinction between structures that *contain* each other and structures that *dominate* each other. Containment is a happenstance relationship between ranges while dominance is one that has a meaningful semantic. A page may happen to *contain* a stanza, but a poem *dominates* the stanzas that it contains.
  
   Jeni Tennison, ["Overlap, Containment and Dominance"](http://www.jenitennison.com/2008/12/06/overlap-containment-and-dominance.html)
  
  The overlapping hierarchies are defined in the schema. If markup "b" is contained by markup "a", and both are in the same hierarchy, then "a" dominates "b"

#### Discontinuity
  ```
  ... [q>and what is the use of a book,<-q]
  thought Alice
  [+q>without pictures or conversation?<q]
  ```
  In this text, the fact that the two sets of "q" tags define one discontinued quote is indicated by suspend/resume indicators before the markup name: a `-` in the first closing tag, and a `+` in the following opening tag. 
  
## TAGML Schema

Markup can either structural, meaning it only contains other markup,  
or non-structural (TODO: betere term verzinnen), meaning it can contain mixed content: both text and markup.

----------
( TAGML is inspired by: )

[TexMECS] http://mlcd.blackmesatech.com/mlcd/2003/Papers/texmecs.html

[LMNL]    http://lmnl-markup.org/specs/archive/LMNL_syntax.xhtml

[FTANML]  https://www.balisage.net/Proceedings/vol10/html/Kay01/BalisageVol10-Kay01.html