package com.wolf.cmpproject001;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NumberGame extends AppCompatActivity {

    TextView number,progress;
    EditText guess;
    Button btn;
    int score=0;
    int total;
    int counter=0;
    SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_game);


        progress=findViewById(R.id.number_game_progress);
        number=findViewById(R.id.number_game_number);
        guess=findViewById(R.id.number_game_guess);

        btn=findViewById(R.id.number_game_check_btn);

        progress.setText((String.valueOf(counter)+"/"+String.valueOf(6)));


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });

        randomize();
        Wait();
    }

    public void Wait(){
        guess.setText("");
        guess.setHint("Remember the Number");
        guess.setEnabled(false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                doSomething();
                guess.setEnabled(true);
                guess.setHint("Enter the Number");
            }
        }, 3000);
    }
    public void randomize(){
        int digit1 = (int) ((Math.random()*9)+1)*1000;
        int digit2 = (int) (Math.random()*10)*100;
        int digit3 = (int) (Math.random()*10)*10;
        int digit4 = (int) (Math.random()*10);

        total=digit1+digit2+digit3+digit4;
        number.setText(String.valueOf(total));
    }
    public void doSomething(){
        number.setText("");
    }
    public void Check(){
        counter++;
        if(guess.getText().toString().equals(String.valueOf(total))){
            score+=5;
            save();
            System.out.println("score: "+score);
            randomize();
            guess.setText("");
            Wait();
        }else{
            randomize();
            Wait();
            Toast.makeText(this, "Upps", Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder builder=new AlertDialog.Builder(NumberGame.this);
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
}