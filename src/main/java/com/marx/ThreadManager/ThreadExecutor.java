package com.marx.ThreadManager;

import com.wobangkj.thread.WorkThreadFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadExecutor {
    public static final ScheduledThreadPoolExecutor TIMER;

	private static final int CORE_POLL_SIZE = 10;

	static {
		TIMER = new ScheduledThreadPoolExecutor(CORE_POLL_SIZE, new WorkThreadFactory("缓存任务"), new ThreadPoolExecutor.DiscardPolicy());
	}
}
