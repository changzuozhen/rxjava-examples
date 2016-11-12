package union.uc.com.rxjava_example.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.internal.util.SubscriptionList;
import rx.schedulers.Schedulers;
import union.uc.com.rxjava_example.R;
import union.uc.com.rxjava_example.plugin.DisplayPluginManager;
import union.uc.com.rxjava_example.ui.SeperatorView;

/**
 * Created by wangli on 4/12/16.
 */
public abstract class APIBaseActivity extends Activity {
    private TextView mLog;
    private LinearLayout mTop;
    private ActionAdapter mActionAdapter = new ActionAdapter();
    private SubscriptionList mLastSubscriptionList = new SubscriptionList();

    public static void printStackTrace(String tag) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stackTraceElements.length; i++) {
            Log.println(Log.INFO, tag, stackTraceElements[i].toString());
        }
    }

    /**
     * 制作打log位置的文件名与文件行号详细信息
     *
     * @param log
     * @param stackTraceElements
     * @return
     */
    private static String build(String log, StackTraceElement... stackTraceElements) {
        StringBuilder buf = new StringBuilder();

        buf.append("[").append(Thread.currentThread().getId()).append("]");
        StackTraceElement stackTraceElement;
        for (int i = 0; i < stackTraceElements.length; i++) {
            stackTraceElement = stackTraceElements[i];
            if (stackTraceElements.length > 1) buf.append("\ni:" + i + "  ");
            if (stackTraceElement.isNativeMethod()) {
                buf.append("(Native Method)");
            } else {
                String fName = stackTraceElement.getFileName();

                if (fName == null) {
                    buf.append("(Unknown Source)");
                } else {
                    int lineNum = stackTraceElement.getLineNumber();
                    buf.append('(');
                    buf.append(fName);
                    if (lineNum >= 0) {
                        buf.append(':');
                        buf.append(lineNum);
                    }
                    buf.append("):");
                }
            }
            buf.append(log);
        }
        return buf.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setupUIComponents();
    }

    private void setupUIComponents() {
        //top
        mTop = new LinearLayout(this);
        mTop.setOrientation(LinearLayout.VERTICAL);
        //bottom
        LinearLayout bottom = new LinearLayout(this);
        mLog = new TextView(this);
        mLog.setSingleLine(false);
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(mLog);
        bottom.addView(scrollView,
                new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));
        ListView actionList = new ListView(this);
        onRegisterAction(mActionAdapter);
        actionList.setAdapter(mActionAdapter);
        bottom.addView(new SeperatorView(this, SeperatorView.MODE_VERTICAL));
        bottom.addView(actionList,
                new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1));

        //container
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        ScrollView topScrollView = new ScrollView(this);
        topScrollView.addView(mTop);
        topScrollView.setBackgroundColor(Color.WHITE);
        container.addView(topScrollView,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 3));
        container.addView(new SeperatorView(this, SeperatorView.MODE_HORIZENTAL));
        container.addView(bottom,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 2));
        setContentView(container);
    }

    protected void logNotImplemented(String tag) {
        log("RxJava not implement!", tag, 3);
    }

    protected void logUseObservable(String tag) {
        log("Use Observable!", tag, 3);
    }

    protected <T> void log(T value, String tag) {
        log("" + value, tag, 3);
    }

    protected void log(Throwable throwable, String tag) {
        log("error:" + throwable.getMessage(), tag, 3);
    }

    protected void logLineSeperator(String tag) {
        log("------------------", tag, 3);
    }

    protected void log(final String tipLine, String tag) {
        log(tipLine, tag, 3);
    }

    protected void log(final String tipLine, String tag, int deepth) {
        //ensure log on ui thread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLog.setText(mLog.getText().toString() + "\r\n" + tipLine);
            }
        });
        StackTraceElement ste = new Throwable().getStackTrace()[deepth];
