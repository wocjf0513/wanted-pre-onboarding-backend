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
![image](https://github.com/wocjf0513/wanted-pre-onboarding-backend/assets/59725406/1482e18e-752e-4192-bc3f-b3cb98ed6f40)


## 4. 구현한 API의 동작을 촬영한 데모 영상 링크
[데모 영상 링크](https://blog.naver.com/wocjf0513/223181579318)
## 5. 구현 방법 및 이유에 대한 간략한 설명
	1) 구현하기 전, Visual Paradigm프로그램을 통해 데이터베이스에 대한 설계를 먼저 했다. 
 	POST USER과 서로 참조하면서, USER삭제시 USER가 작성했던 POST가 다 삭제되도록 합성관계를 갖게 했다. USER가 삭제됐는데, POST가 존재하면 이상하기 때문이다.
  
 	2) 그리고, 요구사항에 필요한 기술스택을 생각해보고 이 과정에서 SpringSecurity, Spring MVC, SpringDataJpa, Docker, JWT를 사용하자고 생각했다. 
  	요구사항에 JWT가 있는데, JWT만 사용하는 경우는 없으므로 SpringSecurity를 기술스택으로 선정했다. 
   	또, docker-compose up을 통해 database같은 환경 설정을 따로 하지 않아도 이미지를 가져와 환경을 구성할 수 있는 편리함에 Docker를 선정했다. 
  	마지막으로는 요구사항에 나온 API들이 일반적인 게시물, 로그인 API이므로 mybatis가 아니어도 springdatajpa를 통해 쉽게 인터페이스를 구성해 사용할 수 있다고 생각했다.
   
	3) 먼저 mysql 이미지를 docker를 통해 가져와서 이를 활용해 DB를 구성했고 이에 대한 설정을 application.properties에서 해준다. 
 	
  	4) 그 다음엔 Spring Security 설정이었다. 
	이를 위한 설정을 SecurityConfig라는 클래스에 작성했다. 
 	UsernamePasswordToken을 발급하는 UsernamePasswordToeknFilter가 작동하기 전, JWT Filter가 발급된 Token에 대해 vaildation을 하고, 유효할 시에 SecurityContext에 이 Token에 대한 정보를 넣어줄 수 있게 해줬다. 
  	그리고 SpringSecrity를 이용해 인증받은 사용자만 POST관련 API를 사용할 수 있도록 PATH를 지정해줬다.
 	
  	5) 다음으로는 요구사항에 관련된 API구현이었다. mvc패턴을 지키기 위해 각각 controller, service, repository를 책임을 분배했다. 
   	이때, entity는 repository와 serivce간에서만 사용되고 다른 layer간의 데이터 전송에는 DTO나 VO를 사용하는 규칙을 지키면서 구현했다. 
    	이는 계층별 관심사를 분리하고 DTO나 VO를 사용하면서 필요한 데이터만 Entity에서 추출함으로서 데이터의 양을 줄일 수 있다. 
     	마지막으로 RESTful API를 위해 HTTP URI 자원을 명시하고 HTTP Method를 통해 행위를 표현하는 규칙을 지키면서 구현했다.
   
## 6. API 명세(request/response 포함)
![image](https://github.com/wocjf0513/wanted-pre-onboarding-backend/assets/59725406/7173aa40-27a1-48d6-aec4-0e5b869e93e7)
![image](https://github.com/wocjf0513/wanted-pre-onboarding-backend/assets/59725406/628c9897-4900-4063-af9e-18a462e26aef)
![image](https://github.com/wocjf0513/wanted-pre-onboarding-backend/assets/59725406/e6f9d8ed-5a0e-4e3b-a469-8526c0bf2d57)
![image](https://github.com/wocjf0513/wanted-pre-onboarding-backend/assets/59725406/d8db8d80-54b9-4df0-ba26-f6f5a55f0545)
![image](https://github.com/wocjf0513/wanted-pre-onboarding-backend/assets/59725406/d2e3b1df-b1d5-4280-a31d-8ec1f808cb2b)
![image](https://github.com/wocjf0513/wanted-pre-onboarding-backend/assets/59725406/babd7653-3843-4129-8c57-143f6d08d03f)
![image](https://github.com/wocjf0513/wanted-pre-onboarding-backend/assets/59725406/9a24e0fc-e393-4660-9f48-3816f49a6618)
![image](https://github.com/wocjf0513/wanted-pre-onboarding-backend/assets/59725406/8487b488-f85e-44a4-b84b-d22439de6858)


