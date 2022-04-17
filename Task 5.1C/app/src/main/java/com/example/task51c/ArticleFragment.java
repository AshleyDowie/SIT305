package com.example.task51c;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ArticleFragment extends Fragment implements RelatedStoryRecyclerViewAdapter.RelatedStoryListener {

    public List<NewsStory> relatedStoryList;
    public int SelectedStoryId;
    private NewsStory SelectedStory;
    RecyclerView relatedRecyclerView;
    RelatedStoryRecyclerViewAdapter relatedStoryRecyclerViewAdapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article, container, false);
        view = v;
        DisplaySelectedStory(SelectedStoryId);
        DisplayRelatedStories(relatedStoryList);
        return v;
    }

    public void DisplayRelatedStories(List<NewsStory> newsStories)
    {
        relatedRecyclerView = view.findViewById(R.id.RelatedRecyclerView);
        relatedStoryRecyclerViewAdapter = new RelatedStoryRecyclerViewAdapter(relatedStoryList, this.getActivity(), this);
        relatedRecyclerView.setAdapter(relatedStoryRecyclerViewAdapter);
        relatedRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    public void DisplaySelectedStory(int index)
    {
        if(SelectedStory != null)
        {
            relatedStoryList.add(SelectedStory);
            relatedStoryRecyclerViewAdapter.notifyDataSetChanged();
        }

        for(int i = 0; i < relatedStoryList.size(); i++)
        {
            if(relatedStoryList.get(i).GetId() == index)
            {
                SelectedStory = relatedStoryList.get(i);
                relatedStoryList.remove(SelectedStory);
                break;
            }
        }

        ImageView imageview = view.findViewById(R.id.imageView);
        imageview.setImageResource(SelectedStory.GetImage());
        TextView headingTextView = view.findViewById(R.id.headingTextView);
        headingTextView.setText(SelectedStory.GetHeading());
        TextView bodyTextView = view.findViewById(R.id.bodyImageView);
        bodyTextView.setText(SelectedStory.GetBody());
    }

    @Override
    public void OnRelatedStoryClick(int position) {
        DisplaySelectedStory(relatedStoryList.get(position).GetId());
    }

}