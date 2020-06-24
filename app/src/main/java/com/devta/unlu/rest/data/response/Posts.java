package com.devta.unlu.rest.data.response;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.devta.unlu.util.DevUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created on : Jun, 23, 2020 at 16:38
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class Posts implements Parcelable {

    private int page;
    private ArrayList<Post> posts;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }



    public Posts(int page, ArrayList<Post> posts) {
        this.page = page;
        this.posts = posts;
    }

    public static class Post implements Parcelable {

        private String id;
        private String thumbnail_image;
        private String event_name;
        private long event_date;
        private int views;
        private int likes;
        private int shares;
        private String formattedViews;
        private String formattedLikes;
        private String formattedShares;
        private String[] dateTimeArray;

        public String getDate() {
            if(dateTimeArray == null)
                dateTimeArray = DevUtil.getFormattedDatetime(event_date);
            return dateTimeArray[0];
        }

        public String getTime() {
            if(dateTimeArray == null)
                dateTimeArray = DevUtil.getFormattedDatetime(event_date);
            return dateTimeArray[1];
        }

        public String getFormattedViews() {
            if(formattedViews == null)
                formattedViews = getFormattedNumber(views);
            return formattedViews;
        }

        public String getFormattedLikes() {
            if(formattedLikes == null) {
                formattedLikes = getFormattedNumber(likes);
            }
            return formattedLikes;
        }

        public String getFormattedShares() {
            if(formattedShares == null) {
                formattedShares = getFormattedNumber(shares);
            }
            return formattedShares;
        }

        private String getFormattedNumber(double number) {
            if(number < 1000) {
                return String.valueOf((int) number);
            }else if(number == 1000) {
                return "1K";
            }else {
                return String.format(Locale.getDefault(),
                        "%.2f", number/1000).concat("K");
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getThumbnail_image() {
            return thumbnail_image;
        }

        public void setThumbnail_image(String thumbnail_image) {
            this.thumbnail_image = thumbnail_image;
        }

        public String getEvent_name() {
            return event_name;
        }

        public void setEvent_name(String event_name) {
            this.event_name = event_name;
        }

        public long getEvent_date() {
            return event_date;
        }

        public void setEvent_date(long event_date) {
            this.event_date = event_date;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getShares() {
            return shares;
        }

        public void setShares(int shares) {
            this.shares = shares;
        }

        public Post() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.id);
            dest.writeString(this.thumbnail_image);
            dest.writeString(this.event_name);
            dest.writeLong(this.event_date);
            dest.writeInt(this.views);
            dest.writeInt(this.likes);
            dest.writeInt(this.shares);
            dest.writeString(this.formattedViews);
            dest.writeString(this.formattedLikes);
            dest.writeString(this.formattedShares);
            dest.writeStringArray(this.dateTimeArray);
        }

        protected Post(Parcel in) {
            this.id = in.readString();
            this.thumbnail_image = in.readString();
            this.event_name = in.readString();
            this.event_date = in.readLong();
            this.views = in.readInt();
            this.likes = in.readInt();
            this.shares = in.readInt();
            this.formattedViews = in.readString();
            this.formattedLikes = in.readString();
            this.formattedShares = in.readString();
            this.dateTimeArray = in.createStringArray();
        }

        public static final Creator<Post> CREATOR = new Creator<Post>() {
            @Override
            public Post createFromParcel(Parcel source) {
                return new Post(source);
            }

            @Override
            public Post[] newArray(int size) {
                return new Post[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeTypedList(this.posts);
    }

    public Posts() {
    }

    protected Posts(Parcel in) {
        this.page = in.readInt();
        this.posts = in.createTypedArrayList(Post.CREATOR);
    }

    public static final Parcelable.Creator<Posts> CREATOR = new Parcelable.Creator<Posts>() {
        @Override
        public Posts createFromParcel(Parcel source) {
            return new Posts(source);
        }

        @Override
        public Posts[] newArray(int size) {
            return new Posts[size];
        }
    };
}
