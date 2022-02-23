package delta7.clubmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import delta7.clubmanager.databinding.ActivityRoomBinding;
import delta7.clubmanager.databinding.Addon2Binding;
import delta7.clubmanager.databinding.Addon4Binding;
import delta7.clubmanager.model.Club;

import delta7.clubmanager.model.Announcement;
import delta7.clubmanager.model.Club;
import delta7.clubmanager.model.ClubMember;

public class RoomActivity extends AppCompatActivity {
    AnnouncementAdapter announcementAdapter;
    MemberAdapter memberAdapter;
    ActivityRoomBinding binding;
    String clubId;
    boolean isOwner = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clubId = getIntent().getStringExtra(ClubListActivity.ROOM_ID);
        ClubListViewModel viewModel = new ViewModelProvider(this).get(ClubListViewModel.class);
        viewModel.getClub(clubId);

        Observer<Club> announcementObserver = new Observer<Club>() {
            @Override
            public void onChanged(Club club) {
                if (club.getAdminId().equals(Session.person.getId())) {
                    isOwner = true;
                    binding.button4.setVisibility(View.VISIBLE);
                } else {
                    binding.button4.setVisibility(View.GONE);
                }
                announcementAdapter = new AnnouncementAdapter(club.getAnnouncements(), isOwner);
                memberAdapter = new MemberAdapter(club.getRoomMembers(), isOwner);
                binding.roomRecyclerview.setAdapter(announcementAdapter);
            }
        };

        viewModel.getClubLiveData().observe(this, announcementObserver);

        Objects.requireNonNull(binding.tab.getTabAt(0)).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.roomRecyclerview.setAdapter(announcementAdapter);
            }
        });

        Objects.requireNonNull(binding.tab.getTabAt(1)).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.roomRecyclerview.setAdapter(memberAdapter);
            }
        });

        binding.roomRecyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.roomRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
                                } else {
                                    String dateTime = SimpleDateFormat.getDateTimeInstance().format(new Date());
                                    Announcement announcement = new Announcement(dialogBinding.editTextTextPersonName3.getText().toString(), dateTime);
                                    viewModel.updateClubAnnouncement(clubId, announcement);
                                }
                            }
                        });

                builder.create().show();
            }
        });
    }

    public static class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementViewHolder> {
        ArrayList<Announcement> data;
        boolean isOwner;

        public AnnouncementAdapter(ArrayList<Announcement> announcements, boolean isOwner) {
            data = announcements;
            this.isOwner = isOwner;
        }

        @NonNull
        @Override
        public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.announcement_list,parent,false);
            return new AnnouncementViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
            holder.body.setText(data.get(position).getBody());
            holder.date.setText(data.get(position).getTime());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public static class MemberAdapter extends RecyclerView.Adapter<MemberViewHolder> {
        ArrayList<ClubMember> data;
        boolean isOwner;

        public MemberAdapter(ArrayList<ClubMember> clubMember, boolean isOwner) {
            data = clubMember;
            this.isOwner = isOwner;
        }

        @NonNull
        @Override
        public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.member_list,parent,false);
            MemberViewHolder viewHolder = new MemberViewHolder(view);
            if (isOwner) {
                viewHolder.kick.setVisibility(View.VISIBLE);
                viewHolder.kick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), data.get(viewHolder.getAdapterPosition()).getName() + " is kicked from this room", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
            holder.member.setText(data.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView body;
        TextView date;

        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.announcement_body);
            date = itemView.findViewById(R.id.announcement_date);
        }
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView member;
        Button kick;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            member = itemView.findViewById(R.id.member_name);
            kick = itemView.findViewById(R.id.member_kick);
        }
    }
}