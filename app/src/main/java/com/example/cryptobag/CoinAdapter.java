package com.example.cryptobag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.ViewGroup;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptobag.com.example.cryptobag.Entities.Coin;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {
    private MainActivity mParentActivity;
    private List<Coin> mCoins;
    //private RecyclerViewClickListener mListener;
    private boolean mTwoPane;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Coin coin = (Coin) v.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(DetailFragment.ARG_ITEM_ID, coin.getId());
                DetailFragment fragment = new DetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment).commit();
            } else {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailFragment.ARG_ITEM_ID, coin.getId());
                context.startActivity(intent);
            }
        }

    };


    public CoinAdapter(MainActivity parent, List<Coin> coins, boolean twoPane) {
        mParentActivity = parent;
        mCoins = coins;
        mTwoPane = twoPane;
    }

    //public interface RecyclerViewClickListener {
    //void onClick(View view, int position);
    //}

    public static class CoinViewHolder extends RecyclerView.ViewHolder {
        public TextView name, value, change;
        //private RecyclerViewClickListener mListener;

        public CoinViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            value = v.findViewById(R.id.tvValue);
            change = v.findViewById(R.id.tvChange);
        }
    }
        //@Override
        //public void onClick(View view){
        //mListener.onClick(view, getAdapterPosition());
        //}
        //}

        @Override
        public CoinAdapter.CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_list_row, parent, false);
            return new CoinViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CoinViewHolder holder, int position) {
            Coin coin = mCoins.get(position);
            holder.name.setText(coin.getName());
            holder.value.setText(NumberFormat.getCurrencyInstance().format(Double.valueOf(coin.getPriceUsd())));
            holder.change.setText(String.valueOf(coin.getPercentChange24h()) + " %");
            holder.itemView.setTag(coin);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mCoins.size();
        }

        // to notify the adapter of changes in data set
        public void setCoins(List<Coin> coins){
        mCoins.clear();
        mCoins.addAll(coins);
        notifyDataSetChanged();
        }
    }

