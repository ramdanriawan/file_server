CREATE OR REPLACE VIEW VIEW_INQ_CLAIMS_CREATE AS
SELECT 
	T.ID_KEY,
	T.TRX_CLASS,
    T.TRX_TRX_ID,
    T.TRX_VOUCHER_ID,
    T6A.TRX_COVER_CODE,
    T6A.TRX_REMARKS,
	DATE_FORMAT(T6A.TRX_INS_START, '%d %b %Y') TRX_INS_START,
	DATE_FORMAT(T6A.TRX_INS_END, '%d %b %Y') TRX_INS_END,
    CONCAT(DATE_FORMAT(T6A.TRX_INS_START, '%d %b %Y'), ' - ', DATE_FORMAT(T6A.TRX_INS_END, '%d %b %Y')) PERIOD,
    T.TRX_CLIENT,
    M5.CLI_NAME TRX_CLIENT_DESC,
    T.TRX_INSURED_NAME,
    T.TRX_CURR_ID,
    T.TRX_TSI_AMOUNT,
    T6A.TRX_INS_INSURED,
	'' SOURCE,
	0 TRX_DEDUC_AMT,
	0 TRX_REINS_NO,
	0 TRX_LIMIT_AMT,
	0 A3
FROM TR0006 T, TR0006A T6A, MA0005 M5
	WHERE T.TRX_CLASS <> 'TRE'
	AND T.TRX_CLASS = T6A.TRX_CLASS 
    AND T.TRX_TRX_ID = T6A.TRX_TRX_ID
    AND T.TRX_VOUCHER_ID = T6A.TRX_VOUCHER_ID
    AND T.TRX_CLIENT = M5.CLI_CODE
 	AND TRX_DATA_STATUS = '11'
 	AND TRX_CLAIM_STATUS = '0'
UNION
SELECT 
	T.ID_KEY,
	T.TRX_CLASS,
    T.TRX_TRX_ID,
    T.TRX_VOUCHER_ID,
    T6J.TRX_COVER_CODE,
    T6J.TRX_REMARKS,
	DATE_FORMAT(T6J.TRX_INS_START, '%d %b %Y') TRX_INS_START,
	DATE_FORMAT(T6J.TRX_INS_END, '%d %b %Y') TRX_INS_END,
    CONCAT(DATE_FORMAT(T6J.TRX_INS_START, '%d %b %Y'), ' - ', DATE_FORMAT(T6J.TRX_INS_END, '%d %b %Y')) PERIOD,
    T.TRX_CLIENT,
    M5.CLI_NAME TRX_CLIENT_DESC,
    T.TRX_INSURED_NAME,
    T.TRX_CURR_ID,
    T.TRX_TSI_AMOUNT,
    '' TRX_INS_INSURED,
	(SELECT MAX(TJ.TRX_NON_PRO) FROM TR0006J TJ WHERE T6J.TRX_VOUCHER_ID = T.TRX_VOUCHER_ID) SOURCE,
	(SELECT SUM(TJ.TRX_DEDUC_AMT) FROM TR0006J TJ WHERE T6J.TRX_VOUCHER_ID = T.TRX_VOUCHER_ID) TRX_DEDUC_AMT,
	T6J.TRX_REINS_NO,
	(SELECT SUM(TJ.TRX_LIMIT_AMT) FROM TR0006J TJ WHERE T6J.TRX_VOUCHER_ID = T.TRX_VOUCHER_ID) TRX_LIMIT_AMT,
	(SELECT SUM(TJ.TRX_OGNRPI) * (SUM(TJ.TRX_XOL_RATE)/100) * (SUM(TJ.TRX_DEPOSIT_RATE)/100) FROM TR0006J TJ WHERE T6J.TRX_VOUCHER_ID = T.TRX_VOUCHER_ID) A3
FROM TR0006 T, TR0006J T6J, MA0005 M5
	WHERE T.TRX_CLASS = 'TRE'
	AND T.TRX_CLASS = T6J.TRX_CLASS 
    AND T.TRX_TRX_ID = T6J.TRX_TRX_ID
    AND T.TRX_VOUCHER_ID = T6J.TRX_VOUCHER_ID
    AND T.TRX_CLIENT = M5.CLI_CODE
 	AND TRX_DATA_STATUS = '11'
 	AND TRX_CLAIM_STATUS = '0';