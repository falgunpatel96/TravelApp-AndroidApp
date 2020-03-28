package com.dal.travelapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.widget.AppCompatEditText;

public class CreditCardEditText extends AppCompatEditText {
    private SparseArray<Pattern> creditCardPatterns = null;
    private final int cardDefaultDrawableResId = R.drawable.creditcard;
    private int cardCurrentDrawableResourceId = 0;
    private Drawable cardCurrentDrawable;

    public CreditCardEditText(Context context) {
        super(context);
        init();
    }

    public CreditCardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreditCardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        if(creditCardPatterns == null)
        {
            creditCardPatterns = new SparseArray<>();

            creditCardPatterns.put(R.drawable.visa, Pattern.compile("^4[0-9]{2,12}(?:[0-9]{3})?$"));
            creditCardPatterns.put(R.drawable.mastercard, Pattern.compile("^5[1-5][0-9]{1,14}$"));
            creditCardPatterns.put(R.drawable.amex, Pattern.compile("^3[47][0-9]{1,13}$"));
        }
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(creditCardPatterns == null)
        {
            init();
        }

        int cardDrawableResId = 0;
        for (int i=0;i<creditCardPatterns.size();i++)
        {
            int key = creditCardPatterns.keyAt(i);
            //get the object by the key
            Pattern p = creditCardPatterns.get(key);
            Matcher m = p.matcher(text);
            if(m.find())
            {
                cardDrawableResId = key;
                break;
            }
        }

        if(cardDrawableResId > 0 && cardDrawableResId != cardCurrentDrawableResourceId)
        {
            cardCurrentDrawableResourceId = cardDrawableResId;
        }
        else if(cardDrawableResId == 0)
        {
            cardCurrentDrawableResourceId = cardDefaultDrawableResId;
        }
        cardCurrentDrawable = getResources().getDrawable(cardCurrentDrawableResourceId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (cardCurrentDrawable == null) {
            return;
        }

        //right offset for showung errors in the edittext
        int rightOffset = 0;
        if (getError() != null && getError().length() > 0)
        {
            rightOffset = (int) getResources().getDisplayMetrics().density * 32;
        }

        int right = getWidth() - getPaddingRight() - rightOffset;

        int top = getPaddingTop();
        int bottom = getHeight() - getPaddingBottom();
        float ratio = (float) cardCurrentDrawable.getIntrinsicWidth()/(float)cardCurrentDrawable.getIntrinsicHeight();

        cardCurrentDrawable.getIntrinsicHeight();

        int left = (int) (right - ((bottom - top) * ratio));
        cardCurrentDrawable.setBounds(left,top,right,bottom);

        cardCurrentDrawable.draw(canvas);

    }
}
