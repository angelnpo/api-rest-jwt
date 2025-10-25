package ec.com.api.jwt.common;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import lombok.extern.slf4j.Slf4j;

/**
 * Class to generate a key pair.
 * 
 * @author Angel Cuenca
 */
@Slf4j
public class GenerateKeyPair {

    public static void main(String[] args) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            byte[] pub = keyPair.getPublic().getEncoded();
            byte[] pri = keyPair.getPrivate().getEncoded();

            PemWriter pemWriter = new PemWriter(new OutputStreamWriter(
                    new FileOutputStream("src/main/resources/certificates/pub.pem")));
            PemObject pemObject = new PemObject("PUBLIC KEY", pub);
            pemWriter.writeObject(pemObject);
            pemWriter.close();

            // generated private key
            pemWriter = new PemWriter(new OutputStreamWriter(
                    new FileOutputStream("src/main/resources/certificates/pri.pem")));
            pemObject = new PemObject("PRIVATE KEY", pri);
            pemWriter.writeObject(pemObject);
            pemWriter.close();
        } catch (Exception e) {
            log.error("Error generated key pair:", e);
        }
    }
}
