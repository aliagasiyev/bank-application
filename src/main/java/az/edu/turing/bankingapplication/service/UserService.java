package az.edu.turing.bankingapplication.service;

import az.edu.turing.bankingapplication.domain.entity.UserEntity;
import az.edu.turing.bankingapplication.domain.repository.UserRepository;
import az.edu.turing.bankingapplication.exception.UserNotFoundException;
import az.edu.turing.bankingapplication.mapper.config.UserMapper;
import az.edu.turing.bankingapplication.model.dto.request.UserRequest;
import az.edu.turing.bankingapplication.model.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void create(UserRequest userDto) {
        UserEntity userEntity = userMapper.toUserEntity(userDto);
        userRepository.save(userEntity);
    }

    public List<UserResponse> getAll() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public Optional<UserResponse> getById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return Optional.of(userMapper.toUserDto(userEntity));
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse update(Long id, UserRequest userDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userEntity.setFullName(userDto.fullName());
        userRepository.save(userEntity);

        return userMapper.toUserDto(userEntity);
    }
}
