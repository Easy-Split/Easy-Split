package models;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private String groupName;
    private List<Member> members;  // List of members in the group
    private List<Expense> expenses; // List of expenses in the group
    private double balance; 

    // Constructor
    public Group(String groupName) {
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    // Getters and Setters
    public String getGroupName() {
        return groupName;
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    // Add a member to the group
    public void addMember(Member member) {
        members.add(member);
    }

    // Add an expense to the group
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

	public double getBalance(Member member) {
		// TODO Auto-generated method stub
		return balance;
	}
	
	 public boolean removeMember(Member member) {
	        return members.remove(member);
	    }
}
