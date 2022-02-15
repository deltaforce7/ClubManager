package delta7.clubmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import delta7.clubmanager.databinding.ActivityClubListBinding;
import delta7.clubmanager.databinding.Addon2Binding;
import delta7.clubmanager.databinding.AddonBinding;

public class ClubListActivity extends AppCompatActivity {

    ActivityClubListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ClubListViewModel viewModel = new ViewModelProvider(this).get(ClubListViewModel.class);

        binding.createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClubListActivity.this);
                // Get the layout inflater

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                Addon2Binding dialogBinding = Addon2Binding.inflate(getLayoutInflater());

                builder.setView(dialogBinding.getRoot())
                        // Add action buttons
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                String code = dialogBinding.code.getText().toString();
                                String name = dialogBinding.namee.getText().toString();
                                String toast = code + "," + name;
                                Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_SHORT).show(); // sign in the user ...

                                Intent i = new Intent(ClubListActivity.this, RoomActivity.class);

                                startActivity(i);
                            }
                        });


                builder.create().show();
            }
        });

        binding.joinRoom.setOnClickListener(new View.OnClickListener() {
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
                                String code = dialogBinding.code.getText().toString();
                                String name = dialogBinding.name.getText().toString();
                                String toast = code + "," + name;
                                Toast.makeText(getApplicationContext(),toast, Toast.LENGTH_SHORT).show(); // sign in the user ...

                                // Start New Activity

                            }

                        });


                builder.create().show();
            }
        });


    }


}
