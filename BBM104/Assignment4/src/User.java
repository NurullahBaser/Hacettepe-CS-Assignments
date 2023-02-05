public class User {
    private String name;
    private String password;
    private boolean clubMember;
    private boolean admin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isClubMember() {
        return clubMember;
    }

    public void setClubMember(boolean clubMember) {
        this.clubMember = clubMember;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User(String name, String password, boolean isClubMember, boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.clubMember = isClubMember;
        this.admin = isAdmin;
    }
}
