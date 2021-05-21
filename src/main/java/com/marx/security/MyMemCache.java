package com.marx.security;



import com.marx.ThreadManager.ThreadExecutor;
import com.wobangkj.cache.Cacheables;
import com.wobangkj.cache.Timing;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class MyMemCache implements Cacheables {

   protected final static Map<Object, Timing> TIMING = new WeakHashMap<>();

	protected Map<Object, Object> cache;
	protected transient boolean shutdown = true;

	public MyMemCache() {
		this.init(64);
	}

	public MyMemCache(int initialCapacity) {
		this.init(initialCapacity);
	}

	/**
	 * 是否停止
	 *
	 * @return 是否停止
	 */
	public boolean isShutdown() {
		return this.shutdown;
	}

	@Override
	public void set(Object key, Object value, Timing timing) {
		TIMING.put(key, timing);
		if (isShutdown()) {
			this.timing();
		}
		this.push(key, value);
	}

	@Override
	public  String getName() {
		return "memory_cache";
	}

	@Override
	public  Object getNativeCache() {
		return this.cache;
	}

	@Override
	public void clear() {
		this.cache.clear();
	}

	@Override
	public  ValueWrapper get( Object key) {
		return new SimpleValueWrapper(this.cache.get(key));
	}

	@Override
	public Object obtain(Object key) {
		return this.cache.get(key);
	}

	@Override
	public void del(Object key) {
		this.cache.remove(key);
	}

	protected void push(Object key, Object value) {
		this.cache.put(key, value);
	}

	/**
	 * 定时任务
	 */
	protected void timing() {
		this.shutdown = false;
		ThreadExecutor.TIMER.scheduleWithFixedDelay(this::autoDelete, 1, 5, TimeUnit.MINUTES);
	}

	/**
	 * 关掉自动任务
	 */
	protected void shutdown() {
		ThreadExecutor.TIMER.shutdown();
	}

	/**
	 * 自动清除
	 */
	protected void autoDelete() {
		LocalDateTime now = LocalDateTime.now();
		for (Map.Entry<Object, Timing> entry : TIMING.entrySet()) {
			if (entry.getValue().getDeadline().isBefore(now)) {
				this.cache.remove(entry.getKey());
				TIMING.remove(entry.getKey());
			}
		}
		if (TIMING.isEmpty()) {
			this.shutdown();
			this.shutdown = true;
		}
	}

	protected void init(int initialCapacity) {
		this.cache = new WeakHashMap<>(initialCapacity);
	}

}
