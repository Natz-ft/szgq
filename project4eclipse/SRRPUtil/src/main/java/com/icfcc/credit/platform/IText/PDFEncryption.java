package com.icfcc.credit.platform.IText;

import org.xhtmlrenderer.util.ArrayUtil;

public class PDFEncryption
{
  private byte[] _userPassword;
  private byte[] _ownerPassword;
  private int _allowedPrivileges = 2324;
  private int _encryptionType = 1;

  public PDFEncryption() {
  }

  public PDFEncryption(byte[] userPassword, byte[] ownerPassword) {
    this._userPassword = ArrayUtil.cloneOrEmpty(userPassword);
    this._ownerPassword = ArrayUtil.cloneOrEmpty(ownerPassword);
  }

  public PDFEncryption(byte[] userPassword, byte[] ownerPassword, int allowedPrivileges) {
    this._userPassword = ArrayUtil.cloneOrEmpty(userPassword);
    this._ownerPassword = ArrayUtil.cloneOrEmpty(ownerPassword);
    this._allowedPrivileges = allowedPrivileges;
  }

  public PDFEncryption(byte[] userPassword, byte[] ownerPassword, int allowedPrivileges, int encryptionType) {
    this._userPassword = ArrayUtil.cloneOrEmpty(userPassword);
    this._ownerPassword = ArrayUtil.cloneOrEmpty(ownerPassword);
    this._allowedPrivileges = allowedPrivileges;
    this._encryptionType = encryptionType;
  }

  public byte[] getUserPassword() {
    return ArrayUtil.cloneOrEmpty(this._userPassword);
  }

  public void setUserPassword(byte[] userPassword) {
    this._userPassword = ArrayUtil.cloneOrEmpty(userPassword);
  }

  public byte[] getOwnerPassword() {
    return ArrayUtil.cloneOrEmpty(this._ownerPassword);
  }

  public void setOwnerPassword(byte[] ownerPassword) {
    this._ownerPassword = ArrayUtil.cloneOrEmpty(ownerPassword);
  }

  public int getAllowedPrivileges() {
    return this._allowedPrivileges;
  }

  public void setAllowedPrivileges(int allowedPrivileges) {
    this._allowedPrivileges = allowedPrivileges;
  }

  public int getEncryptionType() {
    return this._encryptionType;
  }

  public void setEncryptionType(int encryptionType) {
    this._encryptionType = encryptionType;
  }
}