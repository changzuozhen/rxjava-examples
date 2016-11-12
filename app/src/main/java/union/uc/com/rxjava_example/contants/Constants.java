package union.uc.com.rxjava_example.contants;

/**
 * Created by wangli on 4/16/16.
 */
public class Constants {
    public static final String async = "async";
    public static final String blocking_observable = "blocking_observable";
    public static final String combine = "combine";
    public static final String condition = "condition";
    public static final String connectable_observable = "connectable_observable";
    public static final String customer_operator = "customer_operator";
    public static final String error_handler = "error_handler";
    public static final String filter = "filter";
    public static final String math_aggregate = "math_aggregate";
    public static final String observable_create = "observable_create";
    public static final String plugin = "plugin";
    public static final String scheduler = "scheduler";
    public static final String string = "string";
    public static final String subject = "subject";
    public static final String transformation = "transformation";
    public static final String utility = "utility";

    public static final class Utility {
        private static final String TAG = "Utility ";
        public static final String materialize = TAG + "materialize";
        public static final String timestamp = TAG + "timestamp";
        public static final String serialize = TAG + "serialize";
        public static final String cache = TAG + "cache";
        public static final String observeOn = TAG + "observeOn";
        public static final String subscribeOn = TAG + "subscribeOn";
        public static final String doOnEach = TAG + "doOnEach";
        public static final String doOnCompleted = TAG + "doOnCompleted";
        public static final String doOnError = TAG + "doOnError";
        public static final String doOnTerminate = TAG + "doOnTerminate";
        public static final String doOnSubscribe = TAG + "doOnSubscribe";

        public static final String doOnUnsubscribe = TAG + "doOnUnsubscribe";
        public static final String finallyDo = TAG + "finallyDo";
        public static final String delay = TAG + "delay";
        public static final String delaySubscription = TAG + "delaySubscription";
        public static final String timeInterval = TAG + "timeInterval";
        public static final String using = TAG + "using";
        public static final String single = TAG + "single";
        public static final String singleOrDefault = TAG + "singleOrDefault";
    }

    public static final class Transformation {
        private static final String TAG = "Transformation ";
        public static final String map = TAG + "map";
        public static final String flatMap = TAG + "flatMap";
        public static final String concatMap = TAG + "concatMap";
        public static final String flatMapIterable = TAG + "flatMapIterable";
        public static final String switchMap = TAG + "switchMap";
        public static final String scan = TAG + "scan";
        public static final String groupBy = TAG + "groupBy";
        public static final String buffer = TAG + "buffer";
        public static final String window = TAG + "window";
        public static final String cast = TAG + "cast";
    }

    public static final class Subject {
        private static final String TAG = "Subject ";
        public static final String async = TAG + "async";
        public static final String behavior = TAG + "behavior";
        public static final String behavior_with_init_value = TAG + "behavior_with_init_value";
        public static final String publish = TAG + "publish";
        public static final String replay = TAG + "replay";
        public static final String replay_create_with_time = TAG + "replay_create_with_time";
    }

    public static final class Strings {
        private static final String TAG = "Strings ";
        public static final String byLine = TAG + "byLine";
        public static final String decode = TAG + "decode";
        public static final String encode = TAG + "encode";
        public static final String from = TAG + "from";
        public static final String join = TAG + "join";
        public static final String split = TAG + "split";
        public static final String stringConcat = TAG + "stringConcat";
    }

    public static final class Scheduler {
        private static final String TAG = "Scheduler ";
        public static final String io = TAG + "io";
        public static final String compute = TAG + "compute";
        public static final String immediate = TAG + "immediate";
        public static final String new_thread = TAG + "new_thread";
        public static final String trampoline = TAG + "trampoline";
        public static final String self_define = TAG + "self_define";
    }

    public static final class ReactiveStream {
        private static final String TAG = "ReactiveStream ";
        public static final String materialize = TAG + "test";
    }

    public static final class Plugin {
        private static final String TAG = "Plugin ";
        public static final String start_hook = TAG + "start_hook";
    }

    public static final class ObservableCreate {
        private static final String TAG = "ObservableCreate ";
        public static final String just = TAG + "just";
        public static final String from_future = TAG + "from_future";
        public static final String from_iterable = TAG + "from_iterable";
        public static final String repeat = TAG + "repeat";
        public static final String repeatWhen = TAG + "repeatWhen";
        public static final String create = TAG + "create";
        public static final String defer = TAG + "defer";
        public static final String range = TAG + "range";
        public static final String interval = TAG + "interval";
        public static final String timer = TAG + "timer";
        public static final String empty = TAG + "empty";
        public static final String error = TAG + "error";
        public static final String never = TAG + "never";
    }

    public static final class MathAggregate {
        private static final String TAG = "MathAggregate ";
        public static final String averageInteger = TAG + "averageInteger";
        public static final String averageLong = TAG + "averageLong";
        public static final String averageFloat = TAG + "averageFloat";
        public static final String averageDouble = TAG + "averageDouble";
        public static final String max = TAG + "max";
        public static final String maxBy = TAG + "maxBy";
        public static final String min = TAG + "min";
        public static final String minBy = TAG + "minBy";
        public static final String sumInteger = TAG + "sumInteger";
        public static final String sumLong = TAG + "sumLong";
        public static final String sumFloat = TAG + "sumFloat";
        public static final String sumDouble = TAG + "sumDouble";
        public static final String concat = TAG + "concat";
        public static final String count = TAG + "count";
        public static final String countLong = TAG + "countLong";
        public static final String reduce = TAG + "reduce";
        public static final String collect = TAG + "collect";
        public static final String toList = TAG + "toList";
        public static final String toSortedList = TAG + "toSortedList";
        public static final String toMap = TAG + "toMap";
        public static final String toMultiMap = TAG + "toMultiMap";
    }

