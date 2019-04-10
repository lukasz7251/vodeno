package com.vodeno.recruitmenttask.account.db.entity;

import com.vodeno.recruitmenttask.common.BaseEntity;
import com.vodeno.recruitmenttask.user.db.entity.UserEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "Account")
@Table(name = "account")
public class AccountEntity extends BaseEntity {

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal balance = new BigDecimal("0.00");

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
