package models;

public class Member {
    private String name;
    private String email;
    private double sharePercentage;

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member member = (Member) obj;
        return name != null ? name.equals(member.name) : member.name == null;
    }

    public double getSharePercentage() {
        return sharePercentage;
    }

    public void setSharePercentage(double sharePercentage) {
        this.sharePercentage = sharePercentage;
    }
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
