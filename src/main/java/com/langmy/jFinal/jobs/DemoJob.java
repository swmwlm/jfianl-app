package com.langmy.jFinal.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Map;

public class DemoJob implements Job {
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    Map data = jobExecutionContext.getJobDetail().getJobDataMap();
    System.out.println("hi," + data.get("name"));
  }
}