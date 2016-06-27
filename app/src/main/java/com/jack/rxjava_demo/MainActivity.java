package com.jack.rxjava_demo;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jack.rxjava_demo.com.jack.rxjava_demo.bean.Student;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.Observers;
import rx.schedulers.Schedulers;

/**
 * 参考博客：
 * https://gank.io/post/560e15be2dca930e00da1083#toc_12
 * <p>
 * 1.lift 和 compose 的区别
 * <p>
 * a.如果我们的自定义操作符想要作用到Observable发射出来的数据上，我们就要使用lift操作符；也就是说lift() 是针对事件项和事件序列的。
 * b.如果我们的自定义操作符想要改变整个的Observable，就需要使用compose操作符了, compose() 是针对 Observable 自身进行变换。
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 线程调度 Scheduler
     *
     * @param view
     */
    public void onScheduler(View view) {
        Observable.just(1, 2, 3, 4) // IO 线程，由 subscribeOn() 指定
                //指定 subscribe() 发生在 IO 线程
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        Log.e(TAG, Thread.currentThread().getName() + "Schedulers.newThread()");
                        return "call" + integer;
                    }
                }) // 新线程，由 observeOn() 指定
                .observeOn(Schedulers.io())
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        Log.i(TAG, Thread.currentThread().getName() + "Schedulers.io()");

                        final char[] chars = s.toCharArray();
                        int res = -1;
                        for (char c : chars) {
                            res += c;
                        }
                        return res;
                    }
                }) // IO 线程，由 observeOn() 指定
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {   // Android 主线程，由 observeOn() 指定
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->onError:" + e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->onNext:" + integer);
                    }
                });
    }

    /**
     * Filters items emitted by an Observable by only emitting those that satisfy a specified predicate.
     * 查找，学生中学习语文的同学的名字
     *
     * @param view
     */
    public void onFilter(View view) throws IllegalAccessException {
        List<Student> lists = new ArrayList<>();
        lists.add(new Student(10, "jack", Arrays.asList("数学", "语文", "英语")));
        lists.add(new Student(20, "helen", Arrays.asList("物理", "化学", "语文")));
        lists.add(new Student(30, "alice", Arrays.asList("生物", "地理")));

        /** 原始实现 **/
//        for (Student std : lists) {
//            for (String s : std.getCourses()) {
//                if ("语文".equals(s)) {
//                    Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->stu choose 语文：" + std);
//                    break;
//                }
//            }
//        }


        Observable.from(lists)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student stu) {
                        return Observable.from(stu.getCourses())
                                .filter(new Func1<String, Boolean>() {
                                    @Override
                                    public Boolean call(String s) {
                                        return s.equals("语文");
                                    }
                                });
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->onError:" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->onNext:" + s);
                    }
                });


    }

    /**
     * map() 是一对一的转化
     * 打印所有学生的年龄
     *
     * @param view
     */
    public void onMap(View view) {
        List<Student> lists = new ArrayList<>();
        lists.add(new Student(10, "jack", Arrays.asList("数学", "语文", "英语")));
        lists.add(new Student(20, "helen", Arrays.asList("物理", "化学")));
        lists.add(new Student(30, "alice", Arrays.asList("生物", "地理")));

        Observable.from(lists) // 输入类型 String
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Student, Integer>() {
                    @Override
                    public Integer call(Student student) {
                        try {
                            Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->call:" + student.toString());
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return student.getAge();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->call:" + integer);
                    }
                });

    }

    /**
     * flatmap,一对多的转化
     * 打印出每个学生所需要修的所有课程的名称
     */
    public void onFlatmap(View view) {
        List<Student> lists = new ArrayList<>();
        lists.add(new Student(10, "jack", Arrays.asList("数学", "语文", "英语")));
        lists.add(new Student(20, "helen", Arrays.asList("物理", "化学")));
        lists.add(new Student(30, "alice", Arrays.asList("生物", "地理")));

        Observable.from(lists)
                .subscribeOn(Schedulers.io())               // 指定 subscribe() 发生在 IO 线程,也叫事件产生的线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定 Subscriber 的回调发生在主线程,也叫事件消费的线程
                .flatMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->onError:" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->onNext:" + s);
                    }
                });

    }

    /**
     * lift() 是针对事件项和事件序列的!
     * 在 Observable 执行了 lift(Operator) 方法之后，会返回一个新的 Observable，
     * 这个新的 Observable 会像一个代理一样，负责接收原始的 Observable 发出的事件，
     * 并在处理后发送给 Subscriber。
     *
     * @param view
     */
    public void onLift(View view) {
        Observable.just(1, 2, 3, 4)
                .lift(new Observable.Operator<String, Integer>() {
                    @Override
                    public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                        // 将事件序列中的 Integer 对象转换为 String 对象
                        return new Subscriber<Integer>() {
                            @Override
                            public void onNext(Integer integer) {
                                subscriber.onNext("String" + integer);
                            }

                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }
                        };
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->Action1 call:" + s);
                    }
                });
    }

    /**
     * lift() 是针对事件项和事件序列的，
     * 而 compose() 是针对 Observable 自身进行变换。
     *
     * @param view
     */
    public void onCompose(View view) {
        /**
         * Transformer实际上就是一个Func1<Observable<T>, Observable<R>>，
         * 换言之就是：可以通过它将一种类型的Observable转换成另一种类型的Observable
         */
        Observable.Transformer<Integer, String> myTransformer = new Observable.Transformer<Integer, String>() {
            @Override
            public Observable<String> call(Observable<Integer> observable) {
                return observable
                        .map(new Func1<Integer, String>() {
                            @Override
                            public String call(Integer integer) {
                                String info = "myTransforer:" + integer;
                                Log.i(TAG, "转换Obserable->Thread:" + Thread.currentThread().toString() + "->call:" + info);
                                return info;
                            }
                        });
            }
        };
        Observable.just(1, 2, 3).compose(myTransformer)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "Thread:" + Thread.currentThread().toString() + "->call:" + s);
                    }
                });
    }
}
