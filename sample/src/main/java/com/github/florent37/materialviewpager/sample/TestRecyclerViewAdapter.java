package com.github.florent37.materialviewpager.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> contents;

    static final int TYPE_CELL_EDIT_TEXT = 0;
    static final int TYPE_CELL_SPEECH = 1;
    static final int TYPE_CELL_TRANSLATION = 2;
    static final int TYPE_CELL_SMALL_DEFAULT = 3;


    public TestRecyclerViewAdapter(List<Object> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_CELL_EDIT_TEXT;
            case 1:
                return TYPE_CELL_SPEECH;
            case 2:
                return TYPE_CELL_TRANSLATION;
            default:
                return TYPE_CELL_SMALL_DEFAULT;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_CELL_EDIT_TEXT: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small_edit_text, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
            case TYPE_CELL_SPEECH: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small_speech, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
            case TYPE_CELL_TRANSLATION: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big_traslator, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_CELL_EDIT_TEXT:
                break;
            case TYPE_CELL_SPEECH:
                break;
            case TYPE_CELL_TRANSLATION:
                break;
        }
    }
}