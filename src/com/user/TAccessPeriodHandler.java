package com.user;

import com.ConsoleUserInterface;
import com.course.CalendarController;

import java.util.Calendar;
import java.util.TimerTask;

public class TAccessPeriodHandler extends TimerTask {
    Calendar accessStart;
    Calendar accessEnd;
    ConsoleUserInterface cmd = ConsoleUserInterface.getInstance();

    TAccessPeriodHandler(Calendar accessStart, Calendar accessEnd) {
        System.out.println();
        System.out.println("Access Period Poller running (for demonstration purposes)");
        try {
            cmd.display("This student's access period is from "+accessStart.getTime());
            cmd.display("to "+accessEnd.getTime());
            this.setAccessStart(accessStart);
            this.setAccessEnd(accessEnd);
        } catch (IllegalArgumentException e) {
            cmd.display("Access period for this student has error");
            cmd.display("Please contact administrator/ Check database");
            e.printStackTrace();
        }
    }

    public void run() {
        check();
    }

    public void check() {
        System.out.println();
        System.out.println("-------------------------------------------------");
        System.out.println("Checking access time (For demonstration purposes)");
        System.out.println("--------------------------------------------------");
        Calendar currentTime = Calendar.getInstance();
        if (accessStart.compareTo(currentTime) > 0) {
            System.out.println("You are trying to access the program before your access period");
            cmd.displayf("Your access period is from {} to {}", new String[]{CalendarController.caltoString(accessStart),
                    CalendarController.caltoString(accessEnd)});
            System.exit(0);
        } else if (accessEnd.compareTo(currentTime) < 0) {
            System.out.println("You are trying to access the program after your access period");
            cmd.displayf("Your access period is from {} to {}", new String[]{CalendarController.caltoString(accessStart),
                    CalendarController.caltoString(accessEnd)});
            System.exit(0);
        }

    }


    public Calendar getAccessStart() {
        return accessStart;
    }

    public String getAccessStartStr() {
        return CalendarController.caltoString(accessStart);
    }

    public void setAccessStart(Calendar accessStart) {
        if (accessEnd != null) {
            if (accessStart.compareTo(accessEnd) > 0) {
                throw new IllegalArgumentException("Access start date cannot be later than end date");
            }
        }
        this.accessStart = accessStart;
    }

    public Calendar getAccessEnd() {
        return accessEnd;
    }

    public String getAccessEndStr() {
        return CalendarController.caltoString(accessEnd);
    }

    public void setAccessEnd(Calendar accessEnd) {
        if (accessStart != null) {
            if (accessEnd.compareTo(accessStart) < 0) {
                throw new IllegalArgumentException("Access end date cannot be earlier than start date");
            }
        }
        this.accessEnd = accessEnd;
    }
}
