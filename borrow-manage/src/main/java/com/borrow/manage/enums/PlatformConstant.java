package com.borrow.manage.enums;

/**
 * Created by wxn on 2018/4/20
 */
public  interface PlatformConstant {

    interface FundsParam {
        String SUCCESS = "0000";
        String SIGNATURE = "Signature";

        String CONTROL = "control";
        String IDCARD = "idcard";
        String REQ_NO = "reqNo";
        String REQ_TIME = "reqTime";
        String STATUS = "status";
        String MSG = "msg";

        String ISOPEN = "isOpen";
        String ISOPEN_YES = "0";
        String ISOPEN_NO = "1";

        String USER_TYPE = "userType";

        String USER_TYPE_ASSERT = "1";
        String USER_TYPE_LOAN = "2";

        String LOAN_NO = "loanNo";
        String PRODUCT_NAME = "productName";
        String REPAY_MODE = "repayMode";
        String CLOSE_PERIOD = "closePeriod";
        String LOAN_RATE = "loanRate";
        String FEE_RATE = "feeRate";
        String GPS_COST = "GPSCost";
        String EARLY_SERVICE_RATE = "earlyServiceRate";
        String EARLY_SERVICE_FEE = "earlyServiceFee";
        String MONTH_SERVICE_RATE = "monthServiceRate";
        String MONTH_ACCRUAL_RATE = "monthAccrualRate";
        String GURANTEE_VIOLATE_RATE = "guranteeViolateRate";
        String SERVICE_VIOLATE_RATE = "serviceViolateRate";
        String EARLY_PAY_RATE = "earlyPayRate";
        String LOAN_TYPE = "loanType";
        String LOAN_AMT = "loanAmt";
        String LOANER_ID = "loanerId";
        String LOANER_NAME = "loanerName";
        String LOANER_PHONE = "loanerPhone";
        String LOANER_INDUSTRY = "loanerIndustry";
        String JOB_DESC = "jobDesc";
        String INCOMING_DESC = "incomingDesc";
        String CREDIT_INVESTIGATION = "creditInvestigation";
        String COMPANY_NO = "companyNo";
        String COMPANY_NAME = "companyName";
        String COMPANY_DESC = "companyDesc";
        String LOAN_DESC = "loanDesc";
        String GUARANTEE_COMPANY_NAME = "guaranteeCompanyName";
        String GUARANTEE_INFO_DESC = "guaranteeInfoDesc";

        String LOAN_ID = "loanId";
        String REPAY_ID = "repayId";
        String PERIOD = "period";
        String REPAY_DATE = "repayDate";
        String AMOUNT = "amount";
        String INTEREST = "interest";
        String MONTH_SERVICE_FEE = "monthServiceFee";
        String SERVICE_VIOLATE_FEE = "serviceViolateFee";
        String OUTID = "outID";

        String AUDIT_STATUS = "status";
        String AUDIT_STATUS_YES = "1";
        String AUDIT_STATUS_NO = "2";

        String TYPE = "type";





    }


    interface FundsMethod{
        //用户存管确认
        String LOAN_USER_QUERY_REQUEST = "loanUserQueryRequest";
        //筹标
        String PROJECT_CREATE_REQUEST = "projectCreateRequest";
        //资金划拨
        String LOANER_REPAY_REQUEST = "loanerRepayRequest";

    }

}

