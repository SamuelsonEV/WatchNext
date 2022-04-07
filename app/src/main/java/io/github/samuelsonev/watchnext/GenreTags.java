package io.github.samuelsonev.watchnext;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.flexbox.FlexboxLayout;

public class GenreTags {
    static public void setTaglistOnLayout(Context context, String[] genreArray, FlexboxLayout genrePlace){
        // Iterate the list of genres addind each one to one TextView under a RelativeView

        for (String s : genreArray) {
            RelativeLayout relativeLayout = new RelativeLayout(context);
            RelativeLayout.LayoutParams relparams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            relparams.setMargins(3, 3, 3, 3);
            relativeLayout.setLayoutParams(relparams);
            relativeLayout.setBackgroundResource(R.drawable.card_oval);

            // Creating a new TextView
            TextView tv = new TextView(context);
            RelativeLayout.LayoutParams tvparams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            tvparams.addRule(RelativeLayout.CENTER_IN_PARENT);
            tv.setLayoutParams(tvparams);
            tv.setPadding(12, 0, 12, 0);
            tv.setText(s);
            tv.setTextColor(context.getColor(R.color.textcolor));
            tv.setTypeface(Typeface.DEFAULT_BOLD);

            // Adding
            relativeLayout.addView(tv);
            genrePlace.addView(relativeLayout);
        }

    }
}


