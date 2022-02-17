package delta7.clubmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.ArrayList;

import delta7.clubmanager.databinding.ActivitySignBinding;
import delta7.clubmanager.databinding.ActivityLoginBinding;
import delta7.clubmanager.model.Person;

public class SignActivity extends AppCompatActivity {

    ActivitySignBinding binding;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignBinding.inflate(getLayoutInflater());
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

        awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.editTextNumberPassword,".{6,}",R.string.passwordisnotcorrect);
        awesomeValidation.addValidation(this,R.id.editTextNumberPassword2,".{6,}",R.string.passworddoesnotmatch);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate() == true) {
                    String name = binding.editTextTextPersonName.getText().toString();
                    String id = binding.editTextTextPersonName2.getText().toString();
                    String password = binding.editTextNumberPassword.getText().toString();

                    Person person = new Person(name, id, password, new ArrayList<>());
                    viewModel.signUp(person);
                }
            }
        });
    }
}
