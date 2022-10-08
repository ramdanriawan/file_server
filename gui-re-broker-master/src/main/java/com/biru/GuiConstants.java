package com.biru;

public interface GuiConstants {

	interface CHARACTER {
		final String DASH	= "-";
	}
	
	interface KEY {
		final String PARENT_CODE = "parentCode";
		final String CHILD_CODE = "childCode";
	}

	interface HTML {
		final String INDEX	= "index";
		final String LOGIN	= "login";

		/* ========================= STATIC DATA ========================= */

		//Beginning Balance
		final String SD_BEGIN_BAL		= "staticdata/beginBal";

		//Holiday
		final String SD_HOLIDAY			= "staticdata/holiday";

		//Chart of Account
		final String SD_COA				= "staticdata/coa";

		//Exchange Rate
		final String SD_EXCHANGE_RATE 	= "staticdata/exchangeRate";

		//Business Rules
		final String SD_BUSINESS_RULES 	= "staticdata/businessRules";

		//Tc Standard
		final String SD_TC_STANDARD		= "staticdata/tc-standard";

		//Tc Data
		final String SD_TC_DATA			= "staticdata/tc-data";
		
		//Product
		final String SD_PRODUCT			= "staticdata/product";

		//Participant
		final String SD_PARTICIPANT		= "staticdata/participant";
		
		//Agent Officer
		final String SD_AGENT_OFFICER  	= "staticdata/agentOfficer";
		
		//Parameter
		final String SD_PARAMETER 		= "staticdata/parameter";
		
		//Group Management
		final String SD_GROUPMANAGEMENT	= "staticdata/groupManagement";
		
		//User Management
		final String SD_USERMANAGEMENT	= "staticdata/userManagement";
		
		//Budget And Target
				final String SD_BUDGET_AND_TARGET		= "staticdata/budgetAndTarget";
		/* ========================= ACCOUNTING ========================= */

		//Entry Journal
		final String ACCT_ENTY_JOURNAL		= "accounting/entry-journal";

		//Eom - Eoy
		final String ACCT_EOMEOY			= "accounting/eomeoy";

		/* ========================= ACCOUNTING REPORT ========================= */

		//Balance Sheet
		final String ACCTR_BALANCE_SHEET	= "accountingReport/balanceSheet";
		
		//Balance Sheet Report
		final String ACCTR_BS_REPORT		= "accountingReport/bsReport";
		
		//P And L Account
		final String ACCTR_PL_ACCOUNT		= "accountingReport/pAndLAccount";

		//Cash Flow
		final String ACCTR_CASH_FLOW		= "accountingReport/cashFlow";

		//General Ledger
		final String ACCTR_GENERAL_LEDGER	= "accountingReport/generalLedger";

		//Profit And Lost
		final String ACCTR_PROFIT_LOST		= "accountingReport/profitLost";

		//Trial Balance
		final String ACCTR_TRIAL_BALANCE	= "accountingReport/trialBalance";

		//Inquery Journal
		final String ACCTR_INQUIRY_JOURNAL	= "accountingReport/inquiryJournal";

		//Direct Production
		final String M_DPRODUCTION			= "marketing/production/direct";
		
		//Treaty Production
		final String M_TPRODUCTION			= "marketing/production/treaty";
		
		//Treaty Adjustment
		final String M_TADJUSTMENT			= "marketing/treatyAdjustment";
		
		//Production
		final String MR_PRODUCTION			= "marketingReport/production";

		/* ========================= FINANCE ========================= */

		//Bank Book
		final String FNC_BANK_BOOK			= "finance/bankBook";

		//Settlement
		final String FNC_SETTLEMENT			= "finance/settlement";
		
		//Cancel Settlement
		final String FNC_CNCL_SETTLEMENT	= "finance/cancelSettlement";
		
		//Adjustment
		final String FNC_ADJUSTMENT			= "finance/adjustment";

		/* ========================= FINANCE REPORT ========================= */

		//Outstanding
		final String FNCR_OUTSTANDING		= "financeReport/outstanding";

		//Remittance
		final String FNCR_REMITTANCE		= "financeReport/remittance";
		
		//VAT Report
		final String FNCR_VAT_REPORT		= "financeReport/vat-report";
		
		/* ========================= MARKETING ========================= */

		//Debit Credit Notes
		final String M_DC_NOTE				= "marketing/dc-note";
		
		//Direct Modify
		final String M_DMODIFY				= "marketing/direct-modify";
		
		//Endorcement
		final String M_ENDORSEMENT			= "marketing/endorsement";
		
		//New Endorcement
		final String M_NEW_ENDORSEMENT		= "marketing/new-endorsement";
		
		//Renewal
		final String M_RENEWAL				= "marketing/renewal";

		//Extension
		final String M_EXTENSION			= "marketing/extension";
		
		//Direct Modify
		final String M_CLAIMS				= "marketing/claims-internal";
		
		//Claims Internal
		final String M_CLAIM_INTERNAL		= "marketing/claims";
		
		//Cancel Upload
		final String M_CANCEL_UPLOAD		= "marketing/cancel-upload";
		
		//Endorcement
		final String MR_REPORT_PRODUCTION	= "marketingReport/report-production";
		
		//Upload Treaty
		final String M_UPLOAD_TREATY		= "marketing/uploadTreaty";
		final String M_REPORT_PRODUCTION_DI = "marketingReport/report-production-di";
	}

	interface REST {
		final String HOME				= "/home";
		final String LOGIN				= "/login";
		final String MENU				= "/menu";
		final String STATIC_DATA		= "/static-data";
		final String ACCOUNTING			= "/accounting";
		final String ACCOUNTING_REPORT	= "/accounting-report";
		final String FINANCE			= "/finance";
		final String FINANCE_REPORT		= "/finance-report";
		final String GENERAL			= "/general";
		final String MARKETING			= "/marketing";
		final String MARKETING_REPORT	= "/marketing-report";

		//General
		final String APPLICATION_DATE		= "/appDate";
		final String APPLICATION_DATE_NEXT	= "/appDateNext";
		final String CLIENT					= "/client";
		final String COA					= "/chartOfAccount";
		final String CURR_COA				= "/currCoa";
		final String DROPDOWN_BANK			= "/dropdown-bank";
		final String DROPDOWN_CLIENT		= "/dropdown-client";
		final String DROPDOWN_CURRENCY		= "/dropdown-currency";
		final String DROPDOWN_DC_NOTE		= "/dropdown-dc-note";
		final String DROPDOWN_OFFICE		= "/dropdown-office";
		final String DROPDOWN_OFFICER		= "/dropdown-officer";
		final String DROPDOWN_OFFICER_NOT_2	= "/dropdown-officerTypeNot2";
		final String DROPDOWN_PARAM			= "/dropdown-param";
		final String DROPDOWN_PAY_MTHD		= "/dropdown-paymentMethod";
		final String DROPDOWN_PRODUCT		= "/dropdown-product";
		final String DROPDOWN_PROJECT		= "/dropdown-project";
		final String DROPDOWN_STATUS		= "/dropdown-status";
		final String DROPDOWN_VALUE			= "/dropdown-value";
		final String DROPDOWN_FORMAT		= "/dropdown-format";
		final String DROPDOWN_CLIENT_TYPE	= "/dropdown-clientType";
		final String EXCHANGE				= "/exchange";
		final String EXCHANGE_NON_EOM		= "/exchangeNonEom";
		final String EXCHANGE_COA			= "/exchangeCoa";
		final String EXCHANGE_COA_NON_EOM	= "/exchangeCoaNonEom";
		final String GET_PARAMETER			= "/get-parameter";
		final String TAX_RATE				= "/taxRate";
		final String LOGO_COMPANY			= "/logo-company";
		final String IS_ALREADY_CLOSING		= "/is-already-closing";

		/* ========================= STATIC DATA ========================= */

		//Beginning Balance
		final String SD_BEGIN_BAL				= "/beginBal";
		final String SD_BEGIN_BAL_EXPORT		= SD_BEGIN_BAL + "/export";
		final String SD_BEGIN_BAL_INQ			= SD_BEGIN_BAL + "/inquiry";
		final String SD_BEGIN_BAL_SAVE			= SD_BEGIN_BAL + "/save";
		final String SD_BEGIN_BAL_GET_YEAR		= SD_BEGIN_BAL + "/get-year";
		final String SD_BEGIN_BAL_SUM_DEBIT0	= SD_BEGIN_BAL + "/summaryDebit0";
		final String SD_BEGIN_BAL_SUM_CREDIT0	= SD_BEGIN_BAL + "/summaryCredit0";
		final String SD_BEGIN_BAL_LOOKUP_COA	= SD_BEGIN_BAL + "/lookup-coa";

		//Holiday
		final String SD_HOLIDAY				= "/holiday";
		final String SD_HOLIDAY_INQ			= SD_HOLIDAY + "/inquiry";
		final String SD_HOLIDAY_IS_EXSIST	= SD_HOLIDAY + "/isExsist";
		final String SD_HOLIDAY_SAVE		= SD_HOLIDAY + "/save";

		//Chart of Account
		final String SD_COA					= "/coa";
		final String SD_COA_INQ				= SD_COA + "/inquiry";
		final String SD_COA_ROLE_UP			= SD_COA + "/roleUp";
		final String SD_COA_DROPDOWN_CLASS	= SD_COA + "/dropdownClass";
		final String SD_COA_DROPDOWN_CURR	= SD_COA + "/dropdownCurr";
		final String SD_COA_SAVE			= SD_COA + "/save";

		//Exchange Rate
		final String SD_EXCHANGE_RATE				= "/exchangeRate";
		final String SD_EXCHANGE_RATE_DROPDOWN_CURR	= SD_EXCHANGE_RATE + "/dropdownCurr";
		final String SD_EXCHANGE_RATE_SAVE			= SD_EXCHANGE_RATE + "/save";
		final String SD_EXCHANGE_RATE_INQ			= SD_EXCHANGE_RATE + "/inquiry";
		final String SD_EXCHANGE_RATE_INQ_KMK		= SD_EXCHANGE_RATE + "/inquirykmk";
		final String SD_EXCHANGE_RATE_SAVE_KMK		= SD_EXCHANGE_RATE + "/savekmk";
		final String SD_EXCHANGE_RATE_REMOVE_KMK	= SD_EXCHANGE_RATE + "/removekmk";
		final String SD_EXCHANGE_DROPDOWN_CURR		= SD_EXCHANGE_RATE + "/dropdown-curr";

		//Business Rules
		final String SD_BUSINESS_RULES				= "/businessRules";
		final String SD_BUSINESS_RULES_INQ			= SD_BUSINESS_RULES + "/inquiry";
		final String SD_BUSINESS_RULES_INQ_CHILD		= SD_BUSINESS_RULES + "/inquiryChild";
		final String SD_SD_BUSINESS_RULES_SAVE			= SD_BUSINESS_RULES + "/save";
		
