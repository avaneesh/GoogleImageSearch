package com.avklabs.googleimagesearch.adapters;

import android.util.Log;
import android.widget.AbsListView;

/**
 * Created by avkadam on 2/26/15.
 */
public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    private int visibleThreshold = 5;
    private int previousTotalCount;
    private boolean loading = true;
    private int startingPageIndex = 0;
    private int currentPageIndex = 0;

    public EndlessScrollListener(){
        this.loading = true;
        previousTotalCount=0;
        currentPageIndex=0;
        startingPageIndex=0;
    }

    public EndlessScrollListener(int visibleThreshold){
        this.visibleThreshold = visibleThreshold;
        this.loading = true;
        previousTotalCount=0;
        currentPageIndex=0;
        startingPageIndex=0;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.e("scroll", "firstVisibleItem: "+firstVisibleItem+" visibleItemCount: "+visibleItemCount
        +" totalItemCount: "+totalItemCount+ " previousTotalCount: "+this.previousTotalCount+" loading: "+this.loading);

        // If the total item count is zero and the previous isn't, assume the
        //        list is invalidated and should be reset back to initial state
        if (totalItemCount < this.previousTotalCount) {
            this.currentPageIndex = this.startingPageIndex;
            this.previousTotalCount = 0; // totalItemCount
            if (totalItemCount == 0){
                this.loading = true;
                Log.e("scroll", "Loading now.....");
            }
            else {
                this.loading = true;
                Log.e("scroll ERR", "totalItemCount "+ totalItemCount +" is less than previousTotalCount: "+this.previousTotalCount);
            }
        }

        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if ( loading && (totalItemCount > this.previousTotalCount) ) {
            this.currentPageIndex++;
            this.loading = false;
            this.previousTotalCount = totalItemCount;
            Log.e("scroll", "Loading finished ... ");
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if ( !loading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + this.visibleThreshold) ) {
            onLoadMore(currentPageIndex + 1, totalItemCount);
            this.loading = true;
            Log.e("scroll", "Loading more ... ");
        }
    }

    public abstract void onLoadMore(int page, int totalItemCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Don't take any action on changed
    }
}
