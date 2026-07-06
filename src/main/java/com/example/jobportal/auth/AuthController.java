package com.example.jobportal.auth;

import com.example.jobportal.dto.LoginRequestDto;
import com.example.jobportal.dto.LoginResponseDto;
import com.example.jobportal.dto.UserDto;
import com.example.jobportal.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

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


    private ResponseEntity<LoginResponseDto> buildErrorResponse(HttpStatus status,
                                                                String message) {
        return ResponseEntity
                .status(status)
                .body(new LoginResponseDto(message, null, null));
    }


}
