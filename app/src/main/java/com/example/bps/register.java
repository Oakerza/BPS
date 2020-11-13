package com.example.bps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class register extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextEmail;
    private EditText editTextPassword1;
    private EditText editTextPassword2;
    private Button buttonReg;
    private TextView textViewLogin;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String stringUserName;
    private String stringEmail;
    private String stringPassword;
    private String stringUserID;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.reg_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextUserName = findViewById(R.id.reg_editText_user);
        editTextEmail = findViewById(R.id.reg_editText_email);
        editTextPassword1 = findViewById(R.id.reg_editText_password1);
        editTextPassword2 = findViewById(R.id.reg_editText_password2);
        buttonReg = findViewById(R.id.reg_button_register);
        textViewLogin = findViewById(R.id.reg_text_login);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringUserName = editTextUserName.getText().toString();
                stringEmail = editTextEmail.getText().toString().trim();
                String password1 = editTextPassword1.getText().toString().trim();
                String password2 = editTextPassword2.getText().toString().trim();
                if (TextUtils.isEmpty(stringUserName)) {
                    editTextUserName.setError("กรุณาใส่ ชื่อผู้ใช้");
                    return;
                } else if (TextUtils.isEmpty(stringEmail)) {
                    editTextEmail.setError("กรุณาใส่ Email");
                    return;
                } else if (TextUtils.isEmpty(password1)) {
                    editTextPassword1.setError("กรุณาใส่ รหัสผ่าน");
                    return;
                } else if (TextUtils.isEmpty(password2)) {
                    editTextPassword2.setError("กรุณายืนยัน รหัสผ่าน");
                    return;
                } else if (!password1.equals(password2)) {
                    new AlertDialog.Builder(register.this)
                            .setIcon(R.drawable.ic_baseline_warning_24)
                            .setTitle("รหัสผ่านไม่ตรงกัน")
                            .setPositiveButton("แก้ไขรหัสผ่าน", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    editTextPassword1.setText("");
                                    editTextPassword2.setText("");
                                }
                            })
                            .setNegativeButton("ล้างข้อมูล", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    editTextUserName.setText("");
                                    editTextEmail.setText("");
                                    editTextPassword1.setText("");
                                    editTextPassword2.setText("");
                                }
                            })
                            .show();
                } else {
                    stringPassword = password1;
                    firebaseAuth.createUserWithEmailAndPassword(stringEmail, stringPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(register.this, "สร้างบัญชีสำเร็จ",
                                                Toast.LENGTH_SHORT).show();
                                        stringUserID = firebaseAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = firebaseFirestore
                                                .collection("users")
                                                .document(stringUserID);
                                        UserProfile userProfile = new UserProfile(stringUserName,
                                                stringEmail);
                                        documentReference.set(userProfile)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                openMyStoreActivity();
                                            }
                                        });
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Reg","error" + e);
                        }
                    });
                }
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }

    private void openMyStoreActivity() {
        Intent intent = new Intent(this, myStore.class);
        startActivity(intent);
        finish();
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, logIn.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}