import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Task {
    private String name;
    private String description;
    private boolean completed;
    private String time_created;
    private String date_created;
    private String category;
    private String priority;
    private String deadline;

    public Task(String name, String description, boolean completed, String priority, String category, String time_created, String date_created, String deadline) {
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.time_created = obtenirHeureActuelle();
        this.date_created = obtenirDateActuelle();
        this.category = category;
        this.priority = priority;
        this.deadline = deadline;
    }
    // Getters et setters pour deadline
    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String newdeadline) {
        this.deadline = newdeadline;
    }
    // Getters et setters pour name
    public String getName() {
        return name;
    }
    public void setName(String newname) {
        this.name = newname;
    }
    // Getters et setters pour description
    public String getDescription() {
        return description;
    }
    public void setDescription(String newdescription) {
        this.description = newdescription;
    }
    // Getter et setter pour completed
    public boolean getcompleted() {
        return completed;
    }
    public String getcompletedstring() {
        if (completed == true) {
            return "true";
        } else {
            return "false";
        }
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    // Getter et setter pour time
    public String getTime_created() {
        return time_created;
    }
    public void setTime_created(String newtime) {
        this.time_created = newtime;
    }
    // Getter et setter pour dates
    public String getDate_created() {
        return date_created;
    }
    public void setDate_created(String newDates) {
        this.date_created = newDates;
    }
    // Getter et setter pour category
    public String getCategory() {
        return category;
    }
    public void setCategory(String newCategory) {
        this.category = newCategory;
    }

    public String toString() { // Méthode toString() pour afficher les tasks, dans l'interface graphique
        return name;
    }
        // Getter et setter pour priority
        public String getPriority() {
            return priority;
        }
    
        public void setPriority(String newPriority) {
            this.priority = newPriority;
        }
    
    // Méthode statique pour obtenir l'heure actuelle
    public static String obtenirHeureActuelle() {
        // Obtient l'heure actuelle
        LocalTime heure = LocalTime.now();

        // Crée un formateur de date-heure pour formater l'heure
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Formate l'heure en utilisant le formateur
        String heureFormatee = heure.format(formatter);

        // Retourne l'heure formatée
        return heureFormatee;
    }

    // Méthode statique pour obtenir la date actuelle
    public static String obtenirDateActuelle() {
        // Obtient la date actuelle
        LocalDate date = LocalDate.now();

        // Crée un formateur de date pour formater la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Formate la date en utilisant le formateur
        String dateFormatee = date.format(formatter);

        // Retourne la date formatée
        return dateFormatee;
    }
}