		//Parameter
		final String SD_PARAMETER					= "/parameter";
		final String SD_PARAMETER_INQ				= SD_PARAMETER+"/inquiry";
		final String SD_PARAMETER_INQ_CHILD			= SD_PARAMETER+"/inquiryChild";
		final String SD_PARAMETER_SAVE				= SD_PARAMETER+"/save";
		
		//Group Management 
		final String SD_GROUPMANAGEMENT					= "/groupmanagement";
		final String SD_GROUPMANAGEMENT_INQ				= SD_GROUPMANAGEMENT+"/inquiry";
		final String SD_GROUPMANAGEMENT_INQ_CHILD			= SD_GROUPMANAGEMENT+"/inquiryChild";
		final String SD_GROUPMANAGEMENT_SAVE				= SD_GROUPMANAGEMENT+"/save";
		final String SD_GROUPMANAGEMENT_DROPDOWN			= SD_GROUPMANAGEMENT+"/dropdown";
		final String SD_GROUPMANAGEMENT_DELETE			= SD_GROUPMANAGEMENT+"/delete";
		
		//User Management 
		final String SD_USERMANAGEMENT					= "/usermanagement";
		final String SD_USERMANAGEMENT_INQ				= SD_USERMANAGEMENT+"/inquiry";
		final String SD_USERMANAGEMENT_INQ_CHILD			= SD_USERMANAGEMENT+"/inquiryChild";
		final String SD_USERMANAGEMENT_SAVE				= SD_USERMANAGEMENT+"/save";
		final String SD_USERMANAGEMENT_DROPDOWN			= SD_USERMANAGEMENT+"/dropdown";
		final String SD_USERMANAGEMENT_USERLEVELDROPDOWN			= SD_USERMANAGEMENT+"/userleveldropdown";
		final String SD_USERMANAGEMENT_MPLDROPDOWN			= SD_USERMANAGEMENT+"/mpldropdown";
		final String SD_USERMANAGEMENT_STDATADROPDOWN			= SD_USERMANAGEMENT+"/stdatadropdown";
		final String SD_USERMANAGEMENT_SADROPDOWN			= SD_USERMANAGEMENT+"/sadropdown";
		final String SD_USERMANAGEMENT_DATE		= SD_USERMANAGEMENT+"/getdate";
		
		//Agent Officer
		final String SD_AGENT_OFFICER				= "/agentOfficer";
		final String SD_AGENT_OFFICER_INQ			= SD_AGENT_OFFICER + "/inquiry";
		final String SD_AGENT_OFFICER_SAVE			= SD_AGENT_OFFICER + "/save";

		//TC Standard
		final String SD_TC_STANDARD							= "/tc-standard";
		final String SD_TC_STANDARD_DROPDOWN_TYPE_OF_COVER	= SD_TC_STANDARD + "/get-dropdownTypeOfCover";
		final String SD_TC_STANDARD_INQUIRY					= SD_TC_STANDARD + "/inquiry";
		final String SD_TC_STANDARD_DROPDOWN_TC_GROUP		= SD_TC_STANDARD + "/dropdown-tc-group";
		final String SD_TC_STANDARD_DROPDOWN_TC_DETAILS		= SD_TC_STANDARD + "/dropdown-tc-details";
		final String SD_TC_STANDARD_SAVE					= SD_TC_STANDARD + "/save";
		final String SD_TC_STANDARD_DELETE					= SD_TC_STANDARD + "/delete";
		final String SD_TC_STANDARD_DESCRIPTION				= SD_TC_STANDARD + "/description";

		//TC Data
		final String SD_TC_DATA							= "/tc-data";
		final String SD_TC_DATA_DROPDOWN_TC_GROUP		= SD_TC_DATA + "/dropdown-tc-group";
		final String SD_TC_DATA_INQUIRY					= SD_TC_DATA + "/inquiry";
		final String SD_TC_DATA_SAVE					= SD_TC_DATA + "/save";
		final String SD_TC_DATA_DELETE					= SD_TC_DATA + "/delete";
		
		//PRODUCT
		final String SD_PRODUCT							= "/product";
		final String SD_PRODUCT_INQ						= SD_PRODUCT + "/inquiry";
		final String SD_PRODUCT_SAVE					= SD_PRODUCT + "/save";
		final String SD_PRODUCT_SAVE_INSURANCE			= SD_PRODUCT + "/save-insurance";
		final String SD_PRODUCT_DROPDOWN				= SD_PRODUCT + "/dropdown";
		final String SD_PRODUCT_INQ_INS					= SD_PRODUCT + "/inquiry-insurance";
		final String SD_PRODUCT_REMOVE_INS				= SD_PRODUCT + "/remove-insurance";
		final String SD_PRODUCT_DROPDOWN_PA_CHILD		= SD_PRODUCT + "/dropdown-pa-child";
		
		//PARTICIPANT
		final String SD_PARTICIPANT							= "/participant";
		final String SD_PARTICIPANT_INQ						= SD_PARTICIPANT + "/inquiry";
		final String SD_PARTICIPANT_SAVE					= SD_PARTICIPANT + "/save";
		final String SD_PARTICIPANT_DROPDOWN_PARTICIPANT_STATUS		= SD_PARTICIPANT + "/dropdown-participant-status";
		final String SD_PARTICIPANT_DROPDOWN_PARTICIPANT_COUNTRY	= SD_PARTICIPANT + "/dropdown-participant-country";
		final String SD_PARTICIPANT_DROPDOWN_PARTICIPANT_INDUSTRY	= SD_PARTICIPANT + "/dropdown-participant-industry";
		
		//BUDGET AND TARGET
		final String SD_BUDGET_AND_TARGET				= "/budgetAndTarget";
		final String SD_BUDGET_AND_TARGET_COA	= SD_BUDGET_AND_TARGET + "/coa";
		final String SD_BUDGET_AND_TARGET_INQ			= SD_BUDGET_AND_TARGET + "/inquiry";
		final String SD_BUDGET_AND_TARGET_DROPDOWN_TB_YEAR	= SD_BUDGET_AND_TARGET + "/dropdown-tb-year";
		final String SD_BUDGET_AND_TARGET_DROPDOWN_OFFICER	= SD_BUDGET_AND_TARGET + "/dropdown-officer";
		final String SD_BUDGET_AND_TARGET_SAVE	= SD_BUDGET_AND_TARGET + "/save";
		final String SD_BUDGET_AND_TARGET_EXPORT		= SD_BUDGET_AND_TARGET + "/export";
		
		/* ========================= ACCOUNTING ========================= */

		//Entry Journal
		final String ACCT_ENTRY_JOURNAL						= "/entry-journal";
		final String ACCT_ENTRY_JOURNAL_INQ					= ACCT_ENTRY_JOURNAL + "/inquiry";
		final String ACCT_ENTRY_JOURNAL_DROPDOWN_PROJECT	= ACCT_ENTRY_JOURNAL + "/dropdown-project";
		final String ACCT_ENTRY_JOURNAL_DROPDOWN_OFFICE		= ACCT_ENTRY_JOURNAL + "/dropdown-office";
		final String ACCT_ENTRY_JOURNAL_CLIENT				= ACCT_ENTRY_JOURNAL + "/client";
		final String ACCT_ENTRY_JOURNAL_COA					= ACCT_ENTRY_JOURNAL + "/coa";
		final String ACCT_ENTRY_JOURNAL_VOUCHER_CODE		= ACCT_ENTRY_JOURNAL + "/voucher-code";
		final String ACCT_ENTRY_JOURNAL_EXCHANGE			= ACCT_ENTRY_JOURNAL + "/exchange";
		final String ACCT_ENTRY_JOURNAL_SAVE				= ACCT_ENTRY_JOURNAL + "/save";
		final String ACCT_ENTRY_JOURNAL_EDIT				= ACCT_ENTRY_JOURNAL + "/edit";
		final String ACCT_ENTRY_JOURNAL_REMOVE				= ACCT_ENTRY_JOURNAL + "/remove";
		final String ACCT_ENTRY_JOURNAL_PRINT_JOURNAL		= ACCT_ENTRY_JOURNAL + "/print-journal";
		final String ACCT_ENTRY_JOURNAL_EXPORT_EXCEL		= ACCT_ENTRY_JOURNAL + "/export-excel";
		final String ACCT_ENTRY_JOURNAL_PRINT				= ACCT_ENTRY_JOURNAL + "/print";

		//Eom - Eoy
		final String ACCT_EOM_EOY						= "/eomeoy";
		final String ACCT_EOM_EOY_PROCESS				= ACCT_EOM_EOY + "/process";
		final String ACCT_EOM_EOY_PROGRESS				= ACCT_EOM_EOY + "/progress";
		final String ACCT_EOM_EOY_RESET_PROGRESS		= ACCT_EOM_EOY + "/resetProgress";
		final String ACCT_EOM_EOY_CHECK_PROCESS			= ACCT_EOM_EOY + "/checkProcess";

		/* ========================= ACCOUNTING REPORT ========================= */

		//Balance Sheet
		final String ACCTR_BALANCE_SHEET	= "/balanceSheet";
		final String ACCTR_BS_EXPORT_EXCEL	= ACCTR_BALANCE_SHEET + "/export-excel";
		final String ACCTR_BS_SEARCH		= ACCTR_BALANCE_SHEET + "/search";
		final String ACCTR_BS_VIEW_PDF		= ACCTR_BALANCE_SHEET + "/view-pdf";
		
		//Balance Sheet Report
		final String ACCTR_BS_REPORT			= "/bs-report";
		final String ACCTR_BS_REPORT_PREVIEW	= ACCTR_BS_REPORT + "/preview";
		final String ACCTR_BS_REPORT_PRINT		= ACCTR_BS_REPORT + "/print";
		final String ACCTR_BS_REPORT_EXPORT		= ACCTR_BS_REPORT + "/export";
		
		//P And L Account
		final String ACCTR_PL_ACCOUNT			= "/pl-account";
		final String ACCTR_PL_ACCOUNT_PREVIEW	= ACCTR_PL_ACCOUNT + "/preview";
		final String ACCTR_PL_ACCOUNT_PRINT		= ACCTR_PL_ACCOUNT + "/print";
		final String ACCTR_PL_ACCOUNT_EXPORT	= ACCTR_PL_ACCOUNT + "/export";

		//Cash Flow
		final String ACCTR_CASH_FLOW		= "/cashFlow";
		final String ACCTR_CF_EXPORT_EXCEL	= ACCTR_CASH_FLOW + "/export-excel";
		final String ACCTR_CF_SEARCH		= ACCTR_CASH_FLOW + "/search";
		final String ACCTR_CF_VIEW_PDF		= ACCTR_CASH_FLOW + "/view-pdf";

		//General Ledger
		final String ACCTR_GENERAL_LEDGER	= "/generalLedger";
		final String ACCTR_GL_EXPORT_EXCEL	= ACCTR_GENERAL_LEDGER + "/export-excel";
		final String ACCTR_GL_SEARCH		= ACCTR_GENERAL_LEDGER + "/search";
		final String ACCTR_GL_VIEW_PDF		= ACCTR_GENERAL_LEDGER + "/view-pdf";

