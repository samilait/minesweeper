# Määrittelydokumentti

Työssä tullaan käyttämään ensisijaisesti moniulotteisia taulukkoja. Algoritmien valinta on tällä hetkellä vielä hieman avoin, mutta miinaharavan ratkaisussa käytetään todennäkäisesti ns. "raakaa voimaa", eli rekursioita ja toistolauseita, joilla tutkitaan taulukon tiettyä alkioaluetta.

Ongelma on ratkaista miinaharavapeli. Eli kun tiedossa on ruudukon alkion vieressä suoraan tai diagonaalisti olevien miinojen määrä, niin voidaan päätellä missä ruudussa miina sijaitsee. Aina tämä ei kuitenkaan ole mahdollista, vaan joudutaan tekemään arvaus. Lähteiden perusteellä kyseessä on ns. NP-täydellinen ongelma.

Aikavaativuus NP=P ongelmalle on polynominen. Tilavaativuus riippuu suoraan ruudukon koosta.

Ohjelma generoi määritellyn satunnaisen miinaharavapelin pelin aluksi. Peli aloitetaan tekemällä muutama satunnainen ruudun avaus. Mikäli näillä satunnaisilla avauksilla ei osuta miinaan, niin aletaan käymään kenttää läpi aloittaen kohdasta, jossa voidaan ilman arvausta päätellä miinan sijainti ja toistetaan, kunnes kaikki miinat ovat löytyneet. Käyttäjän ei tarvitse syöttää muuta kuin ruudukon koko.

## Lähteet
[P=NP?](http://www.claymath.org/sites/default/files/minesweeper.pdf)

