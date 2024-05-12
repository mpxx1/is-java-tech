package me.macao.business.model.interfaces;

import lombok.NonNull;
import me.macao.business.exception.PasswdCreateException;
import me.macao.business.model.enums.UserAccountType;

import java.util.Collection;

/**
 * An interface representing a user.
 */
public interface User {

  /**
   * Returns the unique ID of this user.
   *
   * @return the unique ID of this user.
   */
  long getId();

  /**
   * Returns the password of this user.
   *
   * @return the password of this user.
   */
  short getPasswd();

  /**
   * Sets the password of this user.
   *
   * @param passwd the new password of this user.
   * @throws PasswdCreateException if the password is not valid.
   */
  void setPasswd(short passwd) throws PasswdCreateException;

  /**
   * Returns the user account type of this user.
   *
   * @return the user account type of this user.
   */
  @NonNull
  UserAccountType getUserAccountType();

  /**
   * Returns the name of this user.
   *
   * @return the name of this user.
   */
  @NonNull
  String getName();

  /**
   * Returns the surname of this user.
   *
   * @return the surname of this user.
   */
  @NonNull
  String getSurname();

  /**
   * Returns the address of this user.
   *
   * @return the address of this user.
   */
  @NonNull
  Address getAddress();

  /**
   * Returns the passport number of this user.
   *
   * @return the passport number of this user.
   */
  @NonNull Long getPassport();

  /**
   * Sets the passport number of this user.
   *
   * @param passport the new passport number of this user.
   */
  void setPassport(Long passport);

  /**
   * Sets the address of this user.
   *
   * @param address the new address of this user.
   */
  void setAddress(Address address);

  /**
   * Sends a message to this user.
   *
   * @param message the message to send.
   */
  void sendMessage(Message message);

  /**
   * Returns the collection of messages received by this user.
   *
   * @return the collection of messages received by this user.
   */
  @NonNull
  Collection<@NonNull Message> getMessages();

  /**
   * Clears the collection of messages received by this user.
   */
  void clearMessages();

  /**
   * Returns whether this user is subscribed to updates.
   *
   * @return whether this user is subscribed to updates.
   */
  boolean isSubscribedToUpdates();

  /**
   * Sets whether this user is subscribed to updates.
   *
   * @param subscribedToUpdates whether this user is subscribed to updates.
   */
  void setSubscribedToUpdates(boolean subscribedToUpdates);
}
