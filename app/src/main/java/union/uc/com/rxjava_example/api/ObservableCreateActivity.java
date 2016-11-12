package union.uc.com.rxjava_example.api;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import union.uc.com.rxjava_example.base.APIBaseActivity;
import union.uc.com.rxjava_example.base.AsyncExecutor;
import union.uc.com.rxjava_example.contants.Constants;

/**
 * Created by wangli on 4/12/16.
 */
public class ObservableCreateActivity extends APIBaseActivity {
    private static final String TAG = "ObservableCreateActivity";

    @Override
    protected void onRegisterAction(ActionRegistery registery) {
        registery.add(Constants.ObservableCreate.just, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2, 3).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });
        registery.add(Constants.ObservableCreate.from_future, new Runnable() {
            @Override
            public void run() {
                Observable.from(AsyncExecutor.SINGLETON.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return "I 'm from future of thread " + Thread.currentThread().getName();
                    }
                })).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        log(TAG, s);
                    }
                });
            }
        });
        registery.add(Constants.ObservableCreate.from_iterable, new Runnable() {
            @Override
            public void run() {
                Observable.from(Arrays.asList(new String[]{"s1", "s2", "s3"}))
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                log(TAG, s);
                            }
                        });
            }
        });
        registery.add(Constants.ObservableCreate.repeat, new Runnable() {
                    @Override
                    public void run() {
                        log(TAG, "RxJava not implemented!");
                    }
                }

        );
        registery.add(Constants.ObservableCreate.repeatWhen, new Runnable() {
                    @Override
                    public void run() {
                        log(TAG, "RxJava not implemented!");
                    }
                }

        );
        registery.add(Constants.ObservableCreate.create, new Runnable() {
                    @Override
                    public void run() {
                        Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                subscriber.onNext(1);
                                subscriber.onNext(2);
                                subscriber.onCompleted();
                            }
                        }).subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log(TAG, integer);
                            }
                        });
                    }
                }

        );
        registery.add(Constants.ObservableCreate.defer, new Runnable() {
                    @Override
                    public void run() {
                        Observable<Long> now = Observable.defer(new Func0<Observable<Long>>() {
                            @Override
                            public Observable<Long> call() {
                                return Observable.just(System.currentTimeMillis());
                            }
                        });

                        now.subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                log(TAG, aLong);
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            log(TAG, "exception:" + e.getMessage());
                        }
                        now.subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                log(TAG, aLong);
                            }
                        });
                    }
                }

        );
        registery.add(Constants.ObservableCreate.range, new Runnable() {
                    @Override
                    public void run() {
                        Observable.range(1, 10).subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log(TAG, integer);
                            }
                        });
                    }
                }

        );
        registery.add(Constants.ObservableCreate.interval, new Runnable() {
                    @Override
                    public void run() {
                        final Subscription subscription =
                                Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                                    @Override
                                    public void call(Long aLong) {
                                        log(TAG, aLong);
                                    }
                                });
                        AsyncExecutor.SINGLETON.schedule(new Runnable() {
                            @Override
                            public void run() {
                                if (!subscription.isUnsubscribed()) {
                                    subscription.unsubscribe();
                                }
                            }
                        }, 10, TimeUnit.SECONDS);
                    }
                }

        );
        registery.add(Constants.ObservableCreate.timer, new Runnable() {
                    @Override
                    public void run() {
                        Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                log(TAG, aLong);
                            }
                        });
                    }
                }

        );
        registery.add(Constants.ObservableCreate.empty, new Runnable() {
                    @Override
                    public void run() {
                        Observable.<String>empty().subscribe(new Observer<String>() {
                            @Override
                            public void onNext(String s) {
                                log(TAG, "onNext:" + s);
                            }

                            @Override
                            public void onCompleted() {
                                log(TAG, "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                log(TAG, "onError:" + e.getMessage());
                            }
                        });
                    }
                }

        );
        registery.add(Constants.ObservableCreate.error, new Runnable() {
                    @Override
                    public void run() {
                        Observable.error(new Exception("abc")).subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                log(TAG, "onNext");
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                log(TAG, "onError:" + throwable.getMessage());
                            }
                        }, new Action0() {
                            @Override
                            public void call() {
                                log(TAG, "onComplete");
                            }
                        });
                    }
                }

        );

        registery.add(Constants.ObservableCreate.never, new Runnable() {
                    @Override
                    public void run() {
                        Observable.<Void>never().subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                log(TAG, "it's impossible!");
                            }
                        });
                    }
                }

        );

    }
}
