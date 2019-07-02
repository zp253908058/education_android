package com.swpu.student.model

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Entities
 * @since 2019-06-30
 */
data class StudentInfo(
    var majorName: String = "",
    var studentName: String = "",
    var studentNumber: String = "",
    var sex: String = "",
    var phoneNumber: String = ""
)

data class EventInfo(
    var id: Long = 0,
    var eventName: String = ""
)