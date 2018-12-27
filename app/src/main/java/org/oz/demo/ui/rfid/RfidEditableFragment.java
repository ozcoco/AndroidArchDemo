package org.oz.demo.ui.rfid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.oz.demo.R;
import org.oz.demo.databinding.FragmentRfidEditableBinding;

import java.util.Objects;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rfid_editable, container, false);

        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initComponent();

        initToolbar();
    }


    private void initComponent() {

        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RfidViewModel.class);

    }


    private void initToolbar() {

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mBinding.toolbar);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mBinding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
