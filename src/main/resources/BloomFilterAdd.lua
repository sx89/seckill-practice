--
-- Created by IntelliJ IDEA.
-- User: Sun
-- Date: 19/7/19/019
-- Time: 15:33
-- To change this template use File | Settings | File Templates.
--
local bloomName = KEYS[1]
local value = KEYS[2]

local result_1 = redis.call('bf.add', bloomName, value)
return result_1
