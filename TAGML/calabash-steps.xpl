<p:library xmlns:p="http://www.w3.org/ns/xproc"
           xmlns:tag="https://huygensing.github.io/TAG/TAGML/ns/tag"
           version="1.0">

  <p:declare-step type="tag:load">
    <p:output port="result"/>
    <p:option name="tagmlfile" required="true"/>
  </p:declare-step>

</p:library>