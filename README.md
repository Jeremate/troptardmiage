troptardmiage
=============
https://troptardmiage.appspot.com

Projet M1 Miage en AngularJS et services REST via Google App Engine en Java

## Use Case
1. L'utilisateur renseigne ses préférences d'événements
2. Au moment d'un événement, le système le notifie et lui demande sa position
3. Le système garde une trace des événements assistés et non assistés
4. Le système établie un classement des meilleurs losers

## Products
- [App Engine][1]

## Language
- [Java][2]
- [AngularJS][8]

## APIs
- [Google Cloud Endpoints][3]
- [Google App Engine Maven plugin][4]
- [Sorties et loisirs en Loire-Atlantique][11]

## Architecture
Backend : Google App Engine via services REST créés en Java

Frontend : AngularJS

### Prérequis
- Java 1.7
- [Maven][7] (mvn en ligne de commande), version 3
- git : gestionnaire de versions
- gitflow : gestionnaire de workflows sous git
- [npm][9] : gestionnaire de paquets nodejs _uniquement utilisé pour installer bower_
- [bower][10] : gestionnaire de paquets frontend

### Frontend
Installation de librairies à l'aide de l'outil bower.

_Exemple :_
* _Se placer dans le dossier `src/main/webapp`_ 
* _Puis exécuter : `bower install nom\_lib`_
* _La librairie est installée dans le dossier `bower_components`_

## Installation et test en local
- Cloner le repo : `git clone https://github.com/jeremate/troptardmiage.git`
- Se déplacer sur la branche develop : `git checkout develop`
- Build le projet : `mvn clean install`
- Tester l'app en local : `mvn appengine:devserver` disponible à [localhost:8080/_ah/api/explorer][5]

[1]: https://developers.google.com/appengine
[2]: http://java.com/en/
[3]: https://developers.google.com/appengine/docs/java/endpoints/
[4]: https://developers.google.com/appengine/docs/java/tools/maven
[5]: https://localhost:8080/_ah/api/explorer
[6]: https://console.developers.google.com/
[7]: https://cloud.google.com/appengine/docs/java/tools/maven
[8]: https://angularjs.org
[9]: https://www.npmjs.com/
[10]: https://www.npmjs.com/package/bower
[11]: http://api.loire-atlantique.fr/