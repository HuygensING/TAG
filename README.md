# Text as Graph (TAG)

## What is it?

The Text As Graph (TAG) data model is a directed property hypergraph for modeling text, markup (roughly comparable to XML elements), and annotations (roughly comparable to what XML attributes would be like if they could contain markup, including attributes on attributes).

## News

### New release: Alexandria 2.4 (October 2020)

##### New features:
- The `export-xml` now has an option to choose a 'leading' layer. When a TAGML document has multiple layers, `export-xml` uses Trojan Horse Milestones on the layers. Previously it used these on *all* layers. In this version you can indicate a layer to define the XML hierarchy: markup in this _leading_ layer will be output as regular XML open/close tags, for all other layers the Trojan Horse Milestones will be used.
- When a view is active, changes to its definition file cannot be committed.
- `init` now also creates a sparql directory.

###### [New/Changed commands for the command-line app](https://huygensing.github.io/alexandria/commands)
- export-xml (new option: `--leadinginglayer` / `-l`)

### New release: [LSP-tagml](https://packagecontrol.io/packages/LSP-tagml) 1.0.1 (May 2020)

LSP-tagml is a plugin for Sublime Text 3 that installs the tagml-language-server.

### New release: tagml-language-server 0.1 (May 2020)

The tagml-language-server is an implementation of the Language Server Protocol for TAGML.
When linked with a compatible editor, it can be used to provide syntax help in the editor. 

### New release: Alexandria 2.3 (September 2019)

##### Bugfixes:
- It is now possible, when a view other than the default view is active, to commit new and changed view definitions, and new tagml source files.
  Committing changes to tagml source files of existing documents is still only possible when the default view is active.
- Running `alexandria init` in your home directory is not allowed and will fail with an error message.
- `alexandria status` will now only search one level deep in watched directories for files/directories that may be added.
- When committing a view definition which is valid json, but does not contain at least one of the required fields `includeLayers`, `excludeLayers`, `includeMarkup`, `excludeMarkup`, alexandria would silently accept this, producing an invalid view.
  This has been fixed: committing this view definition will not fail with an error message.    

### New release: Alexandria 2.2 (July 2019)

###### [New/Changed commands for the command-line app](https://huygensing.github.io/alexandria/commands)
- query

##### Bugfixes:

- After a revert, the reverted file is now no longer shown as modified.
- It is now possible to run alexandria commands from any directory, provided one of its parent directories has been initialized.

### New release: Alexandria 2.1 (December 2018)
                               
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

## TAG-related GitHub repositories

[TAG-related GitHub repositories](repositories)