		//Profit And Lost
		final String ACCTR_PROFIT_LOST		= "/profitLost";
		final String ACCTR_PL_EXPORT_EXCEL	= ACCTR_PROFIT_LOST + "/export-excel";
		final String ACCTR_PL_SEARCH		= ACCTR_PROFIT_LOST + "/search";
		final String ACCTR_PL_VIEW_PDF		= ACCTR_PROFIT_LOST + "/view-pdf";

		//Trial Balance
		final String ACCTR_TRIAL_BALANCE	= "/trialBalance";
		final String ACCTR_TB_EXPORT_EXCEL	= ACCTR_TRIAL_BALANCE + "/export-excel";
		final String ACCTR_TB_SEARCH		= ACCTR_TRIAL_BALANCE + "/search";
		final String ACCTR_TB_VIEW_PDF		= ACCTR_TRIAL_BALANCE + "/view-pdf";

		//Inquiry Journal
		final String ACCTR_INQUIRY_JOURNAL						= "/inquiry-journal";
		final String ACCTR_INQUIRY_JOURNAL_INQ					= ACCTR_INQUIRY_JOURNAL + "/inquiry";
		final String ACCTR_INQUIRY_JOURNAL_DROPDOWN_PROJECT		= ACCTR_INQUIRY_JOURNAL + "/dropdown-project";
		final String ACCTR_INQUIRY_JOURNAL_DROPDOWN_OFFICE		= ACCTR_INQUIRY_JOURNAL + "/dropdown-office";

		/* ========================= FINANCE ========================= */

		//Bank Book
		final String FIN_BANK_BOOK				= "/bank-book";
		final String FIN_BANK_BOOK_INQ			= FIN_BANK_BOOK + "/inquiry";
		final String FIN_BANK_BOOK_INQ_MODIFY	= FIN_BANK_BOOK + "/inquiryModify";
		final String FIN_BANK_BOOK_SAVE			= FIN_BANK_BOOK + "/save";
		final String FIN_BANK_BOOK_DELETE		= FIN_BANK_BOOK + "/delete";
		final String FIN_BANK_BOOK_EXPORT_EXCEL	= FIN_BANK_BOOK + "/export-excel";
		final String FIN_BANK_BOOK_SEARCH		= FIN_BANK_BOOK + "/search";
		final String FIN_BANK_BOOK_VIEW_PDF		= FIN_BANK_BOOK + "/view-pdf";

		//Settlement
		final String FIN_SETTLEMENT				= "/settlement";
		final String FIN_SETTLEMENT_INQ			= FIN_SETTLEMENT + "/inquiry";
		final String FIN_SETTLEMENT_SAVE		= FIN_SETTLEMENT + "/save";
		final String FIN_SETTLEMENT_EXPORT_EXCEL= FIN_SETTLEMENT + "/export-excel";
		final String FIN_SETTLEMENT_SEARCH		= FIN_SETTLEMENT + "/search";
		final String FIN_SETTLEMENT_VIEW_PDF	= FIN_SETTLEMENT + "/view-pdf";
		
		//Cancel Settlement
		final String FIN_CNCL_SETTLEMENT		= "/cancel-settlement";
		final String FIN_CNCL_SETTLEMENT_INQ	= FIN_CNCL_SETTLEMENT + "/inquiry";
		final String FIN_CNCL_SETTLEMENT_SAVE   = FIN_CNCL_SETTLEMENT + "/save";
		final String FIN_CNCL_SETTLEMENT_EXPORT_EXCEL	= FIN_CNCL_SETTLEMENT + "/export-excel";
		final String FIN_CNCL_SETTLEMENT_SEARCH			= FIN_CNCL_SETTLEMENT + "/search";
		final String FIN_CNCL_SETTLEMENT_VIEW_PDF		= FIN_CNCL_SETTLEMENT + "/view-pdf";
		
		//Adjustment
		final String FIN_ADJUST					= "/adjustment";
		final String FIN_ADJUST_INQ				= FIN_ADJUST + "/inquiry";
		final String FIN_ADJUST_SAVE			= FIN_ADJUST + "/save";
		
		/* ========================= FINANCE REPORT ========================= */

		//Outstanding 
		final String FIN_OUTSTANDING				= "/outstanding";
		final String FIN_OUTSTANDING_INQ			= FIN_OUTSTANDING + "/inquiry";
		final String FIN_OUTSTANDING_SAVE			= FIN_OUTSTANDING + "/save";
		final String FIN_OUTSTANDING_EXPORT_EXCEL	= FIN_OUTSTANDING + "/export-excel";
		final String FIN_OUTSTANDING_SEARCH			= FIN_OUTSTANDING + "/search";
		final String FIN_OUTSTANDING_VIEW_PDF		= FIN_OUTSTANDING + "/view-pdf";
		final String FIN_OUTSTANDING_DROPDOWN_TYPE_OF_COVER	= FIN_OUTSTANDING + "/get-dropdownTypeOfCover";
		final String FIN_OUTSTANDING_CLIENT					= FIN_OUTSTANDING + "/client";

		//Remittance 
		final String FIN_REMITTANCE				= "/remittance";
		final String FIN_REMITTANCE_INQ			= FIN_REMITTANCE + "/inquiry";
		final String FIN_REMITTANCE_EXPORT_EXCEL= FIN_REMITTANCE + "/export-excel";
		final String FIN_REMITTANCE_SEARCH		= FIN_REMITTANCE + "/search";
		final String FIN_REMITTANCE_VIEW_PDF	= FIN_REMITTANCE + "/view-pdf";
		
		//Vat Report 
		final String FIN_VAT_REPORT			= "/vat-report";
		final String FIN_VAT_REPORT_INQ		= FIN_VAT_REPORT + "/inquiry";
		final String FIN_VAT_REPORT_EXPORT	= FIN_VAT_REPORT + "/export";
		final String FIN_VAT_REPORT_PRINT	= FIN_VAT_REPORT + "/print";
		
		/* ========================= MARKETING ========================= */

		//Claim Internal
		final String M_CLM_INTERNAL			="/claims";
		
		//Debit Credit Notes
		final String M_DC_NOTE				= "/dc-note";
		final String M_DC_NOTE_INQ			= M_DC_NOTE + "/inquiry";
		final String M_DC_NOTE_SAVE			= M_DC_NOTE + "/save";
		final String M_DC_NOTE_DELETE		= M_DC_NOTE + "/delete";
		final String M_DC_NOTE_EXPORT_EXCEL	= M_DC_NOTE + "/export-excel";
		final String M_DC_NOTE_SEARCH		= M_DC_NOTE + "/search";
		final String M_DC_NOTE_VIEW_PDF		= M_DC_NOTE + "/view-pdf";
		
		//Direct Production
		final String M_DPRODUCTION			= "/direct-production";
		final String M_DPRODUCTION_CLOSING_SEARCH			= M_DPRODUCTION + "/closing-search";
		final String M_DPRODUCTION_DELETE					= M_DPRODUCTION + "/delete";
		final String M_DPRODUCTION_DROPDOWN_DOCUMENT_TYPE	= M_DPRODUCTION + "/dropdown-document-type";
		final String M_DPRODUCTION_DROPDOWN_TC_GROUP		= M_DPRODUCTION + "/dropdown-tc-group";
		final String M_DPRODUCTION_DROPDOWN_TC_DETAILS		= M_DPRODUCTION + "/dropdown-tc-details";
		final String M_DPRODUCTION_DROPDOWN_NAME			= M_DPRODUCTION + "/get-dropdownName";
		final String M_DPRODUCTION_DROPDOWN_TYPE_OF_COVER	= M_DPRODUCTION + "/get-dropdownTypeOfCover";
		final String M_DPRODUCTION_GET_WHT_CLIENT			= M_DPRODUCTION + "/get-whtClient";
		final String M_DPRODUCTION_COMM_OUT_NAME			= M_DPRODUCTION + "/dropdown-commOutName";
		final String M_DPRODUCTION_GET_CLIENT				= M_DPRODUCTION + "/get-client";
		final String M_DPRODUCTION_INQ						= M_DPRODUCTION + "/inquiry";
		final String M_DPRODUCTION_INQ_MODIFY				= M_DPRODUCTION + "/inquiryModify";
		final String M_DPRODUCTION_INQ_SEND					= M_DPRODUCTION + "/inquirySend";
		final String M_DPRODUCTION_UPLOAD_FILE				= M_DPRODUCTION + "/upload-file";
		final String M_DPRODUCTION_SEARCH					= M_DPRODUCTION + "/search";
		final String M_DPRODUCTION_PRINT					= M_DPRODUCTION + "/print";
		final String M_DPRODUCTION_DOCUMENT					= M_DPRODUCTION + "/document";
		final String M_DPRODUCTION_SEND_EMAIL				= M_DPRODUCTION + "/send-email";
		final String M_DPRODUCTION_CLOSING					= M_DPRODUCTION + "/closing";
		final String M_DPRODUCTION_MA_0011					= M_DPRODUCTION + "/ma-0011";
		final String M_DPRODUCTION_MA_0012					= M_DPRODUCTION + "/ma-0012";
		final String M_DPRODUCTION_SAVE						= M_DPRODUCTION + "/save";
		final String M_DPRODUCTION_TERM_AND_CONDITION		= M_DPRODUCTION + "/term-and-condition";
		final String M_DPRODUCTION_IS_VALID_TRX_DATE		= M_DPRODUCTION + "/isValidTrxDate";
		final String M_DPRODUCTION_VALIDATION_USER_LEVEL	= M_DPRODUCTION + "/validationUserLevel";
		
