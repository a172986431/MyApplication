package com.elang.wcdb

import android.database.Cursor
import com.elang.wcdb.statement.StatementResult

/**
 * Created by zhuMH on 18/3/27.
 */

interface ISqlStatement {

    fun createTab(obj: Any): StatementResult

    fun insertData(obj: Any): StatementResult

    fun deleteData(obj: Any): StatementResult

    fun queryData(obj: Any): StatementResult

    fun updateData(obj: Any): StatementResult

    fun <T> gsonCursor(t:T,cursor: Cursor) : MutableList<T>

}
