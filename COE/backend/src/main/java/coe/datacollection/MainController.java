package coe.datacollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    private UserService userService;
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        String jwt = userService.authenticateAndGetJwt(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if (jwt != null) {
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }

    @Transactional (rollbackFor = Exception.class)
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegistrationDTO registrationDTO) {
        log.info("Received registration request: {}", registrationDTO);
        try{
                boolean istrue = userService.newUser(registrationDTO);
            if (istrue){
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(istrue, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Transaction failed due to exception:", e);
            return new ResponseEntity<>("Registration failed due to an error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 
}