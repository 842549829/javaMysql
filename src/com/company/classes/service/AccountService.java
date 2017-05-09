package com.company.classes.service;


import com.company.classes.irepository.IAccountRepository;
import com.company.classes.model.Account;
import com.company.classes.model.AccountCondition;

import java.util.List;

public class AccountService {

    public static void addAccount() throws Exception {
        IAccountRepository repository = null;
        try {
            repository = Factory.createIAccountRepository();
            Account account = new Account();
            account.steName("1");
            account.setValue("1");
            repository.add(account);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (repository != null) {
                repository.close();
            }
        }
    }

    public static List<Account> queryAll() throws Exception{
        try (IAccountRepository repository = Factory.createIAccountRepository()) {
            return  repository.queryAll();
        }
    }

    public static List<Account> queryPaging(AccountCondition condition) throws Exception{
        try (IAccountRepository repository = Factory.createIAccountRepository()) {
            return repository.queryPaging(condition);
        }
    }
}