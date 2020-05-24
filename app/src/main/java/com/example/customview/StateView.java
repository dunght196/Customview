package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StateView extends RelativeLayout {
    private final String TAG = this.getClass().getSimpleName();
    public static final int UNKNOWN = -1;
    public static final int CONTENT = 0;
    public static final int ERROR = 1;
    public static final int EMPTY = 2;
    public static final int LOADING = 3;
    public static final int NETWORK = 4;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UNKNOWN, CONTENT, ERROR, EMPTY, LOADING, NETWORK})
    public @interface State {

    }

    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mNetworkView;

    @State
    private int mViewState = UNKNOWN;


    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.StateView);
        int loadingViewResId = a.getResourceId(R.styleable.StateView_sv_loadingView, R.layout.state_view_loading);
        int networkViewResId = a.getResourceId(R.styleable.StateView_sv_networkView, R.layout.state_view_network);
        int viewState = a.getInt(R.styleable.StateView_sv_viewState, CONTENT);
        switch (viewState) {
            case CONTENT:
                mViewState = CONTENT;
                break;
            case ERROR:
                mViewState = ERROR;
                break;
            case EMPTY:
                mViewState = EMPTY;
                break;
            case LOADING:
                mViewState = LOADING;
                break;
            case NETWORK:
                mViewState = NETWORK;
                break;
            case UNKNOWN:
            default:
                mViewState = UNKNOWN;
                break;
        }
        a.recycle();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        mLoadingView = inflater.inflate(loadingViewResId, this, false);
        addView(mLoadingView, mLoadingView.getLayoutParams());

        mNetworkView = inflater.inflate(networkViewResId, this, false);
        addView(mNetworkView, mNetworkView.getLayoutParams());
    }


    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if(isValidContentView(child)) mContentView = child;
        super.addView(child, params);
    }

    private boolean isValidContentView(View view) {
        if(mContentView != null && mContentView != view) {
            return false;
        }
        return view != mLoadingView && view != mErrorView && view != mEmptyView && view != mNetworkView;
    }

    public void setViewState(@State int state) {
        if(state != mViewState) {
            mViewState = state;
            setViewState(mViewState, null);
        }
    }

    public void setViewState(@State int state, String customMsg) {
        switch (state) {
            case LOADING:
                if (mLoadingView == null) {
                    throw new NullPointerException("Loading View");
                }
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                if (mNetworkView != null) mNetworkView.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(customMsg)) {
                    try {
                        ((TextView) mLoadingView.findViewById(R.id.msv_loading_msg_text)).setText(customMsg);
                    } catch (Exception e) {
                        Log.e(TAG,
                                "Have to a 'R.id.sv_loading_msg_text' id in custom loading layout.");
                    }
                }
                break;
            case NETWORK:
                if (mNetworkView == null) {
                    throw new NullPointerException("Network View");
                }
                if (mContentView != null) mContentView.setVisibility(View.GONE);
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
                mNetworkView.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(customMsg)) {
                    try {
                        ((TextView) mNetworkView.findViewById(R.id.msv_network_msg_text)).setText(customMsg);
                    } catch (Exception e) {
                        Log.e(TAG,
                                "Have to a 'R.id.sv_loading_msg_text' id in custom loading layout.");
                    }
                }
                break;
            case CONTENT:
            default:
                if (mContentView == null) {
                    throw new NullPointerException("Content View null");
                }
                if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);

                mContentView.setVisibility(View.VISIBLE);
                break;
        }
    }


}
