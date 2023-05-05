package pl.coderslab.allyouneedisdietplan.service;

import org.json.JSONObject;
import pl.coderslab.allyouneedisdietplan.entity.security.User;

public interface HighChartService {

  JSONObject getWeightDataJson(User user);
}
