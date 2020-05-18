package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StateView extends RelativeLayout {

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
        super(context);
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

    }


}
