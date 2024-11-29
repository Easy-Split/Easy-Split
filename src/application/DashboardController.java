package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import models.Expense;
import models.Group;
import models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DashboardController {

    @FXML private ListView<String> groupList; // ListView to display groups
    @FXML private User paidByDropdown;
    private List<Group> groups = new ArrayList<>(); // Store groups

    // Handle Add Group button click
    @FXML
    public void handleAddGroup(ActionEvent event) {
        // Ask for the group name
        TextInputDialog groupNameDialog = new TextInputDialog();
        groupNameDialog.setTitle("Add Group");
        groupNameDialog.setHeaderText("Enter the name of the new group:");
        groupNameDialog.setContentText("Group Name:");

        Optional<String> groupNameResult = groupNameDialog.showAndWait();
        groupNameResult.ifPresent(groupName -> {
            // Ask for the number of people
            TextInputDialog peopleCountDialog = new TextInputDialog();
            peopleCountDialog.setTitle("Add Group");
            peopleCountDialog.setHeaderText("How many people are in the group?");
            peopleCountDialog.setContentText("Number of People:");

            Optional<String> peopleCountResult = peopleCountDialog.showAndWait();
            peopleCountResult.ifPresent(peopleCountStr -> {
                try {
                    int peopleCount = Integer.parseInt(peopleCountStr);
                    if (peopleCount <= 0) {
                        showAlert(AlertType.ERROR, "Invalid Number of People", "Number of people must be greater than 0.");
                        return;
                    }

                    // Create the new group
                    Group group = new Group(groupName, peopleCount);
                    groups.add(group);
                    groupList.getItems().add(group.getGroupName());
                    showAlert(AlertType.INFORMATION, "Group Added", "The group \"" + groupName + "\" has been added with " + peopleCount + " people.");
                } catch (NumberFormatException e) {
                    showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid number for people.");
                }
            });
        });
    }

    // Handle Add Expense button click
    @FXML
    public void handleAddExpense(ActionEvent event) {
        // Ensure a group is selected
        String selectedGroupName = groupList.getSelectionModel().getSelectedItem();
        if (selectedGroupName == null) {
            showAlert(AlertType.ERROR, "No Group Selected", "Please select a group to add an expense.");
            return;
        }

        Group selectedGroup = getGroupByName(selectedGroupName);

        // Ask for expense details
        TextInputDialog expenseDescriptionDialog = new TextInputDialog();
        expenseDescriptionDialog.setTitle("Add Expense");
        expenseDescriptionDialog.setHeaderText("Enter the expense description:");
        expenseDescriptionDialog.setContentText("Expense Description:");
        expenseDescriptionDialog.setContentText("Enter the user name paid the amount");

        Optional<String> descriptionResult = expenseDescriptionDialog.showAndWait();
        descriptionResult.ifPresent(description -> {
            TextInputDialog expenseAmountDialog = new TextInputDialog();
            expenseAmountDialog.setTitle("Add Expense");
            expenseAmountDialog.setHeaderText("Enter the expense amount:");
            expenseAmountDialog.setContentText("Expense Amount:");

            Optional<String> amountResult = expenseAmountDialog.showAndWait();
            amountResult.ifPresent(amountStr -> {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount <= 0) {
                        showAlert(AlertType.ERROR, "Invalid Amount", "Amount must be greater than 0.");
                        return;
                    }
                     
                    // Create the expense and add it to the group
                    Expense expense = new Expense(description, amount, new Date() ,paidByDropdown);
                    selectedGroup.addExpense(expense);
                    showAlert(AlertType.INFORMATION, "Expense Added", "Expense \"" + description + "\" of amount " + amount + " has been added.");
                } catch (NumberFormatException e) {
                    showAlert(AlertType.ERROR, "Invalid Amount", "Please enter a valid amount.");
                }
            });
        });
    }

    // Handle selecting a group to view details
    @FXML
    public void handleGroupSelection(MouseEvent event) {
        String selectedGroupName = groupList.getSelectionModel().getSelectedItem();
        if (selectedGroupName != null) {
            Group selectedGroup = getGroupByName(selectedGroupName);
            if (selectedGroup != null) {
                showGroupDetails(selectedGroup);
            }
        }
    }

    // Show details for the selected group
    private void showGroupDetails(Group group) {
        StringBuilder details = new StringBuilder();
        details.append("Group: ").append(group.getGroupName()).append("\n");
        details.append("People: ").append(group.getMembers()).append("\n");
        details.append("Total Expenses: ").append(group.getTotalExpenses()).append("\n");
        details.append("Share per Person: ").append(group.getSharePerPerson()).append("\n\n");

        details.append("Expenses:\n");
        for (Expense expense : group.getExpenses()) {
            details.append(expense.toString()).append("\n");
        }

        showAlert(AlertType.INFORMATION, "Group Details", details.toString());
    }

    // Get group by name
    private Group getGroupByName(String groupName) {
        for (Group group : groups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        return null;
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
