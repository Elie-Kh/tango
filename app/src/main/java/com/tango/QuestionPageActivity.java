package com.tango;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tango.models.QuestionModel;
import com.tango.models.User;

import java.util.HashMap;
import java.util.Map;

public class QuestionPageActivity extends BaseActivity {

    private static final String TAG = "QuestionPageActivity";
    private static final String REQUIRED = "Required";

    // Initialize DataBase
    private DatabaseReference rootDB;


    private EditText QuestionTitle;
    private EditText QuestionBody;
    private FloatingActionButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Set rootDB to root of FireBase DataBase
        rootDB = FirebaseDatabase.getInstance().getReference();

        // Initialize Fields
        QuestionTitle = findViewById(R.id.field_title);
        QuestionBody = findViewById(R.id.field_body);
        submitButton = findViewById(R.id.fab_submit_post);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    /**
     * SubmitPost()
     * Checks if all fields have a valid input / not Empty
     * Add new question to the DataBase
     */
    private void submitPost() {
        final String title = QuestionTitle.getText().toString();
        final String body = QuestionBody.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            QuestionTitle.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            QuestionBody.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // Create an EventListener that will write the new question to DB
        final String userId = getUid();
        rootDB.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(QuestionPageActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new question
                            writeNewPost(userId, user.username, title, body);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());

                        setEditingEnabled(true);
                    }
                });
    }

    private void setEditingEnabled(boolean enabled) {
        QuestionTitle.setEnabled(enabled);
        QuestionBody.setEnabled(enabled);
        if (enabled) {
            submitButton.setVisibility(View.VISIBLE);
        } else {
            submitButton.setVisibility(View.GONE);
        }
    }

    // Writes the actual post to the DB
    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new questionModel at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = rootDB.child("posts").push().getKey();
        QuestionModel questionModel = new QuestionModel(userId, username, title, body);
        Map<String, Object> postValues = questionModel.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        rootDB.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}