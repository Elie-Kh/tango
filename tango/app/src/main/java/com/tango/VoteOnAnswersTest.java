package com.tango;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class VoteOnAnswersTest extends AppCompatActivity {

    private int pointValue;

    //the Vote enum is used to check the status of the vote
    enum Vote
    {
        UNSELECTED, UPVOTED, DOWNVOTED;
    }
    private Vote vote = Vote.UNSELECTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_on_answers_test);

        //TODO get value from database, dont just assign 0
        pointValue = 0;

        //the pointage to be displayed is the Textview
        final TextView pointQty = (TextView) findViewById(R.id.pointQty);

        //instantiate the buttonimages (the up and down arrows)
        final ImageButton upvoteButton= (ImageButton) findViewById(R.id.upvote_button);
        final ImageButton downvoteButton= (ImageButton) findViewById(R.id.downvote_button);


        //display correct pointage
        pointQty.setText(Integer.toString(pointValue));

        //occurs when up arrow is pressed
        upvoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //If no votes yet, upvoting will add a point.
                if(vote == Vote.UNSELECTED) {
                    upvoteButton.setImageResource(R.drawable.ic_up_arrow_selected);
                    pointQty.setText(Integer.toString(++pointValue));
                    vote = Vote.UPVOTED;
                }
                //if downvoted, upvoting adds 2 points
                else if(vote == Vote.DOWNVOTED){
                    upvoteButton.setImageResource(R.drawable.ic_up_arrow_selected);
                    downvoteButton.setImageResource(R.drawable.ic_down_arrow_unselected);
                    pointValue++;
                    pointQty.setText(Integer.toString(++pointValue));
                    vote = Vote.UPVOTED;
                }
                //if already upvoted, upvoting again reverts to unselected, so removes a point
                else if(vote == Vote.UPVOTED){
                    upvoteButton.setImageResource(R.drawable.ic_up_arrow_unselected);
                    pointQty.setText(Integer.toString(--pointValue));
                    vote = Vote.UNSELECTED;

                }
            }
        });
        //occurs when down arrow is pressed
        downvoteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            //If no votes yet, downvoting will remove a point.
                if(vote == Vote.UNSELECTED) {
                downvoteButton.setImageResource(R.drawable.ic_down_arrow_selected);
                pointQty.setText(Integer.toString(--pointValue));
                vote = Vote.DOWNVOTED;
            }
            //if upvoted, downvoting removes 2 points
                else if(vote == Vote.UPVOTED){
                upvoteButton.setImageResource(R.drawable.ic_up_arrow_unselected);
                downvoteButton.setImageResource(R.drawable.ic_down_arrow_selected);
                pointValue--;
                pointQty.setText(Integer.toString(--pointValue));
                vote = Vote.DOWNVOTED;
            }
            //if already downvoted, downvoting again reverts to unselected, so adds a point
                else if(vote == Vote.DOWNVOTED) {
                    downvoteButton.setImageResource(R.drawable.ic_down_arrow_unselected);
                    pointQty.setText(Integer.toString(++pointValue));
                    vote = Vote.UNSELECTED;
                }
            }
        });
    }


}
