package com.wolf.cmpproject001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class UserLogin extends AppCompatActivity {

    Button Login_btn;
    Button Register_btn;
    EditText username,password;
    String username_s,password_s;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private DatabaseReference mReference;
    private HashMap<String,Object> mData;


    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        Client.getInstance();

        Login_btn=(Button) findViewById(R.id.login_btn);
        Register_btn=(Button) findViewById(R.id.registerMe);
        username=(EditText) findViewById(R.id.editTextTextPersonName);
        password=(EditText) findViewById(R.id.editTextTextPassword);



        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login(v);

            }
        });


        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenRegister();
            }
        });
    }
    public void UpdateValue(HashMap<String,Object> hashMap,String uid){
        mReference=FirebaseDatabase.getInstance().getReference("Users").child(uid);
        mReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UserLogin.this,"Score has been changed",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void UpdateScore(int new_score){
        mData = new HashMap<>();

        mData.put("Score",String.valueOf(new_score));


        if(mUser!=null){
            UpdateValue(mData,mUser.getUid());
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
                        case "UserId":Client.instance.setUserID(snp.getValue(String.class));
                            System.out.println("Client.instance.setUserID(snp.getValue(String.class))"+(Client.instance.getUserID()));
                        break;
                        case "password":Client.instance.setPassword(snp.getValue(String.class));break;
                        case "Score":Client.instance.SetScore(Integer.parseInt((snp.getValue(String.class))));

                        break;
                        default:
                    }
                }
                addOnNewIntentListener(new Consumer<Intent>() {
                    @Override
                    public void accept(Intent intent) {
                        intent=new Intent(UserLogin.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserLogin.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void GetData(String uid){
        mReference=FirebaseDatabase.getInstance().getReference("Users").child(uid);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snp: snapshot.getChildren()){
                    System.out.println(snp.getKey()+"  :  "+snp.getValue());
                    switch(String.valueOf(snp.getKey())){
                        case "Email":Client.instance.setEmail(snp.getValue(String.class));break;
                        case "UserId":Client.instance.setUserID(snp.getValue(String.class));break;
                        case "password":
                            Client.instance.setPassword(snp.getValue(String.class));
                            break;
                        case "Score":Client.instance.SetScore(Integer.parseInt((snp.getValue(String.class))));
                            System.out.println("snp.getValue(String.class)): "+snp.getValue(String.class));
                            System.out.println("Client.instance.getScore(): "+Client.instance.getScore());break;
                        default:
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserLogin.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Login(View view){
        username_s=username.getText().toString();
        password_s=password.getText().toString();
        mAuth  =FirebaseAuth.getInstance();


        if(!TextUtils.isEmpty(username_s)&&!TextUtils.isEmpty(password_s)){
            mAuth.signInWithEmailAndPassword(username_s,password_s).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    mUser=authResult.getUser();
                    System.out.println( "mUser.id : "+mUser.getUid() );
                    //mUser= mAuth.getCurrentUser();
                    if(mUser!=null){
                        GetDataAsap(mUser.getUid());
                        //GetData(mUser.getUid());
                        System.out.println("Client.instance.getScore2(): "+Client.instance.getScore());
                        UpdateScore(90+Client.instance.getScore());

                    }
                    OpenMain();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserLogin.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            });
        }else{
            Toast.makeText(this,"Email or Password can not be empty. ",Toast.LENGTH_SHORT).show();
        }
    }
    public void OpenRegister(){
        Intent intent=new Intent(this,UserRegister.class);
        startActivity(intent);
    }
    public void OpenMain(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}