package com.Explicacao.API.service;

import com.Explicacao.API.model.User;
import com.Explicacao.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        var userSave = repository.save(user);
        return userSave;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findByID(Long id) {
        return repository.findById(id);
    }

    public User putUser(Long id, User userUpdate) {

        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User updtate = userOptional.get();

            updtate.setName(userUpdate.getName());
            updtate.setEmail(userUpdate.getEmail());
            updtate.setContact(userUpdate.getContact());

            User updateUser = repository.save(updtate);
            return updateUser;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found");
        }
    }

    public User patchUser(Long id, User userDetails) {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isPresent()) {
            User updateEspecific = optionalUser.get();

            if (userDetails.getName() != null) {
                updateEspecific.setName(userDetails.getName());
            }
            if (userDetails.getEmail() != null) {
                updateEspecific.setEmail(userDetails.getEmail());
            }
            if (userDetails.getContact() != null) {
                updateEspecific.setContact(userDetails.getContact());
            }
            updateEspecific = repository.save(updateEspecific);
            return updateEspecific;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Found");
    }

    public void deleteId(Long id) {
        repository.deleteById(id);
    }


}
