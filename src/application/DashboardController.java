package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Expense;
import models.Group;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashboardController {

    @FXML
    private Button addGroupButton;
    
    @FXML
    private VBox groupVBox; // VBox to hold the groups, no longer static
    
    private List<Group> groups = new ArrayList<>(); // Initialize groups list

    @FXML
    private void initialize() {
        // Initialize the dashboard with the existing groups (if any)
        if (groups != null) {
            for (Group group : groups) {
                addGroupToDashboard(group);
            }
        }
    }
    
    

    @FXML
    private void handleAddGroup() {
        try {
            // Load the Add Group Popup FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/addGroup.fxml"));
            Parent root = loader.load();

            // Show the popup in a new window
            Stage stage = new Stage();
            stage.setTitle("Add Group");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Make the window modal (blocking)
            stage.showAndWait();
            
            AddGroupPopupController popupController = loader.getController();
            Group newGroup = popupController.handleSubmitGroup(); // Assume the popup controller returns a new group
            if (newGroup != null) {
                groups.add(newGroup); // Add to the list
                addGroupToDashboard(newGroup); // Dynamically add the group to the dashboard
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to create a custom HBox for each group and add it to the VBox
    private void addGroupToDashboard(Group group) {
        // Create a new HBox to represent the group
        HBox groupBox = new HBox(10); // 10px spacing between elements
        
        // Add the group's name
        Text groupNameText = new Text(group.getName());
        
        // Add a button to manage the group (e.g., Edit, Delete, etc.)
        Button manageButton = new Button("Add Expense");
        manageButton.setOnAction(event -> {
        	openAddExpensePopup(group);
        });
        
        // Add the text and button to the HBox
        if(groupNameText != null ) {
        groupBox.getChildren().addAll(groupNameText, manageButton);
        
        // Add the HBox to the VBox
        groupVBox.getChildren().add(groupBox);
        }
    }

    
    private void openAddExpensePopup(Group group) {
        try {
            // Load the Add Expense Popup FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/expenseForm.fxml"));
            Parent root = loader.load();
            
            // Get the controller for the Add Expense form
            AddExpensePopupController popupController = loader.getController();
            popupController.initialize(group); 

            // Show the popup in a new window
            Stage stage = new Stage();
            stage.setTitle("Add Expense");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Make it modal
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
