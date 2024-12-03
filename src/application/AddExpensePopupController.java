package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Group;
import models.Expense;
import models.Member;

import java.util.ArrayList;
import java.util.List;

public class AddExpensePopupController {

    @FXML
    private TextField expenseNameField; // Text field for the expense name
    
    @FXML
    private ComboBox<Member> payerComboBox; // ComboBox for selecting the payer
    
    @FXML
    private DatePicker expenseDatePicker; // Date picker for the expense date
    
    @FXML
    private TextField amountField; // Text field for the amount of money paid
    
    @FXML
    private VBox membersVBox; // VBox for displaying members with checkboxes
    
    @FXML
    private Button submitButton; // Button for submitting the expense

    private Group group; // The selected group where the expense will be added
    
    // Initialize the controller with the selected group
    public void initialize(Group group) {
        this.group = group;
        
        payerComboBox.getItems().clear();  // Clear any existing items

        
        // Populate payer ComboBox with the group's members
        payerComboBox.getItems().addAll(group.getMembers());
     // Set a custom StringConverter to display the member names in the ComboBox
        payerComboBox.setConverter(new StringConverter<Member>() {
            @Override
            public String toString(Member member) {
                return member != null ? member.getName() : "";  // Display the name of the member
            }

            @Override
            public Member fromString(String string) {
                // We don't need this, as the ComboBox already stores the member object
                return null;
            }
        });
        
        
        // Add checkboxes for each group member to the Split By section
        for (Member member : group.getMembers()) {
            HBox memberHBox = new HBox(10);
            CheckBox memberCheckBox = new CheckBox(member.getName());
            memberCheckBox.setUserData(member); // Store member object in the checkbox
            
            // Ensure that the payer can't be selected as part of the split
            if (payerComboBox.getValue() != null && payerComboBox.getValue().equals(member)) {
                memberCheckBox.setDisable(true);
            }
            
            memberHBox.getChildren().add(memberCheckBox);
            membersVBox.getChildren().add(memberHBox);
        }
    }

    // Handle the submit expense action
    @FXML
    public void handleSubmitExpense() {
        String expenseName = expenseNameField.getText();
        Member payer = payerComboBox.getValue();
        String amountText = amountField.getText();
        String dateText = expenseDatePicker.getValue() != null ? expenseDatePicker.getValue().toString() : "";

        // Validate the form fields
        if (expenseName.isEmpty()) {
            showAlert("Error", "Expense name cannot be empty.", AlertType.ERROR);
            return;
        }

        if (payer == null) {
            showAlert("Error", "Please select who paid for the expense.", AlertType.ERROR);
            return;
        }

        if (amountText.isEmpty()) {
            showAlert("Error", "Amount cannot be empty.", AlertType.ERROR);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid amount.", AlertType.ERROR);
            return;
        }

        // Collect selected members to split the cost
        List<Member> membersToSplit = getSelectedMembersForSplit();

        if (membersToSplit.isEmpty()) {
            showAlert("Error", "Please select members to split the expense with.", AlertType.ERROR);
            return;
        }

        // Create a new expense object using the constructor
        Expense expense = new Expense(expenseName, payer, amount, dateText, membersToSplit);

        // Add the expense to the group
        group.addExpense(expense);

        // Show a success message and close the popup
        showAlert("Success", "Expense added successfully!", AlertType.INFORMATION);
        closeWindow();
    }

    // Helper method to get selected members for splitting the expense
    private List<Member> getSelectedMembersForSplit() {
        List<Member> selectedMembers = new ArrayList<>();

        // Iterate over the children of membersVBox
        for (Node node : membersVBox.getChildren()) {
            if (node instanceof HBox) { // Ensure the node is a VBox
                HBox child = (HBox) node;

                // Get the first child of the VBox, assuming it's a CheckBox
                if (!child.getChildren().isEmpty() && child.getChildren().get(0) instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) child.getChildren().get(0);

                    // Check if the CheckBox is selected
                    if (checkBox.isSelected()) {
                        // Retrieve the associated Member object
                        if (checkBox.getUserData() instanceof Member) {
                            selectedMembers.add((Member) checkBox.getUserData());
                        }
                    }
                }
            }
        }

        
        return selectedMembers;
    }

    // Helper method to show alerts
    private void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Close the window after submitting the expense
    private void closeWindow() {
        // Close the current window (this is the popup)
        //submitButton.getScene().getWindow().hide();
        
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}
