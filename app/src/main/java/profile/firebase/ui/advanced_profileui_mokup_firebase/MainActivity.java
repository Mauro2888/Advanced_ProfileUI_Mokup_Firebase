package profile.firebase.ui.advanced_profileui_mokup_firebase;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private ViewPager mPagerView;
  private ViewPagerAdapter mAdapterViewPager;
  private TextView mProfile;
  private TextView mUsers;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mPagerView = findViewById(R.id.main_viewpager);
    mAdapterViewPager = new ViewPagerAdapter(getSupportFragmentManager());
    mPagerView.setAdapter(mAdapterViewPager);
    mProfile = findViewById(R.id.profile_tab);
    mUsers = findViewById(R.id.users_tab);
    mProfile.setOnClickListener(this);
    mUsers.setOnClickListener(this);


    mPagerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {
        changeTab(position);

      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });


  }

  private void changeTab(int position) {
   if (position == 0) {
     mProfile.setTextColor(getResources().getColor(R.color.colorWhite));
     mProfile.setTextSize(16);

     mUsers.setTextColor(getResources().getColor(R.color.colorText));
     mUsers.setTextSize(13);
   }

     if (position == 1){
       mUsers.setTextColor(getResources().getColor(R.color.colorWhite));
       mUsers.setTextSize(16);

       mProfile.setTextColor(getResources().getColor(R.color.colorText));
       mProfile.setTextSize(13);
   }
  }

  @Override public void onClick(View v) {
    if (v == mProfile){
      mPagerView.setCurrentItem(0);
    }
    if (v == mUsers){
      mPagerView.setCurrentItem(1);
    }
  }
}
