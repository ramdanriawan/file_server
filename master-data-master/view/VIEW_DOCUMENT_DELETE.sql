CREATE OR REPLACE VIEW `VIEW_DOCUMENT_DELETE` AS SELECT
    DATA_2.*,
    (
        CASE WHEN DATA_2.TRX_SET_AMOUNT IS NULL OR DATA_2.TRX_SET_AMOUNT = '0' THEN 'N' ELSE 'Y'
    END
) AS SETTLED
FROM
    (
    SELECT
        DATA_1.*,
        (
        SELECT
            tr12.TRX_DESCRIPTION
        FROM
            TR0012 tr12
        WHERE
            tr12.TRX_VOUCHER_ID = DATA_1.TRX_VOUCHER_ID
    ) AS DESCRIPTION,
    (
    SELECT
        tr12.TRX_CURR_ID
    FROM
        TR0012 tr12
    WHERE
        tr12.TRX_VOUCHER_ID = DATA_1.TRX_VOUCHER_ID
) AS CURR,
(
    SELECT
        tr12.TRX_INVC_AMOUNT
    FROM
        TR0012 tr12
    WHERE
        tr12.TRX_VOUCHER_ID = DATA_1.TRX_VOUCHER_ID
) AS AMOUNT,
(
    SELECT
        tr12.TRX_SET_AMOUNT
    FROM
        TR0012 tr12
    WHERE
        tr12.TRX_VOUCHER_ID = DATA_1.TRX_VOUCHER_ID
) AS TRX_SET_AMOUNT
FROM
    (
    SELECT
        LPAD((MAX(tr6h.TRX_VOUCHER_ID) +1), LENGTH(tr6h.TRX_VOUCHER_ID), '0') AS TRX_VOUCHER_ID,
        tr6h.ID_KEY,
        tr6h.TRX_OLD_TYPE,
        tr6h.TRX_OLD_VOUCHER_ID,
        tr6h.TRX_CLIENT_ID,
        (
        SELECT
            ma5.CLI_NAME
        FROM
            MA0005 ma5
        WHERE
            ma5.CLI_CODE = tr6h.TRX_CLIENT_ID
    ) AS NAME,
    tr6h.TRX_FILE_NAME,
    DATE(tr6h.CREATE_ON) AS CREATE_ON
FROM
    TR0006H tr6h
WHERE
    tr6h.TRX_DATA_STATUS in (11, 12)
GROUP BY
    tr6h.TRX_FILE_NAME
) DATA_1
) DATA_2;