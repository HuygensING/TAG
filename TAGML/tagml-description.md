# TAGML description

## A syntax for the TAG model.

TAGML (Text-As-Graph Markup Language)

TAG (CAPS) stands for the model, **T**ext **a**s **G**raph

A tag (lowercase) is the entity used to indicate the markup boundaries.

### Markup tags
  ```
  [line>The rain in Spain falls mainly on the plain.<line]
  ```
  
  For every open tag `[markup>` there should be a corresponding close tag `<markup]`  
  markup has a *name*, an optional *identifier*, and (for the open tag) one or more *annotations*.
  ```
  [markup~1 annotation_1='string value' annotation_2=2.718>text<markup~1]
  ```
  `markup` is the name,  
  `1` is the identifier,  
  `annotation_1` and `annotation_2` are the names of the annotations

### Overlapping markup  
  Unlike in XML, markup can *overlap* in TAGML:    
  ```
  [s>[a>Cookie Monster [b>likes<a] cookies.<b]<s]
  ```
  Markup "a" overlaps with markup "b", the "b" markup starts before the "a" markup ends, but that doesn't mean it also has to end before "a". 

### Self-overlapping markup
  ```
  [s>[a~1>Cookie Monster [a~2>likes<a~1] cookies.<a~2]<s]
  ```
  Markup of the same name can overlap by adding identifiers to the markup name (for both the opening and the closing tags).  
  The combination name + identifier should be unique within the TAGML document.

### Markup annotations
  ```
  [poem type="limerick"
        author='John'
        year=1818
        rhymes=true
        keywords=["unfinished","censored"]>There once was a vicar from Slough...<poem]
  ```
  Annotations can be added to the start tag of any markup.  
  Annotation values can be any of the following data types:
  - string: `"string"` or `'string'` (bracketed by `"` or `'`)
  - mixed content: `|mixed [b>content<b]|` (bracketed by `|`)
  - boolean: `true` or `false` (not bracketed)
  - number: `3.14` (not bracketed)
  - (nested) annotation: `{x=1 y=2}` (bracketed by `{` and `}`)
  - list of these data types: `['Hughie', 'Louis', 'Dewey']` (bracketed by `[` and `]`)
  
  By using an annotation data type as value for an annotation, you can have hierarchical annotations:
  ```
  [origin
    location={
      position={x=1 y=2}
      countrycode='nl'
    }>Amsterdam<origin]
  ```

 	
