insert into roles(name) values ('ROLE_USER'), ('ROLE_ADMIN');

insert into users(user_name, password, active) values ('Admin', '$2a$10$H8betvRlG91b.P0INAsfnufN4Wc2WClZN9VvR2XpqvlXbRLTnWLpW', true);

insert into users_roles(user_id, role_id) values (1, 2);

insert into cuisine_types(name) values ('american'), ('asian'), ('british'), ('caribbean'), ('central europe'), ('chinese'), ('eastern europe'), ('french'),('greek'), ('indian'), ('italian'), ('japanese'), ('korean'), ('kosher'), ('mediterranean'), ('mexican'), ('middle eastern'), ('nordic'), ('south american'), ('south east asian'), ('world');

insert into day_names(name) values ('Monday'), ('Tuesday'), ('Wednesday'), ('Thursday'), ('Friday'), ('Saturday'), ('Sunday');

insert into meal_types(name, fraction) values ('Breakfast', 0.3), ('Lunch', 0.4), ('Dinner', 0.3);

insert into healths(name) values ('alcohol-free'), ('celery-free'), ('crustacean-free'), ('dairy-free'), ('egg-free'), ('fish-free'), ('fodmap-free'), ('gluten-free'), ('immuno-supportive'), ('keto-friendly'), ('kidney-friendly'), ('kosher'), ('low-potassium'), ('low-sugar'), ('lupine-free'), ('Mediterranean'), ('mollusk-free'), ('mustard-free'), ('No-oil-added'), ('paleo'), ('peanut-free'), ('pecatarian'), ('pork-free'), ('red-meat-free'), ('sesame-free'), ('shellfish-free'), ('soy-free'), ('sugar-conscious'), ('sulfite-free'), ('tree-nut-free'), ('vegan'), ('vegetarian'), ('wheat-free');

insert into diets(name) values ('balanced'), ('high-fiber'), ('high-protein'), ('low-carb'), ('low-fat'), ('low-sodium');

insert into dish_types(name) values ('biscuits and cookies'), ('bread'), ('cereals'), ('condiments and sauces'), ('desserts'), ('drinks'), ('egg'), ('ice cream and custard'), ('pancake'), ('pasta'), ('pastry'), ('pies and tarts'), ('pizza'), ('preps'), ('preserve'), ('salad'), ('sandwiches'), ('seafood'), ('side dish'), ('soup'), ('special occasions'), ('starter'), ('sweets');

insert into activity_levels(name, value, description) values ('extremely inactive', 1.4, 'cerebral palsy patient'), ('sedentary', 1.6, 'office worker getting little or no exercise'), ('moderately active', 1.75, 'construction worker or person running one hour daily'), ('vigorously active', 2, 'agricultural worker (non mechanized) or person swimming two hours daily'), ('extremely active', 2.4, 'competitive cyclist');