/*
 * Copyright (C) 2012 uPhyca Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uphyca.testing;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @see android.test.ActivityUnitTestCase
 */
public abstract class ActivityUnitTestCase<T extends Activity> implements InstrumentationSupport {

    @Rule
    public AndroidAnnotatedMethodRule _androidAnnotatedMethodRule;

    private final ActivityUnitTester<T> _tester;

    /**
     * @param activityClass
     * @see android.test.ActivityUnitTestCase#ActivityUnitTestCase(Class)
     */
    public ActivityUnitTestCase(Class<T> activityClass) {
        _tester = new ActivityUnitTester<T>(this, activityClass);
        _androidAnnotatedMethodRule = new AndroidAnnotatedMethodRule(_tester.getInstrumentation());
    }

    /**
     * @throws Exception
     * @see android.test.ActivityUnitTestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {
        _tester.setUp();
    }

    /**
     * @throws Exception
     * @see android.test.ActivityUnitTestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception {
        _tester.tearDown();
    }

    /**
     * @param instrumentation
     * @see android.test.InstrumentationTestCase#injectInstrumentation(android.app.Instrumentation)
     */
    @Override
    public void injectInstrumentation(Instrumentation instrumentation) {
        InstrumentationTestCaseInjector injector = InstrumentationTestCaseInjector.getInstance();
        injector.injectInstrumentation(_tester,
                                       instrumentation);
    }

    /**
     * @param instrumentation
     * @deprecated
     * @see android.test.InstrumentationTestCase#injectInsrumentation(android.app.Instrumentation)
     */
    @Override
    public void injectInsrumentation(Instrumentation instrumentation) {
        injectInstrumentation(instrumentation);
    }

    /**
     * @return
     * @see android.test.InstrumentationTestCase#getInstrumentation()
     */
    @Override
    public Instrumentation getInstrumentation() {
        return _tester.getInstrumentation();
    }

    /**
     * @param pkg
     * @param activityCls
     * @param extras
     * @return
     * @see android.test.InstrumentationTestCase#launchActivity(java.lang.String,
     *      java.lang.Class, android.os.Bundle)
     */
    @SuppressWarnings("hiding")
    public final <T extends Activity> T launchActivity(String pkg,
                                                       Class<T> activityCls,
                                                       Bundle extras) {
        return _tester.launchActivity(pkg,
                                      activityCls,
                                      extras);
    }

    /**
     * @param pkg
     * @param activityCls
     * @param intent
     * @return
     * @see android.test.InstrumentationTestCase#launchActivityWithIntent(java.lang.String,
     *      java.lang.Class, android.content.Intent)
     */
    @SuppressWarnings("hiding")
    public final <T extends Activity> T launchActivityWithIntent(String pkg,
                                                                 Class<T> activityCls,
                                                                 Intent intent) {
        return _tester.launchActivityWithIntent(pkg,
                                                activityCls,
                                                intent);
    }

    /**
     * @return
     * @see android.test.ActivityUnitTestCase#getActivity()
     */
    public T getActivity() {
        return _tester.getActivity();
    }

    /**
     * @param application
     * @see android.test.ActivityUnitTestCase#setApplication(android.app.Application)
     */
    public void setApplication(Application application) {
        _tester.setApplication(application);
    }

    /**
     * @param activityContext
     * @see android.test.ActivityUnitTestCase#setActivityContext(android.content.Context)
     */
    public void setActivityContext(Context activityContext) {
        _tester.setActivityContext(activityContext);
    }

    /**
     * @return
     * @see android.test.ActivityUnitTestCase#getRequestedOrientation()
     */
    public int getRequestedOrientation() {
        return _tester.getRequestedOrientation();
    }

    /**
     * @return
     * @see android.test.ActivityUnitTestCase#getStartedActivityIntent()
     */
    public Intent getStartedActivityIntent() {
        return _tester.getStartedActivityIntent();
    }

    /**
     * @param keysSequence
     * @see android.test.InstrumentationTestCase#sendKeys(java.lang.String)
     */
    public void sendKeys(String keysSequence) {
        _tester.sendKeys(keysSequence);
    }

    /**
     * @return
     * @see android.test.ActivityUnitTestCase#getStartedActivityRequest()
     */
    public int getStartedActivityRequest() {
        return _tester.getStartedActivityRequest();
    }

    /**
     * @return
     * @see android.test.ActivityUnitTestCase#isFinishCalled()
     */
    public boolean isFinishCalled() {
        return _tester.isFinishCalled();
    }

    /**
     * @return
     * @see android.test.ActivityUnitTestCase#getFinishedActivityRequest()
     */
    public int getFinishedActivityRequest() {
        return _tester.getFinishedActivityRequest();
    }

    /**
     * @param r
     * @throws Throwable
     * @see android.test.InstrumentationTestCase#runTestOnUiThread(java.lang.Runnable)
     */
    public void runTestOnUiThread(Runnable r) throws Throwable {
        _tester.runTestOnUiThread(r);
    }

    /**
     * @param keys
     * @see android.test.InstrumentationTestCase#sendKeys(int[])
     */
    public void sendKeys(int... keys) {
        _tester.sendKeys(keys);
    }

    /**
     * @param keys
     * @see android.test.InstrumentationTestCase#sendRepeatedKeys(int[])
     */
    public void sendRepeatedKeys(int... keys) {
        _tester.sendRepeatedKeys(keys);
    }

    /**
     * @param testCaseClass
     * @throws IllegalAccessException
     * @see com.uphyca.testing.ActivityUnitTester#scrubClass(java.lang.Class)
     */
    protected void scrubClass(Class<?> testCaseClass) throws IllegalAccessException {
        _tester.scrubClass(testCaseClass);
    }

    /**
     * @param testActivity
     * @see com.uphyca.testing.ActivityUnitTester#setActivity(android.app.Activity)
     */
    protected void setActivity(Activity testActivity) {
        _tester.setActivity(testActivity);
    }

    /**
     * @param intent
     * @param savedInstanceState
     * @param lastNonConfigurationInstance
     * @return
     * @see com.uphyca.testing.ActivityUnitTester#startActivity(android.content.Intent,
     *      android.os.Bundle, java.lang.Object)
     */
    protected T startActivity(Intent intent,
                              Bundle savedInstanceState,
                              Object lastNonConfigurationInstance) {
        return _tester.startActivity(intent,
                                     savedInstanceState,
                                     lastNonConfigurationInstance);
    }
}
