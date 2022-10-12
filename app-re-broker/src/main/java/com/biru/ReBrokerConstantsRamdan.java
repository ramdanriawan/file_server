package com.biru;

public interface ReBrokerConstantsRamdan {
    interface REST {
        String MARKETING_REPORT = "/marketing-report";
        final String GENERAL = "/general";

        String G_VOUCHER_ID = "/get-voucher-id";
        final String TR0006_INQUIRY_VIR = "/tr0006-inquiry-vir";

        interface REPORT_PRODUCTION_DI_VIR {
            String TRANSACTION_DETAIL_DI_VIR = "/report-production-di-vir/transaction-detail";
            String TRANSACTION_DETAIL_DI_DI_VIR = "/report-production-di-vir/transaction-detail-di-vir";
            String EXPORT_DI_VIR = "/report-production-di-vir/export";
            String PRINT_JOURNAL_DI_VIR = "/report-production-di-vir/print-journal";
            String EXPORT_EXCEL_DI_VIR = "/report-production-di-vir/export-excel";
            String PRINT_DI_VIR = "/report-production-di-vir/print";
            String TR6_EXPORT_TO_EXCEL_DI_VIR = "/report-production-di-vir/tr6-export-to-excel";
        }
    }

    interface OUTSTANDING {
        final String INQUIRY = "/outstanding-direct/inquiry";
        final String PRINT_JOURNAL = "/outstanding-direct/print-journal";
        final String EXPORT_EXCEL = "/outstanding-direct/export-excel";
        final String PRINT = "/outstanding-direct/print";
    }


}