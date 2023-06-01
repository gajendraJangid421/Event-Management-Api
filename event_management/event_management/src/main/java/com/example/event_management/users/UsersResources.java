package com.example.event_management.users;

import com.example.event_management.jpa.UsersRepository;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UsersResources {

    private UsersRepository usersRepository;

    public UsersResources(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
    @GetMapping(path = "/api/users")
    public List<UsersDetails> retrieveAllUsersList() {
        return usersRepository.findAll();
    }

    @PostMapping(path = "/api/users")
    public ResponseEntity<UsersDetails> createNewUser(@Valid @RequestBody UsersDetails usersDetails) {
        UsersDetails savedUsersDetails = usersRepository.save(usersDetails);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUsersDetails.getUsersId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/api/users/{id}")
    public void deleteUser(@PathVariable int id) {

        usersRepository.deleteById(id);
    }
}
