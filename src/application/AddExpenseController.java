package application;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddExpenseController {
    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<String> paidByDropdown;

    @FXML
    private ComboBox<String> splitTypeDropdown;

    @FXML
    private void handleSubmitExpense() {
        double amount = Double.parseDouble(amountField.getText());
        String paidBy = paidByDropdown.getValue();
        String splitType = splitTypeDropdown.getValue();

        // Create and add expense to the group based on splitType
    }
}