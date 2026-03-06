# 規格文件 - LessonFeedbackTester.html
來源檔案: `src/main/resources/static/LessonFeedbackTester.html`

---

## API 互動邏輯 (fetch)

針對頁面中每一個 Web API 呼叫，填寫以下資訊。

LessonFeedback 實體欄位：`id`、`lessonId`、`rating`（1–5）、`comment`。

---

### 1. 新增回饋（POST）

* **請求資訊（HTTP Request）**
  - Method: `POST`
  - URL: `{baseUrl}/lesson-feedbacks`
  - Headers: `Content-Type: application/json`
  - Payload (Request Body)（欄位來自表單，發送前已做前端驗證）：
```json
{
  "lessonId": 1,
  "rating": 5,
  "comment": "學生表現優異，主動提問。"
}
```
  - **必填欄位**：`lessonId`（正整數）、`rating`（1–5 整數）
  - **可選欄位**：`comment`（字串，可省略）

* **回應內容 (Response)**
  - HTTP Status: `201 Created`
  - Body:
```json
{
  "id": 1,
  "lessonId": 1,
  "rating": 5,
  "comment": "學生表現優異，主動提問。"
}
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得後 `JSON.parse(text)`，再以 `pretty(data)` 格式化後顯示於 `<pre id="postResult">`。
  - 顯示邏輯：POST 按鈕 → `doFetch(endpoint(""), {method:"POST",...})` → `onSuccess` → `setStatus("POST 成功，HTTP 201", false)`。
  - 其他重要細節：
    - `lessonId` 非正整數時前端阻擋，顯示「lessonId 需為正整數」。
    - `rating` 非 1–5 整數時前端阻擋，顯示「rating 需為 1–5 的整數」。
    - `lessonId` 不存在時後端回 `404`（FK 約束），前端顯示「失敗，HTTP 404」。
    - `comment` 為空字串時不帶入 payload（`if (comment) payload.comment = comment`）。
  - **注意**：以上內容來自程式碼分析。

---

### 2. 查詢所有回饋（GET All）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/lesson-feedbacks`
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
    "lessonId": 1,
    "rating": 5,
    "comment": "學生表現優異，主動提問。"
  },
  {
    "id": 2,
    "lessonId": 2,
    "rating": 3,
    "comment": "需要加強練習。"
  }
]
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得後 `JSON.parse(text)`，再 `pretty(data)` 顯示於 `<pre id="getAllResult">`，同時 `renderTable("getAllTableWrap", data)` 渲染表格。
  - 顯示邏輯：GET all 按鈕 → `doFetch(endpoint(""), {method:"GET"})` → `onSuccess` → 顯示「共 {count} 筆」。
  - 其他重要細節：無資料時後端回 `[]`，表格清空，狀態顯示「共 0 筆」。
  - **注意**：以上內容來自程式碼分析。

---

### 3. 依 ID 查詢回饋（GET by ID）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/lesson-feedbacks/{id}`（id 來自 `<input id="getById">`，預設 `1`）
  - Headers: 無自訂 Header
  - Payload (Request Body):
```json
null
```

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body:
```json
{
  "id": 1,
  "lessonId": 1,
  "rating": 5,
  "comment": "學生表現優異，主動提問。"
}
```
  - 資料解讀與處理邏輯：`response.text()` → `JSON.parse(text)` → `pretty(data)` → `<pre id="getByIdResult">`。
  - 顯示邏輯：GET by ID 按鈕 → `doFetch(endpoint("/" + id), {method:"GET"})` → `onSuccess`。
  - 其他重要細節：
    - `id` 非正整數時前端阻擋，顯示「id 需為正整數」。
    - ID 不存在時後端回 `404`，前端顯示「失敗，HTTP 404」。
  - **注意**：以上內容來自程式碼分析。

---

### 4. 依 lessonId 查詢回饋（GET by lessonId）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/lesson-feedbacks/lesson/{lessonId}`（lessonId 來自 `<input id="getByLessonId">`，預設 `1`）
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
    "lessonId": 1,
    "rating": 5,
    "comment": "學生表現優異，主動提問。"
  }
]
```
  - 資料解讀與處理邏輯：同 GET All，結果顯示於 `<pre id="getByLessonResult">` 及 `<div id="getByLessonTableWrap">` 表格。
  - 顯示邏輯：GET by lessonId 按鈕 → `doFetch(endpoint("/lesson/" + lessonId), {method:"GET"})` → `onSuccess` → 顯示「共 {count} 筆」。
  - 其他重要細節：`lessonId` 非正整數時前端阻擋；lessonId 無對應資料時後端回 `[]`。
  - **注意**：以上內容來自程式碼分析。

---

### 5. 查詢平均評分（GET Average Rating by lessonId）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/lesson-feedbacks/lesson/{lessonId}/average-rating`（lessonId 來自 `<input id="avgLessonId">`，預設 `1`）
  - Headers: 無自訂 Header
  - Payload (Request Body):
```json
null
```

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body:
```json
{
  "lessonId": 1,
  "averageRating": 4.5
}
```
  - 資料解讀與處理邏輯：`response.text()` → `JSON.parse(text)` → `pretty(data)` → `<pre id="avgResult">`。
  - 顯示邏輯：GET avg 按鈕 → `doFetch(endpoint("/lesson/" + lessonId + "/average-rating"), {method:"GET"})` → `onSuccess`。
  - 其他重要細節：lessonId 無回饋時 `LessonFeedbackService.getAverageRating()` 回傳 `0.0`。
  - **注意**：以上內容來自程式碼分析。

