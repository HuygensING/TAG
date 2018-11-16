# Alexandria Command Line App

Wijzigingen om de app nog meer git-like te maken, en het gebruik van symbolische namen weg te nemen.
Views analoog aan git branches

## Alexandria subcommands


- `init`  
  Kan as-is blijven  

- `register-document`  
  `define-view`
   
   Vervangen door:

    * `add` {_file_|_filemask_...}  
      meld de bestanden aan aan het systeem. 
  
    * `commit` {_file_|_filemask_...} -a  
      voor `*.tag/*.tagml`: parsed de file, geeft foutmelding bij parse errors  
      voor `*.json`: parse als een view, basename-.json is de view name (Dus a.json wordt view a)  
      voor alle ge-add-e files: onthoud modification date.  

- `checkout` _viewname | -_
  
   Voorwaarden: 
  
    * de viewname is in het systeem bekend. (dus er is een viewdefinitie bestand dat ge-add en ge-commit is)
  
    * en alle bestanden die met `add` aangemeld zijn ook met `commit` in het systeem opgenomen zijn, en niet gewijzigd sinds de laatste `commit`

    * er is niet al een view uitgecheckt.

   Gebruikt de aangegeven viewdefinitie om alle aangemeldde tagml bronbestanden te vervangen door de view, dus alleen de delen van de tekst en markup zoals in de viewdefinitie aangegeven is.

   Maakt de aangegeven view _actief_.

   `checkout -` zet views uit/activeert de _main_ view, in de \*.tagml bestanden staat weer alle tekst en markup.

- `diff` _tagml_file_

  In de _main_ view toont het het verschil tussen de huidige versie van de aangegeven tagml_file en de tagml serialisatie van het TAG model zoals het in de lokale repository zit.
  Als er een view actief is, dan wordt het verschil getoond tussen de inhoud van het 

- `export-dot`  
  `export-png`  
  `export-svg`  
  `export-xml`  
  `export-tagml`  

  Bij deze commando's moet je aangeven welk document je wilt exporteren. Als we geen symbolische namen willen, dan moet de filename van het bronbestand als parameter meegegeven worden.

  De uitvoer is standaard naar _stdout_. Met `-o` _filename_ kan ook naar een opgegeven bestand geschreven worden.

  Als er een view actief is wordt deze gebruikt bij de export.


- `info`

  Toont:
  - in welke view we zitten.
  - welke views er zijn.
  - welke documenten er zijn.
  - welke bestanden veranderd zijn sinds de laatste commit.

- `import-tagml`

  De oude functionaliteit van dit commando wordt vervuld door het nieuwe `commit` commando

- `help` 

  Toon mogelijke commando's


### algemeen:
Alle commando's onder `init` geven een foutmelding als ze aangeroepen worden vanuit een directory die niet ge-`init` is.

Er is geen hierarchie in de views, zoals bij branches in git.
Je kunt per directory maar 1 view actief maken.
