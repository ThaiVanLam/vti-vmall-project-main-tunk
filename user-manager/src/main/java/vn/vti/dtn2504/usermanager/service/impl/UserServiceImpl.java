package vn.vti.dtn2504.usermanager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.vti.dtn2504.usermanager.dto.request.CreateAccountRequest;
import vn.vti.dtn2504.usermanager.dto.response.CreateAccountResponse;
import vn.vti.dtn2504.usermanager.entity.User;
import vn.vti.dtn2504.usermanager.repository.UserRepository;
import vn.vti.dtn2504.usermanager.service.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        User user = new User();
        user.setUsername(createAccountRequest.getUsername());
        user.setEmail(createAccountRequest.getEmail());
        user.setPassword(passwordEncoder.encode(createAccountRequest.getPassword()));
        User userSaved = userRepository.save(user);
        CreateAccountResponse createAccountResponse = new CreateAccountResponse();
        createAccountResponse.setUsername(userSaved.getUsername());
        createAccountResponse.setEmail(userSaved.getEmail());
        return createAccountResponse;
    }
}
