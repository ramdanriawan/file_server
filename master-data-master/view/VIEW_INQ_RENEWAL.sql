CREATE OR REPLACE VIEW `VIEW_INQ_RENEWAL` AS SELECT
    `tr3`.`ID_KEY` AS `ID_KEY`,
    `tr3`.`TRX_INS_START` AS `TRX_INS_START`,
    `tr3`.`TRX_INS_END` AS `TRX_INS_END`,
    `tr3`.`TRX_VOUCHER_ID` AS `TRX_VOUCHER_ID`,
    `tr3`.`TRX_TYPE` AS `TRX_TYPE`,
    `tr6`.`TRX_CLASS` AS `TRX_CLASS`,
    CASE WHEN `tr6`.`TRX_CLASS` = 'FAC' THEN 'Facultative' WHEN `tr6`.`TRX_CLASS` = 'TRE' THEN 'Treaty Prop' WHEN `tr6`.`TRX_CLASS` = 'XOL' THEN 'Treaty NP'
END AS `TRX_CLASS_DESC`,
`tr6`.`TRX_CLIENT` AS `TRX_CLIENT`,
(
    SELECT
        `ma5`.`CLI_NAME`
    FROM
        `MA0005` `ma5`
    WHERE
        `tr6`.`TRX_CLIENT` = `ma5`.`CLI_CODE`
) AS `CLI_NAME`,
(
    SELECT
        `ma5`.`CLI_ADDRESS`
    FROM
        `MA0005` `ma5`
    WHERE
        `tr6`.`TRX_CLIENT` = `ma5`.`CLI_CODE`
) AS `CLI_ADDRESS`,
`tr3`.`TRX_COVER_CODE` AS `TRX_COVER_CODE`,
(
    SELECT
        GROUP_CONCAT(
            DISTINCT `tr6b`.`TRX_INS_ID` SEPARATOR ','
        )
    FROM
        `TR0006B` `tr6b`
    WHERE
        `tr6b`.`TRX_VOUCHER_ID` = `tr6`.`TRX_VOUCHER_ID` AND `tr6b`.`TRX_INS_TYPE` = '1'
) AS `TRX_INS_ID`,
(
    SELECT
        GROUP_CONCAT(`ma5`.`CLI_NAME` SEPARATOR ',')
    FROM
        `MA0005` `ma5`
    WHERE
        `ma5`.`CLI_CODE` IN(
        SELECT
            `tr6b`.`TRX_INS_ID`
        FROM
            `TR0006B` `tr6b`
        WHERE
            `tr6b`.`TRX_VOUCHER_ID` = `tr6`.`TRX_VOUCHER_ID` AND `tr6b`.`TRX_INS_TYPE` = '1'
        GROUP BY
            `tr6b`.`TRX_INS_ID`
    )
) AS `INS_NAME`,
(
    SELECT
        GROUP_CONCAT(`ma5`.`CLI_NAME` SEPARATOR ',')
    FROM
        `MA0005` `ma5`
    WHERE
        `ma5`.`CLI_CODE` IN(
        SELECT
            `tr6b`.`TRX_INS_ID`
        FROM
            `TR0006B` `tr6b`
        WHERE
            `tr6b`.`TRX_VOUCHER_ID` = `tr6`.`TRX_VOUCHER_ID` AND `tr6b`.`TRX_INS_TYPE` = '0'
        GROUP BY
            `tr6b`.`TRX_INS_ID`
    )
) AS `UNDERWRITE`,
`tr3`.`TRX_DESCRIPTION` AS `TRX_DESCRIPTION`,
`tr6a`.`TRX_REMARKS` AS `TRX_INS_REMARKS`,
`tr6c`.`TRX_CURR_ID` AS `TRX_CURR_ID`,
FORMAT(`tr6c`.`TRX_SUM_INSURED`, 2) AS `TRX_SUM_INSURED`,
FORMAT(`tr6c`.`TRX_PREMIUM_SELL`, 2) AS `TRX_PREMIUM_SELL`,
`ma11`.`TC_DESC` AS `TC_DESC`,
`ma11`.`TC_RENEWABLE` AS `TC_RENEWABLE`,
CASE WHEN `ma11`.`TC_RENEWABLE` = '0' THEN 'Renewable' ELSE 'Non Renewable'
END AS `TC_RENEWABLE_STR`,
`tr6`.`TRX_SHARE` AS `TRX_SHARE`,
`tr6`.`TRX_INSURED_NAME` AS `TRX_INSURED_NAME`,
(
    SELECT
        `tr19`.`TRX_VOUCHER_ID`
    FROM
        `TR0019` `tr19`
    WHERE
        `tr19`.`TRX_OLD_VOUCHER_ID` = `tr3`.`TRX_VOUCHER_ID`
) AS `ID_REF`
FROM
    (
        (
            (
                (
                    (`TR0006A` `tr6a`
                JOIN `TR0003` `tr3`)
                JOIN `TR0006C` `tr6c`
                )
            JOIN `MA0011` `ma11`
            )
        JOIN `TR0006` `tr6`
        )
    JOIN `MA0005` `ma5`
    )
WHERE
    `tr6`.`TRX_VOUCHER_ID` = `tr3`.`TRX_OLD_VOUCHER_ID` AND `tr6a`.`TRX_VOUCHER_ID` = `tr6`.`TRX_VOUCHER_ID` AND `tr6c`.`TRX_VOUCHER_ID` = `tr6`.`TRX_VOUCHER_ID` AND `tr3`.`TRX_COVER_CODE` = `ma11`.`TC_CODE` AND `tr3`.`TRX_CLIENT` = `ma5`.`CLI_CODE` AND `ma5`.`CLI_TYPE` = '1'
ORDER BY
    `tr3`.`TRX_INS_END`;