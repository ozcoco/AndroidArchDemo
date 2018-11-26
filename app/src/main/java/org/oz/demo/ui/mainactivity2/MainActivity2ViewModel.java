package org.oz.demo.ui.mainactivity2;

import org.oz.demo.viewmodel.UserViewModel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class MainActivity2ViewModel extends ViewModel
{

    public final ObservableField<String> msg = new ObservableField<>();

    public final UserViewModel userViewModel = new UserViewModel();


}
