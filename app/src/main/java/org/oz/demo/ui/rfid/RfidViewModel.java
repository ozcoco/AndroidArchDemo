package org.oz.demo.ui.rfid;

import android.app.Application;
import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uhf.scanlable.UHfData;

import org.oz.demo.R;
import org.oz.demo.base.view.DecorativeAdapter;
import org.oz.demo.databinding.ItemRfidBinding;

import java.util.ArrayList;
import java.util.List;

public class RfidViewModel extends AndroidViewModel
{

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


    public RfidViewModel(@NonNull Application application)
    {
        super(application);

        mode.setValue(0);

        isScan.setValue(false);
    }


    @BindingAdapter({"adapter"})
    public static void recycleAdapter(RecyclerView recycler, @NonNull LiveData<List<UHfData.InventoryTagMap>> data)
    {

        final class RecyclerHolder extends RecyclerView.ViewHolder
        {

            final ItemRfidBinding binding;

            public RecyclerHolder(@NonNull View itemView)
            {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
            }
        }

        final DecorativeAdapter<RecyclerHolder, UHfData.InventoryTagMap> adapter = new DecorativeAdapter<>(recycler.getContext(), new DecorativeAdapter.IAdapterDecorator<RecyclerHolder, UHfData.InventoryTagMap>()
        {

            @Override
            public RecyclerHolder onCreateViewHolder(@NonNull Context context, @NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType)
            {


                return new RecyclerHolder(inflater.inflate(R.layout.item_rfid, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull Context context, @NonNull RecyclerHolder holder, @NonNull UHfData.InventoryTagMap data, int position)
            {

                holder.binding.setBean(data);

            }
        });

        adapter.setData(data.getValue());

        recycler.setLayoutManager(new LinearLayoutManager(recycler.getContext()));

        recycler.setAdapter(adapter);

        data.observeForever(adapter::setData);

    }
}