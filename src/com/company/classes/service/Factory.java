package com.company.classes.service;


import com.company.classes.irepository.IAccountRepository;
import com.company.classes.repository.AccountRepository;

public final class Factory {
    public final static IAccountRepository createIAccountRepository() {
        return new AccountRepository(null, "");
    }
}
