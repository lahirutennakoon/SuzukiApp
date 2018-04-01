package com.example.suzukiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonAdminSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonUserSignIn;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //if admin already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //take directly to admin panel
            finish();
            startActivity(new Intent(getApplicationContext(), AdminPanelActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.admin_email);
        editTextPassword = (EditText) findViewById(R.id.admin_password);
        buttonAdminSignIn = (Button) findViewById(R.id.adminLoginBtn);
        buttonUserSignIn = (Button) findViewById(R.id.userLoginBtn);

        progressDialog = new ProgressDialog(this);

        buttonAdminSignIn.setOnClickListener(this);
        buttonUserSignIn.setOnClickListener(this);
    }

//    public void adminLogin(View view)
//    {
//        Toast.makeText(AdminLoginActivity.this, "temporary log in button!", Toast.LENGTH_LONG).show();
//
//        Intent intentAdminPanel = new Intent(this, AdminPanelActivity.class);
//        startActivity(intentAdminPanel);
//    }

    private void adminSignIn(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //need to check if the email or password is empty before proceeding
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter your password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Login in progress");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //admin login success
                            finish();
                            startActivity(new Intent(getApplicationContext(), AdminPanelActivity.class));
                        }
                        else
                        {
                            Toast.makeText(AdminLoginActivity.this, "Email or password wrong!"+task.getException().getMessage(), Toast.LENGTH_LONG).show();


                            return;
                        }

                    }
                });


    }

    @Override
    public void onClick(View view) {
        if(view == buttonAdminSignIn)
        {
            adminSignIn();
        }
        if (view == buttonUserSignIn)
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
