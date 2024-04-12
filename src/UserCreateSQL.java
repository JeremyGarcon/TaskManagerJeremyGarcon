import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class UserCreateSQL {
   

    // Méthode pour insérer un nouvel utilisateur dans la base de données
    public static void CreateUser(User user) {
        // Vérifier si le fichier JSON existe déjà
        if (!JsonVariable.JsonAccount.exists()) {
            // Si le fichier JSON n'existe pas, le créer et le crypter
            Json.createEmptyJson(JsonVariable.JsonAccount); // Exemple de création d'un fichier JSON vide
        }

        // Les données à insérer dans la base de données
        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String dateCreated = user.getDate_created();
        String timeCreated = user.getTime_created();
        String taskJson = readJsonFile(JsonVariable.UrlJsonAccount);// Contiendra le contenu du fichier JSON

        // Lecture du contenu du fichier JSON
        try {
            taskJson = new String(Files.readAllBytes(Paths.get(JsonVariable.UrlJsonAccount)));
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
        }

        // Connexion à la base de données et exécution de la requête d'insertion
        try (Connection connection = DriverManager.getConnection(SQLVariable.URL, SQLVariable.USERNAME, SQLVariable.PASSWORD)) {
            // Requête SQL pour l'insertion des données
            String sql = "INSERT INTO User (Username, Password, Email, DateCreated, TimeCreated, Taskjson) VALUES (?, ?, ?, ?, ?, ?)";
            // Création de l'objet PreparedStatement pour exécuter la requête
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Attribution des valeurs aux paramètres de la requête
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, dateCreated);
                preparedStatement.setString(5, timeCreated);
                preparedStatement.setString(6, taskJson);
                // Exécution de la requête d'insertion
                int rowsAffected = preparedStatement.executeUpdate();
                // Vérification du succès de l'insertion
                if (rowsAffected > 0) {
                    System.out.println("Les données ont été insérées avec succès.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }
    private static String readJsonFile(String filePath) {
        try {
            // Lecture du contenu du fichier JSON
            byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
            // Conversion des données lues en une chaîne de caractères
            return new String(jsonData);
        } catch (IOException e) {
            return ""; // Retourner une chaîne vide en cas d'erreur
        }
    }
}
