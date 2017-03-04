package com.stonewuu.task;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronSignTask {
	private static final Log log = LogFactory.getLog(CronSignTask.class);
	@Resource
	private SignAllTask allTask;
	
	@Scheduled(cron="0 0 2,12,18 * * ?")
	public void signAll(){
		new Thread(allTask).start();
		log.info("自动签到程序运行！");
	}
	
}
