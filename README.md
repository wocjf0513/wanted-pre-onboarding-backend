# 😁 wanted-pre-onboarding-backend <br> (원티드 프리온보딩 백엔드 인턴십 과제)


[인턴십 과제 상세](https://github.com/lordmyshepherd-edu/wanted-pre-onboardung-backend-selection-assignment)

## 1. 지원자의 성명
    심재철
## 2. 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
1. project를 clone한 후, maven project를 import합니다.

2. posts/ 에서 $ docker-compose up -d를 통해서 도커 이미지를 빌드합니다. 

		백그라운드 환경에서 mysql 이미지가 돌아가게 됩니다.
		application.yml을 따로 건들이지 않아도 mydb 데이터베이스가 기본적으로 있기 때문에 해당 db에 orm기능을 이용할 수 있습니다.
 
3. 해당 프로젝트를 maven install한 후, PostApplication.java 파일을 실행해줍니다.

       mvn install시 로컬 레포지토리인 target 폴더에 .jar파일이 생기고, 이를 $java -jar을 이용해 실행할 수 있습니다.

5. 이제 API 명세에 따라 사용하면 됩니다.
## 3. 데이터베이스 테이블 구조
![image](https://github.com/wocjf0513/wanted-pre-onboarding-backend/assets/59725406/4980cf63-3a81-41d0-ae4a-dd3288b22990)

## 4. 구현한 API의 동작을 촬영한 데모 영상 링크
## 5. 구현 방법 및 이유에 대한 간략한 설명
## 6. API 명세(request/response 포함)

