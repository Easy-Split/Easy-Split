package models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Expense {
    private String description;
    protected double amount;
    private Date date;
    protected User paidBy;  // The user who paid for this expense

    public Expense(String description, double amount, Date date, User paidBy) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.paidBy = paidBy;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public User getPaidBy() {
        return paidBy;
    }

    // Calculate how to split the expense among users
    public Map<User, Double> calculateSplit() {
        Map<User, Double> splits = new HashMap<>();
        // Assuming the expense is split equally among all members of the group
        double share = amount / paidBy.getGroup().getMembers().size();

        for (User member : paidBy.getGroup().getMembers()) {
            splits.put(member, share);
        }

        return splits;
    }
    
}
