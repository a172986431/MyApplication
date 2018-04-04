package com.elang.wcdb.statement

/**
 * Created by zhuMH on 18/3/29.
 */
class InsertStatement : FieldBase(){

    val INSERT_SQL = "REPLACE INTO %s (%s) values (%s);"

    override fun makeSqlStatement(args: Array<Any>): StatementResult {
        if (args == null || args.isEmpty()) {
            return StatementResult("",null,null)
        }
        val obj = args[0]
        var tabName = obj::class.java.getSimpleName()
        var fieldNames = ""
        var valuesList = ""
        // 获取对象obj的所有属性域
        val fields = obj::class.java.getDeclaredFields()
        var data = Array<String>(fields.size,{""})
        var index = 0
        for (field in fields) {
            field.isAccessible = true
            val values = field.get(obj)
            field.isAccessible = false
            if (values != null) {
                // 对于每个属性，获取属性名
                var varName = fieldName(field)
                fieldNames += varName + COMMA
                valuesList += QUESTRION_MARK + COMMA
                data[index] = values as String
                index ++
            }
        }
        return StatementResult(String.format(INSERT_SQL, tabName,
                deleteConnectorMark(fieldNames,COMMA),deleteConnectorMark(valuesList,COMMA)),data,null)
    }
}