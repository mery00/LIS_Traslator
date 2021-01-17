package com.github.florent37.materialviewpager.sample;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.Utils;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Message> messageList;
    private final int VIEW_TYPE_MESSAGE_SENT =0;
    private final int VIEW_TYPE_MESSAGE_RECEIVED=1;

    public MessageListAdapter(Context context, List<Message> messageList) {
        mContext = context;
        this.messageList = messageList;
    }
    @Override
    public int getItemCount() {
        Log.d("size messaggi", ""+messageList.size());
        return messageList.size();
    }
    @Override
    public int getItemViewType(int position) {
        UserMessage user = (UserMessage) messageList.get(position).getUserMessage();
        if (user.getName().equals("User_app")) {
            // If the current user is the sender of the message
       /*  if (position == messageList.size() - 1) {
                MaterialViewPagerHelper.registerScrollView(activity, nScrollView);
            }*/
            return 0;
        } else {
          /*  if (position == messageList.size() - 1) {
                MaterialViewPagerHelper.registerScrollView(activity, nScrollView);
            }*/
            return 1;
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) messageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }



    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;
        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body_sent);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time_sent);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name_sent);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile_sent);

        }

        void bind(Message message) {
            messageText.setText(message.getText());
            nameText.setText(message.getUserMessage().getName());
            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getMessageTime());
            profileImage.setImageResource(R.drawable.user);

        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body_received);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time_received);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name_received);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile_received);
        }

        void bind(Message message) {
            messageText.setText(message.getText());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getMessageTime());

            nameText.setText(message.getUserMessage().getName());

            profileImage.setImageResource(R.drawable.ic_launcher);
            // Insert the profile image from the URL into the ImageView.
          //  Utils.displayRoundImageFromUrl(mContext, message.getUserMessage().getProfileUrl(), profileImage);
        }
    }

}