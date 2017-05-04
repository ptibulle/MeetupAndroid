# MeetupAndroid

Code des démos du Meetup Android du 3 mai 2017

Les 3 services sont à ouvrir dans AndroidStudio.

Les 3 plugins peuvent être ajoutés simplement à une app cordova de base !

Installation de cordova :
```
$ npm install -g cordova
```
Création de l'app avec l'ajout de la gestion d'android :
```
$ cordova create MyApp
$ cd MyApp
$ cordova platform add android
```
Ajout des plugins :
```
$ cordova plugin add https://github.com/ptibulle/MeetupAndroid.git#:/plugins/meetupservice1-plugin
$ cordova plugin add https://github.com/ptibulle/MeetupAndroid.git#:/plugins/meetupservice2-plugin
$ cordova plugin add https://github.com/ptibulle/MeetupAndroid.git#:/plugins/meetupservice3-plugin
```
Et en connectant son device android (en mode développeur) c'est parti :
```
$ cordova run android
```

Les magnifiques commandes que je lance dans la console des devtools de chrome://inspect/#devices connecté à l'app cordova lancée sur mon device Android :

```javascript
meetupservice1.startTimer(function(error){console.log(error);}, function(success){console.log(success);});
meetupservice1.stopTimer(function(error){console.log(error);}, function(success){console.log(success);});
meetupservice2.bindService(function(error){console.log(error);}, function(success){console.log(success);});
meetupservice2.startTimer(function(error){console.log(error);}, function(success){console.log(success);});
meetupservice2.stopTimer(function(error){console.log(error);}, function(success){console.log(success);});
meetupservice3.bindService(function(error){console.log(error);}, function(success){console.log(success);});
meetupservice3.register(function(error){console.log(error);}, function(success){console.log(success);});
meetupservice3.startTimer(function(error){console.log(error);}, function(success){console.log(success);});
meetupservice3.stopTimer(function(error){console.log(error);}, function(success){console.log(success);});
meetupservice3.unregister(function(error){console.log(error);}, function(success){console.log(success);});
```
