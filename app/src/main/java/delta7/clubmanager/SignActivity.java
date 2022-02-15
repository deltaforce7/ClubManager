package delta7.clubmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import delta7.clubmanager.databinding.LoginBinding;
import delta7.clubmanager.databinding.SignBinding;
import delta7.clubmanager.model.Person;

public class SignActivity extends AppCompatActivity {

    SignBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ClubListViewModel viewModel = new ViewModelProvider(this).get(ClubListViewModel.class);

        viewModel.getSignUp().observe(this, viewState -> {
            if (viewState == ViewState.SUCCESS) {
                Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                finish();
            } else if (viewState == ViewState.ALREADY_EXIST) {
                Toast.makeText(this, "Id already exists. Try another ID", Toast.LENGTH_SHORT).show();
            } else if (viewState == ViewState.FAILURE) {
                Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextTextPersonName.getText().toString();
                String id = binding.editTextTextPersonName2.getText().toString();
                String password = binding.editTextNumberPassword.getText().toString();

                Person person = new Person(name, id, password, new ArrayList<>());
                viewModel.signUp(person);
            }
        });
    }
}
