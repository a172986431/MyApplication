package com.elang.wcdb.statement

import com.elang.wcdb.annotation.Primary

/**
 * Created by zhuMH on 18/3/30.
 */
class QueryStatement : FieldBase() {

    val DELETE_SQL = "SELECT * FROM %s where %s"

    override fun makeSqlStatement(args: Array<Any>): StatementResult {
        if (args == null || args.isEmpty()) {
            return StatementResult("", null, null)
        }
        val obj = args[0]
        var tabName = obj::class.java.getSimpleName()
        var fieldNames = ""
        // 获取对象obj的所有属性域
        val fields = obj::class.java.getDeclaredFields()
        var data = Array<String>(fields.size, { "" })
        var index = 0
        for (field in fields) {
            val values = field.get(obj)
            if (values != null) {
                if (field.isAnnotationPresent(Primary::class.java)) {
                    fieldNames = fieldName(field) + EQUAL_MARK + QUESTRION_MARK
                    data[index] = values as String
                    break
                } else {
                    // 对于每个属性，获取属性名
                    var varName = fieldName(field)
                    fieldNames += varName + EQUAL_MARK + QUESTRION_MARK + AND
                    data[index] = values as String
                    index++
                }
            }
        }
        return StatementResult(String.format(DELETE_SQL, tabName, deleteConnectorMark(fieldNames, AND)), data, null)
    }
}