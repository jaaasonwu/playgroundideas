package com.playgroundideas.playgroundideas;

import com.playgroundideas.playgroundideas.model.User;

/**
 * Created by Ferdinand on 14/09/2017.
 */

public class TestUtil {

    static User createUser(long id) {
        User user = new User(0, id, "Hans", "Smith", "hans.smith@gmail.com", "0416777888");
        return user;
    }
}
