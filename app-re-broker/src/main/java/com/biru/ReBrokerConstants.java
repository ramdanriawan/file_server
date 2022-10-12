package com.biru;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public interface ReBrokerConstants {
	
	interface BIGDECIMAL {
		BigDecimal VAL100 = new BigDecimal("100");
	}
	
	interface CHARACTER {
		final String UNDERSCORE		= "_";
	}
	
	interface CODE {
		//PARENT CODE
		final String P_SYSDATE		= "SYSDATE";
		
		//CHILD CODE
		final String C_SYSDATE001	= "SYSDATE001";
		final String C_TAXRATEH001	= "TAXRATEH001";
		final String C_TAXRATEH002	= "TAXRATEH002";
		final String C_TAXRATEH006	= "TAXRATEH006";
		final String C_TAXRATEH007	= "TAXRATEH007";
		final String COAEOY007		= "COAEOY007";
	}
	
	interface DOCUMENT {
		final String DOC	= "doc";
		final String HTML	= "html";
		final String PDF	= "pdf";
	}
	
	interface MESSAGE {
		final String FINISH			= "FINISH";
		final String ALREADY_CLOSING = "Data production already closing ";
	}
	
	interface PARAM {
		final String LIMIT			= "limit";
		final String OFFSET			= "offset";
		final String ORDER			= "order";
		final String SORT			= "sort";
		final String FILTER_KEY		= "filterKey";
		final String FILTER_VALUE 	= "filterValue";
		final String TENANT_ID 		= "tenantId";
		
		interface KEY {
			final String PA_CODE		= "paParentCode";
			final String PA_DESC		= "paParentDesc";
			final String COA_CODE		= "coaCode";
			final String COA_DESCRIPT	= "coaDescript";
			final String CLI_CODE		= "cliCode";
			final String CLI_NAME		= "cliName";
			final String GL_TYPE		= "glType";
		}
	}
	
	interface REST {
		final String GENERAL	 		= "/general";
		final String STATIC_DATA 		= "/static-data";
		final String ACCOUNTING_REPORT	= "/accounting-report";
		final String ACCOUNTING 		= "/accounting";
		final String EOMEOY 			= "/eomeoy";
		final String MARKETING_REPORT	= "/marketing-report";
		final String MARKETING 			= "/marketing";
		final String FINANCE 			= "/finance";
		final String FINANCE_REPORT 	= "/finance-report";
		final String LOGIN			 	= "/login";
		
		interface ACCOUNTING_REPORT {
			final String BS_CREATE_EXCEL	= "/balanceSheet/create-excel";
			final String BS_CREATE_HTML		= "/balanceSheet/create-html";
			final String BS_CREATE_PDF		= "/balanceSheet/create-pdf";
			final String CF_CREATE_EXCEL	= "/cashFlow/create-excel";
			final String CF_CREATE_HTML		= "/cashFlow/create-html";
			final String CF_CREATE_PDF		= "/cashFlow/create-pdf";
			final String GL_CREATE_EXCEL	= "/generalLedger/create-excel";
			final String GL_CREATE_HTML		= "/generalLedger/create-html";
			final String GL_CREATE_PDF		= "/generalLedger/create-pdf";
			final String PL_CREATE_EXCEL	= "/profitLost/create-excel";
			final String PL_CREATE_HTML		= "/profitLost/create-html";
			final String PL_CREATE_PDF		= "/profitLost/create-pdf";
			final String TB_CREATE_EXCEL	= "/trialBalance/create-excel";
			final String TB_CREATE_HTML		= "/trialBalance/create-html";
			final String TB_CREATE_PDF		= "/trialBalance/create-pdf";
			
		}
		
		interface BEGIN_BAL {
			final String GET_YEAR			= "/beginBal/get-year";
			final String INQUIRY_V1			= "/beginBal/inquiry/v1.0";
			final String INQUIRY_V2			= "/beginBal/inquiry/v2.0";
			final String SAVE				= "/beginBal/save";
			final String SUM_BAL_DEBIT0		= "/beginBal/summaryDebit0";
			final String SUM_BAL_CREDIT0	= "/beginBal/summaryCredit0";
			final String LOOKUP_COA			= "/beginBal/lookup-coa";
		}
		
		interface GENERAL {
			final String APP_DATE				= "/get-appDate";
			final String APP_DATE_NEXT			= "/get-appDateNext";
			final String DROPDOWN_BANK		 	= "/get-dropdownBank";
			final String DROPDOWN_CLASIFICATION = "/get-dropdownClasification";
			final String DROPDOWN_CLIENT		= "/get-dropdownClient";
			final String DROPDOWN_CURRENCY	 	= "/get-dropdownCurrency";
			final String DROPDOWN_DC_NOTE	 	= "/get-dropdownDCNote";
			final String DROPDOWN_DOCUMENT_TYPE = "/dropdown-document-type";
			final String DROPDOWN_NAME 			= "/get-dropdownName";
			final String DROPDOWN_OFFICE		= "/get-dropdownOffice";
			final String DROPDOWN_OFFICER		= "/get-dropdownOfficer";
			final String DROPDOWN_OFFICER_NOT_2	= "/get-dropdownOfficerTypeNot2";
			final String DROPDOWN_PARAM		 	= "/get-dropdownParam";
			final String DROPDOWN_PAY_MTHD		= "/get-dropdownPaymentMethod";
			final String DROPDOWN_POSITION		= "/get-dropdownPosition";	
			final String DROPDOWN_PRODUCT	 	= "/get-dropdownProduct";
			final String DROPDOWN_PROJECT	 	= "/get-dropdownProject";
			final String DROPDOWN_STATUS		= "/get-dropdownStatus";
			final String DROPDOWN_STATUS_CLAIMS	= "/get-dropdownStatusClaims";
			final String DROPDOWN_TC_DETAILS 	= "/dropdown-tc-details";
			final String DROPDOWN_TC_GROUP 		= "/dropdown-tc-group";
			final String DROPDOWN_TYPE_OF_COVER = "/get-dropdownTypeOfCover";
			final String DROPDOWN_VALUE			= "/get-dropdownValue";
			final String DROPDOWN_FORMAT		= "/get-dropdownFormat";
			final String DROPDOWN_CLIENT_TYPE	= "/get-dropdownClientType";
			final String FIND_EXCHANGE			= "/findExchange";
			final String FIND_EXCHANGE_NON_EOM	= "/findExchangeNonEom";
			final String FIND_TAX_RATE			= "/findTaxRate";
			final String GET_CURR_COA			= "/get-currCoa";
			final String GET_PARAMETER			= "/get-parameter";
			final String LAST_PRO_MONTH_YEAR 	= "/get-lastProMonthAndProYear";
			final String LOOKUP_CLIENT		 	= "/lookup-client";
			final String MESSAGE_SAVE			= "/get-messageSave";
			final String YEAR				 	= "/get-year";
			final String LOGO_COMPANY			= "/get-logoCompany";
			final String DROPDOWN_PARTICIPANT_STATUS 		= "/dropdown-participant-status";
			final String DROPDOWN_PARTICIPANT_COUNTRY 		= "/dropdown-participant-country";
			final String DROPDOWN_PARTICIPANT_INDUSTRY		= "/dropdown-participant-industry";
			final String DROPDOWN_TB_YEAR 		= "/dropdown-tb-year";
			final String DROPDOWN_CURR_TAX		= "/dropdown-curr-tax";
			final String TR0006_INQUIRY			= "/tr0006-inquiry";
            final String DROPDOWN_BUDGET_AND_TARGET_OFFICER = "/dropdown-officer";
            final String DROPDOWN_MENU 			= "/dropdown-menu";
			final String IS_ALREADY_CLOSING		= "/is-already-closing";
		}
		
		interface HOLIDAY {
			final String INQUIRY	= "/holiday/inquiry";
			final String IS_EXSIST	= "/holiday/isExsist";
			final String SAVE		= "/holiday/save";
		}
		
		interface COA {
			final String INQUIRY	    = "/coa/inquiry";
			final String ROLE_UP	    = "/coa/roleUp";
			final String DROPDOWN_CLASS = "/coa/dropdownClass";
			final String DROPDOWN_CURR 	= "/coa/dropdownCurr";
			final String SAVE 			= "/coa/save";
		}
		
		interface ENTRY_JOURNAL {
			final String INQUIRY	   			= "/entry-journal/inquiry";
			final String DROPDOWN_PROJECT    	= "/entry-journal/dropdown-project";
			final String DROPDOWN_OFFICE    	= "/entry-journal/dropdown-office";
			final String TRANSACTION_MIN_DATE   = "/entry-journal/transaction-min-date";
			final String CLIENT   				= "/entry-journal/client";
			final String COA   					= "/entry-journal/coa";
			final String VOUCHRE_CODE			= "/entry-journal/voucher-code";
			final String EXCHANGE				= "/entry-journal/exchange";
			final String SAVE					= "/entry-journal/save";
			final String EDIT					= "/entry-journal/edit";
			final String REMOVE					= "/entry-journal/remove";
			final String PRINT_JOURNAL			= "/entry-journal/print-journal";
			final String EXPORT_EXCEL			= "/entry-journal/export-excel";
			final String PRINT					= "/entry-journal/print";
		}
		
		interface INQUIRY_JOURNAL {
			final String DROPDOWN_PROJECT  		= "/inquiry-journal/dropdown-project";
			final String DROPDOWN_OFFICE    	= "/inquiry-journal/dropdown-office";
			final String INQUIRY	   			= "/inquiry-journal/inquiry";
		}
		
		interface EOMEOY {
			final String PROCESS	   			= "/eomeoy/process";
			final String PROGRESS      			= "/eomeoy/progress";
			final String RESET_PROGRESS     	= "/eomeoy/resetProgress";
			final String CHECK_PROCESS      	= "/eomeoy/checkProcess";
		}
		
		interface BANK_BOOK {
			final String INQUIRY		   		= "/bank-book/inquiry";
			final String INQUIRY_MODIFY	   		= "/bank-book/inquiryModify";
			final String SAVE			   		= "/bank-book/save";
			final String DELETE		   			= "/bank-book/delete";
		}
		
		interface DEBIT_CREDIT_NOTES {
			final String INQUIRY		   		= "/dc-note/inquiry";
			final String DELETE		   			= "/dc-note/delete";
			final String SAVE			   		= "/dc-note/save";
			final String CREATE_EXCEL			= "/dc-note/create-excel";
			final String CREATE_HTML			= "/dc-note/create-html";
			final String CREATE_PDF				= "/dc-note/create-pdf";
			final String CREATE_DOC				= "/dc-note/create-doc";
			final String CREATE_HTML_UPLOAD_TREATY= "/dc-note/create-html-upload-treaty";
			final String CREATE_PDF_UPLOAD_TREATY= "/dc-note/create-pdf-upload-treaty";
			final String CREATE_DOC_UPLOAD_TREATY= "/dc-note/create-doc-upload-treaty";
			
		}
		
		interface PRODUCTION {
			final String MA_0011			   	= "/production/ma-0011";
			final String MA_0012			   	= "/production/ma-0012";
			final String TAX_RATE			   	= "/production/tax-rate";
			final String TERM_AND_CONDITION		= "/production/term-and-condition";
			final String SAVE					= "/production/save";
			final String INQUIRY		   		= "/production/inquiry";
			final String INQUIRY_MODIFY	   		= "/production/inquiryModify";
			final String DELETE		   			= "/production/delete";
			final String CREATE_RPT_DOC			= "/production/report/create-doc";
			final String CREATE_RPT_HTML		= "/production/report/create-html";
			final String CREATE_RPT_PDF			= "/production/report/create-pdf";
			final String INQUIRY_SEND			= "/production/inquirySend";
			final String SEND_EMAIL				= "/production/send-email";
			final String CLOSING				= "/production/closing";
			final String CREATE_CLOSING_HTML	= "/production/create-closing-html";
			final String SEND_EMAIL_CLOSING		= "/production/send-email-closing";
			final String IS_VALID_TRX_DATE		= "/production/isValidTrxDate";
			final String VALIDATION_USER_LEVEL	= "/production/validationUserLevel";
		}
		
		interface PRODUCTION_CLASS {
			final String TRE	= "TRE";
			final String FAC	= "FAC";
		}
		
		interface PRODUCTION_TRE_TYPE {
			final String PROPORTIONAL		= "P";
			final String NON_PROPORTIONAL	= "N";
		}
		
		interface DMODIFY {
			final String INQUIRY			= "/direct-modify/inquiry";
			final String INQUIRY_DETAIL		= "/direct-modify/inquiry-detail";
			final String SAVE				= "/direct-modify/save";
		}
		
		interface DPRODUCTION {
			final String GET_COMM_OUT_NAME		= "/direct-production/get-commOutName";
			final String GET_WHT_CLIENT			= "/direct-production/get-whtClient";
			final String SAVE					= "/direct-production/save";
			final String INQUIRY_MODIFY	   		= "/direct-production/inquiryModify";
			final String CREATE_RPT_HTML		= "/direct-production/report/create-html";
			final String CREATE_RPT_PDF			= "/direct-production/report/create-pdf";
			final String CREATE_RPT_DOC			= "/direct-production/report/create-doc";
			final String SEND_EMAIL				= "/direct-production/send-email";
			final String INQUIRY_SEND			= "/direct-production/inquirySend";
			final String CLOSING				= "/direct-production/closing";
			final String CREATE_CLOSING_HTML	= "/direct-production/create-closing-html";
		}

		interface DPRODUCTION_BDS {
			final String GET_COMM_OUT_NAME		= "/direct-production_bds/get-commOutName";
			final String GET_WHT_CLIENT			= "/direct-production_bds/get-whtClient";
			final String SAVE					= "/direct-production_bds/save";
			final String INQUIRY_MODIFY	   		= "/direct-production_bds/inquiryModify";
			final String CREATE_RPT_HTML		= "/direct-production_bds/report/create-html";
			final String CREATE_RPT_PDF			= "/direct-production_bds/report/create-pdf";
			final String CREATE_RPT_DOC			= "/direct-production_bds/report/create-doc";
			final String SEND_EMAIL				= "/direct-production_bds/send-email";
			final String INQUIRY_SEND			= "/direct-production_bds/inquirySend";
			final String CLOSING				= "/direct-production_bds/closing";
			final String CREATE_CLOSING_HTML	= "/direct-production_bds/create-closing-html";
		}

		interface TPRODUCTION {
			final String DC_NOTE_RPT_DOC		= "/treaty-production/dcNote-doc";
			final String DC_NOTE_RPT_PDF		= "/treaty-production/dcNote-pdf";
			final String CLOSING				= "/treaty-production/closing";
			final String CREATE_RPT_DOC			= "/treaty-production/create-doc";
			final String CREATE_RPT_PDF			= "/treaty-production/create-pdf";
			final String DELETE			   		= "/treaty-production/delete";
			final String INQUIRY		   		= "/treaty-production/inquiry";
			final String INQUIRY_MODIFY	   		= "/treaty-production/inquiryModify";
			final String INQUIRY_SEND			= "/treaty-production/inquirySend";
			final String SAVE					= "/treaty-production/save";
		}
		
		interface TADJUSTMENT {
			final String INQUIRY		   		= "/treaty-adjustment/inquiry";
			final String INQUIRY_MODIFY		   	= "/treaty-adjustment/inquiryModify";
			final String SAVE					= "/treaty-adjustment/save";
			final String PROCESS				= "/treaty-adjustment/process";
			final String CANCEL					= "/treaty-adjustment/cancel";
		}
		
		interface REPORT_PRODUCTION {
			final String TRANSACTION_DETAIL		= "/report-production/transaction-detail";
			final String TRANSACTION_DETAIL_DI		= "/report-production/transaction-detail-di";
			final String EXPORT					= "/report-production/export";
			final String PRINT_JOURNAL			= "/report-production/print-journal";
			final String EXPORT_EXCEL			= "/report-production/export-excel";
			final String PRINT					= "/report-production/print";
			final String TR6_EXPORT_TO_EXCEL	= "/report-production/tr6-export-to-excel";
		}
		
		interface SETTLEMENT {
			final String INQUIRY		   		= "/settlement/inquiry";
			final String SAVE			   		= "/settlement/save";
		}
		
		interface REMITTANCE {
			final String INQUIRY		   		= "/remittance/inquiry";
			final String PRINT_JOURNAL			= "/remittance/print-journal";
			final String EXPORT_EXCEL			= "/remittance/export-excel";
			final String PRINT					= "/remittance/print";
		}

		interface OUTSTANDING {
			final String INQUIRY		   		= "/outstanding/inquiry";
			final String PRINT_JOURNAL			= "/outstanding/print-journal";
			final String EXPORT_EXCEL			= "/outstanding/export-excel";
			final String PRINT					= "/outstanding/print";
		}
		
		interface EXCHANGE_RATE {
			final String INQUIRY	    = "/exchangeRate/inquiry";
			final String ROLE_UP	    = "/exchangeRate/roleUp";
			final String DROPDOWN_CLASS = "/exchangeRate/dropdownClass";
			final String DROPDOWN_CURR 	= "/exchangeRate/dropdownCurr";
			final String SAVE 			= "/exchangeRate/save";
			final String INQUIRY_KMK   	= "/exchangeRate/inquirykmk";
			final String SAVE_KMK		= "/exchangeRate/savekmk";
			final String REMOVE_KMK		= "/exchangeRate/removekmk";
		}
		
		interface BUSINESS_RULES {
			final String INQUIRY		= "/businessRules/inquiry";
			final String INQUIRY_CHILD	= "/businessRules/inquiryChild";
			final String SAVE 			= "/businessRules/save";
		}
		
		interface PARAMETER {
			final String INQUIRY		= "/parameter/inquiry";
			final String INQUIRY_CHILD	= "/parameter/inquiryChild";
			final String SAVE 			= "/parameter/save";
		}
		
		interface USERMANAGEMENT {
			final String INQUIRY		= "/usermanagement/inquiry";
			final String INQUIRY_CHILD	= "/usermanagement/inquiryChild";
			final String SAVE 			= "/usermanagement/save";
			final String DROPDOWN		= "/usermanagement/dropdown";
			final String USERLEVEL_DROPDOWN		= "/usermanagement/userleveldropdown";
			final String MPL_DROPDOWN		= "/usermanagement/mpldropdown";
			final String STDATA_DROPDOWN		= "/usermanagement/stdatadropdown";
			final String SA_DROPDOWN		= "/usermanagement/sadropdown";
		}
		
		interface GROUPMANAGEMENT {
			final String INQUIRY		= "/groupmanagement/inquiry";
			final String INQUIRY_CHILD	= "/groupmanagement/inquiryChild";
			final String SAVE 			= "/groupmanagement/save";
			final String DROPDOWN		= "/groupmanagement/dropdown";
			final String DELETE		= "/groupmanagement/delete";
		}
		
		interface GROUP_USER {
			final String INQUIRY		= "/groupUser/inquiry";
			final String INQUIRY_CHILD	= "/groupUser/inquiryChild";
			final String SAVE 			= "/groupUser/save";
		}
		
		interface USER_RIGHT_MANAGEMENT {
			final String INQUIRY		= "/userRightManagement/inquiry";
			final String SAVE 			= "/userRightManagement/save";
		}
		
		interface AGENT_OFFICER {
			final String INQUIRY		= "/agentOfficer/inquiry";
			final String SAVE 			= "/agentOfficer/save";
		}
		
		interface TC_STANDARD {
			final String INQUIRY	    = "/tc-standard/inquiry";
			final String DESCRIPTION	= "/tc-standard/description";
			final String SAVE	    	= "/tc-standard/save";
			final String DELETE	    	= "/tc-standard/delete";
		}
		
		interface TC_DATA {
			final String INQUIRY	    = "/tc-data/inquiry";
			final String SAVE	   		= "/tc-data/save";
			final String DELETE	    	= "/tc-data/delete";
		}
		
		interface ADJUSTMENT {
			final String INQUIRY		= "/adjustment/inquiry";
			final String SAVE			= "/adjustment/save";
		}
		
		interface PRODUCT {
			final String INQUIRY		= "/product/inquiry";
			final String INQUIRY_INS	= "/product/inquiry-insurance";
			final String REMOVE_INS		= "/product/remove-insurance";
			final String SAVE			= "/product/save";
			final String DROPDOWN		= "/product/dropdown";
			final String SAVE_INS		= "/product/save-insurance";
			final String DROPDOWN_PA_CHILD = "/product/dropdown-pa-child";
		}
		
		interface PARTICIPANT {
			final String GET_PARTICIPANT	= "/participant/getParticipant";
			final String INQUIRY			= "/participant/inquiry";
			final String SAVE				= "/participant/save";
		}
		
		interface CANCEL_SETTLEMENT {
			final String INQUIRY		= "/cancel-settlement/inquiry";
			final String SAVE			= "/cancel-settlement/save";
		}
		
		interface CLAIMS {
			final String DELETE					= "/claims/delete";
			final String SAVE					= "/claims/save";
			final String SETTLEMENT				= "/claims/settlement";
			final String CREATE_CLOSING_REPORT	= "/claims/create-closing-report";
			final String INQ					= "/claims/inquiry";
			final String INQ2					= "/claims/inquiry2";
			final String INQ3					= "/claims/inquiry3";
			
			final String INQ_DATA				= "/claims/inquiry-data";
			final String INQ_DETAIL				= "/claims/inquiry-detail";
			final String INQ_CREATE				= "/claims/inquiry-create";
			final String INQ_CREATE_DETAIL		= "/claims/inquiry-create-detail";
			final String GET_UNDERWRITING		= "/claims/get-underwriting";
			final String DROPDOWN_YEAR			= "/claims/dropdown-year";
			final String EXPORT_CLAIMSLIST_EXCEL	= "/claims/export-claimslist-excel";
			final String GET_TR6J				= "/claims/get-tr6j";
			final String PRINT_PLA_DLA			= "/claims/print-dla-pla";
			final String PRINT_DOC_PLA_DLA		= "/claims/print-doc-dla-pla";
			final String CREATE_DC_NOTES		= "/claims/create-dc-notes";
			final String GET_LIST_INSURANCE		= "/claims/get-list-insurance";
			final String GET_LIST_TR15A			= "/claims/get-list-tr15a";
			final String GET_FINANCE			= "/claims/get-finance";
			final String PROCESS_FINANCE		= "/claims/process-finance";
			final String GET_LIST_INTERNALMEMO		= "/claims/get-list-internalmemo";
			final String CREATE_INTERNALMEMO		= "/claims/create-internalmemo";
			final String CREATE_INTERNALMEMO_EXCEL	= "/claims/create-internalmemo-excel";
		}
		
		interface BUDGET_AND_TARGET {
			final String COA			= "/budget-and-target/coa";
			final String INQUIRY			= "/budget-and-target/inquiry";
			final String SAVE				= "/budget-and-target/save";
			final String EXPORT_EXCEL	= "/budget-and-target/export";
		}
		
		interface ENDORSEMENT {
			final String REMOVE					= "/endorsement/remove";
			final String SAVE					= "/endorsement/save";
			final String CREATE_CLOSING_REPORT	= "/endorsement/create-closing-report";
			final String INQ_SEND_TABLE			= "/endorsement/inquiry-send-table";
		}
		
		interface NEW_ENDORSEMENT {
			final String GET_REQUEST_ID			= "/new-endorsement/get-request-id";
			final String CLOSING				= "/new-endorsement/closing";
		}
		
		interface RENEWAL {
			final String INQUIRY			= "/renewal/inquiry";
			final String REMOVE			= "/renewal/remove";
			final String EDIT			= "/renewal/edit";
			final String SEND			= "/renewal/send";
			final String EXPORT			= "/renewal/export";
			final String PRINT			= "/renewal/print";
		}

		interface EXTENSION {
			final String INQUIRY		= "/extension/inquiry";
			final String ENTRY			= "/extension/entry";
			final String UPLOAD			= "/extension/upload";
			final String DESC			= "extDescription";
			final String ISFILENAME_EXIST	= "/extension/isFileNameExist";
		}
		
		interface LOGIN_PAGE {
			final String LOGIN_PAGE_ITEM		= "/login-page-item";
			final String IS_USER_LOCKED			= "/is-user-locked";
			final String ADD_ERROR_COUNTER		= "/add-error-counter";
			final String RESET_FAILED_LOGIN		= "/reset-failed-login";
			final String RESET_PASSWORD			= "/reset-password";
			final String INQUIRY_USERS			= "/inquiry-users";
		}
		
		interface UPLOAD_TREATY {
			final String INQUIRY		= "/uploadTreaty/inquiry";
			final String UPLOAD			= "/uploadTreaty/upload";
			final String VALIDATE_SOURCE_CODE = "/uploadTreaty/isSourceCodeExist";
		}
		
		interface VAT_REPORT{
			final String INQUIRY		= "/vat-report/inquiry";
			final String PRINT		= "/vat-report/print";
			final String EXPORT		= "/vat-report/export";
		}
		
		interface BS_REPORT{
			final String PREVIEW		= "/bs-report/preview";
			final String PRINT		= "/bs-report/print";
			final String EXPORT		= "/bs-report/export";
		}
		
		interface PL_ACCOUNT{
			final String PREVIEW	= "/pl-account/preview";
			final String PRINT		= "/pl-account/print";
			final String EXPORT		= "/pl-account/export";
		}
		
		interface CANCEL_UPLOAD{
			final String SEARCH		= "/cancel-upload/search";
			final String DELETE		= "/cancel-upload/delete";
		}

		interface OJK_SETUP_DIRECT {
			final String INQUIRY	    		= "/ojk-report-setup-direct/inquiry";
			final String SAVE	    		= "/ojk-report-setup-direct/save";
			final String DELETE	    		= "/ojk-report-setup-direct/delete";
			final String DROPDOWN_REPORT_CODE	= "/ojk-report-setup-direct/dropdown-report-code";
			final String DROPDOWN_ROW	    	= "/ojk-report-setup-direct/dropdown-row";
			final String DROPDOWN_COA	    	= "/ojk-report-setup-direct/dropdown-coa";
		}
	}
	
	interface SIMPLE_DATE_FORMAT {
		final SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
		final SimpleDateFormat formatDateDb = new SimpleDateFormat("MM-dd-yyyy");
		final SimpleDateFormat formatDateId = new SimpleDateFormat("dd/MM/yyyy");
		final SimpleDateFormat formatDateMMMyyyy = new SimpleDateFormat("MMM yyyy");
		final SimpleDateFormat formatDateMMMMyyyy = new SimpleDateFormat("MMMM yyyy");
		final SimpleDateFormat formatDateSql = new SimpleDateFormat("yyyy-MM-dd");
		final SimpleDateFormat formatTimestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	}
	
	interface CLOSING_PARAM {
		final String INTEREST_INSURED_LIST = "interestInsuredList";
		final String CLIENT_LIST = "clientList"; 
		final String REINSURANCE_LIST = "reinsuranceList";
		final String COMMOUT_LIST = "commoutList";
		final String PAYMENT_METHOD_CLI_LIST = "paymentMethodCliList";
		final String PAYMENT_METHOD_REINS_LIST = "paymentMethodReinsList";
		final String VALUES_LIST = "valuesList";
		final String VALUES_BANK_LIST = "valuesBankList";
		final String IS_CLOSING = "isClosing";
		
		final String TRE_CLIENT_VALUE 	= "clientValue"; 
		final String TRE_REINS_VALUE 	= "reinsValue";
		
		final String IS_ADJUSTMENT 		= "isAdjustment";
	}
	
	interface PROD_MAPKEY {
		final String BRKR_FEE_VALUE	= "BRKR_FEE_VALUE";
		final String PREMIUM_VALUE 	= "PREMIUM_VALUE";
		final String TAXIN_BF_VALUE	= "TAXIN_BF_VALUE";
	}
	
	interface PARENTCODE {
		final String TAXRATEH		= "TAXRATEH";
	}
	
	interface CHILDCODE {
		final String TAXRATEH003	= "TAXRATEH003";
		final String TAXRATEH009	= "TAXRATEH009";
		final String TAXRATEH011	= "TAXRATEH011";
		final String TAXRATEH012	= "TAXRATEH012";
	}
		
	enum ENDORSEMENT_TYPE {
		ADD("A","ADD"), DED("D","DED");
		
		private final String key;
	    private final String value;

	    ENDORSEMENT_TYPE(String key, String value) {
	        this.key = key;
	        this.value = value;
	    }

	    public String getKey() {
	        return key;
	    }
	    
	    public String getValue() {
	        return value;
	    }
	}
	
}