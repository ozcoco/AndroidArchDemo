package org.oz.demo.ui.mainactivity2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.oz.demo.BR;
import org.oz.demo.MainActivity;
import org.oz.demo.R;
import org.oz.demo.databinding.MainActivity2FragmentBinding;
<<<<<<< HEAD
import org.oz.demo.ui.NotificationActivity;
=======
import org.oz.demo.ui.Dagger2Activity;
>>>>>>> 1ce99c20516e999a80ea45d3ae8d5bb541a54f97
import org.oz.demo.ui.PagingActivity;
import org.oz.demo.ui.RFIDActivity;

public class MainActivity2Fragment extends Fragment
{

    private MainActivity2ViewModel mViewModel;

    private MainActivity2FragmentBinding mBinding;

    private final Handles handles = new Handles();

    public class Handles
    {

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onSelectDate(View v)
        {
            selectDate();

        }


        public void onSkip(View v)
        {
            startActivityForResult(new Intent(getActivity(), MainActivity.class), 1000);
        }


        public void onTestPaging(View v) {

            startActivity(new Intent(getContext(), PagingActivity.class));

        }

        public void onRFID(View v) {

            startActivity(new Intent(getContext(), RFIDActivity.class));

        }

<<<<<<< HEAD
        public void onNotification()
        {
            startActivity(new Intent(getContext(), NotificationActivity.class));
        }

=======
        public void onDagger2(View v) {

            startActivity(new Intent(getContext(), Dagger2Activity.class));

        }


>>>>>>> 1ce99c20516e999a80ea45d3ae8d5bb541a54f97
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectDate()
    {

        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());

        datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) ->
        {

            final String date = "" + year + month + dayOfMonth;

            mViewModel.dateViewModel.setTime(date);

        });

        datePickerDialog.show();

    }


    public static MainActivity2Fragment newInstance()
    {
        return new MainActivity2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.main_activity2_fragment, container, false);

        mBinding.setLifecycleOwner(this);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MainActivity2ViewModel.class);

        mBinding.setVm(mViewModel);

        mBinding.setHandles(handles);

        mViewModel.user.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback()
        {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId)
            {
                if (propertyId == BR.name)
                    Toast.makeText(getActivity(), mViewModel.user.getName(), Toast.LENGTH_SHORT).show();


            }
        });


        mViewModel.getUserData().observe(this, user -> mBinding.message2.setText(user.toString()));


    }

}
