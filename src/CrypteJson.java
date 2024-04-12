import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class CrypteJson {
    
    private static final String key = "ABCDEFGHIJKLMNOP"; // Exemple de clé de chiffrement (16, 24 ou 32 bytes)

    public static String encrypt(JSONArray jsonArray, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(jsonArray.toString().getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeEncryptedJSONToFile(String encryptedJSON, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(encryptedJSON);
        }
    }

    public static void crypteJson(String File) {
        
        try {
            // Lire le contenu du fichier JSON à chiffrer
            String jsonDataString = new String(Files.readAllBytes(Paths.get(File)));
            JSONArray jsonArray = new JSONArray(jsonDataString);

            // Générer une clé secrète
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");

            // Chiffrer le JSON
            String encryptedJSON = encrypt(jsonArray, secretKey);

            // Écrire le JSON chiffré dans le fichier
            writeEncryptedJSONToFile(encryptedJSON, File);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
    
}
