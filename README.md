# TODO List

### 섬머코딩 과제

**지원자** : 안영진  
**지원분야** : 웹/서버

**사용한 프레임워크**  
Spring Boot 2.0 + Spring JPA + Thymeleaf

---

**기능 요구사항**
- [x] 새로운 TODO(제목과 내용)를 작성할 수 있다.
- [x] TODO 목록을 볼 수 있다.
- [x] TODO 항목의 제목과 내용을 수정할 수 있다.
- [x] TODO 항목을 삭제할 수 있다.
- [x] 사용자의 선택에 의해 TODO에는 마감 기한을 넣을 수 있다.
- [x] TODO 항목의 우선순위를 설정 및 조절할 수 있다.
- [x] TODO 항목에 대한 완료 처리를 할 수 있다.
- [x] 마감기한이 지난 TODO에 대해 알림을 노출할 수 있다

---

**설치**

- java 1.8 이상

github 프로젝트 clone
```$xslt
mkdir git
cd git
git clone https://github.com/hellozin/todo-list.git
```

배포 스크립트인 deploy.sh 에 실행권한 부여 및 실행
```$xslt
chmod 755 ./deploy.sh
./deploy.sh
```