package com.elang.wcdb.statement

import com.elang.wcdb.annotation.Primary
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Field

/**
 * Created by zhuMH on 18/3/29.
 */
abstract open class FieldBase {

    val COMMA = ","
    val EQUAL_MARK = "="
    val QUESTRION_MARK = "?"
    val AND = "AND"

    fun fieldName(field: Field): String {
        var varName = ""
        if (field.isAnnotationPresent(SerializedName::class.java)) {
            varName = field.getAnnotation(SerializedName::class.java).value
        } else {
            varName = field.getName()
        }
        return varName
    }

    /**
     * 去除语句中最后的逗号
     */
    fun deleteConnectorMark(sql: String,mark:String): String {
        if (sql.endsWith(mark)) {
            return sql.substring(0, sql.length - mark.length)
        }
        return sql
    }

    /**
     * 生成对应的SQL语句
     */
    abstract fun makeSqlStatement(args: Array<Any>): StatementResult

}