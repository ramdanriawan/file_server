package com.biru;

public interface GuiConstantsRamdan {

    interface REST {
        String MARKETING_REPORT = "/marketing-report";

        // Khusus outstanding direct
        String OUTSTANDING_REPORT_GUI_URL = "/outstanding-direct";
        String OUTSTANDING_REPORT_SERVER_URL = "/outstanding-direct/inquiry";
        String OUTSTANDING_REPORT_API_URL = "/app-re-broker/marketing-report/outstanding-direct/inquiry";
        String OUTSTANDING_REPORT_DROPDOWN_TYPE_OF_COVER = OUTSTANDING_REPORT_GUI_URL + "/get-dropdownTypeOfCover";
        String FIN_OUTSTANDING_CLIENT = OUTSTANDING_REPORT_GUI_URL + "/client";
        String FIN_OUTSTANDING_INQ = "/outstanding-direct/inquiry";

        String FIN_OUTSTANDING_EXPORT_EXCEL = OUTSTANDING_REPORT_GUI_URL + "/export-excel";
        String FIN_OUTSTANDING_VIEW_PDF = OUTSTANDING_REPORT_GUI_URL + "/view-pdf";
        String OUTSTANDING_REPORT_API_URL_EXCEL = "/app-re-broker/marketing-report/outstanding-direct/export-excel";
        String OUTSTANDING_REPORT_API_URL_PRINT = "/app-re-broker/marketing-report/outstanding-direct/print";

        // khusus report production di vir
        String MR_REPORT_PRODUCTION = "/report-production";
        String MR_REPORT_PRODUCTION_DROPDOWN_TYPE_OF_COVER = MR_REPORT_PRODUCTION + "/get-dropdownTypeOfCover";
        String MR_REPORT_PRODUCTION_CLIENT = MR_REPORT_PRODUCTION + "/client";
        String MR_REPORT_PRODUCTION_INQUIRY = MR_REPORT_PRODUCTION + "/inquiry";
        String MR_REPORT_PRODUCTION_TRANSACTION_DETAIL = MR_REPORT_PRODUCTION + "/transaction-detail";
        String MR_REPORT_PRODUCTION_EXPORT = MR_REPORT_PRODUCTION + "/export";
        String MR_REPORT_PRODUCTION_PRINT_JOURNAL = MR_REPORT_PRODUCTION + "/print-journal";
        String MR_REPORT_PRODUCTION_EXPORT_JOURNAL = MR_REPORT_PRODUCTION + "/export-excel";
        String MR_REPORT_PRODUCTION_PRINT = MR_REPORT_PRODUCTION + "/print";
        String MR_REPORT_PRODUCTION_TR6_EXPORT_TO_EXCEL = MR_REPORT_PRODUCTION + "/tr6-export-to-excel";

        String MR_REPORT_PRODUCTION_DI_VIR = "/report-production-di-vir";
        String MR_REPORT_PRODUCTION_DI_VIR_DROPDOWN_TYPE_OF_COVER = MR_REPORT_PRODUCTION_DI_VIR + "/get-dropdownTypeOfCover";
        String MR_REPORT_PRODUCTION_DI_VIR_CLIENT = MR_REPORT_PRODUCTION_DI_VIR + "/client";
        String MR_REPORT_PRODUCTION_DI_VIR_INQUIRY = MR_REPORT_PRODUCTION_DI_VIR + "/inquiry";
        String MR_REPORT_PRODUCTION_DI_VIR_EXPORT = MR_REPORT_PRODUCTION_DI_VIR + "/export";
        String MR_REPORT_PRODUCTION_DI_VIR_TRANSACTION_DETAIL = MR_REPORT_PRODUCTION_DI_VIR + "/transaction-detail-di-vir";
        String MR_REPORT_PRODUCTION_DI_VIR_PRINT_JOURNAL = MR_REPORT_PRODUCTION_DI_VIR + "/print-journal";
        String MR_REPORT_PRODUCTION_DI_VIR_EXPORT_JOURNAL = MR_REPORT_PRODUCTION_DI_VIR + "/export-excel";
        String MR_REPORT_PRODUCTION_DI_VIR_PRINT = MR_REPORT_PRODUCTION_DI_VIR + "/print";
        String MR_REPORT_PRODUCTION_DI_VIR_TR6_EXPORT_TO_EXCEL = MR_REPORT_PRODUCTION_DI_VIR + "/tr6-export-to-excel";
        String MR_REPORT_PRODUCTION_DI_VIR_EXPORT_EXCEL = "/app-re-broker/marketing-report/report-production/export-excel";
        String MR_REPORT_PRODUCTION_DI_VIR_TRANSACTION_DETAIL_API = "/app-re-broker/marketing-report/report-production-di-vir/transaction-detail-di-vir";
        String GET_VOUCHER_ID = "report-production-di-vir/get-voucher-id";

    }

    interface HTML {
        interface MARKETING_REPORT {
            String OUTSTANDING_DIRECT = "marketingReport/outstanding-direct";
            String M_REPORT_PRODUCTION_DI_VIR = "marketingReport/report-production-di-vir";
        }
    }

