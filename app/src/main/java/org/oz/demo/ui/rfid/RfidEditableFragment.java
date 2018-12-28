package org.oz.demo.ui.rfid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uhf.scanlable.UHfData;

import org.oz.demo.R;
import org.oz.demo.databinding.FragmentRfidEditableBinding;

import java.util.Objects;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Name RfidEditableFragment
 * @package org.oz.demo.ui.rfid
 * @Author oz
 * @Email 857527916@qq.com
 * @Time 2018/12/26 14:15
 * @Description todo
 */
public class RfidEditableFragment extends Fragment {

    private FragmentRfidEditableBinding mBinding;

    private RfidViewModel mViewModel;

    public static RfidEditableFragment newInstance() {

        final RfidEditableFragment fragment = new RfidEditableFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rfid_editable, container, false);

        return mBinding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mViewModel.disconnectUHF();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initComponent();

        mViewModel.connectUHF();

        initToolbar();

        initView();

    }

    private void initView() {

        /*** 监听RFID读写区的类型 ***/
        mViewModel.read6cType.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

                final Disposable subscribe = io.reactivex.Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {

                    final byte type = mViewModel.read6cType.get();


//                    UHfData.UHfGetData.Read6C();


                    emitter.onNext(true);

                }).observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).subscribe(msg -> {


                }, throwable -> {


                });

            }
        });


    }


    private void initComponent() {

        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RfidViewModel.class);

        mBinding.setVm(mViewModel);

    }


    private void initToolbar() {

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mBinding.toolbar);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mBinding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());

    }


}
