package org.oz.demo.ui.mainactivity2;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.oz.demo.BR;
import org.oz.demo.R;
import org.oz.demo.databinding.MainActivity2FragmentBinding;
import org.oz.demo.po.User;

public class MainActivity2Fragment extends Fragment {

    private MainActivity2ViewModel mViewModel;

    private MainActivity2FragmentBinding mBinding;

    public static MainActivity2Fragment newInstance() {
        return new MainActivity2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.main_activity2_fragment, container, false);

        mBinding.setLifecycleOwner(this);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MainActivity2ViewModel.class);

        mBinding.setVm(mViewModel);

        mViewModel.user.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == BR.name)
                    Toast.makeText(getActivity(), mViewModel.user.getName(), Toast.LENGTH_SHORT).show();


            }
        });


        mViewModel.userData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                

            }
        });


    }

}
