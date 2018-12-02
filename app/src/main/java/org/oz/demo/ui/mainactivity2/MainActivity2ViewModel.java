package org.oz.demo.ui.mainactivity2;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import org.oz.demo.po.User;

public class MainActivity2ViewModel extends ViewModel
{

    public final ObservableField<String> msg = new ObservableField<>();

    public final User user = new User();


}
