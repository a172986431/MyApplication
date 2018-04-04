package com.elang.wcdb

import com.elang.wcdb.annotation.Primary

/**
 * Created by zhuMH on 18/4/4.
 */

class UserInfo {
    @Primary
    var id: String? = null
    var name: String? = null
    var age: String? = null

    constructor(){}

    constructor(id: String, name: String, age: String) {
        this.id = id
        this.name = name
        this.age = age
    }

}
