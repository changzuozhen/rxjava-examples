package union.uc.com.rxjava_example.base;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Created by wangli on 4/5/16.
 */
public class UIThreadExecutor implements Executor {
    public static UIThreadExecutor SINGLETON = new UIThreadExecutor();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }
}
