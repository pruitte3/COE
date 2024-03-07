package coe.datacollection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u.department FROM User u WHERE u.userId = :userId")
	public Department findDepartmentByUserId(@Param("userId") Long userId);

	@Query("SELECT u FROM User u WHERE u.department = :fdep")
	public List<User> findUsersByDepartment(@Param("fdep") Department fdep);
	
	@Query("SELECT u FROM User u WHERE u.department.deptId = :id")
	public List<User> findUsersByDepartmentId(@Param("id") Integer id);
	//the following replaces @Query("SELECT u FROM general_info u WHERE u.username = :username")
	User findByUsername(String username);
}
