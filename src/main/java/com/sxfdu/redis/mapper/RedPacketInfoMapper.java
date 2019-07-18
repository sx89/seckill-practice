package com.sxfdu.redis.mapper;

import com.sxfdu.redis.domain.RedPacketInfo;
import com.sxfdu.redis.domain.RedPacketInfoExample;
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

public interface RedPacketInfoMapper {
    @SelectProvider(type=RedPacketInfoSqlProvider.class, method="countByExample")
    int countByExample(RedPacketInfoExample example);

    @DeleteProvider(type=RedPacketInfoSqlProvider.class, method="deleteByExample")
    int deleteByExample(RedPacketInfoExample example);

    @Insert({
        "insert into red_packet_info (red_packet_id, total_amount, ",
        "total_packet, remaining_amount, ",
        "remaining_packet, uid, ",
        "create_time, update_time)",
        "values (#{redPacketId,jdbcType=BIGINT}, #{totalAmount,jdbcType=INTEGER}, ",
        "#{totalPacket,jdbcType=INTEGER}, #{remainingAmount,jdbcType=INTEGER}, ",
        "#{remainingPacket,jdbcType=INTEGER}, #{uid,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(RedPacketInfo record);

    @InsertProvider(type=RedPacketInfoSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(RedPacketInfo record);

    @SelectProvider(type=RedPacketInfoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER),
        @Result(column="red_packet_id", property="redPacketId", jdbcType=JdbcType.BIGINT),
        @Result(column="total_amount", property="totalAmount", jdbcType=JdbcType.INTEGER),
        @Result(column="total_packet", property="totalPacket", jdbcType=JdbcType.INTEGER),
        @Result(column="remaining_amount", property="remainingAmount", jdbcType=JdbcType.INTEGER),
        @Result(column="remaining_packet", property="remainingPacket", jdbcType=JdbcType.INTEGER),
        @Result(column="uid", property="uid", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<RedPacketInfo> selectByExample(RedPacketInfoExample example);

    @UpdateProvider(type=RedPacketInfoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") RedPacketInfo record, @Param("example") RedPacketInfoExample example);

    @UpdateProvider(type=RedPacketInfoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") RedPacketInfo record, @Param("example") RedPacketInfoExample example);
}