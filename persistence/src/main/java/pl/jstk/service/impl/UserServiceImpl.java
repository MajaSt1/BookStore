package pl.jstk.service.impl;

import pl.jstk.entity.User;
import pl.jstk.mapper.UserMapper;
import pl.jstk.repository.UserRepository;
import pl.jstk.service.UserService;
import pl.jstk.to.UserTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserTo findUserByName(String name) {
        User entity = userRepository.findUserByName(name);
        return userMapper.map2To(entity);
    }

}
