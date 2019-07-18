package com.sxfdu.redis.mapper;

import com.sxfdu.redis.domain.SysUserBloom;
import com.sxfdu.redis.domain.SysUserBloomExample;
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

public interface SysUserBloomMapper {
    @SelectProvider(type=SysUserBloomSqlProvider.class, method="countByExample")
    int countByExample(SysUserBloomExample example);

    @DeleteProvider(type=SysUserBloomSqlProvider.class, method="deleteByExample")
    int deleteByExample(SysUserBloomExample example);

    @Insert({
        "insert into sys_user_bloom (user_name, image)",
        "values (#{userName,jdbcType=VARCHAR}, #{image,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insert(SysUserBloom record);

    @InsertProvider(type=SysUserBloomSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyProperty="id")
    int insertSelective(SysUserBloom record);

    @SelectProvider(type=SysUserBloomSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="image", property="image", jdbcType=JdbcType.VARCHAR)
    })
    List<SysUserBloom> selectByExample(SysUserBloomExample example);

    @UpdateProvider(type=SysUserBloomSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") SysUserBloom record, @Param("example") SysUserBloomExample example);

    @UpdateProvider(type=SysUserBloomSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") SysUserBloom record, @Param("example") SysUserBloomExample example);
}