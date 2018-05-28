# Javier

API REST centralisant les échanges entre les différents services.

# Utilisation
- Dans un premier temps, connecter le token et noter son chemin d'accès (exemple: /dev/ttyACM0)
- Reporter ce chemin dans `src/main/java/org/orberto/javier/API/LoadConfigurationListener.java` (ligne 13)
- Compiler Javier : `./gradlew build`
- Lancer Javier `./gradlew jettyRun`

Javier écoute par défaut sur le port 8080.
Pour accéder à swagger, qui permet une visualisation interactive de l'API, visiter : `localhost:8080/javier/api`

Afin de commencer à utiliser Javier à des fins de test, exécuter une requête GET sur `/javier/api/walter/initdb` pour initialiser la base de données du token avec l'utilisateur `test:test` et un portr-monnaie vide.

## TODO
Implémenter les fonctions `setBlob()` et `getBlob()` dans JDBCng pour utiliser les fonctions relatives au porte-monnaie ;)
