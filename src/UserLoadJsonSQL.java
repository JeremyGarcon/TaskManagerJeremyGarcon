import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoadJsonSQL {
    // Méthode pour charger le contenu JSON de l'utilisateur à partir de la base de données
    public static String loadUserJson(String username, String password) {
        String sql = "SELECT Taskjson FROM User WHERE Username = ? AND Password = ?";
        String userTaskJson = null; // Variable pour stocker la colonne Taskjson
        
        try (Connection connection = DriverManager.getConnection(SQLVariable.URL, SQLVariable.USERNAME, SQLVariable.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Vérifier si le résultat contient des lignes
                if (resultSet.next()) {
                    // Récupérer la valeur de la colonne Taskjson
                    userTaskJson = resultSet.getString("Taskjson");
                    // L'utilisateur correspondant aux informations de connexion a été trouvé
                    return userTaskJson;
                } else {
                    // Aucun utilisateur correspondant n'a été trouvé
                    return null;
                }
            }
        } catch (SQLException e) {
            // Erreur lors de la connexion à la base de données
            return null;
        }
    }
}

