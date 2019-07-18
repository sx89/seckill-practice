package com.sxfdu.redis.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;

@Generated("red_packet_info")
public class RedPacketInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public RedPacketInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdIsNull() {
            addCriterion("red_packet_id is null");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdIsNotNull() {
            addCriterion("red_packet_id is not null");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdEqualTo(Long value) {
            addCriterion("red_packet_id =", value, "redPacketId");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdNotEqualTo(Long value) {
            addCriterion("red_packet_id <>", value, "redPacketId");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdGreaterThan(Long value) {
            addCriterion("red_packet_id >", value, "redPacketId");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdGreaterThanOrEqualTo(Long value) {
            addCriterion("red_packet_id >=", value, "redPacketId");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdLessThan(Long value) {
            addCriterion("red_packet_id <", value, "redPacketId");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdLessThanOrEqualTo(Long value) {
            addCriterion("red_packet_id <=", value, "redPacketId");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdIn(List<Long> values) {
            addCriterion("red_packet_id in", values, "redPacketId");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdNotIn(List<Long> values) {
            addCriterion("red_packet_id not in", values, "redPacketId");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdBetween(Long value1, Long value2) {
            addCriterion("red_packet_id between", value1, value2, "redPacketId");
            return (Criteria) this;
        }

        public Criteria andRedPacketIdNotBetween(Long value1, Long value2) {
            addCriterion("red_packet_id not between", value1, value2, "redPacketId");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNull() {
            addCriterion("total_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNotNull() {
            addCriterion("total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountEqualTo(Integer value) {
            addCriterion("total_amount =", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotEqualTo(Integer value) {
            addCriterion("total_amount <>", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThan(Integer value) {
            addCriterion("total_amount >", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_amount >=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThan(Integer value) {
            addCriterion("total_amount <", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThanOrEqualTo(Integer value) {
            addCriterion("total_amount <=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIn(List<Integer> values) {
            addCriterion("total_amount in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotIn(List<Integer> values) {
            addCriterion("total_amount not in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountBetween(Integer value1, Integer value2) {
            addCriterion("total_amount between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("total_amount not between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalPacketIsNull() {
            addCriterion("total_packet is null");
            return (Criteria) this;
        }

        public Criteria andTotalPacketIsNotNull() {
            addCriterion("total_packet is not null");
            return (Criteria) this;
        }

        public Criteria andTotalPacketEqualTo(Integer value) {
            addCriterion("total_packet =", value, "totalPacket");
            return (Criteria) this;
        }

        public Criteria andTotalPacketNotEqualTo(Integer value) {
            addCriterion("total_packet <>", value, "totalPacket");
            return (Criteria) this;
        }

        public Criteria andTotalPacketGreaterThan(Integer value) {
            addCriterion("total_packet >", value, "totalPacket");
            return (Criteria) this;
        }

        public Criteria andTotalPacketGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_packet >=", value, "totalPacket");
            return (Criteria) this;
        }

        public Criteria andTotalPacketLessThan(Integer value) {
            addCriterion("total_packet <", value, "totalPacket");
            return (Criteria) this;
        }

        public Criteria andTotalPacketLessThanOrEqualTo(Integer value) {
            addCriterion("total_packet <=", value, "totalPacket");
            return (Criteria) this;
        }

        public Criteria andTotalPacketIn(List<Integer> values) {
            addCriterion("total_packet in", values, "totalPacket");
            return (Criteria) this;
        }

        public Criteria andTotalPacketNotIn(List<Integer> values) {
            addCriterion("total_packet not in", values, "totalPacket");
            return (Criteria) this;
        }

        public Criteria andTotalPacketBetween(Integer value1, Integer value2) {
            addCriterion("total_packet between", value1, value2, "totalPacket");
            return (Criteria) this;
        }

        public Criteria andTotalPacketNotBetween(Integer value1, Integer value2) {
            addCriterion("total_packet not between", value1, value2, "totalPacket");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountIsNull() {
            addCriterion("remaining_amount is null");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountIsNotNull() {
            addCriterion("remaining_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountEqualTo(Integer value) {
            addCriterion("remaining_amount =", value, "remainingAmount");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountNotEqualTo(Integer value) {
            addCriterion("remaining_amount <>", value, "remainingAmount");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountGreaterThan(Integer value) {
            addCriterion("remaining_amount >", value, "remainingAmount");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("remaining_amount >=", value, "remainingAmount");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountLessThan(Integer value) {
            addCriterion("remaining_amount <", value, "remainingAmount");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountLessThanOrEqualTo(Integer value) {
            addCriterion("remaining_amount <=", value, "remainingAmount");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountIn(List<Integer> values) {
            addCriterion("remaining_amount in", values, "remainingAmount");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountNotIn(List<Integer> values) {
            addCriterion("remaining_amount not in", values, "remainingAmount");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountBetween(Integer value1, Integer value2) {
            addCriterion("remaining_amount between", value1, value2, "remainingAmount");
            return (Criteria) this;
        }

        public Criteria andRemainingAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("remaining_amount not between", value1, value2, "remainingAmount");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketIsNull() {
            addCriterion("remaining_packet is null");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketIsNotNull() {
            addCriterion("remaining_packet is not null");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketEqualTo(Integer value) {
            addCriterion("remaining_packet =", value, "remainingPacket");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketNotEqualTo(Integer value) {
            addCriterion("remaining_packet <>", value, "remainingPacket");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketGreaterThan(Integer value) {
            addCriterion("remaining_packet >", value, "remainingPacket");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketGreaterThanOrEqualTo(Integer value) {
            addCriterion("remaining_packet >=", value, "remainingPacket");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketLessThan(Integer value) {
            addCriterion("remaining_packet <", value, "remainingPacket");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketLessThanOrEqualTo(Integer value) {
            addCriterion("remaining_packet <=", value, "remainingPacket");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketIn(List<Integer> values) {
            addCriterion("remaining_packet in", values, "remainingPacket");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketNotIn(List<Integer> values) {
            addCriterion("remaining_packet not in", values, "remainingPacket");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketBetween(Integer value1, Integer value2) {
            addCriterion("remaining_packet between", value1, value2, "remainingPacket");
            return (Criteria) this;
        }

        public Criteria andRemainingPacketNotBetween(Integer value1, Integer value2) {
            addCriterion("remaining_packet not between", value1, value2, "remainingPacket");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Integer value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Integer value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Integer value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Integer value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Integer value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Integer> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Integer> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Integer value1, Integer value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Integer value1, Integer value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}