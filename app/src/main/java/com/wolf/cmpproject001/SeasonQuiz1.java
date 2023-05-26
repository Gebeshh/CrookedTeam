package com.wolf.cmpproject001;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SeasonQuiz1 extends AppCompatActivity {

    ImageView question;
    Button bahar_btn;
    Button yaz_btn;
    Button kis_btn;
    Button sonbahar_btn;
    TextView progress;
    int remember=0;
    private int id=0;
    ArrayList<String> resimler;
    SharedPreferences settings;
    int counter=0;
    int score=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_quiz1);

        progress=findViewById(R.id.seasons_progress);
        question=findViewById(R.id.imageView);
        question.setImageResource(R.drawable.ilkbahar_1);

        progress.setText((String.valueOf(counter)+"/"+String.valueOf(6)));



        bahar_btn=findViewById(R.id.bahar_btn);
        bahar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(0);
            }
        });



        yaz_btn=findViewById(R.id.yaz_btn);
        yaz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(1);
            }
        });
        kis_btn=findViewById(R.id.kis_btn);
        kis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(2);
            }
        });
        sonbahar_btn=findViewById(R.id.sonbahar_btn);
        sonbahar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(3);
            }
        });
        resimler =new ArrayList<String>();
        resimler.add("ilkbahar_1");
        resimler.add("yaz_1");
        resimler.add("kis_1");
        resimler.add("sonbahar_1");

    }

    public void randomize(){
        int random = (int) (Math.random()*4);

        while(random==remember){
            random = (int) (Math.random()*4);
        }

        System.out.println(random);
        if(resimler.get(random)=="ilkbahar_1"){
            question.setImageResource(R.drawable.ilkbahar_1);
            id=0;
        }else if(resimler.get(random)=="yaz_1"){
            question.setImageResource(R.drawable.yaz_1);
            id=1;
        }else if(resimler.get(random)=="kis_1"){
            question.setImageResource(R.drawable.kis_1);
            id=2;
        }else if(resimler.get(random)=="sonbahar_1"){
            question.setImageResource(R.drawable.sonbahar_1);
            id=3;
        }

        remember=random;
    }
    public void Check(int i){
        counter++;
        if(id==i){
            score+=5;
            save();
            Toast.makeText(this,"Wonderful!",Toast.LENGTH_SHORT).show();
            randomize();
        }else{
            Toast.makeText(this,"Upps ",Toast.LENGTH_SHORT).show();
            randomize();
        }
        Progress();
    }
    public void save(){
        settings = getApplicationContext().getSharedPreferences("MyUserPrefs", 0);
        int homeScore = settings.getInt("MyTotalScore", 0);
        homeScore+=5;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("MyTotalScore", homeScore);
        editor.apply();
        System.out.println("MyTotalscore in the game: "+homeScore);
    }


    public void OpenMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void Progress(){
        progress.setText((String.valueOf(counter)+"/"+String.valueOf(6)));
        if(counter==6){
            AlertDialog.Builder builder=new AlertDialog.Builder(SeasonQuiz1.this);
            builder.setTitle("Congratulation!");
            builder.setMessage("Result Score: "+String.valueOf(score));
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    OpenMainActivity();
                }
            });
            builder.setPositiveButton("Return to the Menu", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    OpenMainActivity();
                }
            });
            AlertDialog alert=builder.create();
            alert.show();
        }

    }

}