package com.example.priya.chatbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Personal_Chat extends AppCompatActivity {

    String nameuser;
    String message;
    String cur_user;

    TextView username;

    EditText editText;

    Button send;

    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference myref,myref2,myref1;
    FirebaseAuth auth;
    FirebaseUser user;

    //=====================
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    private List<Messages> messagesList = new ArrayList<>();
    RecyclerView recyclerView;
    ScrollView scrollView;
    private MessageAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__chat);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        username=(TextView)findViewById(R.id.username);
        send=(Button)findViewById(R.id.send);
        editText=(EditText)findViewById(R.id.editText);

        scrollView=(ScrollView)findViewById(R.id.scroll);

        Intent intent = getIntent();
        nameuser = intent.getExtras().getString("User");
        username.setText(nameuser);

        SharedPreferences prefs = getSharedPreferences("User_details", MODE_PRIVATE);
        cur_user= prefs.getString("username", "Not Registered");

        recyler_view();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")){
                message=editText.getText().toString();
                sendmessage();
                }
                editText.setText("");
            }
        });



    }
    public void recyler_view(){

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MessageAdapter(messagesList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //=====================
        //mLayoutManager.setReverseLayout(true);
        //mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        //============================================================================
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Messages messages=new Messages("","","");


                for (DataSnapshot snapshot:dataSnapshot.child("message").child(cur_user+"-"+nameuser).getChildren()) {

                    String name = snapshot.child("author").getValue().toString();
                    String text = snapshot.child("text").getValue().toString();

                        messages = new Messages(text,name,cur_user);
                        messagesList.add(messages);
                }
                recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();




        //============================================================================
//        myRef.addChildEventListener(new ChildEventListener() {
//
//            Messages messages=new Messages("","","");
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Map map=dataSnapshot.getValue(Map.class);
//                String name =map.get("author").toString();
//                String text = map.get("text").toString();
//
//                messages = new Messages(text,name,cur_user);
//                messagesList.add(messages);
//                recyclerView.setAdapter(mAdapter);
//
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//



    }

    public void sendmessage() {

        Log.d("Chat", "sendmessage: Reached here");


        Log.e("Success", "Success");
        myref = db.getReference("message");



        myref1 = myref.child(cur_user+"-"+nameuser).push();
        myref2=myref1.child("text");
        myref2.setValue(message);
        myref2=myref1.child("author");
        myref2.setValue(cur_user);

        myref1 = myref.child(nameuser+"-"+cur_user).push();
        myref2=myref1.child("text");
        myref2.setValue(message);
        myref2=myref1.child("author");
        myref2.setValue(cur_user);


    }
}
