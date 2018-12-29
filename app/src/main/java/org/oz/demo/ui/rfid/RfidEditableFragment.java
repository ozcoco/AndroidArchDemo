package org.oz.demo.ui.rfid;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.uhf.scanlable.UHfData;

import org.oz.demo.R;
import org.oz.demo.databinding.FragmentRfidEditableBinding;
import org.oz.demo.utils.ToastUtils;

import java.util.Locale;
import java.util.Objects;

import cn.pda.serialport.Tools;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Name RfidEditableFragment
 * @package org.oz.demo.ui.rfid
 * @Author oz
 * @Email 857527916@qq.com
 * @Time 2018/12/26 14:15
 * @Description todo
 */
public class RfidEditableFragment extends Fragment
{

    private FragmentRfidEditableBinding mBinding;

    private RfidViewModel mViewModel;

    public static RfidEditableFragment newInstance()
    {

        final RfidEditableFragment fragment = new RfidEditableFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rfid_editable, container, false);

        return mBinding.getRoot();
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();

        mViewModel.disconnectUHF();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        initComponent();

        mViewModel.connectUHF();

        initToolbar();

        initView();

    }

    private void initView()
    {

        /*** 监听RFID读区存储区变化 ***/
        mViewModel.read6cType.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback()
        {

            @Override
            public void onPropertyChanged(Observable sender, int propertyId)
            {

                //读取指定TAG存储区数据
                read();
            }
        });

        /*** 监听写入数据的变化 ***/
        mViewModel.epcData.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback()
        {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId)
            {

                //写入数据到指定TAG存储区
                write();
            }
        });

    }


    /*** UHF读数据 ***/
    public void read()
    {

        Log.e("read ^_*", "read ----->>>--->>>>>>>>>");

        ToastUtils.info(getContext(), "read ----->>>--->>>>>>>>>", Gravity.BOTTOM, Toast.LENGTH_SHORT).show();

        final RfidViewModel vm = mViewModel;

        io.reactivex.Observable.create((ObservableOnSubscribe<Integer>) emitter ->
        {

            Log.e("read ^_*", "reading ----->>>--->>>>>>>>>");

            final UHfData.InventoryTagMap tag = vm.selectedTag.getValue();

            final byte eNum = (byte) (Objects.requireNonNull(tag).strEPC.length() / 4);

            byte[] epc = UHfData.UHfGetData.hexStringToBytes(Objects.requireNonNull(tag).strEPC);

            final EPCNumberType rwMem = vm.rwMem.getValue();

            final byte type = Objects.requireNonNull(rwMem).getType();

            final byte[] wordPtr = Objects.requireNonNull(rwMem).getWordPtr();

            final byte len = Objects.requireNonNull(rwMem).getLen();

            final byte[] password = EPCNumberType.passwd;

            int rstCode = UHfData.UHfGetData.Read6C(eNum, epc, type, wordPtr, len, password);

            Log.e("read ^_*", "readed ----->>>--->>>>>>>>>");

            emitter.onNext(rstCode);

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(code ->
        {

            Log.e("read ^_*", code.byteValue() == UHF_RESULT.SUCCESS ? "读取数据成功" : ("读取数据失败 code:" + code));


            if (code == UHF_RESULT.SUCCESS)
                ToastUtils.success(Objects.requireNonNull(getContext()), "读取数据成功", Gravity.BOTTOM, Toast.LENGTH_SHORT).show();
            else
                ToastUtils.error(Objects.requireNonNull(getContext()), "读取数据失败 code:" + code, Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT).show();

            final byte[] hexData = UHfData.UHfGetData.getRead6Cdata();

            Log.e("read ^_*", "data : ----->>" + new String(hexData));

            if (hexData == null)
                return;

            final String data = Tools.Bytes2HexString(hexData, hexData.length);

            vm.epcData.set(data);

        });


    }


    /*** UHF写数据 ***/
    public void write()
    {

        final RfidViewModel vm = mViewModel;

        if (!vm.isWrite6c.get() || TextUtils.isEmpty(vm.epcData.get()))
            return;

        Log.e("write ^_*", "write ----->>>--->>>>>>>>>");

        ToastUtils.info(getContext(), "write ----->>>--->>>>>>>>>", Gravity.BOTTOM, Toast.LENGTH_SHORT).show();

        io.reactivex.Observable.create((ObservableOnSubscribe<Integer>) emitter ->
        {

            Log.e("write ^_*", "writing ----->>>--->>>>>>>>>");

            final UHfData.InventoryTagMap tag = vm.selectedTag.getValue();

            final byte eNum = (byte) (Objects.requireNonNull(tag).strEPC.length() / 4);

            byte[] epc = UHfData.UHfGetData.hexStringToBytes(Objects.requireNonNull(tag).strEPC);

            final EPCNumberType rwMem = vm.rwMem.getValue();

            final byte type = Objects.requireNonNull(rwMem).getType();

            final byte[] wordPtr = Objects.requireNonNull(rwMem).getWordPtr();

            final byte len = Objects.requireNonNull(rwMem).getLen();

            final byte[] password = EPCNumberType.passwd;

            final byte[] wData = UHfData.UHfGetData.hexStringToBytes(vm.epcData.get());

            int rstCode = 0;

            switch (rwMem)
            {
                case EPC:
                    rstCode = UHfData.UHfGetData.WriteEPC(eNum, password, epc, wData);
                    break;
                default:
                    UHfData.UHfGetData.Write6c(len, eNum, epc, type, wordPtr, wData, password);
                    break;
            }

            emitter.onNext(rstCode);

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(code ->
        {

            Log.e("write ^_*", code == UHF_RESULT.SUCCESS ? "写入数据成功" : ("写入数据失败 code:" + code));

            if (code == UHF_RESULT.SUCCESS)
                ToastUtils.success(Objects.requireNonNull(getContext()), "写入数据成功", Gravity.BOTTOM, Toast.LENGTH_SHORT).show();
            else
                ToastUtils.error(Objects.requireNonNull(getContext()), "写入数据失败 code:" + code, Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT).show();

        });


    }


    private void initComponent()
    {

        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RfidViewModel.class);

        mBinding.setVm(mViewModel);

    }


    private void initToolbar()
    {

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mBinding.toolbar);

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mBinding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).navigateUp());

    }


}
