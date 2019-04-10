package com.vodeno.recruitmenttask.user.db.entity;

import com.vodeno.recruitmenttask.account.db.entity.AccountEntity;
import com.vodeno.recruitmenttask.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<AccountEntity> accountList = new ArrayList<>();

    public List<AccountEntity> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<AccountEntity> accountList) {
        this.accountList = accountList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
