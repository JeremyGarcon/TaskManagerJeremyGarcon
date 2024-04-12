import javax.swing.*;

public class TaskManager extends JFrame {

    protected static Object main;
    public static DefaultListModel<Task> listModel;
    public static JList<Task> taskList;
    private JButton addButton;

    // Déclaration des labels pour les filtres de catégorie et de priorité
    static JLabel CategoryFiltre;
    static JComboBox CategoryFiltreButton;
    static JLabel PriorityFiltre;
    static JComboBox PriorityFiltreButton;

    // Déclaration des boutons pour les actions sur les tâches
    private JButton removeButton;
    private JButton addCategory;
    private JButton edittask;


    // Déclaration du champ de texte pour l'entrée utilisateur
    private JTextField userInputField;
    private Json jsonHandler;
    private TaskManagerEventHandlers eventHandlers;

    public TaskManager() {
        super("Task Manager"); // Titre de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer l'application lors de la fermeture de la fenêtre

        ImageIcon icon = new ImageIcon("Images/Icone.jpg"); // Création d'une icône pour la fenêtre
        setIconImage(icon.getImage());

        // Initialisation des composants graphiques
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        addButton = new JButton("Add Task");
        addCategory = new JButton("Manage Category");
        edittask = new JButton("Edit Task");
        CategoryFiltre = new JLabel("List of all taks");
        CategoryFiltreButton = new JComboBox(Category.categoryFilter);
        PriorityFiltre = new JLabel("List of all Priority");
        PriorityFiltreButton = new JComboBox(Priority.priorityFilterJson);
        removeButton = new JButton("Remove Task");
        userInputField = new JTextField(15);


        // Création des instances  de TaskManagerJson et TaskManagerEventHandlers
        jsonHandler = new Json();
        eventHandlers = new TaskManagerEventHandlers(addButton,removeButton, userInputField, listModel, taskList, jsonHandler, CategoryFiltreButton, PriorityFiltreButton, edittask, addCategory);

        // Configuration des gestionnaires d'événements pour les boutons
        eventHandlers.setupEventHandlers();

        // Chargement des tâches depuis le fichier JSON
        Json.loadTasksFromFile(listModel);

        // Configuration de la mise en page et affichage de la fenêtre
        getContentPane().add(new TaskManagerPanel(addButton, CategoryFiltre,removeButton, userInputField, taskList, CategoryFiltreButton, PriorityFiltreButton, PriorityFiltre, edittask, addCategory)); // Ajouter le panneau principal à la fenêtre
        setSize(600, 400); // Définir la taille de la fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran


    }
}
