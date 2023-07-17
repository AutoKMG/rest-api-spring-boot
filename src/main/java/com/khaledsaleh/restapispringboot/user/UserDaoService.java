package com.khaledsaleh.restapispringboot.user;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Khaled", LocalDate.now().minusYears(20)));
        users.add(new User(2, "Shahd", LocalDate.now().minusYears(20)));
        users.add(new User(3, "Galal", LocalDate.now().minusYears(19)));
    }
    public List<User> findAll(){
        return users;
    }
    public User findOne(int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().get();
    }

}