//        StackTraceElement[] ste = new Throwable().getStackTrace();
        String log = build(tipLine, ste);
        Log.d(tag, log);
    }

    protected void clearLog() {
        //ensure log on ui thread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLog.setText("");
            }
        });
    }

    protected void sleep(int millsecond, String tag) {
        try {
            Thread.sleep(millsecond);
        } catch (Exception e) {
            log(e.getMessage(), tag, 3);
        }
    }

    private void refreshDispalyArea(String key) {
        mTop.removeAllViews();
        List<DisplayPluginManager.Plugin> plugins = DisplayPluginManager.singleton().getAll();
        for (int i = 0; i < plugins.size(); ++i) {
            final int index = i;
            DisplayPluginManager.Plugin plugin = plugins.get(index);
            Tuple.Tuple2<Observable<View>, View> t = plugin.getView(this, key);
            Observable<View> o = t.item1;
            Subscription s = o.observeOn(Schedulers.from(UIThreadExecutor.SINGLETON)).
                    subscribe(new Action1<View>() {
                        @Override
                        public void call(View view) {
                            view.setVisibility(View.VISIBLE);

                            //            for (int i = 0; i < mTop.getChildCount(); ++i) {
                            //              View v = mTop.getChildAt(i);
                            //              v.clearAnimation();
                            //              log(v.getHeight());
                            //              if (v.equals(view)) {//in
                            //                //YoYo.with(Techniques.ZoomInDown).duration(2000).playOn(v);
                            //                TranslateAnimation t = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                            //                                                              0,
                            //                                                              Animation.RELATIVE_TO_SELF,
                            //                                                              0,
                            //                                                              Animation.RELATIVE_TO_SELF,
                            //                                                              -view.getHeight(),
                            //                                                              Animation.RELATIVE_TO_SELF,
                            //                                                              0);
                            //                t.setDuration(2000);
                            //                v.startAnimation(t);
                            //              } else {//out
                            //                //  YoYo.with(Techniques.SlideInDown).duration(2000).playOn(v);
                            //                TranslateAnimation t = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                            //                                                              0,
                            //                                                              Animation.RELATIVE_TO_SELF,
                            //                                                              0,
                            //                                                              Animation.RELATIVE_TO_SELF,
                            //                                                              0,
                            //                                                              Animation.RELATIVE_TO_SELF,
                            //                                                              v.getHeight());
                            //                t.setDuration(2000);
                            //                v.startAnimation(t);
                            //              }
                            //            }

                        }
                    });

            View v = t.item2;
            if (v != null) {
                v.setVisibility(View.GONE);
                mTop.addView(v);
            }

            mLastSubscriptionList.add(s);
        }
    }

    protected abstract void onRegisterAction(ActionRegistery registery);

    protected interface ActionRegistery {
        void add(String name, Runnable action);

        void add(String name, String code, Runnable action);
    }

    private class ActionItem {
        public String key;
        public String code;
        public Runnable action;
    }

    private class ActionAdapter extends BaseAdapter implements ActionRegistery {
        private List<ActionItem> mActions = new ArrayList<>();

        @Override
        public void add(String name, Runnable action) {
            add(name, "", action);
        }

        //from ActionRegistery
        @Override
        public void add(String key, String code, Runnable action) {
            ActionItem item = new ActionItem();
            item.key = key;
            item.code = code;
            item.action = action;
            mActions.add(item);
        }

        //from BaseAdapter
        @Override
        public int getCount() {
            return mActions.size();
        }

        @Override
        public Object getItem(int position) {
            return mActions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView != null) {
                tv = (TextView) convertView;
            } else {
                tv = new TextView(parent.getContext());
            }
            final ActionItem item = (ActionItem) getItem(position);
            tv.setPadding(20, 20, 20, 10);
            tv.setText(item.key);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearLog();
                    refreshDispalyArea(item.key);
                    item.action.run();
                    String title = item.key + getResources().getString(R.string.sample);
                }
            });

            return tv;
        }
    }
}
