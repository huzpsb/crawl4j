Crawl4j
---

## 简介

Try it yourself! [在线Demo](https://apis.huzpsb.eu.org/search)

在线Demo是以2345网址导航和某OIer博客为原点，各抓取十分钟（共二十分钟的抓取量）的效果。

Crawl4j是一个开源的互联网搜索引擎。你可以使用它在一个小时内索引上万个互联网站点，并进行毫秒级检索。  
Crawl4j致力于为个人提供可自行部署的搜索引擎，让每个人都能拥有自己的搜索引擎，而不是被少数大公司垄断。  
每个人都可以使用Crawl4j来创建自己的搜索引擎，而不需要任何专业知识。  
![](sample.png)

网页版搜索正在研发中。

## 目标

Crawl4j架构上追求精简、别致。因此，Crawl4j会尽力追求：

- 0依赖：Crawl4j不依赖任何第三方库，只使用Java标准库。
- 0配置：Crawl4j不需要任何配置，只需要一个jar包即可运行。
- 可接受：Crawl4j能够在标准的个人电脑上运行。

Crawl4j可能不会去考虑：

- 性能：Crawl4j不会去优化微秒以下的性能。
- SLA：Crawl4j不会去追求99.9%以上的稳定性。只要能够重启后继续运行即可。
- 热插拔：Crawl4j的索引数据库不支持热插拔。包括Token持久化在内的模块全部不会被考虑实现。
- 易迁移：Crawl4j不会去实现避免使用static，或者使用依赖注入等技术。

## 使用

Crawl4j需要Java 8或以上版本。  
对于普通用户，只需要从[Release](https://github.com/huzpsb/crawl4j/releases)页面下载最新的jar包并运行即可。  
我们提供交互式的指南，让你可以轻松地使用Crawl4j。

对于开发者，请自行阅读并理解源代码。  
一般来说，如果您只是想调用Crawl4j的API，那么您只需要关注`search`包下的类即可。  
~~就三个类，每个也就几十行，不会这都看不懂吧？杂鱼❤，真是杂鱼呢..~~

## WebUI

请参考[BukkitHTTP](https://github.com/BukkitHTTP/BukkitHTTP)部署。

## 开发与TODO

Crawl4j的开发目前处于早期阶段，因此我们欢迎任何形式的贡献。   
你可以通过提交Issue来提出你的想法，或者直接提交Pull Request。  
目前而言，我们正在着手完成：

- [x] 网页版搜索引擎
- [ ] 更好的robots.txt合规性支持
- [x] 交互式引导
- [ ] 允许用户提交搜索
- [x] 基于现有索引库的增量索引

## 注意事项

1，Crawl4j目前处于早期阶段，因此可能会有很多问题。并且设计上Crawl4j就不是为企业准备的。  
如果有因为Crawl4j的缺陷导致的任何损失，我无法承担任何责任。如果不能接受这一点，请不要使用Crawl4j。

2，Crawl4j对robots.txt的支持尚未完善，由于robots.txt的规范性问题，Crawl4j可能会无法正确解析robots.txt。  
在使用时，请务必手动再次确认robots.txt的规范性。

3，请在通过修改代码来修改Crawl4j的行为，与新建索引库时，务必注意相关规定与条款。  
在网站使用者不同意这么做的时候，你不应该这么做。否则请自行承担法律责任。

## 官方镜像

[Gitee](https://gitee.com/ImJohnSmith/crawl4j)  
镜像仅供代码与Release加速下载使用。