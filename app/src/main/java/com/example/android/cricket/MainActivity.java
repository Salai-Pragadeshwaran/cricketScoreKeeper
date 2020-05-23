package com.example.android.cricket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int score = 0,
    wickets = 0,
    target = 0,
    maxOver = 0,
    maxWicket = 0;
    int ballNumber = 0,
    overNumber = 0 ,
    stop = 0 ;
    boolean innings ;


    public void setInningValue () {
        if(maxOver == (overNumber + 1) && ballNumber == 6){
            innings = true ;
        }
        else  innings = false;
    }

    /*
    Score Increment
     */
    public void increase (int increase){
        if(!innings && (maxWicket == 0 || wickets < maxWicket)) {
            score += increase;
            setBallCount(0);
            displayScore();
            setInningValue();
        }
        message();
    }

    /*
     To display Win Message
     */
    public void message () {
        if(target != 0 && (target - 1 ) == score && stop == 0 && ( (ballNumber == 6 && overNumber == maxOver - 1) || ( maxWicket != 0 && wickets == maxWicket)) ){
            Toast.makeText(getApplicationContext(), " Match Draw ! ", Toast.LENGTH_SHORT).show();
            ++stop ;
        }
        if(target != 0 && target > score && stop == 0 && ( (ballNumber == 6 && overNumber == maxOver - 1) || ( maxWicket != 0 && wickets == maxWicket))){
            Toast.makeText(getApplicationContext(), "Current Bowling team Wins!", Toast.LENGTH_SHORT).show();
            ++stop ;
            innings = true ;
        }
        if(target != 0 && target > score && ballNumber == 6 && overNumber == maxOver - 1 && stop == 0){
            Toast.makeText(getApplicationContext(), "Current Bowling team Wins!", Toast.LENGTH_SHORT).show();
            ++stop ;
        }
        if(score >= target && target != 0 && stop == 0){
            Toast.makeText(getApplicationContext(), "Current Batting team Wins!", Toast.LENGTH_SHORT).show();
            ++stop ;
        }
    }

    public void increase0 (View View){
       increase(0);
    }

    public void increase1 (View View){
        increase(1);
    }

    public void increase2 (View View){
        increase(2);
    }

    public void increase3 (View View){
        increase(3);
    }

    public void increase4 (View View){
        increase(4);
    }

    public void increase6 (View View){
        increase(6);
    }

    /*
    Extras
     */
    public void wide (View View){
        score += 1;
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("" + score + "/" + wickets);
        runRate();
    }

    public void noBall (View View){
        if(ballNumber == 0){
            return;
        }
        setBallCount(-2);
        runRate();
    }

    /*
          Wicket taken !
    */
    public void wicket (View View){
        if(!innings && (maxWicket == 0 || wickets < maxWicket)) {
            wickets += 1;
            setBallCount(0);
            displayScore();
            setInningValue();
        }
        message();
    }

    /*
    To display Run Rate
     */
    public void runRate () {

        int curRunRate = (score * 600/ ( (overNumber*6) + ballNumber)) ;
        int a = curRunRate%100 ,
                b = curRunRate/100 ;
        TextView currentRunRate = (TextView) findViewById(R.id.currentRunRate);
        currentRunRate.setText("Crnt RunRate: " + b + "." + a);
        int ballsLeft = (( maxOver - overNumber ) * 6 )- (ballNumber)   ;
        if(target > 0){
            TextView toWin = (TextView) findViewById(R.id.toWin);
            int runsLeft = target - score ;
            if(runsLeft < 0){
                runsLeft = 0;
            }

            if(maxOver > 0) {

                toWin.setText("" + runsLeft + " Runs left to win in " + (ballsLeft) +" Balls");
                TextView requiredRunRate = (TextView) findViewById(R.id.requiredRunRate);
                int reqRunRate = (runsLeft * 600) / ballsLeft;
                int c = reqRunRate % 100,
                        d = reqRunRate / 100;
                requiredRunRate.setText("Rqrd RunRate: " + d + "." + c);
            }
            else toWin.setText("" + runsLeft + " Runs left to win");
        }
    }

    /*
     To Display score
     */
    public void displayScore (){
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("" + score + "/" + wickets);
        runRate();
    }

