# ARDF

ARDF是一个帮助开发者快速构建项目的基础框架,提供了丰富的可拓展的基础功能.
Demo:[下载地址(暂未开放)]()

**简介**
--------
LifecycleObserved - Activity/Fragment观察者,实现业务逻辑和Activity的解耦

FragmentControl - 控制Fragment的加载/显示/切换。优化性能预防内存泄漏

RadioGroupControl - 让任何布局带有RadioGroup的功能

BaseDelayDialog - 延迟Dialog，尽量减少Dialog的显示

NotProguard - 不混淆配置.为一些不想被混淆的类添加实现,在混淆的时候它将不会被混淆

请求融合 - 将多个请求融合在一起,逻辑清晰,容易维护

并发请求 - 请求并行,急速网络请求方案

链式请求 - 请求排队,随时修改,饲适用与多种场景

BaseProgressBarView - 动画解决方案,专注动画实现

WebViewFk - 基于WebView的浏览器实现

**入门指南**
--------
设置依赖项
```
implementation "暂不提供"
```
如果想使用默认okhttp发起请求:
```
implementation "com.squareup.okhttp3:okhttp:3.2.0"
implementation "com.squareup.okio:okio:1.7.0"
```

在proguard-rules中添加以下代码
```
-keep class com.lfp.ardf.model.NotProguard {*;}
-keep class * extends com.lfp.ardf.model.NotProguard {*;}
-keep class **.**$** extends com.lfp.ardf.model.NotProguard {*;}
```

为APP创建Applicaiton,并且在onCreate()方法中调用
```
AppFrameworkHolper.init(getApplicationContext()); - 初始化配置
FileCacheConfig.init(getApplicationContext(), "ARDF"); - 文件缓存配置
```

**感谢**
--------
[okhttp](https://github.com/square/okhttp)
[RxJava](https://github.com/ReactiveX/RxJava)

**问题反馈**
--------
交流和建议(QQ群)：759081198

如果遇到问题或者好的建议，请反馈到我的邮箱：ftmtshuashua@gmail.com 或者在我的博客留言

如果觉得对你有用的话，点一下右上的星星赞一下吧

**LICENSE**
--------
```
Copyright (c) 2018-present, ARDF Contributors.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
