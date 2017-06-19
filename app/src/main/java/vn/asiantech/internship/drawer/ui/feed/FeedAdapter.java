package vn.asiantech.internship.drawer.ui.feed;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.models.FeedItem;

import static vn.asiantech.internship.R.id.viewPager;

/**
 * Created by BACKDOOR on 07-Feb-17.
 */
class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<FeedItem> mFeedItems;
    private FeedPagerAdapter mPagerAdapter;
    private int mCurrentPage;

    FeedAdapter(List<FeedItem> feedItems) {
        mFeedItems = feedItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvName.setText(mFeedItems.get(position).getName());
        holder.mTvComment.setText(mFeedItems.get(position).getComment());
        mPagerAdapter = new FeedPagerAdapter(mFeedItems.get(position).getImages());
        holder.mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public int getItemCount() {
        return mFeedItems.size();
    }

    /**
     * create RecyclerViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvComment;
        private ViewPager mViewPager;
        private ImageButton mBtnLeftSlide;
        private ImageButton mBtnRightSlide;

        ViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvFeed);
            mTvComment = (TextView) itemView.findViewById(R.id.tvComment);
            mViewPager = (ViewPager) itemView.findViewById(viewPager);
            mBtnLeftSlide = (ImageButton) itemView.findViewById(R.id.btnLeftSlide);
            mBtnRightSlide = (ImageButton) itemView.findViewById(R.id.btnRightSlide);
            mViewPager.setPageMargin(5);
            mViewPager.setPageMarginDrawable(R.color.colorBlack);
            mCurrentPage = mViewPager.getCurrentItem();
            mBtnLeftSlide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCurrentPage > 0) {
                        mCurrentPage--;
                        mBtnRightSlide.setVisibility(View.VISIBLE);
                    } else {
                        mBtnLeftSlide.setVisibility(View.GONE);
                    }
                    mViewPager.setCurrentItem(mCurrentPage);
                }
            });
            mBtnRightSlide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCurrentPage < mPagerAdapter.getCount()) {
                        mCurrentPage++;
                        mBtnLeftSlide.setVisibility(View.VISIBLE);
                    } else {
                        mBtnRightSlide.setVisibility(View.GONE);
                    }
                    mViewPager.setCurrentItem(mCurrentPage);
                }
            });
        }
    }
}
