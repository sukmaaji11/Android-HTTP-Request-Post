package com.android.httprequestpost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText;
    private EditText jobEditText;
    private Button saveUserButton;
    private ProgressBar progressBar;
    private TextView outputName, outputJob, outputId, outputCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.name);
        jobEditText = findViewById(R.id.job);
        saveUserButton = findViewById(R.id.save_user);
        progressBar = findViewById(R.id.progress_bar);
        outputName = findViewById(R.id.output_name);
        outputJob = findViewById(R.id.output_job);
        outputId = findViewById(R.id.output_id);
        outputCreate = findViewById(R.id.output_create);


        saveUserButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_user:
                User user = new User(nameEditText.getText().toString(), jobEditText.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                MainApplication.apiManager.createUser(user, new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        progressBar.setVisibility(View.GONE);
                        User responseUser = response.body();
                        if (response.isSuccessful() && responseUser != null) {
                            Toast.makeText(MainActivity.this,
                                    String.format("User %s with job %s was created at %s with id %s",
                                            responseUser.getName(),
                                            responseUser.getJob(),
                                            responseUser.getCreatedAt(),
                                            responseUser.getId()),
                                    Toast.LENGTH_LONG)
                                    .show();
                            outputName.setText("Name         : " + responseUser.getName());
                            outputJob.setText("Job              : " + responseUser.getJob());
                            outputCreate.setText("CreateAt   : " + responseUser.getCreatedAt());
                            outputId.setText("Id                 : " + responseUser.getId());
                        } else {
                            Toast.makeText(MainActivity.this,
                                    String.format("Response is %s", String.valueOf(response.code()))
                                    , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,
                                "Error is " + t.getMessage()
                                , Toast.LENGTH_LONG).show();
                    }
                });

                break;
        }
    }
}