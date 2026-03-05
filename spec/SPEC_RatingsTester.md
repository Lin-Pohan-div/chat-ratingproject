# 規格文件 - RatingsTester.html
來源檔案: `src/main/resources/static/RatingsTester.html`

---

## API 互動邏輯 (fetch)

針對頁面中每一個 Web API 呼叫（fetch/XHR），填寫以下資訊。

---

### 1. 取得全部 Reviews（GET All Reviews）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/reviews`
  - Headers: 無自訂 Header（瀏覽器預設）
  - Payload (Request Body):
```json
null
```

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body（格式化顯示實際測試結果）：
```json
[
  {
    "id": 1,
    "studentId": 1,
    "tutorCourseId": 1,
    "rating": 4,
    "comment": "Updated comment",
    "createdAt": "2026-03-05T11:45:16.047167",
    "updatedAt": "2026-03-05T11:47:21.746313"
  },
  {
    "id": 2,
    "studentId": 2,
    "tutorCourseId": 1,
    "rating": 4,
    "comment": "Very good",
    "createdAt": "2026-03-05T11:45:16.047167",
    "updatedAt": "2026-03-05T11:45:16.047167"
  },
  {
    "id": 3,
    "studentId": 3,
    "tutorCourseId": 2,
    "rating": 3,
    "comment": "Average experience",
    "createdAt": "2026-03-05T11:45:16.047167",
    "updatedAt": "2026-03-05T11:45:16.047167"
  },
  {
    "id": 4,
    "studentId": 1,
    "tutorCourseId": 2,
    "rating": 2,
    "comment": "Could improve",
    "createdAt": "2026-03-05T11:45:16.047167",
    "updatedAt": "2026-03-05T11:45:16.047167"
  },
  {
    "id": 5,
    "studentId": 4,
    "tutorCourseId": 3,
    "rating": 5,
    "comment": "Outstanding",
    "createdAt": "2026-03-05T11:45:16.047167",
    "updatedAt": "2026-03-05T11:45:16.047167"
  },
  {
    "id": 7,
    "studentId": 1,
    "tutorCourseId": 1,
    "rating": 5,
    "comment": "Great!",
    "createdAt": "2026-03-05T11:47:31.539977",
    "updatedAt": "2026-03-05T11:47:31.539977"
  }
]
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得回應，直接作為字串顯示，**不做** JSON.parse 或任何欄位轉換。
  - 顯示邏輯：呼叫 `fetchAllReviews()` → `runRequest(url, null, hint)` → `setOutput(text)` → 寫入 `<pre id="output-reviews">`。
  - 其他重要細節：HTTP 非 200 時，`setStatus()` 顯示錯誤訊息並設 `error` CSS 類別；例外（網路錯誤等）以 try/catch 捕捉。
  - **注意**：以上內容來自實際 API 呼叫與程式碼分析。

---

### 2. 依 ID 取得 Review（GET Review by ID）

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
  - Body（格式化顯示實際測試結果）：
```json
{
  "id": 1,
  "studentId": 1,
  "tutorCourseId": 1,
  "rating": 4,
  "comment": "Updated comment",
  "createdAt": "2026-03-05T11:45:16.047167",
  "updatedAt": "2026-03-05T11:47:21.7463132"
}
```
  - 資料解讀與處理邏輯：同 API 1，rawtext 顯示。
  - 顯示邏輯：`fetchReviewById()` → `runRequest()` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：若 ID 不存在，後端回 `404`，前端顯示 `HTTP 404 - 請求失敗`。
  - **注意**：以上內容來自實際 API 呼叫與程式碼分析。

---

### 3. 依 Student 查詢 Reviews（GET Reviews by Student）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/reviews/student/{studentId}`（studentId 來自 `<input id="review-student-id">`，預設 `1`）
  - Headers: 無自訂 Header
  - Payload (Request Body):
```json
null
```

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body（格式化顯示實際測試結果）：
```json
[
  {
    "id": 1,
    "studentId": 1,
    "tutorCourseId": 1,
    "rating": 4,
    "comment": "Updated comment",
    "createdAt": "2026-03-05T11:45:16.047167",
    "updatedAt": "2026-03-05T11:47:21.746313"
  },
  {
    "id": 4,
    "studentId": 1,
    "tutorCourseId": 2,
    "rating": 2,
    "comment": "Could improve",
    "createdAt": "2026-03-05T11:45:16.047167",
    "updatedAt": "2026-03-05T11:45:16.047167"
  }
]
```
  - 資料解讀與處理邏輯：rawtext 顯示，無額外欄位處理。
  - 顯示邏輯：`fetchByStudent()` → `runRequest()` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：若 studentId 無對應資料，後端回 `[]`。
  - **注意**：以上內容來自實際 API 呼叫與程式碼分析。

