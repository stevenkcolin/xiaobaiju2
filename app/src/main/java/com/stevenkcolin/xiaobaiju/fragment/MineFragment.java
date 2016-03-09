package com.stevenkcolin.xiaobaiju.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.activity.NewLoginActivity;
import com.stevenkcolin.xiaobaiju.constant.ReportConstant;
import com.stevenkcolin.xiaobaiju.model.User;
import com.stevenkcolin.xiaobaiju.report.ActionInfo;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by Pengfei on 2016/2/2.
 */
public class MineFragment extends BaseFragment {
    private UMShareAPI mShareAPI = null;

    private ProgressDialog mProgressDialog;
    private TextView mTxtName;
    private Button mBtnLogin;
    private Button mBtnLogout;
    private Button mBtnAboutUs;

    private final int FIRST_REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTxtName = (TextView)getView().findViewById(R.id.user_name);
        mBtnLogin = (Button)getView().findViewById(R.id.login_account);
        mBtnLogout = (Button)getView().findViewById(R.id.logout_account);
        mBtnAboutUs = (Button)getView().findViewById(R.id.about_us);

        //设置几个按钮的onclick事件
        initMineButtons();
        //设置登录状态
        setLoginStatus();
        //初始化登录状态
        initLogin();
    }

    @Override
    public void onResume() {
        super.onResume();
        setLoginStatus();
        fresh();
    }

    private void setLoginStatus()
    {
        if (User.getUser().getId() == null) {
            mBtnLogin.setVisibility(View.VISIBLE);
            mBtnLogout.setVisibility(View.GONE);
        } else {
            mBtnLogin.setVisibility(View.GONE);
            mBtnLogout.setVisibility(View.VISIBLE);
        }

        if (User.getUser().getName() != null) {
            mTxtName.setText(null);
            mTxtName.append(User.getUser().getName() + "\n");
//            mTxtName.append(User.getUser().getId()+"\n");
//            mTxtName.append(User.getUser().getLoginAccount()+"\n");
//            mTxtName.append(User.getUser().getLoginFrom()+"\n");
        }
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
    private void initMineButtons() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewLoginPage();
            }
        });
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        mBtnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutUs();
            }
        });
    }
    //打开AboutUs WebSite
    private void aboutUs(){
        Toast.makeText(getContext(), R.string.txt_about_us_cotent, Toast.LENGTH_SHORT).show();
        //添加打点上报代码
        ActionInfo mActionInfo = new ActionInfo(ReportConstant.REPORT_MY_ABOUTUS);
        mReport.saveOnClick(getContext(), mActionInfo);
        // TODO: 1/8/16 完成官方网站的web端，并打开官方网站。
    }
    //登出
    private void logout() {
        User.getUser().setName(null);
        User.getUser().setId(null);
        User.getUser().setLoginAccount(null);
        User.getUser().setLoginAccount(null);
        mTxtName.setText(null);
        mBtnLogin.setVisibility(View.VISIBLE);
        mBtnLogout.setVisibility(View.GONE);

        //添加打点上报代码
        ActionInfo mActionInfo = new ActionInfo(ReportConstant.REPORT_MY_LOGOUT);
        mReport.saveOnClick(getContext(), mActionInfo);
    }

    //打开登录页面，包含QQ登录，微信登录，微博登录
    private void startNewLoginPage() {
        Intent intent = new Intent(getActivity(), NewLoginActivity.class);
        intent.setAction("Login");
        startActivityForResult(intent, FIRST_REQUEST_CODE);

        //添加打点上报代码
        ActionInfo mActionInfo = new ActionInfo(ReportConstant.REPORT_MY_LOGIN);
        mReport.saveOnClick(getContext(), mActionInfo);
    }
    //处理NewLoginActivity的页面返回值
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            //// TODO: 3/4/16
        }
    }



//    //添加登录QQ的代码
//    public void loginQQ(View view) {
//        if (view.getId() == R.id.login_account) {
//            SHARE_MEDIA platform = SHARE_MEDIA.QQ;
//            /**begin invoke umeng api**/
//            try {
//                mShareAPI.doOauthVerify(getActivity(), platform, umAuthListener);
//                //添加打点上报代码
//                ActionInfo mActionInfo = new ActionInfo(ReportConstant.REPORT_MENU_LOGIN);
//                mReport.saveOnClick(getContext(), mActionInfo);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * auth callback interface
//     **/
//    private UMAuthListener umAuthListener = new UMAuthListener() {
//        @Override
//        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            if (data != null && data.get("openid") != null && data.get("screen_name") == null) {
//                mShareAPI.getPlatformInfo(getActivity(), platform, umAuthListener);
//            } else if (data != null && data.get("openid") != null && data.get("screen_name") != null) {
//                String openId = data.get("openid");
//                String name = data.get("screen_name");
//                String imageUrl = data.get("profile_image_url");
//                new LoginTask().execute(new String[]{GeneralConstant.QQ, openId, name, imageUrl});
//            }
//
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            Toast.makeText(getContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform, int action) {
//            Toast.makeText(getContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
//        }
//    };
//
    //    @Override
    //    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //        mShareAPI.onActivityResult(requestCode, resultCode, data);
    //    }
//
//    //登录
//    class LoginTask extends AsyncTask<String, Integer, Boolean> {
//        @Override
//        protected void onPreExecute() {
//            mProgressDialog = DialogUtil.showWaitDialog(getActivity(), getString(R.string.please_wait));
//        }
//
//        @Override
//        protected Boolean doInBackground(String... params) {
//            String loginFrom = params[0];
//            String loginAccount = params[1];
//            String name = params[2];
//            try {
//                UserService userService = new UserService();
//                userService.login3rdAcountExist(loginFrom, loginAccount, name, getActivity());
//                return true;
//            } catch (Exception e) {
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            if (aBoolean) {
//                mTxtName.setText(User.getUser().getName());
//                mBtnLogin.setVisibility(View.GONE);
//                mBtnLogout.setVisibility(View.VISIBLE);
//                if (!SyncUtil.isSync) {
//                    new SyncTask().execute();
//                }
//                mProgressDialog.dismiss();
//            } else {
//                mProgressDialog.dismiss();
//                Toast.makeText(getActivity(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    class SyncTask extends AsyncTask<String, Integer, Boolean> {
//        @Override
//        protected void onPreExecute() {
//            SyncUtil.isSync = true;
//        }
//
//        @Override
//        protected Boolean doInBackground(String... params) {
//            TaskService taskService = new TaskService();
//            try {
//                taskService.syncTask();
//                return true;
//            } catch (Exception e) {
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            SyncUtil.isSync = false;
//            mProgressDialog.dismiss();
//        }
//    }


}
