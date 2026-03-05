# 規格文件 - ChatMessageTester.html
來源檔案: `src/main/resources/static/ChatMessageTester.html`

---

## API 互動邏輯 (fetch)

針對頁面中每一個 Web API 呼叫（fetch/XHR），填寫以下資訊。

---

### 1. 新增聊天訊息（POST Chat Message）

* **請求資訊（HTTP Request）**
  - Method: `POST`
  - URL: `{baseUrl}/chat-messages`
  - Headers: `Content-Type: application/json`
  - Payload (Request Body)（欄位來自表單輸入，發送前已做前端驗證）：
```json
{
  "bookingId": 1,
  "senderId": 1,
  "content": "Hello, this message is from the tester page."
}
```

* **回應內容 (Response)**
  - HTTP Status: `201 Created`
  - Body:
```json
{
  "booking": {
    "createdAt": "2026-03-05T11:45:16.04617",
    "id": 1,
    "status": "confirmed",
    "studentId": 1,
    "tutorId": 101,
    "updatedAt": "2026-03-05T11:45:16.04617"
  },
  "content": "Hello, this message is from the tester page.",
  "createdAt": "2026-03-05T11:46:02.7544559",
  "id": 1,
  "senderId": 1
}
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得回應後呼叫 `JSON.parse(text)` 解析，再以 `JSON.stringify(data, null, 2)` 格式化縮排後顯示於 `<pre id="postResult">`。
  - 顯示邏輯：`postMessage()` → `fetch(url, {method:"POST",...})` → JSON.parse → `pretty(data)` → `ids.postResult.textContent`。
  - 其他重要細節：
    - 後端回傳的 `booking` 為完整巢狀物件（含 `id`、`studentId`、`tutorId`、`status`、`createdAt`、`updatedAt`）；GET 查詢時該物件另包含 `hibernateLazyInitializer: {}` 欄位（Hibernate Proxy 序列化產物）。
    - HTTP 非 2xx 時，`setStatus(ids.postStatus, "POST 失敗，HTTP {status}", true)` 顯示錯誤。
    - 例外由 try/catch 捕捉，顯示 `POST 例外：{error.message}`。
    - **已知前端顯示差異**：`<table>` 的 `bookingId` 欄位使用 `m.bookingId`，但 API 回傳值中無頂層 `bookingId` 欄位（實際為 `m.booking.id`），故表格該欄位顯示為空。
  - **注意**：以上內容來自實際 API 呼叫與程式碼分析。

---

### 2. 查詢 Booking 的全部訊息（GET Chat Messages by bookingId）

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
    "booking": {
      "createdAt": "2026-03-05T11:45:16.04617",
      "hibernateLazyInitializer": {},
      "id": 1,
      "status": "confirmed",
      "studentId": 1,
      "tutorId": 101,
      "updatedAt": "2026-03-05T11:45:16.04617"
    },
    "content": "Hello, this message is from the tester page.",
    "createdAt": "2026-03-05T11:46:02.754456",
    "id": 1,
    "senderId": 1
  },
  {
    "booking": {
      "createdAt": "2026-03-05T11:45:16.04617",
      "hibernateLazyInitializer": {},
      "id": 1,
      "status": "confirmed",
      "studentId": 1,
      "tutorId": 101,
      "updatedAt": "2026-03-05T11:45:16.04617"
    },
    "content": "Hi! How can I help you today?",
    "createdAt": "2026-03-05T11:46:12.050932",
    "id": 2,
    "senderId": 101
  }
]
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得後呼叫 `JSON.parse(text)` 解析為陣列，再：
    1. `pretty(data)` → `ids.getResult.textContent`：顯示完整 JSON 於 `<pre id="getResult">`
    2. `renderTable(data)`：將陣列渲染為 HTML 表格（見下方邏輯）
    3. 狀態列顯示 `GET 成功，HTTP 200，共 {count} 筆`
  - 顯示邏輯：
    - `<pre id="getResult">` 顯示格式化 JSON。
    - `<div id="tableWrap">` 動態渲染 `<table class="result-table">`，欄位為：`id`、`bookingId`（實際為空，見備註）、`senderId`、`content`、`createdAt`。
    - 訊息依 `createdAt` 升冪排列（由後端 `ORDER BY created_at ASC` 保證）。
  - 其他重要細節：
    - `hibernateLazyInitializer: {}` 為 Hibernate Lazy Proxy 序列化時產生的額外欄位，前端不處理，直接顯示於 JSON 輸出中。
    - 若 bookingId 無任何訊息，後端回傳 `[]`，`renderTable` 清空 `tableWrap`，`pre` 顯示 `[]`，狀態顯示「共 0 筆」。
    - 若 bookingId 為非正整數，前端在送出前即阻擋，顯示「欄位驗證失敗：bookingId 需為正整數」。
  - **注意**：以上內容來自實際 API 呼叫與程式碼分析。

---

## 其他重要功能或邏輯

### 1. 前端輸入驗證（POST 送出前）

- **功能/邏輯名稱**：POST 前端欄位驗證
- **描述**：`postMessage()` 在送出 POST 前先對三個欄位做驗證：
  - `bookingId`：需為正整數（`Number.isInteger(bookingId) && bookingId > 0`）
  - `senderId`：需為正整數
  - `content`：需非空字串（`content.trim()` 不為空）
  
  任一驗證失敗時，直接中止送出並以 `setStatus(ids.postStatus, "欄位驗證失敗：...", true)` 顯示錯誤，不呼叫 API。
- **相關程式碼片段**：
```javascript
// postMessage() 中的驗證邏輯
const bookingId = Number(ids.bookingId.value);
const senderId = Number(ids.senderId.value);
const content = ids.content.value.trim();

