redis：
hash存放token对应的用户名：tokens-<'token', '用户名'>
zset存放token对应的过期时间：tokenExpireTime-<'token', '过期时间'>
string存放邮箱对应的验证码：邮箱-验证码
zset存放话题阅读总榜：topicTotalReadNum-<'话题名', '阅读量'>
zset存放话题阅读周榜：topicWeekReadNum-<'话题名', '阅读量'>
zset存放话题阅读日榜：topicDayReadNum-<'话题名', '阅读量'>