    interface URI {
        //General
        final String G_APPLICATION_DATE = "/app-re-broker/general/get-appDate";
        final String G_APPLICATION_DATE_NEXT = "/app-re-broker/general/get-appDateNext";
        final String G_CURR_COA = "/app-re-broker/general/get-currCoa";
        final String G_DROPDOWN_BANK = "/app-re-broker/general/get-dropdownBank";
        final String G_DROPDOWN_CURRENCY = "/app-re-broker/general/get-dropdownCurrency";
        final String G_DROPDOWN_DC_NOTE = "/app-re-broker/general/get-dropdownDCNote";
        final String G_DROPDOWN_OFFICE = "/app-re-broker/general/get-dropdownOffice";
        final String G_DROPDOWN_OFFICER = "/app-re-broker/general/get-dropdownOfficer";
        final String G_DROPDOWN_OFFICER_NOT_2 = "/app-re-broker/general/get-dropdownOfficerTypeNot2";
        final String G_DROPDOWN_PARAM = "/app-re-broker/general/get-dropdownParam";
        final String G_DROPDOWN_PAY_MTHD = "/app-re-broker/general/get-dropdownPaymentMethod";
        final String G_DROPDOWN_PRODUCT = "/app-re-broker/general/get-dropdownProduct";
        final String G_DROPDOWN_PROJECT = "/app-re-broker/general/get-dropdownProject";
        final String G_DROPDOWN_STATUS = "/app-re-broker/general/get-dropdownStatus";
        final String G_DROPDOWN_STATUS_CLAIMS = "/app-re-broker/general/get-dropdownStatusClaims";
        final String G_FIND_EXCHANGE = "/app-re-broker/general/findExchange";
        final String G_FIND_EXCHANGE_NON_EOM = "/app-re-broker/general/findExchangeNonEom";
        final String G_FIND_TAX_RATE = "/app-re-broker/general/findTaxRate";
        final String G_DROPDOWN_VALUE = "/app-re-broker/general/get-dropdownValue";
        final String G_DROPDOWN_FORMAT = "/app-re-broker/general/get-dropdownFormat";
        final String G_DROPDOWN_CLIENT_TYPE = "/app-re-broker/general/get-dropdownClientType";
        final String G_MESSAGE_SAVE = "/app-re-broker/general/get-messageSave";
        final String G_VOUCHER_ID = "/app-re-broker/general/get-voucher-id";
        final String G_YEAR = "/app-re-broker/general/get-year";
        final String G_LAST_PRO_MONTH_YEAR = "/app-re-broker/general/get-lastProMonthAndProYear";
        final String G_LOOKUP_CLIENT = "/app-re-broker/general/lookup-client";
        final String G_DROPDOWN_TYPE_OF_COVER = "/app-re-broker/general/get-dropdownTypeOfCover";
        final String G_DROPDOWN_CLASIFICATION = "/app-re-broker/general/get-dropdownClasification";
        final String G_DROPDOWN_NAME = "/app-re-broker/general/get-dropdownName";
        final String G_DROPDOWN_TC_GROUP = "/app-re-broker/general/dropdown-tc-group";
        final String G_DROPDOWN_TC_DETAILS = "/app-re-broker/general/dropdown-tc-details";
        final String G_DROPDOWN_DOCUMENT_TYPE = "/app-re-broker/general/dropdown-document-type";
        final String G_DROPDOWN_CLIENT = "/app-re-broker/general/get-dropdownClient";
        final String G_LOGO_COMPANY = "/app-re-broker/general/get-logoCompany";
        final String G_DROPDOWN_PARTICIPANT_STATUS = "/app-re-broker/general/dropdown-participant-status";
        final String G_DROPDOWN_PARTICIPANT_COUNTRY = "/app-re-broker/general/dropdown-participant-country";
        final String G_DROPDOWN_PARTICIPANT_INDUSTRY = "/app-re-broker/general/dropdown-participant-industry";
        final String G_DROPDOWN_TB_YEAR = "/app-re-broker/general/dropdown-tb-year";
        final String G_GET_PARAMETER = "/app-re-broker/general/get-parameter";
        final String G_DROPDOWN_CURR_TAX = "/app-re-broker/general/dropdown-curr-tax";
        final String G_TR0006_INQUIRY = "/app-re-broker/general/tr0006-inquiry";
        final String G_TR0006_INQUIRY_VIR = "/app-re-broker/general/tr0006-inquiry-vir";
        final String G_CLAIMS_INQUIRY = "/app-re-broker/marketing/claims/inquiry";
        final String G_CLAIMS_INQUIRY2 = "/app-re-broker/marketing/claims/inquiry2";
        final String G_CLAIMS_INQUIRY3 = "/app-re-broker/marketing/claims/inquiry3";
        final String G_IS_ALREADY_CLOSING = "/app-re-broker/general/is-already-closing";

