package pl.jstk.service;

import pl.jstk.to.UserTo;

public interface UserService {

	UserTo findUserByName(String name);
}
