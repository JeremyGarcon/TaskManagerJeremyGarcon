public class User {
  
    private static String username;
    private static String password;
    private static String email;
    private static String date_created;
    private static String time_created;

    // Constructeur
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.date_created = Task.obtenirDateActuelle();
        this.time_created = Task.obtenirHeureActuelle();
    }

    // Getters et Setters pour username
    public static String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        User.username = username;
    }

    // Getters et Setters pour password
    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        User.password = password;
    }

    // Getters et Setters pour email
    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getters et Setters pour date_created
    public static String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        User.date_created = date_created;
    }

    // Getters et Setters pour time_created
    public static String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        User.time_created = time_created;
    }
}
