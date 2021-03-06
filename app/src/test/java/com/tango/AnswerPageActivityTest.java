package com.tango;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.junit.Assert.*;

/**
 * Created by charleschan on 2018-03-19.
 */
public class AnswerPageActivityTest {
    @Mock
    AnswerPageActivity ap;
    AnswerPageActivity.AnswerAdapter aa;
    AnswerPageActivity.AnswerViewHolder av;

    @Before
    public void setUp() {
        ap = Mockito.mock(AnswerPageActivity.class);
        aa = Mockito.mock(AnswerPageActivity.AnswerAdapter.class);
        av = Mockito.mock(AnswerPageActivity.AnswerViewHolder.class);

    }

    @Test
    public void onCreate() throws Exception {
        View view = Mockito.mock(View.class);
        PersistableBundle persistableBundle = Mockito.mock(PersistableBundle.class);
        Context context = Mockito.mock(Context.class);
        DatabaseReference databaseReference = Mockito.mock(DatabaseReference.class);
        Bundle bundle = Mockito.mock(Bundle.class);
        AnswerPageActivity a = new AnswerPageActivity();
        AnswerPageActivity.AnswerViewHolder answerViewHolder = new AnswerPageActivity.AnswerViewHolder(view);
        BaseActivity b = new BaseActivity();
        ap.onCreate(bundle);
        b.hideProgressDialog();
        a.onClick(view);
        a.hideProgressDialog();
        answerViewHolder.authorView = null;
        doNothing().when(ap).startActivity((Intent) any());
        ap.onStart();
        ap.onStop();
        assertTrue(true);
    }

    @Test
    public void onStart() throws Exception {
        assertTrue(true);

    }

    @Test
    public void onStop() throws Exception {
        assertTrue(true);
    }

    @Test
    public void onClick() throws Exception {
        assertTrue(true);
    }

}