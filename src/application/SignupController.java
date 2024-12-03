package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    // Handle signup
    public void handleSignup(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate input
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "Signup Failed", "Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Signup Failed", "Passwords do not match.");
            return;
        }

        // Simulating signup success - In real apps, you would save to the database or perform further validation
        System.out.println("Sign up successful with username: " + username);

        // Show success message
        showAlert(AlertType.INFORMATION, "Signup Successful", "Welcome, " + username);

        // After successful signup, navigate to the Home page (main page of the app)
        navigateToHomePage();
    }

    // Show alert message
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigate to the home page (Easy Split app's main page)
    private void navigateToHomePage() throws IOException {
        // Load the home page (assuming you have a home.fxml file for the main screen)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard.fxml"));
        Parent homeRoot = loader.load(); // Load the home page

        // Get the current stage (window) and set the scene to the home page
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene homeScene = new Scene(homeRoot);

        // Set the new scene (home page) and show it
        stage.setScene(homeScene);
        stage.setTitle("Easy Split - Home");
        stage.show();
    }

    // Navigate back to login screen (if you have a button to go back)
    public void goToLogin(ActionEvent event) throws IOException {
        // Load the login page (assuming login.fxml is available)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent loginRoot = loader.load(); // Load the login page

        // Get the current stage and set the scene to the login page
        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene loginScene = new Scene(loginRoot);

        // Set the new scene (login page) and show it
        stage.setScene(loginScene);
        stage.setTitle("Easy Split - Login");
        stage.show();
    }
}