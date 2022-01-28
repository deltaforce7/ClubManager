package delta7.clubmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import delta7.clubmanager.databinding.ActivityClubListBinding;
import delta7.clubmanager.databinding.ActivityMainBinding;
import delta7.clubmanager.databinding.AddonBinding;

public class ClubListActivity extends AppCompatActivity {

    ActivityClubListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClubListActivity.this);
                // Get the layout inflater

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                AddonBinding dialogBinding = AddonBinding.inflate(getLayoutInflater());

                builder.setView(dialogBinding.getRoot())
                        // Add action buttons
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(), dialogBinding.edittext2.getText().toString(), Toast.LENGTH_SHORT).show(); // sign in the user ...

                            }

                        });

                builder.create().show();


            }
        });
    }
}
