package org.characterlab.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.characterlab.android.R;
import org.characterlab.android.adapters.StrengthDetailsCardsAdapter;
import org.characterlab.android.models.StrengthInfoItem;
import org.characterlab.android.views.RoundedParseImageView;

public class StrengthDetailsVideoCardFragment extends Fragment {
    private int mPage;
    private int mPageCount;
    private StrengthInfoItem mItem;
    private StrengthDetailsCardsAdapter.StrengthDetailsCardListener mListener;

    public StrengthDetailsVideoCardFragment() {
    }

    public static StrengthDetailsVideoCardFragment newInstance(int page, int pageCount, StrengthInfoItem item) {
        StrengthDetailsVideoCardFragment fragment = new StrengthDetailsVideoCardFragment();
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
        View view = inflater.inflate(R.layout.fragment_strength_details_video_card, container, false);

        TextView titleTextView = (TextView)view.findViewById(R.id.title_text_view);
        titleTextView.setText(mItem.getTitle());

        String page = Integer.toString(mPage+1) + "/" + Integer.toString(mPageCount);
        TextView pageTextView = (TextView)view.findViewById(R.id.page_text_view);
        pageTextView.setText(page);

        RoundedParseImageView coverImageView = (RoundedParseImageView)view.findViewById(R.id.cover_image_view);
        coverImageView.setImageResource(mItem.getCoverResourceId(getActivity()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCardItemClick(mItem);
            }
        });

        return view;
    }
}
