# API ATI.SU для перевозчика (исполнителя)

Портал: https://ati.su/developers/  ·  Базовый URL: `https://api.ati.su`
Все запросы: `Authorization: Bearer <access_token>`

---

## 1. Авторизация (OAuth 2.0)

Документация: https://ati.su/developers/auth/auth-v2/

Шаг 1 — направить пользователя по ссылке (это обычный вход по логину/паролю в браузере):
```
https://id.ati.su/oauth2/?client_id=<CLIENT_ID>&scope=<SCOPE>&redirect_uri=<REDIRECT_URI>&response_type=code
```

Шаг 2 — ATI.SU редиректит на `redirect_uri` с параметром `code`.

Шаг 3 — обменять code на токен:
```
POST /oauth2/token
Content-Type: application/json

{
  "client_id": "string",
  "client_secret": "string",
  "code": "string",
  "grant_type": "authorization_code"
}
```
Ответ:
```json
{
  "access_token": "0A_00_...",
  "refresh_token": "string",
  "expires_in": 7200
}
```
`access_token` живёт 2 часа — обновлять через `refresh_token` (grant_type: "refresh_token").

---

## 2. Поиск грузов на площадках

Раздел: https://ati.su/developers/api/loads/published/
Метод: `GET /v1.0/loads/search/byboards`

Точный список query-параметров (город загрузки/выгрузки, даты, тип кузова и т.д.) не проверен построчно —
уточни в конструкторе запросов на портале (раздел "Опубликованные грузы" → "Поиск грузов на площадках"),
он показывает актуальную схему прямо в браузере с твоим токеном.

Возвращает список объектов "Груз" — структура ниже (сокращённая, из подтверждённой схемы ответа
метода добавления груза `POST /v2/cargos`, поля идентичны для чтения):

```json
{
  "cargo_application": {
    "cargo_application_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "cargo_application_number": "string",
    "added_at": "2026-07-10T08:00:00.000Z",
    "updated_at": "2026-07-10T08:00:00.000Z",
    "route": {
      "loading": {
        "city": { "city_id": 213, "region_id": 1, "country_id": 1 },
        "coordinates": { "longitude": 37.62, "latitude": 55.75 },
        "dates": { "type": "ready", "first_date": "2026-07-12T00:00:00.000Z" }
      },
      "unloading": {
        "city": { "city_id": 4, "region_id": 16, "country_id": 1 },
        "coordinates": { "longitude": 49.12, "latitude": 55.79 }
      }
    },
    "truck": {
      "trucks_count": 1,
      "load_type": "ftl",
      "body_types": [1],
      "required_capacity": 20,
      "is_tracking": false
    },
    "payment": {
      "type": "with-bargaining",
      "currency_type": 1,
      "rate_with_vat": 68000
    },
    "is_archived": false
  }
}
```

Для твоей модели данных практически важны: `cargo_application_id` (id груза для takeload),
`route.loading/unloading.city.city_id` + `coordinates`, `truck.body_types` и `required_capacity`,
`payment.rate_with_vat` (или `rate_without_vat`/`cash`).

---

## 3. Взять груз в работу (оформить заказ)

Сценарий: https://ati.su/developers/usecases/carrier/takeload/
Метод: `POST /v1.2/orders/takeload/{loadId}`

Тело запроса (подтверждено документацией):
```json
{
  "rate_type": {},
  "driver_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "truck_info_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "semitrailer_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "trailer_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "requisites_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "signer_id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "is_drop_docs": false,
  "deal_kind": {},
  "taker_note": "string"
}
```
Важно: при взятии груз копируется в отдельную сущность заказа — копия не отображается в общем поиске
и её нельзя редактировать напрямую (значит в твоей модели данных "Груз-объявление" и "Заказ" — разные сущности).

