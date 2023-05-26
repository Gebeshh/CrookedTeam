package com.wolf.cmpproject001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DaysLesson extends AppCompatActivity {

    Button day_quiz_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_lesson);


        day_quiz_btn=findViewById(R.id.days_quiz);

        day_quiz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDaysActivity();
            }
        });
    }

    public void OpenDaysActivity(){
        Intent intent=new Intent(this,DaysQuiz.class);
        startActivity(intent);
    }
}