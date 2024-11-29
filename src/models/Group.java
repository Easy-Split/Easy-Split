package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Group {
    private int groupId;           // Unique identifier for the group
    private String groupName;         // Name of the group
    private List<User> members;       // List of users in the group
    private List<Expense> expenses;   // List of expenses in the group
    protected int peopleCount;

    // Constructor to initialize group with ID, number of people, and group name
    public Group(String groupName, int peopleCount) {
        this.groupId++;
        this.peopleCount=peopleCount;
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    // Add a member to the group
    public void addMember(User user) {
        members.add(user);
    }

    // Remove a member from the group
    public void removeMember(User user) {
        members.remove(user);
    }

    // Add an expense to the group and update balances of the users
    public void addExpense(Expense expense) {
        expenses.add(expense);
        Map<User, Double> splits = expense.calculateSplit();

        // Update the balance of each user based on the expense split
        for (User user : splits.keySet()) {
            double amount = splits.get(user);
            if (user == expense.paidBy) {
                user.updateBalance(amount - expense.amount);  // Paid amount deducted
            } else {
                user.updateBalance(-amount);  // Amount to pay for the user
            }
        }
    }

    // Get the members of the group
    public List<User> getMembers() {
        return members;
    }

    // Get the expenses of the group
    public List<Expense> getExpenses() {
        return expenses;
    }

    // Get the name of the group
    public String getGroupName() {
        return groupName;
    }

    // Get the total expense incurred by the group
    public double getTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    // Get the share of each person (total expense / number of members)
    public double getSharePerPerson() {
        if (members.isEmpty()) {
            return 0;
        }
        return getTotalExpenses() / members.size();
    }

    // Get the list of members as a string for display purposes
    public String getMemberNames() {
        StringBuilder memberNames = new StringBuilder();
        for (User member : members) {
            memberNames.append(member.getName()).append(", ");
        }
        return memberNames.length() > 0 ? memberNames.substring(0, memberNames.length() - 2) : "";
    }
    
    public int getPeopleCount() {
    	return peopleCount;
    }
}
