package com.rafael.producto.model.entity;

import lombok.Getter;

@Getter
public enum TipoIva {
    IVA_4(4),
    IVA_12(12),
    IVA_21(21);

    private final int porcentaje;

    TipoIva(int porcentaje) {
        this.porcentaje = porcentaje;
    }

}