package com.example.security.api;

import com.example.security.entity.Account;
import com.example.security.entity.Credential;
import com.example.security.server.AccountServer;
import com.example.security.server.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping(value = "/api/v2")
public class AccountApi {
    @Autowired
    private AccountServer accountServer;
    @Autowired
    ProductServer productServer;
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public String seed(){
        accountServer.register("admin", "123456", 1,1);
        accountServer.register("user", "123456", 2,1);
        productServer.save("mon 1", Double.valueOf(123));
        productServer.save("mon 2", Double.valueOf(123));
        productServer.save("mon 3", Double.valueOf(123));
        productServer.save("mon 4", Double.valueOf(123));
        productServer.save("mon 5", Double.valueOf(123));
        productServer.save("mon 6", Double.valueOf(123));
        productServer.save("mon 7", Double.valueOf(123));
        productServer.save("mon 8", Double.valueOf(123));
        productServer.save("mon 9", Double.valueOf(123));
        productServer.save("mon 10", Double.valueOf(123));
        return "ok";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        Credential credential = accountServer.login(username, password);
        if (credential != null){
            return credential.getAccessToken();
        }else {
            return  "Login Thất bại";
        }
    }
    @RequestMapping(value = "/getInfo/{id}", method = RequestMethod.GET)
    public Optional<Account> login(@PathVariable Long id) {
        Optional<Account> accountOptional =  accountServer.getUser(id);
        return accountOptional;
    }


}
