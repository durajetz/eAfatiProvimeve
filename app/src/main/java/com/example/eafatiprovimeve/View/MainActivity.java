package com.example.eafatiprovimeve.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eafatiprovimeve.Adapters.AfatiProvimeveAdapter;
import com.example.eafatiprovimeve.Model.AfatiProvimeveModel;
import com.example.eafatiprovimeve.R;
import com.example.eafatiprovimeve.Utils.RecyclerViewSwipeDecorator;
import com.example.eafatiprovimeve.ViewModel.AfatiProvimeve;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private AfatiProvimeveAdapter adapter;
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private TextView emptyView;

    public static final String LOCATION_EVENT = "Fakulteti i Inxhinieris?? Elektrike dhe Kompjuterike,Universiteti i Prishtin??s";
    public static final int MY_PERMISSIONS_REQUEST_CALENDAR = 99;
    public static final int ADD_PROVIMI_REQUEST = 1;
    public static final int EDIT_PROVIMI_REQUEST = 2;
    private AfatiProvimeveModel afatiProvimeveModel;
    private Map<String, Integer> weekDays = new HashMap<String, Integer>() {{
        put("Monday", 1);
        put("Tuesday", 2);
        put("Wednesday", 3);
        put("Thursday", 4);
        put("Friday", 5);
    }};
    private Map<String, Integer> Days = new HashMap<String, Integer>() {{
        put("First", 1);
        put("Second", 2);
        put("Third", 3);
        put("Fourth", 4);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
//        }

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

        FloatingActionButton buttonAddProvimi = findViewById(R.id.button_add_provimi);
        buttonAddProvimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivityForResult(intent, ADD_PROVIMI_REQUEST);
            }
        });

        emptyView = findViewById(R.id.empty_view);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new AfatiProvimeveAdapter(this);

        recyclerView.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            checkLocationPermission();
        }

        afatiProvimeveModel = new ViewModelProvider(this).get(AfatiProvimeveModel.class);
        afatiProvimeveModel.getAllProvimet().observe(this, new Observer<List<AfatiProvimeve>>() {
            @Override
            public void onChanged(List<AfatiProvimeve> afatiProvimeves) {
                // update RecyclerView
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
            }
        });

        adapter.setOnItemClickListener(new AfatiProvimeveAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AfatiProvimeve afatiProvimeve) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);

                intent.putExtra(AddEditActivity.EXTRA_ID, afatiProvimeve.getId());
                intent.putExtra(AddEditActivity.EXTRA_NAME, afatiProvimeve.getName());
//                intent.putExtra(AddEditActivity.EXTRA_DITA, afatiProvimeve.getDita());
                intent.putExtra(AddEditActivity.EXTRA_VITI, afatiProvimeve.getViti());
                intent.putExtra(AddEditActivity.EXTRA_SEMESTRI, afatiProvimeve.getSemestri());
                intent.putExtra(AddEditActivity.EXTRA_DIFERENCA, afatiProvimeve.getDiferenca());
                intent.putExtra(AddEditActivity.EXTRA_SALLA, afatiProvimeve.getSalla());
                intent.putExtra(AddEditActivity.EXTRA_DAY, afatiProvimeve.getDita().split("#")[0]);
                intent.putExtra(AddEditActivity.EXTRA_WEEKDAY, afatiProvimeve.getDita().split("#")[1]);

                startActivityForResult(intent, EDIT_PROVIMI_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        AfatiProvimeve deletedProvim = adapter.getProvimiAt(position);
                        afatiProvimeveModel.delete(deletedProvim);
//                        Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
                        Snackbar.make(recyclerView, deletedProvim.getName() + " got deleted.", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                afatiProvimeveModel.insert(deletedProvim);
                            }
                        }).show();
                        break;
                    case ItemTouchHelper.RIGHT:
                        TextView datagjeneruar = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.text_view_datagjeneruar);
                        Toast.makeText(MainActivity.this, "Creating event for date: " + datagjeneruar.getText().toString(), Toast.LENGTH_SHORT).show();

                        Pattern p = Pattern.compile("@?(gmail|googlemail).com$");
                        Matcher m = p.matcher(email.split("@")[1]);
                        if (!m.matches()) {
                            addEvent(getApplicationContext(), adapter.getProvimiAt(position).getName(), datagjeneruar.getText().toString(), adapter.getProvimiAt(position).getDita());
                            afatiProvimeveModel.delete(adapter.getProvimiAt(position));
                            afatiProvimeveModel.insert(adapter.getProvimiAt(position));
//                            Toast.makeText(MainActivity.this, "email", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_INSERT);
                            intent.setData(CalendarContract.Events.CONTENT_URI);
                            intent.putExtra(CalendarContract.Events.TITLE, adapter.getProvimiAt(position).getName());
                            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, LOCATION_EVENT);

                            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, getMilliSeconds(datagjeneruar.getText().toString()) + 9000 * 60 * 60);
                            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, getMilliSeconds(datagjeneruar.getText().toString()) + 9000 * 60 * 60 + (2000 * 60 * 60));//add required delay

