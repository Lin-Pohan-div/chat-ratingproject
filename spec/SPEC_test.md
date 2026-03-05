# 規格文件 - test.html
來源檔案: `web/test.html`（實際分析檔案：`src/main/resources/static/test.html`）
---
## API 互動邏輯 (fetch)
針對頁面中每一個 Web API 呼叫（fetch/XHR），填寫以下資訊。

### 1. 取得課程列表（Courses - GET All）
* **請求資訊（HTTP Request）**
- Method: `GET`
- URL: `/api/courses`
- Headers: 無自訂 Header（瀏覽器預設）
- Payload (Request Body):
```json
null
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
[{"bookingId":101,"courseDate":"2026-03-05","createdAt":"2026-03-05T09:55:57.221179","hour":2,"id":1,"status":1,"updatedAt":"2026-03-05T09:55:57.287956"}]
```
- 資料解讀與處理邏輯：前端未做轉換，直接以文字方式顯示回應內容。
- 顯示邏輯：呼叫 `fetchAll('courses')`，寫入 `textarea#output-courses`。
- 其他重要細節：此頁面以 `res.text()` 接收回應，不做 JSON parse。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 2. 建立課程（Courses - POST）
* **請求資訊（HTTP Request）**
- Method: `POST`
- URL: `/api/courses`
- Headers: `Content-Type: application/json`
- Payload (Request Body):
```json
{"bookingId":101,"courseDate":"2026-03-05","hour":2,"status":0}
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
{"bookingId":101,"courseDate":"2026-03-05","createdAt":"2026-03-05T09:56:26.2566816","hour":2,"id":2,"status":0,"updatedAt":"2026-03-05T09:56:26.2566816"}
```
- 資料解讀與處理邏輯：前端直接顯示後端回傳的新建資料（含 id 與時間戳）。
- 顯示邏輯：`post('courses', body)` -> `log('courses', data)`。
- 其他重要細節：頁面預設 textarea 範例 JSON 與後端 `Course` 欄位不完全一致，實測需送 `bookingId` 才能成功。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 3. 取得單一課程（Courses - GET by ID）
* **請求資訊（HTTP Request）**
- Method: `GET`
- URL: `/api/courses/1`
- Headers: 無自訂 Header（瀏覽器預設）
- Payload (Request Body):
```json
null
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
{"bookingId":101,"courseDate":"2026-03-05","createdAt":"2026-03-05T09:55:57.221179","hour":2,"id":1,"status":1,"updatedAt":"2026-03-05T09:55:57.287956"}
```
- 資料解讀與處理邏輯：無額外處理。
- 顯示邏輯：`fetchById('courses', id)` 寫入 `output-courses`。
- 其他重要細節：若 ID 不存在，後端回 `404`。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 4. 更新課程狀態（Courses - PATCH status）
* **請求資訊（HTTP Request）**
- Method: `PATCH`
- URL: `/api/courses/1/status`
- Headers: `Content-Type: application/json`
- Payload (Request Body):
```json
{"status":1}
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
{"bookingId":101,"courseDate":"2026-03-05","createdAt":"2026-03-05T09:55:57.221179","hour":2,"id":1,"status":1,"updatedAt":"2026-03-05T09:55:57.287956"}
```
- 資料解讀與處理邏輯：`patchStatus` 將 `status` 轉為整數後送出。
- 顯示邏輯：結果寫入 `output-courses`。
- 其他重要細節：若課程不存在，後端回 `404`。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 5. 取得評論列表（Reviews - GET All）
* **請求資訊（HTTP Request）**
- Method: `GET`
- URL: `/api/reviews`
- Headers: 無自訂 Header（瀏覽器預設）
- Payload (Request Body):
```json
null
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
[{"comment":"updated by spec","createdAt":"2026-03-05T09:42:38.882853","id":1,"rating":4,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T09:55:57.482477"},{"comment":"Very good","createdAt":"2026-03-05T09:42:38.882853","id":2,"rating":4,"studentId":2,"tutorCourseId":1,"updatedAt":"2026-03-05T09:42:38.882853"},{"comment":"Average experience","createdAt":"2026-03-05T09:42:38.882853","id":3,"rating":3,"studentId":3,"tutorCourseId":2,"updatedAt":"2026-03-05T09:42:38.882853"},{"comment":"Could improve","createdAt":"2026-03-05T09:42:38.882853","id":4,"rating":2,"studentId":1,"tutorCourseId":2,"updatedAt":"2026-03-05T09:42:38.882853"},{"comment":"Outstanding","createdAt":"2026-03-05T09:42:38.882853","id":5,"rating":5,"studentId":4,"tutorCourseId":3,"updatedAt":"2026-03-05T09:42:38.882853"},{"comment":"spec test","createdAt":"2026-03-05T09:55:57.454571","id":7,"rating":5,"studentId":9,"tutorCourseId":1,"updatedAt":"2026-03-05T09:55:57.454571"}]
```
- 資料解讀與處理邏輯：無欄位轉換，直接輸出字串。
- 顯示邏輯：`fetchAll('reviews')` 寫入 `textarea#output-reviews`。
- 其他重要細節：資料量變化會直接反映在回傳 JSON。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 6. 取得單一評論（Reviews - GET by ID）
* **請求資訊（HTTP Request）**
- Method: `GET`
- URL: `/api/reviews/1`
- Headers: 無自訂 Header（瀏覽器預設）
- Payload (Request Body):
```json
null
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
{"comment":"updated by spec","createdAt":"2026-03-05T09:42:38.882853","id":1,"rating":4,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T09:55:57.482477"}
```
- 資料解讀與處理邏輯：無。
- 顯示邏輯：`fetchById('reviews', id)` 寫入 `output-reviews`。
- 其他重要細節：查無資料時為 `404`。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 7. 依學生查評論（Reviews - GET by Student）
* **請求資訊（HTTP Request）**
- Method: `GET`
- URL: `/api/reviews/student/1`
- Headers: 無自訂 Header（瀏覽器預設）
- Payload (Request Body):
```json
null
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
[{"comment":"updated by spec","createdAt":"2026-03-05T09:42:38.882853","id":1,"rating":4,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T09:55:57.482477"},{"comment":"Could improve","createdAt":"2026-03-05T09:42:38.882853","id":4,"rating":2,"studentId":1,"tutorCourseId":2,"updatedAt":"2026-03-05T09:42:38.882853"}]
```
- 資料解讀與處理邏輯：無。
- 顯示邏輯：由 `fetchAll('reviews/student/' + id)` 呼叫，寫入 `output-reviews`。
- 其他重要細節：回傳陣列可能為空。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 8. 依課程查評論（Reviews - GET by TutorCourse）
* **請求資訊（HTTP Request）**
- Method: `GET`
- URL: `/api/reviews/tutor-course/1`
- Headers: 無自訂 Header（瀏覽器預設）
- Payload (Request Body):
```json
null
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
[{"comment":"updated by spec","createdAt":"2026-03-05T09:42:38.882853","id":1,"rating":4,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T09:55:57.482477"},{"comment":"Very good","createdAt":"2026-03-05T09:42:38.882853","id":2,"rating":4,"studentId":2,"tutorCourseId":1,"updatedAt":"2026-03-05T09:42:38.882853"},{"comment":"spec test","createdAt":"2026-03-05T09:55:57.454571","id":7,"rating":5,"studentId":9,"tutorCourseId":1,"updatedAt":"2026-03-05T09:55:57.454571"}]
```
- 資料解讀與處理邏輯：無。
- 顯示邏輯：`fetchToOutput(..., 'reviews')` 強制輸出到 `output-reviews`。
- 其他重要細節：此呼叫使用 `fetchToOutput`，避免 output key 路徑錯誤。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 9. 取得平均評分（Reviews - GET Avg Rating）
* **請求資訊（HTTP Request）**
- Method: `GET`
- URL: `/api/reviews/tutor-course/1/average-rating`
- Headers: 無自訂 Header（瀏覽器預設）
- Payload (Request Body):
```json
null
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
{"averageRating":4.333333333333333,"tutorCourseId":1}
```
- 資料解讀與處理邏輯：後端直接回傳平均值，前端不做四捨五入。
- 顯示邏輯：`fetchToOutput(..., 'reviews')`，顯示於 `output-reviews`。
- 其他重要細節：若無資料，後端會回傳 `averageRating: 0.0`（Controller fallback）。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 10. 建立評論（Reviews - POST）
* **請求資訊（HTTP Request）**
- Method: `POST`
- URL: `/api/reviews`
- Headers: `Content-Type: application/json`
- Payload (Request Body):
```json
{"studentId":9,"tutorCourseId":1,"rating":5,"comment":"spec test"}
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
{"comment":"spec test","createdAt":"2026-03-05T09:56:26.4550181","id":8,"rating":5,"studentId":9,"tutorCourseId":1,"updatedAt":"2026-03-05T09:56:26.4550181"}
```
- 資料解讀與處理邏輯：無。
- 顯示邏輯：`post('reviews', body)` 寫入 `output-reviews`。
- 其他重要細節：由 `@PrePersist` 自動產生 `createdAt/updatedAt`。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 11. 更新評論（Reviews - PUT）
* **請求資訊（HTTP Request）**
- Method: `PUT`
- URL: `/api/reviews/1`
- Headers: `Content-Type: application/json`
- Payload (Request Body):
```json
{"rating":4,"comment":"updated by spec"}
```
* **回應內容 (Response)**
- HTTP Status: `200 OK`
- Body
```json
{"comment":"updated by spec","createdAt":"2026-03-05T09:42:38.882853","id":1,"rating":4,"studentId":1,"tutorCourseId":1,"updatedAt":"2026-03-05T09:55:57.482477"}
```
- 資料解讀與處理邏輯：更新 `rating/comment`，其餘欄位維持原值。
- 顯示邏輯：`put('reviews', id, body)` 寫入 `output-reviews`。
- 其他重要細節：查無 ID 時回 `404`。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

