package fingertiptech.medontime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
//    https://medontime.herokuapp.com/
//    https://medontime-api.herokuapp.com/api/caretakerapi?key=sH5O!2cdOqP1%5E
//    https://medontime-api.herokuapp.com/API/MedicationAPI?key=sH5O!2cdOqP1%5E
//    https://medontime-api.herokuapp.com/API/PatientAPI?key=sH5O!2cdOqP1%5E
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_medicine, R.id.nav_caretaker,
                R.id.nav_scanQR,
                R.id.nav_login,
                R.id.nav_testmockapi,
                R.id.nav_testmockapiquery,
                R.id.nav_testmockapipost,
                R.id.nav_testmockapiputpatch,
                R.id.nav_testmockapidelete)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        // erase Login account info sharedpreference first, since we keep use the same simulator for testing
        SharedPreferences sharedPreferencesLoginUserInformation = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesLoginUserInformationEditor = sharedPreferencesLoginUserInformation.edit();
        sharedPreferencesLoginUserInformationEditor.putString("PatientLogInInfo", "");
        sharedPreferencesLoginUserInformationEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    public void displayToast(CharSequence text){
//        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
//        toast.show();
//    }
}