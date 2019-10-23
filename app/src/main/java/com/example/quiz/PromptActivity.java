package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {

    private boolean correctAnswer;
    private Button showCorrectAnswerButton;
    private TextView answerTextView;
    public static final String KEY_ANSWER_EXTRA_SHOWN = "answerShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER,false);

        showCorrectAnswerButton=findViewById(R.id.showAnswer);
        answerTextView=findViewById(R.id.answer_text_view);
        showCorrectAnswerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            int answer = correctAnswer ? R.string.true_button : R.string.false_button;
            answerTextView.setText(answer);
            setAnswerShownResult(true);

            }
        });

    }
    private void setAnswerShownResult(boolean answerWasShown){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_ANSWER_EXTRA_SHOWN, answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }


}
