package com.example.priya.chatbot;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {

    private List<User> userList = new ArrayList<>();
    RecyclerView recyclerView;
    private UserAdapter mAdapter;
    String nameuser;

    //=====================
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //SharedPreferences preferences  = getApplicationContext().getSharedPreferences("Phonenumber", MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("User_details", MODE_PRIVATE);
        nameuser= prefs.getString("username", "Not Registered");

        mAdapter = new UserAdapter(userList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //=====================

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        //============================================================================
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user=new User("");


                for (DataSnapshot snapshot:dataSnapshot.child("users").getChildren()) {

                    String name = snapshot.child("name").getValue().toString();

                    if (!nameuser.equals(name)) {

                        user = new User(name);
                        userList.add(user);
                        recyclerView.setAdapter(mAdapter);
                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });



        mAdapter.notifyDataSetChanged();
        //============================================================================



    }
}
