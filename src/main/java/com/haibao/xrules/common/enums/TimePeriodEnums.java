package com.haibao.xrules.common.enums;

import java.util.Date;

/**
 *
 *
 * @author ml.c
 * @date 11:14 PM 6/3/21
 **/
public enum TimePeriodEnums {

    ALL,
    LASTMIN,
    LASTHOUR,
    LASTDAY;

    public Date getMinTime(Date now) {
        if (this.equals(ALL)) {
            return new Date(0);
        } else {
            return new Date(now.getTime() - getTimeDiff());
        }
    }

    public Date getMaxTime(Date now) {
        return now;
    }

    public long getTimeDiff() {
        long timeDiff;
        switch (this) {
            case ALL:
                timeDiff = Long.MAX_VALUE;
                break;
            case LASTMIN:
                timeDiff = 60 * 1000L;
                break;
            case LASTHOUR:
                timeDiff = 3600 * 1000L;
                break;
            case LASTDAY:
                timeDiff = 24 * 3600 * 1000L;
                break;
            default:
                timeDiff = 60 * 1000L;
                break;
        }
        return timeDiff;
    }

}
