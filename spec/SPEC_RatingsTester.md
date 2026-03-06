# 規格文件 - RatingsTester.html
來源檔案: `src/main/resources/static/RatingsTester.html`

---

## API 互動邏輯 (fetch)

針對頁面中每一個 Web API 呼叫，填寫以下資訊。

Review 實體欄位：`id`、`userId`、`courseId`、`rating`（1–5）、`comment`。

---

### 1. 查詢全部 Reviews（GET All Reviews）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/reviews`
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
    "userId": 1,
    "courseId": 1,
    "rating": 4,
    "comment": "Very good"
  },
  {
    "id": 2,
    "userId": 2,
    "courseId": 1,
    "rating": 5,
    "comment": "Outstanding"
  }
]
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得回應，**不做** JSON.parse，直接作為原始字串傳入 `setOutput(text)` 顯示於 `<pre id="output-reviews">`。
  - 顯示邏輯：`fetchAllReviews()` → `runRequest(url, null, hint)` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：HTTP 非 200 時狀態列顯示「HTTP {status} - 請求失敗」（錯誤樣式）。
  - **注意**：以上內容來自程式碼分析。

---

### 2. 依 ID 查詢 Review（GET Review by ID）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/reviews/{id}`（id 來自 `<input id="review-id">`，預設 `1`）
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
  "userId": 1,
  "courseId": 1,
  "rating": 4,
  "comment": "Very good"
}
```
  - 資料解讀與處理邏輯：rawtext 顯示，不做 JSON.parse。
  - 顯示邏輯：`fetchReviewById()` → `runRequest()` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：ID 不存在時後端回 `404`，前端顯示「HTTP 404 - 請求失敗」。
  - **注意**：以上內容來自程式碼分析。

---

### 3. 依 userId 查詢 Reviews（GET Reviews by User）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/reviews/user/{userId}`（userId 來自 `<input id="review-user-id">`，預設 `1`）
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
    "userId": 1,
    "courseId": 1,
    "rating": 4,
    "comment": "Very good"
  }
]
```
  - 資料解讀與處理邏輯：rawtext 顯示，無額外欄位處理。
  - 顯示邏輯：`fetchByUser()` → `runRequest()` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：userId 無對應資料時後端回 `[]`。
  - **注意**：以上內容來自程式碼分析。

---

### 4. 依 courseId 查詢 Reviews（GET Reviews by Course）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/reviews/course/{courseId}`（courseId 來自 `<input id="review-course-id">`，預設 `1`）
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
    "userId": 1,
    "courseId": 1,
    "rating": 4,
    "comment": "Very good"
  },
  {
    "id": 2,
    "userId": 2,
    "courseId": 1,
    "rating": 5,
    "comment": "Outstanding"
  }
]
```
  - 資料解讀與處理邏輯：rawtext 顯示。
  - 顯示邏輯：`fetchByCourse()` → `runRequest()` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：courseId 無對應資料時後端回 `[]`。
  - **注意**：以上內容來自程式碼分析。

---

### 5. 查詢平均評分（GET Average Rating by courseId）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/reviews/course/{courseId}/average-rating`（courseId 來自 `<input id="review-course-id">`，預設 `1`）
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
  "courseId": 1,
  "averageRating": 4.5
}
```
  - 資料解讀與處理邏輯：rawtext 顯示；`averageRating` 為 double，由後端 JPQL `AVG()` 計算後回傳。
  - 顯示邏輯：`fetchAverageRating()` → `runRequest()` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：若 courseId 無評論，`ReviewService.getAverageRating()` 回傳 `0.0`。
  - **注意**：以上內容來自程式碼分析。

---

### 6. 新增 Review（POST）

* **請求資訊（HTTP Request）**
  - Method: `POST`
  - URL: `{baseUrl}/reviews`
  - Headers: `Content-Type: application/json`
  - Payload (Request Body)（來自 `<textarea id="review-body">`，HTML 預設值）：
```json
{
  "userId": 1,
  "courseId": 1,
  "rating": 5,
  "comment": "Great!"
}
```
  - **必填欄位**：`userId`、`courseId`、`rating`（1–5）
  - **可選欄位**：`comment`

* **回應內容 (Response)**
  - HTTP Status: `201 Created`
  - Body:
```json
{
  "id": 3,
  "userId": 1,
  "courseId": 1,
  "rating": 5,
  "comment": "Great!"
}
```
  - 資料解讀與處理邏輯：rawtext 顯示，不做 JSON.parse。
  - 顯示邏輯：`createReview()` → `runRequest(url, options, hint)` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：
    - `userId` null → 後端回 `400`，message 含「userId 不能為空」。
    - `courseId` null → 後端回 `400`，message 含「courseId 不能為空」。
    - `rating` null → 後端回 `400`，message 含「rating 不能為空」。
    - 前端不做欄位驗證，非法 JSON 格式導致後端 `400`。
  - **注意**：以上內容來自程式碼分析。

---

### 7. 更新 Review（PUT）

* **請求資訊（HTTP Request）**
  - Method: `PUT`
  - URL: `{baseUrl}/reviews/{id}`（id 來自 `<input id="review-id">`，預設 `1`）
  - Headers: `Content-Type: application/json`
  - Payload (Request Body)（來自 `<textarea id="review-update-body">`，HTML 預設值）：
```json
{
  "rating": 4,
  "comment": "Updated comment"
}
```
  - **可更新欄位**：`rating`、`comment`（`userId`、`courseId` 由後端保留原值）

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body:
```json
{
  "id": 1,
  "userId": 1,
  "courseId": 1,
  "rating": 4,
  "comment": "Updated comment"
}
```
  - 資料解讀與處理邏輯：rawtext 顯示。
  - 顯示邏輯：`updateReview()` → `runRequest(url, options, hint)` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：
    - ID 不存在 → 後端回 `404`。
    - `rating` 超出 1–5 → `ReviewService.validateReview()` 拋 `IllegalArgumentException` → 後端回 `400`。
    - 前端不做欄位驗證，直接送出 textarea 內容。
  - **注意**：以上內容來自程式碼分析。

---

### 8. 刪除 Review（DELETE）

* **請求資訊（HTTP Request）**
  - Method: `DELETE`
  - URL: `{baseUrl}/reviews/{id}`（id 來自 `<input id="review-delete-id">`，預設 `1`）
  - Headers: 無自訂 Header
  - Payload (Request Body):
```json
null
```

* **回應內容 (Response)**
  - HTTP Status: `204 No Content`
  - Body: 空字串 `""`（無內容）
  - 資料解讀與處理邏輯：`response.text()` 回傳空字串，前端以 `setOutput(text || "{}")` 顯示 `{}`。
  - 顯示邏輯：`deleteReview()` → `runRequest(url, options, hint)` → `setOutput(text || "{}")` → `<pre id="output-reviews">`。
  - 其他重要細節：ID 不存在 → 後端回 `404`；成功狀態列顯示「刪除 Review 成功 (HTTP 204)」。
  - **注意**：以上內容來自程式碼分析。

---

## 其他重要功能或邏輯

### 1. `runRequest()` 統一請求處理器

所有 8 個按鈕操作均透過此函式執行：
1. 狀態列顯示「請求中...」
2. `fetch(url, options || {})` 送出請求
3. `response.text()` 取得回應字串（不做 JSON.parse）
4. `setOutput(text || "{}")` 將結果寫入 `<pre>`
5. 非 2xx → `setStatus("HTTP {status} - 請求失敗", true)`
6. 2xx → `setStatus(successHint + " (HTTP {status})", false)`
7. try/catch 捕捉網路例外，顯示「例外: {error.message}」

### 2. Base URL 設定

- `<input id="baseUrl">` 預設 `/api`，可改為其他主機位址（如 `http://localhost:8080/api`）。
- `baseUrl()` 函式讀取後自動移除尾端 `/`。
- `reviewsUrl(path)` 回傳 `{baseUrl}/reviews/{path}`（path 為空時不加 `/`）。

### 3. 狀態與輸出顯示

- `<div id="review-status" class="status">` 顯示操作狀態；錯誤時套用 `error` CSS 類別（紅色背景）。
- `<pre id="output-reviews">` 顯示 API 原始回應字串（raw text），不做格式化或 syntax highlight。
- 所有操作共用同一個 `<pre>` 輸出區，後一個請求結果覆蓋前一個。
