package com.wolf.cmpproject001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class preposition_lesson extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preposition_lesson);

        btn=(Button) findViewById(R.id.quiz_btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPrepQuizActivity();
            }
        });


    }

    public void OpenPrepQuizActivity(){
        Intent intent=new Intent(this,PrepositionQuiz.class);
        startActivity(intent);
    }
}