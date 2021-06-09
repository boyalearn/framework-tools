package com.frameworktool.sql;

import net.sf.jsqlparser.JSQLParserException;

public class SQLMain {
    public static void main(String[] args) throws JSQLParserException {
        JSQLParser sqlParser = new JSQLParser();
        sqlParser.addWhere();
        sqlParser.addLimit();
    }
}
