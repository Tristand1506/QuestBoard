package ObjectLib;

import UtilLib.LocationType;

public class Location {
    private  String id;
    private String name;
    private String type;

    public void setId(String id) {
        if (this.id == null){
            this.id = id;
        }
        System.out.println("Failed to set new ID");
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public static int toInteger(LocationType lT){
        switch (lT){
            case EMPTY:
                return 0;

            case RESTAURANT:
                return 1;

            case HOTEL:
                return 2;

            case HISTORIC:
                return 3;

            case PARK:
                return 4;

            case MEDICAL:
                return 5;

            case GAS:
                return 6;

            case RECREATION:
                return 7;

            default:
                return -1;
        }
    }
}

