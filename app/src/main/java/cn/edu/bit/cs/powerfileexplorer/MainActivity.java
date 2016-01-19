package cn.edu.bit.cs.powerfileexplorer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.bit.cs.myfileexplorer.FileExplorerActivity;
import cn.edu.bit.cs.myfileexplorer.FileExplorerFragmentContants;
import cn.edu.bit.cs.myfileexplorer.model.ExplorerMode;
import cn.edu.bit.cs.ui.UIHelper;


public class MainActivity extends Activity {
    //三种模式
    private static final int NORMAL=1;
    private static final int SINGLE_FILE_CHOOSE_REQUEST=2;
    private static final int SINGLE_DIRECTORY_CHOOSE_REQUEST=3;

    @InjectView(R.id.btnTest)
    Button testButton;

    @InjectView(R.id.main_rdoChooseSingleFile)
    RadioButton rdoSingleFile;

    @InjectView(R.id.main_rdoChooseFolder)
    RadioButton rdoSingleDirectory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注入ButterKnife组件
        ButterKnife.inject(this);
        setTitle("文件浏览器示例");

    }

    @OnClick(R.id.btnTest)
    void test() {

        int requestCode=NORMAL;
        ExplorerMode useMode=ExplorerMode.NORMAL;
        if(rdoSingleDirectory.isChecked()){
            useMode=ExplorerMode.CHOOSE_DIRECTORY_SINGLE;
            requestCode=SINGLE_DIRECTORY_CHOOSE_REQUEST;

        }
        else {
            if(rdoSingleFile.isChecked()){
                useMode=ExplorerMode.CHOOSE_FILE_SINGLE;
                requestCode=SINGLE_FILE_CHOOSE_REQUEST;

            }
        }
        Intent intent=new Intent(MainActivity.this,FileExplorerActivity.class);
        intent.putExtra(FileExplorerActivity.EXPLORER_MODE_KEY,useMode);
        intent.putExtra(FileExplorerActivity.INITPATH_KEY, "/mnt/sdcard");
        intent.putExtra(FileExplorerActivity.FILELIST_BACKGROUND, "200,127,0");
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode) {
            case SINGLE_FILE_CHOOSE_REQUEST:
                String selectedFile=data.getStringExtra(FileExplorerFragmentContants.SELECTED_PATH);
                UIHelper.toastShowMessageShort(this,selectedFile);
                break;
            case SINGLE_DIRECTORY_CHOOSE_REQUEST:
                String selectedDir=data.getStringExtra(FileExplorerFragmentContants.SELECTED_PATH);
                UIHelper.toastShowMessageShort(this,selectedDir);
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
