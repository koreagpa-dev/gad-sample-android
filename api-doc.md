# GAD API DOCUMENT

## 광고 목록
#### URL : https://gad.api.gpakorea.com/advertisement/list
#### REQUEST
| 파라미터 | 내용 |
| ------------------- | ------------------- |
| media | 미디어 키 |
#### RESPONSE
| 파라미터 | 내용 |
| ------------------- | ------------------- |
| code | 결과 CODE |
| message | 결과 메시지 |
| **items** | 광고 목록 배열 |
#### items
| 파라미터 | 내용 |
| ------------------- | ------------------- |
| key | 광고키 |
| type | 광고타입 (0: 참여형, 1: 설치형, 2: 실행형, 3: 미션형) |
| title | 광고명 |
| url | 광고 URL |
| point | 적립 포인트 |
| icon | 광고 아이콘 |
| mission | 참여방법 |
| **app** | 앱정보 |
| **detail** | 광고 상세 정보 |
#### app
| 파라미터 | 내용 |
| ------------------- | ------------------- |
| market | 마켓 종류 (1: 구글플레이, 2: 원스토어, 3: 웹, 9:etc) |
| package | 패키지명 |
| scheme | custom url scheme |
#### detail
| 파라미터 | 내용 |
| ------------------- | ------------------- |
| require | 광고에 필요한 정보 (ex. 카페닉네임, 사전예약전화번호, 댓글아이디) |
| summary | 개요 |
| description | 상세설명 |
| image1 | 광고 이미지 |

## 포스트백
| 파라미터 | 내용 |
| ------------------- | ------------------- |
| seq | 적립 고유 ID |
| media | 미디어키 |
| adKey | 광고키 |
| adTitle | 광고명 |
| uid | 유저 ID |
| publisher_data | API 방식으로 광고 참여시 전송했던 publisher_data |
| point | 적립 금액 |

