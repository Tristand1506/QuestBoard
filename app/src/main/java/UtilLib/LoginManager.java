package UtilLib;

import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

import ObjectLib.UserAccount;

public class LoginManager {

    private static String TAG = "LoginManager";

    // Singleton LoginManager
    private static final LoginManager Manage = new LoginManager();
    public static LoginManager I() {return Manage;}
    private DatabaseReference activeUserData;

    static private FirebaseAuth mAuth;

    public LoginManager(){

//        activeUserData.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                accounts.clear();
//                System.out.println("Refreshing Accounts...");
//                for (DataSnapshot snap: dataSnapshot.getChildren())
//                {
//                    UserAccount acc = snap.getValue(UserAccount.class);
//                    accounts.add(acc);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }

    private List<UserAccount> accounts = new ArrayList<>();

    public UserAccount getAccountFromEmail(String email){
        for (UserAccount acc: accounts) {
            if (acc.getEmail().equalsIgnoreCase(email)){
                return acc;
            }
        }
        return  null;
    }
    public UserAccount getAccountFromUsername(String username){
        for (UserAccount acc: accounts) {
            if (acc.getUsername().equals(username)){
                return acc;
            }
        }
        return  null;
    }

    public static FirebaseUser getActiveUser(){
        Log.e("LoginManager", FirebaseAuth.getInstance().getCurrentUser().getUid());
        return FirebaseAuth.getInstance().getCurrentUser();
    }
    public static void addUser(UserAccount acc){

        try{
            FirebaseDatabase.getInstance("https://questing-board-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child( getActiveUser().getUid()).setValue(acc);
        } catch(Exception e){
            Log.e(TAG, "addUser: failed to add \n" + e);
        }
    }

    public void UpdateUserData(UserAccount acc){
        System.out.println("Finding User: "+getActiveUser().getUid() );
        activeUserData.child(getActiveUser().getUid()).child("email").setValue(acc.getEmail());
        activeUserData.child(getActiveUser().getUid()).child("username").setValue(acc.getUsername());
        //activeUserData.child(getActiveUser().getUid()).child("gender").setValue(acc.getGender());
        //activeUserData.child(getActiveUser().getUid()).child("phone").setValue(acc.getPhone());
    }
    public void initUsers(){
        //activeUserData = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child();
    }
}
