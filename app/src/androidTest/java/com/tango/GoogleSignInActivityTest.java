package com.tango;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by charleschan on 2018-03-18.
 */
public class GoogleSignInActivityTest {

    @Rule
    public ActivityTestRule<GoogleSignInActivity> mActivityTestRule = new ActivityTestRule<>(GoogleSignInActivity.class);
    private GoogleSignInActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchTitle() {
        View viewTitle = mActivity.findViewById(R.string.google_title_text);
        assertNotNull(viewTitle);

    }

    @Test
    public void testLaunchSignInButton() {
        View viewButton = mActivity.findViewById(R.id.sign_in_button);
        assertNotNull(viewButton);

    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

}