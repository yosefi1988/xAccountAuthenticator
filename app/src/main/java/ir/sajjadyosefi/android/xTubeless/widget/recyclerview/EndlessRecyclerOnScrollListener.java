package ir.sajjadyosefi.android.xTubeless.widget.recyclerview;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    public int previousTotal = 0; // The total number of items in the dataset after the last load
    public boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public void reset(){
        firstVisibleItem = visibleItemCount = totalItemCount = previousTotal = 0; // The total number of items in the dataset after the last load
        loading = true; // True if we are still waiting for the last set of data to load.
        visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
        current_page = 1;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(mLinearLayoutManager != null) {
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading &&
                    (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached

                // Do something
                current_page++;

                onLoadMore(current_page);

                loading = true;
            }

            if (dy > 0) {
                // Scroll Down
                onScrollDown();
            } else if (dy < 0) {
                // Scroll Up
                onScrollUp();
            }

//                    Log.e("Sjd","  visibleItemCount :  " + visibleItemCount  );
//                    Log.e("Sjd","  totalItemCount :  " + totalItemCount  );
//                    Log.e("Sjd","  firstVisibleItem :  " + firstVisibleItem  );
//                    Log.e("Sjd","  previousTotal :  " + previousTotal  );
//                    Log.e("Sjd","  visibleThreshold :  " + visibleThreshold  );
//                    Log.e("Sjd","  loading :  " + loading  );
//                    Log.e("Sjd"," ___________________________________________ " );
        }
    }

    protected abstract void onScrollUp();

    protected abstract void onScrollDown();

    public abstract void onLoadMore(int current_page);

    public void resetCounter(){
        current_page = 1;
    };

}
