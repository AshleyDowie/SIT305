package com.example.task51c;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsStoryRecyclerViewAdapter extends RecyclerView.Adapter<NewsStoryRecyclerViewAdapter.ViewHolder> {

    private List<NewsStory> _newsStories;
    private Context _context;
    private NewsStoryListener _newsStoryListener;

    public NewsStoryRecyclerViewAdapter(List<NewsStory> newsStories, Context context, NewsStoryListener newsStoryListener)
    {
        _newsStories = newsStories;
        _context = context;
        _newsStoryListener = newsStoryListener;
    }

    @NonNull
    @Override
    public NewsStoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(_context).inflate(R.layout.news_story_row, parent, false);
        return new ViewHolder(itemView, _newsStoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsStoryRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.cardHeadingTextView.setText(_newsStories.get(position).GetHeading());
        holder.cardBodyTextView.setText(_newsStories.get(position).GetBody());
        holder.cardImage.setImageResource(_newsStories.get(position).GetImage());
    }

    @Override
    public int getItemCount() {
        return _newsStories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cardBodyTextView;
        TextView cardHeadingTextView;
        ImageView cardImage;
        NewsStoryListener newsStoryListener;

        public ViewHolder(@NonNull View itemView, NewsStoryListener newsStoryListener) {
            super(itemView);
            cardBodyTextView = itemView.findViewById(R.id.cardBodyTextView);
            cardHeadingTextView = itemView.findViewById(R.id.cardHeadingTextView);
            cardImage = itemView.findViewById(R.id.cardImage);
            this.newsStoryListener = newsStoryListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            newsStoryListener.OnNewsStoryClick(getAdapterPosition());
        }
    }
    public interface NewsStoryListener {
        void OnNewsStoryClick(int position);
    }
}
