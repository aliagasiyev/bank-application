package az.edu.turing.bankingapplication.enums;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Bank {
    PASHA_BANK(new BigDecimal("0.02"), new BigDecimal("0.015")),
    KAPITAL_BANK(new BigDecimal("0.03"), new BigDecimal("0.02")),
    UNIBANK(new BigDecimal("0.025"), new BigDecimal("0.03")),
    LEOBANK(new BigDecimal("0.0"), new BigDecimal("0.0"));

    private final BigDecimal sendCommission;
    private final BigDecimal receiptCommission;

    Bank(BigDecimal sendCommission, BigDecimal receiptCommission) {
        this.sendCommission = sendCommission;
        this.receiptCommission = receiptCommission;
    }
}
