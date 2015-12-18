package com.stevenkcolin.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.stevenkcolin.app.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView btnRegister;
    private EditText editPhone;
    private EditText editPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPhone = (EditText) this.findViewById(R.id.edit_login_phone);

        editPwd = (EditText) this.findViewById(R.id.edit_login_pwd);

        btnRegister = (TextView) this.findViewById(R.id.btn_login_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("wjh.android.action.otheractivity");
                startActivity(intent);
            }
        });
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

    @Override
    public void onClick(View v) {

    }

    /**
     * 授权。如果授权成功，则获取用户信息
     *
     * @param platform
     *//*
    private void login(final SHARE_MEDIA platform) {
        mController.doOauthVerify(this, platform,
                new SocializeListeners.UMAuthListener() {

                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        Toast aaa = Toast.makeText(MainActivity.this, "授权开始",
                                Toast.LENGTH_SHORT);
                        aaa.show();
                    }

                    @Override
                    public void onError(SocializeException e,
                                        SHARE_MEDIA platform) {
                        Toast.makeText(MainActivity.this, "授权失败",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        // 获取uid
                        String uid = value.getString("uid");
                        if (!TextUtils.isEmpty(uid)) {
                            // uid不为空，获取用户信息
                            getUserInfo(platform);
                        } else {
                            Toast.makeText(MainActivity.this, "授权失败...",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(MainActivity.this, "授权取消",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    *//**
     * 获取用户信息
     *
     * @param platform
     *//*
    private void getUserInfo(SHARE_MEDIA platform) {
        mController.getPlatformInfo(MainActivity.this, platform,
                new SocializeListeners.UMDataListener() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        // String showText = "";
                        // if (status == StatusCode.ST_CODE_SUCCESSED) {
                        // showText = "用户名：" +
                        // info.get("screen_name").toString();
                        // Log.d("#########", "##########" + info.toString());
                        // } else {
                        // showText = "获取用户信息失败";
                        // }

                        if (info != null) {
                            Toast.makeText(MainActivity.this, info.toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.e(getString(R.string.app_name), "onComplete " + info.toString());
                        }
                    }
                });
    }

    *//**
     * 注销本次登陆
     * @param platform
     *//*
    private void logout(final SHARE_MEDIA platform) {
        mController.deleteOauth(MainActivity.this, platform, new SocializeListeners.SocializeClientListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                String showText = "解除" + platform.toString() + "平台授权成功";
                if (status != StatusCode.ST_CODE_SUCCESSED) {
                    showText = "解除" + platform.toString() + "平台授权失败[" + status + "]";
                }
                Toast.makeText(MainActivity.this, showText, Toast.LENGTH_SHORT).show();
            }
        });
    }


    // 如果有使用任一平台的SSO授权, 则必须在对应的activity中实现onActivityResult方法, 并添加如下代码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 根据requestCode获取对应的SsoHandler
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                resultCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public void onClickGetAPI(View v){
        // TODO: 11/28/15
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.baidu.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is: "+ response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void getUserInfo (View v){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.stevenkcolin.com:3000/user";

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", response);
                        mTextView.setText("Response is: "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        queue.add(stringRequest);
        }


    public void postUserInfo (View v){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.stevenkcolin.com:3000/user/create";

        JSONObject js = new JSONObject();
        try {
//            JSONObject jsonobject_one = new JSONObject();
//
//            jsonobject_one.put("phone", "1234567890");
//            jsonobject_one.put("password", "123456");

            //js.put("data", jsonobject_one.toString());

            js.put("phone", "123456789333");
            js.put("password", "123456");

        }catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("TAG", response.toString());

                        //msgResponse.setText(response.toString());
                        //hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Error: " + error.getMessage());
                //hideProgressDialog();
            }
        }) {

            *//**
             * Passing some request headers
             * *//*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
    };

        queue.add(jsonObjReq);


    }

    private Response.Listener<String> responseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", response.toString());
                Log.e("TAG",response);
            }
        };
    }



    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(),error);
            }
        };
    }
*/
}