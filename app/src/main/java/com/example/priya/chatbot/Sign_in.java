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
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Sign_in extends AppCompatActivity {


    EditText phoneNumber;
    EditText verificationcode;
    EditText username;
    EditText password;

    Button send_otp;
    Button back;

    String phonenumber=null;
    String mVerificationId;
    String nameuser;
    String pass;

    private FirebaseAuth mAuth;

    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference myref,myref2;
    FirebaseAuth auth;
    FirebaseUser user;

    PhoneAuthCredential Credential;

    boolean mVerificationInProgress = false;



    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User_details", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        verificationcode = (EditText) findViewById(R.id.verification_code);

        send_otp = (Button) findViewById(R.id.send_otp);
        back = (Button) findViewById(R.id.back);


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d("ATAG", "onVerificationCompleted:" + credential);
                verificationcode.setText(credential.getSmsCode());
                Toast.makeText(Sign_in.this, "SignInWithCredential:SUCCESS", Toast.LENGTH_LONG).show();
                nameuser = username.getText().toString();
                pass=password.getText().toString();
                //editor.putString("credential", credential);
                editor.apply();
                back.setVisibility(View.VISIBLE);
                //Credential=credential;

            }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Log.w("ATAG", "onVerificationFailed", e);
                    Toast.makeText(Sign_in.this, "onVerificationFailed", Toast.LENGTH_LONG).show();
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        // Invalid request
                        // ...
                        Toast.makeText(Sign_in.this, "Invalid Mobile Number", Toast.LENGTH_LONG).show();
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded
                        // ...
                        Toast.makeText(Sign_in.this, "Quota over", Toast.LENGTH_LONG).show();
                    }

                    // Show a message and update the UI
                    // ...
                    }

                @Override
                public void onCodeSent(String verificationId,
                                       PhoneAuthProvider.ForceResendingToken token) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    Log.d("ATAG", "onCodeSent:" + verificationId);

                    // Save verification ID and resending token so we can use them later
                    mVerificationId = verificationId;
                    mResendToken = token;

                    // ...
                   }
                };


            send_otp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String str = phoneNumber.getText().toString();
                    if (str != null) {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + phoneNumber.getText().toString(),        // Phone number to verify
                                60,                 // Timeout duration
                                TimeUnit.SECONDS,   // Unit of timeout
                                Sign_in.this,               // Activity (for callback binding)
                                mCallbacks);// OnVerificationStateChangedCallbacks

                    } else {
                        Toast.makeText(Sign_in.this, "Enter phonenumber", Toast.LENGTH_LONG).show();
                    }

                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    firebase();
                    if (nameuser != null) {
                        Intent intent = new Intent(Sign_in.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Sign_in.this, "Phone Not Verified", Toast.LENGTH_SHORT).show();
                        ;
                    }

                }
            });






    }




    public void firebase(){


        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Log.e("Success","Success");
        myref = db.getReference("users").push();

        myref2 = myref.child("name");
        myref2.setValue(nameuser);

        myref2 = myref.child("password");
        myref2.setValue(pass);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("User_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //SharedPreferences.Editor editor = getSharedPreferences("UNAME", MODE_PRIVATE).edit();
        editor.putString("username", nameuser);
        editor.putString("password", pass);
        editor.apply();

        Toast.makeText(Sign_in.this,"Welcome  "+nameuser, Toast.LENGTH_LONG).show();

    }

}

