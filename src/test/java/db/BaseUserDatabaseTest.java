package db;

import static org.assertj.core.api.Assertions.assertThat;

import auth.AuthUserDetails;
import app.user.User;
import app.user.db.BaseUserDatabase;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseUserDatabaseTest {

    BaseUserDatabase baseUserDatabase = new BaseUserDatabase();

    @BeforeEach
    void setUp() {
        baseUserDatabase.deleteAll();
    }

    @Test
    void insert() {
        // given
        User user = User.builder()
                .userId("1")
                .email("1@1")
                .password("2")
                .name("3")
                .build();

        // when
        User savedUser = (User) baseUserDatabase.save(user);

        // then
        assertThat(savedUser).isEqualTo(user);

    }

    @Test
    void findByUserId() {
        // given
        User user = User.builder()
                .userId("1")
                .email("1@1")
                .password("2")
                .name("3")
                .build();

        // when
        User savedUser = (User) baseUserDatabase.save(user);
        User foundUser = (User) baseUserDatabase.findById(user.getUserId()).get();

        // then
        assertThat(foundUser).isEqualTo(savedUser);

    }


    @Test
    void findAll() {
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
        baseUserDatabase.save(user);
        baseUserDatabase.save(user2);

        // when
        Collection<AuthUserDetails> users = baseUserDatabase.findAll();

        // then
        assertThat(users.size()).isEqualTo(2);
    }

}