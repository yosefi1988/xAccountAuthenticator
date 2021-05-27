package ir.sajjadyosefi.accountauthenticator.model;

public class Store {
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isFree() {
        return IsFree;
    }

    public void setFree(boolean free) {
        IsFree = free;
    }

    private int Id;
    private String Name;
    private boolean IsFree;
}
