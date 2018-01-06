package profile.firebase.ui.advanced_profileui_mokup_firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

  private Button mBtnRegister;
  private EditText mEmail;
  private EditText mPassword;
  private Button mBtnLogin;
  private FirebaseAuth mAuth;
  private ProgressBar mProgressLogin;

  //Check if user is logged or not
  @Override protected void onStart() {
    super.onStart();
    FirebaseUser user = mAuth.getCurrentUser();
    if (user != null){
      openMainActivity();
    }
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mAuth = FirebaseAuth.getInstance();
    mBtnRegister = findViewById(R.id.new_account_btn);
    mEmail = findViewById(R.id.login_editText_email);
    mPassword = findViewById(R.id.login_editText_password);
    mBtnLogin =findViewById(R.id.login_btn);
    mProgressLogin = findViewById(R.id.progress_login);
    mBtnRegister.setOnClickListener(this);
    mBtnLogin.setOnClickListener(this);

  }

  @Override public void onClick(View view) {
    if (view == mBtnRegister){
      Intent registerActivity = new Intent(LoginActivity.this,RegisterActivity.class);
      startActivity(registerActivity);
    }
    if (view == mBtnLogin){
      String email = mEmail.getText().toString();
      String password = mPassword.getText().toString();


      if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
        mProgressLogin.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
              openMainActivity();
            }else {
              mProgressLogin.setVisibility(View.GONE);
              Toast.makeText(LoginActivity.this, "Errore " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });

      }else {
        Toast.makeText(this, "Please insert Email and Password", Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void openMainActivity() {
    Intent openMain = new Intent(LoginActivity.this,MainActivity.class);
    startActivity(openMain);
    finish();
  }
}
