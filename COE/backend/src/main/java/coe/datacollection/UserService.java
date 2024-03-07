package coe.datacollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Data;

@Data
@Service
public class UserService {
    @Autowired
    private UserRepository UserRepository;
	
	@Autowired
	private GenericRepository genericRepository;

    // create a new user
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user = UserRepository.save(user);
        return convertToDTO(user);
    }

    // retrieve all users
    public List<UserDTO> getAllUsers() {
		return convertToDTO(UserRepository.findAll());
    }

    public UserDTO getUser(Long userId) {
        User currentUser = UserRepository.findById(userId).orElse(null);
        return currentUser != null ? convertToDTO(currentUser) : null;
    }

    // update an existing user
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User cUser = UserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id)); // Customize exception as
                                                                                           // needed
		User nUser = convertFromDTO(userDTO);
		System.out.println("id " + id);
		System.out.println("uid " + cUser.getUserId());
        cUser.setFirstName(nUser.getFirstName() == null ? cUser.getFirstName() : nUser.getFirstName());
		cUser.setLastName(nUser.getLastName() == null ? cUser.getLastName() : nUser.getLastName());
		cUser.setDepartment(nUser.getDepartment() == null ? cUser.getDepartment() : nUser.getDepartment());
		//cUser.setRoleName(nUser.getUserRole() == null ? cUser.getUserRole() : nUser.getUserRole());
		
		cUser.setLoad(nUser.getLoad() == null ? cUser.getLoad() : nUser.getLoad());
		cUser.setRank(nUser.getRank() == null ? cUser.getRank() : nUser.getRank());
		cUser.setStatus(nUser.getStatus() == null ? cUser.getStatus() : nUser.getStatus());
		
		cUser.setJournals(nUser.getJournals());
		cUser.setConferences(nUser.getConferences());
		cUser.setBooks(nUser.getBooks());
		cUser.setChapters(nUser.getChapters());
		cUser.setGrants(nUser.getGrants());
		cUser.setResearchExperienceTotal(nUser.getResearchExperienceTotal());
		cUser.setResearchExperienceStudents(nUser.getResearchExperienceStudents());
		cUser.setPhdAdvised(nUser.getPhdAdvised());
		cUser.setPhdCompleted(nUser.getPhdCompleted());
		cUser.setMsCompleted(nUser.getMsCompleted());
		cUser.setPatentInnovation(nUser.getPatentInnovation());
		cUser.setUgMentored(nUser.getUgMentored());
		cUser.setAwards(nUser.getAwards());
		
		//cUser.setTeaching(nUser.getTeaching() == null ? cUser.getTeaching() : nUser.getTeaching());
		//cUser.setClasses(nUser.getClasses() == null ? cUser.getClasses() : nUser.getClasses());
		//cUser.setServiceActivity(nUser.getServiceActivity() == null ? cUser.getServiceActivity() : nUser.getServiceActivity());
    private final UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder; // For hashing and verifying passwords, done internally
    @Autowired
    private JwtUtil jwtUtil;

        cUser = UserRepository.save(cUser);
        return convertToDTO(cUser);
    }
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    // delete existing user
    public void deleteUser(Long id) {
        UserRepository.deleteById(id);
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil; //used for login authentication process
    }

    // convert User to UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
		
        userDTO.setId(user.getUserId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
		userDTO.setDepartment(user.getDepartment().getDepartment());
        userDTO.setRoleName(user.getUserRole().getRoleName());
		
		userDTO.setLoad(user.getLoad().getLoad());
		userDTO.setRank(user.getRank().getRank());
		userDTO.setStatus(user.getStatus().getStatus());
		
		userDTO.setJournals(user.getJournals());
		userDTO.setConferences(user.getConferences());
		userDTO.setBooks(user.getBooks());
		userDTO.setChapters(user.getChapters());
		userDTO.setGrants(user.getGrants());
		userDTO.setResearchExperienceTotal(user.getResearchExperienceTotal());
		userDTO.setResearchExperienceStudents(user.getResearchExperienceStudents());
		userDTO.setPhdAdvised(user.getPhdAdvised());
		userDTO.setPhdCompleted(user.getPhdCompleted());
		userDTO.setMsCompleted(user.getMsCompleted());
		userDTO.setPatentInnovation(user.getPatentInnovation());
		userDTO.setUgMentored(user.getUgMentored());
		userDTO.setAwards(user.getAwards());
		
		userDTO.setTeaching(user.getTeaching());
		userDTO.setClasses(user.getClasses());
		userDTO.setServiceActivity(user.getServiceActivity());
		
        return userDTO;
    }
	
	// bulk convert
	private List<UserDTO> convertToDTO(List<User> user) {
        List<UserDTO> DTOList = new ArrayList<UserDTO>();
        for (User current : user) {
			DTOList.add(convertToDTO(current));
		}
		
        return DTOList;
    public String authenticateAndGetJwt(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPin())) {
            return jwtUtil.generateToken(user); 
        } else {
            return null;
        }
    }

    // convert UserDTO to User
    public User convertFromDTO(UserDTO dto) {
        User user = new User();
        user.setUserId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

		user.setDepartment(genericRepository.findByString("Department", "deptName", dto.getDepartment()));
        user.setUserRole(genericRepository.findByString("UserRole", "roleName", dto.getRoleName()));
		user.setLoad(genericRepository.findByString("CLoad", "load", dto.getLoad()));
		user.setRank(genericRepository.findByString("URank", "rank", dto.getRank()));
		user.setStatus(genericRepository.findByString("UStatus", "status", dto.getStatus()));
		
		user.setJournals(dto.getJournals());
		user.setConferences(dto.getConferences());
		user.setBooks(dto.getBooks());
		user.setChapters(dto.getChapters());
		user.setGrants(dto.getGrants());
		user.setResearchExperienceTotal(dto.getResearchExperienceTotal());
		user.setResearchExperienceStudents(dto.getResearchExperienceStudents());
		user.setPhdAdvised(dto.getPhdAdvised());
		user.setPhdCompleted(dto.getPhdCompleted());
		user.setMsCompleted(dto.getMsCompleted());
		user.setPatentInnovation(dto.getPatentInnovation());
		user.setUgMentored(dto.getUgMentored());
		user.setAwards(dto.getAwards());
		
		    user.setTeaching(dto.getTeaching());
		    user.setClasses(dto.getClasses());
		    user.setServiceActivity(dto.getServiceActivity());

        // set anything else ...
        return user;
    
    //Spring Data JPA auto implements this method, allows fetching user entity based on the username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // export current user to JSON
    public String exportCurrentUserToJSON(Long currentUserId) throws Exception {
        UserDTO currentUserDTO = getUser(currentUserId);
        if (currentUserDTO != null) {
            return ExportUtility.exportToJSON(List.of(currentUserDTO));
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
	
	public List<UserDTO> findUsersByDepartmentId(int id) {
		return convertToDTO(UserRepository.findUsersByDepartmentId(id));
	}
	
	// get All Values for dropdown
	public List<String> getAllValues(String name)
	{
		List<String> result;
		switch (name) {
			case "department":
				result = genericRepository.findStringVals("Department", "deptName");
				break;
			case "load":
				result = genericRepository.findStringVals("CLoad", "load");
				break;
			case "rank":
				result = genericRepository.findStringVals("URank", "rank");
				break;
			case "status":
				result = genericRepository.findStringVals("UStatus", "status");
				break;
			case "semesterTerm":
				result = genericRepository.findStringVals("Semester", "semesterName.semesterName"); //findStringVals("Semester", "name");
				break;
			case "level":
				result = genericRepository.findStringVals("SLevel", "level");
				break;
			default:
				result = new ArrayList<String>();
				break;
		}
		return result;
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

