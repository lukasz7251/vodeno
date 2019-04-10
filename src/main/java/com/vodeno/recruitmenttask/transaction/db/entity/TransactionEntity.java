package com.vodeno.recruitmenttask.transaction.db.entity;

import com.vodeno.recruitmenttask.account.db.entity.AccountEntity;
import com.vodeno.recruitmenttask.common.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "Transaction")
@Table(name = "transaction")
public class TransactionEntity extends BaseEntity {

    @Column(columnDefinition = "Decimal(10,2) default '0.00'", nullable = false)
    private BigDecimal amount = new BigDecimal("0.00");

    @Column(columnDefinition = "Decimal(10,2) default '0.00'", nullable = false)
    private BigDecimal commission = new BigDecimal("0.00");

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private AccountEntity sender;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    private AccountEntity receiver;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountEntity getSender() {
        return sender;
    }

    public void setSender(AccountEntity sender) {
        this.sender = sender;
    }

    public AccountEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(AccountEntity receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

}
