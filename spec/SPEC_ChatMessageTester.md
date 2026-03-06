# 規格文件 - ChatMessageTester.html
來源檔案: `src/main/resources/static/ChatMessageTester.html`

---

## API 互動邏輯 (fetch)

針對頁面中每一個 Web API 呼叫，填寫以下資訊。

---

### 1. 依 bookingId 查詢訊息（GET Chat Messages by bookingId）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/chat-messages/booking/{bookingId}`（bookingId 來自 `<input id="queryBookingId">`，預設 `1`）
  - Headers: 無自訂 Header
  - Payload (Request Body):
```json
null
```

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body:
```json
[
  {
    "id": 1,
    "bookingId": 1,
    "role": 1,
    "message": "Hello tutor",
    "createdAt": "2026-03-07T10:00:00.000000"
  },
  {
    "id": 2,
    "bookingId": 1,
    "role": 2,
    "message": "Hello student",
    "createdAt": "2026-03-07T10:01:00.000000"
  }
]
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得後呼叫 `JSON.parse(text)` 解析為陣列，再：
    1. `pretty(data)` → `ids.getResult.textContent`：顯示完整 JSON 於 `<pre id="getResult">`
    2. `renderTable(data)`：將陣列渲染為 HTML 表格
    3. 狀態列顯示「GET 成功，HTTP 200，共 {count} 筆」
  - 顯示邏輯：`queryMessages()` → `fetch(url, {method:"GET"})` → JSON.parse → `renderTable(data)` + `pretty(data)`。
  - 其他重要細節：
    - 訊息依 `createdAt` 升冪排列（由後端 `ORDER BY created_at ASC` 保證）。
    - bookingId 無任何訊息時後端回傳 `[]`，`renderTable` 清空 `tableWrap`，狀態顯示「共 0 筆」。
    - bookingId 非正整數時，前端阻擋並顯示「欄位驗證失敗：bookingId 需為正整數。」
  - **注意**：以上內容來自程式碼分析。

---

### 2. 新增訊息（POST Chat Message）

* **請求資訊（HTTP Request）**
  - Method: `POST`
  - URL: `{baseUrl}/chat-messages`
  - Headers: `Content-Type: application/json`
  - Payload (Request Body)（欄位來自表單輸入，發送前已做前端驗證）：
```json
{
  "bookingId": 1,
  "role": 1,
  "message": "Hello, this message is from the tester page."
}
```
  - **必填欄位**：`bookingId`（正整數）、`role`（1 = 學生 / 2 = 老師）、`message`（非空字串）

* **回應內容 (Response)**
  - HTTP Status: `201 Created`
  - Body:
```json
{
  "id": 1,
  "bookingId": 1,
  "role": 1,
  "message": "Hello, this message is from the tester page.",
  "createdAt": "2026-03-07T10:00:00.000000"
}
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得後呼叫 `JSON.parse(text)` 解析，再以 `JSON.stringify(data, null, 2)` 格式化後顯示於 `<pre id="postResult">`。
  - 顯示邏輯：`postMessage()` → `fetch(url, {method:"POST",...})` → JSON.parse → `pretty(data)` → `ids.postResult.textContent`。
  - 其他重要細節：
    - HTTP 非 2xx 時，`setStatus` 顯示「POST 失敗，HTTP {status}」（錯誤樣式）。
    - `bookingId` 不存在時後端回 `404`。
    - 例外由 try/catch 捕捉，顯示「POST 例外：{error.message}」。
  - **注意**：以上內容來自程式碼分析。

---

### 3. 更新訊息（PUT Chat Message by ID）

* **請求資訊（HTTP Request）**
  - Method: `PUT`
  - URL: `{baseUrl}/chat-messages/{id}`（id 來自 `<input id="putId">`，預設 `1`）
  - Headers: `Content-Type: application/json`
  - Payload (Request Body)（僅更新 message 欄位）：
```json
{
  "message": "Updated message content."
}
```
  - **必填欄位**：`message`（非空字串）

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body:
```json
{
  "id": 1,
  "bookingId": 1,
  "role": 1,
  "message": "Updated message content.",
  "createdAt": "2026-03-07T10:00:00.000000"
}
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得後呼叫 `JSON.parse(text)` 解析，以 `pretty(data)` 格式化後顯示於 `<pre id="putResult">`。
  - 顯示邏輯：`putMessage()` → `fetch(url, {method:"PUT",...})` → JSON.parse → `pretty(data)` → `putResult.textContent`。
  - 其他重要細節：
    - `id` 不存在時後端回 `404 Not Found`。
    - `message` 為空字串或空白時，前端阻擋並顯示「欄位驗證失敗：message 不可為空。」
    - PUT 不更新 `role`、`bookingId`、`createdAt`，後端保留原值。
  - **注意**：以上內容來自程式碼分析。

---

### 4. 刪除訊息（DELETE Chat Message by ID）

* **請求資訊（HTTP Request）**
  - Method: `DELETE`
  - URL: `{baseUrl}/chat-messages/{id}`（id 來自 `<input id="deleteId">`，預設 `1`）
  - Headers: 無自訂 Header
  - Payload (Request Body):
```json
null
```

* **回應內容 (Response)**
  - HTTP Status: `204 No Content`
  - Body: 空字串 `""`（無內容）
  - 資料解讀與處理邏輯：`response.text()` 回傳空字串，前端不解析，狀態列顯示「DELETE 成功，HTTP 204（id = {id} 已刪除）」。
  - 顯示邏輯：`deleteMessage()` → `fetch(url, {method:"DELETE"})` → 檢查 `response.ok` → `setStatus`。
  - 其他重要細節：
    - `id` 不存在時後端回 `404 Not Found`，前端顯示「DELETE 失敗，HTTP 404」。
    - 送出前跳出 `confirm()` 確認彈窗，取消即中止請求。
    - 例外由 try/catch 捕捉，顯示「DELETE 例外：{error.message}」。
  - **注意**：以上內容來自程式碼分析。

---

## 其他重要功能或邏輯

### 1. 前端輸入驗證

| 操作 | 驗證項目 |
|------|----------|
| POST | `bookingId` 正整數；`role` 為 1 或 2；`message` 非空白字串 |
| PUT  | `id` 正整數；`message` 非空白字串 |
| DELETE | `id` 正整數；`confirm()` 二次確認 |
| GET  | `queryBookingId` 正整數 |

任一驗證失敗均中止請求，不呼叫 API。

### 2. `renderTable()` 結果表格渲染

- 接收 ChatMessage 陣列，動態產生 HTML 表格插入 `<div id="tableWrap">`。
- **欄位**：`id`、`bookingId`、`role`、`message`、`createdAt`。
- **XSS 防護**：所有欄位值均經 `escapeHtml()` 轉義（`&`、`<`、`>`、`"`、`'`）。
- 若陣列為空或非陣列，清空 `tableWrap.innerHTML`。

### 3. Base URL 設定

- `<input id="baseUrl">` 預設 `/api`，可改為其他主機位址（如 `http://localhost:8080/api`）。
- `normalizeBaseUrl()` 讀取後自動移除尾端 `/`。
- `createMessageEndpoint()` 回傳 `{normalizedBaseUrl}/chat-messages`。

### 4. 填入範例內容按鈕

- 點擊後填入 `bookingId=1`、`role` 隨機（1 或 2）、`message` 含當下 ISO 時間戳，方便快速測試 POST。

### 5. 清除結果按鈕

- 點擊後清空 `<div id="tableWrap">` 與 `<pre id="getResult">`，`getStatus` 重設為「尚未查詢」。
