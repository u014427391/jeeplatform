package org.jeeplatform.repository;


import org.jeeplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);
	
	@Query("from User u where u.username=:username and u.password=:password")
	User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
	
}
