package cn.edu.bit.cs.myfileexplorer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.Window;


import cn.edu.bit.cs.myfileexplorer.model.ExplorerMode;

/**
 * 封装文件管理的相关功能 用法： 1 首先在manifest.xml中声明FileExplorerActivity <activity
 * android:name="cn.edu.bit.cs.myfileexplorer.FileExplorerActivity"
 * android:label="@string/title_activity_file_explorer" > </activity>
 * 
 * 2 使用以下代码启动之 Intent intent=new
 * Intent(MainActivity.this,FileExplorerActivity.class);
 * intent.putExtra(FileExplorerActivity.EXPLORER_MODE_KEY,useMode);
 * intent.putExtra(FileExplorerActivity.INITPATH_KEY, "/mnt/sdcard");
 * startActivityForResult(intent, requestCode);
 * 
 * 3 在onActivityResult中编写以下响应代码：
 * 
 * @Override protected void onActivityResult(int requestCode, int resultCode,
 *           Intent data) { if(resultCode!=RESULT_OK){ return; } switch
 *           (requestCode) { case SINGLE_FILE_CHOOSE_REQUEST: String
 *           selectedFile
 *           =data.getStringExtra(FileExplorerFragmentContants.SELECTED_PATH);
 *           break; case SINGLE_DIRECTORY_CHOOSE_REQUEST: String
 *           selectedDir=data
 *           .getStringExtra(FileExplorerFragmentContants.SELECTED_PATH); break;
 * 
 *           default: break; } super.onActivityResult(requestCode, resultCode,
 *           data); }
 * @author JinXuLiang
 * 
 */
public class FileExplorerActivity extends Activity {

	public static final String EXPLORER_MODE_KEY = "ExplorerMode";
	public static final String INITPATH_KEY = "InitPath";
	public static final String FILELIST_BACKGROUND = "FileListBackground";
	private FileExplorerFragment fragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_file_explorer);
		setTitle("文件浏览器");
        getActionBar().setDisplayHomeAsUpEnabled(true);
		initFragments();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * 初始化Fragment
	 */
	private void initFragments() {
		ExplorerMode useMode = (ExplorerMode) (getIntent()
				.getSerializableExtra(EXPLORER_MODE_KEY));
		String initPath = getIntent().getStringExtra(INITPATH_KEY);
		String colorString = getIntent().getStringExtra(FILELIST_BACKGROUND);

		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		fragment = FileExplorerFragment.newInstance(initPath, useMode,
				colorString);
		ft.replace(R.id.fragment_container, fragment, "FileExplorerFragment");

		ft.commit();
	}

}