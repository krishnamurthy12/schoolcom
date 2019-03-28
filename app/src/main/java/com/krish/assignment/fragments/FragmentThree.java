package com.krish.assignment.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.krish.assignment.R;
import com.krish.assignment.adapters.TabThreeAdapter;
import com.krish.assignment.adapters.TabTwoAdapter;
import com.krish.assignment.api_responses.example_three.Article;
import com.krish.assignment.api_responses.example_three.ExampleResponseThree;
import com.krish.assignment.api_responses.example_two.ExampleResponseTwo;
import com.krish.assignment.utils.OnResponseListener;
import com.krish.assignment.utils.Utilities;
import com.krish.assignment.utils.WebServices;

import java.util.ArrayList;
import java.util.List;

import static com.krish.assignment.utils.Utilities.showSnackBar;
import static com.krish.assignment.utils.Utilities.showToast;
import static com.krish.assignment.utils.Utilities.toggleVisibility;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnResponseListener {


    List<Article> mList=new ArrayList<>();
    RecyclerView mRecyclerView;
    TabThreeAdapter tabThreeAdapter;

    ProgressBar mProgressBar;
    SwipeRefreshLayout mRefreshPage;

    Context mContext;
    View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_two, container, false);
        initializeViews();
        return view;

    }

    private void initializeViews() {

        mProgressBar=view.findViewById(R.id.progressbar_fragment_two);

        mRefreshPage=view.findViewById(R.id.swipeRefreshLayout_fragment_two);

        mRecyclerView=view.findViewById(R.id.vR_recyclerview_fragment_two);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mRefreshPage.setOnRefreshListener(this);

        callDemoAPI();

    }

    public void callDemoAPI() {
        String[] parameters={"techcrunch","efff147ad31d4ce3b42ec99d9de81ea4"};
        if (Utilities.isConnectedToInternet(mContext)) {
            toggleVisibility(true, mProgressBar);
            WebServices<ExampleResponseThree> webServices = new WebServices<ExampleResponseThree>(this);
            webServices.callDemoAPIThree(Utilities.getBaseURL(), WebServices.ApiType.demoapi3,parameters);

        } else {
            showToast(mContext, getResources().getString(R.string.err_msg_nointernet));

        }

    }

    @Override
    public void onRefresh() {
        mRefreshPage.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.greenlight));
        if(mProgressBar.getVisibility()==View.VISIBLE)
        {
            showSnackBar(mContext,"Wait untill the current process to finish");
        }
        else {
            refreshContent();
        }


    }

    public void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callDemoAPI();
                mRefreshPage.setRefreshing(false);

            }
        },3000);
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType URL, boolean isSucces, int code) {
        switch (URL)
        {
            case demoapi3:
                toggleVisibility(false, mProgressBar);
                if(isSucces)
                {
                    ExampleResponseThree exampleResponse= (ExampleResponseThree) response;
                    if(exampleResponse!=null)
                    {
                        if(exampleResponse.getArticles()!=null)
                        {
                            //Success case
                            mList=exampleResponse.getArticles();

                            tabThreeAdapter=new TabThreeAdapter(mContext,mList,mRecyclerView);
                            mRecyclerView.setAdapter(tabThreeAdapter);
                            tabThreeAdapter.notifyDataSetChanged();
                        }else {
                            //List is null
                            showSnackBar(mContext, "List is empty");
                        }
                    }
                    else {
                        //Null Response
                        showSnackBar(mContext, "Something went wrong please try again");
                    }

                }
                else {
                    //API Call Failed
                    showSnackBar(mContext, "API call failed");
                }
        }
    }

}