---

### 4. 依 TutorCourse 查詢 Reviews（GET Reviews by TutorCourse）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/reviews/tutor-course/{tutorCourseId}`（tutorCourseId 來自 `<input id="review-tutorcourse-id">`，預設 `1`）
  - Headers: 無自訂 Header
  - Payload (Request Body):
```json
null
```

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body（格式化顯示實際測試結果）：
```json
[
  {
    "id": 1,
    "studentId": 1,
    "tutorCourseId": 1,
    "rating": 4,
    "comment": "Updated comment",
    "createdAt": "2026-03-05T11:45:16.047167",
    "updatedAt": "2026-03-05T11:47:21.746313"
  },
  {
    "id": 2,
    "studentId": 2,
    "tutorCourseId": 1,
    "rating": 4,
    "comment": "Very good",
    "createdAt": "2026-03-05T11:45:16.047167",
    "updatedAt": "2026-03-05T11:45:16.047167"
  }
]
```
  - 資料解讀與處理邏輯：rawtext 顯示。
  - 顯示邏輯：`fetchByTutorCourse()` → `runRequest()` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：若 tutorCourseId 無對應資料，後端回 `[]`。
  - **注意**：以上內容來自實際 API 呼叫與程式碼分析。

---

### 5. 查詢平均評分（GET Average Rating）

* **請求資訊（HTTP Request）**
  - Method: `GET`
  - URL: `{baseUrl}/reviews/tutor-course/{tutorCourseId}/average-rating`（tutorCourseId 來自 `<input id="review-tutorcourse-id">`，預設 `1`）
  - Headers: 無自訂 Header
  - Payload (Request Body):
```json
null
```

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body（格式化顯示實際測試結果）：
```json
{
  "tutorCourseId": 1,
  "averageRating": 4.0
}
```
  - 資料解讀與處理邏輯：rawtext 顯示；`averageRating` 為 double，由後端直接計算後回傳。
  - 顯示邏輯：`fetchAverageRating()` → `runRequest()` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：若 tutorCourseId 無評論，後端視 ReviewService 實作決定回傳 `0.0` 或 `null`。
  - **注意**：以上內容來自實際 API 呼叫與程式碼分析。

---

### 6. 新增 Review（POST）

* **請求資訊（HTTP Request）**
  - Method: `POST`
  - URL: `{baseUrl}/reviews`
  - Headers: `Content-Type: application/json`
  - Payload (Request Body)（來自 `<textarea id="review-body">`，前端範例預設值使用 `content`，但實際 API 接受 `content` 或 `comment` 兩種欄位名）：
```json
{
  "bookingId": 1,
  "rating": 5,
  "content": "Great!"
}
```
  - **必填欄位**：`bookingId`（Booking ID，用於關聯學生與課程）、`rating`（1~5 星評分）
  - **可選欄位**：`content`（評論內容；後端實體定義了 `@JsonProperty("content")` 將資料庫的 `comment` 欄位映射為 JSON 中的 `content`）

* **回應內容 (Response)**
  - HTTP Status: `201 Created` 或 `200 OK`（視控制器實作而定）
  - Body（格式化顯示實際測試結果）：