        //Beginning Balance
        final String SD_BEGIN_BAL_GET_YEAR = "/app-re-broker/static-data/beginBal/get-year";
        final String SD_BEGIN_BAL_INQ_V1 = "/app-re-broker/static-data/beginBal/inquiry/v1.0";
        final String SD_BEGIN_BAL_INQ_V2 = "/app-re-broker/static-data/beginBal/inquiry/v2.0";
        final String SD_BEGIN_BAL_SAVE = "/app-re-broker/static-data/beginBal/save";
        final String SD_BEGIN_BAL_SUM_DEBIT0 = "/app-re-broker/static-data/beginBal/summaryDebit0";
        final String SD_BEGIN_BAL_SUM_CREDIT0 = "/app-re-broker/static-data/beginBal/summaryCredit0";
        final String SD_BEGIN_BAL_LOOKUP_COA = "/app-re-broker/static-data/beginBal/lookup-coa";

        //Holiday
        final String SD_HOLIDAY_INQ = "/app-re-broker/static-data/holiday/inquiry";
        final String SD_HOLIDAY_IS_EXSIST = "/app-re-broker/static-data/holiday/isExsist";
        final String SD_HOLIDAY_SAVE = "/app-re-broker/static-data/holiday/save";

        //Chart of Account
        final String SD_COA_INQ = "/app-re-broker/static-data/coa/inquiry";
        final String SD_COA_ROLE_UP = "/app-re-broker/static-data/coa/roleUp";
        final String SD_COA_DROPDOWN_CLASS = "/app-re-broker/static-data/coa/dropdownClass";
        final String SD_COA_DROPDOWN_CURR = "/app-re-broker/static-data/coa/dropdownCurr";
        final String SD_COA_SAVE = "/app-re-broker/static-data/coa/save";

        //Balance Sheet
        final String ACCTR_BS_CREATE_EXCEL = "/app-re-broker/accounting-report/balanceSheet/create-excel";
        final String ACCTR_BS_CREATE_HTML = "/app-re-broker/accounting-report/balanceSheet/create-html";
        final String ACCTR_BS_CREATE_PDF = "/app-re-broker/accounting-report/balanceSheet/create-pdf";

        //Balance Sheet Report
        final String ACCTR_BS_REPORT_PREVIEW = "/app-re-broker/accounting-report/bs-report/preview";
        final String ACCTR_BS_REPORT_PRINT = "/app-re-broker/accounting-report/bs-report/print";
        final String ACCTR_BS_REPORT_EXPORT = "/app-re-broker/accounting-report/bs-report/export";

        //Balance Sheet Report
        final String ACCTR_PL_ACCOUNT_PREVIEW = "/app-re-broker/accounting-report/pl-account/preview";
        final String ACCTR_PL_ACCOUNT_PRINT = "/app-re-broker/accounting-report/pl-account/print";
        final String ACCTR_PL_ACCOUNT_EXPORT = "/app-re-broker/accounting-report/pl-account/export";

        //Cash Flow
        final String ACCTR_CF_CREATE_EXCEL = "/app-re-broker/accounting-report/cashFlow/create-excel";
        final String ACCTR_CF_CREATE_HTML = "/app-re-broker/accounting-report/cashFlow/create-html";
        final String ACCTR_CF_CREATE_PDF = "/app-re-broker/accounting-report/cashFlow/create-pdf";

        //General Ledger
        final String ACCTR_GL_CREATE_EXCEL = "/app-re-broker/accounting-report/generalLedger/create-excel";
        final String ACCTR_GL_CREATE_HTML = "/app-re-broker/accounting-report/generalLedger/create-html";
        final String ACCTR_GL_CREATE_PDF = "/app-re-broker/accounting-report/generalLedger/create-pdf";

        //Profit And Lost
        final String ACCTR_PL_CREATE_EXCEL = "/app-re-broker/accounting-report/profitLost/create-excel";
        final String ACCTR_PL_CREATE_HTML = "/app-re-broker/accounting-report/profitLost/create-html";
        final String ACCTR_PL_CREATE_PDF = "/app-re-broker/accounting-report/profitLost/create-pdf";

        //Trial Balance
        final String ACCTR_TB_CREATE_EXCEL = "/app-re-broker/accounting-report/trialBalance/create-excel";
        final String ACCTR_TB_CREATE_HTML = "/app-re-broker/accounting-report/trialBalance/create-html";
        final String ACCTR_TB_CREATE_PDF = "/app-re-broker/accounting-report/trialBalance/create-pdf";

        //Inquiry Journal
        final String ACCTR_INQUIRY_JOURNAL_INQ = "/app-re-broker/accounting-report/inquiry-journal/inquiry";
        final String ACCTR_INQUIRY_JOURNAL_DROPDOWN_PROJECT = "/app-re-broker/accounting-report/inquiry-journal/dropdown-project";
        final String ACCTR_INQUIRY_JOURNAL_DROPDOWN_OFFICE = "/app-re-broker/accounting-report/inquiry-journal/dropdown-office";

