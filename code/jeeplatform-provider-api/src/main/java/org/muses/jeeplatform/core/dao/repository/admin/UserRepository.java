package org.muses.jeeplatform.core.dao.repository.admin;

import org.muses.jeeplatform.core.entity.admin.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	User findByUsername(String username);

	@Query(value = "select u from User u where u.username=:username and u.password=:password")
	User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	@Query( value = "select u from User u where u.id=:id")
	User findById(@Param("id") int id);

	@Query(value = "select u from User u where u.lastLogin BETWEEN :startDate and :endDate")
	Page<User> searchU(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update User u set u.loginIp =?1 where u.username = ?2")
	int updateLoginIpById( String loginIp,  String username);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update User u set u.password =?1 where u.username = ?2")
	int updatePasswordById(String password, String username);
}
