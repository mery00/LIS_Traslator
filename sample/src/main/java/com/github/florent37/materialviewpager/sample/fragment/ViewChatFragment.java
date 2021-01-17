package com.github.florent37.materialviewpager.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.github.florent37.materialviewpager.MaterialViewPagerAnimator;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.sample.Message;
import com.github.florent37.materialviewpager.sample.MessageListAdapter;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.UserMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class ViewChatFragment extends Fragment {

    @BindView(R.id.nestedScrollViewChat)
    NestedScrollView mScrollView;
    //ScrollView scrollView;
    RecyclerView chatRecyclerView;

    public static ViewChatFragment newInstance() {
        return new ViewChatFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        // scrollView = (ScrollView)getView().findViewById(R.id.scrollViewChat);


        chatRecyclerView = (RecyclerView) getView().findViewById(R.id.reyclerview_message_list);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatRecyclerView.setHasFixedSize(true);
        final List<Message> messageList  = new ArrayList<>();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        Message message = new Message("Buongiorno", new UserMessage("User_app"), Calendar.getInstance().getTime().toString(), dateFormat.format(new Date()));
        Message message1 = new Message("Ciao", new UserMessage("Glove"), Calendar.getInstance().getTime().toString(), dateFormat.format(new Date()));
        Message message2 = new Message("Come stai?", new UserMessage("User_app"), Calendar.getInstance().getTime().toString(), dateFormat.format(new Date()));
        Message message3 = new Message("Bene, tu?", new UserMessage("Glove"), Calendar.getInstance().getTime().toString(), dateFormat.format(new Date()));
        Message message4 = new Message("Bene", new UserMessage("User_app"), Calendar.getInstance().getTime().toString(), dateFormat.format(new Date()));
        Message message5 = new Message("Buongiorno", new UserMessage("User_app"), Calendar.getInstance().getTime().toString(), dateFormat.format(new Date()));
        Message message6 = new Message("Ciao", new UserMessage("Glove"), Calendar.getInstance().getTime().toString(), dateFormat.format(new Date()));
        Message message7 = new Message("Come stai?", new UserMessage("User_app"), Calendar.getInstance().getTime().toString(), dateFormat.format(new Date()));
        Message message8 = new Message("Bene, tu?", new UserMessage("Glove"), Calendar.getInstance().getTime().toString(), dateFormat.format(new Date()));
        Message message9 = new Message("Bene", new UserMessage("User_app"), Calendar.getInstance().getTime().toString(), dateFormat.format(new Date()));
        messageList.add(message);
        messageList.add(message1);
        messageList.add(message2);
        messageList.add(message3);
        messageList.add(message4);
        messageList.add(message5);
        messageList.add(message6);
        messageList.add(message7);
        messageList.add(message8);
        messageList.add(message8);
        messageList.add(message8);
        messageList.add(message8);
        messageList.add(message8);
        messageList.add(message8);
        messageList.add(message8);

        chatRecyclerView.setAdapter(new MessageListAdapter(getContext(),messageList));
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);
    }


    public void uploadChatMessage(List<Message> messageListUpload){
        chatRecyclerView.setAdapter(new MessageListAdapter(getContext(),messageListUpload));
    }
}