		//Treaty Production
		final String M_TPRODUCTION			= "/treaty-production";
		final String M_TPRODUCTION_DROPDOWN_TYPE_OF_COVER	= M_TPRODUCTION + "/get-dropdownTypeOfCover";
		final String M_TPRODUCTION_DROPDOWN_CLASIFICATION	= M_TPRODUCTION + "/get-dropdownClasification";
		final String M_TPRODUCTION_DROPDOWN_CURR			= M_TPRODUCTION + "/get-dropdownCurr";
		final String M_TPRODUCTION_EXCHANGE_RATE			= M_TPRODUCTION + "/exchangeRate";
		final String M_TPRODUCTION_MA_0011					= M_TPRODUCTION + "/ma-0011";
		final String M_TPRODUCTION_MA_0012					= M_TPRODUCTION + "/ma-0012";
		final String M_TPRODUCTION_DROPDOWN_NAME			= M_TPRODUCTION + "/get-dropdownName";
		final String M_TPRODUCTION_TAX_RATE					= M_TPRODUCTION + "/tax-rate";
		final String M_TPRODUCTION_TERM_AND_CONDITION		= M_TPRODUCTION + "/term-and-condition";
		final String M_TPRODUCTION_DROPDOWN_TC_GROUP		= M_TPRODUCTION + "/dropdown-tc-group";
		final String M_TPRODUCTION_DROPDOWN_TC_DETAILS		= M_TPRODUCTION + "/dropdown-tc-details";
		final String M_TPRODUCTION_DROPDOWN_DOCUMENT_TYPE	= M_TPRODUCTION + "/dropdown-document-type";
		final String M_TPRODUCTION_SAVE						= M_TPRODUCTION + "/save";
		final String M_TPRODUCTION_DELETE					= M_TPRODUCTION + "/delete";
		final String M_TPRODUCTION_INQ						= M_TPRODUCTION + "/inquiry";
		final String M_TPRODUCTION_INQ_MODIFY				= M_TPRODUCTION + "/inquiryModify";
		final String M_TPRODUCTION_INQ_SEND					= M_TPRODUCTION + "/inquirySend";
		final String M_TPRODUCTION_UPLOAD_FILE				= M_TPRODUCTION + "/upload-file";
		final String M_TPRODUCTION_DELETE_FILE				= M_TPRODUCTION + "/delete-file";
		final String M_TPRODUCTION_EXPORT_DOC				= M_TPRODUCTION + "/export-doc";
		final String M_TPRODUCTION_SEARCH					= M_TPRODUCTION + "/search";
		final String M_TPRODUCTION_VIEW_PDF					= M_TPRODUCTION + "/view-pdf";
		final String M_TPRODUCTION_PRINT					= M_TPRODUCTION + "/print";
		final String M_TPRODUCTION_DOCUMENT					= M_TPRODUCTION + "/document";
		final String M_TPRODUCTION_SEND_EMAIL				= M_TPRODUCTION + "/send-email";
		final String M_TPRODUCTION_SEND_EMAIL_CLOSING		= M_TPRODUCTION + "/send-email-closing";
		final String M_TPRODUCTION_CLOSING					= M_TPRODUCTION + "/closing";
		final String M_TPRODUCTION_CLOSING_PRINT			= M_TPRODUCTION + "/closing-print";
		final String M_TPRODUCTION_CLOSING_DOCUMENT			= M_TPRODUCTION + "/closing-document";
		final String M_TPRODUCTION_CLOSING_SEARCH			= M_TPRODUCTION + "/closing-search";
		
		//Treaty Adjustment
		final String M_TADJUSTMENT				= "/treaty-adjustment";
		final String M_TADJUSTMENT_INQ			= M_TADJUSTMENT + "/inquiry";
		final String M_TADJUSTMENT_INQ_MODIFY	= M_TADJUSTMENT + "/inquiryModify";
		final String M_TADJUSTMENT_PROCESS		= M_TADJUSTMENT + "/process";
		final String M_TADJUSTMENT_CANCEL		= M_TADJUSTMENT + "/cancel";
		final String M_TADJUSTMENT_SAVE			= M_TADJUSTMENT + "/save";
		final String M_TADJUSTMENT_PRINT		= M_TADJUSTMENT + "/print";
		final String M_TADJUSTMENT_DOCUMENT		= M_TADJUSTMENT + "/document";
		
		//Extension
		final String M_EXTENSION				= "/extension";
		final String M_EXTENSION_INQ			= M_EXTENSION + "/inquiry";
		final String M_EXTENSION_SAVE			= M_EXTENSION + "/save";
		final String M_EXTENSION_SAVE_UPLOAD	= M_EXTENSION + "/saveUpload";
		final String M_EXTENSION_UPLOAD_FILE	= M_EXTENSION + "/upload-file";
		final String M_EXTENSION_CLOSING_PRINT		= M_EXTENSION + "/closing-print";
		final String M_EXTENSION_CLOSING_DOCUMENT	= M_EXTENSION + "/closing-document";
		final String M_EXTENSION_CLOSING_SEARCH		= M_EXTENSION + "/closing-search";
		final String M_EXTENSION_ISFILENAME_EXIST 	= M_EXTENSION + "/isFileNameExist";
		
		//Upload Treaty
		final String M_UPLOAD_TREATY				= "/uploadTreaty";
		final String M_UPLOAD_TREATY_INQ			= M_UPLOAD_TREATY + "/inquiry";
		final String M_UPLOAD_TREATY_SAVE_UPLOAD	= M_UPLOAD_TREATY + "/saveUpload";
		final String M_UPLOAD_TREATY_CLOSING_DOCUMENT = M_UPLOAD_TREATY + "/closing-document";
		final String M_UPLOAD_TREATY_CLOSING_SEARCH		= M_UPLOAD_TREATY + "/closing-search";
		final String M_UPLOAD_TREATY_CLOSING_PRINT		= M_UPLOAD_TREATY + "/closing-print";
		

		/* ========================= MARKETING REPORT ========================= */
		//Production
		final String MR_PRODUCTION							= "/production";
		final String MR_PRODUCTION_DROPDOWN_TYPE_OF_COVER	= MR_PRODUCTION + "/get-dropdownTypeOfCover";
		final String MR_PRODUCTION_DROPDOWN_CLASIFICATION	= MR_PRODUCTION + "/get-dropdownClasification";
		final String MR_PRODUCTION_DROPDOWN_CURR			= MR_PRODUCTION + "/get-dropdownCurr";
		final String MR_PRODUCTION_EXCHANGE_RATE			= MR_PRODUCTION + "/exchangeRate";
		final String MR_PRODUCTION_MA_0011					= MR_PRODUCTION + "/ma-0011";
		final String MR_PRODUCTION_MA_0012					= MR_PRODUCTION + "/ma-0012";
		final String MR_PRODUCTION_DROPDOWN_NAME			= MR_PRODUCTION + "/get-dropdownName";
		final String MR_PRODUCTION_TAX_RATE					= MR_PRODUCTION + "/tax-rate";
		final String MR_PRODUCTION_TERM_AND_CONDITION		= MR_PRODUCTION + "/term-and-condition";
		final String MR_PRODUCTION_DROPDOWN_TC_GROUP		= MR_PRODUCTION + "/dropdown-tc-group";
		final String MR_PRODUCTION_DROPDOWN_TC_DETAILS		= MR_PRODUCTION + "/dropdown-tc-details";
		final String MR_PRODUCTION_DROPDOWN_DOCUMENT_TYPE	= MR_PRODUCTION + "/dropdown-document-type";
		final String MR_PRODUCTION_SAVE						= MR_PRODUCTION + "/save";
		final String MR_PRODUCTION_DELETE					= MR_PRODUCTION + "/delete";
		final String MR_PRODUCTION_INQ						= MR_PRODUCTION + "/inquiry";
		final String MR_PRODUCTION_INQ_MODIFY				= MR_PRODUCTION + "/inquiryModify";
		final String MR_PRODUCTION_INQ_SEND					= MR_PRODUCTION + "/inquirySend";
		final String MR_PRODUCTION_UPLOAD_FILE				= MR_PRODUCTION + "/upload-file";
		final String MR_PRODUCTION_DELETE_FILE				= MR_PRODUCTION + "/delete-file";
		final String MR_PRODUCTION_EXPORT_DOC				= MR_PRODUCTION + "/export-doc";
		final String MR_PRODUCTION_SEARCH					= MR_PRODUCTION + "/search";
		final String MR_PRODUCTION_VIEW_PDF					= MR_PRODUCTION + "/view-pdf";
		final String MR_PRODUCTION_PRINT					= MR_PRODUCTION + "/print";
		final String MR_PRODUCTION_DOCUMENT					= MR_PRODUCTION + "/document";
		final String MR_PRODUCTION_SEND_EMAIL				= MR_PRODUCTION + "/send-email";
		final String MR_PRODUCTION_SEND_EMAIL_CLOSING		= MR_PRODUCTION + "/send-email-closing";
		final String MR_PRODUCTION_CLOSING					= MR_PRODUCTION + "/closing";
		final String MR_PRODUCTION_CLOSING_PRINT			= MR_PRODUCTION + "/closing-print";
		final String MR_PRODUCTION_CLOSING_DOCUMENT			= MR_PRODUCTION + "/closing-document";
		final String MR_PRODUCTION_CLOSING_SEARCH			= MR_PRODUCTION + "/closing-search";
		final String MR_IS_VALID_TRX_DATE					= MR_PRODUCTION + "/isValidTrxDate";
		final String MR_VALIDATION_USER_LEVEL				= MR_PRODUCTION + "/validationUserLevel";
		
		final String MR_REPORT_PRODUCTION							= "/report-production";
		final String MR_REPORT_PRODUCTION_DROPDOWN_TYPE_OF_COVER	= MR_REPORT_PRODUCTION + "/get-dropdownTypeOfCover";
		final String MR_REPORT_PRODUCTION_CLIENT					= MR_REPORT_PRODUCTION + "/client";
		final String MR_REPORT_PRODUCTION_INQUIRY					= MR_REPORT_PRODUCTION + "/inquiry";
		final String MR_REPORT_PRODUCTION_TRANSACTION_DETAIL		= MR_REPORT_PRODUCTION + "/transaction-detail";
		final String MR_REPORT_PRODUCTION_EXPORT					= MR_REPORT_PRODUCTION + "/export";
		final String MR_REPORT_PRODUCTION_PRINT_JOURNAL				= MR_REPORT_PRODUCTION + "/print-journal";
		final String MR_REPORT_PRODUCTION_EXPORT_JOURNAL			= MR_REPORT_PRODUCTION + "/export-excel";
		final String MR_REPORT_PRODUCTION_PRINT 					= MR_REPORT_PRODUCTION + "/print";
		final String MR_REPORT_PRODUCTION_TR6_EXPORT_TO_EXCEL		= MR_REPORT_PRODUCTION + "/tr6-export-to-excel";
		
		final String MR_REPORT_PRODUCTION_DI							= "/report-production-di";
		final String MR_REPORT_PRODUCTION_DI_DROPDOWN_TYPE_OF_COVER	= MR_REPORT_PRODUCTION_DI + "/get-dropdownTypeOfCover";
		final String MR_REPORT_PRODUCTION_DI_CLIENT					= MR_REPORT_PRODUCTION_DI + "/client";
		final String MR_REPORT_PRODUCTION_DI_INQUIRY					= MR_REPORT_PRODUCTION_DI + "/inquiry";
		final String MR_REPORT_PRODUCTION_DI_TRANSACTION_DETAIL		= MR_REPORT_PRODUCTION_DI + "/transaction-detail";
		final String MR_REPORT_PRODUCTION_DI_EXPORT					= MR_REPORT_PRODUCTION_DI + "/export";
		final String MR_REPORT_PRODUCTION_DI_PRINT_JOURNAL				= MR_REPORT_PRODUCTION_DI + "/print-journal";
		final String MR_REPORT_PRODUCTION_DI_EXPORT_JOURNAL			= MR_REPORT_PRODUCTION_DI + "/export-excel";
		final String MR_REPORT_PRODUCTION_DI_PRINT 					= MR_REPORT_PRODUCTION_DI + "/print";
		final String MR_REPORT_PRODUCTION_DI_TR6_EXPORT_TO_EXCEL		= MR_REPORT_PRODUCTION_DI + "/tr6-export-to-excel";
		final String M_DMODIFY							= "/direct-modify";
		final String M_DMODIFY_INQ						= M_DMODIFY + "/inquiry";
		final String M_DMODIFY_INQ_DETAIL				= M_DMODIFY + "/inquiry-detail";
		final String M_DMODIFY_SAVE						= M_DMODIFY + "/save";
		final String M_DMODIFY_DROPDOWN_TYPE_OF_COVER	= M_DMODIFY + "/get-dropdownTypeOfCover";
		
