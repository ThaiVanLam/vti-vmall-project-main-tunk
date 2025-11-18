package vn.vti.dtn2504.usermanager.service;

import vn.vti.dtn2504.usermanager.dto.request.CreateAccountRequest;
import vn.vti.dtn2504.usermanager.dto.response.CreateAccountResponse;

public interface UserService {
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);
}
