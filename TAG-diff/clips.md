text tokens need to be synced with their text nodes  
markup tokens need to be synced with their markup nodes.

## examples
###Split 1

*Witness 1*

`<s>De ongewisheid, voor de toestemming.</s>`

*Witness 2*

`<s>De ongewisheid!</s> <s>Voor de toestemming.</s>`

*Input*:  
Witness 1: `[<s>], [De], [ongewisheid], [,], [voor], [de], [toestemming], [.], [</s>]`  
Witness 2: `[<s>], [De], [ongewisheid], [!], [</s>], [<s>], [Voor], [de], [toestemming], [.], [</s>]`  

*Output* (= two lists of edit operations):  
List of text edits: `{"replaced" [,][!]}, {"replaced" [voor][Voor]}`  
List of markup edits: `{"split" [<s></s>][<s></s><s></s>]}`


The model *m<sub>1</sub>* of witness 1 consists of
 - 1 text node *t<sub>1</sub>* containing (De ... toestemming.)
 - 1 markup node *m<sub>1</sub>* "s" linking to the text node

The model *m<sub>2</sub>* of witness 2 consists of
 - 2 text nodes, *t<sub>1</sub>*:(De ongewisheid!) and *t<sub>2</sub>*:(Voor de toestemming.),
 - 2 markup nodes "s" (*m<sub>1</sub>*,*m<sub>2</sub>*), one linking to *t<sub>1</sub>*, the other to *t<sub>2</sub>*

to get from the *m<sub>1</sub>* to *m<sub>2</sub>*, one sequence of graph operations would be:
- create a new textnode *t<sub>2</sub>* containing "Voor de toestemming." (*t<sub>2</sub>* is based on *t<sub>1</sub>*)
- for all markup nodes *m* linked to *t<sub>1</sub>* (except *m<sub>1</sub>*), link *m* to *t<sub>2</sub>*
- create a new markup node *m<sub>2</sub>* "s" ("after" *m<sub>1</sub>*: for every parent node of *m<sub>1</sub>*, *m<sub>2</sub>* should be inserted in its child node list directly after *m<sub>1</sub>*)
- for all parent markup nodes *m* of *m<sub>1</sub>*, make *m* parent markup of *m<sub>2</sub>*, too.
- link *m<sub>2</sub>* to *t<sub>2</sub>*
- change the content of *t<sub>1</sub>* to "De ongewisheid!"


###Split 2
Witness H  

`<div type="p"><s>Washing away in water, what is one night?</s></div>`  

Witness TS  

`<div type="chapter"><s>Washing away in water.</s></div><div type="chapter"><s>What is one night?</s></div>`

Output (= two lists of edit operations):  
List of text edits: `{"replace" [,][.]}{"replace" [what][What]}{"replace" [?][.]}`  
List of markup edits: `{"split" [<div></div>][div></div><div></div]} {"split" [<s></s>][<s></s><s></s>]}`  
List of attribute edits: `{"attribute replaced" [<div type="p">][<div type="chapter">]}`  

###Join:
Witness 3

`<s>De nerveuze verwachting.</s> <s>Voor de instemming.</s>`

Witness 4

`<s>De nerveuze verwachting, voor de instemming.</s>`

Output (= two lists of edit operations):  
List of text edits: `{"replace" [.][,]}{"replace" [Voor][voor]}`  
List of markup edits: `{"join" [<s></s><s></s>][<s></s>]}`