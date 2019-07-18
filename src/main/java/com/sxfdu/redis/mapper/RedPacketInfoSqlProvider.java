package com.sxfdu.redis.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.sxfdu.redis.domain.RedPacketInfo;
import com.sxfdu.redis.domain.RedPacketInfoExample.Criteria;
import com.sxfdu.redis.domain.RedPacketInfoExample.Criterion;
import com.sxfdu.redis.domain.RedPacketInfoExample;
import java.util.List;
import java.util.Map;

public class RedPacketInfoSqlProvider {

    public String countByExample(RedPacketInfoExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("red_packet_info");
        applyWhere(example, false);
        return SQL();
    }

    public String deleteByExample(RedPacketInfoExample example) {
        BEGIN();
        DELETE_FROM("red_packet_info");
        applyWhere(example, false);
        return SQL();
    }

    public String insertSelective(RedPacketInfo record) {
        BEGIN();
        INSERT_INTO("red_packet_info");
        
        if (record.getRedPacketId() != null) {
            VALUES("red_packet_id", "#{redPacketId,jdbcType=BIGINT}");
        }
        
        if (record.getTotalAmount() != null) {
            VALUES("total_amount", "#{totalAmount,jdbcType=INTEGER}");
        }
        
        if (record.getTotalPacket() != null) {
            VALUES("total_packet", "#{totalPacket,jdbcType=INTEGER}");
        }
        
        if (record.getRemainingAmount() != null) {
            VALUES("remaining_amount", "#{remainingAmount,jdbcType=INTEGER}");
        }
        
        if (record.getRemainingPacket() != null) {
            VALUES("remaining_packet", "#{remainingPacket,jdbcType=INTEGER}");
        }
        
        if (record.getUid() != null) {
            VALUES("uid", "#{uid,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            VALUES("update_time", "#{updateTime,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    public String selectByExample(RedPacketInfoExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("red_packet_id");
        SELECT("total_amount");
        SELECT("total_packet");
        SELECT("remaining_amount");
        SELECT("remaining_packet");
        SELECT("uid");
        SELECT("create_time");
        SELECT("update_time");
        FROM("red_packet_info");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        String sqlString = SQL();
        if (example != null && example.getLimit() != null) {
            sqlString += " limit " + example.getLimit();
        }
        if (example != null && example.getOffset() != null) {
            sqlString += " offset " + example.getOffset();
        }
        return sqlString;
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        RedPacketInfo record = (RedPacketInfo) parameter.get("record");
        RedPacketInfoExample example = (RedPacketInfoExample) parameter.get("example");
        
        BEGIN();
        UPDATE("red_packet_info");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getRedPacketId() != null) {
            SET("red_packet_id = #{record.redPacketId,jdbcType=BIGINT}");
        }
        
        if (record.getTotalAmount() != null) {
            SET("total_amount = #{record.totalAmount,jdbcType=INTEGER}");
        }
        
        if (record.getTotalPacket() != null) {
            SET("total_packet = #{record.totalPacket,jdbcType=INTEGER}");
        }
        
        if (record.getRemainingAmount() != null) {
            SET("remaining_amount = #{record.remainingAmount,jdbcType=INTEGER}");
        }
        
        if (record.getRemainingPacket() != null) {
            SET("remaining_packet = #{record.remainingPacket,jdbcType=INTEGER}");
        }
        
        if (record.getUid() != null) {
            SET("uid = #{record.uid,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateTime() != null) {
            SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("red_packet_info");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("red_packet_id = #{record.redPacketId,jdbcType=BIGINT}");
        SET("total_amount = #{record.totalAmount,jdbcType=INTEGER}");
        SET("total_packet = #{record.totalPacket,jdbcType=INTEGER}");
        SET("remaining_amount = #{record.remainingAmount,jdbcType=INTEGER}");
        SET("remaining_packet = #{record.remainingPacket,jdbcType=INTEGER}");
        SET("uid = #{record.uid,jdbcType=INTEGER}");
        SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        SET("update_time = #{record.updateTime,jdbcType=TIMESTAMP}");
        
        RedPacketInfoExample example = (RedPacketInfoExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    protected void applyWhere(RedPacketInfoExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}
