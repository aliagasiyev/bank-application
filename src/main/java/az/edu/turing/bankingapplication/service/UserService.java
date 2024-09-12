package az.edu.turing.bankingapplication.service;

import az.edu.turing.bankingapplication.model.dto.request.UserRequest;
import az.edu.turing.bankingapplication.model.dto.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void create(UserRequest userDto);

    List<UserResponse> getAll();

    Optional<UserResponse> getById(Long id);

    void deleteAll();

    void deleteById(Long id);

    UserResponse update(Long id, UserRequest userDto);
}
