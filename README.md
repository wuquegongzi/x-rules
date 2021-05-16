# X-RULES  持续开发中
### 规则引擎
  当前互联网企业存在很多业务风险,而系统控制规则又多变，所以需要规则引擎一类的应用系统提供基础支撑。
  目前风控领域的产品在业内已趋于成熟。比如实时交易反欺诈、信贷风控、机器学习模型计算、用户画像、企业知识图谱等。

本项目致力于：

  1、前期实现最基本的规则引擎，熟悉风控的最基础流程。

  2、使用drools、redis、mogodb等应用场景，进而比对时下流式计算的优异。

## 背景
 业务风险,eg:

* 账号：垃圾注册、撞库、盗号等
* 交易：盗刷、恶意占用资源、篡改交易金额等
* 活动：薅羊毛
* 短信：短信轰炸

## 项目介绍
**x-rules分析风险事件，根据场景动态调整规则，实现自动精准预警风险。**

### 技术栈介绍
* 轻量级，可扩展，实时的Java业务风控系统
* 基于Spring boot构建
* 使用drools规则引擎管理风控规则，原则上可以动态配置规则
* 使用redis、mongodb做风控计算和事件储存，历史事件支持水平扩展
* 未来集成flink、spark、MLSQL 等流式计算引擎以及其他开源SQL引擎

## 原理
### 统计学
* 次数统计，比如1分钟内某账号的登录次数，可以用来分析盗号等
* 频数统计，比如1小时内某ip上出现的账号，可以用来分析黄牛党等
* 最大统计，比如用户交易金额比历史交易都大，可能有风险
* 最近统计，比如最近一次交易才过数秒，可能机器下单
* 行为习惯，比如用户常用登录地址，用户经常登录时间段，可以用来分析盗号等

**抽象：某时间段，在条件维度（可以是多个维度复合）下，利用统计方法统计结果维度的值。充分发挥你的想象吧！**

### 实时计算
要将任意维度的历史数据（可能半年或更久）实时统计出结果，需要将数据提前安装特殊结果准备好（由于事件的维度数量不固定的，选取统计的维度也是随意的，所以不是在关系数据库中建几个索引就能搞定的），需要利用空间换时间，来降低时间复杂度。

### redis
redis中数据结构sortedset，是个有序的集合，集合中只会出现最新的唯一的值。利用sortedset的天然优势，做频数统计非常有利。

比如1小时内某ip上出现的账号数量统计：

* 保存维度

	ZADD key score member（时间复杂度:O(M*log(N))， N 是有序集的基数， M 为成功添加的新成员的数量），key=ip，score=时间（比如20210507121314），member=账号。存储时略耗性能。
	结构如下：

		1.1.1.1
			|--账号1		202105121314
			|--账号2		20210507121315
			|--账号n		20210507121316
		
		2.2.2.2
			|--账号3		20210507121314
			|--账号4		20210507121315
			|--账号m		20210507121316

* 计算频数

	ZCOUNT key min max（时间复杂度:O(1)），key=ip，min=起始时间，max=截止时间。计算的性能消耗极少，优势明显
* redis lua

	把保存维度，计算频数，过期维度数据等操作，使用lua脚本结合在一起，可以减少网络IO，提高性能


### mongodb
mongodb本身的聚合函数统计维度，支持很多比如：max，min，sum，avg，first，last，标准差，采样标准差，复杂的统计方法可以在基础聚合函数上建立，比如行为习惯：

	getDB().getCollection(collectionName).aggregate(
	            Arrays.asList(
	                    match(match)													--匹配条件维度
	                    , group("$" + field, Accumulators.sum("_count", 1))				--求值维度的次数
	                    , match(new Document("_count", new Document("$gte", minCount))) --过滤，超过minCount才统计
	                    , sort(new Document("_count", -1))								--对次数进行倒叙排列
	            )
	    );

建议在mongodb聚合的维度上建立索引，这样可以使用内存计算，速度较快。

### 风控流程
1. 黑名单
2. 白名单
3. 从细颗粒到粗颗粒，依次执行1和2，将所有黑白名单遍历
4. 风控规则
5. 阈值预警
6. 保存事件

## 环境准备
* mysql，数据结构在x-rules.sql中定义了
* redis
* mongodb，建议使用分片集群

## 项目配置
* 应用配置：application.properties
* 日志配置：logback-spring.xml
* 规则配置：rules/*.drl，规则都是用java语言编写。

> drools的详细文档，请参考官方	[http://docs.jboss.org/drools/release/7.6.0.Final/drools-docs/html_single/index.html](http://docs.jboss.org/drools/release/6.4.0.Final/drools-docs/html_single/index.html)

## 部署
系统默认采用jar打包和运行。
### 打包

	mvn clean install

### 运行
建议jdk 8

	java -jar x-rules-*.jar

## 风控分析入口以及调用样例
* 请求：http://domain/xrules/req?json=JSON.toJsonString(LoginEvent)
* 响应：score字段代码该事件的风险值（超过100分预警）


## TODO
ing

## 致谢
初版思路借鉴：[六道——实时业务风控系统](https://github.com/ysrc/Liudao)。 该项目提供了系统框架基础和代码模板,github目前已停止更新。

## 献词	

