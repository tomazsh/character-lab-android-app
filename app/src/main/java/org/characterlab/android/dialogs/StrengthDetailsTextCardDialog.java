package org.characterlab.android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.characterlab.android.R;
import org.characterlab.android.models.StrengthInfoItem;

public class StrengthDetailsTextCardDialog extends DialogFragment {
    private StrengthInfoItem mItem;

    public StrengthDetailsTextCardDialog(StrengthInfoItem item) {
        mItem = item;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ContextThemeWrapper context = new ContextThemeWrapper(getActivity(),
                android.R.style.Theme_Holo_Light_Dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_strength_detail_card_dialog, null);

        TextView titleTextView = (TextView) view.findViewById(R.id.title_text_view);
        titleTextView.setText(mItem.getTitle());

        TextView pageTextView = (TextView) view.findViewById(R.id.page_text_view);
        pageTextView.setVisibility(View.GONE);

        TextView contentTextView = (TextView) view.findViewById(R.id.content_text_view);
        contentTextView.setText(Html.fromHtml(mItem.getContents()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}
