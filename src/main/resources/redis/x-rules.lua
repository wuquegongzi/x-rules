if ARGV[1]>0 then
  redis.call('ZREMRANGEBYSCORE',KEYS[1],0,ARGV[1]);
  redis.call('EXPIRE',KEYS[1],ARGV[2]);
end
  redis.call('ZADD', KEYS[1], ARGV[3], ARGV[4]);
  return redis.call('ZCOUNT',KEYS[1],ARGV[5],ARGV[6]);