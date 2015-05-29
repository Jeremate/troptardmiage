troptardmiage
=============
https://troptardmiage.appspot.com
Projet M1 Miage en AngularJS et services REST via Google App Engine en Java

## Products
- [App Engine][1]

## Language
- [Java][2]

## APIs
- [Google Cloud Endpoints][3]
- [Google App Engine Maven plugin][4]

## Setup Instructions after generating maven endpoint-skeleton archetype

1. Update the value of `application` in `appengine-web.xml` to the app
   ID you have registered in the App Engine admin console and would
   like to use to host your instance of this sample.

1. Add your API method to `src/main/java/${packageInPathFormat}/YourFirstAPI.java`.

1. Optional step: These sub steps are not required but you need this
   if you want to have auth protected methods.

    1. Update the values in `src/main/java/${packageInPathFormat}/Constants.java`
       to reflect the respective client IDs you have registered in the
       [APIs Console][6]. 

    1. You also need to supply the web client ID you have registered
       in the [APIs Console][4] to your client of choice (web, Android,
       iOS).

1. Run the application with `mvn appengine:devserver`, and ensure it's
   running by visiting your local server's api explorer's address (by
   default [localhost:8080/_ah/api/explorer][5].)

1. Get the client library with

   $ mvnappengine:endpoints_get_client_lib

   It will generate a client library jar file under the
   `target/endpoints-client-libs/<api-name>/target` directory of your
   project, as well as install the artifact into your local maven
   repository.

1. Deploy your application to Google App Engine with

   $ mvn appengine:update

## Architecture
Backend : Google App Engine
Frontend : AngularJS

Utilisation de gitflow pour gérer les versions et le workflow

### Prérequis
- Java 1.7
- [Maven][7] (mvn en ligne de commande), version 3
- git : gestionnaire de versions
- gitflow : gestionnaire de workflows sous git
- npm : gestionnaire de paquets nodejs _uniquement utilisé pour installer bower_
- bower : gestionnaire de paquets frontend

### Frontend
Installation de librairies à l'aide de l'outil bower.
_Exemple : bower install angular_

## Installation et test en local
- Cloner le repo : git clone https://github.com/jeremate/troptardmiage.git
- Se déplacer sur la branche develop : git checkout develop
- mvn clean install
- mvn appengine:devserver

[1]: https://developers.google.com/appengine
[2]: http://java.com/en/
[3]: https://developers.google.com/appengine/docs/java/endpoints/
[4]: https://developers.google.com/appengine/docs/java/tools/maven
[5]: https://localhost:8080/_ah/api/explorer
[6]: https://console.developers.google.com/
[7]: https://cloud.google.com/appengine/docs/java/tools/maven
