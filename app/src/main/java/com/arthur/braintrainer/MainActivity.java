package com.arthur.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    GridLayout answerOptions;
    TextView timer,score,question;
    Button startButton;
    Button[]answerChoices;
    int answer,firstNumber,secondNumber,operationInt,correctOption;
    char operation;
    Random randomGenerator;
    int[]choices;
    CountDownTimer countDown;
    int numbersCorrect;
    int totalQuestions;

    public void startGame(View startB){
        countDown.start();
        numbersCorrect=0;
        totalQuestions=0;
        answerOptions.setVisibility(View.VISIBLE);
        timer.setVisibility(View.VISIBLE);
        score.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.GONE);
        question.setVisibility(View.VISIBLE);
        score.setText(numbersCorrect+"/"+totalQuestions);
        //need to generate a question
        generateQuestion();
        generateAnswers();
    }
    public void selectAnswer(View answerChoice){
        totalQuestions++;
        Button chosen=(Button)answerChoice;
        int chosenAnswer=Integer.valueOf(chosen.getText().toString());
        if(chosenAnswer==answer){
            //Log.i("answer", "correct!");
            numbersCorrect++;
        }
        else{
            //Toast.makeText(getApplicationContext(),"Wrong!",Toast.LENGTH_SHORT).show();
        }
        score.setText(numbersCorrect+"/"+totalQuestions);
        generateQuestion();
        generateAnswers();
    }
    public void generateQuestion(){
        //should use random generator. 2 for numbers. 1 for operation
        //value to store correct Value;
        for(int a:choices){
            a=-1;
        }
        firstNumber=randomGenerator.nextInt(21);
        secondNumber=randomGenerator.nextInt(20)+1;
        if(secondNumber>firstNumber)secondNumber=secondNumber-firstNumber;
        operationInt=randomGenerator.nextInt(4);
        if(operationInt==0){
            operation='+';
            answer=firstNumber+secondNumber;
        }
        else if(operationInt==1){
            operation='-';
            answer=firstNumber-secondNumber;
        }
        else if(operationInt==2){
            operation='x';
            answer=firstNumber*secondNumber;
        }
        else {
            operation='/';
            if(firstNumber%secondNumber!=0){
                firstNumber=firstNumber/secondNumber*secondNumber;
            }
            answer=firstNumber/secondNumber;
        }
        question.setText(firstNumber + "" + operation + "" + secondNumber + " = ?");
    }
    public void generateAnswers(){

        correctOption=randomGenerator.nextInt(4);
        choices[correctOption]=answer;

        for(int i=0;i<4;i++){
            if(correctOption!=i){
                choices[i]=randomGenerator.nextInt(50);
                if(choices[i]==answer){
                    choices[i]=choices[i]+randomGenerator.nextInt(10);
                }
            }
            answerChoices[i].setText(String.valueOf(choices[i]));
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize
        answerChoices=new Button[4];
        answerChoices[0]=(Button)findViewById(R.id.op1);
        answerChoices[1]=(Button)findViewById(R.id.op2);
        answerChoices[2]=(Button)findViewById(R.id.op3);
        answerChoices[3]=(Button)findViewById(R.id.op4);


        choices=new int[4];
        randomGenerator=new Random();
        answerOptions=(GridLayout)findViewById(R.id.answerChoice);
        timer=(TextView)findViewById(R.id.timer);
        score=(TextView)findViewById(R.id.scoreBoard);
        startButton=(Button)findViewById(R.id.startButton);
        question=(TextView)findViewById(R.id.question);

        //timer
        countDown=new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf((int)millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                answerOptions.setVisibility(View.GONE);
                startButton.setVisibility(View.VISIBLE);
                startButton.setText("Try Again");
                question.setText("Score: "+numbersCorrect+"/"+totalQuestions);
                timer.setText(0);
            }
        };


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
