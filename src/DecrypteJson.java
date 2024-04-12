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

public class DecrypteJson {

    private static final String key = "ABCDEFGHIJKLMNOP"; // Exemple de clé de chiffrement (16, 24 ou 32 bytes)

    public static JSONArray decrypt(String encryptedJSON, SecretKey secretKey) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedJSON);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            String decryptedString = new String(decryptedBytes);
            return new JSONArray(decryptedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeJSONToFile(JSONArray jsonArray, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonArray.toString(7));
        }
    }

    public static void decrypteJson(String File) {
         
        try {
            
            // Lire le contenu du fichier JSON chiffré
            String encryptedJSON = new String(Files.readAllBytes(Paths.get(File)));

            // Générer une clé secrète
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");

            // Déchiffrer le JSON
            JSONArray decryptedJSON = decrypt(encryptedJSON, secretKey);

            // Écrire le JSON déchiffré dans le fichier avec le même format de saut de ligne que le fichier d'origine
            writeJSONToFile(decryptedJSON, File);
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
}
