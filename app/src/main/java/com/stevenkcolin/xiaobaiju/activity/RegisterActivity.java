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
import com.stevenkcolin.xiaobaiju.exception.ServerException;
import com.stevenkcolin.xiaobaiju.service.UserService;
import com.stevenkcolin.xiaobaiju.util.DialogUtil;

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
            String phone = params[0];
            try {
                UserService userService = new UserService();
                return userService.checkPhoneExist(phone);
            } catch (Exception e) {
                // do nothing
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
        protected void onPreExecute() {
            barProgressDialog = DialogUtil.showWaitDialog(RegisterActivity.this, getString(R.string.please_wait));
        }

        @Override
        protected Boolean doInBackground(String... params) {
            phone = params[0];
            password = params[1];
            try {
                UserService userService = new UserService();
                boolean isExist = userService.checkPhoneExist(phone);
                if (!isExist) {
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

            try {
                UserService userService = new UserService();
                boolean result = userService.register(phone, password, RegisterActivity.this);
                return result;
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
