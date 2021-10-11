package com.example.eafatiprovimeve.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.eafatiprovimeve.R;

public class AddEditActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.eafatiprovimeve.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.eafatiprovimeve.EXTRA_NAME";
    public static final String EXTRA_DIFERENCA = "com.example.eafatiprovimeve.EXTRA_DIFERENCA";
    public static final String EXTRA_VITI = "com.example.eafatiprovimeve.EXTRA_VITI";
    public static final String EXTRA_SEMESTRI = "com.example.eafatiprovimeve.EXTRA_SEMESTRI";
    public static final String EXTRA_DITA = "com.example.eafatiprovimeve.EXTRA_DITA";

    private EditText editTextName, editTextDita, editTextViti, editTextSemestri, editTextDiferenca;
    private ImageView logoAddEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        editTextName = findViewById(R.id.edit_text_name);
        editTextDita = findViewById(R.id.edit_text_dita);
        editTextViti = findViewById(R.id.edit_text_viti);
        editTextSemestri = findViewById(R.id.edit_text_semestri);
        editTextDiferenca = findViewById(R.id.edit_text_difference);
        logoAddEdit = findViewById(R.id.logo_imageview);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            logoAddEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_twotone_edit));
            setTitle("Edit Provimi");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextDita.setText(intent.getStringExtra(EXTRA_DITA));
            editTextViti.setText(String.valueOf(intent.getIntExtra(EXTRA_VITI, 1)));
            editTextSemestri.setText(String.valueOf(intent.getIntExtra(EXTRA_SEMESTRI, 1)));
            editTextDiferenca.setText(String.valueOf(intent.getIntExtra(EXTRA_DIFERENCA, 1)));
        } else {
            logoAddEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_circle));
            setTitle("Add Provimi");
        }

    }

    private void saveProvimi() {

        String name = editTextName.getText().toString();
        String dita = editTextDita.getText().toString();
        int diferenca = Integer.parseInt(editTextDiferenca.getText().toString());
        int viti = Integer.parseInt(editTextViti.getText().toString());
        int semestri = Integer.parseInt(editTextSemestri.getText().toString());

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_DITA, dita);
        data.putExtra(EXTRA_DIFERENCA, diferenca);
        data.putExtra(EXTRA_VITI, viti);
        data.putExtra(EXTRA_SEMESTRI, semestri);

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
                if (editTextDita.getText().toString().trim().isEmpty()) {
                    editTextDita.setError("Dita is required!");
                    editTextDita.requestFocus();
                    return false;
                }
                if (editTextDiferenca.getText().toString().trim().isEmpty()) {
                    editTextDiferenca.setError("Difference is required!");
                    editTextDiferenca.requestFocus();
                    return false;
                }
                if (editTextViti.getText().toString().trim().isEmpty()) {
                    editTextViti.setError("Year is required!");
                    editTextViti.requestFocus();
                    return false;
                }
                if (editTextSemestri.getText().toString().trim().isEmpty()) {
                    editTextSemestri.setError("Semester year is required!");
                    editTextSemestri.requestFocus();
                    return false;
                }
                saveProvimi();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}