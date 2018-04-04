package com.elang.wcdb.statement

import com.elang.wcdb.annotation.Primary
import java.lang.reflect.Field

/**
 * Created by zhuMH on 18/3/29.
 *
 * 创建数据表
 */
class CreateStatement : FieldBase() {

    val CREATE_SQL = "CREATE TABLE IF NOT EXISTS %s (%s);"

    /**
     * 生成创建表的SQL语句
     */
    override fun makeSqlStatement(args: Array<Any>): StatementResult {
        if (args == null || args.isEmpty()) {
            return StatementResult("",null,null)
        }
        val obj = args[0]
        var tabName = obj::class.java.getSimpleName()
        var fieldNames = ""
        // 获取对象obj的所有属性域
        val fields = obj::class.java.getDeclaredFields()
        for (field in fields) {
            // 对于每个属性，获取属性名
            var varName = fieldName(field)
            val att = getSQLAttribute(field)
            fieldNames += varName + att + COMMA
        }
        return StatementResult(String.format(CREATE_SQL, tabName, deleteConnectorMark(fieldNames,COMMA)),null,null)
    }

    /**
     * 获取field的属性
     */
    fun getSQLAttribute(field: Field): String {
        var attribute = " TEXT"
        val fieldName = field.type.simpleName
        if (Boolean::class.java!!.getSimpleName() == fieldName) {
            attribute = " BOOLEAN"
        } else if (Int::class.java!!.getSimpleName() == fieldName) {
            attribute = " INTEGER"
        }
        if (field.isAnnotationPresent(Primary::class.java)) {
            attribute += " PRIMARY KEY"
        }
        return attribute
    }
}