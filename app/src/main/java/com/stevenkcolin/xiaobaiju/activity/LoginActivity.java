package com.stevenkcolin.xiaobaiju.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private TextView textRegister;
    private EditText editPhone;
    private EditText editPassword;
    private Button btnLogin;
    private ProgressDialog barProgressDialog;

    private final int REGISTER_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editPhone = (EditText)findViewById(R.id.phone);
        editPassword = (EditText)findViewById(R.id.password);

        textRegister = (TextView)findViewById(R.id.register_button);
        textRegister.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editPhone.getText().toString();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("phone", phone);
                startActivityForResult(intent, REGISTER_CODE);
            }
        });

        btnLogin = (Button)findViewById(R.id.sign_in_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    return;
                }
                barProgressDialog = DialogUtil.showWaitDialog(LoginActivity.this, getString(R.string.please_wait));
                new RegisterTask().execute(editPhone.getText().toString(), editPassword.getText().toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REGISTER_CODE:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(LoginActivity.this, TaskListActivity.class);
                    startActivity(intent);
                }
                break;
            default:
        }
    }

    private boolean getAccount() {
        List<String> accountList = FileUtil.read(LoginActivity.this, GeneralConstant.FILE_NAME_ACCOUNT);
        Log.e("LoginActivity", "account:" + accountList);
        if (accountList.size() > 1) {
            String phone = accountList.get(0);
            String password = accountList.get(1);
            Log.e("LoginActivity", "phone:" + phone);
            Log.e("LoginActivity", "password:" + password);
        }
        return true;
    }

    private boolean validate() {
        boolean result = true;
        if (TextUtils.isEmpty(editPhone.getText())) {
            editPhone.setError(getString(R.string.error_empty_phone));
            result = false;
        }
        if (TextUtils.isEmpty(editPassword.getText())) {
            editPassword.setError(getString(R.string.error_empty_password));
            result = false;
        }
        return result;
    }

    class RegisterTask extends AsyncTask<String, Integer, Boolean> {
        private String message = getString(R.string.info_register_success);
        private String phone;
        private String password;
        @Override
        protected Boolean doInBackground(String... params) {
            phone = params[0];
            password = params[1];
            Map<String, String> map = new HashMap<>();
            map.put("phone", phone);
            map.put("password", password);
            try {
                JSONArray jsonArray = (JSONArray) HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.USER_URI + "/" + GeneralConstant.USER_LOGIN_URI, "POST", JSONUtil.stringifySingle(map));
                if (jsonArray.length() == 0) {
                    message = getString(R.string.error_invalid_account);
                    return false;
                }
                JSONObject jsonObject = (JSONObject)jsonArray.get(0);

                User user = User.getUser();
                user.setId(jsonObject.getString("_id"));
                user.setPhone(jsonObject.getString("phone"));

                FileUtil.write(LoginActivity.this, GeneralConstant.FILE_NAME_ACCOUNT, phone + "\r\n" + MD5Util.GetMD5Code(password));
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
            barProgressDialog.dismiss();
            if (result) {
                Intent intent = new Intent(LoginActivity.this, TaskListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

