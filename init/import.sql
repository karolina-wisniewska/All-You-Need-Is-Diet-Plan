create database if not exists dietPlan character set utf8mb4 collate utf8mb4_unicode_ci;

update mysql.user set host = '%' where user='root';

use dietPlan;

create table activity_levels(id tinyint auto_increment primary key, description varchar(255) null, name varchar(30) not null, value double(3, 2) not null) engine = InnoDB;

create table cuisine_types(id tinyint auto_increment primary key, name varchar(30) not null) engine = InnoDB;

create table day_names(id tinyint auto_increment primary key, name varchar(10) not null) engine = InnoDB;

create table diets(id tinyint auto_increment primary key, name varchar(30) not null) engine = InnoDB;
    
create table dish_types(id tinyint auto_increment primary key, name varchar(30) not null) engine = InnoDB;
    
create table healths(id tinyint auto_increment primary key, name varchar(30) not null) engine = InnoDB;
    
create table meal_types(id tinyint auto_increment primary key, fraction double(3, 2) not null, name varchar(30) not null) engine = InnoDB;
    
create table recipes(id bigint auto_increment primary key, external_link varchar(255) not null, label varchar(255) not null) engine = InnoDB;
    
create table roles(id int auto_increment primary key, name varchar(20) not null) engine = InnoDB;
    
create table users(id bigint auto_increment primary key, active bit null, password text not null, user_name varchar(30) not null) engine =InnoDB;       
       
create table users_diets(user_id bigint not null, diet_id tinyint not null, constraint FK4khmk9vseaotrk542v317t8fw foreign key (diet_id) references diets (id), constraint FKrk822usrcxy8oty8on87ilmor foreign key (user_id) references user_params (user_id)) engine = InnoDB;   
    
create table users_healths(user_id bigint not null, health_id tinyint not null, constraint FKak976rk0a5x5bamusnnd4myc6 foreign key (user_id) references user_params (user_id), constraint FKct60pfarn6nykjy947uroagi5 foreign key (health_id) references healths (id)) engine = InnoDB;
    
create table users_roles(user_id bigint not null, role_id int not null, primary key (user_id, role_id), constraint FK2o0jvgh89lemvvo17cbqvdxaa foreign key (user_id) references users (id),    constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles (id)) engine = InnoDB;             
 
create table user_params(user_id bigint not null primary key, daily_calories int unsigned null, date_of_birth date not null, dream_weight double(5, 2) not null, gender smallint null, height tinyint unsigned not null, activity_level_id tinyint not null, cuisine_type_id tinyint null, constraint FKd5gwh5sm7n2ct1m4naikqiuds foreign key (user_id) references users (id), constraint FKf6utia7rj55uwdyqe4dr39gpf foreign key(cuisine_type_id) references cuisine_types (id), constraint FKsns3qwt447o2ue7td9iarjove foreign key (activity_level_id) references activity_levels (id))    engine = InnoDB; 
        
create table plans( user_id bigint not null primary key, constraint FKbybv5po44ssyv6svnv062dwrf foreign key (user_id) references users (id)) engine = InnoDB;

create table latest_weights(id bigint auto_increment primary key, weight double not null, weighting_date datetime(6) not null, user_id bigint null, constraint FKmnni458fsw2d6pt7537k1p5ql        foreign key (user_id) references users (id)) engine = InnoDB;    

create table diet_plan_items(id bigint auto_increment primary key, day_name_id tinyint not null, meal_type_id tinyint not null, plan_id bigint not null, recipe_id bigint not null, constraint FK1l2hoxdadjuel7otedurhu694 foreign key (meal_type_id) references meal_types (id), constraint FK62gqlmkvcd9u5g5i6gwtq4taf foreign key(day_name_id) references day_names (id), constraint FK9p4k2l60y7738c5j2a67vl8xe foreign key (plan_id) references plans (user_id), constraint FKc657k7iml8xf2sy0mh28o1933 foreign key (recipe_id) references recipes (id)) engine = InnoDB;

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
