redis：
hash存放token对应的用户名：tokens-<'token', '用户名'>
zset存放token对应的过期时间：tokenExpireTime-<'token', '过期时间'>
string存放邮箱对应的验证码：邮箱-验证码
zset存放话题阅读总榜：topicTotalReadNum-<'话题名', '阅读量'>
zset存放话题阅读周榜：topicWeekReadNum-<'话题名', '阅读量'>
zset存放话题阅读日榜：topicDayReadNum-<'话题名', '阅读量'>





[版本说明 · alibaba/spring-cloud-alibaba Wiki (github.com)](https://github.com/alibaba/spring-cloud-alibaba/wiki/版本说明#2021x-分支)

版本：

Spring Cloud Alibaba: 2021.0.1.0

Spring Cloud: 2021.0.1

Spring Boot: 2.6.3

Sentinel: 1.8.3

Nacos: 1.4.2