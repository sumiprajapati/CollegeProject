package com.example.de.tripshayatri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
EditText email,password;
Button login;
    FirebaseAuth auth;
TextView register;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth=FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.username);
        password=findViewById(R.id.password);
        dialog=new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myemail=email.getText().toString();
                String mypassword=password.getText().toString();
                if (TextUtils.isEmpty(myemail) || TextUtils.isEmpty(mypassword)) {
                    Toast.makeText(MainActivity.this, "Empty Username or Password", Toast.LENGTH_SHORT).show();
                }
                else{
                   dialog.show();
                   auth.signInWithEmailAndPassword(myemail,mypassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                       @Override
                       public void onSuccess(AuthResult authResult) {
//                           SharedPreferences sp1 = getSharedPreferences("yourfile", Context.MODE_PRIVATE);//for saving login session
//                           SharedPreferences.Editor editor = sp1.edit();//shared preferance lai edit garna lai editor banako
//                           editor.putBoolean("state", true);//login state lai true gareko
//                           editor.commit();
                           dialog.dismiss();
                           Toast.makeText(MainActivity.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
                           Intent i = new Intent(MainActivity.this, DashActivity.class);
                           startActivity(i);
                           //data from mysql


                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           dialog.dismiss();
                           Toast.makeText(MainActivity.this, "Login Faliure", Toast.LENGTH_SHORT).show();
                       }
                   });
                }
                //ya logic lekhna baki cha
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });
    }
}
