package ensa.application01.loginproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Button btnLogout; //Declarer le boutton de logout
    FirebaseAuth mFirebaseAuth; // objet de type FirebaseAuth
    private FirebaseAuth.AuthStateListener mAuthStateListener; // AuthStateListenerss

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLogout = findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); //deconnecter l'instance de FirebaseAuth
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain); // se rediriger vers MainActivity
            }
        });
    }
}