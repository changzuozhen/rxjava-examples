package union.uc.com.rxjava_example.api;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import union.uc.com.rxjava_example.base.APIBaseActivity;
import union.uc.com.rxjava_example.base.UIThreadExecutor;
import union.uc.com.rxjava_example.contants.Constants;

/**
 * Created by wangli on 4/12/16.
 */
public class SchedulerActivity extends APIBaseActivity {
    private static final String TAG = "SchedulerActivity";

    @Override
    protected void onRegisterAction(ActionRegistery registery) {
        registery.add(Constants.Scheduler.io, new Runnable() {
            @Override
            public void run() {
                Observable.just("a", "b").observeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        log(TAG, "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(TAG, "error " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        String threadName = Thread.currentThread().getName();
//                        Log.d(TAG, s + " on " + threadName);
                        log(TAG, s + " on " + threadName);
                    }
                });
            }
        });
        registery.add(Constants.Scheduler.compute, new Runnable() {
            @Override
            public void run() {
                Observable.just("a", "b")
                        .observeOn(Schedulers.computation())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                log(TAG, "complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                log(TAG, "error " + e.toString());
                            }

                            @Override
                            public void onNext(String s) {
                                String threadName = Thread.currentThread().getName();
//                        Log.d(TAG, s + " on " + threadName);
                                log(TAG, s + " on " + threadName);
                            }
                        });
            }
        });
        registery.add(Constants.Scheduler.new_thread, new Runnable() {
            @Override
            public void run() {
                Observable.just("a", "b")
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                log(TAG, "complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                log(TAG, "error " + e.toString());
                            }

                            @Override
                            public void onNext(String s) {
                                String threadName = Thread.currentThread().getName();
//                        Log.d(TAG, s + " on " + threadName);
                                log(TAG, s + " on " + threadName);
                            }
                        });
            }
        });
        registery.add(Constants.Scheduler.trampoline, new Runnable() {
            @Override
            public void run() {
                Observable.just("a", "b")
                        .observeOn(Schedulers.trampoline())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                log(TAG, "complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                log(TAG, "error " + e.toString());
                            }

                            @Override
                            public void onNext(String s) {
                                String threadName = Thread.currentThread().getName();
//                        Log.d(TAG, s + " on " + threadName);
                                log(TAG, s + " on " + threadName);
                            }
                        });
                String threadName = Thread.currentThread().getName();
                log(TAG, "i'm on thread " + threadName);
            }
        });
        registery.add(Constants.Scheduler.immediate, new Runnable() {
            @Override
            public void run() {
                Observable.just("a", "b")
                        .observeOn(Schedulers.immediate())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                log(TAG, "complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                log(TAG, "error " + e.toString());
                            }

                            @Override
                            public void onNext(String s) {
                                String threadName = Thread.currentThread().getName();
//                        Log.d(TAG, s + " on " + threadName);
                                log(TAG, s + " on " + threadName);
                            }
                        });
                String threadName = Thread.currentThread().getName();
                log(TAG, "i'm on thread " + threadName);
            }
        });
        registery.add(Constants.Scheduler.self_define, new Runnable() {
            @Override
            public void run() {
                Observable.just("a", "b")
                        .observeOn(Schedulers.from(UIThreadExecutor.SINGLETON))
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                log(TAG, "complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                log(TAG, "error " + e.toString());
                            }

                            @Override
                            public void onNext(String s) {
                                String threadName = Thread.currentThread().getName();
//                        Log.d(TAG, s + " on " + threadName);
                                log(TAG, s + " on " + threadName);
                            }
                        });
            }
        });

    }
}
