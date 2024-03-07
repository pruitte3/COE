package coe.datacollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import coe.datacollection.EntityDependencies.URank;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Data;

@Data
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder; // For hashing and verifying passwords, done internally
    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil; //used for login authentication process
    }

    public String authenticateAndGetJwt(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPin())) {
            return jwtUtil.generateToken(user); 
        } else {
            return null;
        }
    }
    
    //Spring Data JPA auto implements this method, allows fetching user entity based on the username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean newUser(RegistrationDTO registrationDTO) throws CustomExceptions
    {
        if (isValidAccount(registrationDTO.getFirstName(), registrationDTO.getLastName())) {
            throw new CustomExceptions("There is an account with that username:" + registrationDTO.getUsername());
        }
        User user = new User();        
        user.setUsername(registrationDTO.getUsername());
        user.setPin(passwordEncoder.encode(registrationDTO.getPin()));
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setRank(new URank(registrationDTO.getRank()));
        List <Department> departments = departmentRepository.findByNameContaining(registrationDTO.getDepartment());
        if (departments == null) {
            throw new CustomExceptions("Department is null." + registrationDTO.getDepartment());
        }
        for (Department dept : departments) {
            if (dept.getDeptName().equals(registrationDTO.getDepartment())) {
                user.setDepartment(dept);
            }
        }
        user.setAssignedRole(new UserRole(registrationDTO.getRole()));
        user = userRepository.save(user);
        log.info("Creating new user with details: {}", user);       
        return true;
    }
    
    public boolean isValidAccount(String fName, String lName) {
        List <User> users = userRepository.findAll();
        for (User testUser : users) {
            if (testUser.getLastName().equals(lName) & testUser.getFirstName().equals(fName)) {
                return true;
            }
        }
        return false;
    } 
}