/*
to indicate the overs and number of balls
 */
    public void setBallCount(int x){
        ballNumber += 1 + x ;
        if(ballNumber > 6){
            ballNumber = 1;
            overNumber += 1;
            TextView overView = (TextView) findViewById(R.id.Overs);
            overView.setText("" + overNumber);
        }
        setBallBackgroundColor(ballNumber);
    }


    /*
    To set target at the end of an inning
     */
    public void inningsOver (View View){
        target = score + 1;
        innings = false ;
        commonReset();
    }

    /**
     * to reset the scores to 0
     */
    public void makeScore0 (View View){
        target = 0;
        commonReset();
    }

    public void commonReset () {
        score = 0;
        wickets = 0;
        overNumber = 0;
        stop = 0 ;

        TextView overView = (TextView) findViewById(R.id.Overs);
        overView.setText("" + overNumber);
        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("" + score);
        TextView targetView = (TextView) findViewById(R.id.target);
        targetView.setText("" + target);
        TextView toWin = (TextView) findViewById(R.id.toWin);
        toWin.setText("");
        TextView requiredRunRate = (TextView) findViewById(R.id.requiredRunRate);
        requiredRunRate.setText("");
        TextView currentRunRate = (TextView) findViewById(R.id.currentRunRate);
        currentRunRate.setText("");
        ballNumber = -1;
        setBallCount(0);
    }

/**
*To count the balls of an over " THROUGH THE CHANGE IN COLORS "
 */
    public void setBallBackgroundColor(int ballNumber) {
        switch (ballNumber){
            case 0 : View view = findViewById(R.id.ball1);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball2);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball3);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball4);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball5);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball6);
                view.setBackgroundColor(GRAY);
                break;
            case 1 : view = findViewById(R.id.ball1);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball2);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball3);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball4);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball5);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball6);
                view.setBackgroundColor(GRAY);
                break;
            case 2 : view = findViewById(R.id.ball1);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball2);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball3);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball4);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball5);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball6);
                view.setBackgroundColor(GRAY);
                break;
            case 3 : view = findViewById(R.id.ball1);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball2);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball3);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball4);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball5);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball6);
                view.setBackgroundColor(GRAY);
                break;
            case 4 : view = findViewById(R.id.ball1);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball2);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball3);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball4);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball5);
                view.setBackgroundColor(GRAY);
                view = findViewById(R.id.ball6);
                view.setBackgroundColor(GRAY);
                break;
            case 5 : view = findViewById(R.id.ball1);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball2);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball3);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball4);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball5);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball6);
                view.setBackgroundColor(GRAY);
                break;
            case 6 : view = findViewById(R.id.ball1);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball2);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball3);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball4);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball5);
                view.setBackgroundColor(BLACK);
                view = findViewById(R.id.ball6);
                view.setBackgroundColor(BLACK);
                break;
            default: break;

        }

    }

    /**
     * This method displays the No of overs which is to be played
     */
    private void displayMaxOver(int maxOver){
        TextView overTextView = (TextView) findViewById(
                R.id.over_text_view);
        overTextView.setText(""+ maxOver);
    }


    public void incrementMaxOver(View View){
        maxOver = maxOver + 1 ;
        if(maxOver > 100) maxOver = 100 ;
        displayMaxOver(maxOver);
    }
    public void decrementMaxOver(View View){
        maxOver = maxOver - 1 ;
        if(maxOver < 0) maxOver = 0 ;
        displayMaxOver(maxOver) ;
    }

    /**
     * This method displays the No of Wickets on each team
     */
    private void displayMaxWicket(int maxWicket){
        TextView wicketTextView = (TextView) findViewById(
                R.id.wicket_text_view);
        wicketTextView.setText(""+ maxWicket);
    }


    public void incrementMaxWicket(View View){
        maxWicket = maxWicket + 1;
        if(maxWicket > 100) maxWicket = 100;
        displayMaxWicket(maxWicket);
    }
    public void decrementMaxWicket(View View){
        maxWicket = maxWicket - 1;
        if(maxWicket < 0) maxWicket = 0 ;
        displayMaxWicket(maxWicket);
    }
}
