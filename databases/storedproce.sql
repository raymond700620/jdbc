DELIMITER //
CREATE PROCEDURE MERGE_STAGE_2_MAIN()
BEGIN
  INSERT INTO CERT_EXAM (
    EXAM_CENTER,
    FIRST_NAME,
	LAST_NAME,
	IE_CANDIDATE_ID,
	CANDIDATE_EMAIL,
	COMPANY_NAME,
	TITLE,
	FULL_NAME,
	HOME_ADDRESS_1,
	HOME_ADDRESS_2,
	CITY,
	STATE_PROVINCE,
	ZIP,
	COUNTRY_CODE,
	PHONE,
	SPONSOR_NAME,
	EXAM_NAME,
	EXAM_LMS_ID,
	EXAM_CODE,
	EXAM_DURATION,
	TRANSACTION_DATE,
	ADMIN_DATE,
	EXAM_START_DATE_TIME,
	EXAM_COMP_DATE_TIME,
	DELIVERY_COUNTRY,
	EXAM_STATUS,
	ATTEMP_NO,
	EXAM_SCORE,
	EXAM_PERCENTAGE,
	EXAM_RESULT,
	CONFIRMATION_CODE,
	PROMO_CODE,
	PAYMENT_METHOD,
	TAXES,
	EXAM_FEE,
	PAYMENT_AMOUNT,
	PO_BILLING_TYPE,
	ECREDIT_CODE,
	RESERVATION_GUID,
	AGREE_CHECK_DATE_TIME
 )
 SELECT
	EXAM_CENTER,
    FIRST_NAME,
	LAST_NAME,
	IE_CANDIDATE_ID,
	CANDIDATE_EMAIL,
	COMPANY_NAME,
	TITLE,
	FULL_NAME,
	HOME_ADDRESS_1,
	HOME_ADDRESS_2,
	CITY,
	STATE_PROVINCE,
	ZIP,
	COUNTRY_CODE,
	PHONE,
	SPONSOR_NAME,
	EXAM_NAME,
	EXAM_LMS_ID,
	EXAM_CODE,
	EXAM_DURATION,
	STR_TO_DATE(TRANSACTION_DATE,'%m/%d/%Y %h:%i %p'),
	STR_TO_DATE(ADMIN_DATE,'%m/%d/%Y %h:%i %p'),
	STR_TO_DATE(EXAM_START_DATE_TIME,'%m/%d/%Y %h:%i %p'),
	STR_TO_DATE(EXAM_COMP_DATE_TIME,'%m/%d/%Y %h:%i %p'),
	DELIVERY_COUNTRY,
	EXAM_STATUS,
	ATTEMP_NO,
	EXAM_SCORE,
	EXAM_PERCENTAGE,
	EXAM_RESULT,
	CONFIRMATION_CODE,
	PROMO_CODE,
	PAYMENT_METHOD,
	TAXES,
	EXAM_FEE,
	PAYMENT_AMOUNT,
	PO_BILLING_TYPE,
	ECREDIT_CODE,
	RESERVATION_GUID,
	STR_TO_DATE(AGREE_CHECK_DATE_TIME,'%m/%d/%Y %h:%i %p')
 FROM CERT_EXAM_STAGE
ON DUPLICATE KEY
UPDATE
    EXAM_CENTER = VALUES(EXAM_CENTER),
    FIRST_NAME=VALUES(FIRST_NAME),
	LAST_NAME=VALUES(LAST_NAME),
	IE_CANDIDATE_ID=VALUES(IE_CANDIDATE_ID),
	CANDIDATE_EMAIL=VALUES(CANDIDATE_EMAIL),
	COMPANY_NAME=VALUES(COMPANY_NAME),
	TITLE=VALUES(TITLE),
	FULL_NAME=VALUES(FULL_NAME),
	HOME_ADDRESS_1=VALUES(HOME_ADDRESS_1),
	HOME_ADDRESS_2=VALUES(HOME_ADDRESS_2),
	CITY=VALUES(CITY),
	STATE_PROVINCE=VALUES(STATE_PROVINCE),
	ZIP=VALUES(ZIP),
	COUNTRY_CODE=VALUES(COUNTRY_CODE),
	PHONE=VALUES(PHONE),
	SPONSOR_NAME=VALUES(SPONSOR_NAME),
	EXAM_NAME=VALUES(EXAM_NAME),
	EXAM_LMS_ID=VALUES(EXAM_LMS_ID),
	EXAM_CODE=VALUES(EXAM_CODE),
	EXAM_DURATION=VALUES(EXAM_DURATION),
	TRANSACTION_DATE=VALUES(TRANSACTION_DATE),
	ADMIN_DATE=VALUES(ADMIN_DATE),
	EXAM_START_DATE_TIME=VALUES(EXAM_START_DATE_TIME),
	EXAM_COMP_DATE_TIME=VALUES(EXAM_COMP_DATE_TIME),
	DELIVERY_COUNTRY=VALUES(DELIVERY_COUNTRY),
	EXAM_STATUS=VALUES(EXAM_STATUS),
	ATTEMP_NO=VALUES(ATTEMP_NO),
	EXAM_SCORE=VALUES(EXAM_SCORE),
	EXAM_PERCENTAGE=VALUES(EXAM_PERCENTAGE),
	EXAM_RESULT=VALUES(EXAM_RESULT),
	CONFIRMATION_CODE=VALUES(CONFIRMATION_CODE),
	PROMO_CODE=VALUES(PROMO_CODE),
	PAYMENT_METHOD=VALUES(PAYMENT_METHOD),
	TAXES=VALUES(TAXES),
	EXAM_FEE=VALUES(EXAM_FEE),
	PAYMENT_AMOUNT=VALUES(PAYMENT_AMOUNT),
	PO_BILLING_TYPE=VALUES(PO_BILLING_TYPE),
	ECREDIT_CODE=VALUES(ECREDIT_CODE),
	AGREE_CHECK_DATE_TIME=VALUES(AGREE_CHECK_DATE_TIME);
END //
DELIMITER ;