package com.example.eafatiprovimeve.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eafatiprovimeve.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CalendarPickerActivity extends AppCompatActivity {
    public static final String DATE_SHARED_PREFERENCE = "generatedDate";
    private FirebaseAuth mAuth;
    private TextView welcometxt,myDate;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_picker);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
//        }

        CalendarView calendarView = findViewById(R.id.calendarView);
        mAuth = FirebaseAuth.getInstance();
        welcometxt = findViewById(R.id.welcomeText);
        myDate = findViewById(R.id.myDate);
        btnOk = findViewById(R.id.chooseBtn);

        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

        welcometxt.setText("Pick term start date! " + email.split("@")[0]);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "-" + dayOfMonth + "-" + year;
                myDate.setText(date);
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = prefs.edit();


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = getString(R.string.myDate);
                if(myDate.getText().toString().equals(s)){
                    Toast.makeText(CalendarPickerActivity.this, "Please pick a date to continue!", Toast.LENGTH_SHORT).show();
                }else {
                editor.putString(DATE_SHARED_PREFERENCE, myDate.getText().toString());
                editor.apply();

                startActivity(new Intent(CalendarPickerActivity.this, MainActivity.class));
                }
//                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_user:
                alertsignout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void alertsignout()
    {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog2.setTitle("Confirm Logout");

        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want to Logout?");

        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(CalendarPickerActivity.this, LoginActivity.class));
                        finish();
                    }
                });

        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "You clicked NO", Toast.LENGTH_SHORT)
                                .show();
                        dialog.cancel();
                    }
                });

        // Showing Alert Dialog
        alertDialog2.show();
    }
}