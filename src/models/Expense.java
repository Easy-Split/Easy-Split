package models;

import java.util.List;

public class Expense {
    private String name;
    private Member paidBy;
    private double amount;
    private String date;
    private List<Member> splitMembers;

    public Expense(String name, Member paidBy, double amount, String date, List<Member> splitMembers) {
        this.name = name;
        this.paidBy = paidBy;
        this.amount = amount;
        this.date = date;
        this.splitMembers = splitMembers;
    }

    public String getName() {
        return name;
    }

    public Member getPaidBy() {
        return paidBy;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public List<Member> getSplitMembers() {
        return splitMembers;
    }
}
