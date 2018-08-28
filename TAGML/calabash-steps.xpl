<p:library xmlns:p="http://www.w3.org/ns/xproc"
           xmlns:tag="https://huygensing.github.io/TAG/TAGML/ns/tag"
           xmlns:cx="http://xmlcalabash.com/ns/extensions"
           version="1.0">

  <p:declare-step type="tag:load">
    <p:output port="result"/>
    <p:option name="tagmlfile" required="true" cx:type="xsd:anyURI"/>
  </p:declare-step>

</p:library>