### 12. 刪除評論（Reviews - DELETE）
* **請求資訊（HTTP Request）**
- Method: `DELETE`
- URL: `/api/reviews/7`
- Headers: 無自訂 Header（瀏覽器預設）
- Payload (Request Body):
```json
null
```
* **回應內容 (Response)**
- HTTP Status: `204 No Content`
- Body
```json
[]
```
- 資料解讀與處理邏輯：前端對 `204` 以固定字串 `Deleted (204 No Content)` 顯示，不讀取 body。
- 顯示邏輯：`del('reviews', id)`，成功/失敗都寫入 `output-reviews`。
- 其他重要細節：若刪除不存在 ID，後端回 `404`。
- **注意**：以上內容來自實際 API 呼叫與程式碼分析。
-

## 其他重要功能或邏輯
- 功能/邏輯名稱：統一 URL 組裝與輸出路由
- 描述：所有 API 透過 `makeUrl(resource, id)` 產生路徑；輸出預設依 `output-${resource}` 對應 textarea。針對動態路徑（如 average-rating）使用 `fetchToOutput(..., 'reviews')` 避免輸出目標錯誤。
- 相關程式碼片段：
```javascript
function makeUrl(resource, id = '') {
    return `${base}/${resource}${id ? '/' + id : ''}`;
}

function log(resource, text) {
    const area = document.getElementById('output-' + resource);
    if (area) {
        area.value = text;
    } else {
        console.log(text);
    }
}

async function fetchToOutput(resource, outputKey) {
    const res = await fetch(makeUrl(resource));
    const data = await res.text();
    log(outputKey, data);
}
```

- 功能/邏輯名稱：HTTP 方法封裝
- 描述：頁面將 `GET/POST/PUT/PATCH/DELETE` 封裝為函式，便於快速驗證 Controller。所有函式皆採 `res.text()`，因此 API 回傳會以原始字串展示。
- 相關程式碼片段：
```javascript
async function post(resource, body) {
    const res = await fetch(makeUrl(resource), {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body
    });
    const data = await res.text();
    log(resource, data);
}

async function del(resource, id) {
    const res = await fetch(makeUrl(resource, id), {
        method: 'DELETE'
    });
    const data = res.status === 204 ? 'Deleted (204 No Content)' : await res.text();
    log(resource, data);
}
```
