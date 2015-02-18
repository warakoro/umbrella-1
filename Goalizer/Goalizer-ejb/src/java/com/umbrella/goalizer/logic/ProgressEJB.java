/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.logic;

import com.umbrella.goalizer.entity.Activity;
import com.umbrella.goalizer.entity.Period;
import com.umbrella.goalizer.entity.RecurringTask;
import com.umbrella.goalizer.entity.Task;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author mjanki
 */
@Stateless
@LocalBean
public class ProgressEJB { 
    public HashMap getProgressBar(Task task) {
        HashMap progressMap = new HashMap<>();
        progressMap.put("progress", 0);
        progressMap.put("message", "");
        
        if (task.isSingle()) {
            progressMap.put("progress", getProgressSingle(task));
            progressMap.put("message", getMessageSingle(task).get("message"));
            progressMap.put("color", getMessageSingle(task).get("color"));
        } else if (task.isRecurring()) {
            progressMap.put("progress", getProgressRecurring((RecurringTask)task));
            progressMap.put("message", getMessageRecurring((RecurringTask)task).get("message"));
            progressMap.put("color", getMessageRecurring((RecurringTask)task).get("color"));
        }
        
        return progressMap;
    }
    
    public HashMap getMessageRecurring(RecurringTask task) {
        Calendar cDateCal = Calendar.getInstance();
        cDateCal.setTime(new Date());
        cDateCal.add(Calendar.DATE, 14);
        Date cDate = cDateCal.getTime();
        
        long period = getPeriod(task);
        long elapsedDiff = cDate.getTime() - task.getCreationDate().getTime();
        long elapsed = TimeUnit.DAYS.convert(elapsedDiff, TimeUnit.MILLISECONDS);
        long allDiff = task.getDeadlines().get(task.getDeadlines().size() - 1).getDate().getTime() - task.getCreationDate().getTime();
        long allDays = TimeUnit.DAYS.convert(allDiff, TimeUnit.MILLISECONDS);
        double periods = (double)elapsed / (double)period;
        double allPeriods = (double)allDays / (double)period;
            
        HashMap messageMap = new HashMap();
        
        String message = "";
        String color = "#000000";
        Date deadline = task.getDeadlines().get(task.getDeadlines().size() - 1).getDate();
        if (cDate.before(deadline)) {
            long remainingDaysDiff = deadline.getTime() - cDate.getTime();
            long remainingDays = TimeUnit.DAYS.convert(remainingDaysDiff, TimeUnit.MILLISECONDS);
            if (remainingDays < 5) {
                message = message.concat("You have " + remainingDays + " days left until deadline!");
            } else {
                message = message.concat("You have plenty of time left until deadline!");
            }
            
            HashMap avgRecurrence = getAvgRecurrence(task.getActivityList().size(), period, periods, elapsed);
            message = message.concat(" You're doing " + task.getTitle() + " " + avgRecurrence.get("times") + " times per " + avgRecurrence.get("per") + " day.");
            
            HashMap neededRecurrence = getAvgRecurrence(Math.round((float)allPeriods * (float)task.getRecurrence()), period, allPeriods, allDays);
            message = message.concat(" Ideally you should be doing " + task.getTitle() + " " + neededRecurrence.get("times") + " times per " + neededRecurrence.get("per") + " day.");
        } else {
            // Missed deadline or not
            Date tDeadline = task.getDeadlines().get(task.getDeadlines().size() - 1).getDate();
            long diff = tDeadline.getTime() - cDate.getTime();
            long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            
            if (daysDiff <= 0) {
                message = message.concat("You missed your deadline!");
            } else {
                message = message.concat("You did it on time!");
            }
            
            // State actual recurrence
            HashMap avgRecurrence = getAvgRecurrence(task.getActivityList().size(), period, allPeriods, allDays);
            message = message.concat(" You did " + task.getTitle() + " " + avgRecurrence.get("times") + " times per " + avgRecurrence.get("per") + " day.");
            
            if (daysDiff <= 0) {
                HashMap neededRecurrence = getAvgRecurrence(Math.round((float)allPeriods * (float)task.getRecurrence()), period, allPeriods, allDays);
                message = message.concat(" You were supposed to be doing " + task.getTitle() + " " + neededRecurrence.get("times") + " times per " + neededRecurrence.get("per") + " day!");
            } else {
                message = message.concat(" You've done it perfectly! Good job!");
            }    
        }
        
        messageMap.put("message", message);
        messageMap.put("color", color);
        return messageMap;
    }
    
