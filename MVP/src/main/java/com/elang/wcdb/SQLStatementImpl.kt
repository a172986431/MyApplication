package com.elang.wcdb

import com.elang.wcdb.statement.*
import java.lang.reflect.Proxy.newProxyInstance

/**
 * Created by zhuMH on 18/3/27.
 */

class SQLStatementImpl() {

    enum class FUN_NAME(val funName: String) {
        CREATE_TAB("createTab"),
        INSERT_DATA("insertData"),
        DELETE_DATA("deleteData"),
        QUERY_DATA("queryData"),
        UPDATE_DATA("updateData"),
        GSON_CURSOR("gsonCursor")
    }

    companion object {

        private var sqlStatement: ISqlStatement? = null

        @JvmStatic
        fun initSqlStatement(): ISqlStatement {
            if (sqlStatement == null) {
                sqlStatement = newProxyInstance(ISqlStatement::class.java.getClassLoader(),
                        arrayOf(ISqlStatement::class.java)) { proxy, method, args ->

                    when (method.name) {
                        FUN_NAME.CREATE_TAB.funName -> {
                            return@newProxyInstance CreateStatement().makeSqlStatement(args)
                        }
                        FUN_NAME.INSERT_DATA.funName -> {
                            return@newProxyInstance InsertStatement().makeSqlStatement(args)
                        }
                        FUN_NAME.DELETE_DATA.funName -> {
                            return@newProxyInstance DeleteStatement().makeSqlStatement(args)
                        }
                        FUN_NAME.QUERY_DATA.funName -> {
                            return@newProxyInstance QueryStatement().makeSqlStatement(args)
                        }
                        FUN_NAME.UPDATE_DATA.funName -> {
                            return@newProxyInstance UpdateStatement().makeSqlStatement(args)
                        }
                        FUN_NAME.GSON_CURSOR.funName -> {
                            return@newProxyInstance GsonCursor().cursorToObj(args)
                        }
                    }
                    return@newProxyInstance StatementResult("", null, null)
                } as ISqlStatement
            }
            return sqlStatement as ISqlStatement
        }
    }

}
