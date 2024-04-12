import javax.swing.*;
import org.json.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Json {

    static final String key = "ABCDEFGHIJKLMNOP"; // Exemple de clé de chiffrement (16, 24 ou 32 bytes)

    // Vérification de l'existence du fichier JSON
    static boolean verifyJsonFile(File jsonFile) {
        return jsonFile.exists();
    }

    // Création d'un fichier JSON vide
    public static void createEmptyJson(File jsonFile) {
        if (!verifyJsonFile(jsonFile)) {
            try (FileWriter writer = new FileWriter(jsonFile)) {
                writer.write("[]"); // Écriture d'un tableau JSON vide
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createJson(File jsonFile, String json) {
        if (!verifyJsonFile(jsonFile)) {
            try (FileWriter writer = new FileWriter(jsonFile)) {
                writer.write(json); // Écriture du JSON passé en paramètre
                System.out.println("JSON file created successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
    }

    // Chargement des tâches depuis le fichier JSON
    public static void loadTasksFromFile(DefaultListModel<Task> taskListModel) {
        if (verifyJsonFile(JsonVariable.JsonUser)) {
            DecrypteJson.decrypteJson(JsonVariable.UrlJsonUser);
            try (BufferedReader reader = new BufferedReader(new FileReader(JsonVariable.JsonUser))) {
                String jsonContent = reader.lines().collect(Collectors.joining());
                JSONArray jsonArray = new JSONArray(jsonContent);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String description = jsonObject.getString("description");
                    boolean completed = jsonObject.getBoolean("completed");
                    String priority = jsonObject.getString("priority");
                    String category = jsonObject.getString("category");
                    String time_created = jsonObject.getString("time_created");
                    String date_created = jsonObject.getString("date_created");
                    String deadline = jsonObject.getString("deadline");

                    Task task = new Task(name, description, completed, priority, category, time_created, date_created, deadline);
                    taskListModel.addElement(task);
                }
                CrypteJson.crypteJson(JsonVariable.UrlJsonUser);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } 
    }

    // Ajout d'une tâche
    public static void writeJsonFile(Task task) {
        if (verifyJsonFile(JsonVariable.JsonUser)) {
            DecrypteJson.decrypteJson(JsonVariable.UrlJsonUser);
            try (BufferedReader reader = new BufferedReader(new FileReader(JsonVariable.JsonUser))) {
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
                JSONArray jsonArray = new JSONArray(jsonContent.toString());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", task.getName());
                jsonObject.put("description", task.getDescription());
                jsonObject.put("completed", task.getcompleted());
                jsonObject.put("priority", task.getPriority());
                jsonObject.put("category", task.getCategory());
                jsonObject.put("time_created", task.getTime_created());
                jsonObject.put("date_created", task.getDate_created());
                jsonObject.put("deadline", task.getDeadline());
                jsonArray.put(jsonObject);

                try (FileWriter writer = new FileWriter(JsonVariable.JsonUser)) {
                    writer.write(jsonArray.toString(7));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } 
        CrypteJson.crypteJson(JsonVariable.UrlJsonUser);
    }

    // Supprimer une tâche
    public static void removeTask(int selectedIndex) {

        if (verifyJsonFile(JsonVariable.JsonUser)) {
            DecrypteJson.decrypteJson(JsonVariable.UrlJsonUser);
            try (BufferedReader reader = new BufferedReader(new FileReader(JsonVariable.JsonUser))) {
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
                JSONArray jsonArray = new JSONArray(jsonContent.toString());

                if (selectedIndex >= 0 && selectedIndex < jsonArray.length()) {
                    // Supprimer la tâche à l'index sélectionné
                    jsonArray.remove(selectedIndex);

                    // Écrire le nouveau contenu dans le fichier JSON
                    try (FileWriter writer = new FileWriter(JsonVariable.JsonUser)) {
                        writer.write(jsonArray.toString());
                        System.out.println("Task removed from JSON file successfully!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }   
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                System.err.println("Error reading JSON file or removing task!");
            }
        } 
        CrypteJson.crypteJson(JsonVariable.UrlJsonUser);
    }

    // Rafraîchir la liste des tâches dans l'interface graphique
    public static void resetFilterButtons() {
        TaskManager.CategoryFiltreButton.setSelectedIndex(0); // Réinitialiser l'index sélectionné du ComboBox de catégorie
        TaskManager.PriorityFiltreButton.setSelectedIndex(0); // Réinitialiser l'index sélectionné du ComboBox de priorité
    }

    public static void refreshTaskList() {
        resetFilterButtons();
        TaskManager.listModel.clear();
        loadTasksFromFile(TaskManager.listModel);
    }

    public static void updateTask(Task task, String lastNameTask) {
        try {
            DecrypteJson.decrypteJson(JsonVariable.UrlJsonUser);
            // Lire le contenu du fichier JSON
            String jsonDataString = new String(Files.readAllBytes(Paths.get(JsonVariable.UrlJsonUser)));
            JSONArray jsonArray = new JSONArray(jsonDataString);

            // Appeler la méthode pour mettre à jour les champs
            updateJsonFields(task, jsonArray, lastNameTask);

            // Écrire les modifications dans le fichier JSON
            Files.write(Paths.get(JsonVariable.UrlJsonUser), jsonArray.toString(4).getBytes());
            System.out.println("Fields updated in JSON file successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        CrypteJson.crypteJson(JsonVariable.UrlJsonUser);
    }

    private static void updateJsonFields(Task task, JSONArray jsonArray, String lastNameTask) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // Vérifier si le nom de la tâche correspond
            if (jsonObject.getString("name").equals(lastNameTask)) {
                // Mettre à jour les champs avec les valeurs de la tâche
                jsonObject.put("name", task.getName());
                jsonObject.put("description", task.getDescription());
                jsonObject.put("completed", task.getcompleted());
                jsonObject.put("priority", task.getPriority());
                jsonObject.put("category", task.getCategory());
                jsonObject.put("deadline", task.getDeadline());
                break; // Sortir de la boucle après la mise à jour
            }
        }
    }
}
