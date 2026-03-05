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
  - Body:
```json
[
  {"comment":"Updated comment","createdAt":"2026-03-05T11:45:16.047167","id":1,"rating":4,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T11:47:21.746313"},
  {"comment":"Very good","createdAt":"2026-03-05T11:45:16.047167","id":2,"rating":4,"studentId":2,"tutorCourseId":1,"updatedAt":"2026-03-05T11:45:16.047167"},
  {"comment":"Average experience","createdAt":"2026-03-05T11:45:16.047167","id":3,"rating":3,"studentId":3,"tutorCourseId":2,"updatedAt":"2026-03-05T11:45:16.047167"},
  {"comment":"Could improve","createdAt":"2026-03-05T11:45:16.047167","id":4,"rating":2,"studentId":1,"tutorCourseId":2,"updatedAt":"2026-03-05T11:45:16.047167"},
  {"comment":"Outstanding","createdAt":"2026-03-05T11:45:16.047167","id":5,"rating":5,"studentId":4,"tutorCourseId":3,"updatedAt":"2026-03-05T11:45:16.047167"},
  {"comment":"Great!","createdAt":"2026-03-05T11:47:31.539977","id":7,"rating":5,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T11:47:31.539977"}
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
  - Body:
```json
{"comment":"Updated comment","createdAt":"2026-03-05T11:45:16.047167","id":1,"rating":4,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T11:47:21.7463132"}
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
  - Body:
```json
[
  {"comment":"Updated comment","createdAt":"2026-03-05T11:45:16.047167","id":1,"rating":4,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T11:47:21.746313"},
  {"comment":"Could improve","createdAt":"2026-03-05T11:45:16.047167","id":4,"rating":2,"studentId":1,"tutorCourseId":2,"updatedAt":"2026-03-05T11:45:16.047167"}
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
  - Body:
```json
[
  {"comment":"Updated comment","createdAt":"2026-03-05T11:45:16.047167","id":1,"rating":4,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T11:47:21.746313"},
  {"comment":"Very good","createdAt":"2026-03-05T11:45:16.047167","id":2,"rating":4,"studentId":2,"tutorCourseId":1,"updatedAt":"2026-03-05T11:45:16.047167"}
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
  - Body:
```json
{"tutorCourseId":1,"averageRating":4.0}
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
  - Payload (Request Body)（來自 `<textarea id="review-body">`，預設值）：
```json
{
  "studentId": 1,
  "tutorCourseId": 1,
  "rating": 5,
  "comment": "Great!"
}
```

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body:
```json
{"comment":"Great!","createdAt":"2026-03-05T11:47:31.5399767","id":7,"rating":5,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T11:47:31.5399767"}
```
  - 資料解讀與處理邏輯：rawtext 顯示；回應含後端自動產生的 `id`、`createdAt`、`updatedAt`。
  - 顯示邏輯：`createReview()` → `runRequest()` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：`review-body` textarea 內容由使用者直接編輯後送出，前端不做欄位驗證，非法 JSON 會導致後端 `400`。
  - **注意**：以上內容來自實際 API 呼叫與程式碼分析。

---

### 7. 更新 Review（PUT）

* **請求資訊（HTTP Request）**
  - Method: `PUT`
  - URL: `{baseUrl}/reviews/{id}`（id 來自 `<input id="review-id">`）
  - Headers: `Content-Type: application/json`
  - Payload (Request Body)（來自 `<textarea id="review-update-body">`，預設值）：
```json
{
  "rating": 4,
  "comment": "Updated comment"
}
```

* **回應內容 (Response)**
  - HTTP Status: `200 OK`
  - Body:
```json
{"comment":"Updated comment","createdAt":"2026-03-05T11:45:16.047167","id":1,"rating":4,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T11:47:21.7463132"}
```
  - 資料解讀與處理邏輯：rawtext 顯示；`createdAt` 不變，`updatedAt` 更新為當下時間。
  - 顯示邏輯：`updateReview()` → `runRequest()` → `setOutput(text)` → `<pre id="output-reviews">`。
  - 其他重要細節：PUT 僅更新 `rating` 與 `comment`，`studentId` 與 `tutorCourseId` 由後端保留原值。
  - **注意**：以上內容來自實際 API 呼叫與程式碼分析。

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
  - Body: 空字串 `""`
  - 資料解讀與處理邏輯：`response.text()` 回傳空字串，`setOutput("{})" ` 顯示初始預設值 `{}`。
  - 顯示邏輯：`deleteReview()` → `runRequest()` → `setOutput(text || "{}")` → `<pre id="output-reviews">`。
  - 其他重要細節：若 ID 不存在，後端回 `404`；`204` 無 body，`pre` 仍顯示 `{}`（初始預設）。
  - **注意**：以上內容來自實際 API 呼叫與程式碼分析。

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
