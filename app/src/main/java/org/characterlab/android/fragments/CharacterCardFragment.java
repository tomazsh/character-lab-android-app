package org.characterlab.android.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.characterlab.android.R;
import org.characterlab.android.activities.MainActivity;
import org.characterlab.android.models.Strength;

public class CharacterCardFragment extends Fragment {
    private Strength strength;
    private CharacterCardFragmentListener mListener;

    public interface CharacterCardFragmentListener {
        void onStrengthCardClick(Strength strength);
    }

    public CharacterCardFragment() {
        // Required empty public constructor
    }

    public static CharacterCardFragment newInstance(Strength strength) {
        CharacterCardFragment fragmentCharacterCard = new CharacterCardFragment();
        Bundle args = new Bundle();
        args.putString("strength", strength.toString());
        fragmentCharacterCard.setArguments(args);
        return fragmentCharacterCard;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String strengthStr = getArguments().getString("strength");
        strength = Strength.valueOf(strengthStr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_character_card, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onStrengthCardClick(strength);
            }
        });
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(strength.getName());
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        tvDescription.setText(strength.getDescriptionId());
        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        ivIcon.setImageResource(strength.getIconCircleId());
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (MainActivity) activity;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }
}
