package com.elang.wcdb.statement

import android.database.Cursor
import java.lang.reflect.Type

/**
 * Created by zhuMH on 18/4/3.
 */
class GsonCursor {

    fun cursorToObj(args:Array<Any>) : MutableList<Object>{
        val objs = ArrayList<Object>()
        if (args == null || args.isEmpty() || args.size < 2) {
            return objs
        }
        var obj = args[0]
        var cursor : Cursor = args[1] as Cursor
        if (obj == null) {
            return objs
        }
        try {
            val objClass = obj::class.java
            // 获取对象obj的所有属性域
            val fields = objClass.getDeclaredFields()
            val size = fields.size
            var cursorIndexs = Array<Int>(size,{0})
            var isEmpty = true
            while (cursor != null && cursor.moveToNext()) {
                val genericity = objClass.newInstance()
                for (index in 0..(size - 1)) {
                    val field = fields[index]
                    // 对于每个属性，获取属性名
                    var varName = field.name
                    if (isEmpty){
                        cursorIndexs[index] = cursor.getColumnIndex(varName)
                    }
                    field.set(genericity,cursor.getString(cursorIndexs[index]))
                }
                isEmpty = false
                objs.add(genericity as Object)
            }
        }catch (e:Exception){
        }finally {
            if (cursor != null && !cursor.isClosed){
                cursor.close()
            }
        }
        return objs
    }

}