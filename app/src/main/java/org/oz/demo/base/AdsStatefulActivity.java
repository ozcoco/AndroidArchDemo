package org.oz.demo.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public abstract class AdsStatefulActivity extends BaseActivity {

    private final static String KEY_STATE = "KEY_STATE";

    private final MutableLiveData<State> mState = new MutableLiveData<>();

    @Nullable
    public State getState() {
        return mState.getValue();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {

            mState.setValue((State) savedInstanceState.getSerializable(KEY_STATE));

        } else {

            mState.setValue(State.NORMAL);

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (outState != null)
            outState.putSerializable(KEY_STATE, mState.getValue());

    }

}
