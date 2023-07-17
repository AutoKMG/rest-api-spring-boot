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
    private static int usersCount = 0;

    static {
        users.add(new User(++usersCount, "Khaled", LocalDate.now().minusYears(20)));
        users.add(new User(++usersCount, "Shahd", LocalDate.now().minusYears(20)));
        users.add(new User(++usersCount, "Galal", LocalDate.now().minusYears(19)));
    }
    public List<User> findAll(){
        return users;
    }
    public User findOne(int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }
    public User save(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
    }
}
