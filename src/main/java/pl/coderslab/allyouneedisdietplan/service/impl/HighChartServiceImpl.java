package pl.coderslab.allyouneedisdietplan.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.coderslab.allyouneedisdietplan.entity.LatestWeight;
import pl.coderslab.allyouneedisdietplan.entity.security.User;
import pl.coderslab.allyouneedisdietplan.service.HighChartService;
import pl.coderslab.allyouneedisdietplan.service.LatestWeightService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HighChartServiceImpl implements HighChartService {

  private final LatestWeightService latestWeightService;
  @Override
  public JSONObject getWeightDataJson(User user) {
    List<LatestWeight> userWeights = latestWeightService.findByUserOrderByWeightingDateAsc(user);
    JSONArray jsonDate = new JSONArray();
    JSONArray jsonWeight = new JSONArray();
    JSONObject json = new JSONObject();
    userWeights.forEach(weight->{
      jsonDate.put(weight.getWeightingDate());
      jsonWeight.put(weight.getWeight());
    });
    json.put("date", jsonDate);
    json.put("weight", jsonWeight);
    return json;
  }
}
