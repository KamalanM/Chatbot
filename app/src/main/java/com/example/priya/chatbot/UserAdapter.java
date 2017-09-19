package com.example.priya.chatbot;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {


    private List<User> userList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name= (TextView) view.findViewById(R.id.username);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


//                    Intent intent=new Intent(v.getContext(),Personal_Chat.class);
//                    v.getContext().startActivity(intent);
                    String user=name.getText().toString();
                    Intent i = new Intent(v.getContext(), Personal_Chat.class);
                    i.putExtra("User", user);
                    v.getContext().startActivity(i);
                    //Toast.makeText(v.getContext(),"Don't Touch Again  -_-"+user,Toast.LENGTH_SHORT).show();



                }
            });
        }
    }

    public UserAdapter(List<User>userList1) {
        this.userList = userList1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.usernames, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        User user = userList.get(position);
        holder.name.setText(user.getUsername());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }




}
