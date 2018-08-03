# [ARDF](https://github.com/ftmtshuashua/ARDF) 简介

[![Download](https://img.shields.io/badge/Download-Demo-blue.svg)](https://fir.im/l7b4)
[![GitHub release](https://img.shields.io/badge/release-1.0-blue.svg)](https://github.com/ftmtshuashua/ARDF/releases)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen.svg)](https://github.com/ftmtshuashua/ARDF/pulls)
[![License Apache2.0](http://img.shields.io/badge/license-Apache2.0-brightgreen.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)


`ARDF`是一款Android基础框架,它对Android进行扩充.贯穿整个开发周期,帮助开发者快速构建项目,提供了丰富的可拓展的基础功能.

>项目初建,还不稳定,不建议应用到企业项目中

>ARDF交流平台：QQ群：759081198（群1，未满）

## 目录
- [V1.0.0](#V1.0.0)
- [感谢](#感谢)
- [问题反馈](#问题反馈)
- [LICENSE](#LICENSE)

--------
## V1.0.0

- 网络增强模块
- 特效
- 工具
- 功能

## 入门指南

设置依赖项
```
implementation 'com.lfp:ardf:0.0.3'
```
项目使用的异步框架:
```
implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
implementation 'io.reactivex.rxjava2:rxjava:2.1.14-RC1'
implementation 'com.artemzin.rxjava:proguard-rules:1.1.1.0'
```
如果想使用默认okhttp发起请求:
```
implementation 'com.squareup.okhttp3:okhttp:3.2.0'
implementation 'com.squareup.okio:okio:1.7.0'
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

## 感谢

[okhttp](https://github.com/square/okhttp)
[RxJava](https://github.com/ReactiveX/RxJava)

## 问题反馈

如果你在使用ARDF中遇到任何问题可以提[Issues](https://github.com/ftmtshuashua/ARDF/issues)出来。另外欢迎大家为ARDF贡献智慧，欢迎大家[Fork and Pull requests](https://github.com/ftmtshuashua/ARDF)。

如果觉得对你有用的话，点一下右上的星星赞一下吧。

## LICENSE

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