//                    intent.putExtra(Intent.EXTRA_EMAIL, "eti1375@gmail.com,jbtf2015@gmail.com"); //add invitations
                            intent.putExtra(CalendarContract.Reminders.MINUTES, 30);
                            intent.putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);

                            try {
                                afatiProvimeveModel.delete(adapter.getProvimiAt(position));
                                afatiProvimeveModel.insert(adapter.getProvimiAt(position));
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "There is no app that can support this action", Toast.LENGTH_SHORT).show();
                            }
//                            Toast.makeText(MainActivity.this, "gmail", Toast.LENGTH_SHORT).show();
                            break;
                        }
                }
            }

            @Override
            public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.orange))
                        .addSwipeLeftLabel("Remove")
                        .addSwipeLeftActionIcon(R.drawable.ic_twotone_delete)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cyan))
                        .addSwipeRightActionIcon(R.drawable.ic_event)
                        .addSwipeRightLabel("Add Event")
                        .setSwipeRightLabelTypeface(Typeface.MONOSPACE)
                        .setSwipeRightLabelColor(Color.WHITE)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PROVIMI_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditActivity.EXTRA_NAME);
            String salla = data.getStringExtra(AddEditActivity.EXTRA_SALLA);
            String semestri = data.getStringExtra(AddEditActivity.EXTRA_SEMESTRI);
            String viti = data.getStringExtra(AddEditActivity.EXTRA_VITI);
            String day = data.getStringExtra(AddEditActivity.EXTRA_DAY);
            String weekDay = data.getStringExtra(AddEditActivity.EXTRA_WEEKDAY);
