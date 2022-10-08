CREATE OR REPLACE VIEW `VIEW_RPT_PL` AS SELECT
    `r`.`ROW_RPT` AS `ROW_RPT`,
    `r`.`COA_DESCRIPTION` AS `COA_DESCRIPTION`,
    `r`.`COA_GROUP` AS `COA_GROUP`,
    CASE WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_1` < 0 THEN CONCAT(
        '(',
    REPLACE
        (
            CAST(
                FORMAT(`r`.`BLOCK_1`, 2) AS CHAR CHARSET utf8mb4
            ),
            '-',
            ''
        ),
        ')'
    ) WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_1` >= 0 THEN CAST(
        FORMAT(`r`.`BLOCK_1`, 2) AS CHAR CHARSET utf8mb4
    ) ELSE ''
END AS `BLOCK_1`,
CASE WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_2` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_2`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_2` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_2`, 2) AS CHAR CHARSET utf8mb4
) ELSE ''
END AS `BLOCK_2`,
CASE WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_3` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_3`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_3` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_3`, 2) AS CHAR CHARSET utf8mb4
) ELSE ''
END AS `BLOCK_3`,
CASE WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_4` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_4`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_4` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_4`, 2) AS CHAR CHARSET utf8mb4
) ELSE ''
END AS `BLOCK_4`,
CASE WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_5` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_5`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_5` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_5`, 2) AS CHAR CHARSET utf8mb4
) ELSE ''
END AS `BLOCK_5`,
CASE WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_6` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_6`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) WHEN `r`.`COA_GROUP` <> '' AND `r`.`BLOCK_6` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_6`, 2) AS CHAR CHARSET utf8mb4
) ELSE ''
END AS `BLOCK_6`
FROM
    `RPT_PL` `r`
ORDER BY
    `r`.`ROW_RPT`;