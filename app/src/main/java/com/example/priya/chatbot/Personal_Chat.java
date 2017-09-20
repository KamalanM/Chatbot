package com.example.priya.chatbot;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Personal_Chat extends AppCompatActivity {

    String nameuser;
    String cur_user;
    String message;
    EditText editText;
    Button send;
    RecyclerView recyclerView;

    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference myref,myref2,myref1;
    FirebaseAuth auth;
    FirebaseUser user;
    private MessageAdapter mAdapter;

    //=====================
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    private static List<Messages> messagesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__chat);

        send=(Button)findViewById(R.id.send);
        editText=(EditText)findViewById(R.id.message);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //scrollView=(ScrollView)findViewById(R.id.scroll);


        Intent intent = getIntent();
        nameuser = intent.getExtras().getString("User");
        setTitle(nameuser);

        SharedPreferences prefs = getSharedPreferences("User_details", MODE_PRIVATE);
        cur_user= prefs.getString("username", "Not Registered");

        mAdapter = new MessageAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //=====================
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Messages messages=new Messages("","","");


                for (DataSnapshot snapshot:dataSnapshot.child("message").child(cur_user+"-"+nameuser).getChildren()) {

                    String name = snapshot.child("author").getValue().toString();
                    String text = snapshot.child("text").getValue().toString();

                    MessageAdapter.messagesList.add(text);
                    MessageAdapter.authorlist.add(name);
                    MessageAdapter.cur_userlist.add(cur_user);

//                    messages = new Messages(text,name,cur_user);
//                    messagesList.add(messages);
                    mAdapter.notifyDataSetChanged();
                }

                recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView.setAdapter(mAdapter);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")){
                    message=editText.getText().toString();
                    myref = db.getReference("message");

                    myref1 = myref.child(cur_user+"-"+nameuser).push();

                    myref2=myref1.child("author");
                    myref2.setValue(cur_user);
                    myref2=myref1.child("text");
                    myref2.setValue(message);

                    myref1 = myref.child(nameuser+"-"+cur_user).push();
                    myref2=myref1.child("author");
                    myref2.setValue(cur_user);
                    myref2=myref1.child("text");
                    myref2.setValue(message);

                    editText.setText("");
                    finish();
                    startActivity(getIntent());
                }
            }
        });
    }



    }
