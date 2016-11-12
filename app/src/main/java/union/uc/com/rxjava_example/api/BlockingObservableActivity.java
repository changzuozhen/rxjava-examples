package union.uc.com.rxjava_example.api;


import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import union.uc.com.rxjava_example.base.APIBaseActivity;
import union.uc.com.rxjava_example.contants.Constants;

/**
 * Created by wangli on 4/12/16.
 */
public class BlockingObservableActivity extends APIBaseActivity {

    private static final String TAG = "BlockingObservableActivity";

    @Override
    protected void onRegisterAction(ActionRegistery registery) {
        registery.add(Constants.BlockingObservable.forEach, new Runnable() {
            @Override
            public void run() {
                // 对Observable发射的每一项数据调用一个方法，会阻塞直到Observable完成
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(1);
                        subscriber.onNext(2);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().forEach(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(TAG, integer);
                        sleep(TAG, 500);
                    }
                });
            }
        });
        registery.add(Constants.BlockingObservable.first, new Runnable() {
            @Override
            public void run() {
                // 阻塞直到Observable发射了一个数据，然后返回第一项数据
                Integer i = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        sleep(TAG, 1000);
                        subscriber.onNext(1);
                        sleep(TAG, 2000);
                        subscriber.onNext(2);
                        sleep(TAG, 2100);
                        subscriber.onNext(3);
                        sleep(TAG, 2200);
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .map(new Func1<Integer, Integer>() {
                            @Override
                            public Integer call(Integer integer) {
                                log(TAG, "map:" + integer);
                                return integer;
                            }
                        })
                        .toBlocking()
                        .first();
                log(TAG, "first:" + i);
            }
        });
        registery.add(Constants.BlockingObservable.firstOrDefault, new Runnable() {
            @Override
            public void run() {
                Integer i = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().firstOrDefault(5000);
                log(TAG, i);
            }
        });
        registery.add(Constants.BlockingObservable.last, new Runnable() {
            @Override
            public void run() {
                Integer i = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(1);
                        subscriber.onNext(2);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().last();
                log(TAG, i);
            }
        });
        registery.add(Constants.BlockingObservable.lastOrDefault, new Runnable() {
            @Override
            public void run() {
                Integer i = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().lastOrDefault(5000);
                log(TAG, i);
            }
        });

        // 返回一个总是返回Observable最近发射的数据的iterable
        registery.add(Constants.BlockingObservable.mostRecent, new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> itr = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        sleep(TAG, 10);
                        subscriber.onNext(1);
                        sleep(TAG, 10);
                        subscriber.onNext(2);
                        sleep(TAG, 10);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().mostRecent(5000).iterator();
                while (itr.hasNext()) {
                    log(TAG, itr.next());
                }
            }
        });

        // 返回一个Iterable，会阻塞直到Observable发射了另一个值，然后返回那个值
        registery.add(Constants.BlockingObservable.next, new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> itr = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        sleep(TAG, 500);
                        subscriber.onNext(1);
                        sleep(TAG, 500);
                        subscriber.onNext(2);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().next().iterator();
                while (itr.hasNext()) {
                    log(TAG, itr.next());
                }
            }
        });

        // 返回一个iterable，会阻塞直到或者除非Observable发射了一个iterable没有返回的值，然后返回这个值
        registery.add(Constants.BlockingObservable.latest, new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> itr = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        sleep(TAG, 50);
                        subscriber.onNext(0);
                        subscriber.onNext(1);
                        sleep(TAG, 50);
                        subscriber.onNext(2);
                        subscriber.onNext(3);
                        sleep(TAG, 50);
                        subscriber.onNext(4);
                        subscriber.onNext(5);
                        sleep(TAG, 50);
                        subscriber.onNext(6);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().latest().iterator();
                while (itr.hasNext()) {
                    log(TAG, itr.next());
                }
            }
        });
        registery.add(Constants.BlockingObservable.single, new Runnable() {
            @Override
            public void run() {
                Integer i = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(1);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().single();
                log(TAG, i);
            }
        });
        registery.add(Constants.BlockingObservable.singleOrDefault, new Runnable() {
            @Override
            public void run() {
                Integer i = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().singleOrDefault(3000);
                log(TAG, i);
            }
        });

        // 将Observable转换为一个Future
        registery.add(Constants.BlockingObservable.toFuture, new Runnable() {
            @Override
            public void run() {
                Future<Integer> future = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        sleep(TAG, 500);
                        subscriber.onNext(2);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().toFuture();
                try {
                    log(TAG, "future created get()");
                    log(TAG, future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log(TAG, e);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    log(TAG, e);
                }
            }
        });

        // 将一个发射数据序列的Observable转换为一个Iterable
        registery.add(Constants.BlockingObservable.toIterable, new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> itr = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        sleep(TAG, 500);
                        subscriber.onNext(1);
                        sleep(TAG, 500);
                        subscriber.onNext(2);
                        sleep(TAG, 500);
                        subscriber.onNext(3);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().toIterable().iterator();
                while (itr.hasNext()) {
                    log(TAG, itr.next());
                }
            }
        });

        // 将一个发射数据序列的Observable转换为一个Iterator
        registery.add(Constants.BlockingObservable.getIterator, new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> itr = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        sleep(TAG, 500);
                        subscriber.onNext(1);
                        sleep(TAG, 500);
                        subscriber.onNext(2);
                        sleep(TAG, 500);
                        subscriber.onNext(3);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().getIterator();
                while (itr.hasNext()) {
                    log(TAG, itr.next());
                }
            }
        });
    }
}
