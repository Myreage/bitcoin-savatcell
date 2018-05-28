import com.google.common.net.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.KeyChain;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.KeyChainEventListener;
import org.bitcoinj.wallet.listeners.ScriptsChangeEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.bitcoinj.wallet.listeners.WalletCoinsSentEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.bitcoinj.core.listeners.TransactionConfidenceEventListener;

import static com.google.common.base.Predicates.equalTo;


/**
 * The following example shows how to use the by bitcoinj provided WalletAppKit.
 * The WalletAppKit class wraps the boilerplate (Peers, BlockChain, BlockStorage, Wallet) needed to set up a new SPV bitcoinj app.
 *
 * In this example we also define a WalletEventListener class with implementors that are called when the wallet changes (for example sending/receiving money)
 */
public class Core {





    public static void main(String[] args) {

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "params.properties";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String walletPath  = appProps.getProperty("walletPath");

        NetworkParameters params = RegTestParams.get();



        WalletAppKit kit = new WalletAppKit(params, new File(walletPath), "walletappkit-example");


        kit.connectToLocalHost();

        kit.startAsync();
        kit.awaitRunning();



        kit.wallet().addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
            @Override
            public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
                System.out.println("-----> coins resceived: " + tx.getHashAsString());
                System.out.println("received: " + tx.getValue(wallet));

                // HTTP POST
                com.squareup.okhttp.MediaType JSON =
                        com.squareup.okhttp.MediaType.parse("application/json; charset=utf-8");
                Date date = new Date();
                String json = "{\"to\": " + wallet.currentReceiveAddress() +
                        ", \"address\": " +  tx.getHashAsString() +
                        ", \"amount\": " + tx.getValue(wallet) +
                        ", \"date\": " +  date  + " }";

                RequestBody body = RequestBody.create(JSON, json);
                OkHttpClient client = new OkHttpClient();
                Request request  = new Request.Builder()
                        .url("http://127.0.0.1/javier/api/walter/transaction")
                        .post(body)
                        .build();

            }
        });

        kit.wallet().addCoinsSentEventListener(new WalletCoinsSentEventListener() {
            @Override
            public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
                System.out.println("coins sent");

                /* TODO
                send to api :
                tx.getHashAsString();
                tx.getValue(wallet);
                */
            }
        });

        kit.wallet().addKeyChainEventListener(new KeyChainEventListener() {
            @Override
            public void onKeysAdded(List<ECKey> keys) {
                System.out.println("new key added");
            }
        });

        kit.wallet().addScriptsChangeEventListener(new ScriptsChangeEventListener() {
            @Override
            public void onScriptsChanged(Wallet wallet, List<Script> scripts, boolean isAddingScripts) {
                System.out.println("new script added");
            }
        });

        kit.wallet().addTransactionConfidenceEventListener(new TransactionConfidenceEventListener() {
            @Override
            public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
                System.out.println("-----> confidence changed: " + tx.getHashAsString());
                TransactionConfidence confidence = tx.getConfidence();
                System.out.println("new block depth: " + confidence.getDepthInBlocks());
            }
        });

        kit.wallet().addWatchedAddress(kit.wallet().freshReceiveAddress());
        System.out.println("New address created");
        List<Address> list = kit.wallet().getWatchedAddresses();
        System.out.println("You have " + list.size() + " addresses!");
        for (Address a: list) {
            System.out.println(a.toString());
        }


        while(true){

        }






        // Make sure to properly shut down all the running services when you manually want to stop the kit. The WalletAppKit registers a runtime ShutdownHook so we actually do not need to worry about that when our application is stopping.
        //System.out.println("shutting down again");
        //kit.stopAsync();
        //kit.awaitTerminated();
    }

}