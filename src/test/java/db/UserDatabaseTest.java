package db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import businuess.user.User;
import businuess.user.db.UserDatabase;

class UserDatabaseTest {

    UserDatabase userDatabase = new UserDatabase();
    @BeforeEach
    void setUp(){
        userDatabase.deleteAll();
    }

    @Test
    void insert(){
        // given
        User user = User.builder()
                .userId("1")
                .email("1@1")
                .password("2")
                .name("3")
                .build();

        // when
        User savedUser = userDatabase.save(user);

        // then
        assertThat(savedUser).isEqualTo(user);

    }

    @Test
    void findByUserId(){
        // given
        User user = User.builder()
                .userId("1")
                .email("1@1")
                .password("2")
                .name("3")
                .build();

        // when
        User savedUser = userDatabase.save(user);
        Optional<User> foundUser = userDatabase.findById(user.getUserId());

        // then
        assertThat(foundUser.get()).isEqualTo(savedUser);

    }


    @Test
    void findAll(){
        // given
        User user = User.builder()
                .userId("1")
                .email("1@1")
                .password("2")
                .name("3")
                .build();

        User user2 = User.builder()
                .userId("2")
                .email("1@1")
                .password("2")
                .name("3")
                .build();
        userDatabase.save(user);
        userDatabase.save(user2);

        // when
        Collection<User> users = userDatabase.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
    }

}