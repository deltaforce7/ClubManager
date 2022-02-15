package delta7.clubmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import delta7.clubmanager.databinding.ActivityClubListBinding;
import delta7.clubmanager.databinding.LoginBinding;

public class LogActivity extends AppCompatActivity {

    LoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogActivity.this,SignActivity.class);
                startActivity(i);
            }
        });
    }


}
