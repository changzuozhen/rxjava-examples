package union.uc.com.rxjava_example.api;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import rx.util.async.Async;
import union.uc.com.rxjava_example.base.APIBaseActivity;
import union.uc.com.rxjava_example.base.AsyncExecutor;
import union.uc.com.rxjava_example.contants.Constants;

/**
 * Created by wangli on 4/12/16.
 */
public class AsyncActivity extends APIBaseActivity {
    private static final String TAG = "AsyncActivity";

    @Override
    protected void onRegisterAction(ActionRegistery registery) {
        registery.add(Constants.Async.start, new Runnable() {
            @Override
            public void run() {
                Async.start(new Func0<Integer>() {
                    @Override
                    public Integer call() {
                        log("action run on " + Thread.currentThread().getName(), TAG);
                        return 3;
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer, TAG);
                    }
                });
            }
        });
        registery.add(Constants.Async.toAsync, new Runnable() {
            @Override
            public void run() {
                Async.<Integer>toAsync(new Action0() {
                    @Override
                    public void call() {
                        log("action run on " + Thread.currentThread().getName(), TAG);
                        log("Action0.call ...on subscribe", TAG);
                    }
                }).call();
                logLineSeperator(TAG);
                Async.<Integer>toAsync(new Action0() {
                    @Override
                    public void call() {
                        log("action run on " + Thread.currentThread().getName(), TAG);
                        log("Action0.call", TAG);
                    }
                }).call().subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        log("Action1.call", TAG);
                    }
                });
            }
        });
        registery.add(Constants.Async.startFuture, new Runnable() {
            @Override
            public void run() {
                Async.startFuture(new Func0<Future<Integer>>() {
                    @Override
                    public Future<Integer> call() {
                        return AsyncExecutor.SINGLETON.submit(new Callable<Integer>() {
                            @Override
                            public Integer call() throws Exception {
                                return 3;
                            }
                        });
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer, TAG);
                    }
                });
            }
        });
        registery.add(Constants.Async.deferFuture, new Runnable() {
            @Override
            public void run() {
                Async.deferFuture(new Func0<Future<? extends Observable<Integer>>>() {
                    @Override
                    public Future<? extends Observable<Integer>> call() {
                        return AsyncExecutor.SINGLETON.submit(new Callable<Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call() throws Exception {
                                return Observable.just(1, 2, 3);
                            }
                        });
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer, TAG);
                    }
                });
            }
        });
        registery.add(Constants.Async.forEachFuture, new Runnable() {
            @Override
            public void run() {
                Future<Void> f = Async.forEachFuture(Observable.just(1, 2, 3), new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer, TAG);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log(throwable, TAG);
                    }
                });
                log("task done:" + f.isDone(), TAG);
            }
        });
        registery.add(Constants.Async.fromAction, new Runnable() {
            @Override
            public void run() {
                Async.fromAction(new Action0() {
                    @Override
                    public void call() {
                        log("action run on " + Thread.currentThread().getName(), TAG);
                        log("Action0.call", TAG);
                    }
                }, 3).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer, TAG);
                    }
                });
            }
        });
        registery.add(Constants.Async.fromCallable, new Runnable() {
            @Override
            public void run() {
                Async.fromCallable(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        log("action run on " + Thread.currentThread().getName(), TAG);
                        return 3;
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer, TAG);
                    }
                });
            }
        });
        registery.add(Constants.Async.fromRunnable, new Runnable() {
            @Override
            public void run() {
                Async.fromRunnable(new Runnable() {
                    @Override
                    public void run() {
                        log("Runnable.run on " + Thread.currentThread().getName(), TAG);
                    }
                }, 3).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer, TAG);
                    }
                });
            }
        });
        registery.add(Constants.Async.runAsync, new Runnable() {
            @Override
            public void run() {
                Async.runAsync(Schedulers.io(), new Action2<Observer<? super Integer>, Subscription>() {
                    @Override
                    public void call(Observer<? super Integer> observer, Subscription subscription) {
                        log("Action2 run on " + Thread.currentThread().getName(), TAG);
                        observer.onNext(1);
                        observer.onNext(2);
                        observer.onCompleted();
                    }
                }).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer, TAG);
                    }
                });
            }
        });
    }
}