        //Entry Journal
        final String ACCT_ENTRY_JOURNAL_INQ = "/app-re-broker/accounting/entry-journal/inquiry";
        final String ACCT_ENTRY_JOURNAL_DROPDOWN_PROJECT = "/app-re-broker/accounting/entry-journal/dropdown-project";
        final String ACCT_ENTRY_JOURNAL_DROPDOWN_OFFICE = "/app-re-broker/accounting/entry-journal/dropdown-office";
        final String ACCT_ENTRY_JOURNAL_TRANSACTION_MIN_DATE = "/app-re-broker/accounting/entry-journal/transaction-min-date";
        final String ACCT_ENTRY_JOURNAL_CLIENT = "/app-re-broker/accounting/entry-journal/client";
        final String ACCT_ENTRY_JOURNAL_COA = "/app-re-broker/accounting/entry-journal/coa";
        final String ACCT_ENTRY_JOURNAL_VOUCHER_CODE = "/app-re-broker/accounting/entry-journal/voucher-code";
        final String ACCT_ENTRY_JOURNAL_EXCHANGE = "/app-re-broker/accounting/entry-journal/exchange";
        final String ACCT_ENTRY_JOURNAL_SAVE = "/app-re-broker/accounting/entry-journal/save";
        final String ACCT_ENTRY_JOURNAL_EDIT = "/app-re-broker/accounting/entry-journal/edit";
        final String ACCT_ENTRY_JOURNAL_REMOVE = "/app-re-broker/accounting/entry-journal/remove";
        final String ACCT_ENTRY_JOURNAL_PRINT_JOURNAL = "/app-re-broker/accounting/entry-journal/print-journal";
        final String ACCT_ENTRY_JOURNAL_EXPORT_EXCEL = "/app-re-broker/accounting/entry-journal/export-excel";
        final String ACCT_ENTRY_JOURNAL_PRINT = "/app-re-broker/accounting/entry-journal/print";

        //Eom - Eoy
        final String ACCT_EOM_EOY_PROCESS = "/app-re-broker/accounting/eomeoy/process";
        final String ACCT_EOM_EOY_PROGRESS = "/app-re-broker/accounting/eomeoy/progress";
        final String ACCT_EOM_EOY_RESET_PROGRESS = "/app-re-broker/accounting/eomeoy/resetProgress";
        final String ACCT_EOM_EOY_CHECK_PROCESS = "/app-re-broker/accounting/eomeoy/checkProcess";

        //Bank Book
        final String FIN_BANK_BOOK_INQ = "/app-re-broker/finance/bank-book/inquiry";
        final String FIN_BANK_BOOK_INQ_MODIFY = "/app-re-broker/finance/bank-book/inquiryModify";
        final String FIN_BANK_BOOK_SAVE = "/app-re-broker/finance/bank-book/save";
        final String FIN_BANK_BOOK_DELETE = "/app-re-broker/finance/bank-book/delete";

        //Debit Credit Notes
        final String M_DC_NOTE_INQ = "/app-re-broker/marketing/dc-note/inquiry";
        final String M_DC_NOTE_SAVE = "/app-re-broker/marketing/dc-note/save";
        final String M_DC_NOTE_DELETE = "/app-re-broker/marketing/dc-note/delete";
        final String M_DC_NOTE_CREATE_EXCEL = "/app-re-broker/marketing/dc-note/create-excel";
        final String M_DC_NOTE_CREATE_HTML = "/app-re-broker/marketing/dc-note/create-html";
        final String M_DC_NOTE_CREATE_PDF = "/app-re-broker/marketing/dc-note/create-pdf";
        final String M_DC_NOTE_CREATE_DOC = "/app-re-broker/marketing/dc-note/create-doc";
        final String M_DC_NOTE_CREATE_HTML_U_TREATY = "/app-re-broker/marketing/dc-note/create-html-upload-treaty";
        final String M_DC_NOTE_CREATE_PDF_U_TREATY = "/app-re-broker/marketing/dc-note/create-pdf-upload-treaty";
        final String M_DC_NOTE_CREATE_DOC_U_TREATY = "/app-re-broker/marketing/dc-note/create-doc-upload-treaty";

        //Endorsement
        final String M_ENDORSEMENT_REMOVE = "/app-re-broker/marketing/endorsement/remove";
        final String M_ENDORSEMENT_SAVE = "/app-re-broker/marketing/endorsement/save";
        final String M_ENDORSEMENT_INQUIRY_SEND_TABLE = "/app-re-broker/marketing/endorsement/inquiry-send-table";
        final String M_ENDORSEMENT_CREATE_CLOSING_REPORT = "/app-re-broker/marketing/endorsement/create-closing-report";

        final String M_NEW_ENDORSEMENT_GET_REQUEST_ID = "/app-re-broker/marketing/new-endorsement/get-request-id";
        final String M_NEW_ENDORSEMENT_CLOSING = "/app-re-broker/marketing/new-endorsement/closing";

