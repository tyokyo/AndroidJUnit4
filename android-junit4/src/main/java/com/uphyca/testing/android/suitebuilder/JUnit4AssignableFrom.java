/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.uphyca.testing.android.suitebuilder;

import org.junit.runner.Description;

import com.android.internal.util.Predicate;

/**
 * Modified version of android.test.suitebuilder.AssignableFrom.
 */
class JUnit4AssignableFrom implements Predicate<Description> {

    private final Class<?> root;

    JUnit4AssignableFrom(Class<?> root) {
        this.root = root;
    }

    public boolean apply(Description description) {
        return root.isAssignableFrom(description.getTestClass());
    }
}
