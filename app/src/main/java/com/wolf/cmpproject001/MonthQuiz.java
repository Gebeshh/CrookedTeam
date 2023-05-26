package com.wolf.cmpproject001;

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

import java.util.ArrayList;

public class MonthQuiz extends AppCompatActivity {

    Button a1,a2;
    TextView t1,t2,progress;
    int n1,n2;
    int id;
    int score=0;
    float select;
    ArrayList<String> months;
    SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_quiz);

        id=0;

        progress=(TextView) findViewById(R.id.month_progress);
        t1=(TextView) findViewById(R.id.next_month_txt);
        t2=(TextView) findViewById(R.id.month_question);
        a1=(Button) findViewById(R.id.month_a1);
        a2=(Button) findViewById(R.id.month_a2);

        t1.setText("What is the Next Month");
        progress.setText((String.valueOf(counter)+"/"+String.valueOf(6)));

        months =new ArrayList<String>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

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
            restore();
        }

    }

    public void randomize(){
        int random = (int) ((Math.random()*11)+1);

        id=(random+id)%12;
        t2.setText(months.get(id));

        random = (int) ((Math.random()*10)+2);
        select= (float) Math.random();
        if(select>=0.5){
            a1.setText(months.get((id+1)%12));
            a2.setText(months.get((random+id)%12));
            n1=id;
            n2=(random+id)%12;
        }else{
            a2.setText(months.get((id+1)%12));
            a1.setText(months.get((random+id)%12));

            n2=id;
            n1=(random+id)%12;
        }
    }
    public void restore(){

        t2.setText(months.get(id));
        if(select>=0.50){
            a1.setText(months.get((id+1)%12));
            a2.setText(months.get((n2)%12));
        }else{
            a2.setText(months.get((id+1)%12));
            a1.setText(months.get((n1)%12));
        }

    }
    int counter=0;
    public void Check(int n){
        counter++;
        if(n==id){
            score+=5;
            save();
            System.out.println("score: "+score);
            randomize();
        }else{
            Toast.makeText(this, "Upps, Try Again", Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder builder=new AlertDialog.Builder(MonthQuiz.this);
            builder.setTitle("Congratulation!");
            builder.setMessage("Result Score: "+String.valueOf(score));
            builder.setPositiveButton("Return to the Menu", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    OpenMainActivity();
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
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