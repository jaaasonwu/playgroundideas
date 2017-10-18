package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.persistence.room.TypeConverter;

import com.playgroundideas.playgroundideas.model.DesignCategory;
import com.playgroundideas.playgroundideas.model.ProjectPostStatus;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

import javax.inject.Singleton;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;


@Singleton
public class Converters {

    @TypeConverter
    public static MonetaryAmount monetaryAmountFromString(String value) {
        if (value == null) {
            return null;
        } else {
            MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.ENGLISH);
            return format.parse(value);
        }
    }

    @TypeConverter
    public static String monetaryAmountToString(MonetaryAmount monetaryAmount) {
        if (monetaryAmount == null) {
            return null;
        } else {
            MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.ENGLISH);
            return format.format(monetaryAmount);
        }
    }

    @TypeConverter
    public static DesignCategory designCategoryFromString(String value) {
        return DesignCategory.valueOf(value);
    }

    @TypeConverter
    public static String designCategoryToString(DesignCategory designCategory) {
        return designCategory.name();
    }

    @TypeConverter
    public static ProjectPostStatus projectPostStatusFromString(String value) {
        return ProjectPostStatus.valueOf(value);
    }

    @TypeConverter
    public static String projectPostStatusToString(ProjectPostStatus projectPostStatus) {
        return projectPostStatus.name();
    }

    @TypeConverter
    public static URL urlFromString(String value) {
        URL result = null;
        try {
            result = new URL(value);
        } catch (MalformedURLException e) {
            result = null;
        }
        return result;
    }

    @TypeConverter
    public static String urlToString(URL url) {
        return url.toString();
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