### Dominance / Containment  
  > When you’re talking about overlapping structures, it’s useful to make the distinction between structures that *contain* each other and structures that *dominate* each other. Containment is a happenstance relationship between ranges while dominance is one that has a meaningful semantic. A page may happen to *contain* a stanza, but a poem *dominates* the stanzas that it contains.
  
   Jeni Tennison, ["Overlap, Containment and Dominance"](http://www.jenitennison.com/2008/12/06/overlap-containment-and-dominance.html)
  
  The overlapping hierarchies are defined in the schema. If markup "b" is contained by markup "a", and both are in the same hierarchy, then "a" dominates "b"

### Discontinuity
  ```
  ... [q>and what is the use of a book,<-q]
  thought Alice
  [+q>without pictures or conversation?<q]
  ```
  In this text, the fact that the two sets of "q" tags define one discontinued quote is indicated by suspend/resume
  indicators before the markup name: a `-` in the first closing tag, and a `+` in the following opening tag.
  
### Non-linearity
  In general, the text of a TAGML document is to be read in the order in which it has been transcribed.
  In some cases, there may be different paths through the text,
  for example when an addition/deletion pair has been encoded:
  ```
  [q>To be, or [del>to be not<del][add>not to be<add].<q] 
  ``` 
  To indicate that the `del` and `add` markup pair is where the text diverges, with the `del` part one path,
  and the `add` part the other, we group these markups by enclosing them in `set` tags `|>` and `<|`,
  with `|` to separate the diverging markups:
  ```
  [q>To be, or |>[del>to be not<del]|[add>not to be<add]<|!<q] 
  ```
  In case of a solitary `del` without a corresponding `add`, mark the markup as *optional* to indicate there are two paths:
  one *with* the text marked up by `del`, and one *without* (grouping is not necessary in this case):  
  ```
  [q>To be, or [?del>perchance<?del] not to be?<q] 
  ```
  The alternatives in the group don't need to be contained in markup, this is also valid TAGML:
  ```
  [l>At this point, the new text |>diverged|differed<| from the original.<l] 
  ```
  
  
### Milestones / placeholders / empty markup
  ```
  [img src='http://example.com/img.png']
  ```
  
### Namespaces
  ```
  [!ns p http://tag.com/poetry]
  [p:poem>Roses are red, .....<p:poem]
  ```
  Should be at the top of the document.
 
### Comments
  ```
  [l>text text text<l]
  [! comment !]
  [l>text text text<l]
  ```

### Whitespace significance  
  Whitespace is only significant within string or mixed content annotation values, or inside markup that's been defined
  in the schema as containing mixed content.  
  In all other places, whitespace is insignificant and can be used for formatting the TAGML.
  
  ```
  NOTE: There is a function in the XPath standard called normalize-whitespace that describes how to deal with whitespace.
   It seems like a good idea to follow these rules. Or at least get inspired by it.
    https://www.w3.org/TR/xpath/#function-normalize-space
  
  NOTE: There is an xml:space attribute that tries to define how the XML processor should deal with whitespace.
   As far we know almost nobody uses this. 
  ```

### Escaping
A TAGML document consist of Unicode characters and adheres to the syntax defined in this description.

In text, the following characters need to be escaped using the escape character `\ ` :
```
[ -> \[
] -> \]
< -> \<
> -> \>
| -> \|
\ -> \\
```

Inside annotation string values: `'` -> `\'`, `"` -> `\"`

## Examples

### Genesis
Original in LMNL ( from http://xml.coverpages.org/LMNL-Abstract.html )
```
[book [title [lang}en{lang]}Genesis{title]}
[chapter}
[section [title}The creation of the world.{title]}
[para}
[v}[s}[note}In the beginning of creation, when God made heaven and
earth,{note [alt}In the beginning God created heaven and
earth.{alt]]{v] [v}the earth was without form and void, with darkness
over the face of the abyss, [note}and a mighty wind that swept{note [alt}and
the spirit of God hovering{alt]] over the surface of the waters.{s]{v]
[v}[s}God said, [quote}[s}Let there be a light{s]{quote], and there
was light;{v] [v}and God saw that the light was good, and he separated
the light from darkness.{s]{v] [v}[s}He called the light day, and the
darkness night. So evening came, and morning came, the first
day.{s]{v]
{para]
...{chapter]...{section]...{book]
```

in TAGML:
```
[book title={lang='en' content='Genesis'}>
[chapter>
[section title="The creation of the world.">
[para>
[v>[s>
|>[original>In the beginning of creation, when God made heaven and earth,<original]
[alt>In the beginning God created heaven and earth.<alt]<|
<v] [v>the earth was without form and void, with darkness
over the face of the abyss, |>[original>and a mighty wind that swept<original][alt>and
the spirit of God hovering<alt]<| over the surface of the waters.<s]<v]
[v>[s>God said, [quote>[s>Let there be a light<s]<quote], and there
was light;<v] [v>and God saw that the light was good, and he separated
the light from darkness.<s]<v] [v>[s>He called the light day, and the
darkness night. So evening came, and morning came, the first
day.<s]<v]
<para]
...<chapter]...<section]...<book]
```

The nested `title` annotation had to have an extra annotation `content` added because of the difference in annotation recursion encoding betwen LMNL and TAGML.

The non-linearity in the text that in LMNL is encoded with a `note` markup with `alt` annotation
is encoded in TAGML as a group with `original` and `alt` markup.

## TAGML Grammar
    
   
## TAGML Schema

The schema should:
- define hierarchies
- define annotations for markup (value data type, required?)
- define which markup can contain mixed content
  (markup can be either structural, meaning it only contains other markup,  
  or non-structural (TODO: betere term verzinnen), meaning it can contain mixed content: both text and markup.
  )

----------

( TAGML is inspired by: )

[TexMECS] (http://mlcd.blackmesatech.com/mlcd/2003/Papers/texmecs.html)

[LMNL]    (http://lmnl-markup.org/specs/archive/LMNL_syntax.xhtml)

[FTANML]  (https://www.balisage.net/Proceedings/vol10/html/Kay01/BalisageVol10-Kay01.html)