if (!Number.isInteger(bookingId) || bookingId <= 0 ||
    !Number.isInteger(senderId) || senderId <= 0 || !content) {
    setStatus(ids.postStatus, "欄位驗證失敗：bookingId/senderId 需為正整數，content 不可為空。", true);
    return;
}
```

### 2. `renderTable()` 結果表格渲染

- **功能/邏輯名稱**：動態 HTML 表格渲染
- **描述**：`renderTable(list)` 接收 ChatMessage 陣列，動態產生 HTML 表格插入 `<div id="tableWrap">`。欄位固定為：`id`、`bookingId`、`senderId`、`content`、`createdAt`。所有欄位值均經過 `escapeHtml()` 防止 XSS 注入。
  
  **已知行為**：`m.bookingId` 對應 API 回傳物件中不存在的頂層欄位（回傳實際為巢狀 `m.booking.id`），故 `bookingId` 欄位在表格中顯示為空字串。
- **相關程式碼片段**：
```javascript
function renderTable(list) {
    if (!Array.isArray(list) || list.length === 0) {
        ids.tableWrap.innerHTML = "";
        return;
    }
    const header = "<thead><tr><th>id</th><th>bookingId</th><th>senderId</th><th>content</th><th>createdAt</th></tr></thead>";
    const rows = list.map(function (m) {
        return "<tr>" +
            "<td>" + escapeHtml(String(m.id ?? "")) + "</td>" +
            "<td>" + escapeHtml(String(m.bookingId ?? "")) + "</td>" +  // 實際值為空
            "<td>" + escapeHtml(String(m.senderId ?? "")) + "</td>" +
            "<td>" + escapeHtml(String(m.content ?? "")) + "</td>" +
            "<td>" + escapeHtml(String(m.createdAt ?? "")) + "</td>" +
            "</tr>";
    }).join("");
    ids.tableWrap.innerHTML = "<table class=\"result-table\">" + header + "<tbody>" + rows + "</tbody></table>";
}
```

### 3. `escapeHtml()` XSS 防護

- **功能/邏輯名稱**：HTML 特殊字元跳脫
- **描述**：`renderTable()` 中所有資料欄位均呼叫 `escapeHtml()` 將 `&`、`<`、`>`、`"`、`'` 轉為 HTML 實體，防止 innerHTML 注入 XSS 攻擊。
- **相關程式碼片段**：
```javascript
function escapeHtml(str) {
    return str
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/\"/g, "&quot;")
        .replace(/'/g, "&#039;");
}
```

### 4. 填入範例內容按鈕

- **功能/邏輯名稱**：`fillSampleBtn` 範例資料填充
- **描述**：`<button id="fillSampleBtn">填入範例內容</button>` 點擊後會將 `bookingId`、`senderId`、`content` 輸入欄位填入預設範例值（由 JS 事件監聽器設定），方便快速測試。

### 5. 清除結果按鈕

- **功能/邏輯名稱**：`clearBtn` 清除 GET 結果
- **描述**：`<button id="clearBtn">清除結果</button>` 點擊後清空 `<div id="tableWrap">` 的 innerHTML，並將 `<pre id="getResult">` 重設為 `[]`，`getStatus` 狀態列重設為「尚未查詢」。

### 6. Base URL 設定

- **功能/邏輯名稱**：動態 API Base URL
- **描述**：`<input id="baseUrl">` 預設值為 `/api`，可手動改為其他主機位址。`normalizeBaseUrl()` 讀取後自動移除尾端 `/`。`createMessageEndpoint()` 以此拼接 `/chat-messages` 端點。
- **相關程式碼片段**：
```javascript
function normalizeBaseUrl(value) {
    const trimmed = value.trim();
    if (!trimmed) return "/api";
    return trimmed.endsWith("/") ? trimmed.slice(0, -1) : trimmed;
}

function createMessageEndpoint() {
    return normalizeBaseUrl(ids.baseUrl.value) + "/chat-messages";
}
```
