package com.example.priya.chatbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView username;

    Button login;
    Button new_user;
    Button signout;

    EditText usernam;
    EditText password;

    String nameuser="Not Registered";
    String pass;
    String uname;
    String passw;

    FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username    =   (TextView)findViewById(R.id.name);

        login       =   (Button)findViewById(R.id.login);
        new_user    =   (Button)findViewById(R.id.new_user);
        signout     =   (Button)findViewById(R.id.signout);

        usernam     =   (EditText)findViewById(R.id.username);
        password    =   (EditText)findViewById(R.id.password);

        SharedPreferences preferences  = getApplicationContext().getSharedPreferences("User_details", MODE_PRIVATE);
        final SharedPreferences.Editor editor=preferences.edit();
        SharedPreferences prefs = getSharedPreferences("User_details", MODE_PRIVATE);
        nameuser= prefs.getString("username", "Not Registered");//"No name defined" is the default value.
        pass=prefs.getString("password","Not Registered");
        if (nameuser.equals("Not Registered")) {
            signout.setVisibility(View.INVISIBLE);
        }
        else {
            usernam.setVisibility(View.INVISIBLE);
            password.setVisibility(View.INVISIBLE);
            username.setText(nameuser);
        }

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameuser.equals("Not Registered")) {
                    Intent intent = new Intent(MainActivity.this, Sign_in.class);
                    startActivity(intent);
                }
                if(!nameuser.equals("Not Registered")){
                    Toast.makeText(MainActivity.this,"Already signed-in :"+nameuser,Toast.LENGTH_LONG).show();
                }
            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameuser.equals("Not Registered")) {
                    username.setText("Not Registered");
                    nameuser="Not Registered";
                    editor.clear();
                    editor.apply();
                    signout.setVisibility(View.INVISIBLE);
                    usernam.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                    username.setText(nameuser);
                    Toast.makeText(MainActivity.this,"Signed-out",Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(MainActivity.this,"Not signed-in",Toast.LENGTH_LONG).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               uname=usernam.getText().toString();
               passw=password.getText().toString();

                if(nameuser.equals("Not Registered")) {


                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {



                            for (DataSnapshot snapshot:dataSnapshot.child("users").getChildren()) {

                                String name = snapshot.child("name").getValue().toString();
                                String password = snapshot.child("password").getValue().toString();
                                if(name.equals(uname)&&passw.equals(password)){

                                    editor.putString("username", name);
                                    editor.putString("password", password);
                                    editor.apply();
                                    nameuser=name;
                                    Toast.makeText(MainActivity.this,"Welcome "+nameuser,Toast.LENGTH_LONG).show();
                                    Intent inn = new Intent(MainActivity.this, Chat.class);
                                    startActivity(inn);

                                }


                            }




                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w("TAG", "Failed to read value.", error.toException());

                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Welcome "+nameuser,Toast.LENGTH_LONG).show();
                    Intent inntent = new Intent(MainActivity.this, Chat.class);
                    startActivity(inntent);

                }

            }
        });

    }

}