    public static final class Filter {
        private static final String TAG = "Filter ";
        public static final String filter = TAG + "filter";
        public static final String takeLast = TAG + "takeLast";
        public static final String last = TAG + "last";
        public static final String lastOrDefault = TAG + "lastOrDefault";
        public static final String takeLastBuffer = TAG + "takeLastBuffer";
        public static final String skip = TAG + "skip";
        public static final String skipLast = TAG + "skipLast";
        public static final String take = TAG + "take";
        public static final String first = TAG + "first";
        public static final String takeFirst = TAG + "takeFirst";
        public static final String firstOrDefault = TAG + "firstOrDefault";
        public static final String elementAt = TAG + "elementAt";
        public static final String elementAtOrDefault = TAG + "elementAtOrDefault";
        public static final String sample = TAG + "sample";
        public static final String throttleLast = TAG + "throttleLast";
        public static final String throttleFirst = TAG + "throttleFirst";
        public static final String throttleWithTimeout = TAG + "throttleWithTimeout";
        public static final String debounce = TAG + "debounce";
        public static final String timeout = TAG + "timeout";
        public static final String distinct = TAG + "distinct";
        public static final String distinctUntilChanged = TAG + "distinctUntilChanged";
        public static final String ofType = TAG + "ofType";
        public static final String ignoreElements = TAG + "ignoreElements";
    }

    public static final class ErrorHandler {
        private static final String TAG = "ErrorHandler ";
        public static final String onErrorResumeNext = TAG + "onErrorResumeNext";
        public static final String onErrorReturn = TAG + "onErrorReturn";
        public static final String onExceptionResumeNext = TAG + "onExceptionResumeNext";
        public static final String retry = TAG + "retry";
        public static final String retryWhen = TAG + "retryWhen";
    }

    public static final class CustomerOperator {
        private static final String TAG = "CustomerOperator ";
        public static final String customeOperator = TAG + "customeOperator";
    }

    public static final class ConnectableObservable {
        private static final String TAG = "ConnectableObservable ";
        public static final String connect = TAG + "connect";
        public static final String publish = TAG + "publish";
        public static final String replay = TAG + "replay";
        public static final String refCount = TAG + "refCount";
    }

    public static final class Condition {
        private static final String TAG = "Condition ";
        public static final String amb = TAG + "amb";
        public static final String defaultIfEmpty = TAG + "defaultIfEmpty";
        public static final String doWhile = TAG + "doWhile";
        public static final String ifThen = TAG + "ifThen";
        public static final String skipUtil = TAG + "skipUtil";
        public static final String skipWhile = TAG + "skipWhile";
        public static final String switchcase = TAG + "switchcase";
        public static final String takeUntil = TAG + "takeUntil";
        public static final String takeWhile = TAG + "takeWhile";
        public static final String takeWhileWithIndex = TAG + "takeWhileWithIndex";
        public static final String WhileDo = TAG + "WhileDo";

        public static final String all = TAG + "all";
        public static final String contains = TAG + "contains";
        public static final String exists = TAG + "exists";
        public static final String isEmpty = TAG + "isEmpty";
        public static final String sequenceEqual = TAG + "sequenceEqual";
    }

    public static final class Combine {
        private static final String TAG = "Combine ";
        public static final String startWith = TAG + "startWith";
        public static final String merge = TAG + "merge";
        public static final String mergeDelayError = TAG + "mergeDelayError";
        public static final String zip = TAG + "zip";
        public static final String and_then_when = TAG + "and_then_when";
        public static final String combineLatest = TAG + "combineLatest";
        public static final String join = TAG + "join";
        public static final String groupjoin = TAG + "groupjoin";
        public static final String switchIfEmpty = TAG + "switchIfEmpty";
        public static final String switchOnNext = TAG + "switchOnNext";
    }

    public static final class BlockingObservable {
        private static final String TAG = "BlockingObservable ";
        public static final String forEach = TAG + "forEach";
        public static final String first = TAG + "first";
        public static final String firstOrDefault = TAG + "firstOrDefault";
        public static final String last = TAG + "last";
        public static final String lastOrDefault = TAG + "lastOrDefault";
        public static final String mostRecent = TAG + "mostRecent";
        public static final String next = TAG + "next";
        public static final String latest = TAG + "latest";
        public static final String single = TAG + "single";
        public static final String singleOrDefault = TAG + "singleOrDefault";
        public static final String toFuture = TAG + "toFuture";
        public static final String toIterable = TAG + "toIterable";
        public static final String getIterator = TAG + "getIterator";
    }

    public static final class Async {
        private static final String TAG = "Async ";
        public static final String start = TAG + "start";
        public static final String toAsync = TAG + "toAsync";
        public static final String startFuture = TAG + "startFuture";
        public static final String deferFuture = TAG + "deferFuture";
        public static final String forEachFuture = TAG + "forEachFuture";
        public static final String fromAction = TAG + "fromAction";
        public static final String fromCallable = TAG + "fromCallable";
        public static final String fromRunnable = TAG + "fromRunnable";
        public static final String runAsync = TAG + "runAsync";
    }

}
