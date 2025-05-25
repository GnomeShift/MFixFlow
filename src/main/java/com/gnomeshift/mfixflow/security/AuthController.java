package com.gnomeshift.mfixflow.security;

import com.gnomeshift.mfixflow.security.request.LoginRequest;
import com.gnomeshift.mfixflow.security.request.ChangePasswordRequest;
import com.gnomeshift.mfixflow.security.request.RegisterRequest;
import com.gnomeshift.mfixflow.security.response.JwtResponse;
import com.gnomeshift.mfixflow.security.util.JwtUtils;
import com.gnomeshift.mfixflow.security.util.UserDetailsImpl;
import com.gnomeshift.mfixflow.user.*;
import com.gnomeshift.mfixflow.user.role.Role;
import com.gnomeshift.mfixflow.user.role.RoleRepository;
import com.gnomeshift.mfixflow.user.role.Roles;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final BruteforceProtectionService bruteforceProtection;
    private final UserService userService;

    public AuthController(AuthenticationManager authManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils,
                          BruteforceProtectionService bruteforceProtection, UserService userService) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.bruteforceProtection = bruteforceProtection;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();

        if (bruteforceProtection.isLocked(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new LockedException("Too many login attempts. Try again in " +
                            bruteforceProtection.getLockTime(email) + " minutes"));
        }

        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(auth);

            String jwt = jwtUtils.generateJwtToken(auth);

            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            bruteforceProtection.resetFailedLoginAttempts(email);
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
        }
        catch (BadCredentialsException e) {
            bruteforceProtection.registerFailedLogin(email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BadCredentialsException("Invalid credentials!"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new BadCredentialsException("Email is already in use!"));
        }

        User user = new User();

        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setFailedLoginAttempt(0);
        user.setLockTime(null);

        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Roles.USER)
                    .orElseThrow(() -> new EntityNotFoundException("Role not found"));
            roles.add(userRole);
        }
        else {
            strRoles.forEach(roleName -> {
                try {
                    Roles roleEnum = Roles.valueOf(roleName);
                    Role role;

                    if (roleEnum.equals(Roles.ADMIN)) {
                        role = roleRepository.findByName(Roles.ADMIN).orElseThrow(EntityNotFoundException::new);
                    }
                    else if (roleEnum.equals(Roles.MASTER)) {
                        role = roleRepository.findByName(Roles.MASTER).orElseThrow(EntityNotFoundException::new);
                    }
                    else {
                        role = roleRepository.findByName(Roles.USER).orElseThrow(EntityNotFoundException::new);
                    }

                    roles.add(role);
                }
                catch (EntityNotFoundException e) {
                    throw new EntityNotFoundException("Role " + roleName + " not found");
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cp")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            userService.changePassword(changePasswordRequest);
            return ResponseEntity.ok().build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
