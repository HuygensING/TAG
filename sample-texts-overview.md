# Voorbeeldteksten voor queries (in random order):

## 1. "Krapp's Last Tape" (Samuel Beckett)

- **Soort tekst**: theater/toneel
- **Codering van materiaal**: TEI/XML transcripties; genetisch/diplomatisch met een focus op de materiële en fysieke aspecten van het materiaal
- **Korte omschrijving**: discontinuïteit; overlap; meerdere hierarchiëen; containment/dominance; verschillend narratologische en temporele perspectieven, met name een dubbele hoofdpersoon (i.e. de hoofdpersoon Krapp luistert opnames van hemzelf terug en geeft commentaar op zijn eigen stem), maar ook regieaanwijzingen van Beckett
- **Bron**: www.beckettarchive.org, zie bijv. www.beckettarchive.org/krapp/MS-HRC-SB-5-4/
- **Technische documentatie**: http://uahost.uantwerpen.be/bdmp/ en http://www.beckettarchive.org/editorial.jsp 
- **Eigen schema**: ja, een ODD met RelaxNG output (zie http://uahost.uantwerpen.be/bdmp/index.php/the-tags/4-validation/ om het schema te downloaden)

## 2. "Leaves of Grass" (Walt Whitman)

- **Soort tekst**: poëzie, proza
- **Codering van materiaal**: TEI/XML transcripties, naar eigen zeggen een "non-controversial and structural" tagging (titles, divs, lines, segments)
- **Korte omschrijving**: verzameling gedichten over de Amerikaanse burgeroorlog waarin de topografische kenmerken van de tekst een belangrijke rol spelen; historische achtergronden bij teksten beschikbaar in Whitman Archive (mogelijkheden tot annotatielagen); mixed genre (poëzie en proza, zie "Ashes of Roses" http://whitmanarchive.org/manuscripts/transcriptions/loc.00050.html); hierarchiëen; overlap, containment/dominance; fysieke aspecten van de teksten, zoals gebruik van inkt en verschillende kleuren potlood, papiersoorten, etc.; betekenisvolle inspringing en witregels in transcripties
- **Bron**: whitmanarchive.org, zie bijvoorbeeld de fly leaf 005r van de 1860-1861 editie, http://whitmanarchive.org/published/LG/1860/images/leaf005r.html
- **Technische documentatie**: een wiki, zie http://whitmanarchive.org/mediawiki/index.php/Whitman_Encoding_Guidelines
- **Eigen schema**: ja, een DTD (downloadbaar op http://whitmanarchive.org/about/index.html)

## 3. "Battle Pieces" (Herman Melville)
Soort tekst: 
Codering van materiaal:
Korte omschrijving:
Bron:
Technische documentatie:
Eigen schema:

## 4. "Prometheus Unbound" (Percy Shelley)

- **Soort tekst**: lyrisch drama, drie notitieboeken in handschrift van Percy Shelley
Codering van materiaal: TEI/XML transcripties, zelfde oriëntatie als Frankenstein (zie hieronder): line-by-line, inclusief doorhalingen en toevoegingen, volgens de tekstgenetische codering voorgesteld door de TEI (<surface>s en <zone>s; één manuscript pagina is één <surface>) 
- **Korte omschrijving**: in het digitaal archief kun je het werk vanuit twee perspectieven bestuderen: lineair volgens het gepubliceerde boek, van Akte 1 tot Akte 4, of in de fysieke volgorde van de drie notitieboeken (Bodleian Manuscripts Shelley e.1, e.2, and e.3). Deze cross references zijn gecodeerd met met <anchor> elementen
- **Bron**: http://shelleygodwinarchive.org/contents/prometheus_unbound/
- **Technische documentatie**: http://shelleygodwinarchive.org/about/#technologicalinfrastructure
- **Eigen schema**: Ja, RelaxNG met een XMLSchema datatype, zie https://github.com/umd-mith/sga/blob/master/data/schemata/shelley_godwin_odd.rng

## 5. "Frankenstein" (Mary Godwin Shelley )

- **Soort tekst**: fictionele dagboekbrieven
- **Codering van materiaal**: TEI/XML transcripties, line-by-line en diplomatisch, maar met codering van doorhalingen en toevoegingen
- **Korte omschrijving**: materiële aspecten potentieel interessant; Frankenstein is een tweedelige roman gebaseerd op twee notitieboeken (A en B), die nu in ongebonden staat worden bewaard als MS c.56 en MS c.57. Deze houden echter een andere indeling aan dan de notitieboeken A en B. De gepubliceerde versie van de roman bestaan uit drie delen. Uit een discussie tussen Elisa en Wendell (overheard op Balisage) blijkt dat er verschillende opvattingen zijn over de indeling van de manuscripten en de daaruit voortvloeiende indeling van de roman (was het werk nu bedoeld als twee- of driedelige roman?), dus: meerdere structuren/perspectieven op de tekst; hierarchiëen; overlap; containment/dominance; maar ook twee verschillende auteurs (e.g. je kunt filteren op handschrift van Mary Shelley en op handschrift van Percy Shelley)
- **Bron**: http://shelleygodwinarchive.org/contents/frankenstein/ en https://www.rc.umd.edu/editions/frankenstein
- **Technische documentatie**: http://shelleygodwinarchive.org/about/#technologicalinfrastructure
- **Eigen schema**: Ja, RelaxNG volgens een XMLSchema datatype, zie https://github.com/umd-mith/sga/blob/master/data/schemata/shelley_godwin_odd.rng

## 6. "Lady Susan" (Jane Austen)

- **Soort tekst**: fictionele briefroman; manuscript
- **Codering van materiaal**: TEI/XML transcripties, "diplomatische orientatie" d.w.z. gecodeerd met een focus op het materiaal, de fysieke staat van de manuscripten
- **Korte omschrijving**: als gevolg van de focus op de fysieke structuur van de tekst maakt het project veel gebruik van milestones, cross-referencing attributes, etc. Overlap; multiple hierarchies; verschillende tekststructuren 
- **Bron**: http://www.janeausten.ac.uk/manuscripts/lady_susan/Front_(left)_board.html
- **Technische documentatie**: http://www.janeausten.ac.uk/redist/JA_encoding_model.pdf
- **Eigen schema**: Ja, RelaxNG, http://www.janeausten.ac.uk/redist/tei_austen.xml

## 7. "Opstand der voetnota's" (Raymond Brulez)

- **Soort tekst**: manuscript/typoscript; proza met belangrijke typografische elementen
- **Codering van materiaal**: TEI/XML transcripties volgens de richtlijnen van de Beckett Archive, dus genetisch/diplomatisch met een focus op de materiële en fysieke aspecten van het materiaal
- **Korte omschrijving**: korte prozatekst over een drukkerij waarin 's nachts de kleine letters, die altijd worden gebruikt voor voetnoten, in opstand komen. De tekst bevat twee pagina's van een fictief werk, vóór de opstand en na de opstand. Brulez heeft zijn best gedaan om alle letters terug te laten komen in de tekst. Zie voor een introductie ook https://brulez.uantwerpen.be/de-opstand-der-voetnota-s#/intro. 
- **Kenmerken van de tekst**: overlap; discontinuïteit; meerdere hierarchiëen; belangrijke typografische elementen
- **Bron**: (editie nog niet online; transcripties op Elli's harde schijf)
- **Technische documentatie**: transcripties gemaakt volgens richtlijnen Beckett Archive http://uahost.uantwerpen.be/bdmp/ en http://www.beckettarchive.org/editorial.jsp
- **Eigen schema**: ODD van Beckett Archive

## 8 An XML annotation schema for speech, thought, and writing representation
Dit paper presenteert een XML schema voor het annoteren van een high level narratologische categorie: speech, thought and writing representation (ST&WR). Ze volgen geen TEI, omdat hun eisen buiten de TEI voorzieningen vallen. Zie http://dharchive.org/paper/DH2014/Paper-374.xml