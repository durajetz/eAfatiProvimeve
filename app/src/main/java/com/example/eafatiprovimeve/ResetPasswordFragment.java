package com.example.eafatiprovimeve;

import android.content.Context;
import android.os.Bundle;


import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends Fragment {
    FirebaseAuth mAuth;

    ProgressBar progressBar;
    EditText etEmail;
    TextView backToLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reset_password, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        etEmail = view.findViewById(R.id.et_reset_email);
        Button btnReset = view.findViewById(R.id.btnReset);
        backToLogin = view.findViewById(R.id.backtoLogin_fragment);
        progressBar = view.findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ressetPassword();
            }
        });

        backToLogin.setText("Back to login!");
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                Fragment f = getParentFragmentManager().findFragmentByTag("reset");
                if(f!=null) ft.remove(f);
                ft.commit();
            }
        });

    }


    private void ressetPassword() {
        String email = etEmail.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please provide a valid email!");
            etEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "An email to reset password was sent to " + email, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "This email doesn't exist in our database, Try again!", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}