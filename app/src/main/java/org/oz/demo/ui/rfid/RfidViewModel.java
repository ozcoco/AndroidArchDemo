package org.oz.demo.ui.rfid;

import android.app.Application;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.uhf.scanlable.UHfData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RfidViewModel extends AndroidViewModel {

    public final MutableLiveData<Integer> itemClickPosition = new MutableLiveData<>();

    public final MutableLiveData<Integer> power = new MutableLiveData<>();

    public final MutableLiveData<Integer> mode = new MutableLiveData<>();

    public final MutableLiveData<Boolean> isScan = new MutableLiveData<>();

    public final ObservableInt itemPosition = new ObservableInt();

    public final MutableLiveData<List<UHfData.InventoryTagMap>> inventoryTagMap = new MutableLiveData<>();

    public final LiveData<ArrayMap<String, UHfData.InventoryTagMap>> arrayMapTags = Transformations.map(inventoryTagMap, input ->
    {

        final ArrayMap<String, UHfData.InventoryTagMap> map = new ArrayMap<>();

        for (UHfData.InventoryTagMap bean : input)
            map.put(bean.strEPC, bean);

        return map;
    });

    public final LiveData<List<UHfData.InventoryTagMap>> itemData = Transformations.map(arrayMapTags, input -> new ArrayList<>(input.values()));

    public final LiveData<String> info = Transformations.map(inventoryTagMap, Object::toString);

    public final LiveData<String> epcData = Transformations.map(itemClickPosition, position -> Objects.requireNonNull(itemData.getValue()).get(position).strEPC);

    public RfidViewModel(@NonNull Application application) {
        super(application);

        power.setValue(10);

        mode.setValue(0);

        isScan.setValue(false);
    }

}