		final String M_ENDORSEMENT							= "/endorsement";
		final String M_ENDORSEMENT_DROPDOWN_TYPE_OF_COVER	= M_ENDORSEMENT + "/get-dropdownTypeOfCover";
		final String M_ENDORSEMENT_CLIENT					= M_ENDORSEMENT + "/client";
		final String M_ENDORSEMENT_TYPE_OF_COVER			= M_ENDORSEMENT + "/get-dropdownTypeOfCover";
		final String M_ENDORSEMENT_INQUIRY					= M_ENDORSEMENT + "/inquiry";
		final String M_ENDORSEMENT_REMOVE					= M_ENDORSEMENT + "/remove";
		final String M_ENDORSEMENT_SAVE						= M_ENDORSEMENT + "/save";
		final String M_ENDORSEMENT_CREATE_CLOSING_REPORT	= M_ENDORSEMENT + "/create-closing-report";
		final String M_ENDORSEMENT_INQ_SEND_TABLE			= M_ENDORSEMENT + "/inquiry-send-table";
		
		final String M_NEW_ENDORSEMENT							= "/new-endorsement";
		final String M_NEW_ENDORSEMENT_DROPDOWN_TYPE_OF_COVER	= M_NEW_ENDORSEMENT + "/get-dropdownTypeOfCover";
		final String M_NEW_ENDORSEMENT_CLIENT					= M_NEW_ENDORSEMENT + "/client";
		final String M_NEW_ENDORSEMENT_TYPE_OF_COVER			= M_NEW_ENDORSEMENT + "/get-dropdownTypeOfCover";
		final String M_NEW_ENDORSEMENT_INQUIRY					= M_NEW_ENDORSEMENT + "/inquiry";
		final String M_NEW_ENDORSEMENT_REMOVE					= M_NEW_ENDORSEMENT + "/remove";
		final String M_NEW_ENDORSEMENT_SAVE						= M_NEW_ENDORSEMENT + "/save";
		final String M_NEW_ENDORSEMENT_CREATE_CLOSING_REPORT	= M_NEW_ENDORSEMENT + "/create-closing-report";
		final String M_NEW_ENDORSEMENT_INQ_SEND_TABLE			= M_NEW_ENDORSEMENT + "/inquiry-send-table";
		final String M_NEW_ENDORSEMENT_GET_REQUSET_ID			= M_NEW_ENDORSEMENT + "/get-request-id";
		final String M_NEW_ENDORSEMENT_CLOSING	 				= M_NEW_ENDORSEMENT + "/closing";
		final String M_NEW_ENDORSEMENT_VALIDATION_USER_LEVEL	= M_NEW_ENDORSEMENT + "/validationUserLevel";
		
		final String M_RENEWAL							= "/renewal";
		final String M_RENEWAL_DROPDOWN_TYPE_OF_COVER	= M_RENEWAL + "/get-dropdownTypeOfCover";
		final String M_RENEWAL_CLIENT					= M_RENEWAL + "/client";
		final String M_RENEWAL_INQUIRY					= M_RENEWAL + "/inquiry";
		final String M_RENEWAL_REMOVE					= M_RENEWAL + "/remove";
		final String M_RENEWAL_EDIT						= M_RENEWAL + "/edit";
		final String M_RENEWAL_SEND						= M_RENEWAL + "/send";
		final String M_RENEWAL_EXPORT					= M_RENEWAL + "/export";
		final String M_RENEWAL_PRINT					= M_RENEWAL + "/print";
		
		final String M_CLAIMS							= "/claims-internal";
		final String M_CLAIMS_DOWNLOAD_FILE				= M_CLAIMS + "/download-file";
		final String M_CLAIMS_DROPDOWN_TYPE_OF_COVER	= M_CLAIMS + "/get-dropdownTypeOfCover";
		final String M_CLAIMS_DROPDOWN_TYPE_OF_COVER_VAL = M_CLAIMS + "/get-dropdownTypeOfCoverVal";
		final String M_CLAIMS_DROPDOWN_DOCUMENT_TYPE	= M_CLAIMS + "/dropdown-document-type";
		final String M_CLAIMS_INQ_DATA					= M_CLAIMS + "/inquiry-data";
		final String M_CLAIMS_INQ_DETAIL				= M_CLAIMS + "/inquiry-detail";
		final String M_CLAIMS_INQ_CREATE				= M_CLAIMS + "/inquiry-create";
		final String M_CLAIMS_INQ_CREATE_DETAIL			= M_CLAIMS + "/inquiry-create-detail";
		final String M_CLAIMS_GET_UNDERWRITING			= M_CLAIMS + "/get-underwriting";
		final String M_CLAIMS_SAVE						= M_CLAIMS + "/save";
		final String M_CLAIMS_DELETE					= M_CLAIMS + "/delete";
		final String M_CLAIMS_SETTLEMENT				= M_CLAIMS + "/settlement";
		final String M_CLAIMS_UPLOAD_FILE				= M_CLAIMS + "/upload-file";
		final String M_CLAIMS_DROPDOWN_YEAR				= M_CLAIMS + "/dropdown-year";
		final String M_CLAIMS_EXPORT_CLAIMSLIST_EXCEL	= M_CLAIMS + "/export-claimslist-excel";
		final String M_CLAIMS_GET_TR6J					= M_CLAIMS + "/get-tr6j";
		final String M_CLAIMS_PRINT_DLA_PLA				= M_CLAIMS + "/print-pla-dla";
		final String M_CLAIMS_PRINT_DOC_DLA_PLA			= M_CLAIMS + "/print-doc-pla-dla";
		final String M_CLAIMS_CREATE_DC_NOTES			= M_CLAIMS + "/create-dc-notes";
		final String M_CLAIMS_GET_LIST_INSURANCE		= M_CLAIMS + "/get-list-insurance";
		final String M_CLAIMS_GET_LIST_VALUE			= M_CLAIMS + "/get-list-value";
		final String M_CLAIMS_GET_FINANCE				= M_CLAIMS + "/get-finance";
		final String M_CLAIMS_PROCESS_FINANCE			= M_CLAIMS + "/process-finance";
		final String M_CLAIMS_FINANCE_SEARCH			= M_CLAIMS + "/finance-search";
		final String M_CLAIMS_FINANCE_VIEW_PDF			= M_CLAIMS + "/finance-view-pdf";
		final String M_CLAIMS_FINANCE_EXPORT_EXCEL		= M_CLAIMS + "/finance-export-excel";
		final String M_CLAIMS_GET_LIST_INTERNALMEMO		= M_CLAIMS + "/get-list-internalmemo";
		final String M_CLAIMS_CREATE_INTERNALMEMO		= M_CLAIMS + "/create-internalmemo";
		final String M_CLAIMS_CREATE_INTERNALMEMO_EXCEL = M_CLAIMS + "/create-internalmemo-excel";
		
		final String M_CLAIM							= "/claim";
		final String M_CLAIM_DROPDOWN_TYPE_OF_COVER		= M_CLAIM + "/get-dropdownTypeOfCover";
		final String M_CLAIM_CLIENT						= M_CLAIM + "/client";
		final String M_CLAIM_CLIENT2					= M_CLAIM + "/client2";
		final String M_CLAIM_STATUS						= M_CLAIM + "/get-dropdownStatus";
		final String M_CLAIM_INQUIRY					= M_CLAIM + "/inquiry";
		final String M_CLAIM_INQUIRY2					= M_CLAIM + "/inquiry2";
		final String M_CLAIM_INQUIRY_TABLE_DETAIL		= M_CLAIM + "/inquiry-table-detail";
		final String M_CLAIM_REMOVE						= M_CLAIM + "/remove";
		final String M_CLAIM_SAVE						= M_CLAIM + "/save";
		final String M_CLAIM_CREATE_CLOSING_REPORT		= M_CLAIM + "/create-closing-report";
		final String M_CLAIM_INQ_SEND_TABLE				= M_CLAIM + "/inquiry-send-table";
		
		final String M_CANCEL_UPLOAD					= "/cancel-upload";
		final String M_CANCEL_UPLOAD_SEARCH				= M_CANCEL_UPLOAD + "/search";
		final String M_CANCEL_UPLOAD_DELETE				= M_CANCEL_UPLOAD + "/delete";
		
		final String L_LOGIN_FORM					= "/form";
		final String L_LOGIN_PAGE_ITEM				= "/login-page-item";
		final String L_LOGIN_LOGOUT					= "/logout";
		final String L_LOGIN_RESET_PASSWORD			= "/reset-password";
		final String L_LOGIN_IS_USER_LOKED			= "/is-user-locked";
		final String L_LOGIN_RESET_FAILED_LOGIN		= "/reset-failed-login";
		final String L_LOGIN_SYNC_USER				= "/sync-user";
	}

