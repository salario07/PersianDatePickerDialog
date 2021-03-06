package ir.hamsaa.persiandatepicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;


import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;

import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.hamsaa.persiandatepicker.util.PersianHelper;

/**
 * Created by aliabdolahi on 1/23/17.
 */

public class PersianDatePickerDialog {

    public static final int THIS_YEAR = -1;
    public static final int NO_TITLE = 0;
    public static final int DAY_MONTH_YEAR = 1;
    public static final int WEEKDAY_DAY_MONTH_YEAR = 2;

    private Context context;
    private String positiveButtonString = "تایید";
    private Listener listener;
    private int maxYear = 0;
    private int minYear = 0;
    private PersianCalendar initDate = new PersianCalendar();
    private PersianCalendar pCalendar;
    public static Typeface typeFace,buttonTypeFace;
    private String todayButtonString = "امروز";
    private String negativeButtonString = "لغو";
    private boolean todayButtonVisibility = false;
    private int backgroundColor = Color.WHITE;
    private int titleColor = Color.parseColor("#111111");
    private boolean cancelable = true;
    private boolean forceMode;
    private int pickerBackgroundColor;
    private int pickerBackgroundDrawable;
    private int titleType = 0;
    private int buttonCornerRadius;
    private int primaryButtonColor,secondaryButtonColor,primaryRippleColor, secondaryRippleColor;
    private int primaryButtonTextColor,secondaryButtonTextColor;
    private int buttonTextSize;
    private boolean showInBottomSheet;

    public PersianDatePickerDialog(Context context) {
        this.context = context;
    }

    public PersianDatePickerDialog setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public PersianDatePickerDialog setMaxYear(int maxYear) {
        this.maxYear = maxYear;
        return this;
    }

    public PersianDatePickerDialog setButtonCornerRadius(int buttonCornerRadius) {
        this.buttonCornerRadius = buttonCornerRadius;
        return this;
    }

    public PersianDatePickerDialog setButtonColor(int primaryButtonColor,int secondaryButtonColor,int primaryButtonTextColor,int secondaryButtonTextColor,int primaryRippleColor,int secondaryRippleColor) {
        this.primaryButtonColor=primaryButtonColor;
        this.secondaryButtonColor=secondaryButtonColor;
        this.primaryButtonTextColor=primaryButtonTextColor;
        this.secondaryButtonTextColor=secondaryButtonTextColor;
        this.primaryRippleColor=primaryRippleColor;
        this.secondaryRippleColor =secondaryRippleColor;
        return this;
    }

    public PersianDatePickerDialog setTypeFace(Typeface typeFace,Typeface buttonTypeface) {
        this.typeFace = typeFace;
        this.buttonTypeFace=buttonTypeface;
        return this;
    }

    public PersianDatePickerDialog setButtonTextSize(int buttonTextSize) {
        this.buttonTextSize = buttonTextSize;
        return this;
    }

    public PersianDatePickerDialog setMinYear(int minYear) {
        this.minYear = minYear;
        return this;
    }

    public PersianDatePickerDialog setInitDate(PersianCalendar initDate) {
        return setInitDate(initDate, false);
    }

    public PersianDatePickerDialog setInitDate(PersianCalendar initDate, boolean force) {
        this.forceMode = force;
        this.initDate = initDate;
        return this;
    }

    public PersianDatePickerDialog setPositiveButtonString(String positiveButtonString) {
        this.positiveButtonString = positiveButtonString;
        return this;
    }

    public PersianDatePickerDialog setPositiveButtonResource(@StringRes int positiveButton) {
        this.positiveButtonString = context.getString(positiveButton);
        return this;
    }

    public PersianDatePickerDialog setTodayButtonVisible(boolean todayButtonVisiblity) {
        this.todayButtonVisibility = todayButtonVisiblity;
        return this;
    }

    public PersianDatePickerDialog setTodayButton(String todayButton) {
        this.todayButtonString = todayButton;
        return this;
    }

    public PersianDatePickerDialog setNegativeButton(String negativeButton) {
        this.negativeButtonString = negativeButton;
        return this;
    }

    public PersianDatePickerDialog setTodayButtonResource(@StringRes int todayButton) {
        this.todayButtonString = context.getString(todayButton);
        return this;
    }


    public PersianDatePickerDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public PersianDatePickerDialog setBackgroundColor(@ColorInt int bgColor) {
        this.backgroundColor = bgColor;
        return this;
    }

