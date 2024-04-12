import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.jdesktop.swingx.JXDatePicker;
import java.util.Date;

public class CreateTasks extends JDialog {

    // Variables pour stocker les valeurs sélectionnées
    private static String selectedPriority;
    private static String selectedCategory;
    private static Date selectedDate;

    public CreateTasks(JFrame parent) {
        super(parent, "Create the Task", true); // Crée une fenêtre modale avec un titre
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Ferme la fenêtre sans fermer l'application
        setSize(300, 250); // Définit la taille de la fenêtre
        setLocationRelativeTo(parent); // Centre la fenêtre par rapport à la fenêtre parent
        ImageIcon icon = new ImageIcon("Images/Icone.jpg"); // Crée une icône pour la fenêtre
        setIconImage(icon.getImage()); // Définit l'icône de la fenêtre

        // ComboBox pour la priorité
        JComboBox<String> priorityComboBox = new JComboBox<>(Priority.priorityOptions);
        priorityComboBox.setSelectedIndex(0);

        // ComboBox pour la catégorie
        JComboBox<String> categoryComboBox = new JComboBox<>(Category.categoryOptions);
        categoryComboBox.setSelectedIndex(0);

        // Sélecteur de date
        JXDatePicker datePicker = new JXDatePicker();
        datePicker.setDate(new Date()); // Définit la date actuelle comme date sélectionnée par défaut

        // Bouton OK
        JButton okButton = new JButton("OK");
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Récupère les valeurs sélectionnées
                selectedPriority = (String) priorityComboBox.getSelectedItem();
                selectedCategory = (String) categoryComboBox.getSelectedItem();
                selectedDate = datePicker.getDate();
                dispose(); // Ferme la fenêtre
            }
        });

        // Crée un panneau avec une grille de 4 lignes et 2 colonnes pour organiser les composants
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Priority:")); // Ajoute un label pour la priorité
        panel.add(priorityComboBox); // Ajoute la ComboBox pour la priorité
        panel.add(new JLabel("Category:")); // Ajoute un label pour la catégorie
        panel.add(categoryComboBox); // Ajoute la ComboBox pour la catégorie
        panel.add(new JLabel("Dead Time:")); // Ajoute un label pour la date
        panel.add(datePicker); // Ajoute le sélecteur de date
        panel.add(okButton); // Ajoute le bouton OK

        getContentPane().add(panel, BorderLayout.CENTER); // Ajoute le panneau au contenu de la fenêtre
    }

    // Méthode pour récupérer la priorité sélectionnée
    public static String getSelectedPriority() {
        return selectedPriority;
    }

    // Méthode pour récupérer la catégorie sélectionnée
    public static String getSelectedCategory() {
        return selectedCategory;
    }

    // Méthode pour récupérer la date sélectionnée
    public static Date getSelectedDate() {
        return selectedDate;
    }
}