---

### 6. 更新回饋（PUT）

* **請求資訊（HTTP Request）**
  - Method: `PUT`
  - URL: `{baseUrl}/lesson-feedbacks/{id}`（id 來自 `<input id="putId">`，預設 `1`）
  - Headers: `Content-Type: application/json`
  - Payload (Request Body)（欄位來自表單，發送前已做前端驗證）：
```json
{
  "rating": 4,
  "comment": "已更新的評語內容。"
}
```
  - **必填欄位**：`rating`（1–5 整數）
  - **可選欄位**：`comment`

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body:
```json
{
  "id": 1,
  "lessonId": 1,
  "rating": 4,
  "comment": "已更新的評語內容。"
}
```
  - 資料解讀與處理邏輯：`response.text()` → `JSON.parse(text)` → `pretty(data)` → `<pre id="putResult">`。
  - 顯示邏輯：PUT 按鈕 → `doFetch(endpoint("/" + id), {method:"PUT",...})` → `onSuccess`。
  - 其他重要細節：
    - `id` 非正整數時前端阻擋，顯示「id 需為正整數」。
    - `rating` 非 1–5 整數時前端阻擋，顯示「rating 需為 1–5 的整數」。
    - ID 不存在時後端回 `404`。
    - PUT 不更新 `lessonId`，後端保留原值。
    - `comment` 為空字串時不帶入 payload。
  - **注意**：以上內容來自程式碼分析。

---

### 7. 刪除回饋（DELETE）

* **請求資訊（HTTP Request）**
  - Method: `DELETE`
  - URL: `{baseUrl}/lesson-feedbacks/{id}`（id 來自 `<input id="deleteId">`，預設 `1`）
  - Headers: 無自訂 Header
  - Payload (Request Body):
```json
null
```

* **回應內容 (Response)**
  - HTTP Status: `204 No Content`
  - Body: 空字串 `""`（無內容）
  - 資料解讀與處理邏輯：`response.text()` 回傳空字串，前端不解析（`resultId` 為 `null` 時不更新 `<pre>`），狀態列顯示「DELETE 成功，HTTP 204（id = {id} 已刪除）」。
  - 顯示邏輯：DELETE 按鈕 → `confirm()` → `doFetch(endpoint("/" + id), {method:"DELETE"})` → `onSuccess`。
  - 其他重要細節：
    - `id` 非正整數時前端阻擋，顯示「id 需為正整數」。
    - 送出前跳出 `confirm()` 確認彈窗，取消即中止請求。
    - ID 不存在時後端回 `404`，前端顯示「失敗，HTTP 404」。
  - **注意**：以上內容來自程式碼分析。

---

## 其他重要功能或邏輯

### 1. 前端輸入驗證

| 操作 | 驗證項目 |
|------|----------|
| POST | `lessonId` 正整數；`rating` 1–5 整數 |
| GET by ID | `id` 正整數 |
| GET by lessonId | `lessonId` 正整數 |
| GET avg rating | `lessonId` 正整數 |
| PUT | `id` 正整數；`rating` 1–5 整數 |
| DELETE | `id` 正整數；`confirm()` 二次確認 |

任一驗證失敗均中止請求，不呼叫 API。

### 2. `doFetch()` 統一請求處理器

所有 7 個端點操作均透過此函式執行：
1. `setStatus(statusId, "請求中...", false)`
2. `fetch(url, options)` 送出請求
3. `response.text()` 取得回應字串
4. `resultId` 非 null 時：`JSON.parse(text)` → `pretty(data)` → `<pre>` 顯示
5. 非 2xx → `setStatus(statusId, "失敗，HTTP {status}", true)`
6. 2xx → 執行 `onSuccess(res, data)` callback
7. try/catch 捕捉網路例外，顯示「例外：{error.message}」

### 3. `renderTable()` 結果表格渲染

- 接收 LessonFeedback 陣列，動態產生 HTML 表格插入指定容器。
- **欄位**：`id`、`lessonId`、`rating`、`comment`。
- **XSS 防護**：所有欄位值均經 `escapeHtml()` 轉義（`&`、`<`、`>`、`"`、`'`）。
- GET All 的表格插入 `<div id="getAllTableWrap">`；GET by lessonId 的表格插入 `<div id="getByLessonTableWrap">`。
- 若陣列為空，清空容器 innerHTML。

### 4. Base URL 設定

- `<input id="baseUrl">` 預設 `/api`，可改為其他主機位址（如 `http://localhost:8080/api`）。
- `base()` 函式讀取後自動移除尾端 `/`。
- `endpoint(path)` 回傳 `{base}/lesson-feedbacks{path}`。

### 5. 填入範例按鈕（POST 區）

- 點擊後填入 `lessonId=1`、`rating` 隨機（1–5）、`comment` 含當下 ISO 時間戳，方便快速測試 POST。

### 6. 清除結果按鈕（GET All / GET by lessonId 區）

- 點擊後清空對應 `<pre>` 與表格容器，狀態列重設為「結果已清除」。
