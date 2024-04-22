package ir.sajjadyosefi.accountauthenticator.model;

public class AStore {
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

    public AStorePermissions getPermissions() {
        return Permissions;
    }

    public void setPermissions(AStorePermissions permissions) {
        Permissions = permissions;
    }

    private int Id;
    private String Name;
    private boolean IsFree;
    private AStorePermissions Permissions;
}
