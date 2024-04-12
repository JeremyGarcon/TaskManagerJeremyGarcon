import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;
import java.awt.*;
import java.awt.event.*;

public class EditTask extends JDialog {

    private JTextField nameTextField;
    private JLabel dateCreatedTextField;
    private JLabel timeCreatedTextField;
    private JComboBox<String> priorityComboBox;
    private JComboBox<String> categoryComboBox;
    private JTextArea descriptionTextArea;
    private JXDatePicker deadline;
    private Task task;

    public EditTask(JFrame parent, Task task) {
        super(parent, "Edit Task", true);
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        this.task = task;

        ImageIcon icon = new ImageIcon("Images/Icone.jpg");
        setIconImage(icon.getImage());

        nameTextField = new JTextField(task.getName());
        dateCreatedTextField = new JLabel(task.getDate_created());
        timeCreatedTextField = new JLabel(task.getTime_created());
        priorityComboBox = new JComboBox<>(Priority.priorityOptions);
        priorityComboBox.setSelectedItem(task.getPriority());
        categoryComboBox = new JComboBox<>(Category.categoryEditTask);
        categoryComboBox.setSelectedItem(task.getCategory());
        descriptionTextArea = new JTextArea(task.getDescription(), 8, 30);
        deadline = new JXDatePicker();

        JButton saveButton = new JButton("Save Task");
        JButton cancelButton = new JButton("Cancel");

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(7, 2));
        infoPanel.add(new JLabel("Task Name:"));
        infoPanel.add(nameTextField);
        infoPanel.add(new JLabel("Date Created:"));
        infoPanel.add(dateCreatedTextField);
        infoPanel.add(new JLabel("Time Created:"));
        infoPanel.add(timeCreatedTextField);
        infoPanel.add(new JLabel("Priority:"));
        infoPanel.add(priorityComboBox);
        infoPanel.add(new JLabel("Category:"));
        infoPanel.add(categoryComboBox);
        infoPanel.add(new JLabel("Description:"));
        infoPanel.add(new JScrollPane(descriptionTextArea));
        infoPanel.add(new JLabel("Deadline:"));
        infoPanel.add(deadline);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(infoPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String lastNameTask = task.getName();
                task.setName(nameTextField.getText());
                task.setDate_created(dateCreatedTextField.getText());
                task.setPriority((String) priorityComboBox.getSelectedItem());
                task.setCategory((String) categoryComboBox.getSelectedItem());
                task.setDescription(descriptionTextArea.getText());
        
                // Vérifier si la date de la deadline est sélectionnée
                if (deadline.getDate() != null) {
                    task.setDeadline(deadline.getDate().toString());
                } else {
                    task.setDeadline(""); // Ou une valeur par défaut si aucune date n'est sélectionnée
                }
        
                try {
                    Json.updateTask(task, lastNameTask);
                    Json.refreshTaskList();
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EditTask.this, "An error occurred while saving the task.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                if(JsonVariable.Acount == true) {
                    DecrypteJson.decrypteJson(JsonVariable.UrlJsonAccount);
                    UserUpdateJsonSQL.updateUserJson(User.getUsername(), User.getPassword());
                    CrypteJson.crypteJson(JsonVariable.UrlJsonAccount);
                }
            }
        });
    
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
