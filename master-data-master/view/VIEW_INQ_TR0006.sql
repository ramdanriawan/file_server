CREATE OR REPLACE VIEW `VIEW_INQ_TR0006` AS
SELECT
row_number() over(
ORDER BY
    `a`.`TRX_VOUCHER_ID` DESC, `a`.`TRX_SOURCE_CODE` DESC, `a`.`TRX_DATE` DESC 
) AS `ROW_NUMBER`,
`a`.`TR6_VOUCHER_ID` AS `TR6_VOUCHER_ID`,
`a`.`ID_KEY` AS `ID_KEY`,
`a`.`TRX_DATE` AS `TRX_DATE`,
`a`.`TRX_TRX_ID` AS `TRX_TRX_ID`,
`a`.`TRX_VOUCHER_ID` AS `TRX_VOUCHER_ID`,
`a`.`CLI_NAME` AS `CLI_NAME`,
`a`.`TRX_INSURED_NAME` AS `TRX_INSURED_NAME`,
`a`.`TRX_CURR_ID` AS `TRX_CURR_ID`,
`a`.`TRX_TSI_AMOUNT` AS `TRX_TSI_AMOUNT`,
`a`.`TRX_CLIENT` AS `TRX_CLIENT`,
`a`.`TRX_COVER_CODE` AS `TRX_COVER_CODE`,
`a`.`CREATE_ON` AS `CREATE_ON`,
`a`.`TRX_SOURCE` AS `TRX_SOURCE`,
`a`.`TRX_SOURCE_CODE` AS `TRX_SOURCE_CODE`, 
`a`.`TRX_REMARKS` AS `TRX_REMARKS`,
`a`.`TRX_INS_START` AS `TRX_INS_START`,
`a`.`TRX_INS_END` AS `TRX_INS_END`,
`a`.`SA_NAME` AS `SA_NAME`,
`a`.`TR12_CREATE_ON` AS `TR12_CREATE_ON`
FROM
    (
    SELECT
        `tr6`.`TRX_VOUCHER_ID` AS `TR6_VOUCHER_ID`,
        `tr6`.`ID_KEY` AS `ID_KEY`,
        `tr12`.`TRX_DATE` AS `TRX_DATE`,
        `tr6`.`TRX_TRX_ID` AS `TRX_TRX_ID`,
        `tr12`.`TRX_OLD_VOUCHER_ID` AS `TRX_VOUCHER_ID`,
        (
        SELECT
            `ma5`.`CLI_NAME`
        FROM
            `MA0005` `ma5`
        WHERE
            `ma5`.`CLI_CODE` = `tr6`.`TRX_CLIENT`
    ) AS `CLI_NAME`,
    `tr6`.`TRX_INSURED_NAME` AS `TRX_INSURED_NAME`,
    `tr6`.`TRX_CURR_ID` AS `TRX_CURR_ID`,
    `tr6`.`TRX_TSI_AMOUNT` AS `TRX_TSI_AMOUNT`,
    `tr6`.`TRX_CLIENT` AS `TRX_CLIENT`,
    `tr12`.`TRX_COVER_CODE` AS `TRX_COVER_CODE`,
    `tr6`.`CREATE_ON` AS `CREATE_ON`,
    `tr12`.`TRX_SOURCE` AS TRX_SOURCE_CODE,
    'Production' AS TRX_SOURCE,
	`tr6a`.`TRX_REMARKS` AS `TRX_REMARKS`,
	`tr6a`.`TRX_INS_START` AS `TRX_INS_START`,
	`tr6a`.`TRX_INS_END` AS `TRX_INS_END`,
	(
	SELECT
        `ma12`.`SA_NAME`
    FROM
        `MA0012` `ma12`
    WHERE
        `ma12`.`SA_CODE` = `tr6`.`TRX_OFFICER`
	) AS `SA_NAME`,
 	`tr12`.`CREATE_ON` AS `TR12_CREATE_ON`
FROM
    (
        (
            (
                `TR0006` `tr6`
            JOIN `TR0012` `tr12`
            )
        JOIN `TR0006A` `tr6a`
        )
    JOIN `TR0006C` `tr6c`
    )
WHERE
    `tr6`.`TRX_DATA_STATUS` = '11' AND `tr6`.`TRX_CLASS` = 'FAC' AND `tr12`.`TRX_OLD_TYPE` = `tr6`.`TRX_TRX_ID` AND `tr12`.`TRX_OLD_VOUCHER_ID` = `tr6`.`TRX_VOUCHER_ID` AND `tr12`.`TRX_DATE` =(
    SELECT
        MAX(`tr122`.`TRX_DATE`)
    FROM
        `TR0012` `tr122`
    WHERE
        `tr122`.`TRX_OLD_VOUCHER_ID` = `tr12`.`TRX_OLD_VOUCHER_ID` AND `tr122`.`TRX_SOURCE` = `tr12`.`TRX_SOURCE`
) AND `tr6a`.`TRX_VOUCHER_ID` = `tr6`.`TRX_VOUCHER_ID` AND `tr6c`.`TRX_VOUCHER_ID` = `tr6`.`TRX_VOUCHER_ID` AND `tr12`.`TRX_SOURCE` = '0'
GROUP BY
    `tr6`.`TRX_VOUCHER_ID`
UNION ALL
SELECT
    `tr6`.`TRX_VOUCHER_ID` AS `TR6_VOUCHER_ID`,
    `tr6`.`ID_KEY` AS `ID_KEY`,
    `tr12`.`TRX_DATE` AS `TRX_DATE`,
    `tr6`.`TRX_TRX_ID` AS `TRX_TRX_ID`,
    `tr12`.`TRX_OLD_VOUCHER_ID` AS `TRX_VOUCHER_ID`,
    (
    SELECT
        `ma5`.`CLI_NAME`
    FROM
        `MA0005` `ma5`
    WHERE
        `ma5`.`CLI_CODE` = `tr6`.`TRX_CLIENT`
	) AS `CLI_NAME`,
	`tr6`.`TRX_INSURED_NAME` AS `TRX_INSURED_NAME`,
	`tr6`.`TRX_CURR_ID` AS `TRX_CURR_ID`,
	`tr6`.`TRX_TSI_AMOUNT` AS `TRX_TSI_AMOUNT`,
	`tr6`.`TRX_CLIENT` AS `TRX_CLIENT`,
	`tr12`.`TRX_COVER_CODE` AS `TRX_COVER_CODE`,
	`tr12`.`CREATE_ON` AS `CREATE_ON`,
	`tr12`.`TRX_SOURCE` AS TRX_SOURCE_CODE,
	'Endorsement' AS TRX_SOURCE,
    `tr6a`.`TRX_REMARKS` AS `TRX_REMARKS`,
    `tr6a`.`TRX_INS_START` AS `TRX_INS_START`,
    `tr6a`.`TRX_INS_END` AS `TRX_INS_END`,
    (
    SELECT
        `ma12`.`SA_NAME`
    FROM
        `MA0012` `ma12`
    WHERE
        `ma12`.`SA_CODE` = `tr6`.`TRX_OFFICER`
	) AS `SA_NAME`,
 	`tr12`.`CREATE_ON` AS `TR12_CREATE_ON`
FROM
    (
        (
            (
                `TR0006` `tr6`
            JOIN `TR0012` `tr12`
            )
        JOIN `TR0006A` `tr6a`
        )
    JOIN `TR0006C` `tr6c`
    )
WHERE
    `tr6`.`TRX_DATA_STATUS` = '11' AND `tr6`.`TRX_CLASS` = 'FAC' AND `tr12`.`TRX_OLD_TYPE` = `tr6`.`TRX_TRX_ID` AND `tr12`.`TRX_VOUCHER_ID` = LPAD(`tr6`.`TRX_VOUCHER_ID` + 1, 13, '0') AND `tr6a`.`TRX_VOUCHER_ID` = `tr6`.`TRX_VOUCHER_ID` AND `tr6c`.`TRX_VOUCHER_ID` = `tr6`.`TRX_VOUCHER_ID` AND `tr12`.`TRX_SOURCE` = '2'
GROUP BY
    `tr6`.`TRX_VOUCHER_ID`
) `a` ;