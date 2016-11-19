package union.uc.com.rxjava_example.sample;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;
import union.uc.com.rxjava_example.base.BaseActivity;
import union.uc.com.rxjava_example.ui.LogUtils;

/**
 * Created by wangli on 4/8/16.
 */
public class AndyTestActivity extends BaseActivity {
    private static final String TAG = "AndyTestActivity";

    @Override
    protected String myDescription() {
        return "本示例为展示多种API使用方式.";
    }

    @Override
    protected void setupWorkspace(LinearLayout workspace) {
        workspace.setOrientation(LinearLayout.VERTICAL);
        Button btn = new Button(this);
        btn.setText("Start");
        workspace.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRx();
            }
        });
    }

    private void startRx() {

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }

        Observable.from(list)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return String.valueOf(integer);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.contains("1");
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext() called with: s = [" + s + "]");
                    }
                });
    }
}
