package org.muses.jeeplatform.core.dao.repository.admin;

import org.muses.jeeplatform.core.entity.admin.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	User findByUsername(String username);

	@Query("from User u where u.username=:username and u.password=:password")
	User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	@Query("from User u where u.id=:id")
	User findById(@Param("id") int id);

	@Query("from User u where date_format(u.lastLogin,'yyyy-MM-dd') between date_format((:startDate),'yyyy-MM-dd') and date_format((:endDate),'yyyy-MM-dd')")
	Page<User> searchU(@Param("startDate")Date startDate, @Param("endDate")Date endDate, Pageable pageable);

}
