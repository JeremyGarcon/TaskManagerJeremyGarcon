import javax.swing.*;
import org.json.*;
import java.io.*;
import java.util.HashSet;

public class JsonFilter {

    public static void loadTasksFromCategoryAndPriority(String selectedCategory, String selectedPriority, DefaultListModel<Task> taskListModel) {
        // Utilisation d'un HashSet pour stocker les tâches déjà ajoutées à la liste
        HashSet<Task> addedTasks = new HashSet<>();
        
        taskListModel.clear(); // Assurez-vous que la liste est nettoyée avant d'ajouter de nouvelles tâches
        
        if (Json.verifyJsonFile(JsonVariable.jsonTaskLocal)) {
            DecrypteJson.decrypteJson(JsonVariable.UrlJsonTaskLocal);
            try (BufferedReader reader = new BufferedReader(new FileReader(JsonVariable.jsonTaskLocal))) {
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
                JSONArray jsonArray = new JSONArray(jsonContent.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String taskCategory = jsonObject.optString("category", "");
                    String taskPriority = jsonObject.optString("priority", "");

                    if ((selectedCategory.equals("All") || taskCategory.equals(selectedCategory)) &&
                        (selectedPriority.equals("All") || taskPriority.equals(selectedPriority))) {

                        String name = jsonObject.optString("name", "");
                        String description = jsonObject.optString("description", "");
                        boolean completed = jsonObject.optBoolean("completed", false);
                        String time = jsonObject.optString("time_created", ""); // Peut être vide si non présent dans le JSON
                        String date = jsonObject.optString("date_created", ""); // Peut être vide si non présent dans le JSON
                        String deadline = jsonObject.optString("deadline", ""); // Peut être vide si non présent dans le JSON

                        Task task = new Task(name, description, completed, taskPriority, taskCategory, time, date, deadline);
                        
                        // Vérifie si la tâche est déjà ajoutée à la liste avant de l'ajouter
                        if (addedTasks.add(task)) {
                            taskListModel.addElement(task);
                        }
                    }
                }
    
                
            } catch (IOException | JSONException e) {
                e.printStackTrace();

            }
        } 
        CrypteJson.crypteJson(JsonVariable.UrlJsonTaskLocal);
    }
}
