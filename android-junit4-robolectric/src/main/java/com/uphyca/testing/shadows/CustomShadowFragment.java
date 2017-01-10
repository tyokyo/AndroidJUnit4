package com.uphyca.testing.shadows;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.shadows.ShadowFragment;

@Implements(Fragment.class)
public class CustomShadowFragment extends ShadowFragment {

    private Fragment mTarget;
    private int mTargetRequestCode;

    @Implementation
    public void startActivity(Intent intent) {
        activity.startActivity(intent);
    }

    @Implementation
    public void setTargetFragment(Fragment fragment,
                                  int requestCode) {
        mTarget = fragment;
        mTargetRequestCode = requestCode;
    }

    @Implementation
    public Fragment getTargetFragment() {
        return mTarget;
    }
}