	interface URI {
		//General
		final String G_APPLICATION_DATE			= "/app-re-broker/general/get-appDate";
		final String G_APPLICATION_DATE_NEXT	= "/app-re-broker/general/get-appDateNext";
		final String G_CURR_COA					= "/app-re-broker/general/get-currCoa";
		final String G_DROPDOWN_BANK			= "/app-re-broker/general/get-dropdownBank";
		final String G_DROPDOWN_CURRENCY		= "/app-re-broker/general/get-dropdownCurrency";
		final String G_DROPDOWN_DC_NOTE			= "/app-re-broker/general/get-dropdownDCNote";
		final String G_DROPDOWN_OFFICE			= "/app-re-broker/general/get-dropdownOffice";
		final String G_DROPDOWN_OFFICER			= "/app-re-broker/general/get-dropdownOfficer";
		final String G_DROPDOWN_OFFICER_NOT_2	= "/app-re-broker/general/get-dropdownOfficerTypeNot2";
		final String G_DROPDOWN_PARAM			= "/app-re-broker/general/get-dropdownParam";
		final String G_DROPDOWN_PAY_MTHD		= "/app-re-broker/general/get-dropdownPaymentMethod";
		final String G_DROPDOWN_PRODUCT			= "/app-re-broker/general/get-dropdownProduct";
		final String G_DROPDOWN_PROJECT			= "/app-re-broker/general/get-dropdownProject";
		final String G_DROPDOWN_STATUS			= "/app-re-broker/general/get-dropdownStatus";
		final String G_DROPDOWN_STATUS_CLAIMS	= "/app-re-broker/general/get-dropdownStatusClaims";
		final String G_FIND_EXCHANGE			= "/app-re-broker/general/findExchange";
		final String G_FIND_EXCHANGE_NON_EOM	= "/app-re-broker/general/findExchangeNonEom";
		final String G_FIND_TAX_RATE			= "/app-re-broker/general/findTaxRate";
		final String G_DROPDOWN_VALUE			= "/app-re-broker/general/get-dropdownValue";
		final String G_DROPDOWN_FORMAT			= "/app-re-broker/general/get-dropdownFormat";
		final String G_DROPDOWN_CLIENT_TYPE		= "/app-re-broker/general/get-dropdownClientType";
		final String G_MESSAGE_SAVE				= "/app-re-broker/general/get-messageSave";
		final String G_YEAR						= "/app-re-broker/general/get-year";
		final String G_LAST_PRO_MONTH_YEAR		= "/app-re-broker/general/get-lastProMonthAndProYear";
		final String G_LOOKUP_CLIENT			= "/app-re-broker/general/lookup-client";
		final String G_DROPDOWN_TYPE_OF_COVER	= "/app-re-broker/general/get-dropdownTypeOfCover";
		final String G_DROPDOWN_CLASIFICATION	= "/app-re-broker/general/get-dropdownClasification";
		final String G_DROPDOWN_NAME			= "/app-re-broker/general/get-dropdownName";
		final String G_DROPDOWN_TC_GROUP		= "/app-re-broker/general/dropdown-tc-group";
		final String G_DROPDOWN_TC_DETAILS		= "/app-re-broker/general/dropdown-tc-details";
		final String G_DROPDOWN_DOCUMENT_TYPE	= "/app-re-broker/general/dropdown-document-type";
		final String G_DROPDOWN_CLIENT			= "/app-re-broker/general/get-dropdownClient";
		final String G_LOGO_COMPANY				= "/app-re-broker/general/get-logoCompany";
		final String G_DROPDOWN_PARTICIPANT_STATUS	= "/app-re-broker/general/dropdown-participant-status";
		final String G_DROPDOWN_PARTICIPANT_COUNTRY	= "/app-re-broker/general/dropdown-participant-country";
		final String G_DROPDOWN_PARTICIPANT_INDUSTRY	= "/app-re-broker/general/dropdown-participant-industry";
		final String G_DROPDOWN_TB_YEAR			= "/app-re-broker/general/dropdown-tb-year";
		final String G_GET_PARAMETER			= "/app-re-broker/general/get-parameter";
		final String G_DROPDOWN_CURR_TAX		= "/app-re-broker/general/dropdown-curr-tax";
		final String G_TR0006_INQUIRY			= "/app-re-broker/general/tr0006-inquiry";
		final String G_CLAIMS_INQUIRY			= "/app-re-broker/marketing/claims/inquiry";
		final String G_CLAIMS_INQUIRY2			= "/app-re-broker/marketing/claims/inquiry2";
		final String G_CLAIMS_INQUIRY3			= "/app-re-broker/marketing/claims/inquiry3";
		final String G_IS_ALREADY_CLOSING		= "/app-re-broker/general/is-already-closing";
		
		//Beginning Balance
		final String SD_BEGIN_BAL_GET_YEAR		= "/app-re-broker/static-data/beginBal/get-year";
		final String SD_BEGIN_BAL_INQ_V1		= "/app-re-broker/static-data/beginBal/inquiry/v1.0";
		final String SD_BEGIN_BAL_INQ_V2		= "/app-re-broker/static-data/beginBal/inquiry/v2.0";
		final String SD_BEGIN_BAL_SAVE			= "/app-re-broker/static-data/beginBal/save";
		final String SD_BEGIN_BAL_SUM_DEBIT0	= "/app-re-broker/static-data/beginBal/summaryDebit0";
		final String SD_BEGIN_BAL_SUM_CREDIT0	= "/app-re-broker/static-data/beginBal/summaryCredit0";
		final String SD_BEGIN_BAL_LOOKUP_COA	= "/app-re-broker/static-data/beginBal/lookup-coa";

		//Holiday
		final String SD_HOLIDAY_INQ				= "/app-re-broker/static-data/holiday/inquiry";
		final String SD_HOLIDAY_IS_EXSIST		= "/app-re-broker/static-data/holiday/isExsist";
		final String SD_HOLIDAY_SAVE			= "/app-re-broker/static-data/holiday/save";

		//Chart of Account
		final String SD_COA_INQ				= "/app-re-broker/static-data/coa/inquiry";
		final String SD_COA_ROLE_UP			= "/app-re-broker/static-data/coa/roleUp";
		final String SD_COA_DROPDOWN_CLASS	= "/app-re-broker/static-data/coa/dropdownClass";
		final String SD_COA_DROPDOWN_CURR	= "/app-re-broker/static-data/coa/dropdownCurr";
		final String SD_COA_SAVE			= "/app-re-broker/static-data/coa/save";

		//Balance Sheet
		final String ACCTR_BS_CREATE_EXCEL	= "/app-re-broker/accounting-report/balanceSheet/create-excel";
		final String ACCTR_BS_CREATE_HTML	= "/app-re-broker/accounting-report/balanceSheet/create-html";
		final String ACCTR_BS_CREATE_PDF	= "/app-re-broker/accounting-report/balanceSheet/create-pdf";
		
		//Balance Sheet Report
		final String ACCTR_BS_REPORT_PREVIEW	= "/app-re-broker/accounting-report/bs-report/preview";
		final String ACCTR_BS_REPORT_PRINT		= "/app-re-broker/accounting-report/bs-report/print";
		final String ACCTR_BS_REPORT_EXPORT		= "/app-re-broker/accounting-report/bs-report/export";
		
		//Balance Sheet Report
		final String ACCTR_PL_ACCOUNT_PREVIEW	= "/app-re-broker/accounting-report/pl-account/preview";
		final String ACCTR_PL_ACCOUNT_PRINT		= "/app-re-broker/accounting-report/pl-account/print";
		final String ACCTR_PL_ACCOUNT_EXPORT	= "/app-re-broker/accounting-report/pl-account/export";

		//Cash Flow
		final String ACCTR_CF_CREATE_EXCEL	= "/app-re-broker/accounting-report/cashFlow/create-excel";
		final String ACCTR_CF_CREATE_HTML	= "/app-re-broker/accounting-report/cashFlow/create-html";
		final String ACCTR_CF_CREATE_PDF	= "/app-re-broker/accounting-report/cashFlow/create-pdf";

		//General Ledger
		final String ACCTR_GL_CREATE_EXCEL	= "/app-re-broker/accounting-report/generalLedger/create-excel";
		final String ACCTR_GL_CREATE_HTML	= "/app-re-broker/accounting-report/generalLedger/create-html";
		final String ACCTR_GL_CREATE_PDF	= "/app-re-broker/accounting-report/generalLedger/create-pdf";

		//Profit And Lost
		final String ACCTR_PL_CREATE_EXCEL	= "/app-re-broker/accounting-report/profitLost/create-excel";
		final String ACCTR_PL_CREATE_HTML	= "/app-re-broker/accounting-report/profitLost/create-html";
		final String ACCTR_PL_CREATE_PDF	= "/app-re-broker/accounting-report/profitLost/create-pdf";

		//Trial Balance
		final String ACCTR_TB_CREATE_EXCEL	= "/app-re-broker/accounting-report/trialBalance/create-excel";
		final String ACCTR_TB_CREATE_HTML	= "/app-re-broker/accounting-report/trialBalance/create-html";
		final String ACCTR_TB_CREATE_PDF	= "/app-re-broker/accounting-report/trialBalance/create-pdf";

		//Inquiry Journal
		final String ACCTR_INQUIRY_JOURNAL_INQ					= "/app-re-broker/accounting-report/inquiry-journal/inquiry";
		final String ACCTR_INQUIRY_JOURNAL_DROPDOWN_PROJECT		= "/app-re-broker/accounting-report/inquiry-journal/dropdown-project";
		final String ACCTR_INQUIRY_JOURNAL_DROPDOWN_OFFICE		= "/app-re-broker/accounting-report/inquiry-journal/dropdown-office";

		//Entry Journal
		final String ACCT_ENTRY_JOURNAL_INQ						= "/app-re-broker/accounting/entry-journal/inquiry";
		final String ACCT_ENTRY_JOURNAL_DROPDOWN_PROJECT		= "/app-re-broker/accounting/entry-journal/dropdown-project";
		final String ACCT_ENTRY_JOURNAL_DROPDOWN_OFFICE			= "/app-re-broker/accounting/entry-journal/dropdown-office";
		final String ACCT_ENTRY_JOURNAL_TRANSACTION_MIN_DATE	= "/app-re-broker/accounting/entry-journal/transaction-min-date";
		final String ACCT_ENTRY_JOURNAL_CLIENT  				= "/app-re-broker/accounting/entry-journal/client";
		final String ACCT_ENTRY_JOURNAL_COA		  				= "/app-re-broker/accounting/entry-journal/coa";
		final String ACCT_ENTRY_JOURNAL_VOUCHER_CODE			= "/app-re-broker/accounting/entry-journal/voucher-code";
		final String ACCT_ENTRY_JOURNAL_EXCHANGE				= "/app-re-broker/accounting/entry-journal/exchange";
		final String ACCT_ENTRY_JOURNAL_SAVE					= "/app-re-broker/accounting/entry-journal/save";
		final String ACCT_ENTRY_JOURNAL_EDIT					= "/app-re-broker/accounting/entry-journal/edit";
		final String ACCT_ENTRY_JOURNAL_REMOVE					= "/app-re-broker/accounting/entry-journal/remove";
		final String ACCT_ENTRY_JOURNAL_PRINT_JOURNAL			= "/app-re-broker/accounting/entry-journal/print-journal";
		final String ACCT_ENTRY_JOURNAL_EXPORT_EXCEL			= "/app-re-broker/accounting/entry-journal/export-excel";
		final String ACCT_ENTRY_JOURNAL_PRINT					= "/app-re-broker/accounting/entry-journal/print";

		//Eom - Eoy
		final String ACCT_EOM_EOY_PROCESS			 	= "/app-re-broker/accounting/eomeoy/process";
		final String ACCT_EOM_EOY_PROGRESS				= "/app-re-broker/accounting/eomeoy/progress";
		final String ACCT_EOM_EOY_RESET_PROGRESS		= "/app-re-broker/accounting/eomeoy/resetProgress";
		final String ACCT_EOM_EOY_CHECK_PROCESS			= "/app-re-broker/accounting/eomeoy/checkProcess";

		//Bank Book
		final String FIN_BANK_BOOK_INQ					= "/app-re-broker/finance/bank-book/inquiry";
		final String FIN_BANK_BOOK_INQ_MODIFY			= "/app-re-broker/finance/bank-book/inquiryModify";
		final String FIN_BANK_BOOK_SAVE					= "/app-re-broker/finance/bank-book/save";
		final String FIN_BANK_BOOK_DELETE				= "/app-re-broker/finance/bank-book/delete";

