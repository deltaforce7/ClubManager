package delta7.clubmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import delta7.clubmanager.databinding.ActivityRoomBinding;
import delta7.clubmanager.databinding.Addon2Binding;
import delta7.clubmanager.databinding.Addon4Binding;
import delta7.clubmanager.model.Club;


public class RoomActivity extends AppCompatActivity {
    ContactsAdapter adapter;

    ArrayList<Contact> contacts;
    ActivityRoomBinding binding;


String announcements[]={"be kind","do what you're asked to do","hello","12345"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contacts = new ArrayList<>();
        contacts.add(new Contact("Delta7", true));
        contacts.add(new Contact("Delta6", true));
        contacts.add(new Contact("Delta5", true));
        contacts.add(new Contact("Delta4", true));
        contacts.add(new Contact("Delta3", true));
        contacts.add(new Contact("Delta2", true));
        contacts.add(new Contact("Delta1", false));

        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter= new ContactsAdapter(this,contacts);
        binding.rvContacts.setAdapter(adapter);

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
                Addon4Binding dialogBinding = Addon4Binding.inflate(getLayoutInflater());

                builder.setView(dialogBinding.getRoot())
                        // Add action buttons
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (dialogBinding.editTextTextPersonName3.getText().toString().length() == 0) {
                                    // Show Toast
                                    Toast.makeText(getApplicationContext(), "Type in announcements.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                builder.create().show();
            }
        });
    }
}