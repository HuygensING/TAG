# 2.1 (November 2018)

### New features:

- [AlexandriaStep for use in Calabash](https://github.com/HuygensING/TAG/blob/master/TAGML/CALABASH-README.md)

#### [New commands for the command-line app](https://huygensing.github.io/alexandria-markup-server/)
- export-dot
- export-svg
- export-png
- export-xml 
- export-tagml
- import-tagml 

### Bugfixes:

- The first markup is now always the root markup for the default layer, even if new layers are defined on that markup.
- This means that this first markup tag must correspond with the last closing markup tag, and suspending/resuming of this markup is not allowed.

### Still to realize:

- Whitespace: "In TAGML Whitespace is insignificant unless specified otherwise"
- Data typing (RichText)
- Comments: store and export
- Namespaces: export
- Linking elements

# 2.0 (July 2018)

### TAGML Syntax elements realized in this release:

- Encoding text: escaping
- Adding markup
- Adding annotations
- Milestones
- Comments (parsing only)
- Namespaces (parsing only)
- Data typing (String, Number, Boolean, List, Nested annotation)
- Encoding non-linearity
- Overlapping and self-overlapping markup
- Discontinuity
