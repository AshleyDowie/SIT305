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

public class RelatedStoryRecyclerViewAdapter extends RecyclerView.Adapter<RelatedStoryRecyclerViewAdapter.ViewHolder> {

    private List<NewsStory> _newsStories;
    private Context _context;
    private RelatedStoryListener _relatedStoryListener;

    public RelatedStoryRecyclerViewAdapter(List<NewsStory> newsStories, Context context, RelatedStoryListener relatedStoryListener)
    {
        _newsStories = newsStories;
        _context = context;
        _relatedStoryListener = relatedStoryListener;
    }

    @NonNull
    @Override
    public RelatedStoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(_context).inflate(R.layout.related_story_row, parent, false);
        return new ViewHolder(itemView, _relatedStoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedStoryRecyclerViewAdapter.ViewHolder holder, int position) {

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
        RelatedStoryListener relatedStoryListener;

        public ViewHolder(@NonNull View itemView, RelatedStoryListener relatedStoryListener) {
            super(itemView);
            cardBodyTextView = itemView.findViewById(R.id.cardBodyTextView);
            cardHeadingTextView = itemView.findViewById(R.id.cardHeadingTextView);
            cardImage = itemView.findViewById(R.id.cardImage);
            this.relatedStoryListener = relatedStoryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            relatedStoryListener.OnRelatedStoryClick(getAdapterPosition());
        }
    }
    public interface RelatedStoryListener {
        void OnRelatedStoryClick(int position);
    }
}
