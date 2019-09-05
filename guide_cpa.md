# GAD CPA 연동 가이드
- 유저가 GAD에 참여신청을 하게되면 gad_tracking_id가 발급됩니다.
- GAD를 통해 광고 페이지로 이동시 URL에 이 gad_tracking_id 파라미터가 포함되게 됩니다.
- 플레이스토어 연동시에는 referrer 값으로 gad_tracking_id 값이 넘어갑니다.
- 유저의 액션이 완료됐을때 GAD 서버로 gad_tracking_id 값을 전송합니다.
- GAD가 액션 완료를 수신하면 gad_tracking_id 값에 따라 포스트백을 등록합니다.

## CPA 완료 통지
#### URL : https://gad.api.gpakorea.com/advertisement/comp
#### REQUEST (POST)
| 파라미터 | 내용 |
| --- | --- |
| gad_tracking_id | GAD 트래킹 ID (광고 참여 신청시마다 발급) |
#### RESPONSE
| 파라미터 | 내용 |
| --- | --- |
| code | 결과 CODE |
| message | 결과 메시지 |

- 참고 : [결과코드](https://github.com/koreagpa-dev/gad-sample-android/blob/master/api-doc.md#%EA%B2%B0%EA%B3%BC-%EC%BD%94%EB%93%9C)
