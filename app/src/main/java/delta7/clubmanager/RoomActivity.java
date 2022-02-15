package delta7.clubmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import delta7.clubmanager.databinding.ActivityRoomBinding;


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


    }

}