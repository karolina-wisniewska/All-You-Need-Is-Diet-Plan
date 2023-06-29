create database if not exists dietPlan character set utf8mb4 collate utf8mb4_unicode_ci;

use dietPlan;

create table if not exists dietPlan.activity_levels(id tinyint auto_increment primary key, description varchar(255) null, name varchar(30) not null, value double(3, 2) not null) engine = InnoDB;

create table if not exists dietPlan.cuisine_types(id tinyint auto_increment primary key, name varchar(30) not null) engine = InnoDB;

create table if not exists dietPlan.day_names(id tinyint auto_increment primary key, name varchar(10) not null) engine = InnoDB;

create table if not exists dietPlan.diets(id tinyint auto_increment primary key, name varchar(30) not null) engine = InnoDB;
    
create table if not exists dietPlan.dish_types(id tinyint auto_increment primary key, name varchar(30) not null) engine = InnoDB;
    
create table if not exists dietPlan.healths(id tinyint auto_increment primary key, name varchar(30) not null) engine = InnoDB;
    
create table if not exists dietPlan.meal_types(id tinyint auto_increment primary key, fraction double(3, 2) not null, name varchar(30) not null) engine = InnoDB;
    
create table if not exists dietPlan.recipes(id bigint auto_increment primary key, external_link varchar(255) not null, label varchar(255) not null) engine = InnoDB;
    
create table if not exists dietPlan.roles(id int auto_increment primary key, name varchar(20) not null) engine = InnoDB;
    
create table if not exists dietPlan.users(id bigint auto_increment primary key, active bit null, password text not null, user_name varchar(30) not null) engine =InnoDB;

create table if not exists dietPlan.user_params(user_id bigint not null primary key, daily_calories int unsigned null, date_of_birth date not null, dream_weight double(5, 2) not null, gender smallint null, height tinyint unsigned not null, activity_level_id tinyint not null, cuisine_type_id tinyint null, foreign key (user_id) references users (id), foreign key(cuisine_type_id) references cuisine_types (id), foreign key (activity_level_id) references activity_levels (id)) engine = InnoDB;

create table if not exists dietPlan.users_diets(user_id bigint not null, diet_id tinyint not null, foreign key (diet_id) references diets (id), foreign key (user_id) references user_params (user_id)) engine = InnoDB;

create table if not exists dietPlan.users_healths(user_id bigint not null, health_id tinyint not null, foreign key (user_id) references user_params (user_id), foreign key (health_id) references healths (id)) engine = InnoDB;

create table if not exists dietPlan.users_roles(user_id bigint not null, role_id int not null, primary key (user_id, role_id), foreign key (user_id) references users (id), foreign key (role_id) references roles (id)) engine = InnoDB;

create table if not exists dietPlan.plans( user_id bigint not null primary key, foreign key (user_id) references users (id)) engine = InnoDB;

create table if not exists dietPlan.latest_weights(id bigint auto_increment primary key, weight double not null, weighting_date datetime(6) not null, user_id bigint null, foreign key (user_id) references users (id)) engine = InnoDB;

create table if not exists dietPlan.diet_plan_items(id bigint auto_increment primary key, day_name_id tinyint not null, meal_type_id tinyint not null, plan_id bigint not null, recipe_id bigint not null, foreign key (meal_type_id) references meal_types (id), foreign key(day_name_id) references day_names (id), foreign key (plan_id) references plans (user_id), foreign key (recipe_id) references recipes (id)) engine = InnoDB;