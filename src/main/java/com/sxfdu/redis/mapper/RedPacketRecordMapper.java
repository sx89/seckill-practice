package com.sxfdu.redis.mapper;

import com.sxfdu.redis.domain.RedPacketRecord;
import com.sxfdu.redis.domain.RedPacketRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface RedPacketRecordMapper {
    @SelectProvider(type=RedPacketRecordSqlProvider.class, method="countByExample")
    int countByExample(RedPacketRecordExample example);

    @DeleteProvider(type=RedPacketRecordSqlProvider.class, method="deleteByExample")
    int deleteByExample(RedPacketRecordExample example);

    @Insert({
        "insert into red_packet_record (amount, nick_name, ",
        "img_url, uid, red_packet_id, ",
        "create_time, update_time)",
        "values (#{amount,jdbcType=INTEGER}, #{nickName,jdbcType=VARCHAR}, ",
        "#{imgUrl,jdbcType=VARCHAR}, #{uid,jdbcType=INTEGER}, #{redPacketId,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(RedPacketRecord record);

    @InsertProvider(type=RedPacketRecordSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(RedPacketRecord record);

    @SelectProvider(type=RedPacketRecordSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER),
        @Result(column="amount", property="amount", jdbcType=JdbcType.INTEGER),
        @Result(column="nick_name", property="nickName", jdbcType=JdbcType.VARCHAR),
        @Result(column="img_url", property="imgUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="uid", property="uid", jdbcType=JdbcType.INTEGER),
        @Result(column="red_packet_id", property="redPacketId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<RedPacketRecord> selectByExample(RedPacketRecordExample example);

    @UpdateProvider(type=RedPacketRecordSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") RedPacketRecord record, @Param("example") RedPacketRecordExample example);

    @UpdateProvider(type=RedPacketRecordSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") RedPacketRecord record, @Param("example") RedPacketRecordExample example);
}