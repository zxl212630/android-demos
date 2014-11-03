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
 * 创建者：ZXL    创建日期： 2014年10月23日
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
package com.zxl.demos.actionbar;

import java.util.ArrayList;

import com.zxl.demos.R;
import com.zxl.demos.util.ZLog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ActionBarMainActivity extends ActionBarActivity implements TabListener{
	private static final String TAG = ".actionbar.ActionBarMainActivity";
	private ActionBar mActionBar;
	private ViewPager mViewPager;
	TabsAdapter mTabsAdapter; 
	private ArrayList<View> mViewList = new ArrayList<View>();
	private int[] mImageIdArray ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		mImageIdArray = new int[]{R.drawable.item01, R.drawable.item02, R.drawable.item03, R.drawable.item04,
				R.drawable.item05,R.drawable.item06, R.drawable.item07, R.drawable.item08};
		
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for(int i=1;i<=8;i++){
			ImageView view = new ImageView(this);
			view.setBackgroundResource(mImageIdArray[i-1]);			
			mViewList.add(view);
			
		}
				
		mViewPager = new ViewPager(this);
		mViewPager.setAdapter(new TabsAdapter(mViewList));
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				ZLog.d(TAG,"onPageSelected: " + position);
				mActionBar.setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				ZLog.d(TAG, "onPageScrolled: (%d,%f,%d)", arg0, arg1, arg2);				
			
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				ZLog.d(TAG, "onPageScrollStateChanged: " + arg0);
			}			
			
		});
		
		for(int i=1;i<=8;i++){
			mActionBar.addTab(mActionBar.newTab().setText(i+"号美女").setTabListener(this));
		}
		
		setContentView(mViewPager);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_main, menu);
		MenuItem menuItem = menu.findItem(R.id.action_otherview);
		View view = MenuItemCompat.getActionView(menuItem);
		
//      对于API 11 以及以上的版本，获取ActionView直接调用以下代码即可  
//      View view = item.getActionView();  
		
		Button btn1 = (Button)view.findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "btn1 被点击", 
						Toast.LENGTH_LONG).show();;
			}
		});
		
		MenuItemCompat.setOnActionExpandListener(menuItem, new OnActionExpandListener() {  
	        @Override  
	        public boolean onMenuItemActionCollapse(MenuItem item) {  
	            Toast.makeText(getApplicationContext(), "啊哦，我隐藏起来了！", Toast.LENGTH_SHORT).show();  
	            return true;  
	        }  
	  
	        @Override  
	        public boolean onMenuItemActionExpand(MenuItem item) {  
	            Toast.makeText(getApplicationContext(), "啦啦啦，我出现喽！", Toast.LENGTH_SHORT).show();  
	            return true;  
	        }  
	    });  
		
		
		MenuItem shareItem = menu.findItem(R.id.action_share); 
		ShareActionProvider mShareActionProvider = (ShareActionProvider)  
                MenuItemCompat.getActionProvider(shareItem);  
		mShareActionProvider.setShareIntent(getDefaultIntent());
		
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_search:
			ZLog.d(TAG,"clicked the search menu");
			break;
		case R.id.action_compose:
			ZLog.d(TAG,"clicked the compose menu");
			break;
		case android.R.id.home:
			finish();
			break;
		}
		return true;
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());		
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	private Intent getDefaultIntent() {  
	    Intent intent = new Intent(Intent.ACTION_SEND);  
	       intent.setType("text/plain");    
	       intent.putExtra(Intent.EXTRA_SUBJECT, "分享");     
	       intent.putExtra(Intent.EXTRA_TEXT, "你好 ");    
	       intent.putExtra(Intent.EXTRA_TITLE, "我是标题");  
	    return intent;  
	}  
}
