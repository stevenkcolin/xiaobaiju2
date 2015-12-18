package com.stevenkcolin.xiaobaiju.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.exception.ServerException;
import com.stevenkcolin.xiaobaiju.util.DialogUtil;
import com.stevenkcolin.xiaobaiju.util.FileUtil;
import com.stevenkcolin.xiaobaiju.util.HttpUtil;
import com.stevenkcolin.xiaobaiju.util.JSONUtil;
import com.stevenkcolin.xiaobaiju.util.MD5Util;
import com.stevenkcolin.xiaobaiju.vo.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pengfei on 2015/12/9.
 */
public class RegisterActivity extends BaseActivity {

    private EditText editPhone;
    private EditText editPassword;
    private EditText editConfirmPwd;
    private Button btnRegister;
    private ProgressDialog barProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editPhone = (EditText)findViewById(R.id.phone);
        editPassword = (EditText)findViewById(R.id.password);
        editConfirmPwd = (EditText)findViewById(R.id.confirmPwd);
        btnRegister = (Button)findViewById(R.id.register_button);

        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        editPhone.setText(phone);
        editPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!TextUtils.isEmpty(editPhone.getText())) {
                        String phone = editPhone.getText().toString();
                        new CheckPhoneTask().execute(phone);
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    return;
                }
                barProgressDialog = DialogUtil.showWaitDialog(RegisterActivity.this, getString(R.string.please_wait));

                String phone = editPhone.getText().toString();
                String password = editPassword.getText().toString();
                new RegisterTask().execute(phone, password);
            }
        });
    }

    private boolean validate(){
        boolean result = true;
        if (TextUtils.isEmpty(editPhone.getText())) {
            editPhone.setError(this.getString(R.string.error_empty_phone));
            result = false;
        }

        if (TextUtils.isEmpty(editPassword.getText())) {
            editPassword.setError(this.getString(R.string.error_empty_password));
            result = false;
        }

        if (TextUtils.isEmpty(editConfirmPwd.getText())) {
            editConfirmPwd.setError(this.getString(R.string.error_empty_confirmPwd));
            result = false;
        }

        if (!editPassword.getText().toString().equals(editConfirmPwd.getText().toString())) {
            editConfirmPwd.setError(this.getString(R.string.error_notequal_pwd));
            result = false;
        }

        return result;
    }

    class CheckPhoneTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("phone", params[0]);
            JSONUtil.stringifySingle(map);
            try {
                JSONArray jsonArray = (JSONArray) HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.USER_URI + "/" + GeneralConstant.USER_FIND_URI, "POST", JSONUtil.stringifySingle(map));
                if (jsonArray.length() == 0) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                // TODO: 2015/12/10 deal with server exception
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                editPhone.setError(getString(R.string.error_duplicated_phone));
            }
        }
    }

    class RegisterTask extends AsyncTask<String, Integer, Boolean> {
        private String message = getString(R.string.info_register_success);
        private String phone;
        private String password;
        @Override
        protected Boolean doInBackground(String... params) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("phone", params[0]);
            JSONUtil.stringifySingle(map);
            try {
                JSONArray jsonArray = (JSONArray) HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.USER_URI + "/" + GeneralConstant.USER_FIND_URI, "POST", JSONUtil.stringifySingle(map));
                if (jsonArray.length() > 0) {
                    message = getString(R.string.error_duplicated_phone);
                    return false;
                }
            } catch (ServerException se) {
                message = getString(R.string.error_server);
                return false;
            } catch (Exception e) {
                message = getString(R.string.error_network);
                return false;
            }

            map = new HashMap<String, String>();
            map.put("phone", params[0]);
            map.put("password", params[1]);
            phone = params[0];
            password = params[1];
            try {
                JSONObject jsonObject = (JSONObject)HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.USER_URI + "/" + GeneralConstant.USER_CREATE_URI, "POST", JSONUtil.stringifySingle(map));
                FileUtil.write(RegisterActivity.this, GeneralConstant.FILE_NAME_ACCOUNT, phone + "\r\n" + MD5Util.GetMD5Code(password));
                User user = User.getUser();
                user.setId(jsonObject.getString("_id"));
                user.setPhone(jsonObject.getString("phone"));
                return true;
            } catch (ServerException se) {
                message = getString(R.string.error_server);
                return false;
            } catch (Exception e) {
                message = getString(R.string.error_network);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            barProgressDialog.dismiss();
            if (result) {
                Intent intent = new Intent();
                setResult(RegisterActivity.RESULT_OK, intent);
                RegisterActivity.this.finish();
            }
        }
    }

}
