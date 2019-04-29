package io.iamkyu;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

public class FlowableTest {

    @Test
    public void flowableTest1() throws InterruptedException {
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                String[] datas = {"Foo", "Bar", "Hello", "World"};

                for (String data : datas) {
                    if (emitter.isCancelled()) {
                        return;
                    }

                    emitter.onNext(data);
                }

                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        flowable
                .observeOn(Schedulers.computation())
                .subscribe(new Subscriber<String>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(1L);
                    }

                    @Override
                    public void onNext(String s) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(String.format("%s: %s", threadName, s));
                        this.subscription.request(1L);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println(String.format("%s 완료", Thread.currentThread().getName()));
                    }
                });

        Thread.sleep(500);
    }

    @Test
    public void flowableTest2() throws InterruptedException {
        Flowable.interval(1L, TimeUnit.MILLISECONDS)
                .subscribe(new Subscriber<Long>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long data) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(String.format("%s: %s", threadName, data));

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println(String.format("%s End", Thread.currentThread().getName()));
                    }
                });

        Thread.sleep(10000L);
    }

}