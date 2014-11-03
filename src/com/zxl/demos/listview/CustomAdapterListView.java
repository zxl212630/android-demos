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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zxl.demos.R;
import com.zxl.demos.util.ZLog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class CustomAdapterListView extends Activity {
	private static final String TAG = ".listview.CustomAdapterListView";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ZLog.d(TAG, "onCreate");
		
		MyAdapter adapter = new MyAdapter(this, getData());
		
		ListView view = new ListView(this);
		view.setAdapter(adapter);
		
		setContentView(view);
	}

	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "G1");
		map.put("info", "google 1");
		map.put("img", R.drawable.i1);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G2");
		map.put("info", "google 2");
		map.put("img", R.drawable.i2);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		map.put("img", R.drawable.i3);
		list.add(map);
		
		return list;
	}
}
