# Создание API. Swagger UI, Postman

> Привет! На связи домашнее задание урока 3.2. Создание API. Swagger UI, Postman.

Цели сегодняшней домашней работы: 1) создать полноценное RESTful-приложение, используя структуры model, service, controller; 2) добавить эндпоинты для CRUD-операций над сущностями; добавить swagger и протестировать с помощью Postman.

*Среднее время выполнения: 120 минут.*
>

<aside>
⚠️ Результатом работы третьего курса будет свой веб-сервис — аналог международного учебного заведения волшебников Хогвартс. Это новый проект, который вы будете делать самостоятельно шаг за шагом. С каждым уроком функционал будет наращиваться, а сложность реализации повышаться.

</aside>

**Шаг 1**

Создать простое SpringBoot-приложение. В качестве группы и артефакта проекта можно использовать следующие значения: groupId — ru.hogwarts, artefact — school.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b14dfe45-44d5-4eb8-adc8-029a9fae0e64/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b14dfe45-44d5-4eb8-adc8-029a9fae0e64/Рисунок41.png" width="40px" /> **Критерий оценки:** есть приложение с Maven, которое корректно запускается.

</aside>

**Шаг 2**

1. Создать каталоги model, service, controller в пакете ru.hogwarts.school. В model создать два класса: Student, Faculty.

Класс **Student** имеет следующие поля: **Long id, String name, int age.**

Класс **Faculty** имеет следующие поля: **Long id, String name, String color.**

1. Добавить конструкторы к классам, с помощью которых можно проинициализировать все поля (создать объект класса через new и передать в него все параметры).
2. Создать методы для получения и изменения переменных класса. Сами переменные должны быть private.

Если есть желание, можно добавить свои поля, которые могут быть вам необходимы.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3374bb2b-3d5b-4f99-82b3-520ffe6e01d3/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/3374bb2b-3d5b-4f99-82b3-520ffe6e01d3/Рисунок41.png" width="40px" /> **Критерии оценки:** Есть структура приложения, присутствуют каталоги model, service, controller. Созданы два класса Student и Faculty в директории model. В них присутствует конструктор, необходимые поля, геттеры и сеттеры.

</aside>

**Шаг 3**

1. В каталоге **service** cоздать два класса сервисов для моделей: StudentService и FacultyService.
2. В каждом из них завести HashMap, в котором следует хранить модели. Например Map<Long, Student>.
3. Также создать счетчик идентификатора, который будет инкрементироваться при каждом добавлении нового объекта модели в HashMap.
4. В каждом сервисе реализовать CRUD-методы для создания, чтения, изменения и удаления сущностей.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e7f7cd11-a234-4053-aef3-5b358008510e/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e7f7cd11-a234-4053-aef3-5b358008510e/Рисунок41.png" width="40px" /> **Критерии оценки:** Созданы два сервиса StudentService и FacultyService. В них реализованы CRUD-методы. Сервисы должны хранить состояние путем аккумулирования данных в HashMap.

</aside>

**Шаг 4**

1. В каталоге **controller** cоздать два класса контроллеров для сервисов: StudentController и FacultyController.
2. В них добавить RequestMapping (“student” для StudentController и “faculty” для FacultyController).
3. В каждом контроллере реализовать эндпоинты для создания, получения, изменения и удаления сущностей, используя все правила формирования REST-запросов: GET-методы для получения данных, POST — для создания…

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0fdd9164-6ba9-45f7-b2c8-fa72d6d170f2/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0fdd9164-6ba9-45f7-b2c8-fa72d6d170f2/Рисунок41.png" width="40px" /> **Критерии оценки:** Созданы два контроллера как минимум с четырьмя CRUD-эндпоинтами. URL-маппинг и методы (GET, POST) используются по правилам построения REST-запросов, более подробно описанных в шпаргалке предыдущего урока.

</aside>

**Шаг 5**

1. Добавить фильтрацию студентов по возрасту.

Для этого в StudentController добавить эндпоинт, который принимает число (возраст — поле age) и возвращает список студентов, у которых совпал возраст с переданным числом.

1. Добавить фильтрацию факультетов по цвету.

Для этого в FacultyController добавить эндпоинт, который принимает строку (цвет — поле color) и возвращает список факультетов, у которых совпал цвет с переданной строкой.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0146b2b5-af56-4770-8520-e20d41522ddc/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0146b2b5-af56-4770-8520-e20d41522ddc/Рисунок41.png" width="40px" /> **Критерии оценки:** Созданы два эндпоинта в контроллерах StudentController и FacultyController, которые позволяют фильтровать студентов и факультеты.

</aside>

**Шаг 6**

Добавить swagger к проекту. Для этого добавить зависимость к проекту.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/055dc70e-6a39-4ac4-8d0c-f7b2ec5ad908/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/055dc70e-6a39-4ac4-8d0c-f7b2ec5ad908/Рисунок41.png" width="40px" /> **Критерии оценки:** перейти на страницу Swagger-ui в браузере и проверить, что реализация первых трех шагов работает, как ожидается, путем вызовов запросов через открывшийся интерфейс.

</aside>

**Шаг 7**

Установить Postman и вызвать все эндпоинты проекта, используя его.

<aside>
<img src="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7045fef9-4c1e-4975-836e-0cb596102de9/Рисунок41.png" alt="https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7045fef9-4c1e-4975-836e-0cb596102de9/Рисунок41.png" width="40px" /> **Критерии оценки:** получилось вызвать все эндпоинты проекта и получить успешный ответ.

</aside>