package com.example.task51c;

public class NewsStory {

    private int _Id;
    private String _heading;
    private String _body;
    private int _image;
    private String _category;
    private boolean _isTopStory;

    public NewsStory(int Id, String heading, String body, int image, String category, boolean isTopStory)
    {
        _Id = Id;
        _heading = heading;
        _body = body;
        _image = image;
        _category = category;
        _isTopStory = isTopStory;
    }

    public int GetId()
    {
        return _Id;
    }

    public String GetHeading()
    {
        return _heading;
    }

    public String GetBody()
    {
        return _body;
    }

    public int GetImage()
    {
        return _image;
    }

    public String GetCategory()
    {
        return _category;
    }

    public boolean GetIsTopStory()
    {
        return _isTopStory;
    }

}
