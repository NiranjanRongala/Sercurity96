package com.example.demo99;

import org.ietf.jgss.Oid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class Webcontroller {
    @GetMapping("/")
    public  String publicPage(){
        return "hello buddy";
    }
    @GetMapping("/private")
    public  String privatePage(Authentication authentication){
       //var authentication= SecurityContextHolder.getContext().getAuthentication();
        return "this is private["+ authentication.getName()+"]";
    }
    @GetMapping("/privates")
    public  String privatePage(){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        return "this is private["+ authentication.getName()+"]";
    }

    private static String getName(Authentication authentication){
        return Optional.of(authentication.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getEmail)
                .orElseGet(authentication::getName);
    }

}