```json
{
  "id": 7,
  "bookingId": 1,
  "studentId": 1,
  "tutorCourseId": 1,
  "rating": 5,
  "comment": "Great!",
  "createdAt": "2026-03-05T11:47:31.539977",
  "updatedAt": "2026-03-05T11:47:31.539977"
}
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得回應字串，不做 JSON.parse，直接顯示原始文字。回應含後端自動產生的 `id`、`bookingId`、`studentId`、`tutorCourseId`、`createdAt`、`updatedAt`。
  - 顯示邏輯：`createReview()` → `runRequest(url, options, hint)` → `setOutput(text)` → 寫入 `<pre id="output-reviews">`。
  - 其他重要細節：
    - `review-body` textarea 內容由使用者直接編輯後送出，前端不做欄位驗證
    - 後端透過 `ReviewRequest` DTO 接收請求，包含 `bookingId`、`rating`、`content` 三個欄位
    - 若 `bookingId` 為 null/空，後端回 `400 Bad Request`
    - 若 `rating` 為 null/空，後端回 `400 Bad Request`（`@NotNull` 驗證）
    - 非法 JSON 格式會導致後端 `400 Bad Request`
    - 後端會根據 `bookingId` 自動填入 `studentId` 和 `tutorCourseId`
  - **注意**：以上內容來自實際程式碼分析與 API 測試記錄（`spec/api-results-test.json`）。

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
  - **說明**：PUT 請求直接以 Review 實體作為請求體接收，可更新 `rating` 與 `comment` 欄位

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body（格式化顯示實際測試結果）：
```json
{
  "id": 1,
  "bookingId": null,
  "studentId": 1,
  "tutorCourseId": 1,
  "rating": 4,
  "comment": "Updated comment",
  "createdAt": "2026-03-05T11:45:16.047167",
  "updatedAt": "2026-03-05T11:47:21.7463132"
}
```
  - 資料解讀與處理邏輯：前端以 `response.text()` 取得回應字串，不做 JSON.parse，直接顯示原始文字。`createdAt` 保持不變，`updatedAt` 更新為當下時間。
  - 顯示邏輯：`updateReview()` → `runRequest(url, options, hint)` → `setOutput(text)` → 寫入 `<pre id="output-reviews">`。
  - 其他重要細節：
    - PUT 僅更新 `rating` 與 `comment` 欄位，`studentId`、`tutorCourseId`、`bookingId` 等由後端保留原值
    - 若 ID 不存在，後端回 `404 Not Found`
    - 前端不做欄位驗證，直接送出 textarea 內容
  - **注意**：以上內容來自實際程式碼分析與 API 測試記錄。

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
  - 資料解讀與處理邏輯：`response.text()` 回傳空字串，前端 `setOutput(text || "{}")` 將空字串顯示為預設值 `{}`。
  - 顯示邏輯：`deleteReview()` → `runRequest(url, options, hint)` → `setOutput(text || "{}")` → 寫入 `<pre id="output-reviews">`。
  - 其他重要細節：
    - 若 ID 不存在，後端回 `404 Not Found`
    - `204 No Content` 回應不含 body，`pre` 元素仍顯示 `{}`（初始預設值）
    - 刪除成功後，狀態列顯示「刪除 Review 成功 (HTTP 204)」
  - **注意**：以上內容來自實際程式碼分析與 API 測試記錄（`spec/api-results-test.json`）。

---

## 其他重要功能或邏輯

### 1. `runRequest()` 統一請求處理器

- **功能/邏輯名稱**：`runRequest(url, options, successHint)`
- **描述**：頁面唯一的 HTTP 呼叫函式，所有 8 個按鈕操作均透過此函式執行。流程如下：
  1. 狀態列顯示「請求中...」
  2. 以 `fetch(url, options || {})` 送出請求
  3. `response.text()` 取得回應字串（不做 JSON.parse）
  4. 呼叫 `setOutput(text || "{}")` 將結果寫入 `<pre>`
  5. 若 `!response.ok`，呼叫 `setStatus("HTTP {status} - 請求失敗", true)` 顯示錯誤
  6. 若 `response.ok`，呼叫 `setStatus(successHint + " (HTTP {status})", false)` 顯示成功
  7. try/catch 捕捉網路例外，顯示 `例外: {error.message}`
- **相關程式碼片段**：
```javascript
async function runRequest(url, options, successHint) {
    try {
        setStatus("請求中...", false);
        const response = await fetch(url, options || {});
        const text = await response.text();
        setOutput(text || "{}");

        if (!response.ok) {
            setStatus("HTTP " + response.status + " - 請求失敗", true);
            return;
        }
        setStatus(successHint + " (HTTP " + response.status + ")", false);
    } catch (error) {
        setStatus("例外: " + (error && error.message ? error.message : String(error)), true);
    }
}
```

### 2. Base URL 設定

- **功能/邏輯名稱**：動態 API Base URL
- **描述**：`<input id="baseUrl">` 預設值為 `/api`，可手動改為其他主機位址（例如 `http://localhost:8080/api`）。`baseUrl()` 函式讀取此值並自動移除尾端 `/`，確保 URL 拼接正確。`reviewsUrl(path)` 以此拼接最終端點。
- **相關程式碼片段**：
```javascript
function baseUrl() {
    const value = document.getElementById("baseUrl").value.trim();
    if (!value) return "/api";
    return value.endsWith("/") ? value.slice(0, -1) : value;
}

function reviewsUrl(path) {
    return baseUrl() + "/reviews" + (path ? "/" + path : "");
}
```

### 3. 狀態與輸出顯示

- **功能/邏輯名稱**：`setStatus()` / `setOutput()`
- **描述**：兩個獨立顯示元件：
  - `<div id="review-status" class="status">` 顯示操作狀態文字；錯誤時加上 `error` CSS 類別（紅色背景）。
  - `<pre id="output-reviews">` 顯示 API 原始回應字串（raw text），不做格式化或 syntax highlight。
- **相關程式碼片段**：
```javascript
function setStatus(message, isError) {
    const area = document.getElementById("review-status");
    area.textContent = message;
    area.classList.toggle("error", !!isError);
}

function setOutput(text) {
    document.getElementById("output-reviews").textContent = text;
}
```
