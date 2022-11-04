import javax.net.ssl.KeyManager
import javax.net.ssl.KeyManagerFactory
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.HttpsURLConnection

// First, let's configure the SSL for client authentication = client keystore
KeyStore keystore = KeyStore.getInstance("JKS");
keystore.load(
        new FileInputStream("/path/..."),
        "password".toCharArray()
);

KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()); // SunX509
kmf.init(keystore, "password".toCharArray());
KeyManager[] keyManagers = kmf.getKeyManagers();

// Now, let's configure the client to trust the server = client truststore
KeyStore trustStore = KeyStore.getInstance("JKS");
trustStore.load(
        new FileInputStream("/path/..."),
        "password".toCharArray()
);

TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); // SunX509
tmf.init(trustStore);
TrustManager[] trustManagers = tmf.getTrustManagers();

SSLContext sslContext = SSLContext.getInstance("TLS");
sslContext.init(keyManagers, trustManagers, null); // You can provide SecureRandom also if you wish

HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

def request = new URL("https://example.com")
        .openConnection()

def getRC = request.getResponseCode();
println(getRC);
if (getRC.equals(200)) {
    println(request.getInputStream().getText());
}
