installer bitcoind, qui sert à faire du bitcoin en local

-pour initialiser et lancer bitcoind:
bitcoind -regtest -daemon
bitcoin-cli -regtest generate 101

ça sert à lancer le démon et à entrer 101 nouvelles entrées sinon ça marche pas (faut qu'y ait +100 entrées dans le wallet pour qu'il soit valable si j'ai bien compris)

-dans votre fichier java, remplacer la ligne de récup des paramètres par : 
NetworkParameters params = RegTestParams.get();

-dans votre fichier java, à la création du kit, remplacer le "." par le chemin de votre wallet créé par bitcoind, càd chez moi :
~/.bitcoin/regtest/wallets
