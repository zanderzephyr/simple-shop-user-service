package com.simple_shop.user_service.user.service.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.simple_shop.user_service.user.model.UserType;
import com.simple_shop.user_service.user.service.redis.RedisUserData.RedisUserDataBuilder;
import com.simple_shop.user_service.utils.DataTypeUtils;

/*
 * If the redis service is not available somehow, there are going to be
 * exceptions thrown by Spring. We don't care for those right now and let them
 * move requests into HTTP 500 state.
 */
@Service
public class RedisUserService {
	private final StringRedisTemplate redisTemplate;

	public RedisUserService(final StringRedisTemplate redisTemplate) {
		super();

		this.redisTemplate = redisTemplate;
	}

	public void createUserEntry(final long userId, final RedisUserData userData) {
		final String key = Long.toString(userId);
		final String value = userData.getUserType().name();
		this.redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(60));
	}

	public RedisUserData getUserEntry(final long userId) {
		final String userDataString = this.redisTemplate.opsForValue().get(Long.toString(userId));
		return this.stringToUserData(userDataString);
	}

	public void deleteUserEntry(final long userId) {
		this.redisTemplate.delete(Long.toString(userId));
	}

	private RedisUserData stringToUserData(final String userDataString) {
		if (userDataString != null) {
			final UserType userType = DataTypeUtils.parseEnum(userDataString, UserType.class);
			return new RedisUserDataBuilder().setUserType(userType).build();
		}
		return null;
	}
}