package profile.firebase.ui.advanced_profileui_mokup_firebase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mauro on 06/01/2018.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener{


  private FirebaseAuth mAuth;
  private Button mLogout;
  private FirebaseFirestore mFireStore;
  private String mUser;
  private CircleImageView mImageProfile;
  private TextView mUsername;

  public ProfileFragment() {
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
    
    
    final View view = inflater.inflate(R.layout.fragment_profile,container,false);
    mAuth = FirebaseAuth.getInstance();
    mFireStore = FirebaseFirestore.getInstance();
    mLogout = view.findViewById(R.id.btn_logout);
    mImageProfile = view.findViewById(R.id.profile_image_activity);
    mUsername = view.findViewById(R.id.profile_text_name);
    mLogout.setOnClickListener(this);

    //get Current User
    mUser = mAuth.getCurrentUser().getUid();

    mFireStore.collection("Users").document(mUser).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
      @SuppressLint("CheckResult") @Override public void onSuccess(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot != null){
          String nameUser = documentSnapshot.getString("name");
          String userImage = documentSnapshot.getString("image");

          //setDefaultImage
          RequestOptions defaultProfile = new RequestOptions();
          defaultProfile.placeholder(R.drawable.ic_empty_image_user);
          Glide.with(container.getContext()).applyDefaultRequestOptions(defaultProfile).load(userImage).into(mImageProfile);
          mUsername.setText(nameUser);
        }
      }
    });
    
    return view;
  }

  @Override public void onClick(View view) {
    if (view == mLogout){
      mAuth.signOut();
      Intent openlogin = new Intent(view.getContext(),LoginActivity.class);
      openlogin.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
      startActivity(openlogin);
      getActivity().finish();
      Toast.makeText(view.getContext(), "Logout", Toast.LENGTH_SHORT).show();
    }
  }

}