//            int diferenca = data.getIntExtra(AddEditActivity.EXTRA_DIFERENCA, 1);
            String dita = day + "#" + weekDay;

            int dayExtracted = weekDays.get(day.split(" ")[1]);
            int weekDayExtracted = Days.get(weekDay.split(" ")[1]);
            int diferenca = (7 * (weekDayExtracted - 1) + (dayExtracted - 1));
            AfatiProvimeve afatiProvimeve = new AfatiProvimeve(name, viti, semestri, dita, diferenca, salla);
            afatiProvimeveModel.insert(afatiProvimeve);
            Toast.makeText(this, "Provimi saved.", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_PROVIMI_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Provimi can't be updated.", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddEditActivity.EXTRA_NAME);
            String salla = data.getStringExtra(AddEditActivity.EXTRA_SALLA);
            String semestri = data.getStringExtra(AddEditActivity.EXTRA_SEMESTRI);
            String viti = data.getStringExtra(AddEditActivity.EXTRA_VITI);
            String day = data.getStringExtra(AddEditActivity.EXTRA_DAY);
            String weekDay = data.getStringExtra(AddEditActivity.EXTRA_WEEKDAY);
//            int diferenca = data.getIntExtra(AddEditActivity.EXTRA_DIFERENCA, 1);
            String dita = day + "#" + weekDay;

            int dayExtracted = weekDays.get(day.split(" ")[1]);
            int weekDayExtracted = Days.get(weekDay.split(" ")[1]);
            int diferenca = (7 * (weekDayExtracted - 1) + (dayExtracted - 1));

            AfatiProvimeve afatiProvimeve = new AfatiProvimeve(name, viti, semestri, dita, diferenca, salla);
            afatiProvimeve.setId(id);
            afatiProvimeveModel.update(afatiProvimeve);
            Toast.makeText(this, "Provimi updated.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Provimi not saved.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filter_by_year_menu, menu);
        menuInflater.inflate(R.menu.filter_by_semester_menu, menu);
        menuInflater.inflate(R.menu.sort_by_menu, menu);
        menuInflater.inflate(R.menu.show_all_menu, menu);
        menuInflater.inflate(R.menu.delete_all_menu, menu);
        menuInflater.inflate(R.menu.googlemap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        List<AfatiProvimeve> afatiProvimeves;
        switch (item.getItemId()) {
            case R.id.sort_by_name:
                afatiProvimeveModel.getAllProvimetSortedByName().observe(this, new Observer<List<AfatiProvimeve>>() {
                    @Override
                    public void onChanged(List<AfatiProvimeve> afatiProvimeves) {
                        // update RecyclerView
                        checkForData(afatiProvimeves);
                        adapter.setProvimet(afatiProvimeves);
                    }
                });
                return true;
            case R.id.sort_by_date:
//                afatiProvimeves = afatiProvimeveModel.getProvimetSorted("date");
//                adapter.setProvimet(afatiProvimeves);
            case R.id.show_all_provimet:
                afatiProvimeves = afatiProvimeveModel.getAllProvimet().getValue();
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
                Toast.makeText(this, "All exams", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete_all_provimet:
                afatiProvimeveModel.deleteAllProvimet();
                Toast.makeText(this, "All exams deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.year_one:
                afatiProvimeves = afatiProvimeveModel.getProvimetByYear("Year: I");
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
                Toast.makeText(this, "Exams year one filtered", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.year_two:
                afatiProvimeves = afatiProvimeveModel.getProvimetByYear("Year: II");
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
                Toast.makeText(this, "Exams year two filtered", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.year_three:
                afatiProvimeves = afatiProvimeveModel.getProvimetByYear("Year: III");
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
                Toast.makeText(this, "Exams year three filtered", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.semester_one:
                afatiProvimeves = afatiProvimeveModel.getProvimetBySemster("Semester: I");
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
                Toast.makeText(this, "Exams semster one filtered", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.semester_two:
                afatiProvimeves = afatiProvimeveModel.getProvimetBySemster("Semester: II");
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
                Toast.makeText(this, "Exams semster two filtered", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.semester_three:
                afatiProvimeves = afatiProvimeveModel.getProvimetBySemster("Semester: III");
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
                Toast.makeText(this, "Exams semster three filtered", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.semester_four:
                afatiProvimeves = afatiProvimeveModel.getProvimetBySemster("Semester: IV");
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
                Toast.makeText(this, "Exams semster four filtered", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.semester_five:
                afatiProvimeves = afatiProvimeveModel.getProvimetBySemster("Semester: V");
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
                Toast.makeText(this, "Exams semster five filtered", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.semester_six:
                afatiProvimeves = afatiProvimeveModel.getProvimetBySemster("Semester: VI");
                checkForData(afatiProvimeves);
                adapter.setProvimet(afatiProvimeves);
                Toast.makeText(this, "Exams semster six filtered", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.location:
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_CALENDAR)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_CALENDAR},
                        MY_PERMISSIONS_REQUEST_CALENDAR);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_CALENDAR},
                        MY_PERMISSIONS_REQUEST_CALENDAR);
            }
            return false;
        } else {
            return true;
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALENDAR) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_CALENDAR)
                        == PackageManager.PERMISSION_GRANTED) {
//                        addEvent(getApplicationContext(), "Running for the challenge");
                }
                Toast.makeText(this, "permission allow", Toast.LENGTH_LONG).show();
            } else {

                // Permission denied, Disable the functionality that depends on this permission.
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }
            return;
        }
    }

    public void addEvent(Context ctx, String title, String startDate, String description) {

        ContentResolver contentResolver = ctx.getContentResolver();

        ContentValues calEvent = new ContentValues();
        calEvent.put(CalendarContract.Events.CALENDAR_ID, 1); // XXX pick)
        calEvent.put(CalendarContract.Events.TITLE, title);

        calEvent.put(CalendarContract.Events.DTSTART, getMilliSeconds(startDate) + 9000 * 60 * 60);
        calEvent.put(CalendarContract.Events.DTEND, getMilliSeconds(startDate) + 9000 * 60 * 60 + (2000 * 60 * 60));

        calEvent.put(CalendarContract.Events.EVENT_LOCATION, LOCATION_EVENT);
        calEvent.put(CalendarContract.Events.DESCRIPTION, description);
        calEvent.put(CalendarContract.Events.EVENT_TIMEZONE, "" + java.util.Locale.getDefault());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(ctx, "IF", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, calEvent);
        int id = Integer.parseInt(uri.getLastPathSegment());
        Toast.makeText(ctx, "Created Calendar Event " + id,
                Toast.LENGTH_SHORT).show();
        ContentValues reminders = new ContentValues();
        reminders.put(CalendarContract.Reminders.EVENT_ID, id);
        reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        reminders.put(CalendarContract.Reminders.MINUTES, 30);
        Uri uri1 = contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, reminders);
    }

    public long getMilliSeconds(String date) {
        Date dateSample = null;
        try {
            SimpleDateFormat formatter;
            formatter = new SimpleDateFormat("MM-dd-yyyy");
            dateSample = (Date) formatter.parse(date);
        } catch (Exception ignored) {
        }

        return dateSample.getTime();
    }

    private void checkForData(List<AfatiProvimeve> provimeveList) {
        if (provimeveList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
//            Snackbar.make(MainActivity.this, provimeveList.size() + " exams were found.", Snackbar.LENGTH_LONG);
        }
    }
}