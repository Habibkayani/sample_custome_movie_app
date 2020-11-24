package com.example.primevideoclone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.primevideoclone.R;
import com.example.primevideoclone.model.AllCategory;
import com.example.primevideoclone.model.CategoryItem;

import java.util.List;

public class MainRecylerAdapter extends RecyclerView.Adapter<MainRecylerAdapter.MainViewHolder> {
    Context context;
    List<AllCategory> allCategoryList;

    public MainRecylerAdapter(Context context, List<AllCategory> allCategoryList) {
        this.context = context;
        this.allCategoryList = allCategoryList;
    }

    @NonNull
    @Override
    public MainRecylerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recyler_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecylerAdapter.MainViewHolder holder, int position) {
        holder.CategoryName.setText(allCategoryList.get(position).getCatagoryTitle());
        setItemRecyler(holder.itemRecyler, allCategoryList.get(position).getCategoryItemList());
    }

    @Override
    public int getItemCount() {
        return allCategoryList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView CategoryName;
        RecyclerView itemRecyler;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryName = itemView.findViewById(R.id.categoryname);
            itemRecyler = itemView.findViewById(R.id.item_recyler);
        }
    }

    private void setItemRecyler(RecyclerView recyclerView, List<CategoryItem> categoryItemList) {
        ItemRecylerViewAdapter itemRecylerViewAdapter = new ItemRecylerViewAdapter(context, categoryItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(itemRecylerViewAdapter);

    }
}
