package profile.firebase.ui.advanced_profileui_mokup_firebase;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
  private CircleImageView mImageProfile;
  private Uri mImageUriProfile;
  private Button mBtnBackLoginActivity;
  private FirebaseAuth mAuth;
  private StorageReference mStorage;
  private FirebaseFirestore mFirestore;
  private Button mCreateAccount;
  private EditText mName;
  private EditText mEmail;
  private EditText mPassword;
  private ProgressBar mProgressRegister;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mAuth = FirebaseAuth.getInstance();
    mStorage = FirebaseStorage.getInstance().getReference().child("image_profile");
    mFirestore = FirebaseFirestore.getInstance();

    setContentView(R.layout.activity_register);
    mImageProfile = findViewById(R.id.profileImage);
    mBtnBackLoginActivity = findViewById(R.id.btn_back_login);
    mCreateAccount = findViewById(R.id.btn_create_account);

    mName = findViewById(R.id.register_editText_name);
    mEmail = findViewById(R.id.register_editText_email);
    mPassword = findViewById(R.id.register_editText_password);

    mProgressRegister = findViewById(R.id.progressRegister);
    mCreateAccount.setOnClickListener(this);
    mImageProfile.setOnClickListener(this);
    mBtnBackLoginActivity.setOnClickListener(this);
  }

  @Override public void onClick(View view) {
    if (view == mImageProfile){
      Intent openDialog = new Intent(Intent.ACTION_GET_CONTENT);
      openDialog.setType("image/*");
      startActivityForResult(Intent.createChooser(openDialog,"Select image Profile"),StaticValues.PICK_IMAGE_PROFILE);
    }
    if (view == mBtnBackLoginActivity){
      Intent backLogin = new Intent(RegisterActivity.this,LoginActivity.class);
      startActivity(backLogin);
      finish();
    }
    if (view == mCreateAccount){

      if (mImageUriProfile != null){
        mProgressRegister.setVisibility(View.VISIBLE);

      final String name = mName.getText().toString();
      String email = mEmail.getText().toString();
      String password = mPassword.getText().toString();

      if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

          @Override public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){

              //Get User id
              final String user_id = mAuth.getCurrentUser().getUid();


              //put user id to storage
              final StorageReference userProfile = mStorage.child(user_id + ".jpg");
              userProfile.putFile(mImageUriProfile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                @Override public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                  if (task.isSuccessful()){

                    //get firestore and put collection data uri image + name
                    String download_Url = task.getResult().getDownloadUrl().toString();
                    Map<String,Object> collecitonData = new HashMap<>();
                    collecitonData.put("name",name);
                    collecitonData.put("image",download_Url);

                    Log.d("TAG_ID"," user id " + user_id +" user profile " + userProfile + " download url " + download_Url);

                    //put to firestore
                    mFirestore.collection("Users").document(user_id).set(collecitonData).addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                          @Override public void onSuccess(Void aVoid) {
                            openMainActivity();
                          }
                        });
                  }else {
                    mProgressRegister.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                  }

                }
              });

            }
          }
        });
      }else {
        mProgressRegister.setVisibility(View.GONE);
      }
    }else {
        mProgressRegister.setVisibility(View.GONE);
        Toast.makeText(this, "Please select image profile", Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void openMainActivity() {
    Intent openMain = new Intent(RegisterActivity.this,MainActivity.class);
    startActivity(openMain);
    finish();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == StaticValues.PICK_IMAGE_PROFILE && resultCode == RESULT_OK){
     mImageUriProfile = data.getData();
     mImageProfile.setImageURI(mImageUriProfile);

    }
    super.onActivityResult(requestCode, resultCode, data);
  }


  public String getUriPath(Uri uri){
    Cursor cursor = null;
    try{
      String [] projector = { MediaStore.Images.Media.DATA};
      cursor = getContentResolver().query(uri,projector,null,null,null,null);
      int dataImage = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      cursor.moveToFirst();
      String image = cursor.getColumnName(dataImage);

        return image;

    }finally {
      if (cursor != null){
        cursor.close();
      }
    }


  }

}
