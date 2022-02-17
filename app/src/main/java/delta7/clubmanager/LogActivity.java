package delta7.clubmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.ArrayList;

import delta7.clubmanager.databinding.ActivityLoginBinding;
import delta7.clubmanager.model.Person;

public class LogActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ClubListViewModel viewModel = new ViewModelProvider(this).get(ClubListViewModel.class);

        viewModel.getLogin().observe(this, viewState -> {
            if (viewState == ViewState.SUCCESS) {
                Intent i = new Intent(LogActivity.this,ClubListActivity.class);
                startActivity(i);
            } else if (viewState == ViewState.WRONG_PASSWORD) {
                Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
            } else if (viewState == ViewState.NOT_EXIST) {
                Toast.makeText(this, "Id doesn't exist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.editTextTextPersonName,".{1,}",R.string.noid);
        awesomeValidation.addValidation(this,R.id.editTextNumberPassword,".{1,}",R.string.nopassword);

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogActivity.this,SignActivity.class);
                startActivity(i);
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate() == true) {
                    String id = binding.editTextTextPersonName.getText().toString();
                    String password = binding.editTextNumberPassword.getText().toString();

                    viewModel.login(id, password);
                }
            }
        });
    }


}