        //Renewal
        final String M_RENEWAL_INQUIRY = "/app-re-broker/marketing/renewal/inquiry";
        final String M_RENEWAL_REMOVE = "/app-re-broker/marketing/renewal/remove";
        final String M_RENEWAL_EDIT = "/app-re-broker/marketing/renewal/edit";
        final String M_RENEWAL_SEND = "/app-re-broker/marketing/renewal/send";
        final String M_RENEWAL_EXPORT = "/app-re-broker/marketing/renewal/export";
        final String M_RENEWAL_PRINT = "/app-re-broker/marketing/renewal/print";


        //Settlement
        final String FIN_SETTLEMENT_INQ = "/app-re-broker/finance/settlement/inquiry";
        final String FIN_SETTLEMENT_SAVE = "/app-re-broker/finance/settlement/save";

        //Remittance
        final String FIN_REMITTANCE_INQ = "/app-re-broker/finance-report/remittance/inquiry";
        final String FIN_REMITTANCE_PRINT_JOURNAL = "/app-re-broker/finance-report/remittance/print-journal";
        final String FIN_REMITTANCE_EXPORT_EXCEL = "/app-re-broker/finance-report/remittance/export-excel";
        final String FIN_REMITTANCE_PRINT = "/app-re-broker/finance-report/remittance/print";

        //Remittance
        final String FIN_VAT_REPORT_INQ = "/app-re-broker/finance-report/vat-report/inquiry";
        final String FIN_VAT_REPORT_PRINT = "/app-re-broker/finance-report/vat-report/print";
        final String FIN_VAT_REPORT_EXPORT = "/app-re-broker/finance-report/vat-report/export";

        //Outstanding
        final String FIN_OUTSTANDING_INQ = "/app-re-broker/finance-report/outstanding/inquiry";
        final String FIN_OUTSTANDING_SAVE = "/app-re-broker/finance-report/outstanding/save";
        final String FIN_OUTSTANDING_EXPORT_EXCEL = "/app-re-broker/finance-report/outstanding/export-excel";
        final String FIN_OUTSTANDING_PRINT = "/app-re-broker/finance-report/outstanding/print";

        //Cancel Settlement
        final String FIN_CNCL_SETTLEMENT_INQ = "/app-re-broker/finance/cancel-settlement/inquiry";
        final String FIN_CNCL_SETTLEMENT_SAVE = "/app-re-broker/finance/cancel-settlement/save";

        //Production Facultative
        final String MR_PRODUCTION_MA_0011 = "/app-re-broker/marketing-report/production/ma-0011";
        final String MR_PRODUCTION_MA_0012 = "/app-re-broker/marketing-report/production/ma-0012";
        final String MR_PRODUCTION_TAX_RATE = "/app-re-broker/marketing-report/production/tax-rate";
        final String MR_PRODUCTION_TERM_AND_CONDITION = "/app-re-broker/marketing-report/production/term-and-condition";
        final String MR_PRODUCTION_SAVE = "/app-re-broker/marketing-report/production/save";
        final String MR_PRODUCTION_DELETE = "/app-re-broker/marketing-report/production/delete";
        final String MR_PRODUCTION_INQ = "/app-re-broker/marketing-report/production/inquiry";
        final String MR_PRODUCTION_INQ_MODIFY = "/app-re-broker/marketing-report/production/inquiryModify";
        final String MR_PRODUCTION_INQ_SEND = "/app-re-broker/marketing-report/production/inquirySend";
        final String MR_PRODUCTION_RPT_DOC = "/app-re-broker/marketing-report/production/report/create-doc";
        final String MR_PRODUCTION_RPT_HMTL = "/app-re-broker/marketing-report/production/report/create-html";
        final String MR_PRODUCTION_RPT_PDF = "/app-re-broker/marketing-report/production/report/create-pdf";
        final String MR_PRODUCTION_SEND_EMAIL = "/app-re-broker/marketing-report/production/send-email";
        final String MR_PRODUCTION_SEND_EMAIL_CLOSING = "/app-re-broker/marketing-report/production/send-email-closing";
        final String MR_PRODUCTION_CLOSING = "/app-re-broker/marketing-report/production/closing";
        final String MR_PRODUCTION_RPT_CLOSING_HTML = "/app-re-broker/marketing-report/production/create-closing-html";
        final String MR_IS_VALID_TRX_DATE = "/app-re-broker/marketing-report/production/isValidTrxDate";
        final String MR_VALIDATION_USER_LEVEL = "/app-re-broker/marketing-report/production/validationUserLevel";

