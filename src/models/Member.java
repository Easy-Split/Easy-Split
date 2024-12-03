package models;

public class Member {
    private String name;
    private String email;
    private double balance; // How much the member owes or is owed

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
        this.balance = 0; // Default balance is 0
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount; // This can be positive (credit) or negative (debt)
    }
    
    
    public String toString(Member member) {
        return member != null ? member.getName() : "";  // Display the name of the member
    }
}
