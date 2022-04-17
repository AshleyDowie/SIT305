package com.example.task51c;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HubFragment.OnHubFragmentListener{

    HubFragment hubFragment;
    ArticleFragment articleFragment;
    FragmentManager fragmentManager;


    String[] headerList = {
            "Hockey Grand Final",
            "Munchies Draft",
            "Stocks Soar",
            "Extinction Event",
            "War Declared"};
    String[] bodyList = {
            "Hockey grand final this Thursday. Maggots vs Ants. The host of the event has urged families to gamble heavily.",
            "A new player has been drafted for the Munchies. The player is 9 foot tall and comes from Fakeland.",
            "Stocks are set to soar after price of everything rises. Wages are anticipated to be excluded from the general price increase.",
            "The dodo is now extinct due to feral cat populations. It is believed that their numbers have been in steady decline for some time now.",
            "Fakestan has declared war on Fakeland after the king of Fakeland made an insulting meme of the prime minister of Fakestan."};
    int[] imageList = {R.drawable.sport, R.drawable.sport, R.drawable.earth, R.drawable.earth, R.drawable.earth};
    String[] CategoryList = {"Sport", "Sport", "News", "News", "News"};
    boolean[] IsTopStoryList = {false, true, false, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        hubFragment = new HubFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment, hubFragment)
                .commit();

        hubFragment.newsStoryList = new ArrayList<>();
        hubFragment.topStoryList = new ArrayList<>();

        for(int i = 0; i < headerList.length; i++)
        {
            NewsStory newsStory = new NewsStory(i,headerList[i], bodyList[i], imageList[i], CategoryList[i], IsTopStoryList[i]);
            hubFragment.newsStoryList.add(newsStory);
            if(newsStory.GetIsTopStory())
            {
                hubFragment.topStoryList.add(newsStory);
            }
        }
    }

    @Override
    public void openNewsStory(int newsStoryId) {
        articleFragment = new ArticleFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment, articleFragment)
                .addToBackStack(null)
                .commit();

        articleFragment.relatedStoryList = new ArrayList<>();

        for(int i = 0; i < headerList.length; i++)
        {
            NewsStory newsStory = new NewsStory(i,headerList[i], bodyList[i], imageList[i], CategoryList[i], IsTopStoryList[i]);
            if(newsStory.GetCategory().equals(CategoryList[newsStoryId]))
            {
                articleFragment.relatedStoryList.add(newsStory);
            }
        }
        articleFragment.SelectedStoryId = newsStoryId;
    }
}