        //Production Treaty
        final String M_TPRODUCTION_DNCN_CREATE_PDF = "/app-re-broker/marketing/treaty-production/dcNote-pdf";
        final String M_TPRODUCTION_DNCN_CREATE_DOC = "/app-re-broker/marketing/treaty-production/dcNote-doc";
        final String M_TPRODUCTION_CLOSING = "/app-re-broker/marketing/treaty-production/closing";
        final String M_TPRODUCTION_DELETE = "/app-re-broker/marketing/treaty-production/delete";
        final String M_TPRODUCTION_INQ = "/app-re-broker/marketing/treaty-production/inquiry";
        final String M_TPRODUCTION_INQ_MODIFY = "/app-re-broker/marketing/treaty-production/inquiryModify";
        final String M_TPRODUCTION_INQ_SEND = "/app-re-broker/marketing/treaty-production/inquirySend";
        final String M_TPRODUCTION_RPT_DOC = "/app-re-broker/marketing/treaty-production/create-doc";
        final String M_TPRODUCTION_RPT_PDF = "/app-re-broker/marketing/treaty-production/create-pdf";
        final String M_TPRODUCTION_SAVE = "/app-re-broker/marketing/treaty-production/save";

        //Treaty Adjustment
        final String M_TADJUSTMENT_INQ = "/app-re-broker/marketing/treaty-adjustment/inquiry";
        final String M_TADJUSTMENT_INQ_MODIFY = "/app-re-broker/marketing/treaty-adjustment/inquiryModify";
        final String M_TADJUSTMENT_PROCESS = "/app-re-broker/marketing/treaty-adjustment/process";
        final String M_TADJUSTMENT_CANCEL = "/app-re-broker/marketing/treaty-adjustment/cancel";
        final String M_TADJUSTMENT_SAVE = "/app-re-broker/marketing/treaty-adjustment/save";

        //Production Direct
        final String M_DPRODUCTION_GET_COMM_OUT_NAME = "/app-re-broker/marketing/direct-production/get-commOutName";
        final String M_DPRODUCTION_GET_WHT_CLIENT = "/app-re-broker/marketing/direct-production/get-whtClient";
        final String M_DPRODUCTION_SAVE = "/app-re-broker/marketing/direct-production/save";
        final String M_DPRODUCTION_INQ_MODIFY = "/app-re-broker/marketing/direct-production/inquiryModify";
        final String M_DPRODUCTION_INQ_SEND = "/app-re-broker/marketing/direct-production/inquirySend";
        final String M_DPRODUCTION_RPT_HMTL = "/app-re-broker/marketing/direct-production/report/create-html";
        final String M_DPRODUCTION_RPT_DOC = "/app-re-broker/marketing/direct-production/report/create-doc";
        final String M_DPRODUCTION_RPT_PDF = "/app-re-broker/marketing/direct-production/report/create-pdf";
        final String M_DPRODUCTION_SEND_EMAIL = "/app-re-broker/marketing/direct-production/send-email";
        final String M_DPRODUCTION_CLOSING = "/app-re-broker/marketing/direct-production/closing";
        final String M_DPRODUCTION_RPT_CLOSING_HTML = "/app-re-broker/marketing/direct-production/create-closing-html";

        //Claims
        final String M_CLAIMS_INQ_DATA = "/app-re-broker/marketing/claims/inquiry-data";
        final String M_CLAIMS_INQ_DETAIL = "/app-re-broker/marketing/claims/inquiry-detail";
        final String M_CLAIMS_INQ_CREATE = "/app-re-broker/marketing/claims/inquiry-create";
        final String M_CLAIMS_INQ_CREATE_DETAIL = "/app-re-broker/marketing/claims/inquiry-create-detail";
        final String M_CLAIMS_GET_UNDERWRITING = "/app-re-broker/marketing/claims/get-underwriting";
        final String M_CLAIMS_SAVE = "/app-re-broker/marketing/claims/save";
        final String M_CLAIMS_DELETE = "/app-re-broker/marketing/claims/delete";
        final String M_CLAIMS_SETTLEMENT = "/app-re-broker/marketing/claims/settlement";
        final String M_CLAIMS_DROPDOWN_YEAR = "/app-re-broker/marketing/claims/dropdown-year";
        final String M_CLAIMS_EXPORT_CLAIMSLIST_EXCEL = "/app-re-broker/marketing/claims/export-claimslist-excel";
        final String M_CLAIMS_GET_TR6J = "/app-re-broker/marketing/claims/get-tr6j";
        final String M_CLAIMS_PRINT_DLA_PLA = "/app-re-broker/marketing/claims/print-dla-pla";
        final String M_CLAIMS_PRINT_DOC_DLA_PLA = "/app-re-broker/marketing/claims/print-doc-dla-pla";
        final String M_CLAIMS_CREATE_DC_NOTES = "/app-re-broker/marketing/claims/create-dc-notes";
        final String M_CLAIMS_GET_LIST_INSURANCE = "/app-re-broker/marketing/claims/get-list-insurance";
        final String M_CLAIMS_GET_LIST_VALUE = "/app-re-broker/marketing/claims/get-list-tr15a";
        final String M_CLAIMS_GET_FINANCE = "/app-re-broker/marketing/claims/get-finance";
        final String M_CLAIMS_PROCESS_FINANCE = "/app-re-broker/marketing/claims/process-finance";
        final String M_CLAIMS_GET_LIST_INTERNALMEMO = "/app-re-broker/marketing/claims/get-list-internalmemo";
        final String M_CLAIMS_CREATE_INTERNALMEMO = "/app-re-broker/marketing/claims/create-internalmemo";
        final String M_CLAIMS_CREATE_INTERNALMEMO_EXCEL = "/app-re-broker/marketing/claims/create-internalmemo-excel";

