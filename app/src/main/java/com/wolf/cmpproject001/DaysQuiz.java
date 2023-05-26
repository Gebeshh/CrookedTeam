package com.wolf.cmpproject001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class DaysQuiz extends AppCompatActivity {

    Button a1,a2;
    TextView t1,t2;
    int n1,n2;
    int id;
    int score=0;
    int counter=0;
    ArrayList<String> days;
    TextView progress;
    SharedPreferences settings;
    float select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_quiz);

        id=0;


        progress=findViewById(R.id.progress_report);
        t1=(TextView) findViewById(R.id.question_txt);
        t2=(TextView) findViewById(R.id.q_txt);
        a1=(Button) findViewById(R.id.a1_btn);
        a2=(Button) findViewById(R.id.a2_btn);

        t1.setText("What is the Next Day");
        progress.setText((String.valueOf(counter)+"/"+String.valueOf(6)));

        days =new ArrayList<String>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(n1);
            }
        });

        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(n2);
            }
        });


        if(savedInstanceState==null){
            randomize();
        }else{
            onRestoreInstanceState(savedInstanceState);
            restore(id,n1,n2);
        }

    }
    public void randomize(){
        int random = (int) ((Math.random()*6)+1);

        id=(random+id)%7;
        t2.setText(days.get(id));

        random = (int) ((Math.random()*5)+2);
        select= (float) Math.random();
        if(select>=0.5){
            a1.setText(days.get((id+1)%7));
            a2.setText(days.get((random+id)%7));
            n1=id;
            n2=(random+id)%7;
        }else{
            a2.setText(days.get((id+1)%7));
            a1.setText(days.get((random+id)%7));

            n2=id;
            n1=(random+id)%7;
        }
    }

    public void restore(int idd,int n11,int n22){

        id=idd;
        t2.setText(days.get(id));
        if(select>=0.50){
            a1.setText(days.get((id+1)%7));
            a2.setText(days.get((n2)%7));
        }else{
            a2.setText(days.get((id+1)%7));
            a1.setText(days.get((n1)%7));
        }


    }

    public void Check(int n){
        counter++;
        if(n==id){
            score+=5;
            save();
            Toast.makeText(this, "YeY", Toast.LENGTH_SHORT).show();
            System.out.println("score: "+score);
            randomize();
        }else{
            Toast.makeText(this, "Upps", Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder builder=new AlertDialog.Builder(DaysQuiz.this);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("id",id);
        outState.putInt("score",score);
        outState.putInt("counter",counter);
        outState.putInt("n1",n1);
        outState.putInt("n2",n2);
        outState.putFloat("select",select);
    }

    protected void onRestoreInstanceState(Bundle savedInstance){
        if(savedInstance!=null){
            id=savedInstance.getInt("id");
            score=savedInstance.getInt("score");
            counter=savedInstance.getInt("counter");
            n1=savedInstance.getInt("n1");
            n2=savedInstance.getInt("n2");
            select=savedInstance.getFloat("select");
            progress.setText((String.valueOf(counter)+"/"+String.valueOf(6)));
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(counter>=6){
            OpenMainActivity();
        }
    }
}