[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/KHsyuHcO)
# Лабораторная работа: Тестирование REST API и сервисного слоя

## Цель работы
Научиться тестировать REST API с использованием Postman и Rest Assured, а также мокировать зависимости при тестировании сервисного слоя.

## Задания

1. **Тестирование контроллера `UserAnalyticsController`**
    - Используя **Postman**, протестировать основные эндпоинты контроллера (для этого можно изменить исходный код контроллера):
        - `POST /register` – регистрация пользователя.
        - `POST /recordSession` – запись сессии пользователя.
        - `GET /totalActivity` – получение общего времени активности пользователя.
        - `GET /inactiveUsers` – поиск неактивных пользователей.
        - `GET /monthlyActivity` – получение метрик активности пользователя за месяц.
    - Проверить обработку ошибок (отсутствие параметров, некорректные данные).

2. **Тестирование `UserAnalyticsController` с использованием Rest Assured**
    - Написать **JUnit-тесты** с использованием **Rest Assured**, проверяющие корректность работы API.
    - Добавить тесты на граничные случаи и обработку ошибок.

3. **Тестирование `UserStatusService` с моками**
    - Использовать **Mockito** для создания моков `UserAnalyticsService`.
    - Протестировать метод `getUserStatus(userId)`, проверив корректность возвращаемого статуса.
    - Протестировать метод `getUserLastSessionDate(userId)`, замокав вызов `getUserSessions()` и проверив корректность работы.
    - Используйте метод verify для проверки корректности вызова замоканного метода

## Рекомендации
- В Postman создайте коллекцию запросов и сохраните их для демонстрации.

## Ожидаемый результат
- Тесты успешно проходят, API корректно обрабатывает запросы.
- Тесты находят ошибки, где API некорректно обрабатывает запросы
- В сервисном слое успешно мокируются зависимости, тесты проверяют бизнес-логику.

