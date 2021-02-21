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

public class MainActivity extends AppCompatActivity {
    EditText emailId, password; // Declaration des textView et les bouttons
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance(); //recuperation de l'instance
        emailId = findViewById(R.id.editTextTextEmailAddress); //recuperation d'email a partir de l'activity main
        password = findViewById(R.id.editTextTextPassword); //recuperation de mot de passe a partir de l'activity main
        btnSignUp = findViewById(R.id.button); //recuperation du boutton a partir de l'activity main
        tvSignIn = findViewById(R.id.textView); //recuperation de text view a partir de l'activity main
        btnSignUp.setOnClickListener(new View.OnClickListener() { // definir la methode onClick du boutton sign up
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString(); // recuperer email entré dans l'activity
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please Enter Email");
                    emailId.requestFocus();
                } else if(pwd.isEmpty()){
                    password.setError("Please Enter Password");
                    password.requestFocus();
                } else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error Occurred !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}