package com.sychina.admin.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 *
 */
public final class LocalDateTimeHelper {

    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDateTime longToLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return dateToLocalDateTime(new Date(timestamp));
    }

    public static LocalDateTime parse(String str) {
        return ZonedDateTime.parse(str).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime max(LocalDateTime time1, LocalDateTime time2) {
        if (time1 == null) {
            return time2;
        }
        if (time2 == null) {
            return time1;
        }

        return time1.isAfter(time2) ? time1 : time2;
    }

}
