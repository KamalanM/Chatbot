package com.example.priya.chatbot;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import static com.example.priya.chatbot.R.color.inspire_blue;
import static com.example.priya.chatbot.R.color.notification_icon_bg_color;

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{


    private List<Messages> messagesList;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_left,text_right;


        public MyViewHolder(View view) {
            super(view);
            text_left= (TextView) view.findViewById(R.id.text_left);
            text_right= (TextView) view.findViewById(R.id.text_right);



        }
    }

    public MessageAdapter(List<Messages>messagesList1) {
        this.messagesList = messagesList1;
    }

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {


        Messages messages = messagesList.get(position);
        String cur_user=messages.getCur_user();
        String author=messages.getAuthor();
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if(cur_user.equals(author)){


            holder.text_right.setGravity(Gravity.RIGHT);
          //  layoutParams.gravity=Gravity.RIGHT;
            holder.text_right.setBackgroundColor(Color.parseColor("#40c272"));
            holder.text_right.setText(messages.getMessage());

        }
        else {
            holder.text_left.setBackgroundColor(Color.parseColor("#51b9d1"));
            //layoutParams.gravity=Gravity.LEFT;
            holder.text_left.setGravity(Gravity.LEFT);
            holder.text_left.setText(messages.getMessage());

        }

        //holder.text.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }






}
