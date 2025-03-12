package com.aos.core.util

import org.json.JSONObject

object JsonUtil {

    /**
     * JSON 문자열에서 특정 키 값을 추출하는 함수 (JSONObject 사용)
     * @param jsonString JSON 문자열
     * @param key 추출할 키 값
     * @return 해당 키의 값 (없으면 기본 메시지 반환)
     */

    fun extractErrorDetail(jsonString: String?): String {
        return try {
            jsonString?.let {
                val jsonObject = JSONObject(it)
                val response = jsonObject.optJSONObject("response") // "response" 객체 가져오기
                response?.optString("errorDetail", "알 수 없는 오류가 발생했습니다.") ?: "알 수 없는 오류가 발생했습니다."
            } ?: "알 수 없는 오류가 발생했습니다."
        } catch (e: Exception) {
            "알 수 없는 오류가 발생했습니다."
        }
    }
}
