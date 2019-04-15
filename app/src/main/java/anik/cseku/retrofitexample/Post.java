package anik.cseku.retrofitexample;

import com.google.gson.annotations.SerializedName;

public class Post {

    private int userId;

    private int Id;


    private String title;

    @SerializedName("body")
    private String bodyText;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return title;
    }

    public String getBodyText() {
        return bodyText;
    }
}