    public PersianDatePickerDialog setTitleColor(@ColorInt int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public PersianDatePickerDialog setPickerBackgroundColor(@ColorInt int color) {
        this.pickerBackgroundColor = color;
        return this;
    }

    public PersianDatePickerDialog setPickerBackgroundDrawable(@DrawableRes int drawableBg) {
        this.pickerBackgroundDrawable = drawableBg;
        return this;
    }

    public PersianDatePickerDialog setTitleType(int titleType) {
        this.titleType = titleType;
        return this;
    }

    public PersianDatePickerDialog setShowInBottomSheet(boolean b) {
        this.showInBottomSheet = b;
        return this;
    }

    public void show() {

        pCalendar = new PersianCalendar();

        View v = View.inflate(context, R.layout.dialog_picker, null);
        final PersianDatePicker datePicker = v.findViewById(R.id.datePicker);
        final TextView dateText = v.findViewById(R.id.dateText);
        final com.google.android.material.button.MaterialButton positiveButton = v.findViewById(R.id.positive_button);
        final com.google.android.material.button.MaterialButton todayButton = v.findViewById(R.id.today_button);
        final com.google.android.material.button.MaterialButton negativeButton = v.findViewById(R.id.negative_button);
        final LinearLayout container = v.findViewById(R.id.container);

        container.setBackgroundColor(backgroundColor);
        dateText.setTextColor(titleColor);


        if (pickerBackgroundColor != 0) {
            datePicker.setBackgroundColor(pickerBackgroundColor);
        } else if (pickerBackgroundDrawable != 0) {
            datePicker.setBackgroundDrawable(pickerBackgroundDrawable);
        }

        if (maxYear > 0) {
            datePicker.setMaxYear(maxYear);
        } else if (maxYear == THIS_YEAR) {
            maxYear = pCalendar.getPersianYear();
            datePicker.setMaxYear(pCalendar.getPersianYear());
        }

        if (minYear > 0) {
            datePicker.setMinYear(minYear);
        } else if (minYear == THIS_YEAR) {
            minYear = pCalendar.getPersianYear();
            datePicker.setMinYear(pCalendar.getPersianYear());
        }

        if (initDate != null) {
            int initYear = initDate.getPersianYear();
            if (initYear > maxYear || initYear < minYear) {
                Log.e("PERSIAN CALENDAR", "init year is more/less than minYear/maxYear");
                if (forceMode) {
                    datePicker.setDisplayPersianDate(initDate);
                }
            } else {
                datePicker.setDisplayPersianDate(initDate);
            }

        }

        if (typeFace != null) {
            dateText.setTypeface(typeFace);
            datePicker.setTypeFace(typeFace);
        }

        if (buttonTypeFace!=null){
            positiveButton.setTypeface(buttonTypeFace);
            todayButton.setTypeface(buttonTypeFace);
            negativeButton.setTypeface(buttonTypeFace);
        }

        positiveButton.setCornerRadius(buttonCornerRadius);
        todayButton.setCornerRadius(buttonCornerRadius);
        negativeButton.setCornerRadius(buttonCornerRadius);

        positiveButton.setBackgroundTintList(ContextCompat.getColorStateList(context,primaryButtonColor));
        todayButton.setBackgroundTintList(ContextCompat.getColorStateList(context,secondaryButtonColor));
        negativeButton.setBackgroundTintList(ContextCompat.getColorStateList(context,secondaryButtonColor));

        positiveButton.setTextColor(ContextCompat.getColorStateList(context,primaryButtonTextColor));
        todayButton.setTextColor(ContextCompat.getColorStateList(context,secondaryButtonTextColor));
        negativeButton.setTextColor(ContextCompat.getColorStateList(context,secondaryButtonTextColor));

        if (primaryRippleColor!=0 && secondaryRippleColor!=0){
            positiveButton.setRippleColor(ContextCompat.getColorStateList(context,primaryRippleColor));
            todayButton.setRippleColor(ContextCompat.getColorStateList(context,secondaryRippleColor));
            negativeButton.setRippleColor(ContextCompat.getColorStateList(context,secondaryRippleColor));
        }

        positiveButton.setText(positiveButtonString);
        todayButton.setText(todayButtonString);
        negativeButton.setText(negativeButtonString);

        if (buttonTextSize!=0){
            positiveButton.setTextSize(buttonTextSize);
            todayButton.setTextSize(buttonTextSize);
            negativeButton.setTextSize(buttonTextSize);
        }

        if (todayButtonVisibility) {
            todayButton.setVisibility(View.VISIBLE);
        }

        pCalendar = datePicker.getDisplayPersianDate();
        updateView(dateText);

        datePicker.setOnDateChangedListener(new PersianDatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(int newYear, int newMonth, int newDay) {
                pCalendar.setPersianDate(newYear, newMonth, newDay);
                updateView(dateText);
            }
        });


        final AppCompatDialog dialog;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && showInBottomSheet) {
            dialog = new BottomSheetDialog(context);
            dialog.setContentView(v);

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            dialog.setCancelable(cancelable);
            dialog.create();
        } else {
            dialog = new AlertDialog.Builder(context)
                    .setView(v)
                    .setCancelable(cancelable)
                    .create();
        }


        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDateSelected(datePicker.getDisplayPersianDate());
                }
                dialog.dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                datePicker.setDisplayDate(new Date());

                if (maxYear > 0) {
                    datePicker.setMaxYear(maxYear);
                }

                if (minYear > 0) {
                    datePicker.setMinYear(minYear);
                }

                pCalendar = datePicker.getDisplayPersianDate();
                dateText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateView(dateText);
                    }
                }, 100);
            }
        });

        dialog.show();
    }

    private void updateView(TextView dateText) {
        String date;
        switch (titleType) {
            case NO_TITLE:
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) dateText.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 30);
                dateText.setLayoutParams(layoutParams);
                break;
            case DAY_MONTH_YEAR:
                date = pCalendar.getPersianDay() + " " +
                        pCalendar.getPersianMonthName() + " " +
                        pCalendar.getPersianYear();

                dateText.setText(PersianHelper.toPersianNumber(date));
                break;
            case WEEKDAY_DAY_MONTH_YEAR:
                date = pCalendar.getPersianWeekDayName() + " " +
                        pCalendar.getPersianDay() + " " +
                        pCalendar.getPersianMonthName() + " " +
                        pCalendar.getPersianYear();

                dateText.setText(PersianHelper.toPersianNumber(date));
                break;
            default:
                Log.d("PersianDatePickerDialog", "never should be here");
                break;
        }

    }

}
