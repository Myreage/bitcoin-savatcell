package tools;

/**
 * Constants definitions
 * @author tranvan
 */
public class Constants 
{
    
    public final static int CMD_STORE_FILE = 0;
    public final static int CMD_SHARE_FILE = 1;
    public final static int CMD_GET_FILES_DESC = 2;
    public final static int CMD_READ_FILE = 3;
    public final static int CMD_GET_FILE_TO_SHARE = 4;
    public final static int CMD_GET_USER = 5;
    

    public final static String TMP_FILES=			"TMP/";    
    
    public final static String SYM_DECR_FILE_NAME = "Decrypted_";
    public final static String SYM_ENCR_FILE_NAME = "Encrypted_";
    public final static String PUB_KEY_PREFIX = "pub";
    public final static String PR_KEY_PREFIX = "priv";
    public final static String KEY_EXT = ".key";
    
    public final static String RSA_ALG = "RSA";
    public final static int RSA_KEY_LENGTH = 1024;
    public final static int RSA_PLAIN_BLOCK_SIZE = (RSA_KEY_LENGTH / 8 ) - 11;
    
    public final static int AES_KEY_ENCODED_BYTES_LENGTH = 24;
    public final static int IV_LENGTH = 16;
    public final static int IV_ENCODED_BYTES_LENGTH = 24;

    public final static int SQL_OK = 100;
    public final static int SQL_KO = 101;
    public final static int SQL_NOT_UNIQUE = 102;
    public final static int KO = -1;
    public final static int OK = 104;
    
}
