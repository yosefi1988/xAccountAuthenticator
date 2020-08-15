package ir.sajjadyosefi.android.xTubeless.classes.model;

/**
 * Created by sajjad on 1/20/2018.
 */

public class Category {
    private int ID;
    private int HeaderID;
    private String Name;
    private String Statement;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getHeaderID() {
        return HeaderID;
    }

    public void setHeaderID(int headerID) {
        HeaderID = headerID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatement() {
        return Statement;
    }

    public void setStatement(String statement) {
        Statement = statement;
    }


}
