package org.characterlab.android.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import com.parse.FindCallback;

import org.characterlab.android.R;
import org.characterlab.android.adapters.StrengthDetailsCardsAdapter;
import org.characterlab.android.adapters.StudentsGridAdapter;
import org.characterlab.android.dialogs.StrengthDetailsTextCardDialog;
import org.characterlab.android.helpers.ParseClient;
import org.characterlab.android.helpers.ProgressBarHelper;
import org.characterlab.android.models.StrengthInfo;
import org.characterlab.android.models.StrengthInfoItem;
import org.characterlab.android.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StrengthDetailsFragment extends Fragment
        implements StrengthDetailsCardsAdapter.StrengthDetailsCardListener {
    StrengthInfo mStrengthInfo;
    StrengthDetailsFragmentListener mListener;
    StrengthDetailsCardsAdapter mAboutCardsAdapter;
    StrengthDetailsCardsAdapter mBuildCardsAdapter;
    StudentsGridAdapter mGoodStudentsGridAdapter;
    StudentsGridAdapter mBadStudentsGridAdapter;
    ProgressBarHelper mProgressBarHelper;
    int mCompletedRequests = 0;

    ScrollView mScrollView;
    GridView mBadStudentsGridView;
    GridView mGoodStudentsGridView;

    public interface StrengthDetailsFragmentListener {
        void onStrengthDetailsStudentClick(Student student);
        FragmentManager strengthDetailsFragmentManager();
    }

    public StrengthDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_strength_details, container, false);

        mAboutCardsAdapter = new StrengthDetailsCardsAdapter(mListener.strengthDetailsFragmentManager(), this);
        mAboutCardsAdapter.setBuildItems(mStrengthInfo.getAboutItems());

        mBuildCardsAdapter = new StrengthDetailsCardsAdapter(mListener.strengthDetailsFragmentManager(), this);
        mBuildCardsAdapter.setBuildItems(mStrengthInfo.getBuildItems());

        mGoodStudentsGridAdapter = new StudentsGridAdapter(getActivity(), new ArrayList<Student>());
        mBadStudentsGridAdapter = new StudentsGridAdapter(getActivity(), new ArrayList<Student>());

        Resources r = inflater.getContext().getResources();
        int margin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());

        mScrollView = (ScrollView)v.findViewById(R.id.scroll_view);
        mScrollView.setVisibility(View.GONE);

        ImageView heroImageView = (ImageView)v.findViewById(R.id.hero_image_view);
        heroImageView.setImageResource(mStrengthInfo.getHeroImageResourceId(getActivity()));

        TextView descriptionTextView = (TextView)v.findViewById(R.id.description_text_view);
        descriptionTextView.setText(Html.fromHtml(mStrengthInfo.getDescription()));

        TextView aboutTitleTextView = (TextView)v.findViewById(R.id.about_title_text_view);
        aboutTitleTextView.setText(mStrengthInfo.getAboutTitle().toUpperCase());

        ViewPager aboutPager = (ViewPager)v.findViewById(R.id.about_pager);
        aboutPager.setClipToPadding(false);
        aboutPager.setPageMargin(margin);
        aboutPager.setAdapter(mAboutCardsAdapter);

        TextView buildTitleTextView = (TextView)v.findViewById(R.id.build_title_text_view);
        buildTitleTextView.setText(mStrengthInfo.getBuildTitle().toUpperCase());

        ViewPager buildPager = (ViewPager)v.findViewById(R.id.build_pager);
        buildPager.setClipToPadding(false);
        buildPager.setPageMargin(margin);
        buildPager.setAdapter(mBuildCardsAdapter);

        mBadStudentsGridView = (GridView)v.findViewById(R.id.good_students_grid_view);
        mBadStudentsGridView.setAdapter(mBadStudentsGridAdapter);
        mBadStudentsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student student = mBadStudentsGridAdapter.getItem(i);
                mListener.onStrengthDetailsStudentClick(student);
            }
        });
        mBadStudentsGridAdapter.notifyDataSetChanged();

        mGoodStudentsGridView = (GridView)v.findViewById(R.id.bad_students_grid_view);
        mGoodStudentsGridView.setAdapter(mGoodStudentsGridAdapter);
        mGoodStudentsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student student = mGoodStudentsGridAdapter.getItem(i);
                mListener.onStrengthDetailsStudentClick(student);
            }
        });
        mGoodStudentsGridAdapter.notifyDataSetChanged();

        mProgressBarHelper = new ProgressBarHelper(getActivity());
        mProgressBarHelper.setupProgressBarViews(v);
        mProgressBarHelper.showProgressBar();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadBadStudentsDataFromParse();
        loadGoodStudentsDataFromParse();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (StrengthDetailsFragmentListener)activity;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    //region Getters and Setters

    public StrengthInfo getStrengthInfo() {
        return mStrengthInfo;
    }

    public void setStrengthInfo(StrengthInfo strengthInfo) {
        mStrengthInfo = strengthInfo;
    }

    //endregion

    //region Parse Loading

    private void loadGoodStudentsDataFromParse() {
        ParseClient.getGoodStudentsForStrength(mStrengthInfo.getStrength(), new FindCallback<Student>() {
            @Override
            public void done(List<Student> studentList, com.parse.ParseException e) {
                if (e == null) {
                    int x = mScrollView.getScrollX();
                    int y = mScrollView.getScrollY();

                    mGoodStudentsGridAdapter.clear();
                    for (Student student : studentList) {
                        mGoodStudentsGridAdapter.add(student);
                    }
                    mGoodStudentsGridAdapter.notifyDataSetChanged();
                    setGridViewHeightBasedOnChildren(mGoodStudentsGridView);

                    mScrollView.setScrollX(x);
                    mScrollView.setScrollY(y);
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
                mCompletedRequests++;
                updateViews();
            }
        });
    }

    private void loadBadStudentsDataFromParse() {
        ParseClient.getGoodStudentsForStrength(mStrengthInfo.getStrength(), new FindCallback<Student>() {
            @Override
            public void done(List<Student> studentList, com.parse.ParseException e) {
                if (e == null) {
                    int x = mScrollView.getScrollX();
                    int y = mScrollView.getScrollY();

                    mBadStudentsGridAdapter.clear();
                    for (Student student : studentList) {
                        mBadStudentsGridAdapter.add(student);
                    }
                    mBadStudentsGridAdapter.notifyDataSetChanged();
                    setGridViewHeightBasedOnChildren(mBadStudentsGridView);

                    mScrollView.scrollTo(x, y);
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
                mCompletedRequests++;
                updateViews();
            }
        });
    }

    private void updateViews() {
        if (mCompletedRequests > 1) {
            mScrollView.setVisibility(View.VISIBLE);
            mProgressBarHelper.hideProgressBar();
        }
    }

    //endregion

    public void setGridViewHeightBasedOnChildren(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int items = listAdapter.getCount();
        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);

        gridView.setNumColumns(items);

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = listItem.getMeasuredHeight();
        params.width = (listItem.getMeasuredWidth()+60)*items;
        gridView.setLayoutParams(params);

    }

    @Override
    public void onCardItemClick(StrengthInfoItem item) {
        System.out.println(item);
        StrengthDetailsTextCardDialog dialog = new StrengthDetailsTextCardDialog(item);
        dialog.show(getFragmentManager(), "strength_details_text_card");
    }
}
