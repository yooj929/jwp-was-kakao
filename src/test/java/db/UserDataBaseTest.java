package db;

import static org.assertj.core.api.Assertions.assertThat;

import user.User;
import infra.db.UserDataBase;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDataBaseTest {

    @BeforeEach
    void setUp(){
        UserDataBase.deleteAll();
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
        User savedUser = UserDataBase.insert(user);

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
        User savedUser = UserDataBase.insert(user);
        User foundUser = UserDataBase.findByUserId(user.getUserId());

        // then
        assertThat(foundUser).isEqualTo(savedUser);

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
        UserDataBase.insert(user);
        UserDataBase.insert(user2);

        // when
        Collection<User> users = UserDataBase.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
    }

}