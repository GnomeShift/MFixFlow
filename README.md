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
* Управление дефектами.
* Управление устройствами.
* Управление ролями.
* Логирование изменений статуса заявок.
* REST API.
* Поддержка PostgreSQL.
* Предустановленные роли (USER, MASTER, ADMIN)

# ⚙️Конфигурация
#### 1️⃣ Клонируйте репозиторий:
```bash
git clone https://github.com/GnomeShift/MFixFlow
```

#### 2️⃣ Сгенерируйте пару ключей:
```bash
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048 && openssl rsa -pubout -in private_key.pem -out public_key.pem
```
> [!WARNING]
> Ключи, сгенерированные SSH-клиентами (PuTTY и т.д.), начинающиеся с "----BEGIN SSH", без расширения .pem, не поддерживаются!

#### 3️⃣ Откройте `application.properties` в текстовом редакторе и укажите значения переменных.

| Переменная                                                   | Значение                                                                                                                                                      |
|--------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| spring.datasource.url=jdbc:postgresql://IP_БД:ПОРТ_БД/ИМЯ_БД | IP, порт, имя БД                                                                                                                                              |
| spring.datasource.username=ЛОГИН                             | Пользователь БД                                                                                                                                               |
| spring.datasource.password=ПАРОЛЬ                            | Пароль пользователя БД                                                                                                                                        |
| spring.jpa.hibernate.ddl-auto=ЗНАЧЕНИЕ                       | create - создать структуру БД (**УДАЛИТ ВСЕ ДАННЫЕ**);<br/>update - обновить структуру БД;<br/>validate - проверить структуру БД;<br/>none - ничего не делать |
| jwt.expirationMs=ЗНАЧЕНИЕ                                    | Время жизни JWT-токена в миллисекундах (**по умолчанию - 86400000**)                                                                                          |
| jwt.privateKeyPath=/ПУТЬ/ДО/ФАЙЛА                            | Путь до файла приватного ключа (**по умолчанию - ./src/main/resources/private_key.pem**)                                                                      |
| jwt.publicKeyPath=/ПУТЬ/ДО/ФАЙЛА                             | Путь до файла публичного ключа (**по умолчанию - ./src/main/resources/public_key.pem**)                                                                       |

#### 4️⃣ Запустите сборку проекта:
```bash
gradlew bootRun
```
> [!NOTE]
> Убедитесь, что у Вас установлен Gradle.

# 📡API
**baseUrl для отправки запросов**
> http://localhost:8080/{api_endpoint}

В таблице ниже приведены доступные API-Endpoints.

| API-Endpoint                                | Метод  | Роль          | Описание                           |
|---------------------------------------------|--------|---------------|------------------------------------|
| {baseUrl}**/api/auth/register**             | POST   | -             | Регистрация пользователя           |
| {baseUrl}**/api/auth/login**                | POST   | -             | Авторизация пользователя           |
| {baseUrl}**/api/users**                     | GET    | ADMIN         | Поиск всех пользователей           |
| {baseUrl}**/api/users/{id}**                | GET    | ADMIN         | Поиск пользователя по ID           |
| {baseUrl}**/api/users/{id}**                | PUT    | ALL           | Обновление пользователя            |
| {baseUrl}**/api/users/{id}**                | DELETE | ADMIN         | Удаление пользователя              |
| {baseUrl}**/api/requests**                  | GET    | MASTER        | Поиск всех заявок                  |
| {baseUrl}**/api/requests/{id}**             | GET    | ALL           | Поиск заявки по ID                 |
| {baseUrl}**/api/requests/by-device/{id}**   | GET    | ALL           | Поиск заявки по устройству         |
| {baseUrl}**/api/requests/by-assignee/{id}** | GET    | ALL           | Поиск заявки по назначенному       |
| {baseUrl}**/api/requests/by-defect/{id}**   | GET    | ALL           | Поиск заявки по типу неисправности |
| {baseUrl}**/api/requests**                  | POST   | ALL           | Создание заявки                    |
| {baseUrl}**/api/requests/{id}**             | PUT    | ALL           | Обновление заявки                  |
| {baseUrl}**/api/requests/{id}**             | DELETE | ALL           | Удаление заявки                    |
| {baseUrl}**/api/requests/{id}/logs**        | GET    | MASTER, ADMIN | Поиск логов по ID заявки           |
| {baseUrl}**/api/requests/logs**             | DELETE | ADMIN         | Очистить все логи заявок           |
| {baseUrl}**/api/defects**                   | GET    | ALL           | Поиск всех дефектов                |
| {baseUrl}**/api/defects/{id}**              | GET    | ALL           | Поиск дефекта по ID                |
| {baseUrl}**/api/defects**                   | POST   | MASTER, ADMIN | Создание дефекта                   |
| {baseUrl}**/api/defects/{id}**              | PUT    | MASTER, ADMIN | Обновление дефекта                 |
| {baseUrl}**/api/defects/{id}**              | DELETE | MASTER, ADMIN | Удаление дефекта                   |
| {baseUrl}**/api/devices**                   | GET    | ALL           | Поиск всех устройств               |
| {baseUrl}**/api/devices/{id}**              | GET    | ALL           | Поиск устройства по ID             |
| {baseUrl}**/api/devices**                   | POST   | MASTER, ADMIN | Создание устройства                |
| {baseUrl}**/api/devices/{id}**              | PUT    | MASTER, ADMIN | Обновление устройства              |
| {baseUrl}**/api/devices/{id}**              | DELETE | MASTER, ADMIN | Удаление устройства                |
| {baseUrl}**/api/roles**                     | GET    | ADMIN         | Поиск всех ролей                   |
| {baseUrl}**/api/roles/{id}**                | GET    | ADMIN         | Поиск роли по ID                   |
| {baseUrl}**/api/roles**                     | POST   | ADMIN         | Создание роли                      |
| {baseUrl}**/api/roles/{id}**                | PUT    | ADMIN         | Обновление роли                    |
| {baseUrl}**/api/roles/{id}**                | DELETE | ADMIN         | Удаление роли                      |
