package union.uc.com.rxjava_example.api;


import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
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
                Integer i = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(1);
                        subscriber.onNext(2);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().first();
                log(TAG, i);
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
        registery.add(Constants.BlockingObservable.mostRecent, new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> itr = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(1);
                        subscriber.onNext(2);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().mostRecent(5000).iterator();
                while (itr.hasNext()) {
                    log(TAG, itr.next());
                }
            }
        });
        registery.add(Constants.BlockingObservable.next, new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> itr = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(1);
                        subscriber.onNext(2);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().next().iterator();
                while (itr.hasNext()) {
                    log(TAG, itr.next());
                }
            }
        });
        registery.add(Constants.BlockingObservable.latest, new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> itr = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(1);
                        subscriber.onNext(2);
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
        registery.add(Constants.BlockingObservable.toFuture, new Runnable() {
            @Override
            public void run() {
                Future<Integer> future = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(2);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().toFuture();
                try {
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
        registery.add(Constants.BlockingObservable.toIterable, new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> itr = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(1);
                        subscriber.onNext(2);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.newThread()).toBlocking().toIterable().iterator();
                while (itr.hasNext()) {
                    log(TAG, itr.next());
                }
            }
        });
        registery.add(Constants.BlockingObservable.getIterator, new Runnable() {
            @Override
            public void run() {
                Iterator<Integer> itr = Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(1);
                        subscriber.onNext(2);
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
