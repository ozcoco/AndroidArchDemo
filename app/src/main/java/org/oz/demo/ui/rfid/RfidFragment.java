package org.oz.demo.ui.rfid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.uhf.scanlable.UHfData;

import org.oz.demo.R;
import org.oz.demo.databinding.RfidFragmentBinding;
import org.oz.demo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RfidFragment extends Fragment {

    private RfidViewModel mViewModel;

    private RfidFragmentBinding mBinding;

    public static RfidFragment newInstance() {
        return new RfidFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.rfid_fragment, container, false);

        mBinding.setLifecycleOwner(this);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //important
        initComponent();

//        initRFID();

    }

    private void initComponent() {

        mViewModel = ViewModelProviders.of(this).get(RfidViewModel.class);

        mBinding.setVm(mViewModel);

    }


    private final BroadcastReceiver keyReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            int keyCode = intent.getIntExtra("keyCode", 0);
            // H941
            if (keyCode == 0) {
                keyCode = intent.getIntExtra("keycode", 0);
            }

            boolean keyDown = intent.getBooleanExtra("keydown", false);

            if (keyCode == KeyEvent.KEYCODE_F3) {
                //todo
                ToastUtils.info(context, String.format(Locale.CHINA, "button : %d", KeyEvent.KEYCODE_F3), Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT).show();

                initRFID();

            }


            if (keyDown) {

                //todo

                ToastUtils.info(context, String.format(Locale.CHINA, "button : %d", keyCode), Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT).show();

                initRFID();

            } else {

                //todo

                ToastUtils.info(context, String.format(Locale.CHINA, "button : %d", keyCode), Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT).show();

            }

        }
    };

    

    @Override
    public void onStart() {
        super.onStart();

        //注册扫码按钮广播接收器，监听扫码按钮事件
        final IntentFilter filter = new IntentFilter();
        filter.addAction("android.rfid.FUN_KEY");
        Objects.requireNonNull(getActivity()).registerReceiver(keyReceiver, filter);

    }


    @Override
    public void onPause() {
        super.onPause();

        //注销扫码按钮广播接收器
        Objects.requireNonNull(getActivity()).unregisterReceiver(keyReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        disconnectUHF();
    }

    /**
     * 初始化RFID读写器
     **/
    private void initRFID() {

        if (connectUHF()) {

            //得到为累计的数据集合
            final Disposable disposable = Observable.create((ObservableOnSubscribe<List<UHfData.InventoryTagMap>>) emitter -> {

                for (; ; ) {

                    //清除累计的数据
                    UHfData.lsTagList.clear();
                    UHfData.dtIndexMap.clear();
                    //检查有效范围内是否有符合协议的电子标签存在
                    UHfData.Inventory_6c(0, 0);

                    final List<UHfData.InventoryTagMap> list = new ArrayList<>(UHfData.lsTagList);

                    final ArrayMap<String, UHfData.InventoryTagMap> map = mViewModel.arrayMapTags.getValue();

                    if (map != null && map.size() > 0 && list.size() > 0) {

                        for (UHfData.InventoryTagMap bean : list) {

                            if (!map.containsKey(bean.strEPC)) {

                                emitter.onNext(list);

                                break;
                            }
                        }

                    } else {
                        emitter.onNext(list);
                    }

                }
            }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(list -> {

                mViewModel.inventoryTagMap.postValue(list);

            }, throwable -> ToastUtils.info(Objects.requireNonNull(getContext()), "未知错误", Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT).show());


        } else {

            Snackbar.make(mBinding.recycler, "连接RFID读写器失败！", Snackbar.LENGTH_SHORT).show();

            initRFID();
        }

    }


    /**
     * @Name connectUHF
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2018/12/20 14:17
     * @Description 通过串口，连接RFID读写器,  return ,true connected, unable connected
     */
    private boolean connectUHF() {

        final int state = UHfData.UHfGetData.OpenUHf("/dev/ttyMT1", 57600);

        //清除累计的数据
        UHfData.lsTagList.clear();
        UHfData.dtIndexMap.clear();

        return state == 0;
    }


    /**
     * @Name disconnectUHF
     * @Params []
     * @Return boolean
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2018/12/20 14:23
     * @Description 断开RFID读写器， return, true 成功，false失败
     */
    private boolean disconnectUHF() {
        UHfData.lsTagList.clear();
        UHfData.dtIndexMap.clear();
        return UHfData.UHfGetData.CloseUHf() == 0;
    }


}