Точная схема тела ответа (структура заказа/сделки) не проверена — смотри в конструкторе запросов
раздел "Работа с заказами" (https://ati.su/developers/api/orders/deals/).

---

## 4. Встречные предложения (если хочешь не просто брать, а предлагать свою ставку)

Раздел: https://ati.su/developers/api/loads/published/ → "Создание встречного предложения"
Точные поля тела запроса не проверены — уточни в конструкторе запросов на портале.

---

## 5. Свой автопарк и водители (синхронизация со стороны ATI.SU)

Раздел: https://ati.su/developers/api/catalogs/autopark/
Раздел "Ваши машины": https://ati.su/developers/api/trucks/
Точные тела запросов не проверены — открой конструктор запросов, разделы совпадают по смыслу с твоей
сущностью "Водитель/машина" (кузов, грузоподъёмность, текущее местоположение).

---

## 6. Вебхуки — уведомления в реальном времени

Обзор: https://ati.su/developers/api/webhooks/
Аутентификация вебхуков (HMAC): https://ati.su/developers/api/webhooks/authentication/

### Создание подписки
`POST /webhooks/v1/create`
```json
{
  "topic": "cargoes.on_boards",
  "callback": "https://твой-домен.ru/webhook?userId=000000",
  "subscription_type": "complex",
  "subscription": {
    "channel": "cargoes.on_boards",
    "boards": ["644bdc87dbd6fc3747887c7b"]
  }
}
```
Ответ: `202 Accepted`, заголовок `Location` содержит URL для проверки статуса вебхука.

### Верификация (ATI.SU шлёт тебе, не ты им)
```
GET https://твой-домен.ru/webhook?status=verification&verification_status=progress&topic=cargoes.on_boards&challenge=608b672ed0a5aaecc2d42488
```
Твой сервер должен ответить за 20 секунд той же строкой:
```json
"608b672ed0a5aaecc2d42488"
```

### Получение события (новый груз на площадке)
ATI.SU шлёт тебе:
```
POST https://твой-домен.ru/webhook?topic=cargoes.on_boards
Content-Type: application/json

{
  "topic": "cargoes.on_boards",
  "entities": [
    {
      "entity_id": "4ea8c372-9510-4880-a80d-fb9ac19129cf",
      "action_date": "2026-07-12T05:12:41.687Z",
      "entity": { "id": "4ea8c372-9510-4880-a80d-fb9ac19129cf" }
    }
  ],
  "is_retry": false
}
```
Ты обязан ответить 2xx за 20 секунд (просто подтверждение получения — не обработки).
`entity` в вебхуке, судя по документации, содержит саму сущность целиком либо только id —
это стоит проверить на реальном тестовом вебхуке, когда получишь client_id, документация по теме
"Грузы на площадках" (https://ati.su/developers/api/webhooks/topics/cargoes-on-boards/) должна
уточнять точный состав полей.

### Проверка статуса вебхука
`GET /webhooks/v1/status/{id}` → возвращает `status`: `created` / `verification` / `active` / `removed` / `deactivated`.

### Удаление
`DELETE /webhooks/v1/delete/{id}` → `202 Accepted`.

---

## 7. Ошибки — общий формат по всему API

```json
{
  "Error": "load_conflict_error",
  "Reason": "Найден дубликат в грузах",
  "ConflictLoadId": "e87584af-1bed-457b-9ee3-a595b715c550"
}
```
Ошибка валидации содержит дополнительно `ErrorsList` с массивом `{property, reason, error, context}` по каждому невалидному полю.

Полезные коды: `access_denied_error`, `load_not_found_error`, `load_reserved_error` (груз уже взят),
`load_cant_reserve` (нельзя забронировать).

---

## 8. Платное, но потенциально полезное для тебя
- Перехват грузов (алерты по критериям): https://ati.su/developers/paid-api/interception/interception-cargoes/
- Средние ставки: https://ati.su/developers/paid-api/average_prices/
- Ставки в похожих грузах: https://ati.su/developers/paid-api/price_prediction/