CREATE OR REPLACE VIEW `VIEW_INQ_CANCEL_SET` AS SELECT
    `t12`.`ID_KEY` AS `ID_KEY`,
	`t12`.`TRX_DATE` AS `TRX_DATE`,
    DATE_FORMAT(`t12`.`TRX_DATE`, '%d/%m/%Y') AS `TRX_DATE_STR`,
    `t12`.`TRX_TYPE` AS `TRX_TYPE`,
    `t12`.`TRX_VOUCHER_ID` AS `TRX_VOUCHER_ID`,
    `m5`.`CLI_NAME` AS `TRX_CLIENT_DESC`,
	`t12`.`TRX_CLIENT` AS `TRX_CLIENT`,
    `t12`.`TRX_DESCRIPTION` AS `TRX_DESCRIPTION`,
    `t12`.`TRX_DATA_STATUS` AS `TRX_DATA_STATUS`,
    `t12`.`TRX_CURR_ID` AS `TRX_CURR_ID`,
    `t12`.`TRX_INVC_AMOUNT` AS `TRX_INVC_AMOUNT`,
    `t12`.`TRX_SET_AMOUNT` AS `TRX_SET_AMOUNT`,
    `t12`.`TRX_INVC_AMOUNT` - `t12`.`TRX_SET_AMOUNT` AS `TRX_INVOICE_AMOUNT`,
    `t12`.`TRX_REMARKS` AS `TRX_REMARKS`
FROM
    (`TR0012` `t12`
JOIN `MA0005` `m5`)
WHERE
    `t12`.`TRX_CLIENT` = `m5`.`CLI_CODE` AND `t12`.`TRX_DATA_STATUS` = '11';
	