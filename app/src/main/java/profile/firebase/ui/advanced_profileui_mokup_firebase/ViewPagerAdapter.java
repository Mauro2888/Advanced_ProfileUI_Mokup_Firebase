package profile.firebase.ui.advanced_profileui_mokup_firebase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Mauro on 06/01/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

  ViewPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    switch (position){
      case 0:
        return new ProfileFragment();
      case 1:
        return new UsersFragment();
    }


    return null;
  }

  @Override public int getCount() {
    return 2;
  }

}
