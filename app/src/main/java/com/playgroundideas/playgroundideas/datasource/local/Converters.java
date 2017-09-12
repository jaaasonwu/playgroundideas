package com.playgroundideas.playgroundideas.datasource.local;

import android.arch.persistence.room.TypeConverter;

import com.playgroundideas.playgroundideas.domain.DesignCategory;

import java.util.Locale;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

/**
 * Created by Ferdinand on 12/09/2017.
 */

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
    public static DesignCategory designCategoryFromLong(Long value) {
        for (DesignCategory d : DesignCategory.values()) {
            if (value.equals(d.getId())) {
                return d;
            }
        }
        throw new IllegalArgumentException("Could not recognize design category");
    }

    @TypeConverter
    public static Long designCategoryToLong(DesignCategory designCategory) {
        return designCategory.getId();
    }


}
