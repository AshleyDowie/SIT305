package com.example.task51c;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class HubFragment extends Fragment implements TopStoryRecyclerViewAdapter.TopStoryListener, NewsStoryRecyclerViewAdapter.NewsStoryListener {

    public List<NewsStory> newsStoryList;
    public List<NewsStory> topStoryList;
    OnHubFragmentListener hubCallback;
    RecyclerView newsRecyclerView;
    RecyclerView topRecyclerView;
    TopStoryRecyclerViewAdapter topStoryRecyclerViewAdapter;
    NewsStoryRecyclerViewAdapter newsStoryRecyclerViewAdapter;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hub, container, false);
        view = v;
        DisplayTopStories();
        DisplayNewsStories();
        return v;
    }

    public void DisplayNewsStories()
    {
        newsRecyclerView = view.findViewById(R.id.NewsRecyclerView);
        newsStoryRecyclerViewAdapter = new NewsStoryRecyclerViewAdapter(newsStoryList, this.getActivity(), this);
        newsRecyclerView.setAdapter(newsStoryRecyclerViewAdapter);
        newsRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2, RecyclerView.HORIZONTAL, false));
    }

    public void DisplayTopStories()
    {
        topRecyclerView = view.findViewById(R.id.TopRecyclerView);
        topStoryRecyclerViewAdapter = new TopStoryRecyclerViewAdapter(topStoryList, this.getActivity(), this);
        topRecyclerView.setAdapter(topStoryRecyclerViewAdapter);
        topRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void OnTopStoryClick(int position) {
        hubCallback.openNewsStory(topStoryList.get(position).GetId());
    }

    @Override
    public void OnNewsStoryClick(int position) {
        hubCallback.openNewsStory(newsStoryList.get(position).GetId());
    }

    public interface OnHubFragmentListener {
        void openNewsStory(int newsStoryId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hubCallback = (OnHubFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hubCallback = null;
    }
}