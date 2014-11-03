/*********************** 佛祖保佑 *************************
 *                    _ooOoo_ 
 *                   o8888888o 
 *                   88" . "88 
 *                   (| -_- |) 
 *                   O\  =  /O 
 *                ____/`---'\____ 
 *              .'  \\|     |//  `. 
 *             /  \\|||  :  |||//  \ 
 *            /  _||||| -:- |||||-  \ 
 *            |   | \\\  -  /// |   | 
 *            | \_|  ''\---/''  |   | 
 *            \  .-\__  `-`  ___/-. / 
 *          ___`. .'  /--.--\  `. . __ 
 *       ."" '<  `.___\_<|>_/___.'  >'"". 
 *     | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
 *      \  \ `-.   \_ __\ /__ _/   .-` /  / 
 * ======`-.____`-.___\_____/___.-`____.-'====== 
 *                  `=---=' 
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
 *********************** 版权声明 *********************************
 * 
 * 版权所有：XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
 * 
 ************************* 变更记录 *********************************
 *
 * 创建者：ZXL    创建日期： 2014年10月25日
 * 版本：1.0.0
 * 创建记录：创建类结构。
 * 
 * 修改者：             修改日期：
 * 版本：
 * 修改记录：
 *
 * 修改者：             修改日期：
 * 版本：
 * 修改记录：
 * ……
 ************************* To  Do *********************************
 *
 * 1、实现所有未实现的方法（抛出UnsupportedOperationException的方法）。
 * 
 ************************* 随   笔 *********************************
 *
 * 
 * 
 ******************************************************************
 *
 */
