package com.example.secondhomework.service;

import com.example.secondhomework.converter.UserConverter;
import com.example.secondhomework.dao.UserDAO;
import com.example.secondhomework.dto.UserDTO;
import com.example.secondhomework.entity.User;
import com.example.secondhomework.exception.UserIsNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Transactional // For delete method
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User findByUsername(String username) {
        Boolean isExist = userDAO.existsUserByUsername(username);

        if (!isExist) {
            throw new UserIsNotExistException("User with username : " + username + " is not exists!");
        }
        return userDAO.findUserByUsername(username);
    }


    public User findByPhone(String phone) {
        Boolean isExist = userDAO.existsUserByPhone(phone);
        if (!isExist) {
            throw new UserIsNotExistException("User with phone : " + phone + " is not exists!");
        }
        return userDAO.findUserByPhone(phone);
    }

    public User save(User user) {
        return userDAO.save(user);
    }

    public User findByUsernameAndPhone(String username, String phone){return userDAO.findUserByUsernameAndPhone(username, phone);}

    @Transactional // For delete method
    public void deleteByUsernameAndPhone(String username, String phone) {

        Boolean isExists = userDAO.existsByUsernameAndPhone(username, phone);

        if(!isExists)
        {
            throw new UserIsNotExistException("User phone : " + phone + " and user username : " + username + " is not matching with each other!");
        }
        userDAO.deleteByUsernameAndPhone(username, phone);
    }

    public UserDTO update(UserDTO userDTO,Long id) {

        var user = userDAO.findById(id).orElse(null);
        if(Objects.isNull(user))
        {
            throw new UserIsNotExistException("User id : " + userDTO.getId() + " is not found!");
        }
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setUsername(userDTO.getUsername());
        user = userDAO.save(user);
        return UserConverter.INSTANCE.convertUserToUserDTO(user);
    }

}
