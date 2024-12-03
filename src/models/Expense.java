package models;

import java.util.List;

public class Expense {

    private String expenseName;
    private double amount;
    private String paidBy;  // Simplified to string for now
    private List<String> members; // List of members' names
    private double amountPerPerson;
    private Group group;  // Link to the group this expense belongs to

    // Constructor to initialize all fields
    public Expense(String expenseName, double amount, String paidBy, List<String> members, Group group) {
        this.expenseName = expenseName;
        this.amount = amount;
        this.paidBy = paidBy;
        this.members = members;
        this.group = group;
        this.amountPerPerson = calculateAmountPerPerson();
    }

    // Method to calculate the amount each member has to pay
    private double calculateAmountPerPerson() {
        if (members != null && !members.isEmpty()) {
            return amount / members.size();
        } else {
            return 0.0;
        }
    }

    // Getters and setters
    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        this.amountPerPerson = calculateAmountPerPerson();  // Recalculate if the amount changes
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
        this.amountPerPerson = calculateAmountPerPerson();  // Recalculate if members list changes
    }

    public double getAmountPerPerson() {
        return amountPerPerson;
    }

    public void setAmountPerPerson(double amountPerPerson) {
        this.amountPerPerson = amountPerPerson;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    // Display information about the expense
    @Override
    public String toString() {
        return "Expense Name: " + expenseName + "\n" +
               "Amount: " + amount + "\n" +
               "Paid By: " + paidBy + "\n" +
               "Amount Per Person: " + amountPerPerson + "\n" +
               "Group: " + (group != null ? group.getName() : "No group");
    }
}
