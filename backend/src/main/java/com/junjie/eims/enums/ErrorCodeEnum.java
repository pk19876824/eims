package com.junjie.eims.enums;

/**
 * @author junjie.zhang
 * @since 2019/4/21 3:06 PM
 */
public enum ErrorCodeEnum {
  OK(0, "Success"),
  UNKNOWN_ERROR(100000, "Server Error"),
  ADD_EMPLOYEE_ERROR(110000, "Add Employee Error");

  private int errorCode;
  private String errorMsg;

  ErrorCodeEnum(int errorCode, String errorMsg) {
    this.errorCode = errorCode;
    this.errorMsg = errorMsg;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public String getErrorMsg() {
    return errorMsg;
  }
}
