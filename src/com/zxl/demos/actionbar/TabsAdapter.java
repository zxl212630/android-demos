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

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class TabsAdapter extends PagerAdapter {
	ArrayList<View> mViewList = new ArrayList<View>();
	
	public TabsAdapter(ArrayList<View> viewList){
		mViewList = viewList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mViewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViewList.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mViewList.get(position));
		return mViewList.get(position);
	}
	
	
}
