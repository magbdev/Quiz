package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String QUIZ_TAG = "MainActivity";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;
    private Button buttonTrue;
    private Button buttonFalse;
    private Button buttonNext;
    private TextView questionTextView;
    private Button buttonPrompt;
    private boolean answerWasShown;

    private Question[] questions = new Question[] {
            new Question(R.string.q_australia,false),
            new Question(R.string.q_england,true),
            new Question(R.string.q_spain,false),
            new Question(R.string.q_holland,true),
            new Question(R.string.q_italy,true),
            new Question(R.string.q_usa,false)
    };
    private int currentIndex =0;

    private void checkAnswerCorrectness(boolean userAnswer)
    {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMassageId = 0;
        if(answerWasShown){
            resultMassageId=R.string.answer_was_shown;
        }
        else {
            if (userAnswer == correctAnswer) {
                resultMassageId = R.string.correct_answer;
            } else {
                resultMassageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this,resultMassageId,Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        buttonTrue=findViewById(R.id.true_button);
        buttonFalse=findViewById(R.id.false_button);
        buttonNext=findViewById(R.id.next_button);
        questionTextView=findViewById(R.id.question_textView);
        buttonPrompt=findViewById(R.id.prompt_button);

        buttonTrue.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                checkAnswerCorrectness(true);
            }
        });
        buttonFalse.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                checkAnswerCorrectness(false);
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                currentIndex = (currentIndex+1)%questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });
        buttonPrompt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            Intent intent = new Intent(MainActivity.this,PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent,REQUEST_CODE_PROMPT);
            }
        });
        setNextQuestion();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG,"Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX,currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) {return;}
        if(requestCode==REQUEST_CODE_PROMPT){
            if(data==null) {return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_ANSWER_EXTRA_SHOWN,false);

        }
    }
}
