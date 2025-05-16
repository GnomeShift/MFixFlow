<h1>
<p align="center">
<a href="https://github.com/GnomeShift/MFixFlow" target="_blank" rel="noopener noreferrer">MFixFlow</a>
</p>
</h1>

<p align="center">
  <a href="README.md">🇷🇺 Русский</a>
</p>

## 🚀Быстрая навигация
* [Обзор](#обзор)
    * [Функции](#функции)
* [Конфигурация](#конфигурация)
    * [API](#api)

# 🌐Обзор
**MFixFlow** - система управления заявками на ремонт техники.

## ⚡Функции
* Управление пользователями.
* Управление заявками.
* REST API.
* Поддержка PostgreSQL.

# ⚙️Конфигурация
#### 1️⃣ Клонируйте репозиторий:
```bash
git clone https://github.com/GnomeShift/MFixFlow
```

#### 2️⃣ Откройте `application.properties` в текстовом редакторе и укажите значения переменных.

| Переменная                                                   | Значение                                                                                                                                                      |
|--------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| spring.datasource.url=jdbc:postgresql://IP_БД:ПОРТ_БД/ИМЯ_БД | IP, порт, имя БД                                                                                                                                              |
| spring.datasource.username=ЛОГИН                             | Пользователь БД                                                                                                                                               |
| spring.datasource.password=ПАРОЛЬ                            | Пароль пользователя БД                                                                                                                                        |
| spring.jpa.hibernate.ddl-auto=ЗНАЧЕНИЕ                       | create - создать структуру БД (**УДАЛИТ ВСЕ ДАННЫЕ**);<br/>update - обновить структуру БД;<br/>validate - проверить структуру БД;<br/>none - ничего не делать |

#### 3️⃣ Запустите сборку проекта:
```bash
gradlew bootRun
```
> [!NOTE]
> Убедитесь, что у Вас установлен Gradle.

# 📡API
**baseUrl для отправки запросов**
> http://localhost:8080/{api_endpoint}

В таблице ниже приведены доступные API-Endpoints.

| API-Endpoint                             | Метод  | Описание                           |
|------------------------------------------|--------|------------------------------------|
| {baseUrl}**/api/users**                  | GET    | Поиск всех пользователей           |
| {baseUrl}**/api/users/{id}**             | GET    | Поиск пользователя по ID           |
| {baseUrl}**/api/users**                  | POST   | Создание пользователя              |
| {baseUrl}**/api/users/{id}**             | PUT    | Обновление пользователя            |
| {baseUrl}**/api/users/{id}**             | DELETE | Удаление пользователя              |
| {baseUrl}**/api/requests**               | GET    | Поиск всех заявок                  |
| {baseUrl}**/api/requests/{id}**          | GET    | Поиск заявки по ID                 |
| {baseUrl}**/api/requests/bydevice/{id}** | GET    | Поиск заявки по устройству         |
| {baseUrl}**/api/requests/bymaster/{id}** | GET    | Поиск заявки по мастеру            |
| {baseUrl}**/api/requests/bydefect/{id}** | GET    | Поиск заявки по типу неисправности |
| {baseUrl}**/api/requests**               | POST   | Создание новой заявки              |
| {baseUrl}**/api/requests/{id}**          | PUT    | Обновление заявки                  |
| {baseUrl}**/api/requests/{id}**          | DELETE | Удаление заявки                    |
