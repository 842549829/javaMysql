package com.company.classes.repository;

import com.company.classes.dbcommcon.BaseRepository;
import com.company.classes.dbcommcon.IUnitOfWork;
import com.company.classes.irepository.IAccountRepository;
import com.company.classes.model.Account;
import com.company.classes.model.AccountCondition;
import com.company.classes.model.ParametersType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository extends BaseRepository implements IAccountRepository<Account> {
    public AccountRepository(IUnitOfWork unit, String name) {
        super(unit, name);
    }

    @Override
    public void add(Account entity) throws Exception {
        String sql = "INSERT INTO Account(Name,Value) Value(?,?)";
        this.getPreparedStatement(sql);
        this.addParameter(ParametersType.STRING, entity.getName());
        this.addParameter(ParametersType.STRING, entity.getValue());
        this.executeNonQuery();
    }

    @Override
    public List<Account> queryAll() throws Exception {
        List<Account> accounts = null;
        String sql = "SELECT * FROM Account";
        this.getPreparedStatement(sql);
        ResultSet resultSet =  this.executeQuery();
        accounts = SetAccount(accounts, resultSet);
        return accounts;
    }

    @Override
    public List<Account> queryPaging(AccountCondition condition) throws Exception {
        List<Account> accounts = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM Account ");
        if (condition.getName() != null) {
            sql.append(" WHERE Name = ? ");
            this.addParameter(ParametersType.STRING, condition.getName());
        }
        sql.append(" ORDER BY Name LIMIT ?,?");
        this.getPreparedStatement(sql.toString());
        this.addParameter(ParametersType.INT, condition.getPaging().getStartRows());
        this.addParameter(ParametersType.INT, condition.getPaging().getPageSize());
        ResultSet resultSet = this.executeQuery();
        accounts = SetAccount(accounts, resultSet);
        return accounts;
    }

    private List<Account> SetAccount(List<Account> accounts, ResultSet ret) throws Exception {
        while (ret.next()) {
            if (accounts == null) {
                accounts = new ArrayList<Account>();
            }
            Account account = new Account();
            String name = ret.getString(1);
            account.steName(name);
            String value = ret.getString(2);
            account.setValue(value);
            accounts.add(account);
        }
        return accounts;
    }
}