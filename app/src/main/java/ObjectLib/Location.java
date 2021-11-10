package ObjectLib;

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
}
