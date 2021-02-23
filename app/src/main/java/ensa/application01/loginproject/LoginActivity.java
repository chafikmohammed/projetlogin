package ensa.application01.loginproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailId, password; // Declaration des textView et les bouttons
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance(); //recuperation de l'instance
        emailId = findViewById(R.id.editTextTextEmailAddress); //recuperation d'email a partir de l'activity main
        password = findViewById(R.id.editTextTextPassword); //recuperation de mot de passe a partir de l'activity main
        btnSignIn = findViewById(R.id.button); //recuperation du boutton a partir de l'activity main
        tvSignUp = findViewById(R.id.textView); //recuperation de text view a partir de l'activity main

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() { // definir la methode onClick du boutton sign up
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString(); // recuperer email entré dans l'activity
                String pwd = password.getText().toString(); // recuperer password
                if(email.isEmpty()){
                    emailId.setError("Please Enter Email");
                    emailId.requestFocus(); // afficher message d'erreur
                } else if(pwd.isEmpty()){
                    password.setError("Please Enter Password");
                    password.requestFocus();
                } else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show(); //afficher une notification d'erreur
                } else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override // on utilise la methode signIn de la classe FirebaseAuth et on la donne l'email et password
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){ // si la creation ne fonctionne pas
                                Toast.makeText(LoginActivity.this,"Login Error, Please login again", Toast.LENGTH_SHORT).show();
                            }else { //Sinon on se redirige vers l'activité home
                                Intent intToHome = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occurred !", Toast.LENGTH_SHORT).show();// notification d'erreur
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() { // Si on clique sur la textView
            @Override
            public void onClick(View v) { // on se redirige vers la page de login
                Intent intSignUp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() { // Ajouter authentification state listener au Firebase Authentification
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}