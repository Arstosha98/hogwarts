# Тестирование веб-приложений в Spring Boot

> Привет! На связи домашнее задание урока 3.6. Тестирование веб-приложений в Spring Boot.

На прошлом уроке мы узнали много интересного про потоки данных и научились работать с файлами: получать потоки данных в запросе и сохранять их в БД и на локальный диск.

Цель сегодняшней домашней работы — научиться писать тесты для приложений Spring Boot двумя способами: используя TestRestTemplate и используя WebMvcTest.

*Среднее время выполнения: 90 минут.*
>

Домашняя работа будет состоять из двух заданий. Реализовывать их нужно в двух параллельных ветках.

## Задание 1

**Шаг 1**

Создать класс для тестирования в пакете test. Создать по одному тесту на каждый эндпоинт контроллера StudentController, используя TestRestTemplate.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b3ece0ea-b544-4e72-9f11-12866926f3df/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b3ece0ea-b544-4e72-9f11-12866926f3df/Рисунок41.png" width="40px" /> **Критерии оценки:** В пакете test создан класс для тестирования StudentController. Для тестирования использовался TestRestTemplate. Для каждого эндпоинта контроллера StudentController создан как минимум один тест.

</aside>

**Шаг 2**

Создать еще один класс для тестирования в пакете test. Создать по одному тесту на каждый эндпоинт контроллера FacultyController, используя TestRestTemplate.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/fdffee5c-f806-474f-ba68-202b9a3cd117/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/fdffee5c-f806-474f-ba68-202b9a3cd117/Рисунок41.png" width="40px" /> **Критерии оценки:** В пакете test создан класс для тестирования FacultyController. Для тестирования использовался TestRestTemplate. Для каждого эндпоинта контроллера FacultyController создан как минимум один тест.

</aside>

## Задание 2

**Шаг 1**

Создать класс для тестирования в пакете test. Создать по одному тесту на каждый эндпоинт контроллера StudentController, используя WebMvcTest.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b3ece0ea-b544-4e72-9f11-12866926f3df/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b3ece0ea-b544-4e72-9f11-12866926f3df/Рисунок41.png" width="40px" /> **Критерии оценки:** В пакете test создан класс для тестирования StudentController. Для тестирования использовался WebMvcTest. Для каждого эндпоинта контроллера StudentController создан как минимум один тест.

</aside>

**Шаг 2**

Создать еще один класс для тестирования в пакете test. Создать по одному тесту на каждый эндпоинт контроллера FacultyController, используя WebMvcTest.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/fdffee5c-f806-474f-ba68-202b9a3cd117/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/fdffee5c-f806-474f-ba68-202b9a3cd117/Рисунок41.png" width="40px" /> **Критерии оценки:** В пакете test создан класс для тестирования FacultyController. Для тестирования использовался WebMvcTest. Для каждого эндпоинта контроллера FacultyController создан как минимум один тест.

</aside>