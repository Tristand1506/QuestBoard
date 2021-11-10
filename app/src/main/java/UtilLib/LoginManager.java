package UtilLib;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

    private String TAG = "LoginManager";

    // Singleton LoginManager
    private static final LoginManager Manage = new LoginManager();
    public static LoginManager I() {return Manage;}
    private DatabaseReference userData = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users/ ");

    static private FirebaseAuth mAuth;

    public LoginManager(){

        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                accounts.clear();
                System.out.println("Refreshing Accounts...");
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    UserAccount acc = snap.getValue(UserAccount.class);
                    accounts.add(acc);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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

    public void UpdateUserData(UserAccount acc){
        System.out.println("Finding User: "+getActiveUser().getUid() );
        userData.child(getActiveUser().getUid()).child("email").setValue(acc.getEmail());
        userData.child(getActiveUser().getUid()).child("username").setValue(acc.getUsername());
        //userData.child(getActiveUser().getUid()).child("gender").setValue(acc.getGender());
        //userData.child(getActiveUser().getUid()).child("phone").setValue(acc.getPhone());
    }
    public void initUsers(){
        userData = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users");
    }
}
