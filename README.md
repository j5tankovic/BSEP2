# Security testing
### Projekat iz predmeta BSEP

Projekat se sastoji iz četiri dela:
  - GrootApp (web servis)
  - GrootClient-protected (klijent sa zaštićenim rutama i ui elementima)
  - GrootClient-unprotected (klijent sa nezaštićenim rutama i ui elementima)
  - FeatureParser (generiše feature fajlove)
  
Cilj je na da se na osnovu ulaznog fajla, gde su definisana prava pristupa korisnicima određenih uloga, testira aplikacija.
Primer ulaznog fajla se nalazi na putanji *FeatureParser/example/accees_rules.csv*

Za testiranje se koristi alat [BDD-Security](http://www.continuumsecurity.net/bdd-intro.html).<br/>

Na osnovu datog ulaznog fajla, generišu se feature fajlovi koji opisuju korake testiranja.<br/>
Za potrebe ovog projekta testiraju se i web servisi i klijent.<br/>
Testovi web servisa podrazumevaju da odgovori koji su vraćeni budu **200 OK** ili **403 Forbidden**. GrootClient-unprotected je zato namerno napravljen klijent tako da bilo koji korisnik može izvršiti bilo koji akciju. <br/>

Testovi za klijenta podrazumevaju da ruta bude */unatuhorized* ili da konkretan ui element nije vidljiv ukoliko korisnik nema dozvolu za neku akciju. <br/>
I obrnuto važi...korisniku je sve dostupno i vidljivo ukoliko ima za to dozvolu.<br/>

### Pokretanje:
  
#### GrootApp
  
Mora postojati lokalna MySQL baza podataka sa korisnikom *root* koji ima šifru *root*, i u njoj šema **grootedu**
```SQL
CREATE SCHEMA `parliament` DEFAULT CHARACTER SET utf8 ;
 ```
 U Intellij-u napraviti Spring Boot konfiguraciju i pokrenuti.
 
 #### GrootClient
 
 Pre pokretanja potrebno je uraditi:
 ```
 bower install
 ```
 Za **GrootClient-protected** pokrenuti server na portu 8082:
 ```
 http-server -p 8082
 ```
 Za **GrootClient-unprotected** pokrenuti server na portu 8081:
 ```
 http-server -p 8081
 ```
 
 #### FeatureParser
 Pokrenuti sa komandom:
 ```python
 python rules_to_scenarios.py putanja_do_ulaznog_fajla
 ```
 ###### Izgenerisani fajlovi su feature fajlovi koji se smeštaju u projekat bdd-security (*src/test/resources/features/*)
 
 #### BDD-Security
 
 Pre pokretanja testova uraditi:
 ```gradle
 gradle clean
 ```
 
 Testiranje pokrenuti sa komandom:
 ```gradle
 gradle -Dcucumber.options="--tags @access_control_service,@access_control_client"
 ```
 U međuvremenu skuvati kafu (mozda i odgledati epizodu Prijatelja ili preslušati [GoTG Awesome Mix Vol.2](https://youtu.be/0TqM7F11LA4)) i vratiti se dragom Selenium-u koji je za to vreme zavšio svoj posao. :)
 
 Rezultati izvršavanja testova nalaziće se na putanji *bdd-security/build/reports/cucumber/pretty/feature_overview.html*
 
