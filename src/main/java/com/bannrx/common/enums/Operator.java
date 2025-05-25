package com.bannrx.common.enums;


public enum Operator {
    EQUALTO("="),
    LESSTHAN("<"),
    LESSTHANOREQUALTO("<="),
    GREATERTHAN(">"),
    GREATERTHANOREQUALTO(">=");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public static Operator fromSymbol(String symbol) {
        for(Operator op : values()){
            if(op.symbol.equals(symbol)){
                return op;
            }
        }
        return Operator.EQUALTO;
    }
}
