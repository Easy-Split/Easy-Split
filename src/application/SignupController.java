package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utils.SQLConnection;

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    // Handle signup
    public void handleSignup(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate input
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Signup Failed", "Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Signup Failed", "Passwords do not match.");
            return;
        }

        // Save user to database
        if (saveUserToDatabase(username, email, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Signup Successful", "Welcome, " + username);
            goToLogin(); // Navigate to login page
        } else {
            showAlert(Alert.AlertType.ERROR, "Signup Failed", "An error occurred while saving your information.");
        }
    }

    // Save user to the database
    private boolean saveUserToDatabase(String username, String email, String password) {
        String query = "INSERT INTO Users (username, password, email, createdAt) VALUES (?, HASHBYTES('SHA2_256', ?), ?, GETDATE())";
        try (Connection connection = SQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password); // Plaintext password, hashed by DB
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Navigate to login screen
    public void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/login.fxml"));
            Parent loginRoot = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);

            stage.setScene(loginScene);
            stage.setTitle("Easy Split - Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to navigate to the login page.");
        }
    }

    // Show alert message
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
