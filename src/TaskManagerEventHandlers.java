import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;


public class TaskManagerEventHandlers {

    private JButton addButton;
    private JButton removeButton;
    private JTextField userInputField;
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private JComboBox categoryFiltreButton;
    private JComboBox priorityFiltreButton;
    private JButton edittask;

    // Constructeur de la classe TaskManagerEventHandlers, qui prend en paramètre les éléments de l'interface graphique
    public TaskManagerEventHandlers(JButton addButton, JButton removeButton, JTextField userInputField, DefaultListModel<Task> listModel, JList<Task> taskList, 
                                    Json jsonHandler, JComboBox categoryFiltreButton, JComboBox priorityFiltreButton, JButton edittask, JButton addCategory ) {
        this.addButton = addButton;
        this.removeButton = removeButton;
        this.userInputField = userInputField;
        this.listModel = listModel;
        this.taskList = taskList;
        this.categoryFiltreButton = categoryFiltreButton;
        this.priorityFiltreButton = priorityFiltreButton;
        this.edittask = edittask;
    }

    public void setupEventHandlers() {

        addButton.addActionListener(new ActionListener() { // Méthode pour ajouter une tâche
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = userInputField.getText() + "(New Task)";
                if (!taskName.isEmpty()) {
                    Task newTask = new Task(taskName, "", false, "", "(New Task)", "", "", taskName );
                    listModel.addElement(newTask);
                    Json.writeJsonFile(newTask);
                    userInputField.setText("");
                    Json.refreshTaskList();
                    if(JsonVariable.Acount == true) {
                        DecrypteJson.decrypteJson(JsonVariable.UrlJsonAccount);
                        UserUpdateJsonSQL.updateUserJson(User.getUsername(), User.getPassword());
                        CrypteJson.crypteJson(JsonVariable.UrlJsonAccount);
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() { // Méthode pour supprimer une tâche
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                   Json.removeTask(selectedIndex);
                    Json.refreshTaskList();
                    if(JsonVariable.Acount == true) {
                        DecrypteJson.decrypteJson(JsonVariable.UrlJsonAccount);
                        UserUpdateJsonSQL.updateUserJson(User.getUsername(), User.getPassword());
                        CrypteJson.crypteJson(JsonVariable.UrlJsonAccount);
                    }
                }
            }
        });

        edittask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                Task taskToEdit = listModel.getElementAt(selectedIndex);
                EditTask EditTask = new EditTask(null, taskToEdit);
                EditTask.setVisible(true);
                if(JsonVariable.Acount == true) {
                    DecrypteJson.decrypteJson(JsonVariable.UrlJsonAccount);
                    UserUpdateJsonSQL.updateUserJson(User.getUsername(), User.getPassword());
                    CrypteJson.crypteJson(JsonVariable.UrlJsonAccount);
                }
            }
        });
        
           
        categoryFiltreButton.addActionListener(new ActionListener() { // Méthode pour filtrer les tâches par catégorie
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryFiltreButton.getSelectedItem();
                String selectedPriority = (String) priorityFiltreButton.getSelectedItem();
                filterTasks(selectedCategory, selectedPriority);
            }
        });
        
        priorityFiltreButton.addActionListener(new ActionListener() { // Méthode pour filtrer les tâches par priorité
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryFiltreButton.getSelectedItem();
                String selectedPriority = (String) priorityFiltreButton.getSelectedItem();
                filterTasks(selectedCategory, selectedPriority);
            }
        });

        
    }
    private void filterTasks(String selectedCategory, String selectedPriority) { // Méthode pour filtrer les tâches
        if (selectedCategory.equals("All") && selectedPriority.equals("All")) {
            Json.refreshTaskList();
            return;
        } else {
            // Filtrer les tâches par priorité uniquement
            listModel.clear();
            JsonFilter.loadTasksFromCategoryAndPriority(selectedCategory, selectedPriority, listModel);
        }
    }    
}