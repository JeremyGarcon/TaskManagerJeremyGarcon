import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JsonUpdateSQL {

    public static void updateJsonInDatabase(String username, String password) {
        try {
            // Lire le contenu du fichier JSON
            String jsonData = new String(Files.readAllBytes(Paths.get(JsonVariable.UrlJsonUser)));

            // Exécuter la requête SQL pour mettre à jour la colonne Taskjson
            String sql = "UPDATE User SET Taskjson = ? WHERE Username = ? AND Password = ?";
            try (Connection connection = DriverManager.getConnection(SQLVariable.URL, SQLVariable.USERNAME, SQLVariable.PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                
                preparedStatement.setString(1, jsonData);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                
                int rowsAffected = preparedStatement.executeUpdate();
                // Vérifier si la mise à jour a réussi
                if (rowsAffected > 0) {
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
