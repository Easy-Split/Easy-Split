package application;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Group;
import models.Member;
import models.MemberBalance;
import models.Expense;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

import java.util.HashMap;
import java.util.Map;

public class GroupDetailsPopupController {

    @FXML
    private TableView<Expense> expensesTable;  // Table to display the expenses
    @FXML
    private TableColumn<Expense, String> expenseNameColumn;
    @FXML
    private TableColumn<Expense, Double> amountColumn;
    @FXML
    private TableColumn<Expense, String> membersColumn;
    @FXML
    private TableColumn<Expense, String> dateColumn;  // New column for the date

    @FXML
    private TableView<MemberBalance> balancesTable;  // Table to display balances
    @FXML
    private TableColumn<MemberBalance, String> memberColumn;
    @FXML
    private TableColumn<MemberBalance, Double> balanceColumn;

    @FXML
    private Button closeButton;

    private Group group;

    public void initialize(Group group) {
        this.group = group;

        // Set up the Expenses Table
        expenseNameColumn.setCellValueFactory(cellData -> cellData.getValue().getExpenseNameProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().getAmountProperty().asObject());
        membersColumn.setCellValueFactory(cellData -> cellData.getValue().getMembersInvolvedProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());  // Binding for date

        // Populate the expenses table with data
        expensesTable.getItems().setAll(group.getExpenses());

        // Set up the Balances Table
        memberColumn.setCellValueFactory(cellData -> cellData.getValue().getMemberNameProperty());
        balanceColumn.setCellValueFactory(cellData -> cellData.getValue().getBalanceProperty().asObject());

        // Populate balances for each member
        Map<Member, Double> balances = calculateBalances();
        for (Map.Entry<Member, Double> entry : balances.entrySet()) {
            balancesTable.getItems().add(new MemberBalance(entry.getKey(), entry.getValue()));
        }
    }

    // Calculate balance for each member
    private Map<Member, Double> calculateBalances() {
        Map<Member, Double> balances = new HashMap<>();
        
        // Initialize all members with 0 balance
        for (Member member : group.getMembers()) {
            balances.put(member, 0.0);
        }

        // Calculate the balances
        for (Expense expense : group.getExpenses()) {
            Map<Member, Double> splits = expense.getSplits();
            for (Map.Entry<Member, Double> entry : splits.entrySet()) {
                Member member = entry.getKey();
                double share = entry.getValue();
                if (expense.getPayer().equals(member)) {
                    balances.put(member, balances.get(member) - share);  // They paid, so they are owed money
                } else {
                    balances.put(member, balances.get(member) + share);  // They owe money
                }
            }
        }

        return balances;
    }

    // Close the window after displaying the group details
    @FXML
    public void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
