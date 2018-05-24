package apps;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;

import tools.Constants;
import api.ClientAPI;
import beans.User;
import configuration.Configuration;
import cryptoTools.KeyManager;


public class Plug
{
    /**
     * APP Main
     * 
     * @author Majdi Ben Fredj
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        int userGID = Integer.parseInt(Configuration.getConfiguration().getProperty("myGID"));
        String tCellIP = Configuration.getConfiguration().getProperty("myIP");
        int port = Integer.parseInt(Configuration.getConfiguration().getProperty("myPort"));

        User user= null;

        // load user PubKey
        try {
            String KeyPath = Configuration.getConfiguration().getProperty("keyPath");
            KeyManager keygen = new KeyManager();
            String publicKeyPath = KeyPath + Constants.PUB_KEY_PREFIX + userGID + Constants.KEY_EXT;
            PublicKey pubKey = keygen.LoadPublicKey(publicKeyPath, Constants.RSA_ALG);
            String pubkey = keygen.PublicKeyToString(pubKey);

            user = new User(userGID, tCellIP, port, pubkey);
            
        } catch (Exception e) {
            e.printStackTrace();
        }


        // TEST STOREFILE
        ClientAPI.storeFile("/home/user/SIPD/machines/Client10/Image.jpg", user);

        // TEST GETFILEDESC
        ClientAPI.getFileDesc(user);

        // TEST READFILE
        ClientAPI.readFile("10|/home/user/SIPD/machines/Client10/TMP/Encrypted_Image.jpg",user);

        //TEST SHAREFILE
        System.out.println("Users GID : "+userGID);
                
        System.out.println("Dooooone");
    }
}
