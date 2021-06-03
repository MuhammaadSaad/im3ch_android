package com.adnan.tech.im3ch;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.ConstVar;
import com.adnan.tech.im3ch.Util.DialogClass;

public class SignUpActivity extends AppCompatActivity {

    Button btn_sign_up;
    TextView tv_type, tv_gender;
    EditText et_user_name, et_pwd, et_email, et_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        try {
            Init();
            OnClickListener();
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void Init() {
        try {
            new Anim().Entry(this);
            btn_sign_up = findViewById(R.id.btn_sign_up);

            tv_type = findViewById(R.id.tv_type);
            tv_gender = findViewById(R.id.tv_gender);

            et_user_name = findViewById(R.id.et_user_name);
            et_pwd = findViewById(R.id.et_pwd);
            et_email = findViewById(R.id.et_email);
            et_number = findViewById(R.id.et_number);
            tv_type.setText(getIntent().getStringExtra(ConstVar.Type));

        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void OnClickListener() {
        try {
            tv_gender.setOnClickListener(v -> {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Select your gender");

                    String[] lst_gender = {"Male", "Female"};
                    builder.setItems(lst_gender, (dialog, which) -> tv_gender.setText(lst_gender[which]));

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } catch (Exception ex) {
                    new DialogClass(this, "Exception", ex.getMessage());
                }
            });
            btn_sign_up.setOnClickListener(v -> {
                try {
                    if (et_user_name.getText().toString().equals("") &&
                            et_pwd.getText().toString().equals("") &&
                            et_email.getText().toString().equals("") &&
                            et_number.getText().toString().equals("")) {
                        if (et_pwd.getText().toString().length() > 8) {
                            if (isValidEmail(et_email.getText().toString())) {

                            } else {
                                Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Password length should be greater than 8", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    new DialogClass(this, "Exception", ex.getMessage());
                }
            });
        } catch (
                Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }

    }

    public void funSignUp(){
        try{

        }catch (Exception ex){

        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }
}