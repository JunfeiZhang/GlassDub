package edu.washington.glassdub.glassdub;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.util.AttributeSet;

/**
 * Created by Diana on 3/4/2017.
 */

public class CustomEditText extends TextInputEditText {

    Drawable icon = (Drawable) ResourcesCompat.getDrawable(getResources(),
            R.drawable.ic_error_outline_24dp, null);

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        setCompoundDrawables(null, null, icon, null);
    }
}
