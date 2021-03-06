# demo-crud-jwt

[REST API documentation](http://localhost:8080/v3/api-docs)

[POSTMAN](https://app.getpostman.com/join-team?invite_code=0db148b741bb7b382c54cf84711e22b0&ws=a11cc439-505f-4b90-a91f-25f2242f2af5)


Необходимо создать приложение реализующие REST API</br>
База данных Postgres</br>
Java 11 версии</br>
Собранный jar должен прилагаться к проекту</br>
Использовать maven</br>

База данных (по согласованию можно добавить поля и таблицы)</br>
## USERS:</br>
    ID: bigint
    NAME: varchar
    AGE: ?
    EMAIL: varchar
## PROFILES:</br>
    ID: bigint
    CASH: ? рубли + копейки
    USER_EMAIL: связь с таблицей users по ID
## PHONES:</br>
    ID: bigint
    VALUE: varchar
    USER_EMAIL: связь с таблицей users по ID

Требование к API и функционалу</br>

Реализовать CRUD для пользователя (обязательно сделать сервис слой)</br>
email уникальны</br>
добавить возможность фильтрации данных по разным полям: age(год или возраст), phone, name(like), email</br>
добавить пагинацию к списку пользователей</br>

Связь с таблицей USERS для PHONES и PROFILE  происходит через по ID</br>
У пользователя может быть более одного телефона (не более одного профиля)</br>
телефоны уникальны</br>
пользователь может сменить email если он не занят другим пользователям (пользователь может сменить только свои данные)</br>

Добавить swagger</br>

Добавить JWT token, механизм получения токена на ваше усмотрение (закрыть необходимый функционал авторизацией)</br>

Кеширование 2-го уровня</br>

Реализовать проверку входных данных</br>

Начальный счет указывается при создании пользователя</br>
раз в 20 секунд счета клиентов увеличиваются на 10% но не более 107% от начального депозита</br>

Добавить логирование</br>

Простые тесты</br>

# Результат:

## Table "public.users"
Column |          Type          | Collation | Nullable | Default</br>
-----------------------------------------------------------</br>
id     | bigint                 |           | not null |</br>
age    | integer                |           | not null |</br>
email  | character varying(255) |           | not null |</br>
name   | character varying(255) |           | not null |</br>

Indexes:</br>
"users_pkey" PRIMARY KEY, btree (id)</br>
"users_email_unique" UNIQUE CONSTRAINT, btree (email)</br>
Referenced by:</br>
TABLE "phones" CONSTRAINT "phones_users_id_fk" FOREIGN KEY (users_email) REFERENCES users(id)</br>
TABLE "profiles" CONSTRAINT "profiles_users_id_fk" FOREIGN KEY (users_email) REFERENCES users(id)</br>

## Table "public.phones"
Column      |          Type          | Collation | Nullable | Default</br>
-----------------------------------------------------------------------</br>
id          | bigint                 |           | not null |</br>
values      | character varying(255) |           | not null |</br>
users_email | bigint                 |           | not null |</br>

Indexes:</br>
"phones_pkey" PRIMARY KEY, btree (id)</br>
"phones_values_unique" UNIQUE CONSTRAINT, btree ("values")</br>
Foreign-key constraints:</br>
"phones_users_id_fk" FOREIGN KEY (users_email) REFERENCES users(id)</br>

## Table "public.profiles"</br>
Column    |     Type      | Collation | Nullable | Default</br>
--------------------------------------------------------------</br>
id          | bigint        |           | not null |</br>
cash        | numeric(19,2) |           | not null |</br>
users_email | bigint        |           | not null |</br>
Indexes:</br>

"profiles_pkey" PRIMARY KEY, btree (id)</br>
Foreign-key constraints:</br>
"profiles_users_id_fk" FOREIGN KEY (users_email) REFERENCES users(id)</br>

