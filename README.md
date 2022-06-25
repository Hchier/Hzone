"# hzone" 
# redis
## key

1. hash: blogIdAndUsername blogId username, 用hash存放博客id与其作者。
2. set: blogFavorOfUsername blogId1 blogId2, 用set存放每个人的博客点赞情况。
3. set: blogFavorToAddOfUsername blogFavor1 blogFavor2, 用set存放每个人新增的点赞情况（存BlogFavor对象），每隔一段时间就批量插入mysql，然后清除key。
4. set: blogFavorToCancelOfUsername blogId1 blogId2, 用set存放每个人新增的取消点赞情况（存BlogFavor对象），每隔一段时间就批量更新mysql，然后清除key。
5. hash: blogIdAndFavorNum blogId favorNum, 用hash存放博客id与点赞数量。 
