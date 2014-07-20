package org.characterlab.android.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.characterlab.android.R;
import org.characterlab.android.adapters.StrengthDetailsCardsAdapter;
import org.characterlab.android.models.StrengthInfoItem;

public class StrengthDetailsTextCardFragment extends Fragment {
    private int mPage;
    private int mPageCount;
    private StrengthInfoItem mItem;
    private StrengthDetailsCardsAdapter.StrengthDetailsCardListener mListener;

    public StrengthDetailsTextCardFragment() {
    }

    public static StrengthDetailsTextCardFragment newInstance(int page, int pageCount,StrengthInfoItem item) {
        StrengthDetailsTextCardFragment fragment = new StrengthDetailsTextCardFragment();
        Bundle args = new Bundle();
        args.putInt("pageNum", page);
        args.putInt("pageCount", pageCount);
        args.putSerializable("item", item);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(StrengthDetailsCardsAdapter.StrengthDetailsCardListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("pageNum", 0);
        mPageCount = getArguments().getInt("pageCount", 0);
        mItem = (StrengthInfoItem)getArguments().getSerializable("item");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strength_detail_text_card, container, false);

        Drawable d = inflater.getContext().getResources().getDrawable(R.drawable.strength_details_card_bg);
        view.findViewById(R.id.container).setBackground(d);

        TextView titleTextView = (TextView)view.findViewById(R.id.title_text_view);
        titleTextView.setText(mItem.getTitle());

        String page = Integer.toString(mPage+1) + "/" + Integer.toString(mPageCount);
        TextView pageTextView = (TextView)view.findViewById(R.id.page_text_view);
        pageTextView.setText(page);

        TextView contentTextView = (TextView)view.findViewById(R.id.content_text_view);
        contentTextView.setText(Html.fromHtml(mItem.getContents()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCardItemClick(mItem);
            }
        });

        return view;
    }
}