        //Direct Modify
        final String M_DMODIFY_INQUIRY = "/app-re-broker/marketing/direct-modify/inquiry";
        final String M_DMODIFY_INQUIRY_DETAIL = "/app-re-broker/marketing/direct-modify/inquiry-detail";
        final String M_DMODIFY_SAVE = "/app-re-broker/marketing/direct-modify/save";

        final String M_CANCEL_UPLOAD_SEARCH = "/app-re-broker/marketing/cancel-upload/search";
        final String M_CANCEL_UPLOAD_DELETE = "/app-re-broker/marketing/cancel-upload/delete";

        final String MR_REPORT_PRODUCTION_TRANSACTION_DETAIL = "/app-re-broker/marketing-report/report-production/transaction-detail";
        final String MR_REPORT_PRODUCTION_EXPORT = "/app-re-broker/marketing-report/report-production/export";
        final String MR_REPORT_PRODUCTION_PRINT_JOURNAL = "/app-re-broker/marketing-report/report-production/print-journal";
        final String MR_REPORT_PRODUCTION_EXPORT_EXCEL = "/app-re-broker/marketing-report/report-production/export-excel";
        final String MR_REPORT_PRODUCTION_PRINT = "/app-re-broker/marketing-report/report-production/print";
        final String MR_REPORT_PRODUCTION_TR6_EXPORT_TO_EXCEL = "/app-re-broker/marketing-report/report-production/tr6-export-to-excel";

        final String MR_REPORT_PRODUCTION_TRANSACTION_DETAIL_DI = "/app-re-broker/marketing-report/report-production/transaction-detail-di";
        final String MR_REPORT_PRODUCTION_DI_EXPORT = "/app-re-broker/marketing-report/report-production-di/export";
        final String MR_REPORT_PRODUCTION_DI_PRINT_JOURNAL = "/app-re-broker/marketing-report/report-production-di/print-journal";
        final String MR_REPORT_PRODUCTION_DI_EXPORT_EXCEL = "/app-re-broker/marketing-report/report-production/export-excel";
        final String MR_REPORT_PRODUCTION_DI_PRINT = "/app-re-broker/marketing-report/report-production-di/print";
        final String MR_REPORT_PRODUCTION_DI_TR6_EXPORT_TO_EXCEL = "/app-re-broker/marketing-report/report-production-di/tr6-export-to-excel";
        //Exchange Rate
        final String SD_ER_DROPDOWN_CURR = "/app-re-broker/static-data/coa/dropdownCurr";
        final String SD_ER_SAVE = "/app-re-broker/static-data/exchangeRate/save";
        final String SD_ER_INQ = "/app-re-broker/static-data/exchangeRate/inquiry";
        final String SD_ER_INQ_KMK = "/app-re-broker/static-data/exchangeRate/inquirykmk";
        final String SD_ER_SAVE_KMK = "/app-re-broker/static-data/exchangeRate/savekmk";
        final String SD_ER_REMOVE_KMK = "/app-re-broker/static-data/exchangeRate/removekmk";

        //Business Rules
        final String SD_BR_INQ = "/app-re-broker/static-data/businessRules/inquiry";
        final String SD_BR_INQ_CHILD = "/app-re-broker/static-data/businessRules/inquiryChild";
        final String SD_BR_SAVE = "/app-re-broker/static-data/businessRules/save";

        //Parameter
        final String SD_PARAMETER_INQ = "/app-re-broker/static-data/parameter/inquiry";
        final String SD_PARAMETER_INQ_CHILD = "/app-re-broker/static-data/parameter/inquiryChild";
        final String SD_PARAMETER_SAVE = "/app-re-broker/static-data/parameter/save";

        //User Management
        final String SD_USERMANAGEMENT_INQ = "/app-re-broker/static-data/usermanagement/inquiry";
        final String SD_USERMANAGEMENT_INQ_CHILD = "/app-re-broker/static-data/usermanagement/inquiryChild";
        final String SD_USERMANAGEMENT_SAVE = "/app-re-broker/static-data/usermanagement/save";
        final String SD_USERMANAGEMENT_DROPDOWN = "/app-re-broker/static-data/usermanagement/dropdown";
        final String SD_USERMANAGEMENT_USERLEVELDROPDOWN = "/app-re-broker/static-data/usermanagement/userleveldropdown";
        final String SD_USERMANAGEMENT_MPLDROPDOWN = "/app-re-broker/static-data/usermanagement/mpldropdown";
        final String SD_USERMANAGEMENT_STDATADROPDOWN = "/app-re-broker/static-data/usermanagement/stdatadropdown";
        final String SD_USERMANAGEMENT_SADROPDOWN = "/app-re-broker/static-data/usermanagement/sadropdown";
        //User Management
        final String SD_GROUPMANAGEMENT_INQ = "/app-re-broker/static-data/groupmanagement/inquiry";
        final String SD_GROUPMANAGEMENT_INQ_CHILD = "/app-re-broker/static-data/groupmanagement/inquiryChild";
        final String SD_GROUPMANAGEMENT_SAVE = "/app-re-broker/static-data/groupmanagement/save";
        final String SD_GROUPMANAGEMENT_DROPDOWN = "/app-re-broker/static-data/groupmanagement/dropdown";
        final String SD_GROUPMANAGEMENT_DELETE = "/app-re-broker/static-data/groupmanagement/delete";

