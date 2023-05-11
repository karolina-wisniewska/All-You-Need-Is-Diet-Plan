package pl.coderslab.allyouneedisdietplan.service.security;

import pl.coderslab.allyouneedisdietplan.entity.security.Role;

public interface RoleService {

  Role findByName(String name);

}
