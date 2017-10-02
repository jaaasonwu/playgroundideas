package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.persistence.room.TypeConverter;

import com.playgroundideas.playgroundideas.model.DesignCategory;
import com.playgroundideas.playgroundideas.model.ProjectPostStatus;

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

}
