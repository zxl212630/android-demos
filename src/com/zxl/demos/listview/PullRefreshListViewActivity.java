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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.zxl.demos.listview.PullRefreshListView.OnPullRefreshListener;
import com.zxl.demos.util.ZLog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

public class PullRefreshListViewActivity extends Activity {
	private static final String TAG = ".listview.PullRefreshListViewActivity";
	private static long mCursor = 0;
	private PullRefreshListView mListView;	
	private ArrayAdapter<String> mAdapter;
	private List<String> mData;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ZLog.d(TAG, "onCreate");
		
		mData = getData();
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1,mData);
		
		mListView = new PullRefreshListView(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnPullRefreshListener(mPullRefreshListener);
		setContentView(mListView);
	}
	
	private OnPullRefreshListener mPullRefreshListener = new OnPullRefreshListener() {
		@Override
		public void onRefresh() {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mHandler.sendEmptyMessage(1);
				}
			}).start();
		}
	};
	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		
		while(mCursor<6){
			data.add(0, "测试数据" + (++mCursor));
		}
		return data;
	}

	private Handler mHandler = new MyHandler(this){

		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 1){//完成刷新
				mData.add(0, "测试数据" + (++mCursor));
				mAdapter.notifyDataSetChanged();
				mListView.pullRefreshComplete();
			}
		}
		
	};
	
	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler{
		WeakReference<Activity> mActivity;
		
		public MyHandler(Activity activity){
			mActivity = new WeakReference<Activity>(activity);
		}
	};
}
