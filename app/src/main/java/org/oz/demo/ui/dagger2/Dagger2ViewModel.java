package org.oz.demo.ui.dagger2;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Dagger2ViewModel extends ViewModel {

    public final MutableLiveData<String> msg = new MutableLiveData<>();

}
