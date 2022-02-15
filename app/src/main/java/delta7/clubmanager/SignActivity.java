package delta7.clubmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import delta7.clubmanager.databinding.LoginBinding;
import delta7.clubmanager.databinding.SignBinding;

public class SignActivity extends AppCompatActivity {

    SignBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
