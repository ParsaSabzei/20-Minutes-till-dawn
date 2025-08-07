package ap.project.service;

import ap.project.model.App;
import ap.project.model.User;

import java.util.List;

public class UserService {
    private List<User> users;

    public UserService(List<User> users) {
        this.users = users;
    }

    public boolean isUserExist(String username) {
        return users.stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public void addUser(User user) {
        users.add(user);
    }


    public User findUserByUserName(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public void remove(User loggedInUser) {
        users.remove(loggedInUser);
    }
}
