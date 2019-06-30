package com.swpu.student.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AccountEntity
 * @since 2019-06-27
 */
@Entity(tableName = "accountObservable", indices = [Index(value = ["student_number"], unique = true)])
data class AccountEntity(@ColumnInfo(name = "student_number") var number: String, @ColumnInfo var password: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long = 0

    @ColumnInfo(name = "last_login_date")
    var lastLoginDate: Date = Date()
}
