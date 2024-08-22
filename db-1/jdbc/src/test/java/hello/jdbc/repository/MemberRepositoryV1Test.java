package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {
    MemberRepositoryV1 repository; // DI

    @BeforeEach
    void beforeEach() {
        // 기존 DriverManager -> 항상 새로운 커넥션 획득
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        HikariDataSource dataSource = new HikariDataSource(); // -> 커넥션 풀에서 하나만 반환하며 이용
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException {
        log.info("start");

        // save
        Member member = new Member("memberV3", 10000);
        repository.save(member); // 0

        // findById
        Member findMember = repository.findById(member.getMemberId()); // 1
        log.info("find member = {}", findMember);
        assertThat(findMember).isEqualTo(member);

        // update
        repository.update(member.getMemberId(), 2000); // 2
        Member updateMember = repository.findById(member.getMemberId()); // 3
        assertThat(updateMember.getMoney()).isEqualTo(2000);

        // delete
        repository.delete(member.getMemberId()); // 4
        assertThatThrownBy(() -> repository.findById(member.getMemberId())) // 5
                .isInstanceOf(NoSuchElementException.class);
    }

}