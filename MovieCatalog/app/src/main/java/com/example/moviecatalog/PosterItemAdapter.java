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

    private final LayoutInflater mInflater;
    private MovieList data;
    private OnClickItemListener listener;
    private LinkedList<String> movieIds;

    public PosterItemAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PosterItemAdapter.PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.poster_item, parent, false);

        return new PosterViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterItemAdapter.PosterViewHolder holder, int position) {
        String BASE_URL = "https://image.tmdb.org/t/p/w342";
        String mCurrent = BASE_URL + data.getMovies().get(position).getPosterPath();
        Picasso.get().load(mCurrent).into(holder.posterItemView);
        holder.titleItemView.setText(data.getMovies().get(position).getTitle());
        holder.posterItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (listener != null) {
                    listener.onClickItem(data.getMovies().get(pos), pos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (data == null || data.getMovies() == null) {
            return 0;
        }
        return data.getMovies().size();
    }

    public MovieList getData() {
        return this.data;
    }

    public void setData(MovieList data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addMovieID(String id) {
        if (movieIds.contains(id)) {
            movieIds.remove(id);
        } else {
            this.movieIds.add(id);
        }
    }

    public LinkedList<String> getMovieIds() {
        return this.movieIds;
    }

    public void setOnClickListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    interface OnClickItemListener {
        void onClickItem(Movie movie, int position);
    }

    class PosterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView posterItemView;
        public final TextView titleItemView;
        final PosterItemAdapter mAdapter;

        public PosterViewHolder(@NonNull View itemView, PosterItemAdapter adapter) {
            super(itemView);
            posterItemView = itemView.findViewById(R.id.imageItem);
            titleItemView = itemView.findViewById(R.id.titleView);
            mAdapter = adapter;
        }
    }
}
