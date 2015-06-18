troptardmiage
=============
[Application TropTardMiage][0]

Projet M1 Miage en AngularJS et services REST via Google App Engine en Java

## Use Case
1. L'utilisateur renseigne ses thèmes favoris
2. Une liste d'événements lui est proposé pour lesquels il peut s'inscrire
3. Au moment d'un événement, le système le notifie et lui demande sa position
4. Le système garde une trace des événements assistés
5. Le système établie un classement des meilleurs losers

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

Le fichier bower.json renseigne sur les dépendances de l'app. Il se met à jour à chaque install en précisant l'option `--save`. Il permet aussi d'éviter de partager le dossier bower_components qui peut devenir volumineux.

_Exemple :_
* _Se placer dans le dossier `src/main/webapp`_ 
* _Puis exécuter : `bower install nom_lib --save`_
* _La librairie est installée dans le dossier `bower_components`_

## Installation et test en local
- Cloner le repo : `git clone https://github.com/jeremate/troptardmiage.git`
- Se déplacer sur la branche develop : `git checkout develop`
- Télécharger les dépendances de la webapp : `cd src/main/webapp ; bower install`
- Revenir à la racine du projet : `cd ../../../`
- Build le projet : `mvn clean install`
- Tester l'app en local : `mvn appengine:devserver` disponible à [localhost:8080/_ah/api/explorer][5]

### Importer le projet sous Eclipse
- Dans la fenêtre "Project Explorer", cliquer-droit > Import > Import.
- Une nouvelle fenêtre apparait, choisir "Git > Projects from Git".
- Cliquer sur "Next" puis "Existing local repository".
- Soit le projet apparait dans la liste, soit il faut l'ajouter via le bouton "Add" puis cliquer sur "Next".
- Dans la liste de sélection, choisir "Import as general project" puis cliquer sur "Finish".
- Enfin cliquer-droit sur le projet > Configure > Convert to Maven Project

_Si Eclipse a rajouté des fichiers (.project, .classpath, etc...), spécifier à Git de ne pas les inclure dans le .gitignore_

## Reste à faire
- Réaliser des filtres sur les événements
- Indiquer le chargement par un spin ou équivalent
- Permettre de voir la liste de ses inscriptions
- Afficher des messages d'erreur en cas d'incidents

[0]: https://troptardmiage.appspot.com
[1]: https://developers.google.com/appengine
[2]: http://java.com/en/
[3]: https://developers.google.com/appengine/docs/java/endpoints/
[4]: https://developers.google.com/appengine/docs/java/tools/maven
[5]: http://localhost:8080/_ah/api/explorer
[6]: https://console.developers.google.com/
[7]: https://cloud.google.com/appengine/docs/java/tools/maven
[8]: https://angularjs.org
[9]: https://www.npmjs.com/
[10]: https://www.npmjs.com/package/bower
[11]: http://api.loire-atlantique.fr/