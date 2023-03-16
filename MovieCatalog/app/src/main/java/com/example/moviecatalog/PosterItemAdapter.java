package com.example.moviecatalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class PosterItemAdapter extends RecyclerView.Adapter<PosterItemAdapter.PosterViewHolder> {

    private final LinkedList<String> mPosterList;
    private LayoutInflater mInflater;

    public PosterItemAdapter(Context context,LinkedList<String> posterList) {
        mInflater = LayoutInflater.from(context);
        this.mPosterList = posterList;
    }

    @NonNull
    @Override
    public PosterItemAdapter.PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.poster_item, parent, false);

        return new PosterViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterItemAdapter.PosterViewHolder holder, int position) {
        String mCurrent = mPosterList.get(position);
        Picasso.get().load(mCurrent).into(holder.posterItemView);

    }

    @Override
    public int getItemCount() {
        return mPosterList.size();
    }

    class PosterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView posterItemView;
        final PosterItemAdapter mAdapter;

        public PosterViewHolder(@NonNull View itemView, PosterItemAdapter adapter) {
            super(itemView);
            posterItemView = itemView.findViewById(R.id.imageItem);
            mAdapter = adapter;
        }
    }
}
