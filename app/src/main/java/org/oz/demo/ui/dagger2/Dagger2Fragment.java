package org.oz.demo.ui.dagger2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.oz.demo.R;
import org.oz.demo.ui.dagger2.di.components.DaggerPaintComponent;

import javax.inject.Inject;

public class Dagger2Fragment extends Fragment {

    private Dagger2ViewModel mViewModel;

    public static Dagger2Fragment newInstance() {
        return new Dagger2Fragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dagger2_fragment, container, false);
    }


    @Inject
    Paint paint;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(Dagger2ViewModel.class);

        DaggerPaintComponent.create().inject(this);

        mViewModel.msg.setValue(paint.getColor().color());

    }

}
