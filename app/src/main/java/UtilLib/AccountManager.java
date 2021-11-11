package UtilLib;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ObjectLib.UserAccount;

public class AccountManager {

    private static String TAG = "LoginManager";

    // Singleton LoginManager
    private static final AccountManager Manage = new AccountManager();
    public static AccountManager I() {return Manage;}
    private static DatabaseReference activeUserData;

    private static UserAccount activeAccountData;

    public static UserAccount getActiveAccountData() {
        return activeAccountData;
    }

    // ensures active account is bound to the last user whop logged in
    static FirebaseAuth.AuthStateListener userBind = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            activeUserData = FirebaseDatabase.getInstance("https://questing-board-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child( getActiveUser().getUid());
            activeUserData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println("Refreshing Account Info...");

                    UserAccount acc = dataSnapshot.getValue(UserAccount.class);
                    activeAccountData = acc;
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
    };




    public AccountManager(){
        FirebaseAuth.getInstance().addAuthStateListener(userBind);
    }

    public static FirebaseUser getActiveUser(){
        Log.e("LoginManager", FirebaseAuth.getInstance().getCurrentUser().getUid());
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    // adds userData to database
    public static void addUser(UserAccount acc){
        try{
            FirebaseDatabase.getInstance("https://questing-board-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child( getActiveUser().getUid()).setValue(acc);
        } catch(Exception e){
            Log.e(TAG, "addUser: failed to add \n" + e);
        }
    }

    public static void initBindUser(){
        FirebaseAuth.getInstance().addAuthStateListener(userBind);
    }

    public static void UpdateAccountData(UserAccount acc){
        System.out.println("Finding User: "+getActiveUser().getUid() );
        activeUserData.child("email").setValue(acc.getEmail());
        activeUserData.child("username").setValue(acc.getUsername());
        activeUserData.child("prefUnits").setValue(acc.getPrefUnits());
        activeUserData.child("prefLandmark").setValue(acc.getPrefLandmark());

    }

}
