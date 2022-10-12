CREATE OR REPLACE VIEW `VIEW_RPT_BS` AS SELECT
    `r`.`ROW_RPT` AS `ROW_RPT`,
    `r`.`ROW_HEAD` AS `ROW_HEAD`,
    `r`.`COA_CODE` AS `COA_CODE`,
    `r`.`COA_DESCRIPTION` AS `COA_DESCRIPTION`,
    `r`.`COA_GROUP` AS `COA_GROUP`,
    `r`.`POSITION` AS `POSITION`,
    CASE WHEN `r`.`POSITION` = 'L' AND `r`.`BLOCK_1_L` >= 0 THEN CAST(
        FORMAT(`r`.`BLOCK_1_L`, 2) AS CHAR CHARSET utf8mb4
    ) WHEN `r`.`POSITION` = 'L' AND `r`.`BLOCK_1_L` < 0 THEN CONCAT(
        '(',
    REPLACE
        (
            CAST(
                FORMAT(`r`.`BLOCK_1_L`, 2) AS CHAR CHARSET utf8mb4
            ),
            '-',
            ''
        ),
        ')'
    ) ELSE ''
END AS `BLOCK_1_L`,
CASE WHEN `r`.`POSITION` = 'R' AND `r`.`BLOCK_1_R` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_1_R`, 2) AS CHAR CHARSET utf8mb4
) WHEN `r`.`POSITION` = 'R' AND `r`.`BLOCK_1_R` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_1_R`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) ELSE ''
END AS `BLOCK_1_R`,
CASE WHEN `r`.`POSITION` = 'L' AND `r`.`BLOCK_2_L` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_2_L`, 2) AS CHAR CHARSET utf8mb4
) WHEN `r`.`POSITION` = 'L' AND `r`.`BLOCK_2_L` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_2_L`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) ELSE ''
END AS `BLOCK_2_L`,
CASE WHEN `r`.`POSITION` = 'R' AND `r`.`BLOCK_2_R` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_2_R`, 2) AS CHAR CHARSET utf8mb4
) WHEN `r`.`POSITION` = 'R' AND `r`.`BLOCK_2_R` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_2_R`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) ELSE ''
END AS `BLOCK_2_R`,
CASE WHEN `r`.`POSITION` = 'L' AND `r`.`BLOCK_3_L` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_3_L`, 2) AS CHAR CHARSET utf8mb4
) WHEN `r`.`POSITION` = 'L' AND `r`.`BLOCK_3_L` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_3_L`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) ELSE ''
END AS `BLOCK_3_L`,
CASE WHEN `r`.`POSITION` = 'R' AND `r`.`BLOCK_3_R` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_3_R`, 2) AS CHAR CHARSET utf8mb4
) WHEN `r`.`POSITION` = 'R' AND `r`.`BLOCK_3_R` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_3_R`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) ELSE ''
END AS `BLOCK_3_R`,
CASE WHEN `r`.`POSITION` = 'L' AND `r`.`BLOCK_4_L` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_4_L`, 2) AS CHAR CHARSET utf8mb4
) WHEN `r`.`POSITION` = 'L' AND `r`.`BLOCK_4_L` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_4_L`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) ELSE ''
END AS `BLOCK_4_L`,
CASE WHEN `r`.`POSITION` = 'R' AND `r`.`BLOCK_4_R` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_4_R`, 2) AS CHAR CHARSET utf8mb4
) WHEN `r`.`POSITION` = 'R' AND `r`.`BLOCK_4_R` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_4_R`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) ELSE ''
END AS `BLOCK_4_R`,
CASE WHEN `r`.`POSITION` = 'L' AND `r`.`BLOCK_5_L` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_5_L`, 2) AS CHAR CHARSET utf8mb4
) WHEN `r`.`POSITION` = 'L' AND `r`.`BLOCK_5_L` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_5_L`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) ELSE ''
END AS `BLOCK_5_L`,
CASE WHEN `r`.`POSITION` = 'R' AND `r`.`BLOCK_5_R` >= 0 THEN CAST(
    FORMAT(`r`.`BLOCK_5_R`, 2) AS CHAR CHARSET utf8mb4
) WHEN `r`.`POSITION` = 'R' AND `r`.`BLOCK_5_R` < 0 THEN CONCAT(
    '(',
REPLACE
    (
        CAST(
            FORMAT(`r`.`BLOCK_5_R`, 2) AS CHAR CHARSET utf8mb4
        ),
        '-',
        ''
    ),
    ')'
) ELSE ''
END AS `BLOCK_5_R`
FROM
    `RPT_BS` `r`
ORDER BY
    `r`.`ROW_RPT`;