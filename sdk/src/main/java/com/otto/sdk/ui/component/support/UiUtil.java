package com.otto.sdk.ui.component.support;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import java.text.DecimalFormat;
import java.util.List;

public class UiUtil {

    public static int getPositionFromArray(String value, List<String> items) {
        int i = 0;
        for (String item : items) {
            if (item.equals(value)) return i;
            i++;
        }
        return 0;
    }

    public static String formatMoneyIDR(long number) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String result = "Rp " + formatter.format(number).replaceAll(",", ".");

        return result;
    }

    public static int getLocationScreenX(View view) {
        int screenX = 0;
        if ((view.getMeasuredWidth() / 2) < view.getX()) {
            screenX = (int) view.getX() - 5;
        } else {
            screenX = (int) view.getX() + 5;
        }

        return screenX;
    }


    public static class AccordionCallback {
        public void onExpandListener() {

        }

        public void onCollapseListener() {

        }
    }


    public static void accordionView(View viewButton, final View viewContent, boolean defaultExpand, final AccordionCallback callback) {
        int contentHeight = ((LinearLayout.LayoutParams) viewContent.getLayoutParams()).height;
        accordionView(viewButton, viewContent, contentHeight, defaultExpand, callback);
    }

    public static void accordionView(View viewButton, final View viewContent, final int contentHeight, boolean defaultExpand, final AccordionCallback callback) {

        if (defaultExpand) {
            expandAccordion(viewContent, contentHeight, callback);
        } else {
            collapseAccordion(viewContent, contentHeight, callback);
        }

        viewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean expand = viewContent.getVisibility() == View.GONE;
                if (expand) {
                    expandAccordion(viewContent, contentHeight, callback);
                } else {
                    collapseAccordion(viewContent, contentHeight, callback);
                }
            }
        });


    }

    public static void collapseAccordion(final View content, final int contentHeight, final AccordionCallback callback) {

        content.post(new Runnable() {
            @Override
            public void run() {
                int initialHeight = contentHeight;//content.getMeasuredHeight();

                ValueAnimator valueAnimator = ValueAnimator.ofInt(initialHeight, 0);
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        content.getLayoutParams().height = (int) animation.getAnimatedValue();
                        content.requestLayout();
                        if ((int) animation.getAnimatedValue() == 0)
                            content.setTag(content.getMeasuredHeight());
                        content.setVisibility(View.GONE);


                        if (callback != null) callback.onCollapseListener();
                    }
                });
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.setDuration(500);
                valueAnimator.start();
            }
        });


    }

    public static void expandAccordion(final View content, final int contentHeight, final AccordionCallback callback) {

        content.post(new Runnable() {
            @Override
            public void run() {

                int targetHeight = contentHeight;//content.getMeasuredHeight();
                content.setVisibility(View.VISIBLE);
                ValueAnimator valueAnimator = ValueAnimator.ofInt(targetHeight);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        content.getLayoutParams().height = (int) animation.getAnimatedValue();
                        content.requestLayout();
                        if (callback != null) callback.onExpandListener();
                    }
                });
                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.setDuration(500);
                valueAnimator.start();
            }
        });


    }
}
