package models;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
    private String name;
    private List<Member> members;

    public Group(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void addMember(String memberName, String memberEmail) {
        this.members.add(new Member(memberName, memberEmail));
    }
}



