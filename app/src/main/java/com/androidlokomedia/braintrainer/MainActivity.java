package com.androidlokomedia.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView jawabanTextView;
    TextView sumTextview;
    TextView point;
    Button playAgain;
    Button button0, button1, button2, button3;
    ArrayList<Integer>  jawaban = new ArrayList<Integer>();
    int locationCorrectAnswer;
    int score = 0;
    int numberOfQuitions=0;
    TextView timer;
    RelativeLayout gameRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startButton = (Button)findViewById(R.id.startButton);
        sumTextview = (TextView) findViewById(R.id.tambahTextView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        jawabanTextView = (TextView)findViewById(R.id.scoreTextView);
        point = (TextView)findViewById(R.id.pointTextView);
        timer = (TextView)findViewById(R.id.timerTextView);
        playAgain = (Button)findViewById(R.id.playButton);
//        playAgain.setVisibility(View.INVISIBLE);
        gameRelativeLayout = (RelativeLayout)findViewById(R.id.gameRelativeLayout);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void playAgain(View view){
        score = 0;
        numberOfQuitions = 0;
        timer.setText("30s");
        point.setText("0/0");
        jawabanTextView.setText("");
        generateQuestion();

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                timer.setText(String.valueOf(l/1000));
                playAgain.setVisibility(View.INVISIBLE);
                button0.setVisibility(View.VISIBLE);
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                timer.setText("0s");
                jawabanTextView.setText("Your Score "+Integer.toString(score)+"/"+Integer.toString(numberOfQuitions));
                playAgain.setVisibility(View.VISIBLE);
//                gameRelativeLayout.setVisibility(View.INVISIBLE);
                button0.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                generateQuestion();
            }
        }.start();
    }

    public void startClick(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);
        playAgain(findViewById(R.id.playButton));
    }

    public void generateQuestion(){
        Random random = new Random();
        int a = random.nextInt(21);
        int b = random.nextInt(21);
//        playAgain.setVisibility(View.INVISIBLE);

        sumTextview.setText(Integer.toString(a) + " + "+Integer.toString(b));

        locationCorrectAnswer = random.nextInt(4);
        Log.i("locationcorrectanswer", "generateQuestion: "+locationCorrectAnswer);
        jawaban.clear();

        int incorectAnsewer;
        for (int i = 0;  i< 4; i++) {
            if (i == locationCorrectAnswer) {
                Log.i("correctanswer", "lokasi: "+locationCorrectAnswer+" banding i: "+i);
                jawaban.add(a+b);
            } else {
                incorectAnsewer = random.nextInt(41);
                Log.i("incorect", "lokasi: "+incorectAnsewer+" banding i: "+i);
                while (incorectAnsewer ==  a + b) {
                    Log.i("incorect", "lokasi: "+incorectAnsewer+" banding i: "+i);
                    incorectAnsewer =  random.nextInt(41);
                }
                jawaban.add(incorectAnsewer);
            }
        }

        button0.setText(Integer.toString(jawaban.get(0)));
        button1.setText(Integer.toString(jawaban.get(1)));
        button2.setText(Integer.toString(jawaban.get(2)));
        button3.setText(Integer.toString(jawaban.get(3)));
    }

    public void pilihJawaban(View view){
       Log.i("Tag", "pilihJawaban: "+(String)view.getTag());
//        boolean bolSatu = objSatu.equals(objDua);
//        boolean bolDua  = (objSatu == objDua);
//        System.out.println("data1=data2 adalah "+data1.equals(data2));
        if (view.getTag().toString().equals(Integer.toString(locationCorrectAnswer))) {
            Log.i("correct", "pilihJawaban: coorect "+view.getTag().toString().equals(Integer.toString(locationCorrectAnswer))+ " "+locationCorrectAnswer);
            score++;
            jawabanTextView.setText("correct");
        } else {
            jawabanTextView.setText("wrong!!");
        }

        numberOfQuitions++;
        point.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuitions));
        generateQuestion();
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
