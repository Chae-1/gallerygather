엔터티 매핑
== 
## 객체, 테이블 매핑
## @Entity
- JPA에 해당 객체가 엔터티라는 것을 알려주는 어노테이션.
- 해당 클래스는 JPA에 의해 관리된다.
- 단, 아래의 조건을 모두 만족 해야 한다.
  - 기본 생성자 필수
  - final, enum, interface, inner 클래스는 적용안된다.
  - 저장할 필드(getter나 setter를 사용하기 때문에) final을 사용하면 안된다.
## @Table
- 실제 데이터베이스 테이블과 엔터티를 매핑하는 어노테이션.
- User 엔터티가 실제 데이터베이스에는 users 라고 생성되었을 때 사용.
### @Table 속성
  - name
    - 매핑 테이블 이름 지정
  - uniqueConstraints
    - DDL 생성 시, 유니크 제약 조건 생성

## 스키마 자동 생성 기능
- DDL을 애플리케이션 실행 시점에 자동 생성
- 데이터베이스 방언(데이터베이스 문법)을 활용 적절한 DDL 생성

### hibernate.hbm2ddl.auto, spring.hibernate.ddl.auto
- create
  - 기존 테이블을 drop하고 새로 테이블 생성.
  - 운영 환경에서 절대로 사용하면 안됨.
- create-drop
  - create와 같지만, 종료시점에 테이블 DROP
- update
  - 변경분만 반영
- validate
  - 실제 테이블과 정상 매핑되었나?
- none
  - 사용 x

개발 중에는 create, update, 하나의 브런치에 통합하거나 운영시 validate.

## 매핑 어노테이션
- `@Column`
  - name
    - 테이블에서 사용할 컬럼이름 지정.
  - insertable, updatable
    - 등록, 변경 가능 여부
  - nullable
    - null 허용 여부를 등록
    - not null 제약 조건 설정
  - columnDefinition
    - 컬럼 정보를 직접 준다.
  - length
    - 길이를 설정할 수 있음
    - string 타입에만 적용가능
  - precision
    - 숫자 타입의 정밀도
- `@Temporal`
  - 날짜 타입 매핑에 사용
  - LocalDatexx 사용시 필요 없음.
- `@Enumerated`
  - Enum 타입 필드 사용시 사용
  - 반드시 String 사용한다.
- `@Lob`
  - 큰 타입의 데이터의 경우 사용
  - String : CLOB, 다른 타입 : BLOB
- `@Transient`
  - 실제 DB에 저장하지 않을 속성 지정

## 기본 키 매핑
- `@Id`
  - 기본키로 매핑할 속성에 해당 어노테이션을 붙인다.
- `@GeneratedValue`
  - 기본키를 자동으로 생성하는데 있어서 전략을 선택하는 어노테이션

### @GeneratedValue
- IDENTITY
  - 기본 키 생성을 데이터베이스에 위임.
  - oracle에서 사용했던 auto_increment 전략, mysql의 auto_increment
  - DB에서 자동으로 생성해주기 때문에 영속성에 관리되는 시점에 insert 된다.
- SEQUENCE
  - 기본 키를 생성하기 위해 데이터베이스 시퀸스를 사용해서 자동 생성.
  - auto_increment를 지원하지 않는 DB에서 사용하거나 시퀸스로 관리하고 싶을 때 사용.
  - `@SequenceGenerator`가 필요
    - initialValue
    - allocationSize
- TABLE
  - 키 생성용 테이블을 만들고 이를 기본 키 값으로 사용하는 전략
  - @TableGenerator 필요
- AUTO
  - DB에 따라 자동으로 전략을 택함.

기본 키 제약 조건을 지킬 수 있는 자연키를 찾기가 어렵기 때문에 대체키를 사용하자.

## 연관 관계의 주인

### 객체와 테이블의 연관 관계 차이
- 객체는 참조를 통해 객체 사이의 관계를 구축한다. 
- 테이블은 외래키를 통해 테이블 사이의 관계를 구축한다.
- 객체를 테이블에 맞춰 설계하면 객체지향적으로 코드를 짤 수 없다.

### 단방향 연관관계
- 실제 DB에 있는 외래키와 엔터티에 존재하는 객체와 매핑하는 작업이 연관관계 매핑이다.
- 다중성을 나타내는 데 사용하는 `@ManyToOne`, `@JoinColumn`를 사용.


### 양방향 연관관계
- 객체는 양방향 연관관계를 갖기 위해 서로 다른 참조를 가져야한다.
- 단방향에서 설정한 한 방향(N:1) 이외 다른 방향에도 (1:N) 이를 알려야한다.
- mappedBy 속성을 사용 해 두 객체 중 어느 객체가 연관관계의 주인인지 설정해줘야한다.
  - 주인은 mappedBy 속성을 사용하지 않고, 주인이 아니면 이를 사용해서 연관관계의 주인을 지정해야한다.
  - 어느 객체가 두 관계 사이의 외래키를 관리할 것인지를 결정하는 것이다.
  - 실제 테이블의 외래키가 있는 객체가 연관관계의 주인이 된다.
- mappedBy 속성으로 지정한 곳은 조회밖에 하지 못한다.

