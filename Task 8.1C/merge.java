package com.example.task81c.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.task81c.model.User;
import com.example.task81c.model.Video;
import com.example.task81c.util.Util;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.USER_TABLE_NAME + "(" + Util.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.USER_NAME + " TEXT , " + Util.USER_FULL_NAME + " TEXT , " + Util.USER_PASSWORD + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        String CREATE_VIDEO_TABLE = "CREATE TABLE " + Util.VIDEO_TABLE_NAME + "(" + Util.VIDEO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.VIDEO_URL + " TEXT , " + Util.USER_ID + " INTEGER, FOREIGN KEY (" + Util.USER_ID + ") REFERENCES " + Util.USER_TABLE_NAME
                + "(" + Util.VIDEO_ID + ") )";

        sqLiteDatabase.execSQL(CREATE_VIDEO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = "DROP TABLE IF EXISTS";

        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.VIDEO_TABLE_NAME});
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.USER_TABLE_NAME});

        onCreate(sqLiteDatabase);
    }

    public long InsertUser (User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USER_FULL_NAME, user.getUserFullName());
        contentValues.put(Util.USER_NAME, user.getUserName());
        contentValues.put(Util.USER_PASSWORD, user.getUserPassword());

        long newRowId = db.insert(Util.USER_TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public long InsertVideo (Video video)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USER_ID, video.getUserId());
        contentValues.put(Util.VIDEO_URL, video.getVideoURL());

        long newRowId = db.insert(Util.VIDEO_TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public boolean UserExists(String userName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.USER_TABLE_NAME, new String[]{Util.USER_ID}, Util.USER_NAME + "=?",
                new String[]{userName}, null, null, null);

        int numberOfRows = cursor.getCount();

        db.close();

        if(numberOfRows > 0)
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    public boolean VideoExists(String url, int userId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.VIDEO_TABLE_NAME, new String[]{Util.VIDEO_ID}, Util.VIDEO_URL + "=? AND "
                        + Util.USER_ID + "=?",
                new String[]{url, String.valueOf(userId)}, null, null, null);

        int numberOfRows = cursor.getCount();

        db.close();

        if(numberOfRows > 0)
        {
            return true;
        }

        else
        {
            return false;
        }
    }


    public Integer GetUser(String userName, String userPassword)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.USER_TABLE_NAME, new String[]{Util.USER_ID}, Util.USER_NAME + "=? and " + Util.USER_PASSWORD + "=?",
                new String[]{userName, userPassword}, null, null, null);

        Integer userId = null;
        if(cursor.moveToFirst())
        {
            userId = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return userId;
    }

    public ArrayList<Video> GetVideos(int userId){

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + Util.VIDEO_ID + ", " + Util.USER_ID + ", " + Util.VIDEO_URL + " FROM " + Util.VIDEO_TABLE_NAME + " WHERE " + Util.USER_ID + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        ArrayList<Video> videos = new ArrayList<Video>();
        while(cursor.moveToNext()){
            Video video = new Video(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
            videos.add(video);
        }
        cursor.close();
        db.close();
        return videos;
    }
}

package com.example.task81c.model;

public class User {
    private int userId;
    private String userFullName;
    private String userName;
    private String userPassword;

    public User(String userFullName, String userName, String userPassword) {
        this.userFullName = userFullName;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User(int userId, String userFullName, String userName, String userPassword) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserFullName(String userFullName) {
        this.userName = userFullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}

package com.example.task81c.model;

public class Video {
    private int videoId;
    private int userId;
    private String videoURL;

    public Video(int userId, String videoURL) {
        this.userId = userId;
        this.videoURL = videoURL;
    }

    public Video(int videoId, int userId, String videoURL) {
        this.videoId = videoId;
        this.userId = userId;
        this.videoURL = videoURL;
    }

    public int getUserId() {
        return userId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

}

package com.example.task81c.util;

public class Util {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "database";

    public static final String USER_TABLE_NAME = "users";
    public static final String USER_ID = "userid";
    public static final String USER_FULL_NAME = "userfullname";

    public static final String USER_NAME = "username";
    public static final String USER_PASSWORD = "userpassword";

    public static final String VIDEO_TABLE_NAME = "videos";
    public static final String VIDEO_ID = "videoid";
    public static final String VIDEO_URL = "videourl";

    public static final String YOUTUBE_API_KEY = "AIzaSyCmF7A9DnDuFi-y0O6StVuraNxliVfUtJY";
}


package com.example.task81c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task81c.data.DatabaseHelper;
import com.example.task81c.model.User;
import com.example.task81c.model.Video;

public class HomePageActivity extends AppCompatActivity {

    int userId;
    DatabaseHelper dbHelper;
    EditText homeURLEditText;
    Button homePlayButton;
    Button homeAddButton;
    Button homeListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        dbHelper = new DatabaseHelper(this);
        homeURLEditText = findViewById(R.id.homeURLEditText);
        homePlayButton = findViewById(R.id.homePlayButton);
        homeAddButton = findViewById(R.id.homeAddButton);
        homeListButton = findViewById(R.id.homeListButton);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);

        String url = intent.getStringExtra("url");

        if(url != null)
        {
            homeURLEditText.setText(url);
        }

        homePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = homeURLEditText.getText().toString();

                if(url == null || url.trim().length() == 0)
                {
                    Toast.makeText(HomePageActivity.this, "Please enter a url for a youtube video " +
                                    "or select one from your playlist",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                OpenPlayActivity(url);
            }
        });

        homeAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = homeURLEditText.getText().toString();

                if(url == null || url.trim().length() == 0)
                {
                    Toast.makeText(HomePageActivity.this, "Please enter a url for a youtube video.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                AttemptCreateVideo(url);
            }
        });

        homeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPlayListActivity();
            }
        });
    }

    private void AttemptCreateVideo(String url)
    {
        if(dbHelper.VideoExists(url, userId))
        {
            Toast.makeText(HomePageActivity.this, "The video url " + url + " is already in your playlist.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        CreateVideo(url);
    }

    private void CreateVideo(String url)
    {
        Video video = new Video(userId, url);
        dbHelper.InsertVideo(video);
        Toast.makeText(HomePageActivity.this, "You have added " + url + " to your playlist.",
                Toast.LENGTH_SHORT).show();
    }

    public void OpenPlayListActivity()
    {
        Intent playlistIntent = new Intent(HomePageActivity.this, PlayListActivity.class);
        playlistIntent.putExtra("userId", userId);
        startActivity(playlistIntent);
    }

    public void OpenPlayActivity(String url)
    {
        Intent playIntent = new Intent(HomePageActivity.this, PlayActivity.class);
        playIntent.putExtra("url", url);
        startActivity(playIntent);
    }
}

package com.example.task81c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.task81c.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText userNameEditText;
    EditText passwordEditText;
    Button loginButton;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(userName == null || userName.trim().length() == 0)
                {
                    Toast.makeText(MainActivity.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password == null || password.trim().length() == 0)
                {
                    Toast.makeText(MainActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AttemptLogin(userName, password);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSignUpActivity();
            }
        });
    }

    public void AttemptLogin(String userName, String password)
    {
        Integer userId = dbHelper.GetUser(userName, password);
        if(userId == null)
        {
            Toast.makeText(MainActivity.this, "Username or password is invalid.", Toast.LENGTH_SHORT).show();
            return;
        }
        OpenHomeActivity(userId);
    }

    public void OpenHomeActivity(int userId)
    {
        Intent loginIntent = new Intent(MainActivity.this, HomePageActivity.class);
        loginIntent.putExtra("userId", userId);
        startActivity(loginIntent);
        finish();
    }

    public void OpenSignUpActivity()
    {
        Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
    }
}

package com.example.task81c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.example.task81c.util.Util;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayActivity extends YouTubeBaseActivity {

    String url;
    YouTubePlayerView youTubePlayer;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        youTubePlayer = findViewById(R.id.youtubePlayer);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                Toast.makeText(PlayActivity.this, "Playing video", Toast.LENGTH_SHORT).show();
                youTubePlayer.loadVideo(url);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(PlayActivity.this, "Failed to play video", Toast.LENGTH_SHORT).show();

            }
        };
        youTubePlayer.initialize(Util.YOUTUBE_API_KEY, onInitializedListener);
    }
}

package com.example.task81c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.task81c.data.DatabaseHelper;
import com.example.task81c.model.Video;

import java.util.ArrayList;

public class PlayListActivity extends AppCompatActivity {

    int userId;
    DatabaseHelper dbHelper;
    ListView videoListView;
    ArrayList videoArrayList;
    VideoAdapter videoArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);

        videoListView = findViewById(R.id.videoListView);

        videoArrayList = dbHelper.GetVideos(userId);
        videoArrayAdapter = new VideoAdapter(this, videoArrayList);
        videoListView.setAdapter(videoArrayAdapter);

        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Video video = videoArrayAdapter.getItem(i);

                Intent homePageIntent = new Intent(PlayListActivity.this, HomePageActivity.class);
                homePageIntent.putExtra("userId", userId);
                homePageIntent.putExtra("url", video.getVideoURL());
                startActivity(homePageIntent);
                finish();
            }
        });
    }
}

package com.example.task81c;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task81c.data.DatabaseHelper;
import com.example.task81c.model.User;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText fullNameEditText;
    EditText userNameEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button createAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DatabaseHelper(this);
        fullNameEditText = findViewById(R.id.signUpFullNameEditText);
        userNameEditText = findViewById(R.id.signUpUserNameEditText);
        passwordEditText = findViewById(R.id.signUpPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.signUpConfirmPasswordEditText);
        createAccountButton = findViewById(R.id.signUpCreateAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullName = fullNameEditText.getText().toString();
                String userName = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if(fullName == null || userName.trim().length() == 0)
                {
                    Toast.makeText(SignUpActivity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userName == null || userName.trim().length() == 0)
                {
                    Toast.makeText(SignUpActivity.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password == null || password.trim().length() == 0)
                {
                    Toast.makeText(SignUpActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(confirmPassword == null || password.trim().length() == 0)
                {
                    Toast.makeText(SignUpActivity.this, "Please confirm your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(SignUpActivity.this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                AttemptCreateUser(fullName, userName, password);
            }
        });
    }

    private void AttemptCreateUser(String fullName, String userName, String password)
    {
        if(dbHelper.UserExists(userName))
        {
            Toast.makeText(SignUpActivity.this, "The username " + userName + " is already taken.", Toast.LENGTH_SHORT).show();
            return;
        }
        CreateUser(fullName, userName, password);
    }

    private void CreateUser(String fullName, String userName, String password)
    {
        User user = new User(fullName, userName, password);
        dbHelper.InsertUser(user);
        finish();
    }
}

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