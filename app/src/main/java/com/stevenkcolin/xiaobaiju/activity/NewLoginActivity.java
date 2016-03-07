package com.stevenkcolin.xiaobaiju.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.service.UserService;
import com.stevenkcolin.xiaobaiju.util.DialogUtil;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class NewLoginActivity extends BaseActivity {
    private UMShareAPI mShareAPI = null;
    private ProgressDialog mProgressDialog;

    private Button mBtnQQLogin;
    private Button mBtnWeixinLogin;
    private Button mBtnWeibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        mBtnQQLogin = (Button)this.findViewById(R.id.btn_QQ_Login);
        mBtnWeixinLogin = (Button)this.findViewById(R.id.btn_Weixin_Login);
        mBtnWeibo = (Button) this.findViewById(R.id.btn_Weibo_Login);
        initLogin();

        mBtnQQLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginQQ(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_login, menu);
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

    private void initLogin() {
        /** init auth api**/
        try {
            mShareAPI = UMShareAPI.get(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //微信    wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
    }

    //添加登录QQ的代码
    public void loginQQ(View view) {
        if (view.getId() == R.id.btn_QQ_Login) {
            SHARE_MEDIA platform = SHARE_MEDIA.QQ;
            /**begin invoke umeng api**/
            try {
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * auth callback interface
     **/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data != null && data.get("openid") != null && data.get("screen_name") == null) {
                mShareAPI.getPlatformInfo(NewLoginActivity.this, platform, umAuthListener);
            } else if (data != null && data.get("openid") != null && data.get("screen_name") != null) {
                String openId = data.get("openid");
                String name = data.get("screen_name");
                String imageUrl = data.get("profile_image_url");
                new LoginTask().execute(new String[]{GeneralConstant.QQ, openId, name, imageUrl});
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(NewLoginActivity.this, "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(NewLoginActivity.this, "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    //登录
    class LoginTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            mProgressDialog = DialogUtil.showWaitDialog(NewLoginActivity.this, getString(R.string.please_wait));
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String loginFrom = params[0];
                String loginAccount = params[1];
                String name = params[2];
                try {
                    UserService userService = new UserService();
                    userService.login3rdAcountExist(loginFrom, loginAccount, name, NewLoginActivity.this);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
            catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                mProgressDialog.dismiss();
            } else {
                mProgressDialog.dismiss();
                Toast.makeText(NewLoginActivity.this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
            }
            Intent i = new Intent();
            setResult(Activity.RESULT_OK,i);
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