package com.zxl.demos.listview;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.zxl.demos.R;
import com.zxl.demos.util.ZLog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class PullRefreshListView extends ListView implements OnScrollListener {
	private static final String TAG = ".listview.PullRefreshListView";
	private final static int REFRESH_STATUS_NORMAL = 0;  
    private final static int REFRESH_STATUS_PULL_REFRESH = 1;  
    private final static int REFRESH_STATUS_RELEASE_REFRESH = 2;  
    private final static int REFRESH_STATUS_LOADING= 3;
    
//    private final static int REFRESH_BACKING = 0;      //反弹中
//    private final static int REFRESH_BACED = 1;        //达到刷新界限，反弹结束后
//    private final static int REFRESH_RETURN = 2;       //没有达到刷新界限，返回
//    private final static int REFRESH_DONE = 3;         //加载数据结束
    
    // 实际的padding的距离与界面上偏移距离的比例  
    private final static int RATIO = 3;
    
    private int mDownY;
    private int mMoveY;
    
    //store the index of first item, if index not 0 , will not allow pull refresh
    private int mFirstItemIndex;
    private boolean mIsRecord;
        
    private LayoutInflater mInflater;
    
    
    //下拉刷新头部
    private LinearLayout mHeader;
    private int mHeaderContentHeight;  
    // 下拉刷新的操作提示，如下拉刷新，释放立即刷新，正在刷新...
    private TextView mTipsTextview;
	private String mLoadingText = "正在刷新...";
	private String mReleaseText = "松开刷新";
    private String mRefreshText = "下拉刷新";
    private TextView mLastUpdatedTextView;  
    private ImageView mArrowImageView;  
    private ProgressBar mProgressBar; 
        
    //当前下拉刷新的状态
    private int mPullRefreshStatus;
    //是否允许刷新，只有添加了刷新的监听事件才允许刷新
    private boolean mIsRefreshable;
    //刷新的监听事件
	private OnPullRefreshListener mPullRefreshListener; 
	
	private DateFormat mDateFormat; 
    
    public DateFormat getDateFormat() {
		return mDateFormat;
	}
	public void setDateFormat(DateFormat mDateFormat) {
		this.mDateFormat = mDateFormat;
	}
	public TextView getTipsTextview() {
		return mTipsTextview;
	}
	public void setTipsTextview(TextView mTipsTextview) {
		this.mTipsTextview = mTipsTextview;
	}
	public String getReleaseText() {
		return mReleaseText;
	}
	public void setReleaseText(String mReleaseText) {
		this.mReleaseText = mReleaseText;
	}
	public String getRefreshText() {
		return mRefreshText;
	}
	public void setRefreshText(String mRefreshText) {
		this.mRefreshText = mRefreshText;
	}

	public boolean isRefreshable() {
		return mIsRefreshable;
	}

    public String getLoadingText() {
		return mLoadingText;
	}
	public void setLoadingText(String mLoadingText) {
		this.mLoadingText = mLoadingText;
	}
	public void setTipsText(String text){
		mTipsTextview.setText(text);
	}
	
	public void setOnPullRefreshListener(OnPullRefreshListener l){
		if(null != l){
			mIsRefreshable = true;
			mPullRefreshListener = l;
		}		
	}
	
	
	public void pullRefreshComplete(){
		mHeader.setPadding(mHeader.getPaddingLeft(), 
				 0 - mHeaderContentHeight, 
				 mHeader.getPaddingRight(), 
				 mHeader.getPaddingBottom());
		mLastUpdatedTextView.setText("更新于:" + mDateFormat.format(new Date()));
		changeRefreshStatus(REFRESH_STATUS_NORMAL);
	}

	public PullRefreshListView(Context context) {
		this(context,null);
	}
	public PullRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	@SuppressLint("InflateParams")
	private void init(Context context){
		mInflater = LayoutInflater.from(context);
		
		mHeader = (LinearLayout)mInflater.inflate(R.layout.pull_refresh_listview_header, null);
		addHeaderView(mHeader);
		
		mArrowImageView = (ImageView)mHeader.findViewById(R.id.head_arrowImageView);
		mArrowImageView.setMinimumHeight(50);
		mArrowImageView.setMinimumWidth(70);
		
		mProgressBar = (ProgressBar)mHeader.findViewById(R.id.head_progressBar);
		
		mTipsTextview = (TextView)mHeader.findViewById(R.id.head_tipsTextView);
		
		mLastUpdatedTextView = (TextView)mHeader.findViewById(R.id.head_lastUpdatedTextView);
		
		measureView(mHeader);
		
		mHeaderContentHeight = mHeader.getMeasuredHeight();
		
		mHeader.setPadding(0, -1 * mHeaderContentHeight, 0, 0);
		mHeader.invalidate();
		
		setSelection(1);
		
		setOnScrollListener(this);
		
		mPullRefreshStatus = PullRefreshListView.REFRESH_STATUS_NORMAL;
		
		mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
	}
	
	//因为是在构造函数里测量高度，应该先measure一下
    private void measureView(View child) {  
         ViewGroup.LayoutParams p = child.getLayoutParams();  
            if (p == null) {  
                p = new ViewGroup.LayoutParams(  
                        ViewGroup.LayoutParams.MATCH_PARENT,  
                        ViewGroup.LayoutParams.WRAP_CONTENT);  
            }  
  
            int childWidthSpec = ViewGroup.getChildMeasureSpec(0,  
                    0 + 0, p.width);  
            int lpHeight = p.height;  
            int childHeightSpec;  
            if (lpHeight > 0) {  
                childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);  
            } else {  
                childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);  
            }  
            child.measure(childWidthSpec, childHeightSpec);  
    }


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		ZLog.d(TAG, "onScroll: mPullRefreshState=%d,firstVisibleItem=%d,"
				+ "headerBottom=%d",
				mPullRefreshStatus,firstVisibleItem,
				mHeader.getBottom());
		mFirstItemIndex = firstVisibleItem;
	}
	
	@Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        setSelection(1);
    }
	
    @SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
    	if(mIsRefreshable){
    		switch(ev.getAction()){
    		case MotionEvent.ACTION_DOWN:
    			doActionDown(ev);
    			break;
    		case MotionEvent.ACTION_MOVE:
    			doActionMove(ev);
    			break;
    		case MotionEvent.ACTION_UP:
    			doActionUp(ev);
    			break;
    		}
    	}
		return super.onTouchEvent(ev);
	}
    
    /**
     * 触摸action_down时调用
     * @param ev
     */
    private void doActionDown(MotionEvent ev){
    	mDownY = (int)ev.getY();
    	ZLog.d(TAG, "doActionDown: mDownY=%d,",mDownY);
    }
    
    /**
     * 触摸 action_move事件调用
     * @param ev
     */
    private void doActionMove(MotionEvent ev){
    	ZLog.d(TAG, "doActionMove: mPullRefreshState=%d,"
    			+ "bottom:%d,paddingTop=%d,"
    			+ "%d-%d=%d",
    			mPullRefreshStatus,
    			mHeader.getBottom(),mHeader.getPaddingTop(),
    			(int)mMoveY,(int)mDownY,(int)(mMoveY - mDownY));
    	mMoveY = (int)ev.getY();
    	if(0 == mFirstItemIndex && false == mIsRecord){
    		mDownY = mMoveY;
    		mIsRecord = true;
    	}
    	if(!mIsRecord || REFRESH_STATUS_LOADING == mPullRefreshStatus){
    		return;
    	}
     	
    	int offset = (int)(mMoveY - mDownY) / RATIO;
    	
    	switch(mPullRefreshStatus){
    	case REFRESH_STATUS_NORMAL:
    		if(offset > 0){
    			mHeader.setPadding(mHeader.getPaddingLeft(), 
    					offset - mHeaderContentHeight, 
    					mHeader.getPaddingRight(), 
    					mHeader.getPaddingBottom());
    			changeRefreshStatus(REFRESH_STATUS_PULL_REFRESH);
    		}
    		break;
    	case REFRESH_STATUS_PULL_REFRESH:
    		setSelection(0);
    		mHeader.setPadding(mHeader.getPaddingLeft(), 
					offset - mHeaderContentHeight, 
					mHeader.getPaddingRight(), 
					mHeader.getPaddingBottom());
    		if(offset < 0){
    			changeRefreshStatus(REFRESH_STATUS_NORMAL);
    		}else if(offset > mHeaderContentHeight){
    			changeRefreshStatus(REFRESH_STATUS_RELEASE_REFRESH);
    		}
    		break;
    	case REFRESH_STATUS_RELEASE_REFRESH:
    		setSelection(0);
    		mHeader.setPadding(mHeader.getPaddingLeft(), 
					offset - mHeaderContentHeight, 
					mHeader.getPaddingRight(), 
					mHeader.getPaddingBottom());
    		if (offset >= 0 && offset <= mHeaderContentHeight){
    			changeRefreshStatus(REFRESH_STATUS_PULL_REFRESH);
    		}else if(offset < 0){
    			changeRefreshStatus(REFRESH_STATUS_NORMAL);
    		}
    		break;
    	default:
    		return;
    	}
    }
    
    private void doActionUp(MotionEvent ev){
    	mIsRecord = false;
		
		if(REFRESH_STATUS_LOADING == mPullRefreshStatus){
			return;
		}
		if(mPullRefreshStatus == REFRESH_STATUS_PULL_REFRESH){
			mHeader.setPadding(mHeader.getPaddingLeft(), 
					0 - mHeaderContentHeight, 
					mHeader.getPaddingRight(), 
					mHeader.getPaddingBottom());
			changeRefreshStatus(REFRESH_STATUS_NORMAL);
		}else if(mPullRefreshStatus == REFRESH_STATUS_RELEASE_REFRESH){
			mHeader.setPadding(mHeader.getPaddingLeft(), 
					mHeader.getPaddingRight(), 
					mHeader.getPaddingRight(), 
					mHeader.getPaddingBottom());
			changeRefreshStatus(REFRESH_STATUS_LOADING);
		}
	}
    
    private void changeRefreshStatus(int status){
    	switch(status){
    	case REFRESH_STATUS_NORMAL:
    		setTipsText(getRefreshText());
    		mProgressBar.setVisibility(View.GONE);
    		mArrowImageView.setVisibility(View.VISIBLE);
    		break;
    	case REFRESH_STATUS_PULL_REFRESH:
    		setTipsText(getRefreshText());
    		mArrowImageView.setImageResource(R.drawable.arrow_down);
    		break;
    	case REFRESH_STATUS_RELEASE_REFRESH:
    		setTipsText(getReleaseText());
    		mArrowImageView.setImageResource(R.drawable.arrow_up);
    		break;
    	case REFRESH_STATUS_LOADING:
    		mProgressBar.setVisibility(View.VISIBLE);
    		mArrowImageView.setVisibility(View.GONE);
    		setTipsText(getLoadingText());
    		onRefresh();
    		break;
    	}
    	mPullRefreshStatus = status;    	
    }
    
    private void onRefresh(){
    	if(mPullRefreshListener != null){
    		mPullRefreshListener.onRefresh();
    	}
    }

	/**
     * 内部类
     * @author zxl
     *
     */
    

	public interface OnPullRefreshListener{
		void onRefresh();
	}
}
