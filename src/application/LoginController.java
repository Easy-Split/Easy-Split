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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.SQLConnection;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    // Handle login
    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Please enter your username and password.");
            return;
        }

        // Authenticate user
        if (authenticateUser(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome back, " + username);
            navigateToDashboard(); // Navigate to the dashboard
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }
    
    private void handleLoginSuccess(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();

            // Pass UserID to the DashboardController
            DashboardController dashboardController = loader.getController();
            dashboardController.setCurrentUserId(userId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Authenticate user credentials with the database
    private boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = HASHBYTES('SHA2_256', ?)";
        try (Connection connection = SQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password); // Plaintext password hashed by DB
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if a matching record is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Navigate to the dashboard
    private void navigateToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/dashboard.fxml"));
            Parent dashboardRoot = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene dashboardScene = new Scene(dashboardRoot);

            stage.setScene(dashboardScene);
            stage.setTitle("Easy Split - Dashboard");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open the dashboard.");
        }
    }

    // Navigate to signup screen
    public void goToSignup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/signup.fxml"));
            Parent signupRoot = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene signupScene = new Scene(signupRoot);

            stage.setScene(signupScene);
            stage.setTitle("Easy Split - Signup");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to navigate to the signup page.");
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

