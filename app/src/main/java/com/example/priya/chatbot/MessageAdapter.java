package com.example.priya.chatbot;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.example.priya.chatbot.R.color.inspire_blue;
import static com.example.priya.chatbot.R.color.inspire_green;
import static com.example.priya.chatbot.R.color.notification_icon_bg_color;


public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{


    public static ArrayList<String> cur_userlist=new ArrayList<>();
    public static ArrayList<String> authorlist=new ArrayList<>();
    public static ArrayList<String> messagesList=new ArrayList<>();



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_left,text_right,name_left,name_right;
        public RelativeLayout RL_left,RL_right;


        public MyViewHolder(View view) {
            super(view);
            text_left= (TextView) view.findViewById(R.id.text_left);
            //text_right= (TextView) view.findViewById(R.id.text_right);
            name_left= (TextView) view.findViewById(R.id.name_left);
            //name_right= (TextView) view.findViewById(R.id.name_right);
            //RL_left=(RelativeLayout)view.findViewById(R.id.user_one);
            //RL_right=(RelativeLayout)view.findViewById(R.id.user_two);

        }
    }

    /*public MessageAdapter(List<Messages>messagesList1) {
        this.messagesList = messagesList1;
    }*/

    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyViewHolder holder, int position) {


//        Messages messages = messagesList.get(position);
//        String cur_user=messages.getCur_user();
//        String author=messages.getAuthor();

        String cur_user=cur_userlist.get(position);
        String author=authorlist.get(position);
        //LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if(cur_user.equals(author)){


//            holder.text_right.setGravity(Gravity.RIGHT);
//            layoutParams.gravity=Gravity.RIGHT;
//            holder.text_right.setBackgroundColor(Color.parseColor("#40c272"));
            //holder.RL_left.setVisibility(View.GONE);
//            holder.name_right.setText(cur_user);
//            holder.text_right.setText(messagesList.get(position));
            holder.name_left.setText(cur_user);
            holder.text_left.setText(messagesList.get(position));
            holder.name_left.getResources().getColor(inspire_green,null);
            holder.text_left.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            holder.name_left.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        }
        else {
//            holder.text_left.setBackgroundColor(Color.parseColor("#51b9d1"));
//            layoutParams.gravity=Gravity.LEFT;
//            holder.text_left.setGravity(Gravity.LEFT);
            //holder.RL_right.setVisibility(View.GONE);
            holder.text_left.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            holder.name_left.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            holder.name_left.setText(authorlist.get(position));
            holder.text_left.setText(messagesList.get(position));

        }

        //holder.text.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }


}













//public class MessageAdapter extends ArrayAdapter<Messages> {
//
//    private Context context;
//    private List<Messages> values;
//    public static ArrayList<String> cur_userlist=new ArrayList<>();
//    public static ArrayList<String> authorlist=new ArrayList<>();
//    public static ArrayList<String> messagesList=new ArrayList<>();
//    public TextView text_left,text_right,name_left,name_right;
//    public RelativeLayout RL_left,RL_right;
//
//
//
//    public MessageAdapter(Context context, List<Messages> values) {
//        super(context, R.layout.activity_personal__chat, values);
//
//
//        this.context = context;
//        this.values = values;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View row = convertView;
//
//        if (row == null) {
//            LayoutInflater inflater =
//                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            row = inflater.inflate(R.layout.activity_personal__chat, parent, false);
//        }
//
//        text_left= (TextView) row.findViewById(R.id.text_left);
//        text_right= (TextView) row.findViewById(R.id.text_right);
//        name_left= (TextView) row.findViewById(R.id.name_left);
//        name_right= (TextView) row.findViewById(R.id.name_right);
//        RL_left=(RelativeLayout)row.findViewById(R.id.user_one);
//        RL_right=(RelativeLayout)row.findViewById(R.id.user_two);
//
//        String cur_user=cur_userlist.get(position);
//        String author=authorlist.get(position);
//        if(cur_user.equals(author)){
//
//
//            RL_left.setVisibility(View.GONE);
//            name_right.setText(cur_user);
//            text_right.setText(messagesList.get(position));
//
//        }
//        else {
//            RL_right.setVisibility(View.GONE);
//            name_left.setText(authorlist.get(position));
//            text_left.setText(messagesList.get(position));
//
//        }
//
//        return row;
//    }
//
//
//}
//













