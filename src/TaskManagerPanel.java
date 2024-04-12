import javax.swing.*;
import java.awt.*;

public class TaskManagerPanel extends JPanel {

    public TaskManagerPanel(JButton addButton, JLabel CategoryFiltre, JButton removeButton, JTextField userInputField, JList<Task> taskList, 
                JComboBox categoryFiltreButton, JComboBox priorityFiltreButton, JLabel priorityFiltre, JButton edittask, 
                JButton addCategory) {


        // Configuration du layout principal et de la couleur de fond
        setLayout(new BorderLayout());

        // Panel pour l'ajout de nouvelles tâches et les filtres
        JPanel inputAndFilterPanel = new JPanel(new BorderLayout());

        // Panel pour l'ajout de nouvelles tâches
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(userInputField, BorderLayout.CENTER); // Champ de texte pour l'entrée utilisateur
        inputPanel.add(addButton, BorderLayout.EAST); // Bouton pour ajouter une nouvelle tâche
        inputAndFilterPanel.add(inputPanel, BorderLayout.NORTH); // Ajout du panneau d'entrée en haut

        // Panel pour les boutons de catégorie et de priorité
        JPanel categoryPriorityPanel = new JPanel(new GridLayout(1, 2));
        categoryPriorityPanel.add(categoryFiltreButton); // Combobox pour filtrer les catégories
        categoryPriorityPanel.add(priorityFiltreButton); // Combobox pour filtrer par priorité
        inputAndFilterPanel.add(categoryPriorityPanel, BorderLayout.SOUTH); // Ajout des boutons de catégorie et de priorité en bas

        // Panel pour la liste de tâches
        JPanel taskListPanel = new JPanel(new BorderLayout());
        taskListPanel.add(new JScrollPane(taskList), BorderLayout.CENTER); // Ajout de la liste de tâches avec défilement

        // Panel pour les boutons d'action
        JPanel actionButtonPanel = new JPanel(new GridLayout(1, 3));
        actionButtonPanel.add(removeButton); // Bouton pour supprimer les tâches sélectionnées
        actionButtonPanel.add(addCategory); // Bouton pour ajouter une nouvelle catégorie
        actionButtonPanel.add(edittask);    // boutton pour Edit une task, (pas encore finis)

        // Ajout des panneaux principaux à la fenêtre principale
        add(inputAndFilterPanel, BorderLayout.NORTH); // Ajout du panneau avec les boutons de création de tâche et de filtres en haut
        add(taskListPanel, BorderLayout.CENTER); // Ajout de la liste de tâches au centre
        add(actionButtonPanel, BorderLayout.SOUTH); // Ajout des boutons d'action en bas

        // Personnalisation du rendu des cellules dans la JList pour centrer le texte
        taskList.setCellRenderer(new TaskListCellRenderer());
    }

    // Classe interne pour le rendu personnalisé des tâches dans la JList
    private static class TaskListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Task) {
                Task task = (Task) value;
                String emoji = getEmojiForPriority(task.getPriority());
                setText(emoji + " " + task.getName());
                setHorizontalAlignment(SwingConstants.CENTER);
            }
            return this;
        }

        // Méthode pour obtenir l'emoji en fonction de la priorité
        private String getEmojiForPriority(String priority) {
            if (priority.equals("High")) {
                return "❌"; // Priorité élevée
            } else if (priority.equals("Medium")) {
                return "⚠️"; // Priorité moyenne
            } else if (priority.equals("Low")) {
                return "✅"; // Priorité faible
            } else {
                return ""; // Par défaut, pas d'emoji
            }
        }
    }
}
