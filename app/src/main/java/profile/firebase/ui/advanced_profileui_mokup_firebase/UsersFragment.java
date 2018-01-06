package profile.firebase.ui.advanced_profileui_mokup_firebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mauro on 06/01/2018.
 */

public class UsersFragment extends Fragment {

  public UsersFragment() {
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_users,container,false);
  }
}
