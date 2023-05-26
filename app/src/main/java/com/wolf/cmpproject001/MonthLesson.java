package com.wolf.cmpproject001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MonthLesson extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_lesson);
        btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMonthQuizActivity();
            }
        });
    }
    public void OpenMonthQuizActivity(){
        Intent intent=new Intent(this,MonthQuiz.class);
        startActivity(intent);
    }
}