		//Debit Credit Notes
		final String M_DC_NOTE_INQ						= "/app-re-broker/marketing/dc-note/inquiry";
		final String M_DC_NOTE_SAVE						= "/app-re-broker/marketing/dc-note/save";
		final String M_DC_NOTE_DELETE					= "/app-re-broker/marketing/dc-note/delete";
		final String M_DC_NOTE_CREATE_EXCEL				= "/app-re-broker/marketing/dc-note/create-excel";
		final String M_DC_NOTE_CREATE_HTML				= "/app-re-broker/marketing/dc-note/create-html";
		final String M_DC_NOTE_CREATE_PDF				= "/app-re-broker/marketing/dc-note/create-pdf";
		final String M_DC_NOTE_CREATE_DOC				= "/app-re-broker/marketing/dc-note/create-doc";
		final String M_DC_NOTE_CREATE_HTML_U_TREATY		= "/app-re-broker/marketing/dc-note/create-html-upload-treaty";
		final String M_DC_NOTE_CREATE_PDF_U_TREATY		= "/app-re-broker/marketing/dc-note/create-pdf-upload-treaty";
		final String M_DC_NOTE_CREATE_DOC_U_TREATY		= "/app-re-broker/marketing/dc-note/create-doc-upload-treaty";
		
		//Endorsement
		final String M_ENDORSEMENT_REMOVE				= "/app-re-broker/marketing/endorsement/remove";
		final String M_ENDORSEMENT_SAVE					= "/app-re-broker/marketing/endorsement/save";
		final String M_ENDORSEMENT_INQUIRY_SEND_TABLE	= "/app-re-broker/marketing/endorsement/inquiry-send-table";
		final String M_ENDORSEMENT_CREATE_CLOSING_REPORT = "/app-re-broker/marketing/endorsement/create-closing-report";
		
		final String M_NEW_ENDORSEMENT_GET_REQUEST_ID				= "/app-re-broker/marketing/new-endorsement/get-request-id";
		final String M_NEW_ENDORSEMENT_CLOSING						= "/app-re-broker/marketing/new-endorsement/closing";

		//Renewal
		final String M_RENEWAL_INQUIRY					= "/app-re-broker/marketing/renewal/inquiry";
		final String M_RENEWAL_REMOVE					= "/app-re-broker/marketing/renewal/remove";
		final String M_RENEWAL_EDIT						= "/app-re-broker/marketing/renewal/edit";
		final String M_RENEWAL_SEND						= "/app-re-broker/marketing/renewal/send";
		final String M_RENEWAL_EXPORT					= "/app-re-broker/marketing/renewal/export";
		final String M_RENEWAL_PRINT					= "/app-re-broker/marketing/renewal/print";


		//Settlement
		final String FIN_SETTLEMENT_INQ					= "/app-re-broker/finance/settlement/inquiry";
		final String FIN_SETTLEMENT_SAVE				= "/app-re-broker/finance/settlement/save";

		//Remittance
		final String FIN_REMITTANCE_INQ					= "/app-re-broker/finance-report/remittance/inquiry";
		final String FIN_REMITTANCE_PRINT_JOURNAL		= "/app-re-broker/finance-report/remittance/print-journal";
		final String FIN_REMITTANCE_EXPORT_EXCEL		= "/app-re-broker/finance-report/remittance/export-excel";
		final String FIN_REMITTANCE_PRINT				= "/app-re-broker/finance-report/remittance/print";
		
		//Remittance
		final String FIN_VAT_REPORT_INQ					= "/app-re-broker/finance-report/vat-report/inquiry";
		final String FIN_VAT_REPORT_PRINT				= "/app-re-broker/finance-report/vat-report/print";
		final String FIN_VAT_REPORT_EXPORT				= "/app-re-broker/finance-report/vat-report/export";

		//Outstanding
		final String FIN_OUTSTANDING_INQ				= "/app-re-broker/finance-report/outstanding/inquiry";
		final String FIN_OUTSTANDING_SAVE				= "/app-re-broker/finance-report/outstanding/save";
		final String FIN_OUTSTANDING_EXPORT_EXCEL		= "/app-re-broker/finance-report/outstanding/export-excel";
		final String FIN_OUTSTANDING_PRINT				= "/app-re-broker/finance-report/outstanding/print";
		
		//Cancel Settlement
		final String FIN_CNCL_SETTLEMENT_INQ			= "/app-re-broker/finance/cancel-settlement/inquiry";
		final String FIN_CNCL_SETTLEMENT_SAVE			= "/app-re-broker/finance/cancel-settlement/save";

		//Production Facultative
		final String MR_PRODUCTION_MA_0011				= "/app-re-broker/marketing-report/production/ma-0011";
		final String MR_PRODUCTION_MA_0012				= "/app-re-broker/marketing-report/production/ma-0012";
		final String MR_PRODUCTION_TAX_RATE				= "/app-re-broker/marketing-report/production/tax-rate";
		final String MR_PRODUCTION_TERM_AND_CONDITION	= "/app-re-broker/marketing-report/production/term-and-condition";
		final String MR_PRODUCTION_SAVE					= "/app-re-broker/marketing-report/production/save";
		final String MR_PRODUCTION_DELETE				= "/app-re-broker/marketing-report/production/delete";
		final String MR_PRODUCTION_INQ					= "/app-re-broker/marketing-report/production/inquiry";
		final String MR_PRODUCTION_INQ_MODIFY			= "/app-re-broker/marketing-report/production/inquiryModify";
		final String MR_PRODUCTION_INQ_SEND				= "/app-re-broker/marketing-report/production/inquirySend";
		final String MR_PRODUCTION_RPT_DOC				= "/app-re-broker/marketing-report/production/report/create-doc";
		final String MR_PRODUCTION_RPT_HMTL				= "/app-re-broker/marketing-report/production/report/create-html";
		final String MR_PRODUCTION_RPT_PDF				= "/app-re-broker/marketing-report/production/report/create-pdf";
		final String MR_PRODUCTION_SEND_EMAIL			= "/app-re-broker/marketing-report/production/send-email";
		final String MR_PRODUCTION_SEND_EMAIL_CLOSING	= "/app-re-broker/marketing-report/production/send-email-closing";
		final String MR_PRODUCTION_CLOSING				= "/app-re-broker/marketing-report/production/closing";
		final String MR_PRODUCTION_RPT_CLOSING_HTML		= "/app-re-broker/marketing-report/production/create-closing-html";
		final String MR_IS_VALID_TRX_DATE				= "/app-re-broker/marketing-report/production/isValidTrxDate";
		final String MR_VALIDATION_USER_LEVEL			= "/app-re-broker/marketing-report/production/validationUserLevel";
		
		//Production Treaty
		final String M_TPRODUCTION_DNCN_CREATE_PDF		= "/app-re-broker/marketing/treaty-production/dcNote-pdf";
		final String M_TPRODUCTION_DNCN_CREATE_DOC		= "/app-re-broker/marketing/treaty-production/dcNote-doc";
		final String M_TPRODUCTION_CLOSING				= "/app-re-broker/marketing/treaty-production/closing";
		final String M_TPRODUCTION_DELETE				= "/app-re-broker/marketing/treaty-production/delete";
		final String M_TPRODUCTION_INQ					= "/app-re-broker/marketing/treaty-production/inquiry";
		final String M_TPRODUCTION_INQ_MODIFY			= "/app-re-broker/marketing/treaty-production/inquiryModify";
		final String M_TPRODUCTION_INQ_SEND				= "/app-re-broker/marketing/treaty-production/inquirySend";
		final String M_TPRODUCTION_RPT_DOC				= "/app-re-broker/marketing/treaty-production/create-doc";
		final String M_TPRODUCTION_RPT_PDF				= "/app-re-broker/marketing/treaty-production/create-pdf";
		final String M_TPRODUCTION_SAVE					= "/app-re-broker/marketing/treaty-production/save";
		
		//Treaty Adjustment
		final String M_TADJUSTMENT_INQ					= "/app-re-broker/marketing/treaty-adjustment/inquiry";
		final String M_TADJUSTMENT_INQ_MODIFY			= "/app-re-broker/marketing/treaty-adjustment/inquiryModify";
		final String M_TADJUSTMENT_PROCESS				= "/app-re-broker/marketing/treaty-adjustment/process";
		final String M_TADJUSTMENT_CANCEL				= "/app-re-broker/marketing/treaty-adjustment/cancel";
		final String M_TADJUSTMENT_SAVE					= "/app-re-broker/marketing/treaty-adjustment/save";
		
		//Production Direct
		final String M_DPRODUCTION_GET_COMM_OUT_NAME	= "/app-re-broker/marketing/direct-production/get-commOutName";
		final String M_DPRODUCTION_GET_WHT_CLIENT		= "/app-re-broker/marketing/direct-production/get-whtClient";
		final String M_DPRODUCTION_SAVE					= "/app-re-broker/marketing/direct-production/save";
		final String M_DPRODUCTION_INQ_MODIFY			= "/app-re-broker/marketing/direct-production/inquiryModify";
		final String M_DPRODUCTION_INQ_SEND				= "/app-re-broker/marketing/direct-production/inquirySend";
		final String M_DPRODUCTION_RPT_HMTL				= "/app-re-broker/marketing/direct-production/report/create-html";
		final String M_DPRODUCTION_RPT_DOC				= "/app-re-broker/marketing/direct-production/report/create-doc";
		final String M_DPRODUCTION_RPT_PDF				= "/app-re-broker/marketing/direct-production/report/create-pdf";
		final String M_DPRODUCTION_SEND_EMAIL			= "/app-re-broker/marketing/direct-production/send-email";
		final String M_DPRODUCTION_CLOSING				= "/app-re-broker/marketing/direct-production/closing";
		final String M_DPRODUCTION_RPT_CLOSING_HTML		= "/app-re-broker/marketing/direct-production/create-closing-html";
		
		//Claims
		final String M_CLAIMS_INQ_DATA					= "/app-re-broker/marketing/claims/inquiry-data";
		final String M_CLAIMS_INQ_DETAIL				= "/app-re-broker/marketing/claims/inquiry-detail";
		final String M_CLAIMS_INQ_CREATE				= "/app-re-broker/marketing/claims/inquiry-create";
		final String M_CLAIMS_INQ_CREATE_DETAIL			= "/app-re-broker/marketing/claims/inquiry-create-detail";
		final String M_CLAIMS_GET_UNDERWRITING			= "/app-re-broker/marketing/claims/get-underwriting";
		final String M_CLAIMS_SAVE						= "/app-re-broker/marketing/claims/save";
		final String M_CLAIMS_DELETE					= "/app-re-broker/marketing/claims/delete";
		final String M_CLAIMS_SETTLEMENT				= "/app-re-broker/marketing/claims/settlement";
		final String M_CLAIMS_DROPDOWN_YEAR				= "/app-re-broker/marketing/claims/dropdown-year";
		final String M_CLAIMS_EXPORT_CLAIMSLIST_EXCEL	= "/app-re-broker/marketing/claims/export-claimslist-excel";
		final String M_CLAIMS_GET_TR6J					= "/app-re-broker/marketing/claims/get-tr6j";
		final String M_CLAIMS_PRINT_DLA_PLA				= "/app-re-broker/marketing/claims/print-dla-pla";
		final String M_CLAIMS_PRINT_DOC_DLA_PLA			= "/app-re-broker/marketing/claims/print-doc-dla-pla";
		final String M_CLAIMS_CREATE_DC_NOTES			= "/app-re-broker/marketing/claims/create-dc-notes";
		final String M_CLAIMS_GET_LIST_INSURANCE		= "/app-re-broker/marketing/claims/get-list-insurance";
		final String M_CLAIMS_GET_LIST_VALUE			= "/app-re-broker/marketing/claims/get-list-tr15a";
		final String M_CLAIMS_GET_FINANCE				= "/app-re-broker/marketing/claims/get-finance";
		final String M_CLAIMS_PROCESS_FINANCE			= "/app-re-broker/marketing/claims/process-finance";
		final String M_CLAIMS_GET_LIST_INTERNALMEMO		= "/app-re-broker/marketing/claims/get-list-internalmemo";
		final String M_CLAIMS_CREATE_INTERNALMEMO		= "/app-re-broker/marketing/claims/create-internalmemo";
		final String M_CLAIMS_CREATE_INTERNALMEMO_EXCEL = "/app-re-broker/marketing/claims/create-internalmemo-excel";
		
