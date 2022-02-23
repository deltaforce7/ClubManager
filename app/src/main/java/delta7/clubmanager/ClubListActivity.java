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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

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
    public static String ROOM_ID = "roomId";
    public static String ROOM_NAME = "roomName";
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClubListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ClubListViewModel viewModel = new ViewModelProvider(this).get(ClubListViewModel.class);

        viewModel.getCreateClub().observe(this, viewState -> {
            if (viewState == ViewState.SUCCESS) {
                Intent intent = new Intent(ClubListActivity.this, RoomActivity.class);
                Club club = viewModel.getClubLiveData().getValue();
                intent.putExtra(ROOM_ID, club.getRoomId());
                intent.putExtra(ROOM_NAME, club.getRoomName());
                startActivity(intent);
            } else if (viewState == ViewState.ALREADY_EXIST) {
                Toast.makeText(this, "Room code already exists", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.code,".{1,}",R.string.plstypecode);
        awesomeValidation.addValidation(this,R.id.namee,".{1,}",R.string.plstypename);

        viewModel.getJoinClub().observe(this, viewState -> {
            if (viewState == ViewState.SUCCESS) {
                Intent intent = new Intent(ClubListActivity.this, RoomActivity.class);
                Club club = viewModel.getClubLiveData().getValue();
                intent.putExtra(ROOM_ID, club.getRoomId());
                intent.putExtra(ROOM_NAME, club.getRoomName());
                startActivity(intent);
            } else if (viewState == ViewState.NOT_EXIST) {
                Toast.makeText(this, "Room doesn't exist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.code,".{1,}",R.string.plstypecode);
        awesomeValidation.addValidation(this,R.id.namee,".{1,}",R.string.plstypename);

        binding.rooms.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rooms.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        RoomsAdapter adapter= new RoomsAdapter(this, Session.person.getJoinedClubs());
        binding.rooms.setAdapter(adapter);

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
                                if (dialogBinding.code.getText().toString().length() == 0 || dialogBinding.namee.getText().toString().length() == 0) {
                                    // Show Toast
                                    Toast.makeText(getApplicationContext(), "Type in room code and room name.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String code = dialogBinding.code.getText().toString();
                                    String name = dialogBinding.namee.getText().toString();
                                    Club club = new Club(code, name, Session.person.getId(), new ArrayList<>(), new ArrayList<>());
                                    viewModel.createClub(club);
                                }
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
                                if (dialogBinding.code.getText().toString().length() == 0 || dialogBinding.code.getText().toString().length() == 0) {
                                    // Show Toast
                                    Toast.makeText(getApplicationContext(), "Type in room code and room name.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                String code = dialogBinding.code.getText().toString();

                                ArrayList<JoinedClub> joinedClubs = Session.person.getJoinedClubs();
                                for (int i = 0; i < joinedClubs.size(); i++) {
                                    JoinedClub joinedClub = joinedClubs.get(i);
                                    if (joinedClub.getRoomId().equals(code)) {
                                        Toast.makeText(getApplicationContext(), "You already joined this room", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                viewModel.joinClub(code);
                            }
                        });
                builder.create().show();
            }
        });
    }

    public static class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {
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
            ViewHolder viewHolder =  new ViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(parent.getContext(), RoomActivity.class);
                    intent.putExtra(ROOM_ID, data.get(viewHolder.getAdapterPosition()).getRoomId());
                    intent.putExtra(ROOM_NAME, data.get(viewHolder.getAdapterPosition()).getRoomName());
                    parent.getContext().startActivity(intent);
                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RoomsAdapter.ViewHolder holder, int position) {
            holder.roomName.setText(data.get(position).getRoomName());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView roomName;

            public ViewHolder(View itemView) {
                super(itemView);
                roomName = itemView.findViewById(R.id.room_name);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RoomsAdapter adapter= new RoomsAdapter(this, Session.person.getJoinedClubs());
        binding.rooms.setAdapter(adapter);
    }
}
