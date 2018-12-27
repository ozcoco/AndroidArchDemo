package org.oz.demo.ui.rfid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.uhf.scanlable.UHfData;

import org.oz.demo.R;
import org.oz.demo.base.view.DecorativeAdapter;
import org.oz.demo.databinding.ItemRfidBinding;
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

    boolean isScan = false;

    boolean isConnected = false;

    long startPressTime = 0;
    long endPressTime = 0;


    int mode = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }


    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_rfid, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_clear) {

            mViewModel.inventoryTagMap.setValue(new ArrayList<>());

        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.rfid_fragment, container, false);

        mBinding.setLifecycleOwner(this);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //important
        initComponent();

        isConnected = connectUHF();

        initToolbar();

        initRecycler();

        initView();

    }


    private void initRecycler() {

        final RecyclerView recycler = mBinding.recycler;

        final class RecyclerHolder extends RecyclerView.ViewHolder {

            final ItemRfidBinding binding;

            public RecyclerHolder(@NonNull View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }
        }

        final DecorativeAdapter<RecyclerHolder, UHfData.InventoryTagMap> adapter = new DecorativeAdapter<>(recycler.getContext(), new DecorativeAdapter.IAdapterDecorator<RecyclerHolder, UHfData.InventoryTagMap>() {

            @Override
            public RecyclerHolder onCreateViewHolder(@NonNull Context context, @NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {

                return new RecyclerHolder(inflater.inflate(R.layout.item_rfid, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull Context context, @NonNull RecyclerHolder holder, @NonNull UHfData.InventoryTagMap data, int position) {

                holder.binding.setBean(data);

                holder.binding.getRoot().setOnClickListener(v -> {

                    mViewModel.itemClickPosition.setValue(position);

                    ToastUtils.info(Objects.requireNonNull(getContext()), String.format(Locale.CHINA, "position: %d", position), Gravity.BOTTOM, Toast.LENGTH_SHORT).show();

                    Navigation.findNavController(v).navigate(R.id.action_rfid_to_editable);

                });

            }
        });

        adapter.setData(mViewModel.itemData.getValue());

        recycler.setLayoutManager(new LinearLayoutManager(recycler.getContext()));

        recycler.setAdapter(adapter);

        mViewModel.itemData.observe(this, adapter::setData);

    }


    private void initToolbar() {

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mBinding.toolbar);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mBinding.toolbar.setNavigationOnClickListener(v -> getActivity().finish());

    }

    private void initView() {

        /*** 设置功率 ***/
        mViewModel.power.observe(this, power ->
        {

            if (isConnected) {

                UHfData.UHfGetData.SetRfPower(power.byteValue());

            } else {

                isConnected = connectUHF();

                UHfData.UHfGetData.SetRfPower(power.byteValue());
            }

        });

        mViewModel.isScan.observe(this, aBoolean ->
        {
            if (mViewModel.mode.getValue() != 0)
                mBinding.txInfo.setText(aBoolean ? "开始扫描……" : "结束扫描");
            else
                mBinding.txInfo.setText("");

        });


        mBinding.rdgMode.setOnCheckedChangeListener((group, checkedId) ->
        {
            if (checkedId == R.id.rdb_1)
                mViewModel.mode.setValue(0);
            else
                mViewModel.mode.setValue(1);

        });


    }

    private void initComponent() {

        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RfidViewModel.class);

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

                Log.e("BroadcastReceiver>>>>>>", String.format(Locale.CHINA, "button------>1 : %d", KeyEvent.KEYCODE_F3));

                //                ToastUtils.info(context, String.format(Locale.CHINA, "button : %d", KeyEvent.KEYCODE_F3), Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT).show();

                if (keyDown) {

                    //todo

                    startPressTime = System.currentTimeMillis();


                    if (!isScan) {
                        isScan = true;

                        mViewModel.isScan.setValue(true);

                        scanTags();
                    } else {

                        isScan = false;

                        mViewModel.isScan.setValue(false);
                    }

                    Log.e("BroadcastReceiver>>>>>>", String.format(Locale.CHINA, "button------>2 : %d", keyCode));

                    //                ToastUtils.info(context, String.format(Locale.CHINA, "button : %d", keyCode), Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT).show();

                    scanTags();

                } else {

                    //                isScan = false;
                    //
                    //                mViewModel.isScan.setValue(false);

                    endPressTime = System.currentTimeMillis();

                    //todo
                    Log.e("BroadcastReceiver>>>>>>", String.format(Locale.CHINA, "button------>3 : %d", keyCode));

                    //                ToastUtils.info(context, String.format(Locale.CHINA, "button : %d", keyCode), Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT).show();

                }

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
    private void scanTags() {

        if (isConnected) {

            //得到为累计的数据集合
            final Disposable disposable = Observable.create((ObservableOnSubscribe<UHfData.InventoryTagMap>) emitter ->
            {

                for (; isScan; ) {
                    Log.e("Scan>>>>>>", "scan start> ---------------------------->>>>>>");

                    //清除累计的数据
                    UHfData.lsTagList.clear();
                    UHfData.dtIndexMap.clear();

                    //检查有效范围内是否有符合协议的电子标签存在
                    UHfData.Inventory_6c(0, 0);

                    final List<UHfData.InventoryTagMap> list = new ArrayList<>(UHfData.lsTagList);

                    if (list.size() == 0)
                        continue;

                    final ArrayMap<String, UHfData.InventoryTagMap> map = mViewModel.arrayMapTags.getValue();

                    if (map != null && map.size() > 0) {
                        one:
                        for (UHfData.InventoryTagMap bean : list) {
                            if (bean != null && !map.containsKey(bean.strEPC)) {
                                emitter.onNext(bean);

                                if (mViewModel.mode.getValue() == 0) {
                                    isScan = false;
                                    mViewModel.isScan.postValue(false);
                                    break one;
                                }

                            } else {

                                Objects.requireNonNull(getActivity()).runOnUiThread(() -> ToastUtils.info(Objects.requireNonNull(getContext()), "已扫描录入", Gravity.BOTTOM, Toast.LENGTH_SHORT).show());

                            }

                        }

                    } else {

                        for (UHfData.InventoryTagMap bean : list) {
                            emitter.onNext(bean);

                            if (mViewModel.mode.getValue() == 0) {
                                isScan = false;
                                mViewModel.isScan.postValue(false);
                                break;
                            }

                        }

                    }

                }
            }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(data ->
            {
                final List<UHfData.InventoryTagMap> list = new ArrayList<>();

                if (mViewModel.inventoryTagMap.getValue() != null)
                    list.addAll(mViewModel.inventoryTagMap.getValue());

                list.add(data);

                mViewModel.inventoryTagMap.postValue(list);

            }, throwable -> ToastUtils.info(Objects.requireNonNull(getContext()), "未知错误", Gravity.CENTER_VERTICAL, Toast.LENGTH_SHORT).show());

        } else {

            Snackbar.make(mBinding.recycler, "连接RFID读写器失败！", Snackbar.LENGTH_SHORT).show();

            isConnected = connectUHF();

            scanTags();
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
