package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * 예외 누수 문제 해결
 * 체크 예외를 런타임 예외로 변경
 * MemberRepository 인터페이스 사용 * throws SQLException 제거
 */
@Slf4j
public class MemberRepositoryV4_2 implements MemberRepository{
    private final DataSource dataSource; // 외부에서 주입받는다
    private final SQLExceptionTranslator exTranslator;

    public MemberRepositoryV4_2(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }


    public Member save(Member member) {
        String sql = "insert into member(member_id, money) values(?, ?)"; // 쿼리문

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId()); // 첫번째 파라미터
            pstmt.setInt(2, member.getMoney()); // 두번째 파라미터
            pstmt.executeUpdate(); // 반환값 영향받은 row count
            return member;
        } catch (SQLException e) {
            throw exTranslator.translate("save", sql, e);
        } finally {
            close(con, pstmt, null);
        }

    }

    public Member findById(String memberId)  {
        String sql = "select * from member where member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery(); // 데이터를 조회할때 -> executeQuery()

            if (rs.next()) { // rs커서 이동, 처음 커서는 데이터를 가리키지 않음, return: true면 데이터가 있다는 뜻
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        } catch (SQLException e) {
            throw exTranslator.translate("findById", sql, e);
        } finally {
            close(con, pstmt, rs);
        }


    }
    public Member findById(Connection con, String memberId)  {
        String sql = "select * from member where member_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery(); // 데이터를 조회할때 -> executeQuery()

            if (rs.next()) { // rs커서 이동, 처음 커서는 데이터를 가리키지 않음, return: true면 데이터가 있다는 뜻
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }

        } catch (SQLException e) {
            throw exTranslator.translate("findById", sql, e);
        } finally {
            // connection은 여기서 닫지 않는다.
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
        }


    }

    public void update(String memberId, int money)  {
        String sql = "update member set money=? where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);
        } catch (SQLException e) {
            throw exTranslator.translate("update", sql, e);
        } finally {
            close(con, pstmt, null);
        }

    }

    public void update(Connection con, String memberId, int money)  {
        String sql = "update member set money=? where member_id=?";
        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);
        } catch (SQLException e) {
            throw exTranslator.translate("update", sql, e);

        } finally {
            // connection은 여기서 닫지 않는다.
            JdbcUtils.closeStatement(pstmt);
        }

    }

    public void delete(String memberId)  {
        String sql = "delete from member where member_id=?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw exTranslator.translate("delete", sql, e);

        } finally {
            close(con, pstmt, null);
        }
    }

    /**
     * JDBCUtils 사용
     * @param con
     * @param stmt
     * @param rs
     */
    private void close(Connection con, PreparedStatement stmt, ResultSet rs) { // 리소스 정리는 역순
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);

        DataSourceUtils.releaseConnection(con, dataSource); // dataSource에서 커넥션을 얻는 것을 추상화

    }

    private Connection getConnection()  {
        // 주의! 트랜잭션 동기화를 사용하려면 DatasourceUtils를 사용해야 한다.
        Connection con = DataSourceUtils.getConnection(dataSource); // 내가 직접 데이터 소스에서 꺼내지 않음
        log.info("DOGOD connection = {}, class = {}", con, con.getClass());
        return con;
    }
}
