// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: http://codemirror.net/LICENSE

(function(mod) {
  if (typeof exports == "object" && typeof module == "object") // CommonJS
    mod(require("../../lib/codemirror"));
  else if (typeof define == "function" && define.amd) // AMD
    define(["../../lib/codemirror"], mod);
  else // Plain browser env
    mod(CodeMirror);
})(function(CodeMirror) {
"use strict";

// var aaa_grammar = {
//   // prefix ID for regular expressions, represented as strings, used in the grammar
//   "RegExpID"                          : "RE::",

//   // Style model
//   "Style"                             : {
//     "atom"                         : "atom",
//     "attribute"                    : "attribute",
//     "bracket"                      : "bracket",
//     "builtin"                      : "builtin",
//     "comment"                      : "comment",
//     "def"                          : "def",
//     "error"                        : "error",
//     "hr"                           : "hr",
//     "keyword"                      : "keyword",
//     "link"                         : "link",
//     "meta"                         : "meta",
//     "number"                       : "number",
//     "operator"                     : "operator",
//     "property"                     : "property",
//     "punctuation"                  : "punctuation",
//     "qualifier"                    : "qualifier",
//     "string"                       : "string",
//     "string-2"                     : "string-2",
//     "tag"                          : "tag",
//     "variable"                     : "variable",
//     "variable-2"                   : "variable-2",
//     "variable-3"                   : "variable-3"
//   },

//   // Lexical model
//   "Lex"                               : {
//     "comment:comment"              : ["[!", "!]"],
//     "atom"                         : "!atom",
//     "attribute"                    : "!attribute",
//     "bracket"                      : "!bracket",
//     "builtin"                      : "!builtin",
//     "def"                          : "!def",
//     "error"                        : "!error",
//     "hr"                           : "!hr",
//     "keyword"                      : "!keyword",
//     "link"                         : "!link",
//     "meta"                         : "!meta",
//     "number"                       : "!number",
//     "operator"                     : "!operator",
//     "property"                     : "!property",
//     "punctuation"                  : "!punctuation",
//     "qualifier"                    : "!qualifier",
//     "string"                       : "!string",
//     "string-2"                     : "!string-2",
//     "tag"                          : "!tag",
//     "variable"                     : "!variable",
//     "variable-2"                   : "!variable-2",
//     "variable-3"                   : "!variable-3",
//     "text"                         : "RE::/[^<&]+/"
//   },

//   // Syntax model (optional)
//   "Syntax"                         : {
//     "aaa"                          : "( atom | attribute | bracket | builtin | comment | def | error | hr | keyword | link | meta | number | operator | property | punctuation | qualifier | string | string-2 | tag | variable | variable-2 | variable-3 | text)*"
//   },

//   // what to parse and in what order
//   "Parser"                            : [ ["aaa"] ]

// };

// var aaa_mode = CodeMirror.getMode(aaa_grammar);

// https://codemirror.net/demo/simplemode.html
CodeMirror.defineSimpleMode("aaa", {
  start: [
    {regex: /@/, token: "qualifier", next: "keywordstate"},
    {regex: /\[!/, token: "comment", next: "comment"},
  ],

  comment: [
    {regex: /.*?!]/, token: "comment", next: "start"},
    {regex: /.*/, token: "comment"}
  ],

  keywordstate: [
    {regex: /atom/, token: "atom", next: "start"},
    {regex: /attribute/, token: "attribute", next: "start"},
    {regex: /bracket/, token: "bracket", next: "start"},
    {regex: /builtin/, token: "builtin", next: "start"}
  ]
});

CodeMirror.defineMIME("text/aaa", "aaa");

});
