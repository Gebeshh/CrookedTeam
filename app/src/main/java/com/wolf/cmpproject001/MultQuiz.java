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

public class MultQuiz extends AppCompatActivity {

    int n1,n2,true_Answer,id_of_true;
    Button a1,a2,a3,a4;
    TextView n1_t,n2_t,progress;
    SharedPreferences settings;
    int r1,r2,r3,r4;
    int score;
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mult_quiz);

        progress=findViewById(R.id.mult_progress);
        n1_t =findViewById(R.id.number1_text);
        n2_t =findViewById(R.id.number2_text);

        a1 =findViewById(R.id.number1);
        a2 =findViewById(R.id.number2);
        a3 =findViewById(R.id.number3);
        a4 =findViewById(R.id.number4);

        progress.setText((String.valueOf(counter)+"/"+String.valueOf(6)));
        if(savedInstanceState==null){
            randomize();
        }else{
            onRestoreInstanceState(savedInstanceState);
        }


        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(0);
            }
        });
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(1);
            }
        });
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(2);
            }
        });
        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check(3);
            }
        });

    }

    public void randomize(){
        int offset;
        n1=(int)(Math.random()*10);
        n2=(int)(Math.random()*10);
        id_of_true=(int)(Math.random()*4);

        true_Answer=(int)(n1*n2);
        n1_t.setText(String.valueOf(n1));
        n2_t.setText(String.valueOf(n2));

        if(id_of_true!=0){
            offset=(int)((Math.random()*10)+1);
            if(offset>5){
                offset=5-offset;
            }
            r1=true_Answer+offset;
            a1.setText(String.valueOf(true_Answer+offset));
        }else{
            r1=true_Answer;
            a1.setText(String.valueOf(true_Answer));
        }
        if(id_of_true!=1){
            offset=(int)((Math.random()*10)+1);
            if(offset>5){
                offset=5-offset;
            }
            r2=true_Answer+offset;
            a2.setText(String.valueOf(true_Answer+offset));
        }
        else{
            r2=true_Answer;
            a2.setText(String.valueOf(true_Answer));
        }
        if(id_of_true!=2){
            offset=(int)((Math.random()*10)+1);
            if(offset>5){
                offset=5-offset;
            }
            r3=true_Answer+offset;
            a3.setText(String.valueOf(true_Answer+offset));
        }
        else{
            r3=true_Answer;
            a3.setText(String.valueOf(true_Answer));

        }
        if(id_of_true!=3){
            offset=(int)((Math.random()*10)+1);
            if(offset>5){
                offset=5-offset;
            }
            r4=true_Answer+offset;
            a4.setText(String.valueOf(true_Answer+offset));

        }else{
            r4=true_Answer;
            a4.setText(String.valueOf(true_Answer));        }

    }
    public void Check(int id){
        counter++;
        if(id==id_of_true){
            score+=5;
            save();
            System.out.println("score: "+score);
            randomize();
        }else{
            Toast.makeText(this,"Upps, Try again",Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder builder=new AlertDialog.Builder(MultQuiz.this);
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

        outState.putInt("true_Answer",true_Answer);
        outState.putInt("score",score);
        outState.putInt("counter",counter);
        outState.putInt("n1",n1);
        outState.putInt("n2",n2);
        outState.putInt("r1",r1);
        outState.putInt("r2",r2);
        outState.putInt("r3",r3);
        outState.putInt("r4",r4);
        outState.putFloat("id_of_true",id_of_true);
    }

    protected void onRestoreInstanceState(Bundle savedInstance){
        if(savedInstance!=null) {
            true_Answer = savedInstance.getInt("true_Answer");
            score = savedInstance.getInt("score");
            counter = savedInstance.getInt("counter");
            n1 = savedInstance.getInt("n1");
            n2 = savedInstance.getInt("n2");
            r1 = savedInstance.getInt("r1");
            r2 = savedInstance.getInt("r2");
            r3 = savedInstance.getInt("r3");
            r4 = savedInstance.getInt("r4");
            a4.setText(String.valueOf(r4));
            a3.setText(String.valueOf(r3));
            a2.setText(String.valueOf(r2));
            a1.setText(String.valueOf(r1));
            id_of_true = savedInstance.getInt("id_of_true");
            progress.setText((String.valueOf(counter) + "/" + String.valueOf(6)));
            n1_t.setText(String.valueOf(n1));
            n2_t.setText(String.valueOf(n2));
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