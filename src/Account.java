import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Account extends JFrame {
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton LoginButton;
    private JButton noAccountButton;
    private JButton goBackButton;
    private JLabel text;

    public Account() {

        setTitle("Login Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran

        JPanel panel = new JPanel(new BorderLayout());

        text = new JLabel("<html><div style='text-align: center;'>Welcome to the Login Form<br>Ce connecter et/ou crée un compte fait que vos données seront sauvegardées dans une base de données SQL avec des fichiers JSON.<br>Le No Account signifie que vos données resteront strictement en local.</div></html>", SwingConstants.CENTER);
        loginButton = createStyledButton("Login", Color.decode("#0066CC"));
        createAccountButton = createStyledButton("Create Account", Color.decode("#33CC33"));
        noAccountButton = createStyledButton("No Account", Color.decode("#FF0000"));

        // Gestionnaire d'événements pour le bouton "Login"
        loginButton.addActionListener(e -> showLoginPage("login"));

        // Gestionnaire d'événements pour le bouton "Create Account"
        createAccountButton.addActionListener(e -> showLoginPage("createAccount"));

        // Gestionnaire d'événements pour le bouton "No Account"
        noAccountButton.addActionListener(e -> showLoginPage("No Account"));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(loginButton);
        buttonPanel.add(createAccountButton);
        buttonPanel.add(noAccountButton);
        panel.add(text, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);

        setVisible(true);
    }

    // Méthode pour créer et styliser les boutons
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    // Méthode pour afficher la page correspondante
    private void showLoginPage(String page) {
        
        // Supprimer les boutons existants
        getContentPane().removeAll();

        if (page.equals("login")) {

            JTextField usernameField;
            JPasswordField passwordField;

            usernameField = new JTextField();
            passwordField = new JPasswordField();

            setSize(300, 200);
            setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
            JPanel panel = new JPanel(new BorderLayout());
            JPanel loginPanel = new JPanel(new GridLayout(2, 2));
            loginPanel.add(new JLabel("Username:"));
            loginPanel.add(usernameField);
            loginPanel.add(new JLabel("Password:"));
            loginPanel.add(passwordField);
            panel.add(loginPanel, BorderLayout.CENTER);
            panel.add(new JLabel("Login Page", SwingConstants.CENTER), BorderLayout.NORTH);
            goBackButton = createStyledButton("Go Back", Color.GRAY);
            LoginButton = createStyledButton("Login", Color.decode("#0066CC"));
            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
            buttonPanel.add(goBackButton);
            buttonPanel.add(LoginButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);
            add(panel);

            LoginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Code pour se connecter
                            String username = usernameField.getText();
                            String password = new String(passwordField.getPassword());

                            if (username.isEmpty() || password.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                if (UserLoginSQL.loginUser(username, password) == true) {
                                    dispose();
                                    User User = new User(username, password, null);
                                    JsonVariable.Acount = true;
                                    String ContentJsonUser = UserLoadJsonSQL.loadUserJson(User.getUsername(), User.getPassword());
                                    JsonVariable.JsonUser = JsonVariable.JsonAccount;
                                    JsonVariable.UrlJsonUser = JsonVariable.UrlJsonAccount;
                                    Json.createJson(JsonVariable.JsonUser, ContentJsonUser);
                                    CrypteJson.crypteJson(JsonVariable.UrlJsonUser);
                                    System.err.println("ContentJsonUser: " + ContentJsonUser);
                                    JsonVariable.JsonUser = JsonVariable.JsonAccount;
                                    JsonVariable.UrlJsonUser = JsonVariable.UrlJsonAccount;
                                    TaskManager taskManager = new TaskManager();
                                    taskManager.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Incorrect username or password", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }); 

        } else if (page.equals("createAccount")) {

             // Déclaration des champs de texte en tant que variables de classe
                JTextField usernameField;
                JPasswordField passwordField;
                JPasswordField confirmPasswordField;
                JTextField emailField;

                // Initialisation des champs de texte
                usernameField = new JTextField();
                passwordField = new JPasswordField();
                confirmPasswordField = new JPasswordField();
                emailField = new JTextField();

                setSize(300, 200);
                setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
                JPanel panel = new JPanel(new BorderLayout());
                JPanel formPanel = new JPanel(new GridLayout(5, 2));
                formPanel.add(new JLabel("Username:"));
                formPanel.add(usernameField);
                formPanel.add(new JLabel("Password:"));
                formPanel.add(passwordField);
                formPanel.add(new JLabel("Confirm Password:"));
                formPanel.add(confirmPasswordField);
                formPanel.add(new JLabel("Email:"));
                formPanel.add(emailField);
                panel.add(formPanel, BorderLayout.CENTER);
                panel.add(new JLabel("Create Account Page", SwingConstants.CENTER), BorderLayout.NORTH);
                goBackButton = createStyledButton("Go Back", Color.GRAY);
                createAccountButton = createStyledButton("Create Account", Color.decode("#33CC33"));
                JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
                buttonPanel.add(goBackButton);
                buttonPanel.add(createAccountButton);
                panel.add(buttonPanel, BorderLayout.SOUTH);
                add(panel);

            createAccountButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Code pour créer un compte
                   String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    String confirmPassword = new String(confirmPasswordField.getPassword());
                    String email = emailField.getText();

                    if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!password.equals(confirmPassword)) {
                        JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        User User = new User(username, password, email);
                        UserCreateSQL.CreateUser(User);
                        dispose();
                        Account loginForm = new Account();
                        loginForm.setVisible(true);
                        JOptionPane.showMessageDialog(null, "Your Account has been successfully created Please log in");
                    }

                }
            });

        } else if (page.equals("No Account")) {
            if (!Json.verifyJsonFile(JsonVariable.jsonTaskLocal)) {
                System.err.println("Le fichier JSON de tâches n'existe pas. Création d'un nouveau.");
                Json.createEmptyJson(JsonVariable.jsonTaskLocal);
                CrypteJson.crypteJson(JsonVariable.UrlJsonTaskLocal);
            }
            dispose();
            TaskManager taskManager = new TaskManager();
            taskManager.setVisible(true);
        }
        
        goBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Account();
                    }
                });
            }
        });
        revalidate(); // Rafraîchit l'interface utilisateur
        repaint();
    }
}
