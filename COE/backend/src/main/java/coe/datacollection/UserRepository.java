package coe.datacollection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//the following replaces @Query("SELECT u FROM general_info u WHERE u.username = :username")
	User findByUsername(String username);
}
