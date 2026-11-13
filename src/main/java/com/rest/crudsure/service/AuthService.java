package com.rest.crudsure.service;

import com.rest.crudsure.dto.AuthResponse;
import com.rest.crudsure.dto.LoginRequest;
import com.rest.crudsure.dto.RegisterRequest;
import com.rest.crudsure.entity.Usuario;
import com.rest.crudsure.repository.UsuarioRepository;
import com.rest.crudsure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Usuario usuario = new Usuario(
                request.getNombre(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );

        usuarioRepository.save(usuario);
        String jwtToken = jwtService.generateToken(usuario);

        return new AuthResponse(jwtToken, usuario.getEmail(), usuario.getNombre());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String jwtToken = jwtService.generateToken(usuario);

        return new AuthResponse(jwtToken, usuario.getEmail(), usuario.getNombre());
    }
}