# Viikkoraportti 3

Tällä viikolla olen korjannut avustajan. Aikaisemmin avustaja "huijasi" käyttämällä laudassa olevaa tietoa miinojen sijainnista ilman, että ruutuja oli avattu. Nyt avustaja käyttää aitoa päättelylogiikkaa miinojen sijainnista.

Toteutettu logiikka tällä hetkellä on ns. one-to-one päättely, jossa ruudun lähellä olevien miinojen lukumäärän ollessa yhtäsuuri kuin avaamattomien ruutujen päätellään kyseessä olevan miina (punainen ruutu). Kun miinojen sijainti on päätelty voidaan päätellä miinattomat avaamattomat ruudut (vihreä ruutu).

Seuraavaksi avustajaan tehtyä logiikkaa käytetään automaattisessa ratkaisijassa iteroimalla niin kauan, kunnes one-to-one menetelmällä ei voida enää päätellä miinojen sijaintia, tai kunnes peli voitetaan. One-to-one logiikka ei toimi aina, joten logiikkaa laajennetaan seuraavaksi ottamaan huomioon monimutkaisemmat tilanteet. 

Koodi pitää sisällään turhaa toistoa ja liian pitkiä metodeja, joten siistiminen on myös paikallaan.

Aikaa on kulunut noin 8h.
