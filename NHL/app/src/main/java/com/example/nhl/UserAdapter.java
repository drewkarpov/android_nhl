package com.example.nhl;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhl.activities.UserDataActivity;
import com.example.nhl.helpers.ColorGenerator;
import com.example.nhl.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersViewHolder> {

    private List<User> users;
    public onUserClickListener onUserClickListener;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    interface onUserClickListener {
        void onNoteClick(int position);
    }

    public void setOnUserClickListener(UserAdapter.onUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_card, viewGroup, false);
        return new UsersViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewName.setText(user.getName());
        holder.textViewStatus.setText(user.getStatus());
        String id = String.format("%s", user.getId());
        holder.textViewId.setText(String.format("%s", user.getId()));
        if (!user.getComment().isEmpty()) {
            holder.textViewCommentFlag.setText("*");
        }
        int colorId;
        int priority = user.getPriority();
        colorId = ColorGenerator.getColorId(holder, priority);
        holder.textViewStatus.setBackgroundColor(colorId);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UserDataActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", user.getName());
                intent.putExtra("comment", user.getComment());
                intent.putExtra("status", user.getStatus());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewStatus;
        private TextView textViewId;
        private TextView textViewCommentFlag;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewCommentFlag = itemView.findViewById(R.id.textViewCommentId);

        }
    }
}

