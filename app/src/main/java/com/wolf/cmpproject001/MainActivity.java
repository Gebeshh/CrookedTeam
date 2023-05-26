package com.wolf.cmpproject001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    Button season_lesson_btn;
    Button clock_lesson_btn;
    Button month_btn;
    Button mult_btn;
    Button pre_btn;
    Button number_btn;
    Button days_btn;

    TextView text;
    SharedPreferences settings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Client.getInstance();

        mult_btn=findViewById(R.id.mult_btn);
        pre_btn=findViewById(R.id.preposition_btn);
        season_lesson_btn=(Button) findViewById(R.id.season_lesson_btn);
        month_btn=findViewById(R.id.month_lesson_btn);
        clock_lesson_btn=(Button) findViewById(R.id.clock_lesson_btn);
        number_btn=(Button) findViewById(R.id.numbers);
        days_btn=(Button) findViewById(R.id.days);



        text =(TextView) findViewById(R.id.userName);


        System.out.println("bak burdayım: "+Client.instance.getUserID());

        number_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenNumberctivity();
            }
        });
        days_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDaysActivity();
            }
        });
        mult_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMultActivity();
            }
        });
        month_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMonthActivity();
            }
        });
        pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPrepActivity();
            }
        });

        season_lesson_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Curnet user client score is : "+Client.instance.getEmail());
                text.setText(Client.instance.getEmail());
                OpenSeasonActivity();

            }
        });

        clock_lesson_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenClockActivity();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////
        //yaziyor
        //settings = getApplicationContext().getSharedPreferences("MyUserPrefs", 0);
        //SharedPreferences.Editor editor = settings.edit();
        //editor.putInt("MyTotalScore", any_score);
        //editor.apply();
        // Get from the SharedPreferences
        //getiriyor
        settings = getApplicationContext().getSharedPreferences("MyUserPrefs", 0);
        int homeScore = settings.getInt("MyTotalScore", 0);
        System.out.println("MyTotalscore int the main activity: "+homeScore);
        text.setText(String.valueOf(homeScore));
        ///////////////////////////////////////////////////////////////////////////////////////
    }
    public void OpenDaysActivity(){
        Intent intent=new Intent(this,DaysLesson.class);
        startActivity(intent);
    }
    public void OpenSeasonActivity(){
        Intent intent=new Intent(this,Seasons.class);
        startActivity(intent);
    }
    public void OpenClockActivity(){
        Intent intent=new Intent(this,ClockLesson.class);
        startActivity(intent);
    }
    public void OpenPrepActivity(){
        Intent intent=new Intent(this,preposition_lesson.class);
        startActivity(intent);
    }
    public void OpenMonthActivity(){
        Intent intent=new Intent(this,MonthLesson.class);
        startActivity(intent);
    }
    public void OpenMultActivity(){
        Intent intent=new Intent(this,MultLesson.class);
        startActivity(intent);
    }
    public void OpenNumberctivity(){
        Intent intent=new Intent(this,NumberGame.class);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    protected void onResume(){
        super.onResume();
        settings = getApplicationContext().getSharedPreferences("MyUserPrefs", 0);
        int homeScore = settings.getInt("MyTotalScore", 0);
        System.out.println("MyTotalscore int the main activity: "+homeScore);
        text.setText("Score: "+String.valueOf(homeScore));
    }
}