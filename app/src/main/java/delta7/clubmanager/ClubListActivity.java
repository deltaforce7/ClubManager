package delta7.clubmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import delta7.clubmanager.databinding.ActivityClubListBinding;
import delta7.clubmanager.databinding.Addon2Binding;
import delta7.clubmanager.databinding.AddonBinding;
import delta7.clubmanager.model.Club;
import delta7.clubmanager.model.ClubMember;
import delta7.clubmanager.model.JoinedClub;

public class ClubListActivity extends AppCompatActivity {

    ActivityClubListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ClubListViewModel viewModel = new ViewModelProvider(this).get(ClubListViewModel.class);

        viewModel.getCreateClub().observe(this, viewState -> {
            if (viewState == ViewState.SUCCESS) {
                Intent i = new Intent(ClubListActivity.this, RoomActivity.class);
                startActivity(i);
            } else if (viewState == ViewState.ALREADY_EXIST) {
                Toast.makeText(this, "Room code already exists", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getJoinClub().observe(this, viewState -> {
            if (viewState == ViewState.SUCCESS) {
                Intent i = new Intent(ClubListActivity.this, RoomActivity.class);
                startActivity(i);
            } else if (viewState == ViewState.NOT_EXIST) {
                Toast.makeText(this, "Room doesn't exist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.rooms.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        RoomsAdapter adapter= new RoomsAdapter(this, );
//        binding.rooms.setAdapter(adapter);

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
                                Club club = new Club(code, name, Session.person.getId(), new ArrayList<>(), new ArrayList<>());
                                viewModel.createClub(club);
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

                                viewModel.joinClub(code);
                            }
                        });
                builder.create().show();
            }
        });
    }

    public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {
        ArrayList<JoinedClub> data;
        Context context;

        public RoomsAdapter(Context context,ArrayList<JoinedClub> data) {
            this.data = data;
            this.context = context;
        }

        @NonNull
        @Override
        public RoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.rooms_list,parent,false);
            RoomsAdapter.ViewHolder viewHolder= new RoomsAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RoomsAdapter.ViewHolder holder, int position) {
//            holder.nameTextView.setText(data.get(position).getName());
            holder.nameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Clicked on"+data.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView nameTextView;
            public Button messageButton;


            public ViewHolder(View itemView) {

                super(itemView);

                nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
                messageButton = (Button) itemView.findViewById(R.id.message_button);
            }
        }
    }

}
