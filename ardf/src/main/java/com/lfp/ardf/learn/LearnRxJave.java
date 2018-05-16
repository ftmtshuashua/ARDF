package com.lfp.ardf.learn;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * 先知其然，再知其所以然.
 * Created by LiFuPing on 2018/5/14.
 */
class LearnRxJave {
    private LearnRxJave() {
    }


    void MethodTable_Observable() { /*io.reactivex.Observable 方法：*/
        Integer[] integers = {0, 9, 6, 4, 8};
//        Observable.fromArray(integers).subscribe(new Observer<Integer>() {
//        });

        /* 术语
        Observable 被观察者
        ? 未明白的操作
        */

        /* 主要操作符
        create ;  创建一个复杂业务的Observable
        from   :  将其它相同种类的对象和数据转换为Observable
        just   :  类似于from ,区别在于可以发送不同种类的对象和数据
        range  :  按顺序发送范围中的值

        empty  :  正常终止,会调用onCompleted
        never  :  ?挂起
        error  :  错误终止,会调用onError

        timer  :  延时操作,默认执行在computation
        interval :心跳操作,默认执行在computation

        repeat :  重复发送Observable
        defer  :  在被订阅的时候创建Observable

         //转换操作符
        map    :  遍历数据并且转换
        flatMap:  类似于map ,区别转换为Observable并且合并
        concatMap:类似于flatMap,区别是不合并,顺序发送
        switchMap:并发操作中执行最新的Observable
        groupBy:  数据分组
        scan   :  递归
        buffer :  缓存，合并部分数据之后在发送
        window :  类似于buffer,区别是发送的是Observable

        //过滤操作符
        filter :  过滤数据
        ofType :  过滤数据类型
        first  :  只发送第一个或某个满足条件的数据.没有满足条件的数据时会onError(NoSuchElementException)
        firstOrDefault:first的变形，增加一个默认返回数据
        takeFirst:类似于first,区别是不调用onNext()但是会调用onCompleted
        single :  输入输出数据 >1 条时onError(NoSuchElementException)
        singleOrDefault:类似于first与firstOrDefault的关系
        last   :  与first相反 同样具有lastOrDefault，TakeLast
        skip   :  跳过几项数据或者一些时间之后执行
        skipLast: 跳过末尾几项数据
        take   :  类似于first,区别在完成之后调用onCompleted(),变体TakeLast，takeLastBuffer
        debounce: 防止数据抖动
        distinct: 过滤掉重复数据
        elementAt:取原始数据中的某数据作为发送对象
        ignoreElements:不允许发送所有数据，只执行onError或onCompleted， ?会执行流程

        //组合操作符
        merge  :  将多个Observables的输出合并 - 顺序可能发生变化
        mergeDelayError:类似于merge，不同是如果有error，它会在所有next执行完之后再执行error (subscribeOn和observeOn对于他不起作用)
        concat :  类似与merge，不同是有顺序的
        zip    :  按顺序合并数据,会抛弃多余数据
        startWith:在一个Observable在发射数据之前先发射一个指定的数据序列
        combineLatest:类似与zip,不同是不对应顺序，结合最近发送的数据
        join   :  数据交叉组合，每一种可能都组合一次
        switchOnNext:在新Observable执行的时候抛弃旧的Observable


        //辅助操作符
        delay  :  在原始Observable在发射每项数据之前都暂停一段指定的时间
        delaySubscription:类似于delay,区别在dealy是延迟数据的发送，而此操作符是延迟数据的注册
        do     :  给Observable执行周期的关键节点添加回调.doOnNext，doOnSubscribe，doOnUnsubscribe，doOnCompleted，doOnError，doOnTerminate和doOnEach
        subscribeOn/observeOn:自定调度器(线程)
        timestamp/timeInterval:数据发送的事件，timeInterval是每一项发送的时间
        timeout:  指定一个时间，如果超过此事件为接收到信息将会onError
        to     :  转换为另一个对象或数据结构

        //条件和布尔操作符
        all    :  判断所有数据是否瞒足条件，当不满足时终止
        amb    :  只执行最先发送的Observables
        contains: 只发送包含的数据
        isEmpty:  类似于contains,区别是判断未发送数据
        defaultIfEmpty:如果没有发送数据，发射一个默认数据
        sequenceEqual:判断连个Observable数据与动作是否完全相同
        skipUntil:忽略自己发送的数据，并在自己开始发送数据的时候发送接收Observable的数据（之前数据将被丢弃）
        skipWhile:忽略条件中的数据，和filter相反
        takeUntil:与skipUntil相反
        takeWhile:指定的某个条件不成立的那一刻停止发射原始Observable
         */


        /*----------- Observable中方法表以及作用 ----------*/

        /*
        amb()
        ambArray()
        bufferSize()
        combineLatest()
        combineLatestDelayError()
        concat()
        concatArray()
        concatArrayDelayError()
        concatArrayEager()
        concatDelayError()
        concatEager
        create
        defer
        empty
        error
        fromArray
        fromCallable
        fromFuture
        fromIterable
        fromPublisher
        generate
        interval
        intervalRange
        just
        merge
        mergeArray
        mergeDelayError
        mergeArrayDelayError
        never
        range
        rangeLong
        sequenceEqual
        switchOnNext
        switchOnNextDelayError
        timer
        unsafeCreate
        using
        wrap
        zip
        zipArray
        zipIterable
        all
        ambWith
        any
        as
        blockingFirst
        blockingForEach
        blockingIterable
        blockingLast
        blockingLatest
        blockingMostRecent
        blockingNext
        blockingSingle
        toFuture
        blockingSubscribe
        buffer
        cache
        cacheWithInitialCapacity
        cast      : 强制类型转化
        collect
        collectInto
        compose
        concatMap
        concatMapDelayError
        concatMapEager
        concatMapEagerDelayError
        concatMapCompletable
        concatMapCompletableDelayError
        concatMapIterable
        concatMapMaybe
        concatMapMaybeDelayError
        concatMapSingle
        concatMapSingleDelayError
        concatWith
        contains
        count
        debounce
        defaultIfEmpty
        delay
        delaySubscription
        dematerialize
        distinct
        distinctUntilChanged
        doAfterNext
        doAfterTerminate
        doFinally
        doOnDispose
        doOnComplete
        doOnEach
        doOnError
        doOnLifecycle
        doOnNext
        doOnSubscribe
        doOnTerminate
        elementAt
        elementAtOrError
        filter
        firstElement
        first
        firstOrError
        flatMap
        flatMapCompletable
        flatMapIterable
        flatMapMaybe
        flatMapSingle
        forEach
        forEachWhile
        groupBy
        groupJoin
        hide
        ignoreElements
        isEmpty
        join
        lastElement
        last
        lastOrError
        lift
        map
        materialize
        mergeWith
        observeOn
        ofType
        onErrorResumeNext
        onErrorReturn
        onErrorReturnItem
        onExceptionResumeNext
        onTerminateDetach
        publish
        reduce
        reduceWith
        repeat
        repeatUntil
        repeatWhen
        replay
        retry
        retryUntil
        retryWhen
        safeSubscribe
        sample
        scan
        scanWith
        serialize
        share
        singleElement
        single
        singleOrError
        skip
        skipLast
        skipUntil
        skipWhile
        sorted
        startWith
        startWithArray
        subscribe
        subscribeActual
        subscribeWith
        subscribeOn
        switchIfEmpty
        switchMap
        switchMapCompletable
        switchMapCompletableDelayError
        switchMapMaybe
        switchMapMaybeDelayError
        switchMapSingle
        switchMapSingleDelayError
        switchMapDelayError
        take
        takeLast
        takeUntil
        takeWhile
        throttleFirst
        throttleLast
        throttleWithTimeout
        timeInterval
        timeout
        timeout0
        timestamp
        to
        toList
        toMap
        toMultimap
        toFlowable
        toSortedList
        unsubscribeOn
        window
        withLatestFrom
        zipWith
        test
        */
    }


}
