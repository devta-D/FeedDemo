package com.devta.unlu.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devta.unlu.R;
import com.devta.unlu.databinding.ItemPostBinding;
import com.devta.unlu.rest.data.response.Posts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created on : Jun, 23, 2020 at 17:58
 * Author     : Divyanshu Tayal
 * Name       : devta-D
 * GitHub     : https://github.com/devta-D/
 * LinkedIn   : https://www.linkedin.com/in/divyanshu-tayal-4a95b2aa/
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostVH> {

    private ArrayList<Posts.Post> posts;

    private final int SORT_TYPE_DATE = -101;
    private final int SORT_TYPE_VIEWS = -102;
    private final int SORT_TYPE_LIKES = -103;
    private final int SORT_TYPE_SHARES = -104;

    public PostsAdapter(ArrayList<Posts.Post> posts) {
        if(posts == null) posts = new ArrayList<>();
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding postBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_post, parent, false);
        return new PostVH(postBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostVH postViewHolder, int position) {
        Posts.Post post = posts.get(postViewHolder.getAdapterPosition());
        postViewHolder.binding.title.setText(post.getEvent_name());
        Glide.with(postViewHolder.binding.thumbnail.getContext())
                .load(post.getThumbnail_image())
                .placeholder(R.color.colorPlaceholder)
                .error(R.color.colorPlaceholder)
                .into(postViewHolder.binding.thumbnail);

        postViewHolder.binding.date.setText(post.getDate());
        postViewHolder.binding.time.setText(post.getTime());

        postViewHolder.binding.views.setText(post.getFormattedViews());
        postViewHolder.binding.likes.setText(post.getFormattedLikes());
        postViewHolder.binding.shares.setText(post.getFormattedShares());
    }

    public void onRefresh(ArrayList<Posts.Post> posts) {
        this.posts.clear();
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void onMorePostsFound(final ArrayList<Posts.Post> posts) {
        if(posts == null || posts.isEmpty()) return;
        this.posts.addAll(posts);
        notifyItemRangeInserted(getItemCount(), this.posts.size());
    }

    public ArrayList<Posts.Post> getPosts() {
        return posts;
    }

    public boolean sortByNewest() {
        return genericSort(SORT_TYPE_DATE, true);
    }

    public boolean sortByOldest() {
        return genericSort(SORT_TYPE_DATE, false);
    }

    public boolean sortByViewsHighToLow() {
        return genericSort(SORT_TYPE_VIEWS, false);
    }

    public boolean sortByViewsLowToHigh() {
        return genericSort(SORT_TYPE_VIEWS, true);
    }

    public boolean sortByLikesHighToLow() {
        return genericSort(SORT_TYPE_LIKES, false);
    }

    public boolean sortByLikesLowToHigh() {
        return genericSort(SORT_TYPE_LIKES, true);
    }

    public boolean sortBySharesHighToLow() {
        return genericSort(SORT_TYPE_SHARES, false);
    }

    public boolean sortBySharesLowToHigh() {
        return genericSort(SORT_TYPE_SHARES, true);
    }

    private boolean genericSort(final int sortType, final boolean asc) {
        Collections.sort(posts, new Comparator<Posts.Post>() {
            @Override
            public int compare(Posts.Post p0, Posts.Post p1) {
                switch (sortType) {
                    case SORT_TYPE_DATE:
                        if(asc) {
                            return Long.compare(p1.getEvent_date(), p0.getEvent_date());
                        }else {
                            return Long.compare(p0.getEvent_date(), p1.getEvent_date());
                        }
                    case SORT_TYPE_VIEWS:
                        if(asc) {
                            return Integer.compare(p0.getViews(), p1.getViews());
                        }else {
                            return Integer.compare(p1.getViews(), p0.getViews());
                        }
                    case SORT_TYPE_LIKES:
                        if(asc) {
                            return Integer.compare(p0.getLikes(), p1.getLikes());
                        }else {
                            return Integer.compare(p1.getLikes(), p0.getLikes());
                        }
                    case SORT_TYPE_SHARES:
                        if(asc) {
                            return Integer.compare(p0.getShares(), p1.getShares());
                        }else {
                            return Integer.compare(p1.getShares(), p0.getShares());
                        }
                    default:
                        return Long.compare(p1.getEvent_date(), p0.getEvent_date());
                }
            }
        });
        notifyDataSetChanged();
        return true;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostVH extends RecyclerView.ViewHolder {
        ItemPostBinding binding;
        PostVH(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
