package at3;

import java.util.ArrayList;

public class ActivityStatement {
    String name;
    Integer id;
    ArrayList<String> constraints = new ArrayList<String>();

    public ActivityStatement(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
