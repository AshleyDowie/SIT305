package com.example.task81c;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.task81c.model.Video;

import java.util.ArrayList;

public class VideoAdapter extends BaseAdapter {

    Context context;
    ArrayList<Video> data;
    private static LayoutInflater inflater = null;

    public VideoAdapter(Context context, ArrayList<Video> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Video getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getVideoId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
        {
            view = inflater.inflate(R.layout.video_row, null);
        }
        TextView text = view.findViewById(R.id.video_url);
        Video video = data.get(position);
        text.setText(video.getVideoURL());
        return view;
    }
}