        //Agent Officer

        final String SD_AGENT_OFFICER_INQ = "/app-re-broker/static-data/agentOfficer/inquiry";
        final String SD_AGENT_OFFICER_SAVE = "/app-re-broker/static-data/agentOfficer/save";

        //TC Standard
        final String SD_TC_STANDARD_INQ = "/app-re-broker/static-data/tc-standard/inquiry";
        final String SD_TC_STANDARD_DESCRIPTION = "/app-re-broker/static-data/tc-standard/description";
        final String SD_TC_STANDARD_SAVE = "/app-re-broker/static-data/tc-standard/save";
        final String SD_TC_STANDARD_DELETE = "/app-re-broker/static-data/tc-standard/delete";

        //TC Data
        final String SD_TC_DATA_INQ = "/app-re-broker/static-data/tc-data/inquiry";
        final String SD_TC_DATA_SAVE = "/app-re-broker/static-data/tc-data/save";
        final String SD_TC_DATA_DELETE = "/app-re-broker/static-data/tc-data/delete";

        //Adjustment
        final String FIN_ADJUSTMENT_INQ = "/app-re-broker/finance/adjustment/inquiry";
        final String FIN_ADJUSTMENT_SAVE = "/app-re-broker/finance/adjustment/save";

        //Product
        final String SD_PRODUCT_INQ = "/app-re-broker/static-data/product/inquiry";
        final String SD_PRODUCT_SAVE = "/app-re-broker/static-data/product/save";
        final String SD_PRODUCT_SAVE_INSURANCE = "/app-re-broker/static-data/product/save-insurance";
        final String SD_PRODUCT_DROPDOWN = "/app-re-broker/static-data/product/dropdown";
        final String SD_PRODUCT_INQ_INS = "/app-re-broker/static-data/product/inquiry-insurance";
        final String SD_PRODUCT_REMOVE_INS = "/app-re-broker/static-data/product/remove-insurance";
        final String SD_PRODUCT_DROPDOWN_PA_CHILD = "/app-re-broker/static-data/product/dropdown-pa-child";

        //Product
        final String SD_PARTICIPANT_GET_PARTICIPANT = "/app-re-broker/static-data/participant/getParticipant";
        final String SD_PARTICIPANT_INQ = "/app-re-broker/static-data/participant/inquiry";
        final String SD_PARTICIPANT_SAVE = "/app-re-broker/static-data/participant/save";

        //Extension
        final String M_EXTENSION_INQ = "/app-re-broker/marketing/extension/inquiry";
        final String M_EXTENSION_SAVE = "/app-re-broker/marketing/extension/save";
        final String M_EXTENSION_ENTRY = "/app-re-broker/marketing/extension/entry";
        final String M_EXTENSION_UPLOAD = "/app-re-broker/marketing/extension/upload";
        final String M_ISFILENAME_EXIST = "/app-re-broker/marketing/extension/isFileNameExist";

        //Budget and Target
        final String SD_BUDGET_AND_TARGET_INQ = "/app-re-broker/static-data/budget-and-target/inquiry";
        final String SD_BUDGET_AND_TARGET_SAVE = "/app-re-broker/static-data/budget-and-target/save";
        final String SD_BUDGET_AND_TARGET_DELETE = "/app-re-broker/static-data/budget-and-target/delete";
        final String SD_BUDGET_AND_TARGET_EXPORT = "/app-re-broker/static-data/budget-and-target/export";
        final String SD_BUDGET_AND_TARGET_COA = "/app-re-broker/static-data/budget-and-target/coa";

        //Upload Treaty
        final String M_UPLOAD_TREATY_INQ = "/app-re-broker/marketing/uploadTreaty/inquiry";
        final String M_UPLOAD_TREATY_UPLOAD = "/app-re-broker/marketing/uploadTreaty/upload";

        final String L_LOGIN_PAGE_ITEM = "/app-re-broker/login/login-page-item";
        final String L_LOGIN_IS_USER_LOCKED = "/app-re-broker/login/is-user-locked";
        final String L_LOGIN_RESET_FAILED_LOGIN = "/app-re-broker/login/reset-failed-login";
        final String L_LOGIN_ADD_ERROR_COUNTER = "/app-re-broker/login/add-error-counter";
        final String L_LOGIN_RESET_PASSWORD = "/app-re-broker/login/reset-password";
        final String L_LOGIN_INQUIRY_USERS = "/app-re-broker/login/inquiry-users";

        final String MENU = "/app-re-broker/menu";


    }
}