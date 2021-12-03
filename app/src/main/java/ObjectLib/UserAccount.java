package ObjectLib;

import java.util.ArrayList;
import java.util.List;

import UtilLib.LocationType;
import UtilLib.UnitType;

public class UserAccount {

    private String TAG = "User_Account";


    private String id;

    private String email;
    private String username;

    private UnitType prefUnits;
    private List<String> favorites = new ArrayList<>();
    private LocationType prefLandmark;



    ////////////////
    // constructors
    ////////////////

    //default constructor
    public UserAccount(){
        setPrefUnits(UnitType.METRIC);
        setPrefLandmark(LocationType.EMPTY);
    }

    //partial constructor
    public UserAccount(String username, String email) {
        setEmail(email);
        setUsername(username);
        setPrefUnits(UnitType.METRIC);
        setPrefLandmark(LocationType.EMPTY);
    }

    //full constructor
    public UserAccount(String username, String email, UnitType unit, LocationType pref, List<String> favs) {
        setEmail(email);
        setUsername(username);

        prefUnits = unit;
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

    public UnitType getPrefUnits() {
        return prefUnits;
    }

    public void setPrefUnits(UnitType prefUnits) {
        this.prefUnits = prefUnits;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public LocationType getPrefLandmark() {
        return prefLandmark;
    }

    public void setPrefLandmark(LocationType prefLandmark) {
        this.prefLandmark = prefLandmark;
    }

    public Boolean isFavorite(String in){
        if (favorites != null) {
            for (int i = 0; i < favorites.size(); i++){
                if (favorites.get(i).equals(in)){
                    return true;
                }
            }
        }
        return  false;
    }

    @Override
    public String toString() {
        return "UserAcount{" +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

