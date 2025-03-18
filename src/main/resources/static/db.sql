-- Таблица пользователей
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       age INT NOT NULL,
                       weight DOUBLE PRECISION NOT NULL,
                       height DOUBLE PRECISION NOT NULL,
                       goal VARCHAR(50) NOT NULL,
                       daily_calories DOUBLE PRECISION NOT NULL
);

-- Таблица блюд
CREATE TABLE dishes (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        calories_per_serving INT NOT NULL,
                        protein DOUBLE PRECISION NOT NULL,
                        fat DOUBLE PRECISION NOT NULL,
                        carbs DOUBLE PRECISION NOT NULL
);

-- Таблица приемов пищи
CREATE TABLE meals (
                       id SERIAL PRIMARY KEY,
                       user_id INT NOT NULL,
                       date DATE NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Промежуточная таблица для связи Meal и Dish
CREATE TABLE meal_dishes (
                             meal_id INT NOT NULL,
                             dish_id INT NOT NULL,
                             PRIMARY KEY (meal_id, dish_id),
                             FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE,
                             FOREIGN KEY (dish_id) REFERENCES dishes(id) ON DELETE CASCADE
);



