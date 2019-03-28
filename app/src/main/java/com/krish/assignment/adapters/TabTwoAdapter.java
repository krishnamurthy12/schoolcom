package com.krish.assignment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.krish.assignment.R;
import com.krish.assignment.api_responses.example_two.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TabTwoAdapter extends RecyclerView.Adapter<TabTwoAdapter.MyViewHolder> {

    private List<Article> mList = new ArrayList<>();
    private Context mContext;
    private final RecyclerView mRecyclerView;

    public TabTwoAdapter(Context mContext, List<Article> mList,RecyclerView mRecyclerView) {
        this.mList = mList;
        this.mContext = mContext;
        this.mRecyclerView=mRecyclerView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_single_item_layout, viewGroup, false);
        return new TabTwoAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //Set values to views here

        Picasso.get()
                .load(mList.get(i).getUrlToImage())
                .error(mContext.getResources().getDrawable(R.drawable.loading))
                .placeholder(mContext.getResources().getDrawable(R.drawable.loading))
                .into(myViewHolder.mImage);

        myViewHolder.mTitle.setText(mList.get(i).getTitle());
        myViewHolder.mDescription.setText(mList.get(i).getDescription());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImage;
        TextView mTitle, mDescription;
        LinearLayout mRootLayout, mDetailsLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //Initialize views here
            mRootLayout = itemView.findViewById(R.id.mainContactLayout);
            mDetailsLayout = itemView.findViewById(R.id.details_layout);

            mImage = itemView.findViewById(R.id.vI_lsil_image);
            mTitle = itemView.findViewById(R.id.vT_lsil_title);
            mDescription = itemView.findViewById(R.id.vT_lsil_description);

            mRootLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.mainContactLayout) {
                boolean shouldExpandDetailsLayout = mDetailsLayout.getVisibility() == View.GONE;

                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(200);

                if (shouldExpandDetailsLayout) {
                    // Expand the the item which is previously not expanded
                    mRootLayout.setBackground(mContext.getResources().getDrawable(R.drawable.focused_card));

                    // notifyItemChanged(position);
                    mDetailsLayout.setVisibility(View.VISIBLE);

                } else {
                    //collapse the item which is previously opened
                    mRootLayout.setBackground(null);
                    mDetailsLayout.setVisibility(View.GONE);
                }

                TransitionManager.beginDelayedTransition(mRecyclerView, autoTransition);
                mRootLayout.setActivated(shouldExpandDetailsLayout);

                mRootLayout.setActivated(false);
            }

        }
    }
}
