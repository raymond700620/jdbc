CREATE TABLE CERT_EXAM_RESULT (
  ID         BIGINT(20) NOT NULL AUTO_INCREMENT,
  DATA_SOURCE VARCHAR(50),
  CREATE_DATE    DATE,
  UPDATE_DATE    DATE,
  CANDIDATE_EMAIL     VARCHAR(250),
  CANDIDATE_FIRSTNAME VARCHAR(50),
  CANDIDATE_LASTNAME  VARCHAR(50),
  CANDIDATE_COMPANY   VARCHAR(100),
  SITE_REGION   VARCHAR(50),
  SITE_COUNTRY  VARCHAR(50),
  EXAM_CODE VARCHAR(100),
  EXAM_TITLE VARCHAR(250),
  EXAM_DATE  DATE,
  SCORE BIGINT(20),
  GRADE VARCHAR(50),

  PRIMARY KEY (ID)
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;

CREATE UNIQUE INDEX UNIQUE_EXAM_ENTRY ON CERT_EXAM_RESULT (
  DATA_SOURCE,
  CANDIDATE_EMAIL,
  EXAM_CODE,
  EXAM_DATE
);


CREATE TABLE EXAM_CODE_MAP (
  ID         INT(20) NOT NULL AUTO_INCREMENT,
  DATA_SOURCE VARCHAR(50),
  EXAM_CODE VARCHAR(100),
  PIVOTAL_CODE VARCHAR(100),
  CREATE_DATE    DATE,
  UPDATE_DATE    DATE,

  PRIMARY KEY (ID)
)
  ENGINE = innodb
  DEFAULT CHARSET = utf8;

CREATE UNIQUE INDEX UNIQUE_EXAM_CODE_MAP ON EXAM_CODE_MAP (
  DATA_SOURCE,
  EXAM_CODE,
  PIVOTAL_CODE
);