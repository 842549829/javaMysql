package com.company.classes.irepository;

import com.company.classes.model.AccountCondition;

import java.util.List;

public interface IAccountRepository<Account> extends IRepository<Account> {
    List<Account> queryAll() throws Exception;

    List<Account> queryPaging(AccountCondition condition) throws  Exception;
}
