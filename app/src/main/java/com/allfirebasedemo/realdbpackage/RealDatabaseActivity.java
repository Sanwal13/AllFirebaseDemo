package com.allfirebasedemo.realdbpackage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allfirebasedemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Sanwal Singh on 26/12/16.
 */

public class RealDatabaseActivity extends AppCompatActivity {

    private static final String TAG = RealDatabaseActivity.class.getSimpleName();
    public TextView txtDetails;
    public EditText inputName, inputEmail, inputPhone, inputAddress;
    public Button btnSave, btn_getDetail;
    private String userId;

    private DatabaseReference mFirebaseDatabase;
    public FirebaseDatabase mFirebaseInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtimedb);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        txtDetails = (TextView) findViewById(R.id.txt_user);
        inputName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPhone = (EditText) findViewById(R.id.phone);
        inputAddress = (EditText) findViewById(R.id.address);
        btnSave = (Button) findViewById(R.id.btn_save);
        btn_getDetail = (Button) findViewById(R.id.btn_getDetail);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");// same as from firebase

        mFirebaseInstance.getReference("app_title").setValue("Real time Database");

        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String phone = inputPhone.getText().toString();
                String address = inputAddress.getText().toString();

                Log.e("RealDB", "User ID : " + userId);

                if (TextUtils.isEmpty(userId)) {
                    createUser(name, email, phone, address);
                } else {
                    updateUser(name, email, phone, address);
                }
            }
        });

        btn_getDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = mFirebaseInstance.getReference("users");
                myRef.orderByChild("name").equalTo("Sanwal").addListenerForSingleValueEvent
                        (new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                    Log.d(TAG, "PARENT: " + childDataSnapshot.getKey());
                                    UserDetails user = childDataSnapshot.getValue(UserDetails.class);
                                    Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        toggleButton();
    }

    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }


    private void createUser(String name, String email, String phone, String address) {

        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        UserDetails user = new UserDetails(name, email, phone, address);
        mFirebaseDatabase.child(userId).setValue(user);
        addUserChangeListener();
    }

    private void addUserChangeListener() {
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails user = dataSnapshot.getValue(UserDetails.class);

                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);
                txtDetails.setText(user.name + ", " + user.email + ", " + user.phone + ", " + user.address);

                inputEmail.setText("");
                inputName.setText("");
                inputPhone.setText("");
                inputAddress.setText("");

                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String name, String email, String phone, String address) {
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);

        if (!TextUtils.isEmpty(email))
            mFirebaseDatabase.child(userId).child("email").setValue(email);

        if (!TextUtils.isEmpty(phone))
            mFirebaseDatabase.child(userId).child("phone").setValue(phone);

        if (!TextUtils.isEmpty(address))
            mFirebaseDatabase.child(userId).child("address").setValue(address);
    }


    private void getUserDetails() {
        mFirebaseDatabase.child(userId).getDatabase();

        Log.e("FIRE", "Database :" + mFirebaseDatabase.child(userId).getDatabase());
    }

}
/*{
        "rules": {
        "users": {
        ".read": true,
        ".write": true
        }
        }
        }*/
