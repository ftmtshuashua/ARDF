# ARDF
--------
ARDF是一个帮助开发者快速构建项目的基础结构,提供了丰富的可拓展的基础功能.
Demo:[下载地址(暂未开放)]()

**简介**
--------
Activity/Fragment观察者
解耦逻辑代码和活动页面

并发请求/链式请求(ChainRequest)
各种请求场景使用


**入门指南**
--------
设置依赖项
```
implementation "---"
```
```
在项目中创建 Application 并且调用 AppFrameworkHolper.init(getApplicationContext()); 完成框架的初始化
```

如果想使用默认okhttp发起请求:
```
implementation "com.squareup.okhttp3:okhttp:3.2.0"
implementation "com.squareup.okio:okio:1.7.0"
```

learn 包:先知其然，再知其所以然


**感谢**
--------
[okhttp](https://github.com/square/okhttp)
[RxJava](https://github.com/ReactiveX/RxJava)

**问题反馈**
--------
如果遇到问题或者好的建议，请反馈到我的邮箱：ftmtshuashua@gmail.com 或者在我的博客留言

如果觉得对你有用的话，点一下右上的星星赞一下吧

**LICENSE**
--------
```
Copyright (c) 2016-present, RxJava Contributors.

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
