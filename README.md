# Text as Graph (TAG)

## What is it?

The Text As Graph (TAG) data model is a directed property hypergraph for modeling text, markup (roughly comparable to XML elements), and annotations (roughly comparable to what XML attributes would be like if they could contain markup, including attributes on attributes).

## News

### New release: Alexandria 2.1(December 2018)
                               
##### New features:

- [AlexandriaStep for use in Calabash](https://huygensing.github.io/TAG/TAGML/CALABASH-README)
- [TAGML Syntax highlighting in Sublime Text 3](https://huygensing.github.io/tagml-sublime-syntax/)

###### [New/Changed commands for the command-line app](https://huygensing.github.io/alexandria/commands)
- about
- add
- commit
- export-dot
- export-svg
- export-png
- export-xml 

##### Bugfixes:

- The first markup is now always the root markup for the default layer, even if new layers are defined on that markup.
- This means that this first markup tag must correspond with the last closing markup tag, and suspending/resuming of this markup is not allowed.


### Upcoming presentation

Haentjens Dekker, Ronald. Invited talk at the Workshop on Scholarly Digital Editions, Graph Data-Models and Semantic Web Technologies, Université de Lausanne, 3 June 2019.

## Implementations

### Alexandria
[**Alexandria**](https://huygensing.github.io/alexandria/) is a prototype implementation of TAG. 

* [Alexandria command line app](https://github.com/HuygensING/alexandria#alexandria-command-line-app)
* [Alexandria command line app source code](https://github.com/HuygensING/alexandria)

### TAGML
[**TAGML**](https://github.com/HuygensING/TAG/tree/master/TAGML) (Text-As-Graph Markup Language) is a syntax for the TAG model.

### HyperCollate
**HyperCollate** is a prototype collation engine that is able to handle intradocumentary variation (i.e. variation within one document), in addition to finding the differences between witnesses. This advanced form of collation is possible because HyperCollate looks not only at the text of a document, but also at its markup.

* [HyperCollate documentation and source code](https://huygensing.github.io/hyper-collate/)

## Presentations and Publications

* Bleeker, Elli. 2018. “Adressing Ancient Promises: Text Modeling and _Alexandria_”. Invited talk at the DH-Kolloquium of the Berlin Brandenburgische Akademie der Wissenschaften, 2 November 2018. Slides [here](https://edoc.bbaw.de/frontdoor/index/index/searchtype/latest/docId/2932/).
 
* Haentjens Dekker, Ronald, Elli Bleeker, Bram Buitendijk, Astrid Kulsdom and David J. Birnbaum. 2018. “TAGML: A markup language of many dimensions.” Presented at Balisage: The Markup Conference 2018, Washington, DC, July 31 - August 3, 2018. In _Proceedings of Balisage: The Markup Conference 2018. Balisage Series on Markup Technologies_, vol. 21 (2018).   
doi: `https://doi.org/10.4242 BalisageVol21.HaentjensDekker01.`
	* Paper: <http://www.balisage.net/Proceedings/vol21/html/HaentjensDekker01/BalisageVol21-HaentjensDekker01.html>
	* Slides: <https://docs.google.com/presentation/d/1TpOtNJR_3FSKfMSzUvI4wuJBBbZcchVGFjG2qP4csck/edit?usp=sharing>

* Bleeker, Elli, Bram Buitendijk, Ronald Haentjens Dekker, and Astrid Kulsdom. 2018. "Perspectives on Text. Synthesising Textual Knowledge". Presented at the international [Computational Methods for Literary-Historical Textual Scholarship](http://cts.dmu.ac.uk/events/CMLHTS/) conference at De Montfort University, July 4th 2018.

* Bleeker, Elli, Bram Buitendijk, Ronald Haentjens Dekker, and Astrid Kulsdom. 2018. “Including XML markup in the automated collation of literary text.” Presented at XML Prague 2018, Prague, February 8–10, 2018. In _XML Prague 2018. Conference proceedings._ Prague: University of Economics, 77–95.
	* Paper: <http://archive.xmlprague.cz/2018/files/xmlprague-2018-proceedings.pdf#page=89>.
	* Slides: <http://archive.xmlprague.cz/2018/files/presentations/BleekerBuitendijkHaentjensDekkerKulsdom_XML_Prague_2018.pdf>
	* Video: <https://www.youtube.com/watch?v=WudSN3mGsGk>

* Haentjens Dekker, Ronald, and David J. Birnbaum. 2017. “It’s more than just overlap: Text As Graph.” Presented at Balisage: The Markup Conference 2017, Washington, DC, August 1-4, 2017. In _Proceedings of Balisage: The Markup Conference 2017_. Balisage Series on Markup Technologies, vol. 19 (2017). doi:`10.4242/BalisageVol19.Dekker01`.
	* Paper: <https://www.balisage.net/Proceedings/vol19/html/Dekker01/BalisageVol19-Dekker01.html>
	* Visualization: [“Hunting of the Snark”](snark-fly.mp4) (mp4, 20G)

