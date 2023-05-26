package com.wolf.cmpproject001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClockLesson extends AppCompatActivity {

    Button start_clock_quiz_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_lesson);

        start_clock_quiz_btn=(Button) findViewById(R.id.clock_lesson_quiz);

        start_clock_quiz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSeasonQuiz();
            }
        });
    }
    public void OpenSeasonQuiz(){
        Intent intent=new Intent(this,ClockQuiz1.class);
        startActivity(intent);
    }
}