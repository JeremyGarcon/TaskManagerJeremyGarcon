import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserUpdateJsonSQL {

    public static boolean updateUserJson(String username, String password) {
        // Lecture du contenu du fichier JSON
        String jsonContent;
        try {
            jsonContent = new String(Files.readAllBytes(Paths.get(JsonVariable.UrlJsonUser)));
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
            return false;
        }

        // Requête SQL pour mettre à jour la colonne Taskjson
        String sql = "UPDATE User SET Taskjson = ? WHERE Username = ? AND Password = ?";
        
        try (Connection connection = DriverManager.getConnection(SQLVariable.URL, SQLVariable.USERNAME, SQLVariable.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            // Paramètres de la requête
            preparedStatement.setString(1, jsonContent);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            
            // Exécution de la requête
            int rowsAffected = preparedStatement.executeUpdate();
            
            // Vérification si la mise à jour a réussi
            if (rowsAffected > 0) {
                // Mise à jour réussie
                return true;
            } else {
                // Aucune mise à jour effectuée
                return false;
            }
        } catch (SQLException e) {
            // Erreur lors de la connexion à la base de données
            return false;
        }
    }
}
