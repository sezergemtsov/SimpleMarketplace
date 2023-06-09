# Проект - простой маркетплейс

## Настройка и первый запуск

Вариант бэк-энда простенького маркетплейса с ограниченным функционалом нв базе фреймворка Spring

Полный список требований описан [ здесь ](https://github.com/sezergemtsov/SimpleMarketplace/blob/master/TechnicalTask.md)

Приложение сконфигурировано с использованием Hibernate и Liquibase поэтому при первом запуске для инициализации всех таблиц
установите следующие настройки в файле application.properties:

spring.jpa.hibernate.ddl-auto=create
spring.liquibase.enabled=false

После этого следует остановить приложение, поменяйте настройки:

spring.jpa.hibernate.ddl-auto=none
spring.liquibase.enabled=true

Теперь все созданные таблицы заполняются миграциями и отслеживаются

## Авторизация

Приложение использует встроенный функционал Spring Security поэтому для доступа доступа к любым функциям требуется авторизация

В базу пользователей предзаполнены два пользователя с разными правами:

Администратор с расширенными правами name = "admin" password = "admin"
Пользователь со стандартными правами name = "user" password = "user"


## Функционал приложения

Как было описано в первом разделе проект написан согласно требованиям

Краткий обзор используемых эндпоинтов можно посмотреть [ здесь ](https://github.com/sezergemtsov/SimpleMarketplace/blob/master/endpoints.md)
