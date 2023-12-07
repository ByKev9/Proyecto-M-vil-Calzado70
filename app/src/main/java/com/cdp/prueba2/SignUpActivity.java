package com.cdp.prueba2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Pair;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    TextView LogLabel, LogLabel2,Iniciasesi;
    ImageView logosignimageview;
    TextInputLayout usuariotext, contraseñatext;
    MaterialButton iniciosesion2;
    TextInputEditText nameedittext,emailedittext,passwordedittext,confirmpasswordedit;

    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        logosignimageview = findViewById(R.id.logosignimageview);
        LogLabel = findViewById(R.id.LogLabel);
        LogLabel2 = findViewById(R.id.LogLabel2);
        usuariotext = findViewById(R.id.usuariotext);
        contraseñatext = findViewById(R.id.contraseñatext);
        Iniciasesi = findViewById(R.id.Iniciasesi);
        iniciosesion2 = findViewById(R.id.iniciosesion2);
        nameedittext= findViewById(R.id.nameedittext);
        emailedittext = findViewById(R.id.emailedittext);
        passwordedittext = findViewById(R.id.passwordedittext);
        confirmpasswordedit = findViewById(R.id.confirmpasswordedit);
        mAuth = FirebaseAuth.getInstance();

        iniciosesion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        Iniciasesi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                transitionBack();
            }

        });


    }
    public void validate(){
        database = FirebaseDatabase.getInstance();

        String name = nameedittext.getText().toString();
        String email = emailedittext.getText().toString();
        String password = passwordedittext.getText().toString();
        String confirmpassword = confirmpasswordedit.getText().toString();

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
        if (!confirmpassword.equals(password)){
            confirmpasswordedit.setError("Deben ser iguales");
            return;
        }else{
            registrar(email,password);
        }



    }

    public void registrar(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()  {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(SignUpActivity.this,UserActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(SignUpActivity.this, "Fallo en registrarse", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        transitionBack();
    }
    public void transitionBack(){
        Intent intent =new Intent(SignUpActivity.this,LoginActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0]=new Pair<View, String>(logosignimageview, "logoImagetrans");
        pairs[1]=new Pair<View, String>(LogLabel, "texttrans");
        pairs[2]=new Pair<View, String>(LogLabel2, "isTextTranss");
        pairs[3]=new Pair<View, String>(usuariotext, "emailtrans");
        pairs[4]=new Pair<View, String>(contraseñatext, "passtrans");
        pairs[5]=new Pair<View, String>(Iniciasesi, "newusertrans");
        pairs[6]=new Pair<View, String>(iniciosesion2, "buttontrans");

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }else{

            startActivity(intent);
            finish();
        }
    }


}