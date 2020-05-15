package org.muses.jeeplatform.oauth.repository;

import org.muses.jeeplatform.oauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/05/15 17:34  修改内容:
 * </pre>
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    @Query(value = "select u from User u where u.username=:username and u.password=:password")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
