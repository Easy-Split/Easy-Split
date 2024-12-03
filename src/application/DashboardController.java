package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
        Text groupNameText = new Text(group.getGroupName());
        
        // Add a button to manage the group (e.g., Edit, Delete, etc.)
        Button manageButton = new Button("Add Expense");
        manageButton.setOnAction(event -> {
        	openAddExpensePopup(group);
        });
        
     // Add a button to view group details (expenses and balances)
        Button viewDetailsButton = new Button("View Group Details");
        viewDetailsButton.setOnAction(event -> openGroupDetailsPopup(group));
        
     // Add an Edit button to modify the group
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> openEditGroupPopup(group, groupBox));

        // Add a Delete button to remove the group
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> deleteGroup(group));
        
        // Add the text and button to the HBox
        if(groupNameText != null ) {
        groupBox.getChildren().addAll(groupNameText, manageButton, viewDetailsButton, editButton,deleteButton );
        
        // Add the HBox to the VBox
        groupVBox.getChildren().add(groupBox);
        }
    }

    
    private void openEditGroupPopup(Group group, HBox groupBox) {
        try {
            // Load the Edit Group Popup FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/editGroup.fxml"));
            Parent root = loader.load();

            // Get the controller for the Edit Group form
            EditGroupPopupController popupController = loader.getController();
            popupController.initialize(group); // Pass the group to the popup

            // Show the popup in a new window
            Stage stage = new Stage();
            stage.setTitle("Edit Group");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Make it modal
            stage.showAndWait();

            // After the popup is closed, refresh the dashboard if the group was modified
            for (Node node : groupBox.getChildren()) {
                if (node instanceof Text) {
                    ((Text) node).setText(group.getGroupName()); // Update group name
                    break;
                }
            }        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void deleteGroup(Group group) {
        // Show a confirmation dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Group");
        alert.setHeaderText("Are you sure you want to delete this group?");
        alert.setContentText("This action cannot be undone.");

        // If the user confirms, remove the group
        if (alert.showAndWait().get() == ButtonType.OK) {
            groups.remove(group); // Remove from the list
            groupVBox.getChildren().clear(); // Clear the current UI
            initialize(); // Refresh the dashboard
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
    
 // Open Group Details Popup
    private void openGroupDetailsPopup(Group group) {
        try {
            // Load the Group Details Popup FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/groupDetailsPopup.fxml"));
            Parent root = loader.load();
            
            // Get the controller for the Group Details Popup
            GroupDetailsPopupController popupController = loader.getController();
            popupController.initialize(group); 

            // Show the popup in a new window
            Stage stage = new Stage();
            stage.setTitle("Group Details");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Make it modal
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    
    

