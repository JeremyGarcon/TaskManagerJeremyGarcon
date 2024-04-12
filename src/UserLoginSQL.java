import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginSQL {

    // Méthode pour vérifier si l'utilisateur existe dans la base de données
    public static boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM User WHERE Username = ? AND Password = ?";
        
        try (Connection connection = DriverManager.getConnection(SQLVariable.URL, SQLVariable.USERNAME, SQLVariable.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Vérifier si le résultat contient des lignes
                if (resultSet.next()) {
                    // L'utilisateur correspondant aux informations de connexion a été trouvé
                    return true;
                } else {
                    // Aucun utilisateur correspondant n'a été trouvé
                    return false;
                }
            }
        } catch (SQLException e) {
            // Erreur lors de la connexion à la base de données
            return false;
        }
    }
}
    