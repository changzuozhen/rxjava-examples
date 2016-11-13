package union.uc.com.rxjava_example.api;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.joins.Plan0;
import rx.observables.JoinObservable;
import rx.schedulers.Schedulers;
import union.uc.com.rxjava_example.base.APIBaseActivity;
import union.uc.com.rxjava_example.contants.Constants;

/**
 * Created by wangli on 4/12/16.
 */
public class CombineActivity extends APIBaseActivity {
    private static final String TAG = "CombineActivity";

    @Override
    protected void onRegisterAction(ActionRegistery registery) {
        registery.add(Constants.Combine.startWith, new Runnable() {
            @Override
            public void run() {
                logNotImplemented(TAG);
            }
        });

        // 将多个Observable合并为一个
        registery.add(Constants.Combine.merge, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2, 3)
                        .mergeWith(Observable.just(4, 5, 6))
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log(TAG, integer);
                            }
                        });
            }
        });
        registery.add(Constants.Combine.mergeDelayError, new Runnable() {
            @Override
            public void run() {
                logNotImplemented(TAG);
            }
        });

        // 使用一个函数组合多个Observable发射的数据集合，然后再发射这个结果
        registery.add(Constants.Combine.zip, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2, 3)
                        .zipWith(Observable.just("a", "b", "c"), new Func2<Integer, String, String>() {
                            @Override
                            public String call(Integer integer, String s) {
                                return s + integer * 2;
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                log(TAG, s);
                            }
                        });
            }
        });

        // 通过模式和计划组合多个Observables发射的数据集合
        registery.add(Constants.Combine.and_then_when, new Runnable() {
            @Override
            public void run() {
                Plan0<String> p = //
                        JoinObservable.from(Observable.just(1, 2, 3))//
                                .and(Observable.just("a", "b", "c"))//
                                .and(Observable.just("d", "e"))//
                                .then(new Func3<Integer, String, String, String>() {
                                    @Override
                                    public String call(Integer integer, String s, String s2) {
                                        String ret = "" + integer + " " + s + " " + s2;
                                        log(TAG, "then() called with: integer = [" + integer + "], s = [" + s + "], s2 = [" + s2 + "]");
                                        return ret;
                                    }
                                });
                JoinObservable.when(p).toObservable().subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        log(TAG, s);
                    }
                });
            }
        });

        // 当两个Observables中的任何一个发射了一个数据时，通过一个指定的函数组合每个Observable发射的最新数据（一共两个数据），然后发射这个函数的结果
        registery.add(Constants.Combine.combineLatest, new Runnable() {
            @Override
            public void run() {
                Observable
                        .combineLatest(
                                Observable.create(new Observable.OnSubscribe<Integer>() {
                                    @Override
                                    public void call(Subscriber<? super Integer> subscriber) {
                                        for (int i = 0; i < 10; ++i) {
                                            sleep(TAG, 501);
                                            subscriber.onNext(i);
                                            sleep(TAG, 502);
                                        }
                                    }
                                }).subscribeOn(Schedulers.newThread()),

                                Observable.create(new Observable.OnSubscribe<String>() {
                                    @Override
                                    public void call(Subscriber<? super String> subscriber) {
                                        final String[] arr = new String[]{"a", "b", "c"};
                                        for (int i = 0; i < arr.length; ++i) {
                                            sleep(TAG, 201);
                                            subscriber.onNext(arr[i]);
                                            sleep(TAG, 202);
                                        }
                                    }
                                }).subscribeOn(Schedulers.newThread()),

                                new Func2<Integer, String, String>() {
                                    @Override
                                    public String call(Integer integer, String s) {
                                        return "" + integer + " " + s;
                                    }
                                }
                        )
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                log(TAG, s);
                            }
                        });
            }
        });

        //无论何时，如果一个Observable发射了一个数据项，只要在另一个Observable发射的数据项定义的时间窗口内，就将两个Observable发射的数据合并发射
        registery.add(Constants.Combine.join, new Runnable() {
                    @Override
                    public void run() {
                        Observable.create(
                                new Observable.OnSubscribe<Integer>() {
                                    @Override
                                    public void call(Subscriber<? super Integer> subscriber) {
                                        for (int i = 0; i < 10; ++i) {
                                            subscriber.onNext(i);
                                            sleep(TAG, 1000);
                                        }
                                    }
                                }).subscribeOn(Schedulers.newThread())
                                .join(
                                        Observable.create(new Observable.OnSubscribe<String>() {
                                            @Override
                                            public void call(Subscriber<? super String> subscriber) {
                                                final String[] arr = new String[]{"a", "b", "c"};
                                                for (int i = 0; i < arr.length; ++i) {
                                                    subscriber.onNext(arr[i]);
                                                    sleep(TAG, 400);
                                                }
                                            }
                                        }).subscribeOn(Schedulers.newThread()),
                                        new Func1<Integer, Observable<Long>>() {
                                            @Override
                                            public Observable<Long> call(Integer integer) {
                                                return Observable.timer(1, TimeUnit.SECONDS);
                                            }
                                        },
                                        new Func1<String, Observable<Long>>() {
                                            @Override
                                            public Observable<Long> call(String s) {
                                                return Observable.timer(2, TimeUnit.SECONDS);
                                            }
                                        },
                                        new Func2<Integer, String, String>() {
                                            @Override
                                            public String call(Integer integer, String s) {
                                                return " " + integer + " " + s;
                                            }
                                        }
                                )
                                .subscribe(new Action1<String>() {
                                               @Override
                                               public void call(String s) {
                                                   log(TAG, s);
                                               }
                                           }
                                );
                    }
                }

        );

        // 无论何时，如果一个Observable发射了一个数据项，只要在另一个Observable发射的数据项定义的时间窗口内，就将两个Observable发射的数据合并发射
        registery.add(Constants.Combine.groupjoin, new Runnable() {
            @Override
            public void run() {
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; ++i) {
                            subscriber.onNext(i);
                            sleep(TAG, 1000);
                        }
                    }
                }).subscribeOn(Schedulers.newThread())
                        .groupJoin(Observable.create(new Observable.OnSubscribe<String>() {
                                    @Override
                                    public void call(Subscriber<? super String> subscriber) {
                                        final String[] arr = new String[]{"a", "b", "c"};
                                        for (int i = 0; i < arr.length; ++i) {
                                            subscriber.onNext(arr[i]);
                                            sleep(TAG, 400);
                                        }
                                    }
                                }).subscribeOn(Schedulers.newThread()),
                                new Func1<Integer, Observable<Long>>() {
                                    @Override
                                    public Observable<Long> call(Integer integer) {
                                        return Observable.timer(1, TimeUnit.SECONDS);
                                    }
                                },
                                new Func1<String, Observable<Long>>() {
                                    @Override
                                    public Observable<Long> call(String s) {
                                        return Observable.timer(2, TimeUnit.SECONDS);
                                    }
                                },
                                new Func2<Integer, Observable<String>, Observable<String>>() {
                                    @Override
                                    public Observable<String> call(final Integer integer,
                                                                   Observable<String> stringObservable) {
                                        return stringObservable.map(new Func1<String, String>() {
                                            @Override
                                            public String call(String s) {
                                                return " " + integer + s;
                                            }
                                        });
                                    }
                                })
                        .subscribe(new Action1<Observable<String>>() {
                            @Override
                            public void call(Observable<String> o) {
                                o.subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String s) {
                                        log(TAG, s);
                                    }
                                });
                            }
                        });
            }
        });
        registery.add(Constants.Combine.switchIfEmpty, new Runnable() {
                    @Override
                    public void run() {
                        Observable.<Integer>empty().switchIfEmpty(Observable.just(4, 5))
                                .subscribe(new Action1<Integer>() {
                                    @Override
                                    public void call(Integer integer) {
                                        log(TAG, integer);
                                    }
                                });
                    }
                }

        );

        registery.add(Constants.Combine.switchOnNext, new Runnable() {
                    @Override
                    public void run() {
                        logNotImplemented(TAG);
                    }
                }

        );
    }
}
