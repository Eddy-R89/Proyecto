package com.parada.model;

import java.math.BigDecimal;

/** Un solo lugar para cambiar el símbolo de moneda. */
public final class Moneda {
    public static final String SIMBOLO = "Q";
    private Moneda() {}
    public static String fmt(BigDecimal v) {
        return SIMBOLO + String.format(java.util.Locale.US, "%,.2f", v);
    }
}
