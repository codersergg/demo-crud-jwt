# demo-crud-jwt

Необходимо создать приложение реализующие REST API
База данных Postgres
Java 11 версии
Собранный jar должен прилагаться к проекту
Использовать maven

База данных (по согласованию можно добавить поля и таблицы)
USERS:
ID: bigint
NAME: varchar
AGE: ?
EMAIL: varchar
PROFILES:
ID: bigint
CASH: ? рубли + копейки
USER_EMAIL: связь с таблицей users по ID
PHONES:
ID: bigint
VALUE: varchar
USER_EMAIL: связь с таблицей users по ID

Требование к API и функционалу
Реализовать CRUD для пользователя (обязательно сделать сервис слой)
email уникальны
добавить возможность фильтрации данных по разным полям: age(год или возраст), phone, name(like), email
добавить пагинацию к списку пользователей
Связь с таблицей USERS для PHONES и PROFILE  происходит через по ID
У пользователя может быть более одного телефона (не более одного профиля)
телефоны уникальны
пользователь может сменить email если он не занят другим пользователям (пользователь может сменить только свои данные)
Добавить swagger
Добавить JWT token, механизм получения токена на ваше усмотрение (закрыть необходимый функционал авторизацией)
Кеширование 2-го уровня
Реализовать проверку входных данных
Начальный счет указывается при создании пользователя
раз в 20 секунд счета клиентов увеличиваются на 10% но не более 107% от начального депозита
Добавить логирование
Простые тесты

