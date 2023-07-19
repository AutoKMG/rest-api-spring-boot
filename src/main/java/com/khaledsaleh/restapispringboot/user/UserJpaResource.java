package com.khaledsaleh.restapispringboot.user;

import com.khaledsaleh.restapispringboot.jpa.PostRepository;
import com.khaledsaleh.restapispringboot.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {
    private UserRepository userRepository;
    private PostRepository postRepository;
    public UserJpaResource(UserRepository userRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/jpa/users/{userIndex}")
    public EntityModel<User> retrieveUser(@PathVariable int userIndex){
        Optional<User> user = userRepository.findById(userIndex);
        if (user.isEmpty()){
            throw new UserNotFoundException("id:"+userIndex);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/jpa/users/{userIndex}")
    public void deleteUser(@PathVariable int userIndex){
        userRepository.deleteById(userIndex);
    }
    @GetMapping("/jpa/users/{userIndex}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int userIndex){
        Optional<User> user = userRepository.findById(userIndex);
        if (user.isEmpty()){
            throw new UserNotFoundException("id:"+userIndex);
        }
        return user.get().getPosts();
    }
    @GetMapping("/jpa/users/{userIndex}/posts/{postIndex}")
    public Post retrievePostById(@PathVariable int userIndex, @PathVariable int postIndex){
        Optional<User> user = userRepository.findById(userIndex);
        if (user.isEmpty()){
            throw new UserNotFoundException("id:"+userIndex);
        }
        Optional<Post> post = postRepository.findById(postIndex);
        if (post.isEmpty()){
            throw new IllegalArgumentException();
        }
        return post.get();
    }
    @PostMapping("/jpa/users/{userIndex}/posts")
    public ResponseEntity<Post> createPostForUser(@PathVariable int userIndex, @Valid @RequestBody Post post){
        Optional<User> user = userRepository.findById(userIndex);
        if (user.isEmpty()){
            throw new UserNotFoundException("id:"+userIndex);
        }
        post.setUser(user.get());
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
