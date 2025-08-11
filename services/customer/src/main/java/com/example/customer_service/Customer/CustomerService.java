package com.example.customer_service.Customer;

import com.example.customer_service.S3.S3Client;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final S3Client s3Client;
    private final Keycloak keycloak;
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    @Value("${keycloak.realm}")
    private String realm;
    @Transactional
    public ResponseEntity<String> createCustomer(CustomerRequest request) {
        // user
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setFirstName(request.getName());
        user.setLastName(request.getName());

        // API create
        Response response = keycloak.realm(realm).users().create(user);
        if (response.getStatus() != 201 && response.getStatus() != 200 && response.getStatus() != 204) {
            throw new RuntimeException("Keycloak realm creation failed");
        }

        // id
        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        // *** SET PASSWORD HERE ***
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(request.getPassword());

        keycloak.realm(realm).users().get(userId).resetPassword(passwordCred);

        // role
        RoleRepresentation role = keycloak.realm(realm).roles().get(request.getRole()).toRepresentation();
        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(List.of(role));

        // save customer
        Customer customer = customerMapper.toCustomer(request);
        customer.setId(userId);
        customer = customerRepository.save(customer);

        return ResponseEntity.ok("Customer created");
    }


    public ResponseEntity<Object> login(LoginRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", request.getUsername());
        formData.add("password", request.getPassword());
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> body = response.getBody();

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken((String) body.get("access_token"));
            loginResponse.setRefreshToken((String) body.get("refresh_token"));
            loginResponse.setExpiresIn(Long.valueOf(body.get("expires_in").toString()));
            loginResponse.setRefreshExpiresIn(Long.valueOf(body.get("refresh_expires_in").toString()));
            loginResponse.setTokenType((String) body.get("token_type"));
            return ResponseEntity.ok(loginResponse);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public ResponseEntity<Object> myInfo(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        CustomerDTO customerDTO = customerMapper.toCustomerDTO(customer);
        return ResponseEntity.ok(customerDTO);
    }
}
