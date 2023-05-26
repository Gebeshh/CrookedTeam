package com.wolf.cmpproject001;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PrepositionQuiz extends AppCompatActivity {
    String answer_guess;
    ImageView question;
    int remember=0;
    int score;
    String answer;
    Button check_btn;
    ArrayList<String> resimler;
    SharedPreferences settings;
    TextView progress;
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preposition_quiz);




        progress=findViewById(R.id.preposition_progress);
        progress.setText((String.valueOf(counter)+"/"+String.valueOf(6)));

        Spinner spinnerLanguages=findViewById(R.id.answer_btn_prep);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.preps, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerLanguages.setAdapter(adapter);

        question=findViewById(R.id.imageView2);
        question.setImageResource(R.drawable.behind_empty);
        check_btn=findViewById(R.id.check_btn_prep);

        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer_guess = spinnerLanguages.getSelectedItem().toString();
                System.out.println("answer_guees is : "+answer_guess);
                System.out.println("answer is : "+answer);
                Check();
            }
        });

        resimler =new ArrayList<String>();
        resimler.add("Behind");
        resimler.add("Between");
        resimler.add("In");
        resimler.add("InFrontOf");
        resimler.add("Near");
        resimler.add("Under");
        resimler.add("On");

        answer="Behind";
    }
    public void randomize(){
        int random = (int) (Math.random()*7);

        while(random==remember){
            random = (int) (Math.random()*7);
        }

        System.out.println("random: "+random);
        if(resimler.get(random)=="Behind"){
            question.setImageResource(R.drawable.behind_empty);
            answer="Behind";
        }else if(resimler.get(random)=="Between"){
            question.setImageResource(R.drawable.between_empty);
            answer="Between";
        }else if(resimler.get(random)=="In"){
            question.setImageResource(R.drawable.in_empty);
            answer="In";
        }else if(resimler.get(random)=="InFrontOf"){
            question.setImageResource(R.drawable.in_front_empty);
            answer="InFrontOf";
        }else if(resimler.get(random)=="Near"){
            question.setImageResource(R.drawable.near_empty);
            answer="Near";
        }else if(resimler.get(random)=="Under"){
            question.setImageResource(R.drawable.under_empty);
            answer="Under";
        }else if(resimler.get(random)=="On"){
            question.setImageResource(R.drawable.on_empty);
            answer="On";
        }

        remember=random;
    }
    public void Check(){
        counter++;
        if(answer.equals(answer_guess)){
            score+=5;
            save();
            randomize();
            System.out.println("score"+score);
            Toast.makeText(PrepositionQuiz.this,"Amazing!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(PrepositionQuiz.this,"Upps",Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder builder=new AlertDialog.Builder(PrepositionQuiz.this);
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