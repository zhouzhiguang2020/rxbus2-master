# RxBus3
rst step is to include RxBus2 into your project, for example, as a Gradle compile dependency:

Now we write the hello world app.

## Hello,World.
If you using this library on Android. Maybe you want to observe event on **Main Thread**(UI Thread).
So in your Application onCreate you should config MainScheduler for RxBus once.

```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // if you using @RxSubscribe Annotation,and observeOn MAIN, you should config this.
        RxBus.setMainScheduler(AndroidSchedulers.mainThread());
    }
}
```
> You can find AndroidSchedulers here [RxAndroid](https://github.com/ReactiveX/RxAndroid).(This operation is optional, do this only if you want to use @RxSubscribe Annotation and observeOn MAIN THREAD.)
### Annotation usage(just for RxBus)
* write listen event method

```java
    @RxSubscribe(observeOnThread = EventThread.MAIN)
    public void listenRxIntegerEvent(int code) {
        String text = String.format("{ Receive event: %s\nCurrent thread: %s }", code, Thread.currentThread().getId());
        Log.d("RxBus",text)
    }
```
```java
    @RxSubscribe(observeOnThread = EventThread.IO,isSticky = true)
    public void listenRxStringEvent(String event) {
        final String text = String.format("{ Receive event: %s\nCurrent thread: %s }", event, Thread.currentThread().getId());
        Log.d("RxBus",text);
    }
```

* register and unregister listen method

```java
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
	    //some other code ...
        RxBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        //auto release register with Annotation RxSubscribe.
        RxBus.getDefault().unregister(this);
        super.onDestroy();
    }
```

* post event

```java
@Override
public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFireEvent:
                RxBus.getDefault().post(100);//post integer event
                RxBus.getDefault().post("Hi,a string event");//post string event
		        RxBus.getDefault().post(new MyEvent("data on my event"));//post my event.
                break;
         }
 }

```
### Common usage

for example,ReplayBus.

```java
ReplayBus.getDefault()
                .ofType(String.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        String text = "{ [" + tag + "]:" + s+" }";
                        Log.d("ReplayBus”,text);
                    }
                });
  ReplayBus.getDefault().post("ReplayBus"+ RandomUtil.random(100));
```

### Proguard
add this line into your "proguard-rules.pro" file.
>If you use annotation, you need it. Don't forget keep your bean or entity in proguard too.

```groovy
# For using annotation
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepclassmembers class ** {
    @com.threshold.rxbus2.annotation.RxSubscribe <methods>;
}
-keep enum com.threshold.rxbus2.util.EventThread { *; }
```

## FAQ
* What is difference among of RxBus(PublishBus)/BehaviorBus/ReplayBus？

	Please see difference of PublishSubject / BehaviorSubject / ReplaySubject from RxJava doc.Here is link:<http://reactivex.io/documentation/subject.html>

* Can you demonstrate detailed?

	Please see app module in this repo,It show 3 type bus usage.