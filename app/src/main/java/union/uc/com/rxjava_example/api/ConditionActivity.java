package union.uc.com.rxjava_example.api;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Statement;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import union.uc.com.rxjava_example.base.APIBaseActivity;
import union.uc.com.rxjava_example.contants.Constants;

/**
 * Created by wangli on 4/12/16.
 */
public class ConditionActivity extends APIBaseActivity {
    private static final String TAG = "ConditionActivity";

    @Override
    protected void onRegisterAction(ActionRegistery registery) {

        //给定多个Observable，只让第一个发射数据的Observable发射全部数据
        registery.add(Constants.Condition.amb, new Runnable() {
            @Override
            public void run() {
                Observable.amb(Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                sleep(TAG, 1000);
                                subscriber.onNext(1);
                                subscriber.onNext(11);
                                subscriber.onCompleted();
                            }
                        }).subscribeOn(Schedulers.newThread()),
                        Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                sleep(TAG, 500);
                                subscriber.onNext(2);
                                subscriber.onNext(22);
                                subscriber.onCompleted();
                            }
                        }).subscribeOn(Schedulers.newThread()),
                        Observable.create(new Observable.OnSubscribe<Integer>() {
                            @Override
                            public void call(Subscriber<? super Integer> subscriber) {
                                sleep(TAG, 300);
                                subscriber.onNext(3);
                                sleep(TAG, 300);
                                subscriber.onNext(33);
                                sleep(TAG, 300);
                                subscriber.onNext(333);
                                subscriber.onCompleted();
                            }
                        }).subscribeOn(Schedulers.newThread())).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });

        // 发射来自原始Observable的数据，如果原始Observable没有发射数据，就发射一个默认数据
        registery.add(Constants.Condition.defaultIfEmpty, new Runnable() {
            @Override
            public void run() {
                Observable.<Integer>empty().defaultIfEmpty(3).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });

        // 如果满足一个条件，发射原始Observable的数据，然后重复发射直到不满足这个条件为止
        registery.add(Constants.Condition.doWhile, new Runnable() {
            @Override
            public void run() {
                Statement.doWhile(Observable.range(1, 10), new Func0<Boolean>() {
                    boolean r = false;

                    @Override
                    public Boolean call() {
                        r = !r;
                        return r;
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });

        // 只有当某个条件为真时才发射原始Observable的数据序列，否则发射一个空的或默认的序列
        registery.add(Constants.Condition.ifThen, new Runnable() {
            @Override
            public void run() {
                Statement.ifThen(new Func0<Boolean>() {
                    boolean r = false;

                    @Override
                    public Boolean call() {
                        r = !r;
                        return r;
                    }
                }, Observable.just(1, 2, 3)).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });

        // 丢弃原始Observable发射的数据，直到第二个Observable发射了一个数据，然后发射原始Observable的剩余数据
        registery.add(Constants.Condition.skipUtil, new Runnable() {
            @Override
            public void run() {
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 0; i < 10; ++i) {
                            sleep(TAG, 200);
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.newThread())
                        .skipUntil(Observable.timer(1, TimeUnit.SECONDS))
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log(TAG, integer);
                            }
                        });
            }
        });

        // 丢弃原始Observable发射的数据，直到一个特定的条件为假，然后发射原始Observable剩余的数据
        registery.add(Constants.Condition.skipWhile, new Runnable() {
            @Override
            public void run() {
                Observable.range(1, 10).skipWhile(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer i) {
                        return i < 3;
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });

        // 基于一个计算结果，发射一个指定Observable的数据序列
        registery.add(Constants.Condition.switchcase, new Runnable() {
            @Override
            public void run() {
                Observable<Integer> source1 = Observable.just(1, 2, 3);
                Observable<Integer> source2 = Observable.just(4, 5, 6);

                Map<Integer, Observable<Integer>> map = new HashMap<Integer, Observable<Integer>>();
                map.put(1, source1);
                map.put(2, source2);

                Statement.switchCase(new Func0<Integer>() {
                    int count = 1;

                    @Override
                    public Integer call() {
                        return count++;
                    }
                }, map).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });

        // 发射来自原始Observable的数据，直到第二个Observable发射了一个数据或一个通知
        registery.add(Constants.Condition.takeUntil, new Runnable() {
            @Override
            public void run() {
                Observable.range(1, 10).takeUntil(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer i) {
                        return i > 3;
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });


        // 发射原始Observable的数据，直到一个特定的条件为真，然后跳过剩余的数据
        registery.add(Constants.Condition.takeWhile, new Runnable() {
            @Override
            public void run() {
                Observable.range(1, 10).takeWhile(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer i) {
                        return i < 3;
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });


        // 发射原始Observable的数据，直到一个特定的条件为真，然后跳过剩余的数据
        registery.add(Constants.Condition.takeWhileWithIndex, new Runnable() {
            @Override
            public void run() {
                logNotImplemented(TAG);
            }
        });

        // 发射原始Observable的数据序列，然后重复发射这个序列直到不满足这个条件为止
        registery.add(Constants.Condition.WhileDo, new Runnable() {
            @Override
            public void run() {
                Statement.whileDo(Observable.just(1, 2, 3), new Func0<Boolean>() {
                    int count = 2;

                    @Override
                    public Boolean call() {
                        return count-- > 0;
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                    }
                });
            }
        });

// 判断是否所有的数据项都满足某个条件
        registery.add(Constants.Condition.all, new Runnable() {
            @Override
            public void run() {
                Observable.just(2, 4, 6).all(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                }).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        log(TAG, aBoolean);
                    }
                });
            }
        });

        // 判断Observable是否会发射一个指定的值
        registery.add(Constants.Condition.contains, new Runnable() {
            @Override
            public void run() {
                Observable.range(1, 10).contains(3).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        log(TAG, aBoolean);
                    }
                });
            }
        });

        // 判断Observable是否发射了一个值
        registery.add(Constants.Condition.exists, new Runnable() {
            @Override
            public void run() {
                Observable.just(1, 2, 3).exists(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 3;
                    }
                }).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        log(TAG, aBoolean);
                    }
                });
            }
        });

        // 判断Observable是否为空
        registery.add(Constants.Condition.isEmpty, new Runnable() {
            @Override
            public void run() {
                Observable.empty().isEmpty().subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        log(TAG, aBoolean);
                    }
                });
            }
        });

        // 判断两个Observables发射的序列是否相等
        registery.add(Constants.Condition.sequenceEqual, new Runnable() {
            @Override
            public void run() {
                Observable.sequenceEqual(Observable.just(1, 2, 3),
                        Observable.range(1, 3),
                        new Func2<Integer, Integer, Boolean>() {
                            @Override
                            public Boolean call(Integer integer1, Integer integer2) {
                                return integer1 == integer2;
                            }
                        }).subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        log(TAG, aBoolean);
                    }
                });
            }
        });
    }
}