		//Direct Modify
		final String M_DMODIFY_INQUIRY					= "/app-re-broker/marketing/direct-modify/inquiry";
		final String M_DMODIFY_INQUIRY_DETAIL			= "/app-re-broker/marketing/direct-modify/inquiry-detail";
		final String M_DMODIFY_SAVE						= "/app-re-broker/marketing/direct-modify/save";
		
		final String M_CANCEL_UPLOAD_SEARCH				= "/app-re-broker/marketing/cancel-upload/search";
		final String M_CANCEL_UPLOAD_DELETE				= "/app-re-broker/marketing/cancel-upload/delete";
		
		final String MR_REPORT_PRODUCTION_TRANSACTION_DETAIL = "/app-re-broker/marketing-report/report-production/transaction-detail";
		final String MR_REPORT_PRODUCTION_EXPORT			 = "/app-re-broker/marketing-report/report-production/export";
		final String MR_REPORT_PRODUCTION_PRINT_JOURNAL		 = "/app-re-broker/marketing-report/report-production/print-journal";
		final String MR_REPORT_PRODUCTION_EXPORT_EXCEL		 = "/app-re-broker/marketing-report/report-production/export-excel";
		final String MR_REPORT_PRODUCTION_PRINT				 = "/app-re-broker/marketing-report/report-production/print";
		final String MR_REPORT_PRODUCTION_TR6_EXPORT_TO_EXCEL	= "/app-re-broker/marketing-report/report-production/tr6-export-to-excel";
		
		final String MR_REPORT_PRODUCTION_TRANSACTION_DETAIL_DI = "/app-re-broker/marketing-report/report-production/transaction-detail-di";
		final String MR_REPORT_PRODUCTION_DI_EXPORT			    = "/app-re-broker/marketing-report/report-production-di/export";
		final String MR_REPORT_PRODUCTION_DI_PRINT_JOURNAL	    = "/app-re-broker/marketing-report/report-production-di/print-journal";
		final String MR_REPORT_PRODUCTION_DI_EXPORT_EXCEL		= "/app-re-broker/marketing-report/report-production/export-excel";
		final String MR_REPORT_PRODUCTION_DI_PRINT				= "/app-re-broker/marketing-report/report-production-di/print";
		final String MR_REPORT_PRODUCTION_DI_TR6_EXPORT_TO_EXCEL	= "/app-re-broker/marketing-report/report-production/tr6-export-to-excel";
		//Exchange Rate
		final String SD_ER_DROPDOWN_CURR				= "/app-re-broker/static-data/coa/dropdownCurr";
		final String SD_ER_SAVE							= "/app-re-broker/static-data/exchangeRate/save";
		final String SD_ER_INQ							= "/app-re-broker/static-data/exchangeRate/inquiry";
		final String SD_ER_INQ_KMK						= "/app-re-broker/static-data/exchangeRate/inquirykmk";
		final String SD_ER_SAVE_KMK						= "/app-re-broker/static-data/exchangeRate/savekmk";
		final String SD_ER_REMOVE_KMK					= "/app-re-broker/static-data/exchangeRate/removekmk";

		//Business Rules
		final String SD_BR_INQ		    				= "/app-re-broker/static-data/businessRules/inquiry";
		final String SD_BR_INQ_CHILD					= "/app-re-broker/static-data/businessRules/inquiryChild";
		final String SD_BR_SAVE 	   					= "/app-re-broker/static-data/businessRules/save";
		
		//Parameter
		final String SD_PARAMETER_INQ					= "/app-re-broker/static-data/parameter/inquiry";
		final String SD_PARAMETER_INQ_CHILD				= "/app-re-broker/static-data/parameter/inquiryChild";
		final String SD_PARAMETER_SAVE					= "/app-re-broker/static-data/parameter/save";
		
		//User Management
		final String SD_USERMANAGEMENT_INQ					= "/app-re-broker/static-data/usermanagement/inquiry";
		final String SD_USERMANAGEMENT_INQ_CHILD				= "/app-re-broker/static-data/usermanagement/inquiryChild";
		final String SD_USERMANAGEMENT_SAVE					= "/app-re-broker/static-data/usermanagement/save";
		final String SD_USERMANAGEMENT_DROPDOWN					= "/app-re-broker/static-data/usermanagement/dropdown";
		final String SD_USERMANAGEMENT_USERLEVELDROPDOWN					= "/app-re-broker/static-data/usermanagement/userleveldropdown";
		final String SD_USERMANAGEMENT_MPLDROPDOWN					= "/app-re-broker/static-data/usermanagement/mpldropdown";
		final String SD_USERMANAGEMENT_STDATADROPDOWN					= "/app-re-broker/static-data/usermanagement/stdatadropdown";
		final String SD_USERMANAGEMENT_SADROPDOWN					= "/app-re-broker/static-data/usermanagement/sadropdown";
		//User Management
		final String SD_GROUPMANAGEMENT_INQ					= "/app-re-broker/static-data/groupmanagement/inquiry";
		final String SD_GROUPMANAGEMENT_INQ_CHILD				= "/app-re-broker/static-data/groupmanagement/inquiryChild";
		final String SD_GROUPMANAGEMENT_SAVE					= "/app-re-broker/static-data/groupmanagement/save";
		final String SD_GROUPMANAGEMENT_DROPDOWN					= "/app-re-broker/static-data/groupmanagement/dropdown";
		final String SD_GROUPMANAGEMENT_DELETE					= "/app-re-broker/static-data/groupmanagement/delete";	
				
		//Agent Officer

		final String SD_AGENT_OFFICER_INQ		    	= "/app-re-broker/static-data/agentOfficer/inquiry";
		final String SD_AGENT_OFFICER_SAVE 	   			= "/app-re-broker/static-data/agentOfficer/save";

		//TC Standard
		final String SD_TC_STANDARD_INQ					= "/app-re-broker/static-data/tc-standard/inquiry";
		final String SD_TC_STANDARD_DESCRIPTION			= "/app-re-broker/static-data/tc-standard/description";
		final String SD_TC_STANDARD_SAVE				= "/app-re-broker/static-data/tc-standard/save";
		final String SD_TC_STANDARD_DELETE				= "/app-re-broker/static-data/tc-standard/delete";

		//TC Data
		final String SD_TC_DATA_INQ						= "/app-re-broker/static-data/tc-data/inquiry";
		final String SD_TC_DATA_SAVE					= "/app-re-broker/static-data/tc-data/save";
		final String SD_TC_DATA_DELETE					= "/app-re-broker/static-data/tc-data/delete";
		
		//Adjustment
		final String FIN_ADJUSTMENT_INQ					= "/app-re-broker/finance/adjustment/inquiry";
		final String FIN_ADJUSTMENT_SAVE				= "/app-re-broker/finance/adjustment/save";
		
		//Product
		final String SD_PRODUCT_INQ					= "/app-re-broker/static-data/product/inquiry";
		final String SD_PRODUCT_SAVE					= "/app-re-broker/static-data/product/save";
		final String SD_PRODUCT_SAVE_INSURANCE			= "/app-re-broker/static-data/product/save-insurance";
		final String SD_PRODUCT_DROPDOWN				= "/app-re-broker/static-data/product/dropdown";
		final String SD_PRODUCT_INQ_INS					= "/app-re-broker/static-data/product/inquiry-insurance";
		final String SD_PRODUCT_REMOVE_INS				= "/app-re-broker/static-data/product/remove-insurance";
		final String SD_PRODUCT_DROPDOWN_PA_CHILD		= "/app-re-broker/static-data/product/dropdown-pa-child";
		
		//Product
		final String SD_PARTICIPANT_GET_PARTICIPANT		= "/app-re-broker/static-data/participant/getParticipant";
		final String SD_PARTICIPANT_INQ					= "/app-re-broker/static-data/participant/inquiry";
		final String SD_PARTICIPANT_SAVE				= "/app-re-broker/static-data/participant/save";
		
		//Extension
		final String M_EXTENSION_INQ					= "/app-re-broker/marketing/extension/inquiry";
		final String M_EXTENSION_SAVE					= "/app-re-broker/marketing/extension/save";
		final String M_EXTENSION_ENTRY					= "/app-re-broker/marketing/extension/entry";
		final String M_EXTENSION_UPLOAD					= "/app-re-broker/marketing/extension/upload";
		final String M_ISFILENAME_EXIST					= "/app-re-broker/marketing/extension/isFileNameExist";
		
		//Budget and Target
		final String SD_BUDGET_AND_TARGET_INQ			= "/app-re-broker/static-data/budget-and-target/inquiry";
		final String SD_BUDGET_AND_TARGET_SAVE			= "/app-re-broker/static-data/budget-and-target/save";
		final String SD_BUDGET_AND_TARGET_DELETE		= "/app-re-broker/static-data/budget-and-target/delete";
		final String SD_BUDGET_AND_TARGET_EXPORT			= "/app-re-broker/static-data/budget-and-target/export";
		final String SD_BUDGET_AND_TARGET_COA			= "/app-re-broker/static-data/budget-and-target/coa";
		
		//Upload Treaty
		final String M_UPLOAD_TREATY_INQ				= "/app-re-broker/marketing/uploadTreaty/inquiry";
		final String M_UPLOAD_TREATY_UPLOAD				= "/app-re-broker/marketing/uploadTreaty/upload";
		
		final String L_LOGIN_PAGE_ITEM					= "/app-re-broker/login/login-page-item";
		final String L_LOGIN_IS_USER_LOCKED				= "/app-re-broker/login/is-user-locked";
		final String L_LOGIN_RESET_FAILED_LOGIN			= "/app-re-broker/login/reset-failed-login";
		final String L_LOGIN_ADD_ERROR_COUNTER			= "/app-re-broker/login/add-error-counter";
		final String L_LOGIN_RESET_PASSWORD				= "/app-re-broker/login/reset-password";
		final String L_LOGIN_INQUIRY_USERS			= "/app-re-broker/login/inquiry-users";
		
		final String MENU				= "/app-re-broker/menu";
	}


}