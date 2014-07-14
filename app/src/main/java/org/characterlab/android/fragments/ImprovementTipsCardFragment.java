package org.characterlab.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.characterlab.android.R;
import org.characterlab.android.models.Strength;
import org.characterlab.android.models.StrengthInfo;
import org.characterlab.android.models.StrengthInfoItem;

import java.util.List;

/**
 * Created by mandar.b on 7/13/2014.
 */
public class ImprovementTipsCardFragment extends Fragment {
    private int pageNumber;
    private int pageCount;
    private Strength strength;

    public static ImprovementTipsCardFragment newInstance(int pageNumber, int pageCount, Strength strength) {
        ImprovementTipsCardFragment fragment = new ImprovementTipsCardFragment();
        Bundle args = new Bundle();
        args.putInt("pageNum", pageNumber);
        args.putInt("pageCount", pageCount);
        args.putString("strength", strength.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt("pageNum", 0);
        pageCount = getArguments().getInt("pageCount", 0);
        String strengthStr = getArguments().getString("strength");
        strength = Strength.valueOf(strengthStr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_improve_tips_card, container, false);

        TextView tvTipsCardPageCount = (TextView) v.findViewById(R.id.tvTipsCardPageCount);
        String pageCountStr = pageNumber + "/" + pageCount;
        tvTipsCardPageCount.setText(pageCountStr);

        TextView tvTipsCardLabel = (TextView) v.findViewById(R.id.tvTipsCardLabel);
        String tipsCardTitle = "Improve " + strength.getName();
        tvTipsCardLabel.setText(tipsCardTitle);

        StrengthInfo strengthInfo = StrengthInfo.fromStrength(getActivity(), strength);
        List<StrengthInfoItem> infoItems = strengthInfo.getBuildItems();
        int count = 1;
        for (StrengthInfoItem infoItem : infoItems) {
            StringBuilder builder = new StringBuilder();
            builder.append("<b>");
            builder.append(infoItem.getTitle());
            builder.append(":   ");
            builder.append("</b>");
            String contents = infoItem.getContents().trim();
            contents = contents.replace("<p>", "");
            contents = contents.replace("</p>", "");
            builder.append(contents);

            TextView tvImprovementTips;
            if (count == 1) {
                tvImprovementTips = (TextView) v.findViewById(R.id.tvImprovementTips1);
                tvImprovementTips.setText(Html.fromHtml(builder.toString()));
            } else if (count == 2) {
                tvImprovementTips = (TextView) v.findViewById(R.id.tvImprovementTips2);
                tvImprovementTips.setText(Html.fromHtml(builder.toString()));
            } else if (count == 3) {
                tvImprovementTips = (TextView) v.findViewById(R.id.tvImprovementTips3);
                tvImprovementTips.setText(Html.fromHtml(builder.toString()));
                break;
            }
            count++;
        }

        return v;
    }



}
