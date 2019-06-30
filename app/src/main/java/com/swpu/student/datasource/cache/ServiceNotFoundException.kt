package com.swpu.student.datasource.cache

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ServiceNotFoundException
 *
 * @since 2019/6/23
 */
class ServiceNotFoundException : RuntimeException {
    constructor() {}

    constructor(message: String) : super(message) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}

    constructor(cause: Throwable) : super(cause) {}
}
