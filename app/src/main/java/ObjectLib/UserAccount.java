package ObjectLib;

import java.util.List;

import UtilLib.LocationType;
import UtilLib.UnitType;

public class UserAccount {

    String email;
    String username;

    UnitType units;
    List<Location> favorites;
    LocationType prefLandmark;



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


    @Override
    public String toString() {
        return "UserAcount{" +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

