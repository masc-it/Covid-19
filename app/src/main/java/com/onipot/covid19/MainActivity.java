package com.onipot.covid19;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onipot.covid19.fragment.NazioneFragment;
import com.onipot.covid19.fragment.RegioneFragment;

public class MainActivity extends AppCompatActivity {


    final Fragment nazioneFragment = new NazioneFragment();
    final Fragment regioneFragment =  new RegioneFragment();

    public final FragmentManager fm = getSupportFragmentManager();

    private Fragment active = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {


        switch (item.getItemId()) {
            case R.id.nav_nazionale:

                if ( !(active instanceof NazioneFragment) ) {

                    active = new NazioneFragment();
                    fm.beginTransaction().replace(R.id.main_container, active).commit();

                }
                return true;
            case R.id.nav_regione:
                if ( !(active instanceof RegioneFragment) ) {
                    active = new RegioneFragment();

                    fm.beginTransaction().replace(R.id.main_container, active).commit();
                }
                return true;
        }
        return false;
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        active = new NazioneFragment();
        fm.beginTransaction().replace(R.id.main_container, active, "").commit();
        /*new FetchData("https://github.com/pcm-dpc/COVID-19/raw/master/dati-andamento-nazionale/dpc-covid19-ita-andamento-nazionale.csv",
                csv -> {



                }

                ).execute();*/

    }

}
