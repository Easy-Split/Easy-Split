package application;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    // In-memory user data (for simplicity)
    private static final String VALID_USERNAME = "user";
    private static final String VALID_PASSWORD = "password";

    // Handle login
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            // Successful login, transition to main app
            showAlert(AlertType.INFORMATION, "Login Successful", "Welcome back, " + username);
            // You can launch the main dashboard here, like open a new window
            // For now, just close the login window:
            navigateToDashboard(); // Redirects to the dashboard
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }
    
 // Navigate to Dashboard
    private void navigateToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard.fxml"));
            Parent dashboardRoot = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene dashboardScene = new Scene(dashboardRoot);
            stage.setScene(dashboardScene);
            stage.setTitle("Easy Split - Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Navigate to signup screen
    public void goToSignup(ActionEvent event) throws IOException {
        // Implement logic to show the signup screen (e.g., load signup.fxml)
        System.out.println("Navigating to Signup screen...");
        
        // You can transition to the signup scene here:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/signup.fxml"));
        Parent signupRoot = loader.load();


        // Get the current stage and set the scene to the login page
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene signupScene = new Scene(signupRoot);

        // Set the new scene (login page) and show it
        stage.setScene(signupScene);
        stage.setTitle("Easy Split - Signup");
        stage.show();
    }

    // Show alert message
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}