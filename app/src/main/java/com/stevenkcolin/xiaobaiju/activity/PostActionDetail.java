package com.stevenkcolin.xiaobaiju.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.dao.PostActionDao;
import com.stevenkcolin.xiaobaiju.model.PostAction;

public class PostActionDetail extends BaseActivity {

    private PostAction mPostAction;
    private EditText mEditTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_action);

        mPostAction = getIntent().getSerializableExtra("PostAction") == null ? null : (PostAction)getIntent().getSerializableExtra("PostAction");
        mEditTitle = (EditText)findViewById(R.id.edtTitlePostActions);

        if (mPostAction != null) {
            String strTitle = mPostAction.getTitle().toString();
            mEditTitle.setText(strTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_action, menu);
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

    //点击返回的时候，会触发onPause事件，然后保存taskDetail中的数据
    @Override
    protected void onPause()
    {
        super.onPause();
        saveData();

    }

    //保存taskdetail中的数据
    private void saveData()
    {
        //取得editTitle的值
        String title = mEditTitle.getText().toString();
        if (title.trim().length()>0) {
//            创建task对象, 并保存task
            if (mPostAction == null) {
                mPostAction = new PostAction();
                mPostAction.setTitle(title);
            } else {
                mPostAction.setTitle(title);
            }
            PostActionDao.save(mPostAction);
        }
    }
}
