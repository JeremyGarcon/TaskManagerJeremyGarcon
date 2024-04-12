public class Main {
    public static void main(String[] args) {
        // Vérifier si le fichier JSON existe
       if (JsonVariable.JsonAccount.exists()) {
            // Tenter de supprimer le fichier JSON
            if (JsonVariable.JsonAccount.delete()) {
                System.out.println("Fichier JSON supprimé avec succès.");
            } else {
                System.out.println("La suppression du fichier JSON a échoué.");
            }
        }
        
        // Afficher le formulaire de connexion
        Account loginForm = new Account();
        loginForm.setVisible(true);
    }
}
