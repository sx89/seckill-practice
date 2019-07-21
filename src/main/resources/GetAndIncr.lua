--
-- Created by IntelliJ IDEA.
-- User: Sun
-- Date: 19/7/21/021
-- Time: 9:30
-- To change this template use File | Settings | File Templates.
--

local key1 = KEYS[1]

local result_1 = redis.call('GET', key1);
if tonumber(result_1)<1000
    then
    local result_2 = redis.call('INCR', key1);
    return result_1;
else
    return result_1;
end

