package com.cdp.prueba2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    TextView LogLabel, LogLabel2,Iniciasesi;
    ImageView logoImageView;
    TextInputLayout usuariotext, contraseñatext;
    MaterialButton iniciosesion;
    TextInputEditText emailedittext, passwordedittext;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logoImageView = findViewById(R.id.logoImageView);
        LogLabel = findViewById(R.id.LogLabel);
        LogLabel2 = findViewById(R.id.LogLabel2);
        usuariotext = findViewById(R.id.usuariotext);
        contraseñatext = findViewById(R.id.contraseñatext);
        Iniciasesi = findViewById(R.id.Iniciasesi);
        iniciosesion = findViewById(R.id.iniciosesion);

        emailedittext= findViewById(R.id.emailedittext);
        passwordedittext=findViewById(R.id.passwordedittext);
        mAuth = FirebaseAuth.getInstance();

        Iniciasesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,SignUpActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0]=new Pair<View, String>(logoImageView, "logoImagetrans");
                pairs[1]=new Pair<View, String>(LogLabel, "texttrans");
                pairs[2]=new Pair<View, String>(LogLabel2, "isTextTranss");
                pairs[3]=new Pair<View, String>(usuariotext, "emailtrans");
                pairs[4]=new Pair<View, String>(contraseñatext, "passtrans");
                pairs[5]=new Pair<View, String>(Iniciasesi, "newusertrans");
                pairs[6]=new Pair<View, String>(iniciosesion, "buttontrans");

                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                    startActivity(intent,options.toBundle());
                }else{

                    startActivity(intent);
                    finish();
                }
            }
        });

        iniciosesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }
    public void validate(){
        String email = emailedittext.getText().toString().trim();
        String password = passwordedittext.getText().toString().trim();


        if(email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailedittext.setError("correo invalido");
            return;
        }else{
            emailedittext.setError(null);
        }
        if (password.isEmpty() || password.length()<8){
            passwordedittext.setError("Se necesitan mas de 8 caracteres");
            return;
        }else if (!Pattern.compile("[0-9]").matcher(password).find()){
            passwordedittext.setError("Al menos un numero");
            return;
        }else{
            passwordedittext.setError(null);
        }

            iniciarSesion(email,password);
        }
        public void iniciarSesion(String email,String passord){
            mAuth.signInWithEmailAndPassword(email, passord)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task ){
                            if (task.isSuccessful()){
                                Intent intent = new Intent(LoginActivity.this,UserActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this,"Credenciales equivocadas, intente de nuevo",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }

    }
