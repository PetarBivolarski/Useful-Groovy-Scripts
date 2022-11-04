import java.security.Key
import java.security.KeyStore
import java.security.cert.Certificate

// Useful when we want to use them in curl --cert and --key

KeyStore keyStore = KeyStore.getInstance("jks");
keyStore.load(
        new FileInputStream("path"),
        "pass".toCharArray()
);
char[] password = "0beliXm,.-".toCharArray();
/* Save the private key. */
FileOutputStream kos = new FileOutputStream("tmpkey.der");
Key pvt = keyStore.getKey("cso_soabp", password);
kos.write(pvt.getEncoded());
kos.flush();
kos.close();
/* Save the certificate. */
FileOutputStream cos = new FileOutputStream("tmpcert.der");
Certificate pub = keyStore.getCertificate("cso_soabp");
cos.write(pub.getEncoded());
cos.flush();
cos.close();

/* Use OpenSSL utilities to convert these files (which are in binary format) to PEM format.
openssl pkcs8 -inform der -nocrypt < tmpkey.der > tmpkey.pem
openssl x509 -inform der < tmpcert.der > tmpcert.pem
 */
