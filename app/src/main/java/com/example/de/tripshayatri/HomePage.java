package com.example.de.tripshayatri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout mainFrame;
    HomeFragment homeFragment;
    ProfileFragment profileFragment;
    SearchFragment searchFragment;
    VisitedFragment visitedFragment;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);
        mainFrame=findViewById(R.id.mainframe);
        bottomNavigationView =findViewById(R.id.navigation);
        homeFragment=new HomeFragment();
        profileFragment=new ProfileFragment();
        searchFragment=new SearchFragment();
        visitedFragment=new VisitedFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.search:
                        setFragment(searchFragment);
                        return true;
                    case R.id.visited:
                        setFragment(visitedFragment);
                        return true;
                    case R.id.profile:
                        setFragment(profileFragment);
                        return true;
                        default:
                            return  false;
                }

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe,fragment);
        fragmentTransaction.commit();
    }
}
