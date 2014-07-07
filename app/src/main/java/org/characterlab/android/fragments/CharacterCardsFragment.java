package org.characterlab.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.characterlab.android.R;
import org.characterlab.android.models.Strength;

public class CharacterCardsFragment extends Fragment {
    CharacterCardsFragmentListener mListener;

    public interface CharacterCardsFragmentListener {
        void onStrengthCardClick(Strength strength);
    }

    public CharacterCardsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character_cards, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CharacterCardsFragmentListener)activity;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }
}
