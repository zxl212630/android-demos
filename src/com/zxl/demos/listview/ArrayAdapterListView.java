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
 * 创建者：ZXL    创建日期： 2014年10月24日
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

import java.util.ArrayList;
import java.util.List;

import com.zxl.demos.util.ZLog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArrayAdapterListView extends Activity {
	private static final String TAG = "com.zxl.demos.listview.SimpleListView";
	
	private ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		ZLog.d(TAG, "onCreate");
		
		mListView = new ListView(this);
		mListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1,getData()));
		
		setContentView(mListView);
	}
	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		
		data.add("测试数据1");
		data.add("测试数据2");
		data.add("测试数据3");
		data.add("测试数据4");
		data.add("测试数据5");
		data.add("测试数据6");
		data.add("测试数据7");
		data.add("测试数据8");
		
		return data;
	}
	  
}
