package models;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private List<Member> members;
    private List<Expense> expenses;

    public Group(String name) {
        this.name = name;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void addMember(String name, String email) {
        members.add(new Member(name, email));
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        // Update balances when expense is added
        double splitAmount = expense.getAmount() / expense.getSplitMembers().size();
        for (Member member : expense.getSplitMembers()) {
            if (!member.equals(expense.getPaidBy())) {
                member.updateBalance(splitAmount); // Update balance of members
            }
        }
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
