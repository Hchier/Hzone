package xyz.hchier.hzone.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import xyz.hchier.hzone.entity.Talk;

/**
 * @author Hchier
 */
@Mapper
public interface TalkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Talk record);

    Talk selectByPrimaryKey(Integer id);

    List<Talk> selectAll();

    int updateByPrimaryKey(Talk record);

    //    void test(Map<String, List<Talk>> map);
    void test(List<Talk> talkList1, List<Talk> talkList2);
}