    public HashMap getAvgRecurrence(int countPastActivities, long period, double periods, long elapsed) {
        // Actual frequency
        HashMap avgRecurrence = new HashMap();
        if (countPastActivities == 0) {
            avgRecurrence.put("times", 0);
            avgRecurrence.put("per", 1);
        } else {
            double perDays = (double)elapsed / (double)countPastActivities;
            if (perDays >= 1) {
                avgRecurrence.put("times", 1);
                avgRecurrence.put("per", Math.round(perDays));
            } else {
                avgRecurrence.put("times", Math.round(1 / perDays));
                avgRecurrence.put("per", 1);
            }
        }
        
        return avgRecurrence;
    }
    
    public int getProgressRecurring(RecurringTask task) {
        Calendar cDateCal = Calendar.getInstance();
        cDateCal.setTime(new Date());
        cDateCal.add(Calendar.DATE, 14);
        Date cDate = cDateCal.getTime();
        
        long period = getPeriod(task);

        long elapsedDiff = cDate.getTime() - task.getCreationDate().getTime();
        long elapsed = TimeUnit.DAYS.convert(elapsedDiff, TimeUnit.MILLISECONDS);
        
        long allDiff = task.getDeadlines().get(task.getDeadlines().size() - 1).getDate().getTime() - task.getCreationDate().getTime();
        long allDays = TimeUnit.DAYS.convert(allDiff, TimeUnit.MILLISECONDS);
        
        double periods = (double)elapsed / (double)period;
        double allPeriods = (double)allDays / (double)period;
        
        if (cDate.before(task.getDeadlines().get(task.getDeadlines().size() - 1).getDate())) {
            Calendar lDeadlineCal = Calendar.getInstance();
            lDeadlineCal.setTime(task.getCreationDate());
            lDeadlineCal.add(Calendar.DATE, (int)(Math.ceil(periods) - 1) * (int)period);
            Date lDeadline = lDeadlineCal.getTime();

            int cPeriodTimes = 0;
            for (Activity activity : task.getActivityList()) {
                if (activity.getCreationDate().getTime() >= lDeadline.getTime()) {
                    cPeriodTimes++;
                }
            }
            int timesLeftInPeriod = task.getRecurrence() - cPeriodTimes;

            if (timesLeftInPeriod <= 0) {
                return 100;
            } else {
                double progress = ((double)cPeriodTimes / (double)task.getRecurrence()) * 100.0;
                progress = (progress > 100.0) ? 100.0 : progress;
                return (int)Math.round(progress);
            }
        } else {
            double progress = ((double)task.getActivityList().size() / ((double)task.getRecurrence() * (double)allPeriods));
            progress = progress * 100;
            progress = (progress > 100.0) ? 100.0 : progress;
            return (int)Math.round(progress);
        }
    }
    
    public int getProgressSingle(Task task) {
        if (task.getActivityList().size() > 0) {
            return 100;
        } else {
            return 0;
        }
    }
    
    public HashMap getMessageSingle(Task task) {
        Calendar cDateCal = Calendar.getInstance();
        cDateCal.setTime(new Date());
        cDateCal.add(Calendar.DATE, 14);
        Date cDate = cDateCal.getTime();
        
        Date tDeadline = task.getDeadlines().get(task.getDeadlines().size() - 1).getDate();
        long diff = tDeadline.getTime() - cDate.getTime();
        long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        
        HashMap messageMap = new HashMap();
        
        String mString;
        String color;
        if (task.getActivityList().size() > 0) {
            if (daysDiff <= 0) {
                mString = "You finished your task, but late! Try to do it on time next time!";
                color = "#0033ff";
            } else {
                mString = "Well done! You finished your task on time!";
                color = "#33cc00";
            }
        } else {
            if (daysDiff <= 0) {
                mString = "You have missed your deadline! You can do it still, but be careful next time!";
                color = "#ff0000";
            } else if (daysDiff < 5) {
                mString = "You have " + daysDiff + " days left until your deadline! You have time!";
                color = "#cccc00";
            } else {
                mString = "You have not done this task yet, but you have plenty of time left until your deadline!";
                color = "#ff9900";
            }
        }
        
        messageMap.put("message", mString);
        messageMap.put("color", color);
        return messageMap;
    }
    
    public long getPeriod(RecurringTask task) {
        long period = 1;
        if (task.getPeriod() == Period.DAY) {
            period = 1;
        } else if (task.getPeriod() == Period.WEEK) {
            period = 7;
        } else if (task.getPeriod() == Period.MONTH) {
            period = 30;
        } else if (task.getPeriod() == Period.YEAR) {
            period = 365;
        } else if (task.getPeriod() == Period.UNTIL_DEADLINE) {
            Date deadline = task.getDeadlines().get(task.getDeadlines().size() - 1).getDate();
            Date creation = task.getCreationDate();
            long diff = deadline.getTime() - creation.getTime();
            period = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }
        
        return period;
    }
}
