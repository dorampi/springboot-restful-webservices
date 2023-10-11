package com.dxl.springboot.service.impl;

import com.dxl.springboot.dto.UserDto;
import com.dxl.springboot.entity.User;
import com.dxl.springboot.exception.EmailAlreadyExistsException;
import com.dxl.springboot.exception.ResourceNotFoundException;
import com.dxl.springboot.repository.UserRepository;
import com.dxl.springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dxl.springboot.mapper.AutoUserMapper.AUTO_USER_MAPPER;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    // since spring boot 3.x.x we don't need to use @Autowired annotation since it has one parametrized constructor
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        // Convert UserDto into User JPA Entity
        // User user = UserMapper.mapToUser(userDto);
//        User user = modelMapper.map(userDto, User.class);

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists for user");
        }

        User user = AUTO_USER_MAPPER.mapToUser(userDto);


        User savedUser = userRepository.save(user);

        // Convert User JPA entity to UserDto and return
        // UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);
        // model mapper
//        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);

        return AUTO_USER_MAPPER.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
//        return UserMapper.mapToUserDto(user);
        // model mapper
        // return modelMapper.map(user, UserDto.class);

        //mapstruct
        return AUTO_USER_MAPPER.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
//        return userList.stream()
//                .map(UserMapper::mapToUserDto)
//                .collect(Collectors.toList());

        // model mapper
//        return userList.stream()
//                .map(user -> modelMapper.map(user, UserDto.class))
//                .collect(Collectors.toList());

        // mapstruct
        return userList.stream()
                .map(AUTO_USER_MAPPER::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user) {
        final Long userId = user.getId();

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());

        User updatedUser = userRepository.save(existingUser);
//        return UserMapper.mapToUserDto(updatedUser);
        // model mapper
//        return modelMapper.map(updatedUser, UserDto.class);

        // mapstruct
        return AUTO_USER_MAPPER.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        userRepository.delete(existingUser);
    }
}
