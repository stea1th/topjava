package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> userMap = new LinkedHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        Stream.of(new User("Vadim", "vad@rambler.ru", "999", Role.ROLE_ADMIN, Role.ROLE_USER),
                new User("Bubsel", "bubsel@mail.ru", "1234", Role.ROLE_USER),
                new User("Pukeschka", "puki@google.com", "6543", Role.ROLE_USER),
                new User("Frantik", "fran@gmx.de", "4321", Role.ROLE_USER))
                .forEach(this::save);
                //.collect(Collectors.toMap(User::getId, Function.identity()));

    }

    /*public static void main(String[] args) {
        //userMap.forEach((key, value) -> System.out.println(key + "= " + value));
        InMemoryUserRepositoryImpl impl = new InMemoryUserRepositoryImpl();
        impl.userMap.forEach((key, value) -> System.out.println(key + "= " + value));
        impl.getAll().forEach(System.out::println);
    }*/

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userMap.remove(id, get(id));
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        int id = user.isNew()? generateId() : user.getId();
        user.setId(id);
        return userMap.put(id, user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userMap.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userMap.values()
                .stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getId))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return getAll().stream()
                .filter(x-> x.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public int generateId(){
        log.info("generateId");
        return counter.incrementAndGet();
    }
}
