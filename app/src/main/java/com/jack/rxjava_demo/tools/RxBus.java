package com.jack.rxjava_demo.tools;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Jacky on 2016/8/29.
 */

public class RxBus {
    private static volatile RxBus instance;

    //Subject,Represents an object that is both an Observable and an Observer.
    private final Subject<Object, Object> BUS;

    //为了保证多线程的调用中结果的确定性，我们按照官方推荐将 Subject 转换成了一个 SerializedSubject 。
    private RxBus() {
        BUS = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void post(Object event) {
        BUS.onNext(event);
    }

    public <T> Observable<T> toObserverable(Class<T> eventType) {
        // ofType = filter + cast
        return BUS.ofType(eventType);
    }
}
