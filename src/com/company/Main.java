package com.company;

import com.company.classes.model.Account;
import com.company.classes.model.AccountCondition;
import com.company.classes.model.Apple;
import com.company.classes.model.Paging;
import com.company.classes.service.AccountService;
import com.company.classes.util.HttpRequest;
import com.company.classes.util.JsonSerialization;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        //get();
        //post();
        //json();
        //queryAll();
        queryPaging();
    }

    private static void json() {
        Apple apple = new Apple("<a href=\"https://www.baidu.com/s?wd=%E7%BA%A2%E5%AF%8C%E5%A3%AB&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1d9uyRduHTdm19-ujP-PjTY0ZwV5Hcvrjm3rH6sPfKWUMw85HfYnjn4nH6sgvPsT6KdThsqpZwYTjCEQLGCpyw9Uz4Bmy-bIi4WUvYETgN-TLwGUv3EnHb4nHnvnWb1P16YnHmkPjRYPs\" target=\"_blank\" class=\"baidu-highlight\">红富士</a>", "red", "big");
        String jsonString = JsonSerialization.toJson(apple);
        System.out.println(jsonString);

        Apple obj = JsonSerialization.parentJson(jsonString, Apple.class);
    }

    private static void get() {
        String s = HttpRequest.sendGet("http://localhost:6144/Home/RequestString", "key=123&v=456", 30 * 1000);
        System.out.println(s);
    }

    private static void post() {
        String sr = HttpRequest.sendPost("http://localhost:38599/AliPayNotice/PaySdkNotice", "key=123&v=456", 30 * 1000);
        System.out.println(sr);
    }

    private static void add() {
        try {
            AccountService.addAccount();
            System.out.println("OK");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void queryAll() {
        try {
            List<Account> accounts = AccountService.queryAll();
            for (Account item : accounts) {
                System.out.println("Name:" + item.getName() + "  Value:" + item.getValue());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void queryPaging() {
        try {
            Paging paging = new Paging();
            paging.setPageSize(2);
            AccountCondition condition = new AccountCondition(paging);
            //condition.setName("没有大");
            List<Account> accounts = AccountService.queryPaging(condition);
            for (Account item : accounts) {
                System.out.println("Name:" + item.getName() + "  Value:" + item.getValue());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
