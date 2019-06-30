package com.swpu.student.datasource.database

import androidx.room.TypeConverter
import java.util.*


/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Converters
 * @since 2019-06-28
 */
class Converters {
    @TypeConverter
    fun revertDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun converterDate(value: Date): Long {
        return value.time
    }
}