package union.uc.com.rxjava_example.api;

import java.io.File;
import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;
import union.uc.com.rxjava_example.base.APIBaseActivity;
import union.uc.com.rxjava_example.contants.Constants;

/**
 * Created by wangli on 4/12/16.
 */
public class UtilityActivity extends APIBaseActivity {
    private static final String TAG = "UtilityActivity";

    @Override
    protected void onRegisterAction(ActionRegistery registery) {
        registery.add(Constants.Utility.materialize, new Runnable() {
            @Override
            public void run() {
                Observable o1 = Observable.range(1, 3).materialize();
                o1.subscribe(new Action1<Notification<Integer>>() {
                    @Override
                    public void call(Notification<Integer> integerNotification) {
                        log(TAG, "******");
                        log(TAG, "kind:" + integerNotification.getKind());
                        log(TAG, "value:" + integerNotification.getValue());
                    }
                });
                o1.dematerialize().subscribe(new Action1() {
                    @Override
                    public void call(Object o) {
                        log(TAG, o.toString());
                    }
                });
            }
        });
        registery.add(Constants.Utility.timestamp, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2).timestamp().subscribe(new Action1<Timestamped<Integer>>() {
                    @Override
                    public void call(Timestamped<Integer> integerTimestamped) {
                        log(TAG, "" + integerTimestamped.getValue() + " " + integerTimestamped.getTimestampMillis());
                    }
                });
            }
        });
        registery.add(Constants.Utility.serialize, new Runnable() {
            @Override
            public void run() {
                Observable<Integer> o = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 3; i++) {
                            subscriber.onNext(i);
                            sleep(TAG, 1000);
                        }
                        subscriber.onError(new Exception("xx"));
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread());
                o.observeOn(Schedulers.computation()).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, "no serialize1 on compute:" + integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log(TAG, "Exception no serialize1 on compute:" + throwable.getMessage());
                    }
                });
                o.observeOn(Schedulers.io()).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, "no serialize1 on io:" + integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log(TAG, "Exception no serialize1 on io:" + throwable.getMessage());
                    }
                });
                o.serialize().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, "serialize:" + integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log(TAG, "Exception serialize1:" + throwable.getMessage());
                    }
                });
            }
        });
        registery.add(Constants.Utility.cache, new Runnable() {
            @Override
            public void run() {
                Observable o = Observable.range(1, 10).cache();
                o.subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, "s1:" + integer);
                    }
                });

                o.subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, "s2:" + integer);
                    }
                });
            }
        });
        registery.add(Constants.Utility.observeOn, new Runnable() {
            @Override
            public void run() {
                Observable.range(1, 2).observeOn(Schedulers.io()).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, "" + integer + " on " + Thread.currentThread().getName());
                    }
                });
            }
        });
        registery.add(Constants.Utility.subscribeOn, new Runnable() {
            @Override
            public void run() {
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(0);
                        subscriber.onNext(1);
                        subscriber.onCompleted();
                        log(TAG, "here in: " + Thread.currentThread().getName());
                    }
                })
                        .subscribeOn(Schedulers.computation())
                        .observeOn(Schedulers.io())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log(TAG, "" + integer + " on " + Thread.currentThread().getName());
                            }
                        });
            }
        });
        registery.add(Constants.Utility.doOnEach, new Runnable() {
            @Override
            public void run() {
                Observable.range(1, 10).doOnEach(new Action1<Notification<? super Integer>>() {
                    @Override
                    public void call(Notification<? super Integer> notification) {
                        log(TAG, "doOnEach:" + notification.getKind() + " " + notification.getValue());
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });
        registery.add(Constants.Utility.doOnCompleted, new Runnable() {
            @Override
            public void run() {
                Observable.range(1, 3).doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        log(TAG, "onCompleted");
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });
        registery.add(Constants.Utility.doOnError, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, "3").cast(Integer.class).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log(TAG, "doOnError:" + throwable.getMessage());
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log(TAG, throwable);
                    }
                });
            }
        });
        registery.add(Constants.Utility.doOnTerminate, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2).doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        log(TAG, "OnTerminate");
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });
        registery.add(Constants.Utility.doOnSubscribe, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        log(TAG, "OnSubscribe");
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });
        registery.add(Constants.Utility.doOnUnsubscribe, new Runnable() {
            @Override
            public void run() {
                Subscription subscription = Observable.just(1, 2).doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        log(TAG, "OnUnSubscribe");
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
                subscription.unsubscribe();
            }
        });
        registery.add(Constants.Utility.finallyDo, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2).finallyDo(new Action0() {
                    @Override
                    public void call() {
                        log(TAG, "finallyDo");
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });
        registery.add(Constants.Utility.delay, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2).delay(2, TimeUnit.SECONDS).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });
        registery.add(Constants.Utility.delaySubscription, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2)
                        .delaySubscription(2, TimeUnit.SECONDS)
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log(TAG, integer);
                            }
                        });
            }
        });
        registery.add(Constants.Utility.timeInterval, new Runnable() {
            @Override
            public void run() {
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(1);
                        sleep(TAG, 1000);
                        subscriber.onNext(2);
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.newThread())
                        .timeInterval()
                        .subscribe(new Action1<TimeInterval<Integer>>() {
                            @Override
                            public void call(TimeInterval<Integer> integerTimeInterval) {
                                log(TAG, "" + integerTimeInterval.getValue() + " " +
                                        integerTimeInterval.getIntervalInMilliseconds());
                            }
                        });
            }
        });

        registery.add(Constants.Utility.using, new Runnable() {
            @Override
            public void run() {
                Observable.using(new Func0<File>() {
                    @Override
                    public File call() {
                        File f = null;
                        try {
                            f = new File("ABC");
                            if (!f.exists()) {
                                f.createNewFile();
                            }

                        } catch (Exception e) {
                            Observable.error(e);
                        }
                        return f;
                    }
                }, new Func1<File, Observable<String>>() {
                    @Override
                    public Observable<String> call(File file) {
                        return Observable.just(file.exists() ? "exist" : "not exist");
                    }
                }, new Action1<File>() {
                    @Override
                    public void call(File file) {
                        if (file != null && file.exists()) {
                            file.delete();
                        }
                    }
                }).subscribe(new Action1<String>() {
                    @Override
                    public void call(String exist) {
                        log(TAG, exist);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log(TAG, throwable);
                    }
                });
            }
        });
        registery.add(Constants.Utility.single, new Runnable() {
            @Override
            public void run() {
                Observable.just(1).single().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });
        registery.add(Constants.Utility.singleOrDefault, new Runnable() {
            @Override
            public void run() {
                Observable.<Integer>empty().singleOrDefault(10).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });
    }
}
