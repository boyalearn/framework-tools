package com.frameworktool.sql;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class JSQLParser {

    public void addWhere() throws JSQLParserException {
        Select stmt = (Select) CCJSqlParserUtil.parse("SELECT a.col1,a.col2 FROM table a WHERE id = 30");
        System.out.println(stmt.toString());
        Table table = (Table) ((PlainSelect) stmt.getSelectBody()).getFromItem();
        if (table.getName().contains("table") && !table.getAlias().getName().isEmpty()) {
            Expression oldExpression = ((PlainSelect) stmt.getSelectBody()).getWhere();
            final Expression envCondition = CCJSqlParserUtil.parseCondExpression(table.getAlias().getName() + ".tenant_id=?");
            AndExpression andExpression = new AndExpression(oldExpression, envCondition);
            ((PlainSelect) stmt.getSelectBody()).setWhere(andExpression);
        }
        System.out.println(stmt.toString());
    }

    public void addLimit() throws JSQLParserException {
        Select stmt = (Select) CCJSqlParserUtil.parse("SELECT a.col1,a.col2 FROM table a WHERE id = 30");
        System.out.println(stmt.toString());
        Limit limit = new Limit();
        limit.setOffset(new LongValue(1));
        limit.setRowCount(new LongValue(10));
        ((PlainSelect) stmt.getSelectBody()).setLimit(limit);
        System.out.println(stmt.toString());
    }
}
