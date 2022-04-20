package com.example.eafatiprovimeve.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eafatiprovimeve.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEditActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.eafatiprovimeve.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.eafatiprovimeve.EXTRA_NAME";
    public static final String EXTRA_DIFERENCA = "com.example.eafatiprovimeve.EXTRA_DIFERENCA";
    public static final String EXTRA_VITI = "com.example.eafatiprovimeve.EXTRA_VITI";
    public static final String EXTRA_SEMESTRI = "com.example.eafatiprovimeve.EXTRA_SEMESTRI";
    public static final String EXTRA_DITA = "com.example.eafatiprovimeve.EXTRA_DITA";
    public static final String EXTRA_SALLA = "com.example.eafatiprovimeve.EXTRA_SALLA";

    private EditText editTextName, editTextDita;
    private ImageView logoAddEdit;
    private Spinner sallaSpinner, semesterSpinner, yearSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        editTextName = findViewById(R.id.edit_text_name);
        editTextDita = findViewById(R.id.edit_text_dita);
//        editTextDiferenca = findViewById(R.id.edit_text_difference);
        logoAddEdit = findViewById(R.id.logo_imageview);

        sallaSpinner = findViewById(R.id.salla_spinner);
        ArrayAdapter<CharSequence> salla_adapter = ArrayAdapter.createFromResource(this,
                R.array.salla_array, android.R.layout.simple_spinner_item);
        salla_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sallaSpinner.setAdapter(salla_adapter);

        semesterSpinner = findViewById(R.id.edit_text_semestri);
        ArrayAdapter<CharSequence> semester_adapter = ArrayAdapter.createFromResource(this,
                R.array.semester_array, android.R.layout.simple_spinner_item);
        semester_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semester_adapter);

        yearSpinner = findViewById(R.id.edit_text_viti);
        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(this,
                R.array.viti_array, android.R.layout.simple_spinner_item);
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(year_adapter);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            logoAddEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_twotone_edit));
            setTitle("Edit Provimi");
            sallaSpinner.setSelection(salla_adapter.getPosition(intent.getStringExtra(EXTRA_SALLA)));
            semesterSpinner.setSelection(semester_adapter.getPosition(intent.getStringExtra(EXTRA_SEMESTRI)));
            yearSpinner.setSelection(year_adapter.getPosition(intent.getStringExtra(EXTRA_VITI)));
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextDita.setText(intent.getStringExtra(EXTRA_DITA));
//            editTextDiferenca.setText(String.valueOf(intent.getIntExtra(EXTRA_DIFERENCA, 1)));
        } else {
            logoAddEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_circle));
            setTitle("Add Provimi");
        }

    }

    private void saveProvimi() {
        String salla = sallaSpinner.getSelectedItem().toString();
        String semestri = semesterSpinner.getSelectedItem().toString();
        String name = editTextName.getText().toString();
        String dita = editTextDita.getText().toString();
        String viti = yearSpinner.getSelectedItem().toString();
//        int diferenca = Integer.parseInt(editTextDiferenca.getText().toString());

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_DITA, dita);
//        data.putExtra(EXTRA_DIFERENCA, diferenca);
        data.putExtra(EXTRA_VITI, viti);
        data.putExtra(EXTRA_SEMESTRI, semestri);
        data.putExtra(EXTRA_SALLA, salla);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_provimi_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_provimi:
                if (editTextName.getText().toString().trim().isEmpty()) {
                    editTextName.setError("Name is required!");
                    editTextName.requestFocus();
                    return false;
                }

                Pattern p = Pattern.compile("^(H|M|E|P|MK)[1-4]{1}");
                Matcher m = p.matcher(editTextDita.getText().toString().trim());
                if (!m.matches()) {
                    editTextDita.setError("Format [Day and WeekNumber]: \n{H|M|MK|E|P}{1-4}");
                    editTextDita.requestFocus();
                    return false;
                }

                if (yearSpinner.getSelectedItem().toString().trim().equals("Choose year")) {
                    TextView errorText = (TextView) yearSpinner.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Year is required!");//changes the selected item text to this
                    yearSpinner.requestFocus();
                    return false;
                }
                if (semesterSpinner.getSelectedItem().toString().trim().equals("Choose semester")) {
                    TextView errorText = (TextView) semesterSpinner.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Semester is required!");//changes the selected item text to this
                    semesterSpinner.requestFocus();
                    return false;
                }
                if (editTextDita.getText().toString().trim().isEmpty()) {
                    editTextDita.setError("Dita is required!");
                    editTextDita.requestFocus();
                    return false;
                }
//                if (editTextDiferenca.getText().toString().trim().isEmpty()) {
//                    editTextDiferenca.setError("Difference is required!");
//                    editTextDiferenca.requestFocus();
//                    return false;
//                }

                if (sallaSpinner.getSelectedItem().toString().trim().equals("Choose exam place")) {
                    TextView errorText = (TextView) sallaSpinner.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Exam place is required!");//changes the selected item text to this
                    sallaSpinner.requestFocus();
                    return false;
                }
                saveProvimi();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}