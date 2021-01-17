package com.github.florent37.materialviewpager.sample.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.sample.MainActivity;
import com.github.florent37.materialviewpager.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class TranslationFragment extends Fragment {

    //@BindView(R.id.nestedScrollViewSelfLearning)
    @BindView(R.id.nestedScrollViewTranslation)
    NestedScrollView mScrollView;
    TextView textViewTranslation;
    Button buttonTranslation;
    public static TranslationFragment newInstance() {
        return new TranslationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_translation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);


        textViewTranslation = getActivity().findViewById(R.id.textViewTranslation);
        textViewTranslation.setText("Voglio inserire una traduzione");
        buttonTranslation = getActivity().findViewById(R.id.buttonTranslation);

        buttonTranslation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Toast.makeText(getActivity(), "Sto cliccando traduci ", Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).sendBluetoothInformation("Inizia traduzione");
            }
        });
    }


    public void setTextViewTranslation(String text){
        textViewTranslation.setText(text);
    }

}
