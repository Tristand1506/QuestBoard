package ObjectLib;

import java.util.List;

import UtilLib.LocationType;
import UtilLib.UnitType;

public class UserAccount {

    private String TAG = "User_Account";


    private String id;

    private String email;
    private String username;

    private UnitType units;
    private List<Location> favorites;
    private LocationType prefLandmark;



    ////////////////
    // constructors
    ////////////////

    //default constructor
    public UserAccount(){}

    //partial constructor
    public UserAccount(String username, String email) {
        setEmail(email);
        setUsername(username);
    }

    //full constructor
    public UserAccount(String username, String email, UnitType unit, LocationType pref, List<Location> favs) {
        setEmail(email);
        setUsername(username);

        units = unit;
        prefLandmark = pref;
        favorites = favs;
    }

    public void setId(String id) {
        if (this.id == null){
            this.id = id;
        }
        System.out.println(TAG+": Failed to set new ID \n ID already assigned");
    }
    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }

    public UnitType getUnits() {
        return units;
    }

    public void setUnits(UnitType units) {
        this.units = units;
    }

    public List<Location> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Location> favorites) {
        this.favorites = favorites;
    }

    public LocationType getPrefLandmark() {
        return prefLandmark;
    }

    public void setPrefLandmark(LocationType prefLandmark) {
        this.prefLandmark = prefLandmark;
    }

    @Override
    public String toString() {
        return "UserAcount{" +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

