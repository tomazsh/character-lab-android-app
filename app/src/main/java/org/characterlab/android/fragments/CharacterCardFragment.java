package org.characterlab.android.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.characterlab.android.R;

public class CharacterCardFragment extends Fragment {
    private int page;
    private String title;
    private String description;
    private int resId;

    public CharacterCardFragment() {
        // Required empty public constructor
    }

    public static CharacterCardFragment newInstance(int page, String title, String description, int resId) {
        CharacterCardFragment fragmentCharacterCard = new CharacterCardFragment();
        Bundle args = new Bundle();
        args.putInt("pageNum", page);
        args.putString("title", title);
        args.putString("description", description);
        args.putInt("image", resId);
        fragmentCharacterCard.setArguments(args);
        return fragmentCharacterCard;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("pageNum", 0);
        title = getArguments().getString("title");
        description = getArguments().getString("description");
        resId = getArguments().getInt("image", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_character_card, container, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        tvDescription.setText(description);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        ivIcon.setImageResource(resId);
        return view;
    }

}
