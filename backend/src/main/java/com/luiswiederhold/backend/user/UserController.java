package com.luiswiederhold.backend.user;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/{id}")
    public User getUserByID(@PathVariable int id) {
        Optional<User> userRep = userRepository.findById(id);
        return userRep.orElse(null);
    }

    @PostMapping("/create")
    public User setUserByID(@RequestBody User user) {
        System.out.print("Post request received");
        return userRepository.save(user);
    }

    @DeleteMapping("/delete")
    public String deleteUserByID(@RequestBody int id) {
        System.out.print("Delete request received");

        if(!userRepository.existsById(id)) return "Id "+ id + "doesn't exist in DB";

        userRepository.deleteById(id);

        return "Deleted user with ID: " + id;
    }

}
