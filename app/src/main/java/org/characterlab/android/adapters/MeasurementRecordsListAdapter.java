package org.characterlab.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.characterlab.android.R;
import org.characterlab.android.models.StrengthAssessment;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by mandar.b on 7/13/2014.
 */
public class MeasurementRecordsListAdapter extends ArrayAdapter<StrengthAssessment> {

    private static class ViewHolder {
        RelativeLayout rlMeasurementList;
        TextView tvMeasurementDate;
        TextView tvMeasurementIndex;
    }

    public MeasurementRecordsListAdapter(Context context, List<StrengthAssessment> assessmentList) {
        super(context, 0, assessmentList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StrengthAssessment assessment = getItem(position);

        final ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.measurement_list_item, parent, false);

            viewHolder.rlMeasurementList = (RelativeLayout) convertView.findViewById(R.id.rlMeasurementList);
            viewHolder.tvMeasurementDate = (TextView) convertView.findViewById(R.id.tvMeasurementDate);
            viewHolder.tvMeasurementIndex = (TextView) convertView.findViewById(R.id.tvMeasurementIndex);

            convertView.setTag(viewHolder);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM. d, yyyy", Locale.US);
        dateFormat.format(assessment.getCreatedAt());

        viewHolder.tvMeasurementDate.setText(dateFormat.format(assessment.getCreatedAt()));

        viewHolder.tvMeasurementIndex.setText(attachIndexSuffix(getCount() - position));
//        viewHolder.tvMeasurementIndex.setText(attachIndexSuffix(assessment.getGroupId()));

        if (position % 2 == 0) {
            viewHolder.rlMeasurementList.setBackgroundColor(getContext().getResources().getColor(android.R.color.background_dark));
        } else {
            viewHolder.rlMeasurementList.setBackgroundColor(getContext().getResources().getColor(android.R.color.background_light));
        }

        return convertView;
    }

    private String attachIndexSuffix(int measurementIndex) {
        return measurementIndex + "th Measurement";
    }

}
