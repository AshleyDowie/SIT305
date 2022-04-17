package com.example.task51c;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopStoryRecyclerViewAdapter extends RecyclerView.Adapter<TopStoryRecyclerViewAdapter.ViewHolder> {

    private List<NewsStory> _newsStories;
    private Context _context;
    private TopStoryListener _topStoryListener;

    public TopStoryRecyclerViewAdapter(List<NewsStory> newsStories, Context context, TopStoryListener topStoryListener)
    {
        _newsStories = newsStories;
        _context = context;
        _topStoryListener = topStoryListener;
    }

    @NonNull
    @Override
    public TopStoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(_context).inflate(R.layout.top_story_row, parent, false);
        return new ViewHolder(itemView, _topStoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStoryRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.cardImage.setImageResource(_newsStories.get(position).GetImage());
    }

    @Override
    public int getItemCount() {
        return _newsStories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cardImage;
        TopStoryListener topStoryListener;

        public ViewHolder(@NonNull View itemView, TopStoryListener topStoryListener) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            this.topStoryListener = topStoryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            topStoryListener.OnTopStoryClick(getAdapterPosition());
        }
    }
    public interface TopStoryListener {
        void OnTopStoryClick(int position);
    }
}
