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

/**
 * Created by mandar.b on 7/12/2014.
 */
public class WeakStrengthCardFragment extends Fragment {
    private int pageNumber;
    private int pageCount;
    private Strength weakStrength1;
    private Strength weakStrength2;
    private Strength weakStrength3;

    TextView tvStrongCardPageCount;
    TextView tvStDetailsWeak1;
    ImageView ivStDetailsWeak1;
    TextView tvStDetailsWeak2;
    ImageView ivStDetailsWeak2;
    TextView tvStDetailsWeak3;
    ImageView ivStDetailsWeak3;


    public WeakStrengthCardFragment() {
    }

    public static WeakStrengthCardFragment newInstance(int pageNumber, int pageCount, Strength weak1, Strength weak2, Strength weak3) {
        WeakStrengthCardFragment weakStrengthCardFragment = new WeakStrengthCardFragment();
        Bundle args = new Bundle();
        args.putInt("pageNum", pageNumber);
        args.putInt("pageCount", pageCount);
        args.putString("weak1", weak1.toString());
        args.putString("weak2", weak2.toString());
        args.putString("weak3", weak3.toString());
        weakStrengthCardFragment.setArguments(args);
        return weakStrengthCardFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt("pageNum", 0);
        pageCount = getArguments().getInt("pageCount", 0);
        String weakStrength1Str = getArguments().getString("weak1");
        weakStrength1 = Strength.valueOf(weakStrength1Str);
        String weakStrength2Str = getArguments().getString("weak2");
        weakStrength2 = Strength.valueOf(weakStrength2Str);
        String weakStrength3Str = getArguments().getString("weak3");
        weakStrength3 = Strength.valueOf(weakStrength3Str);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_weak_strength_card, container, false);

        tvStrongCardPageCount = (TextView) v.findViewById(R.id.tvStrongCardPageCount);
        tvStDetailsWeak1 = (TextView) v.findViewById(R.id.tvStDetailsWeak1);
        ivStDetailsWeak1 = (ImageView) v.findViewById(R.id.ivStDetailsWeak1);
        tvStDetailsWeak2 = (TextView) v.findViewById(R.id.tvStDetailsWeak2);
        ivStDetailsWeak2 = (ImageView) v.findViewById(R.id.ivStDetailsWeak2);
        tvStDetailsWeak3 = (TextView) v.findViewById(R.id.tvStDetailsWeak3);
        ivStDetailsWeak3 = (ImageView) v.findViewById(R.id.ivStDetailsWeak3);

        updateViews();

        return v;
    }

    public void resetView(int pageNum, int pageCnt, Strength weak1, Strength weak2, Strength weak3) {
        pageNumber = pageNum;
        pageCount = pageCnt;
        weakStrength1 = weak1;
        weakStrength2 = weak2;
        weakStrength3 = weak3;

        updateViews();
    }

    private void updateViews() {
        String pageCountStr = pageNumber + "/" + pageCount;
        tvStrongCardPageCount.setText(pageCountStr);

        tvStDetailsWeak1.setText(weakStrength1.getName());
        ivStDetailsWeak1.setImageResource(weakStrength1.getIconCircleId());

        tvStDetailsWeak2.setText(weakStrength2.getName());
        ivStDetailsWeak2.setImageResource(weakStrength2.getIconCircleId());

        tvStDetailsWeak3.setText(weakStrength3.getName());
        ivStDetailsWeak3.setImageResource(weakStrength3.getIconCircleId());
    }


}


