package org.oz.demo.ui.paging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.oz.demo.R;
import org.oz.demo.databinding.ItemSimpleBinding;
import org.oz.demo.databinding.PagingFragmentBinding;

import java.util.Objects;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;


public class PagingFragment extends Fragment {

    private PagingViewModel mViewModel;

    private PagingFragmentBinding mBinding;

    public static PagingFragment newInstance() {
        return new PagingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.paging_fragment, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PagingViewModel.class);
        // TODO: Use the ViewModel

        initRecycle();

    }


    private void initRecycle() {

        final class RecycleViewHolder extends RecyclerView.ViewHolder {

            ItemSimpleBinding itemBinding;

            RecycleViewHolder(@NonNull View itemView) {
                super(itemView);

                itemBinding = DataBindingUtil.bind(itemView);

            }

        }

        mBinding.recycler.setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));


        mBinding.recycler.setAdapter(new RecyclerView.Adapter<RecycleViewHolder>() {

            @NonNull
            @Override
            public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RecycleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {

                holder.itemBinding.setUser(mViewModel.users.getValue().get(position));

            }

            @Override
            public int getItemCount() {
                return mViewModel.users.getValue() == null ? 0 : mViewModel.users.getValue().size();
            }
        });

        mViewModel.users.observe(this, users -> Objects.requireNonNull(mBinding.recycler.getAdapter()).notifyDataSetChanged());

    }

}
