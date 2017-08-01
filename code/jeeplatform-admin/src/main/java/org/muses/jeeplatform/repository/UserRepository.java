package org.muses.jeeplatform.repository;



import org.muses.jeeplatform.model.entity.Menu;
import org.muses.jeeplatform.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	User findByUsername(String username);
	
	@Query("from User u where u.username=:username and u.password=:password")
	User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	@Query("from User u where u.id=:id")
	User findById(@Param("id") int id);

}
