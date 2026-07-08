package com.example.jobportal.auth;

import com.example.jobportal.dto.LoginRequestDto;
import com.example.jobportal.dto.LoginResponseDto;
import com.example.jobportal.dto.RegisterRequestDto;
import com.example.jobportal.dto.UserDto;
import com.example.jobportal.entity.JobPortalUser;
import com.example.jobportal.entity.Role;
import com.example.jobportal.repository.JobPortalUserRepository;
import com.example.jobportal.repository.RoleRepository;
import com.example.jobportal.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final JobPortalUserRepository jobPortalUserRepository;
    private final RoleRepository roleRepository;
    private final CompromisedPasswordChecker compromisedPasswordChecker;

    @PostMapping("/login/public")
    public ResponseEntity<LoginResponseDto>apiLogin(@RequestBody LoginRequestDto loginRequestDto){
        try{
            var resultAuthentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.username(),loginRequestDto.password()));
            String jwtToken = jwtUtil.generateJwtToken(resultAuthentication);


            var userDto = new UserDto();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(),userDto,jwtToken));
        }catch (BadCredentialsException ex) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED,
                    "Invalid username or password");
        } catch (Exception ex) {
            ex.printStackTrace();   // or log.error(...)

            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,

                    ex.getMessage());
        }

    }
    @PostMapping("/register/public")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto registerRequestDto){
        CompromisedPasswordDecision decision = compromisedPasswordChecker.check(registerRequestDto.password());
        if(decision.isCompromised()){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("password","choose a strong password"));
        }
        Optional<JobPortalUser> existingUser = jobPortalUserRepository.findUserByEmailAndMobileNumberAllIgnoreCase(registerRequestDto.email(),registerRequestDto.mobileNumber());
        if(existingUser.isEmpty()){
            JobPortalUser jobPortalUser = new JobPortalUser();
            BeanUtils.copyProperties(registerRequestDto,jobPortalUser);
            jobPortalUser.setPasswordHash(passwordEncoder.encode(registerRequestDto.password()));
            Role role = roleRepository.findRoleByName("ROLE_JOB_SEEKER").orElseThrow(()->new IllegalArgumentException("Role not Found"));
            jobPortalUser.setRole(role);
            jobPortalUserRepository.save(jobPortalUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already present");


    }


    private ResponseEntity<LoginResponseDto> buildErrorResponse(HttpStatus status,
                                                                String message) {
        return ResponseEntity
                .status(status)
                .body(new LoginResponseDto(message, null, null));
    }


}
