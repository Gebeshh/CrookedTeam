package com.wolf.cmpproject001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserRegister extends AppCompatActivity {

    Button Register_btn;
    EditText username,password;
    String username_s,password_s;


    private HashMap<String,Object> mData;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Register_btn=(Button) findViewById(R.id.onay_btn);
        username=(EditText) findViewById(R.id.editTextTextPersonName);
        password=(EditText) findViewById(R.id.editTextTextPassword);
        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

    }
    public void SignUp(){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://cmpproject001-2ad26-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference();

        mAuth= FirebaseAuth.getInstance();

        username_s=username.getText().toString();
        password_s=password.getText().toString();
        if(!TextUtils.isEmpty(username_s)&&!TextUtils.isEmpty(password_s)){
            mAuth.createUserWithEmailAndPassword(username_s,password_s)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                mUser =mAuth.getCurrentUser();


                                mData =new HashMap<>();
                                mData.put("Email",username_s);
                                mData.put("password",password_s);
                                mData.put("UserId",mUser.getUid());
                                GetDataAsap(mUser.getUid());
                                myRef.child("Users").child(mUser.getUid()).setValue(mData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(UserRegister.this,"Account succesfully created. ",Toast.LENGTH_SHORT).show();


                                            OpenMain();
                                        }else{
                                            Toast.makeText(UserRegister.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(UserRegister.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(this,"Email or Password can not be empty. ",Toast.LENGTH_SHORT).show();
        }
    }
    public void GetDataAsap(String uid){
        mReference=FirebaseDatabase.getInstance().getReference("Users").child(uid);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snp: snapshot.getChildren()){
                    switch(String.valueOf(snp.getKey())){
                        case "Email":Client.instance.setEmail(snp.getValue(String.class));break;
                        case "UserId":Client.instance.setUserID(snp.getValue(String.class));break;
                        case "password":Client.instance.setPassword(snp.getValue(String.class));break;
                        case "Score":Client.instance.SetScore(Integer.parseInt((snp.getValue(String.class))));break;
                        default:
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserRegister.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void OpenMain(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void AddMe(String userid){
    }
}