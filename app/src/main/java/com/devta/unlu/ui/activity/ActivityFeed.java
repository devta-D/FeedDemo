package com.devta.unlu.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.devta.unlu.R;
import com.devta.unlu.application.UnluApplication;
import com.devta.unlu.async.PostReaderAsync;
import com.devta.unlu.async.PostWriterAsync;
import com.devta.unlu.databinding.ActivityFeedBinding;
import com.devta.unlu.mvp.FeedMVP;
import com.devta.unlu.nointernetdialog.NoInternetDialog;
import com.devta.unlu.rest.data.response.Posts;
import com.devta.unlu.ui.adapter.PostsAdapter;
import com.devta.unlu.ui.dialog.DialogSortingOptions;
import com.devta.unlu.util.DevUtil;
import com.malinskiy.superrecyclerview.OnMoreListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

public class ActivityFeed extends AppCompatActivity implements FeedMVP.View,
        OnMoreListener, SwipeRefreshLayout.OnRefreshListener,
        NoInternetDialog.Callbacks, View.OnClickListener,
        DialogSortingOptions.Callbacks, PostReaderAsync.PostReaderListener {

    @Inject
    FeedMVP.Presenter feedPresenter;

    private int currentPage = 1;
    private PostsAdapter postsAdapter;
    private int selectedSortOptionPosition = -1;

    private ActivityFeedBinding feedBinding;

    private final static int TOTAL_NO_OF_PAGES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_feed);
        UnluApplication.getInstance().getComponent().inject(this);
        feedPresenter.setView(this);
        if(feedBinding.btnSort != null)
            feedBinding.btnSort.setOnClickListener(this);
        if(feedBinding.fabBtnSort != null)
            feedBinding.fabBtnSort.setOnClickListener(this);
        sortButtonsEnable(false);
        feedBinding.listPosts.setRefreshListener(this);
        if(savedInstanceState != null) {
            loadFromInstanceState(savedInstanceState);
        }else {
            new PostReaderAsync(this, this).execute();
        }
    }

    @Override
    public void onPostExecutionOfReadingDB(Posts posts) {
        if(posts == null || posts.getPage() == 0
                || posts.getPosts() == null || posts.getPosts().isEmpty()) {
            currentPage = 1;
            loadPosts(false);
        }else {
            currentPage = posts.getPage();
            onPostsLoaded(posts);
        }
    }

    private void sortButtonsEnable(boolean enable) {
        if(feedBinding.btnSort != null)
            feedBinding.btnSort.setEnabled(enable);
        if(feedBinding.fabBtnSort != null)
            feedBinding.fabBtnSort.setEnabled(enable);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_sort || view.getId() == R.id.fab_btn_sort) {
            DialogSortingOptions.newInstance(
                    this, selectedSortOptionPosition)
                    .show(getSupportFragmentManager(), "sorting_option_dialog");
        }
    }

    @Override
    public void onPostsLoaded(Posts feed) {
        feedBinding.listPosts.setRefreshing(false);
        if(postsAdapter == null) {
            postsAdapter = new PostsAdapter(feed.getPosts());
            if(selectedSortOptionPosition < 0
                    && currentPage < TOTAL_NO_OF_PAGES) {
                feedBinding.listPosts.setupMoreListener(this, 1);
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            feedBinding.listPosts.setLayoutManager(layoutManager);
            feedBinding.listPosts.setAdapter(postsAdapter);
            if(currentPage <= 1)
                new PostWriterAsync(this, PostWriterAsync.TYPE_REFRESH).execute(feed);
        }else {
            if(currentPage == 1) {
                postsAdapter.onRefresh(feed.getPosts());
                new PostWriterAsync(this, PostWriterAsync.TYPE_REFRESH).execute(feed);
            }else {
                postsAdapter.onMorePostsFound(feed.getPosts());
                new PostWriterAsync(this, PostWriterAsync.TYPE_INSERT).execute(feed);
            }
            if(currentPage == TOTAL_NO_OF_PAGES) {
                feedBinding.listPosts.removeMoreListener();
            }else {
                feedBinding.listPosts.setupMoreListener(this, 1);
            }
        }
        sortButtonsEnable(postsAdapter.getItemCount() != 0);
    }

    private void loadPosts(boolean incrementPage) {
        sortButtonsEnable(false);
        if(incrementPage) currentPage = currentPage + 1;
        if(!DevUtil.isInternetAvailable(getApplicationContext())) {
            onInternetNotFound();
            return;
        }
        feedPresenter.loadPosts(currentPage);
    }

    private void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        selectedSortOptionPosition = 0;
        loadPosts(false);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        loadPosts(true);
    }

    @Override
    public void onPostsError(String errorMessage) {
        sortButtonsEnable(postsAdapter != null);
        if(TextUtils.isEmpty(errorMessage))
            errorMessage = "Something went wrong!";
        makeToast(errorMessage);
    }

    public void onInternetNotFound() {
        sortButtonsEnable(postsAdapter != null);
        feedBinding.listPosts.setRefreshing(false);
        feedBinding.listPosts.hideProgress();
        feedBinding.listPosts.hideMoreProgress();
        if(currentPage == 1) {
            new NoInternetDialog.Builder(this)
                    .setCallbacks(this)
                    .setRetryAvailable(true)
                    .show();
        }else {
            makeToast(getString(R.string.txt_no_internet));
        }
    }

    @Override
    public void onInternetDialogButtonClick(int buttonType) {
        if(buttonType == NoInternetDialog.BUTTON_RETRY) {
            feedBinding.listPosts.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(postsAdapter != null) {
            outState.putInt("page", currentPage);
            outState.putInt("sort_position", selectedSortOptionPosition);
            outState.putParcelableArrayList("list", postsAdapter.getPosts());
        }
    }

    private void loadFromInstanceState(@NonNull Bundle savedInstanceState) {
        int page = savedInstanceState.getInt("page");
        if(page >= 1) {
            currentPage = page;
            selectedSortOptionPosition = savedInstanceState.getInt(
                    "sort_position", selectedSortOptionPosition);
            onPostsLoaded(new Posts(currentPage,
                    savedInstanceState.<Posts.Post>getParcelableArrayList("list")));
        }
    }


    @Override
    public void sortByNewest(int optionPosition) {
        selectedSortOptionPosition = optionPosition;
        feedBinding.listPosts.removeMoreListener();
        if(postsAdapter.sortByNewest())
            feedBinding.listPosts.getRecyclerView().scrollToPosition(0);
    }

    @Override
    public void sortByOldest(int optionPosition) {
        selectedSortOptionPosition = optionPosition;
        feedBinding.listPosts.removeMoreListener();
        if(postsAdapter.sortByOldest())
            feedBinding.listPosts.getRecyclerView().scrollToPosition(0);
    }

    @Override
    public void sortByViewsHighToLow(int optionPosition) {
        selectedSortOptionPosition = optionPosition;
        feedBinding.listPosts.removeMoreListener();
        if(postsAdapter.sortByViewsHighToLow())
            feedBinding.listPosts.getRecyclerView().scrollToPosition(0);
    }

    @Override
    public void sortByViewsLowToHigh(int optionPosition) {
        selectedSortOptionPosition = optionPosition;
        feedBinding.listPosts.removeMoreListener();
        if(postsAdapter.sortByViewsLowToHigh())
            feedBinding.listPosts.getRecyclerView().scrollToPosition(0);
    }

    @Override
    public void sortByLikesHighToLow(int optionPosition) {
        selectedSortOptionPosition = optionPosition;
        feedBinding.listPosts.removeMoreListener();
        if(postsAdapter.sortByLikesHighToLow())
            feedBinding.listPosts.getRecyclerView().scrollToPosition(0);
    }

    @Override
    public void sortByLikesLowToHigh(int optionPosition) {
        selectedSortOptionPosition = optionPosition;
        feedBinding.listPosts.removeMoreListener();
        if(postsAdapter.sortByLikesLowToHigh())
            feedBinding.listPosts.getRecyclerView().scrollToPosition(0);
    }

    @Override
    public void sortBySharesHighToLow(int optionPosition) {
        selectedSortOptionPosition = optionPosition;
        feedBinding.listPosts.removeMoreListener();
        if(postsAdapter.sortBySharesHighToLow())
            feedBinding.listPosts.getRecyclerView().scrollToPosition(0);
    }

    @Override
    public void sortBySharesLowToHigh(int optionPosition) {
        selectedSortOptionPosition = optionPosition;
        feedBinding.listPosts.removeMoreListener();
        if(postsAdapter.sortBySharesLowToHigh())
            feedBinding.listPosts.getRecyclerView().scrollToPosition(0);
    }

}
