package com.android.shopping.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.shopping.R;
import com.android.shopping.utils.Logger;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Dialog extends android.app.Dialog {
    private Context context;
    private String message;
    private String title;
    private String yestxt;

    private RelativeLayout dialog_root;
    private RelativeLayout dialog_content;
    private TextView dialog_title;
    private TextView dialog_message;
    private ButtonFlat btn_change;
    private ButtonFlat btn_later;
    private ButtonFlat btn_yes;

    private View.OnClickListener changeListener;
    private View.OnClickListener laterListener;
    private View.OnClickListener yesListener;

    public Dialog(Context context, String title, String message) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.message = message;
        this.title = title;
    }

    public Dialog(Context context, String title, String message, String yestxt) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.message = message;
        this.title = title;
        this.yestxt = yestxt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        initViews();
    }

    private void initViews() {
        dialog_root = (RelativeLayout) this.findViewById(R.id.dialog_root);
        dialog_content = (RelativeLayout) this.findViewById(R.id.dialog_content);
        dialog_title = (TextView) this.findViewById(R.id.dialog_title);
        dialog_message = (TextView) this.findViewById(R.id.dialog_message);
        btn_change = (ButtonFlat) this.findViewById(R.id.btn_change);
        btn_later = (ButtonFlat) this.findViewById(R.id.btn_later);
        btn_yes = (ButtonFlat) this.findViewById(R.id.btn_yes);

        setTitle(title);
        setMessage(message);
        setYesTxt(yestxt);

        dialog_root.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX() < dialog_content.getLeft()
                        || event.getX() > dialog_content.getRight()
                        || event.getY() > dialog_content.getBottom()
                        || event.getY() < dialog_content.getTop()) {
                    dismiss();
                }
                return false;
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (changeListener != null) {
                    changeListener.onClick(view);
                }
            }
        });

        btn_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (laterListener != null) {
                    laterListener.onClick(view);
                }
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (yesListener != null) {
                    yesListener.onClick(view);
                }
            }
        });
    }

    public void setTitle(String title) {
        this.title = title;
        dialog_title.setText(title);
    }

    public void setMessage(String message) {
        this.message = message;
        dialog_message.setText(message);
    }

    public void setOnClickChange(View.OnClickListener changeListener) {
        this.changeListener = changeListener;
        if (btn_change != null) {
            btn_change.setOnClickListener(changeListener);
        }
    }

    public void setYesTxt(String txt) {
        this.yestxt = txt;
        if (!TextUtils.isEmpty(txt)) {
            btn_yes.setText(txt);
        }
    }

    public void setOnClickLater(View.OnClickListener laterListener) {
        this.laterListener = laterListener;
        if (btn_later != null) {
            btn_later.setOnClickListener(laterListener);
        }
    }

    public void setOnClickYes(View.OnClickListener yesListener) {
        this.yesListener = yesListener;
        if (btn_yes != null) {
            btn_yes.setOnClickListener(yesListener);
        }
    }

    @Override
    public void show() {
        super.show();
        dialog_content.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_show_amin));
        dialog_root.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_root_show_amin));
    }

    @Override
    public void dismiss() {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.dialog_hide_amin);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dialog_content.post(new Runnable() {
                    @Override
                    public void run() {
                        Dialog.super.dismiss();
                    }
                });

            }
        });
        Animation backAnim = AnimationUtils.loadAnimation(context, R.anim.dialog_root_hide_amin);

        dialog_content.startAnimation(anim);
//        dialog_root.startAnimation(backAnim);
    }
}
