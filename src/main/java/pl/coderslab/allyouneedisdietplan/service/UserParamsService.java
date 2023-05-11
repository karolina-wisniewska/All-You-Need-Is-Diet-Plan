package pl.coderslab.allyouneedisdietplan.service;

import pl.coderslab.allyouneedisdietplan.entity.UserParams;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

public interface UserParamsService {

  void save(UserParams userParams);
  UserParams findByUser(User user);
  Long calculateDailyCalories(UserParams userParams);
  String calculateSuccessDate(UserParams userParams);
}
