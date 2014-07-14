package org.characterlab.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.characterlab.android.R;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthInfo;
import org.characterlab.android.models.StudentDetailViewModel;

/**
 * Created by mandar.b on 7/11/2014.
 */
public class StrongStrengthCardFragment extends Fragment {
    private int pageNumber;
    private int pageCount;
    private Strength strongest;
    private Strength improved;

    public StrongStrengthCardFragment() {
    }

    public static StrongStrengthCardFragment newInstance(int pageNumber, int pageCount, Strength strongest, Strength improved) {
        StrongStrengthCardFragment strongStrengthCardFragment = new StrongStrengthCardFragment();
        Bundle args = new Bundle();
        args.putInt("pageNum", pageNumber);
        args.putInt("pageCount", pageCount);
        args.putString("strongest", strongest.toString());
        args.putString("improved", improved.toString());
        strongStrengthCardFragment.setArguments(args);
        return strongStrengthCardFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt("pageNum", 0);
        pageCount = getArguments().getInt("pageCount", 0);
        String strongestStrengthStr = getArguments().getString("strongest");
        strongest = Strength.valueOf(strongestStrengthStr);
        String improvedStrengthStr = getArguments().getString("improved");
        improved = Strength.valueOf(improvedStrengthStr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_strong_strength_card, container, false);

        TextView tvStrongCardPageCount = (TextView) v.findViewById(R.id.tvStrongCardPageCount);
        String pageCountStr = pageNumber + "/" + pageCount;
        tvStrongCardPageCount.setText(pageCountStr);
        TextView tvStDetailsStrong = (TextView) v.findViewById(R.id.tvStDetailsStrong);
        ImageView ivStDetailsStrong = (ImageView) v.findViewById(R.id.ivStDetailsStrong);
        TextView tvStDetailsImproved = (TextView) v.findViewById(R.id.tvStDetailsImproved);
        ImageView ivStDetailsImproved = (ImageView) v.findViewById(R.id.ivStDetailsImproved);

        tvStDetailsStrong.setText(strongest.getName());
        ivStDetailsStrong.setImageResource(strongest.getIconCircleId());
        tvStDetailsImproved.setText(improved.getName());
        ivStDetailsImproved.setImageResource(improved.getIconCircleId());

        return v;
    }

}


