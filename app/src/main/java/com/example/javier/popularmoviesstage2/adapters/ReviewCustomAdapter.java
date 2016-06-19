package com.example.javier.popularmoviesstage2.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.javier.popularmoviesstage2.R;
import com.example.javier.popularmoviesstage2.model.ReviewEntity;

import java.util.List;

/**
 * Created by javie on 11/06/2016.
 */

public class ReviewCustomAdapter extends RecyclerView.Adapter<ReviewCustomAdapter.ViewHolder> {


    private List<ReviewEntity> mListReviews;
    private Activity mActivity;

    public ReviewCustomAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public ReviewCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.username.setText(mListReviews.get(position).getAuthor());
        holder.content.setText(mListReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mListReviews == null ? 0 : mListReviews.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {


        private TextView username;

        private TextView content;


        public ViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.reviewer);
            content = (TextView) view.findViewById(R.id.contentReview);
        }

        public TextView getContent() {
            return content;
        }

        public void setContent(TextView content) {
            this.content = content;
        }

        public TextView getUsername() {
            return username;
        }

        public void setUsername(TextView username) {
            this.username = username;
        }
    }


    public List<ReviewEntity> getmListReviews() {
        return mListReviews;
    }

    public void setmListReviews(List<ReviewEntity> mListReviews) {
        this.mListReviews = mListReviews;
    }
}
