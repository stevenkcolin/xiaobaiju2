package com.stevenkcolin.xiaobaiju.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.constant.ReportConstant;
import com.stevenkcolin.xiaobaiju.model.User;
import com.stevenkcolin.xiaobaiju.report.ActionInfo;
import com.stevenkcolin.xiaobaiju.service.TaskService;
import com.stevenkcolin.xiaobaiju.service.UserService;
import com.stevenkcolin.xiaobaiju.util.DialogUtil;
import com.stevenkcolin.xiaobaiju.util.SyncUtil;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by Pengfei on 2016/2/2.
 */
public class MineFragment extends BaseFragment {
    private UMShareAPI mShareAPI = null;

    private ProgressDialog mProgressDialog;
    private TextView txtName;
    private Button btnLogin;
    private Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtName = (TextView)getView().findViewById(R.id.user_name);
        btnLogin = (Button)getView().findViewById(R.id.login_account);
        btnLogout = (Button)getView().findViewById(R.id.logout_account);

        if (User.getUser().getId() == null) {
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
        } else {
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        }

        if (User.getUser().getName() != null) {
            txtName.setText(User.getUser().getName());
        }

        //添加aboutUs事件
        openAboutUs();
        initLogin();
        openLoginPage();
    }

    //给左侧餐单按钮about_us添加事件，欢迎打开官方网站
    private void openAboutUs() {
        getView().findViewById(R.id.about_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.txt_about_us_cotent, Toast.LENGTH_SHORT).show();
                //添加打点上报代码
                ActionInfo mActionInfo = new ActionInfo(ReportConstant.REPORT_MENU_ABOUTUS);
                mReport.saveOnClick(getContext(), mActionInfo);
                // TODO: 1/8/16 完成官方网站的web端，并打开官方网站。
            }
        });
    }

    private void initLogin() {
        /** init auth api**/
        try {
            mShareAPI = UMShareAPI.get(getActivity());
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

    public UMShareAPI getmShareAPI() {
        return mShareAPI;
    }

    //给左侧菜单按钮login_account添加事件，打开login Page
    private void openLoginPage() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginQQ(view);
            }
        });
    }

    //添加登录QQ的代码
    public void loginQQ(View view) {
        if (view.getId() == R.id.login_account) {
            SHARE_MEDIA platform = SHARE_MEDIA.QQ;
            /**begin invoke umeng api**/
            try {
                mShareAPI.doOauthVerify(getActivity(), platform, umAuthListener);

                //添加打点上报代码
                ActionInfo mActionInfo = new ActionInfo(ReportConstant.REPORT_MENU_LOGIN);
                mReport.saveOnClick(getContext(), mActionInfo);
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
                mShareAPI.getPlatformInfo(getActivity(), platform, umAuthListener);
            } else if (data != null && data.get("openid") != null && data.get("screen_name") != null) {
                String openId = data.get("openid");
                String name = data.get("screen_name");
                String imageUrl = data.get("profile_image_url");
                new LoginTask().execute(new String[]{GeneralConstant.QQ, openId, name, imageUrl});
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
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
            mProgressDialog = DialogUtil.showWaitDialog(getActivity(), getString(R.string.please_wait));
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String loginFrom = params[0];
            String loginAccount = params[1];
            String name = params[2];
            try {
                UserService userService = new UserService();
                userService.login3rdAcountExist(loginFrom, loginAccount, name, getActivity());
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                txtName.setText(User.getUser().getName());
                btnLogin.setVisibility(View.GONE);
                btnLogout.setVisibility(View.VISIBLE);
                if (!SyncUtil.isSync) {
                    new SyncTask().execute();
                }
                mProgressDialog.dismiss();
            } else {
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class SyncTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            SyncUtil.isSync = true;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            TaskService taskService = new TaskService();
            try {
                taskService.syncTask();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            SyncUtil.isSync = false;
            mProgressDialog.dismiss();
        }
    }


}
